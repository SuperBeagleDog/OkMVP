package com.lyf.okmvp.okhttp.interfaces;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.ArrayMap;

/**
 * @Author Lyf
 * @CreateTime 2018/1/23
 * @Description
 **/
public interface IBaseRequestMethods {

    /**
     * @param url                 Get请求的地址
     * @param params              请求参数
     * @param responseCallback<T> 请求返回的结果，其中的T,是json对应的Bean类
     */
    <T> void doGet(@NonNull String url, @Nullable ArrayMap<String, Object> params, @Nullable Callback<T> responseCallback);
    
}
