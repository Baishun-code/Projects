package com.psp.task;

import com.psp.entity.TdTxService;
import com.psp.service.ScheduleNameService;
import com.psp.util.Util;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class FetchManager {

    private LinkedBlockingDeque<MessageWrapper> messageObjs;
    private LinkedBlockingDeque<String> callBackMessages;
    private KafkaTemplate<String, String> kafkaTemplate;
    private ConcurrentHashMap<String, MessageWrapper> map;
    private FetchThread fetchThread;
    private CallBackThread callBackThread;
    private RestTemplate restTemplate;
    private ScheduleNameService scheduleNameService;

    public FetchManager(KafkaTemplate<String, String> kafkaTemplate,
                        ScheduleNameService scheduleNameService,
                        RestTemplate restTemplate){
        callBackMessages = new LinkedBlockingDeque<>();
        this.kafkaTemplate = kafkaTemplate;
        messageObjs = new LinkedBlockingDeque<>();
        map = new ConcurrentHashMap<>();
        this.restTemplate = restTemplate;
        this.scheduleNameService = scheduleNameService;
        new Thread(new FetchThread(messageObjs,
                kafkaTemplate,
                map,
                callBackMessages,
                restTemplate,
                this)).start();
    }

    public void pollFromService(){
        List<TdTxService> tdTxServices = scheduleNameService.getAllFetchTdTx();
        for (int i = 0; i < tdTxServices.size(); i++) {
            TdTxService tdTxService = tdTxServices.get(i);
            String url =
                    Util.assembleUrl(tdTxService.getSerName(), tdTxService.getReqUri());
            messageObjs
                    .addFirst(new MessageWrapper(tdTxService.getTopic(),
                    tdTxService.getSerName(),
                    url));
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
