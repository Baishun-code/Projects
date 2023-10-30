package com.psp.util;

import com.google.gson.Gson;
import com.psp.entity.ResponseV0;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class Util {

    private static final Gson gson = new Gson();

    public static String converObjectToJason(Object o){
        return gson.toJson(o);
    }

    public static <T> T converJasonToObject(String json, Class<T> type){
        return gson.fromJson(json, type);
    }

    public static Date getDataDate(){
        return new Date();
    }

    //need completion
    public static Map<String, Object> retrectMessageFromReponse(ResponseV0 responseV0){
        return null;
    }

    //
    public static String assembleUrl(String serName, String reqUri){
        String url = "http://";
        return url.concat(serName).concat(reqUri);
    }

    public static String getStringDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String curDate = simpleDateFormat.format(date);
        return curDate;
    }


}
