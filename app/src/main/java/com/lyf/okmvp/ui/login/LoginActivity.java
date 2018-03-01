package com.lyf.okmvp.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lyf.login.LoginContact;
import com.lyf.okmvp.ui.BaseActivity;


/**
 * @Author Lyf
 * @CreateTime 2018/2/5
 * @Description A login Activity implements LoginContact.View
 **/
public class LoginActivity extends BaseActivity implements LoginContact.View {

    private LoginContact.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new LoginPresenter(getApplication(),this);
        onLoginClick();
    } // fakes a login action.
    private void onLoginClick() {

        // pass params to presenter which will check the params, and do login request if the params is valid.
        mPresenter.onLoginWithPassWord("phone", "132123456789",
                "password", "*****");

    }

    @Override
    public void showLoading() {
        // Shows a login dialog.
    }

    @Override
    public void hideLoading() {
        // Shows a login dialog.
    }

}
