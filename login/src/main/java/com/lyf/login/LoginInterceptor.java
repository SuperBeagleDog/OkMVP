package com.lyf.login;

import android.app.Application;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lyf.login.util.PhoneUtil;


/**
 * @Author Lyf
 * @CreateTime 2018/2/1
 * @Description A LoginInterceptor class is a checking params interceptor.
 *  return true if the params are legal, return false if the params are illegal.
 **/
public class LoginInterceptor {

    private Application application;
    private final static String PHONE_NUM_NULL = "请输入手机号码";
    private final static String PHONE_NUM_ERROR = "手机号码错误";
    private final static String PASS_WORD_NULL = "请输入密码";
    private final static String VERIFY_CODES_NULL = "请输入验证码";


    public boolean checkLoginWithPassWord(@NonNull Application application, @NonNull String phoneNum, @NonNull String passWord) {
        this.application = application;
        return checkPhoneNum(phoneNum) && checkPhonePassWord(passWord);
    }

    public boolean checkLoginWithVerifyCode(@NonNull Application application, @NonNull String phoneNum, @NonNull String verificationCode) {
        this.application = application;
        return checkPhoneNum(phoneNum) && checkVerifyCodes(verificationCode);
    }

    /**
     * Checking passWord whether is legal.
     *
     * @return true legal，false illegal
     */
    public boolean checkPhoneNum(@NonNull String phoneNum) {

        if (phoneNum == null || phoneNum.length() == 0) {
            showToast(PHONE_NUM_NULL);
            return false;
        } else if (!PhoneUtil.isPhoneNum(phoneNum)) {
            showToast(PHONE_NUM_ERROR);
            return false;
        }
        return true;
    }

    /**
     * Checking passWord whether is legal.
     *
     * @return true legal，false illegal
     */
    public boolean checkPhonePassWord(@NonNull String passWord) {

        if (passWord == null || passWord.length() == 0) {
            showToast(PASS_WORD_NULL);
            return false;
        }

        return true;
    }

    /**
     * Checking verificationCode whether is legal.
     *
     * @return true legal，false illegal
     */
    public boolean checkVerifyCodes(@NonNull String verificationCode) {

        if (verificationCode == null || verificationCode.length() == 0) {
            showToast(VERIFY_CODES_NULL);
            return false;
        }

        return false;
    }

    private void showToast(String text) {

        if (application != null) {
            Toast.makeText(application, text, Toast.LENGTH_SHORT).show();
        }
    }


}
