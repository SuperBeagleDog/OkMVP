package com.lyf.okmvp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lyf.okmvp.R;
import com.lyf.okmvp.http.HttpUtils;

import framework.bean.BaseBean;
import framework.database.daos.UserDao;
import framework.database.databases.UserDataBase;
import framework.database.entities.UserInfo;
import framework.mvp.model.get.GetAppRequest;
import framework.net.response.Callback;
import framework.net.response.Response;
import framework.thread.ThreadManager;

import framework.thread.interfaces.ObserverListener;
import framework.thread.interfaces.SubscribeListener;
import framework.utils.LogUtil;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ThreadManager.runOnUiThread(()
                -> Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show());


//        System.out.print("ss");
//        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                doNetWorkTest();
//            }
//        });
//
//        int answer = 42;
//
//        Thread t = new Thread(new Runnable() {
//
//            public void run() {
//
//                System.out.println("The answer is: " + answer);
//
//            }
//
//        });
    }

    private void doNetWorkTest() {

        GetAppRequest.getAppConfig(new Callback<BaseBean>() {
            @Override
            public void onFailure(int errorCode, String errorMsg, Response<BaseBean> response) {
                LogUtil.log("onFailure=" + Thread.currentThread().getName());
            }

            @Override
            public void onResponse(BaseBean bean, Response<BaseBean> response) {
                LogUtil.log("onResponse=" + Thread.currentThread().getName());
            }
        });

    }

    private void runRxJava2Demo() {

//        RxJava2Demo.create();
//        RxJava2Demo.fromPublisher();
//        RxJava2Demo.just();
//        RxJava2Demo.amb();
//        RxJava2Demo.ambArray();
//        RxJava2Demo.combineLatest();
//        RxJava2Demo.concat();
//        RxJava2Demo.fromCallable();
//        RxJava2Demo.fromFuture();
//        RxJava2Demo.merge();
//        RxJava2Demo.mergeArray();
//        RxJava2Demo.fromCallable();
//        RxJava2Demo.fromIterable();

    }

    private void toastUserBean() {

        ThreadManager.execute(new SubscribeListener<UserInfo>() {
            @Override
            public UserInfo runOnSubThread() throws Exception {
                // Do something on subThread and then return the T bean.
                UserDao userDao = UserDataBase.getInstance(MainActivity.this).getUserDao();
                // query a user
                return userDao.getUserInfoViaId(100);
            }
        }, new ObserverListener<UserInfo>() {
            @Override
            public void runOnUiThread(@Nullable UserInfo userInfo) {
                // Do something on UiThread with UserInfo class.
                if (userInfo == null) {
                    Toast.makeText(MainActivity.this, "query failed!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "userName=" + userInfo.getFirstName() + userInfo.getLastName(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void log(String log) {
        Log.d(MainActivity.class.getSimpleName(), log);
    }

}
