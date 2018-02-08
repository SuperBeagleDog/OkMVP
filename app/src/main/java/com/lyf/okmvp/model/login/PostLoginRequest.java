package com.lyf.okmvp.model.login;

import android.util.ArrayMap;

import com.lyf.okmvp.http.HttpManager;

import framework.bean.BaseBean;
import framework.okhttp.consts.HttpConst;
import framework.okhttp.interfaces.Callback;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @Author Lyf
 * @CreateTime 2018/2/5
 * @Description
 **/
public class PostLoginRequest {

    public static void PostLogin(final ArrayMap<String, Object> params, final Callback<BaseBean> responseCallback) {

        HttpManager.getInstance().doPost(HttpConst.POST_LOGIN_REQUEST, params,
                new Callback<BaseBean>() {
                    @Override
                    public void onFailure(Call call, Exception e) {
                        // 请求失败回调
                    }

                    @Override
                    public void onResponse(Call call, Response response, BaseBean bean) {
                        // 请求成功回调.
                        // 获取Bean类数据
                        String msg = bean.getMsg();
                    }
                });

    }
}
