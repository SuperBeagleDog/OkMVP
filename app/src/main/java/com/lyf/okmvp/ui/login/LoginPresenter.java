package com.lyf.okmvp.ui.login;

import android.app.Application;

import com.lyf.okmvp.model.login.PostLoginRequest;
import android.support.v4.util.ArrayMap;
import framework.bean.BaseBean;
import framework.login.ILogin;
import framework.login.presenters.BaseLoginPresenter;
import framework.net.response.Callback;
import framework.net.response.Response;


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

        PostLoginRequest.PostLogin(loginParams, new Callback<BaseBean>() {

            @Override
            public void onFailure(int errorCode, String errorMsg, Response<BaseBean> response) {

            }

            @Override
            public void onResponse(BaseBean bean, Response<BaseBean> response) {

            }
        });

    }



}