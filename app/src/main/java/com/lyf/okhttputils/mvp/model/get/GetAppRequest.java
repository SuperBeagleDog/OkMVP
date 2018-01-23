package com.lyf.okhttputils.mvp.model.get;

import com.lyf.okhttputils.bean.BaseMsg;
import com.lyf.okhttputils.okhttp.HttpManager;
import com.lyf.okhttputils.okhttp.consts.HttpConst;
import com.lyf.okhttputils.okhttp.interfaces.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @Author Lyf
 * @CreateTime 2018/1/23
 * @Description 与app/相关的网络请求
 **/
public class GetAppRequest {

    /**
     * 获取App的配置
     * @param responseCallback 通过该回调，返回App的配置
     */
    public static void getAppConfig(final Callback<BaseMsg> responseCallback) {

        HttpManager.getInstance().doGet(HttpConst.GET_APP_CONFIGS,
                new Callback<BaseMsg>() {
                    @Override
                    public void onFailure(Call call, Exception e) {

                        if (responseCallback != null) {
                            responseCallback.onFailure(call, e);
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response, BaseMsg bean) {

                        if (responseCallback != null) {
                            responseCallback.onResponse(call, response, bean);
                        }

                    }
                });

    }

}
