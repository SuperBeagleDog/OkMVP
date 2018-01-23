package com.lyf.okhttputils.okhttp;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @Author Lyf
 * @CreateTime 2018/1/22
 * @Description OkHttp的工厂类，用于统一生产OkHttpClient实例。
 **/
class OkFactory {

    //设置连接超时时间
    private final static int CONNECT_TIMEOUT = 500;
    //设置读取超时时间
    private final static int READ_TIMEOUT = 500;
    //设置写的超时时间
    private final static int WRITE_TIMEOUT = 500;

    //整个项目，一个Client
    private static OkHttpClient OK_INSTANCE;

    static OkHttpClient getOkHttpClient() {

        try {
            if (OK_INSTANCE == null) {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                        .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
                OK_INSTANCE = builder.build();
            }

            return OK_INSTANCE;
        } catch (Exception e) {
            return null;
        }

    }


}
