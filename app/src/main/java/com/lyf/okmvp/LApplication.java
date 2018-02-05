package com.lyf.okmvp;

import android.app.Application;

import framework.SingletonFactory;

/**
 * @Author Lyf
 * @CreateTime 2018/1/23
 * @Description
 **/
public class LApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SingletonFactory.initSingletonFactory(this);
    }

}
