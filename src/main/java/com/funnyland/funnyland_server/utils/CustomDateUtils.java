package com.funnyland.funnyland_server.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomDateUtils {
    public static long getCurrentTimeMillis(){
        return System.currentTimeMillis();
    }

    public static LocalDateTime getCurrentDateTime(){
        return LocalDateTime.now();
    }

    public static String getCurrentKRDate2yyyyMMddHHmmss_SSS(){
        LocalDateTime currentDateTime = LocalDateTime.now();

        String result = currentDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss_SSS"));
        return result;

    }
}
