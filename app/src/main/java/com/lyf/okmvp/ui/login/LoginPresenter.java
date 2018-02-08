package com.lyf.okmvp.ui.login;

import android.app.Application;
import android.util.ArrayMap;

import com.lyf.okmvp.model.login.PostLoginRequest;

import framework.bean.BaseBean;
import framework.login.ILogin;
import framework.login.presenters.BaseLoginPresenter;
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

        PostLoginRequest.PostLogin(loginParams, new Callback<BaseBean>() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void onResponse(Call call, Response response, BaseBean bean) {

            }
        });

    }


}