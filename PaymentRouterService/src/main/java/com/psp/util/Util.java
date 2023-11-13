package com.psp.util;

public class Util {

    //need to be done
    public static <T> T decodeObj(String serialObj, Class<T> type){
        return null;
    }

    public static String assembleUrl(String ip, String port, String url){
        String res = "http://";
        return res.concat(ip).concat(":").concat(port).concat(url);
    }
}
