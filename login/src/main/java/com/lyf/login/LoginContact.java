package com.lyf.login;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


/**
 * @Author Lyf
 * @CreateTime 2018/2/27
 * @Description
 **/
public class LoginContact {

    public interface View extends BaseView<LoginContact.Presenter> {

        void showLoading();

        void hideLoading();
    }

    public interface Presenter extends BasePresenter {


        /**
         * login with phoneNum and passWord.
         *
         * @param phoneNumKey   request name
         * @param phoneNumValue request value
         * @param passWordKey   request name
         * @param passWordValue request value
         */
        void onLoginWithPassWord(@NonNull String phoneNumKey, @NonNull String phoneNumValue,
                                 @NonNull String passWordKey, @NonNull String passWordValue);


        /**
         * login with phoneNum and verificationCode.
         */
        void onLoginWithVerifyCode(@NonNull String phoneNum, @NonNull String verificationCode);

        /**
         * login with loginInfo of other platforms.
         *
         * @param loginInfo loginInfo of other platforms.
         * @param <T>       A bean, saving loginInfo of other platforms.
         * @param isSuccess true if login success.
         */
        <T> void onLoginWithPlatforms(@Nullable T loginInfo, boolean isSuccess);


        /**
         * login with unKnow params for compatibility.
         * Note that : This method will not check the params whether is valid.
         * So that you have to check the params whether is valid by yourself.
         *
         * @param params unKnow params. These params will do nothing for checking.
         */
        void onLoginWithUnKnowParams(String... params);
    }

}
