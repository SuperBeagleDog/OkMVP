package com.lyf.okmvp.utils;


public class LogUtil {

    private final static String DEFAULT_TAG = "DEFAULT_TAG";

    public static void log(String msg) {
        android.util.Log.d(DEFAULT_TAG, msg);
    }

    public static void log(String tag, String msg) {
        android.util.Log.d(tag, msg);
    }

}
