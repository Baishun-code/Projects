package psp.task;

import psp.util.Util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentMap;

public class MessageTask implements Runnable{

    private String key;
    private ConcurrentHashMap<String, Object> map;
    private String topic;
    private ConcurrentLinkedDeque<Object> messageBodies;
    private boolean RUNNING;

    public MessageTask(ConcurrentLinkedDeque<Object> messageBodies,
                       ConcurrentHashMap<String, Object> map,
                       String topic){
        this.key = key;
        this.topic = topic;
        this.map = map;
        RUNNING = true;
        this.messageBodies = messageBodies;
    }

    @Override
    public void run() {
        while (RUNNING){
            Object messageObj = messageBodies.pollLast();
            String message = Util.converObjectToJason(messageObj);
        }
    }
}
