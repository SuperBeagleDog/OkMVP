package com.lyf.okmvp.okhttp.interfaces;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @Author Lyf
 * @CreateTime 2018/1/22
 * @Description 网络请求的方式
 **/
public interface IRequestMethods extends IBaseRequestMethods {


    void doGet(@NonNull String url);

    <T> void doGet(@NonNull String url, @Nullable Callback<T> responseCallback);


    /**
     *
     */
    void doPost(Object tag, String url);

    /**
     *
     */
    void doPut(Object tag, String url);

    /**
     *
     */
    void doDelete(Object tag, String url);

}
