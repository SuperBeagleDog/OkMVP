package com.lyf.login;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.ArrayMap;



/**
 * @Author Lyf
 * @CreateTime 2018/2/5
 * @Description A AbstractLoginPresenter deals with certain tasks.
 * For instance, initializing an interceptor to checking login params.
 **/
public abstract class AbstractLoginPresenter implements LoginContact.Presenter {

    private Application mApplication;

    private LoginContact.View mLoginView;
    private ArrayMap<String, String> mLoginParams = new ArrayMap<>();
    private LoginInterceptor mLoginInterceptor = new LoginInterceptor();

    public AbstractLoginPresenter(Application mApplication,LoginContact.View mLoginView) {
        this.mLoginView = mLoginView;
        this.mApplication = mApplication;
    }

    public abstract void onLoginWithPassWord(@NonNull ArrayMap<String, String> mLoginParams);
    public abstract void onLoginWithPlatforms(@NonNull ArrayMap<String, String> mLoginParams);
    public abstract void onLoginWithVerifyCode(@NonNull ArrayMap<String, String> mLoginParams);


    @Override
    public void onLoginWithPassWord(@NonNull String phoneNumKey, @NonNull String phoneNumValue,
                                    @NonNull String passWordKey, @NonNull String passWordValue) {

        if ( mLoginInterceptor.checkLoginWithPassWord(mApplication,
                phoneNumValue, passWordValue)) {

            mLoginParams.put(phoneNumKey, phoneNumValue);
            mLoginParams.put(passWordKey, passWordValue);
            onLoginWithPassWord(mLoginParams);
        }

    }

    @Override
    public void onLoginWithVerifyCode(@NonNull String phoneNum, @NonNull String verificationCode) {

    }

    @Override
    public <T> void onLoginWithPlatforms(@Nullable T loginInfo, boolean isSuccess) {

    }

    @Override
    public void onLoginWithUnKnowParams(String... params) {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public LoginContact.View getView() {
        return mLoginView;
    }
}