package com.psp.task;

import com.psp.entity.TdTxService;
import com.psp.service.ScheduleNameService;
import com.psp.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

@Slf4j
public class FetchManagerService implements FetchManger{

    private LinkedBlockingDeque<MessageWrapper> messageObjs;
    private LinkedBlockingDeque<String> callBackMessages;
    private KafkaTemplate<String, String> kafkaTemplate;
    //store key unique value of each record normally serial number
    //and MessageWrapper pairs
    //Functionality:
    //(1) While the message is in process, it will be stored in the map
    //and data with same key will be discarded to avoid sending the data
    //for multiple times, if the message is canceled from its original
    //service or filed to delivery, the record will be removed from map
    //(2) all back thread can easily get the service name of a serialNum
    private ConcurrentHashMap<String, MessageWrapper> map;
    private FetchThread fetchThread;
    private RestTemplate restTemplate;
    private ScheduleNameService scheduleNameService;
    private int FETCH_THREAD_GROUP_SIZE;
    private int CALLBACK_THREAD_GROUP_SIZE;
    private Thread[] fetchThreadGroup;
    private Thread[] callbackThreadGroup;
    private boolean USE_BATCH;
    private int BATCH_SIZE;


    public FetchManagerService(KafkaTemplate<String, String> kafkaTemplate,
                               ScheduleNameService scheduleNameService,
                               RestTemplate restTemplate,
                               int fetchSize,
                               int callBackSize,
                               boolean USE_BATCH,
                               int BATCH_SIZE){
        callBackMessages = new LinkedBlockingDeque<>();
        this.kafkaTemplate = kafkaTemplate;
        messageObjs = new LinkedBlockingDeque<>();
        map = new ConcurrentHashMap<>();
        FETCH_THREAD_GROUP_SIZE = fetchSize;
        CALLBACK_THREAD_GROUP_SIZE = callBackSize;
        this.restTemplate = restTemplate;
        this.scheduleNameService = scheduleNameService;
        startThreadGroup();
    }

    public void pollFromService(){
        //all services need data transformation
        List<TdTxService> tdTxServices = scheduleNameService.getAllFetchTdTx();
        Map<String, MessageWrapper> curMessageMap = new HashMap<>();
        for (int i = 0; i < tdTxServices.size(); i++) {
            TdTxService tdTxService = tdTxServices.get(i);
            String url =
                    Util.assembleUrl(tdTxService.getSerName(), tdTxService.getReqUri());
            if(!curMessageMap.containsKey(tdTxService.getSerName())){
                curMessageMap.put(tdTxService.getSerName(),
                        new MessageWrapper(tdTxService.getTopic(), tdTxService.getSerName()));
            }

            MessageWrapper messageWrapper = curMessageMap.get(tdTxService.getSerName());
            // 0 means this is collect url
            if("0".equals(tdTxService.getInterfaceType())){
                messageWrapper.setReqUrl(url);
            }else {//cancel url
                messageWrapper.setCallBackUrl(url);
            }
        }

        //putting all polling url into messageObjs
        for (Map.Entry<String, MessageWrapper> stringMessageWrapperEntry : curMessageMap.entrySet()) {
            messageObjs.add(stringMessageWrapperEntry.getValue());
        }

    }

    public List<String> getMessagesInHandling(){
        List<String> res = new ArrayList<>();
        for (Map.Entry<String, MessageWrapper> messageEntry : map.entrySet()) {
            res.add(messageEntry.getKey());
        }
        return res;
    }

    private void startThreadGroup(){
        log.info("Starting threads ...");
        fetchThreadGroup = new Thread[FETCH_THREAD_GROUP_SIZE];
        callbackThreadGroup = new Thread[CALLBACK_THREAD_GROUP_SIZE];

        log.info("Start {} fetch threads ", fetchThreadGroup.length);
        for (int i = 0; i < fetchThreadGroup.length; i++) {
            fetchThreadGroup[i] = new Thread(new FetchThread(
                    messageObjs,
                    kafkaTemplate,
                    map,
                    callBackMessages,
                    restTemplate,
                    this));
            fetchThreadGroup[i].setName("FetchManager-Fetch-Thread-"+i);
            fetchThreadGroup[i].start();
        }

        log.info("Start {} callback threads ", callbackThreadGroup.length);
        for (int i = 0; i < callbackThreadGroup.length; i++) {
            callbackThreadGroup[i] = new Thread(new CallBackThread(
                    callBackMessages,
                    map,
                    restTemplate,
                    USE_BATCH,
                    BATCH_SIZE));
            callbackThreadGroup[i].setName("FetchManager-Callback-Thread-"+i);
            callbackThreadGroup[i].start();
        }
    }

//    public void put(Map<String, Object> dataMap, String topic, String serviceName){
//        for (Map.Entry<String, Object> messageBody : dataMap.entrySet()) {
//            if(!map.containsKey(messageBody.getKey())){
//                MessageWrapper messageWrapper = new MessageWrapper(messageBody.getKey(),
//                        topic, messageBody.getValue(), serviceName);
//                messageObjs.addFirst(messageWrapper);
//                map.put(messageBody.getKey(), messageWrapper);
//            }
//        }
//    }

    public FetchThread getFetchThread() {
        return fetchThread;
    }

    public void setFetchThread(FetchThread fetchThread) {
        this.fetchThread = fetchThread;
    }
}
