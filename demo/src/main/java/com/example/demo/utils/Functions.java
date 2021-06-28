package com.example.demo.utils;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Functions {

    public static Map<String, Object> page(String status, long total_item, int total_page, int per_page, int current_page, Object data)
    {
        Map<String, Object> ret = new HashMap<>();

        ret.put("status", status);
        ret.put("total_item", total_item);
        ret.put("total_page", total_page);
        ret.put("per_page", per_page);
        ret.put("current_page", current_page);
        ret.put("response_data", data);

        return ret;
    }

    public static Map<String, Object> response(String status, String msg, Object data)
    {
        Map<String, Object> ret = new HashMap<>();
        ret.put("status", status);
        ret.put("message", msg);
        ret.put("response_data", data);

        return ret;
    }

    public static Map<String, Object> error(Integer errCd, String errMsg, String msg)
    {
        Map<String, Object> error = new HashMap<>();
        error.put("error_code", errCd);
        error.put("error_message", errMsg);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "failed");
        response.put("message", msg);
        response.put("error", error);

        return response;
    }

    public static String generateRandom(int length){
        Random random = new SecureRandom();
        String isi = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder value = new StringBuilder(length);
        for (int i = 0; i < length; i++){
            value.append(isi.charAt(random.nextInt(isi.length())));
        }
        return new String(value);
    }

    public static Timestamp getTimeStamp()
    {
        Date date= new Date();
        long time = date.getTime();
        Timestamp timestamp = new Timestamp(time);

        return timestamp;
    }
}
