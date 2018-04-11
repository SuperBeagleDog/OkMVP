package com.lyf.okmvp;

import android.app.Application;

import framework.SingletonFactory;
import framework.net.http.HttpBuilder;
import framework.net.HttpManager;


/**
 * @Author Lyf
 * @CreateTime 2018/1/23
 * @Description
 **/
public class LApplication extends Application {

    public static Application INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;

        HttpManager.initHttpManager(new HttpBuilder()
                .setCodeSuccess(0)
                .setConnectTimeOut(30 * 1000)
                .setReadTimeOut(30 * 1000)
                .setWriteTimeOut(30 * 1000));

        SingletonFactory.initSingletonFactory(this);

    }

}
