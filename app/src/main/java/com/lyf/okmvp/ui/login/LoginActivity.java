package com.lyf.okmvp.ui.login;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.lyf.okmvp.ui.BaseActivity;

import framework.login.ILogin;
import framework.login.interceptors.ILoginInterceptor;
import framework.utils.ToastHelper;


/**
 * @Author Lyf
 * @CreateTime 2018/2/5
 * @Description 登录页
 **/
public class LoginActivity extends BaseActivity {

    private ILogin mLoginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // mLoginPresenter is an Object of ILogin Interface.
        mLoginPresenter = new LoginPresenter(getApplication());
        onLoginClick();
    }

    // fakes a login action.
    private void onLoginClick() {

        // pass params to presenter which will check the params, and do login request if the params is valid.
        mLoginPresenter.onLoginWithPassWord("phone", "132123456789",
                "password", "*****");

        // Clears the common checking if you need.
        // mLoginPresenter.clearAllInterceptors();

        // add interceptor to filter the params before it is passed to server if you need.
//        mLoginPresenter.addInterceptor(new ILoginInterceptor() {
//            @Override
//            public boolean checkLoginWithPassWord(@NonNull Application application,
//                                                  @NonNull String phoneNum, @NonNull String passWord) {
//                if (phoneNum.length() < 6) {
//                    ToastHelper.toast(application, "手机号不足6位");
//                    return false;
//                } else {
//                    return true;
//                }
//            }
//
//            @Override
//            public boolean checkLoginWithVerifyCode(@NonNull Application application,
//                                                    @NonNull String phoneNum, @NonNull String verificationCode) {
//                return false;
//            }
//
//        });

    }

}
