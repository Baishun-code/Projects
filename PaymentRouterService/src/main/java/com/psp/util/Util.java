package com.psp.util;

import com.google.gson.Gson;

public class Util {

    private static Gson gson = new Gson();

    //
    public static <T> T decodeObj(String serialObj, Class<T> type){
        return gson.fromJson(serialObj, type);
    }

    public static String assembleUrl(String ip, String port, String url){
        String res = "http://";
        return res.concat(ip).concat(":").concat(port).concat(url);
    }
}
