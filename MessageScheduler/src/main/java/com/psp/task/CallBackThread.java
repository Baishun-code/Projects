package com.psp.task;

import com.psp.entity.TdTxService;
import com.psp.service.ScheduleNameService;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.psp.entity.ResponseV0;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

import static com.psp.util.Util.assembleUrl;

public class CallBackThread implements Runnable{

    private LinkedBlockingDeque<String> callBackMessages;
    private ConcurrentHashMap<String, MessageWrapper> map;
    private RestTemplate restTemplate;
    private boolean USING_BATCH;
    private ScheduleNameService callBackUrlService;
    private int BATCH_SIZE;
    private boolean RUNNING;
    private HashMap<String, List<String>> mapList;

    public CallBackThread(LinkedBlockingDeque<String> callBackMessages,
                          ConcurrentHashMap<String, MessageWrapper> map,
                          RestTemplate restTemplate,
                          boolean useBatch,
                          int batchSize,
                          ScheduleNameService callBackUrlService){
        this.callBackMessages = callBackMessages;
        this.map = map;
        this.USING_BATCH = useBatch;
        this.BATCH_SIZE = batchSize;
        this.restTemplate = restTemplate;
        this.callBackUrlService = callBackUrlService;
        RUNNING = true;
        //contains service data pair, because multiple service
        //could have callback requests
        mapList = new HashMap<>();
    }

    @Override
    public void run() {

        while (RUNNING){
            try {
                doWork();
            }catch (Exception e){

            }
        }
    }

    private void doWork() throws InterruptedException {
        //get call back data from blocking queue
        //if there is no data in the queue, the
        //code would block
        String key = callBackMessages.take();
        //get service name of the data
        String serName = map.get(key).serviceName;
        //if mapList doesn't contain serName before
        //initialize it
        if(mapList.containsKey(serName)){
            mapList.put(serName, new LinkedList<>());
        }
        List<String> curList = mapList.get(serName);
        curList.add(key);
        //get the request url from database
        TdTxService tdTxService = callBackUrlService.queryCallBackUrl(serName);
        String reqUri = tdTxService.getReqUri();
        //assemble request url via common assemble method
        String url = assembleUrl(serName, reqUri);

        //if size of curList is equal or larger than BATCH_SIZE,
        //send request to the service to cancel tx data, larger
        //because there it is possible that data is failed to
        //send back, next iteration the size of curList will be larger
        if((USING_BATCH && curList.size() >= BATCH_SIZE) ||
                !USING_BATCH){//if not in batch model, send data directly
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity httpEntity = new HttpEntity<Object>(curList, headers);
            //sending post request in sync model, because need to decide
            //whether to remove the data from curList and map based on
            //the response code sending back
            ResponseEntity<ResponseV0> responseEntity =
                    restTemplate.postForEntity(url, httpEntity, ResponseV0.class);
            ResponseV0 body = responseEntity.getBody();
            //only remove data when the response code is 200,
            //which means that original service has deleted the
            //recode from tx table in its database
            if("200".equals(body.code)){
                curList.clear();
                map.remove(key);
            }
        }
    }

}
