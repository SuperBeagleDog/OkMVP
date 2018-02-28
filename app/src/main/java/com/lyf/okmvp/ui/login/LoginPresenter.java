package com.lyf.okmvp.ui.login;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import com.lyf.login.AbstractLoginPresenter;
import com.lyf.login.LoginContact;
import com.lyf.okmvp.model.login.PostLoginRequest;

import framework.bean.BaseBean;
import framework.net.response.Callback;
import framework.net.response.Response;

/**
 * @Author Lyf
 * @CreateTime 2018/2/28
 * @Description
 **/
public class LoginPresenter extends AbstractLoginPresenter {


    public LoginPresenter(Application mApplication, LoginContact.View mLoginView) {
        super(mApplication, mLoginView);
    }

    @Override
    public void onLoginWithPassWord(@NonNull ArrayMap<String, Object> mLoginParams) {

        PostLoginRequest.PostLogin(mLoginParams, new Callback<BaseBean>() {
            @Override
            public void onFailure(int errorCode, String errorMsg, Response<BaseBean> response) {

            }

            @Override
            public void onResponse(BaseBean bean, Response<BaseBean> response) {

            }
        });

    }

    @Override
    public void onLoginWithPlatforms(@NonNull ArrayMap<String, Object> mLoginParams) {

    }

    @Override
    public void onLoginWithVerifyCode(@NonNull ArrayMap<String, Object> mLoginParams) {

    }
}
