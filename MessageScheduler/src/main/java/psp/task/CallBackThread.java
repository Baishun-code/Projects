package psp.task;

import org.springframework.web.client.RestTemplate;
import psp.util.Util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class CallBackThread implements Runnable{

    private LinkedBlockingDeque<String> callBackMessages;
    private ConcurrentHashMap<String, MessageWrapper> map;
    private FetchManager fetchManager;
    private RestTemplate restTemplate;
    private boolean RUNNING;

    public CallBackThread(LinkedBlockingDeque<String> callBackMessages,
                          ConcurrentHashMap<String, MessageWrapper> map,
                          FetchManager fetchManager,
                          RestTemplate restTemplate){
        this.callBackMessages = callBackMessages;
        this.map = map;
        this.fetchManager = fetchManager;
        this.restTemplate = restTemplate;
    }

    @Override
    public void run() {
        try {
            while (RUNNING){
                String key = callBackMessages.take();
                String serName = map.get(key).serviceName;
                //restTemplate.exchange();
                map.remove(key);
            }
        }catch (Exception e){

        }
    }
}
