package com.psp.entity;

public class ResponseV0 {

    public String code;
    public String msg;
    public Object response;

    public ResponseV0(String code, String msg, Object response){
        this.code = code;
        this.msg = msg;
        this.response = response;
    }

    public ResponseV0(String code, String msg){
        this.code = code;
        this.msg = msg;
        this.response = response;
    }

    public static ResponseV0 success(Object reponse){
        return new ResponseV0("200", "success", reponse);
    }

    public static ResponseV0 success(String msg, Object reponse){
        return new ResponseV0("200", msg, reponse);
    }

    public static ResponseV0 fail(String msg){
        return new ResponseV0("300", msg);
    }
}
