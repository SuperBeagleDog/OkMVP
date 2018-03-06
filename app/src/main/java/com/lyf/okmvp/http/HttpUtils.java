package com.lyf.okmvp.http;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

import framework.net.BaseHttpUtil;
import framework.net.header.HeaderManager;
import framework.net.response.Callback;

/**
 * @Author Lyf
 * @CreateTime 2018/2/8
 * @Description
 **/
public class HttpUtils extends BaseHttpUtil{

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
        return params;
    }

}