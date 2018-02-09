package com.lyf.okmvp.ui.login;

import android.app.Application;
import android.util.ArrayMap;

import com.lyf.okmvp.model.login.PostLoginRequest;

import framework.bean.BaseBean;
import framework.login.ILogin;
import framework.login.presenters.BaseLoginPresenter;
import framework.okhttp.Result;
import framework.okhttp.interfaces.Callback;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @Author Lyf
 * @CreateTime 2018/2/5
 * @Description
 **/
public class LoginPresenter extends BaseLoginPresenter implements ILogin {


    public LoginPresenter(Application mContext) {
        super(mContext);
    }

    @Override
    protected void onLogin(ArrayMap<String, Object> loginParams) {

        PostLoginRequest.PostLogin(loginParams, new Callback<BaseBean<Integer>>() {
            @Override
            public void onFailure(Result<BaseBean<Integer>> response) {
                // 请求失败回调
            }

            @Override
            public void onResponse(Result<BaseBean<Integer>> response, BaseBean<Integer> bean) {
                // 请求成功回调.

                // 获取code和msg
                int code = response.getCode();
                String msg = response.getMsg();

                // 从response的getData()里拿bean,再从bean的getData()里拿bean
                Integer data = response.getData().getData();

                // 直接从bean的getData()里拿bean
                data = bean.getData();

            }
        });

    }


}