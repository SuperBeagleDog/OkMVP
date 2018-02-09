package com.lyf.okmvp.http;


import android.util.ArrayMap;

import framework.okhttp.BaseHttpUtil;
import framework.okhttp.header.HeaderManager;

/**
 * @Author Lyf
 * @CreateTime 2018/2/8
 * @Description
 **/
public class HttpUtils extends BaseHttpUtil {

    private static HttpUtils INSTANCE;

    private HttpUtils() {
    }

    public static HttpUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HttpUtils();
        }

        return INSTANCE;
    }

    @Override
    protected ArrayMap<String, Object> getSignParams(ArrayMap<String, Object> params) {

        HeaderManager.getHeaderManager();
        // do sign params here.
        return params;
    }

}