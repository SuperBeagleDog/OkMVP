package com.lyf.okmvp.model.login;

import android.util.ArrayMap;

import com.lyf.okmvp.http.HttpUtils;

import framework.bean.BaseBean;
import framework.okhttp.Result;
import framework.okhttp.consts.HttpUrlConst;
import framework.okhttp.interfaces.Callback;

/**
 * @Author Lyf
 * @CreateTime 2018/2/5
 * @Description
 **/
public class PostLoginRequest {

    public static void PostLogin(final ArrayMap<String, Object> params, final Callback<BaseBean<Integer>> responseCallback) {

        HttpUtils.getInstance().doPost(HttpUrlConst.POST_LOGIN_REQUEST, params, new Callback<BaseBean<Integer>>() {
            @Override
            public void onFailure(Result<BaseBean<Integer>> response) {
                // 请求失败回调
                responseCallback.onFailure(response);
            }

            @Override
            public void onResponse(Result<BaseBean<Integer>> response, BaseBean<Integer> bean) {
                // 请求成功回调.
                // 获取Bean类数据

                int code = response.getCode();
                String msg = response.getMsg();

                // 从response的getData()里拿bean,再从bean的getData()里拿bean
                Integer data = response.getData().getData();
                // 从bean的getData()里拿bean
                data = bean.getData();
                responseCallback.onResponse(response, bean);
            }
        });

    }


}
