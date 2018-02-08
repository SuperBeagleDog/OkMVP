package com.lyf.okmvp.http;


import android.util.ArrayMap;

import framework.okhttp.BaseHttpManager;
import framework.okhttp.header.BaseHeaderManager;

/**
 * @Author Lyf
 * @CreateTime 2018/2/8
 * @Description
 **/
public class HttpManager extends BaseHttpManager {

    private static HttpManager INSTANCE;

    private HttpManager() {
    }

    public static HttpManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HttpManager();
        }

        return INSTANCE;
    }

    @Override
    protected ArrayMap<String, Object> getSignParams(ArrayMap<String, Object> params) {

        BaseHeaderManager.getHeaderManager();
        // do sign params here.
        return params;
    }

}