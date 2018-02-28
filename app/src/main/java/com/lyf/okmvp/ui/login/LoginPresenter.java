package com.lyf.okmvp.ui.login;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.ArrayMap;

import com.lyf.login.AbstractLoginPresenter;

/**
 * @Author Lyf
 * @CreateTime 2018/2/28
 * @Description
 **/
public class LoginPresenter extends AbstractLoginPresenter {

    public LoginPresenter(Application mApplication) {
        super(mApplication);
    }

    @Override
    public void onLoginWithPassWord(@NonNull ArrayMap<String, String> mLoginParams) {

    }

    @Override
    public void onLoginWithPlatforms(@NonNull ArrayMap<String, String> mLoginParams) {

    }

    @Override
    public void onLoginWithVerifyCode(@NonNull ArrayMap<String, String> mLoginParams) {

    }
}
