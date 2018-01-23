package com.lyf.okhttputils.ui;

import android.os.Bundle;
import com.lyf.okhttputils.R;
import com.lyf.okhttputils.bean.BaseMsg;
import com.lyf.okhttputils.mvp.model.get.GetAppRequest;
import com.lyf.okhttputils.okhttp.interfaces.Callback;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetAppRequest.getAppConfig(new Callback<BaseMsg>() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void onResponse(Call call, Response response, BaseMsg bean) {

            }
        });

    }


}
