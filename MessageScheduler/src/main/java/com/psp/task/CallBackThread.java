package com.psp.task;

import com.psp.service.CallBackUrlService;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.psp.entity.ResponseV0;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class CallBackThread implements Runnable{

    private LinkedBlockingDeque<String> callBackMessages;
    private ConcurrentHashMap<String, MessageWrapper> map;
    private FetchManager fetchManager;
    private RestTemplate restTemplate;
    private boolean USING_BATCH;
    private CallBackUrlService callBackUrlService;
    private int BATCH_SIZE;
    private boolean RUNNING;

    public CallBackThread(LinkedBlockingDeque<String> callBackMessages,
                          ConcurrentHashMap<String, MessageWrapper> map,
                          FetchManager fetchManager,
                          RestTemplate restTemplate,
                          boolean useBatch,
                          int batchSize,
                          CallBackUrlService callBackUrlService){
        this.callBackMessages = callBackMessages;
        this.map = map;
        this.USING_BATCH = useBatch;
        this.fetchManager = fetchManager;
        this.BATCH_SIZE = batchSize;
        this.restTemplate = restTemplate;
        this.callBackUrlService = callBackUrlService;
    }

    @Override
    public void run() {
        //contains service data pair, because multiple service
        //could have callback requests
        HashMap<String, List<String>> mapList = new HashMap<>();
        while (RUNNING){
            try {
                //get call back data from blocking queue
                //if there is no data in the queue, the
                //code would block
                String key = callBackMessages.take();
                //get service name of the data
                String serName = map.get(key).serviceName;
                List<String> curList = mapList.get(serName);
                curList.add(key);
                //get the request url from database
                String url = callBackUrlService.queryCallBackUrl(serName);

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
                        curList = new LinkedList<>();
                        map.remove(key);
                    }
                }
            }catch (Exception e){

            }

        }



    }


    public static void main(String[] args) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        List<String> list = new ArrayList<>();
        list.add("abc");
        list.add("vbc");
        list.add("asd");
        URI uri = new URI("http://localhost:8001/tx/test");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(list, headers);
        ResponseEntity<ResponseV0> exchange =
                restTemplate.postForEntity(uri, httpEntity, ResponseV0.class);
        ResponseV0 body = exchange.getBody();
        System.out.println(body.response);
    }
}
