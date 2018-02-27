package com.lyf.okmvp.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.lyf.okmvp.R;

import framework.bean.BaseBean;
import framework.mvp.model.get.GetAppRequest;
import framework.net.response.Callback;
import framework.net.response.Response;

import com.lyf.okmvp.demo.rxjava2.TransformingOperations;
import framework.thread.ThreadManager;

import framework.utils.LogUtil;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ThreadManager.runOnUiThread(() ->
                toast("test"));
        TransformingOperations.actionFlatMap();


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

    private void toast(String test) {
        Toast.makeText(getApplicationContext(), test, Toast.LENGTH_SHORT).show();
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




    private void log(String log) {
        Log.d(MainActivity.class.getSimpleName(), log);
    }

}
