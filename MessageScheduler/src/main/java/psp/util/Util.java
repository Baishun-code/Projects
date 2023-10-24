package psp.util;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;


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

    public static String getStringDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String curDate = simpleDateFormat.format(date);
        return curDate;
    }


}
