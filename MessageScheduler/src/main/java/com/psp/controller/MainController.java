package com.psp.controller;

import com.psp.entity.ResponseV0;
import com.psp.task.FetchManger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class MainController {

    @Autowired
    public FetchManger fetchManger;

    @RequestMapping("/test")
    public String Test(){
        return "test";
    }

    @RequestMapping("/pending/data")
    public ResponseV0 pendingData(){
        List<String> messagesInHandling = fetchManger.getMessagesInHandling();
        return ResponseV0.success(messagesInHandling);
    }


}
