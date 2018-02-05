package com.lyf.okmvp.model.login;

import android.util.ArrayMap;

import framework.bean.BaseMsg;
import framework.okhttp.HttpManager;
import framework.okhttp.consts.HttpConst;
import framework.okhttp.interfaces.Callback;
import framework.utils.GsonTools;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @Author Lyf
 * @CreateTime 2018/2/5
 * @Description
 **/
public class PostLoginRequest {

    public static void PostLogin(final ArrayMap<String, Object> params, final Callback<BaseMsg> responseCallback) {

        HttpManager.getInstance().doPost(HttpConst.POST_LOGIN_REQUEST, params,
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
