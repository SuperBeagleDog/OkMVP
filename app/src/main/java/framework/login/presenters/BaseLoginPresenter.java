package framework.login.presenters;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.ArrayMap;
import java.util.ArrayList;
import java.util.List;
import framework.login.interceptors.ILoginInterceptor;
import framework.login.ILogin;
import framework.login.interceptors.LoginInterceptor;


/**
 * @Author Lyf
 * @CreateTime 2018/2/1
 * @Description The Presenter of Login
 **/
public abstract class BaseLoginPresenter implements ILogin {

    // Uses Application context is better.
    private Application mContext;

    // Interceptor for checking whether the params legal or not.
    private List<ILoginInterceptor> mInterceptorArray;

    // Params, post it to server for logging.
    private ArrayMap<String, Object> mLoginParams;

    public BaseLoginPresenter(Application mContext) {

        this.mContext = mContext;
        mLoginParams = new ArrayMap<>();
        mInterceptorArray = new ArrayList<>();
        // adds a common LoginInterceptor
        mInterceptorArray.add(new LoginInterceptor());
    }


    @Override
    public boolean addInterceptor(ILoginInterceptor interceptor) {

        if (interceptor == null) {
            throw new RuntimeException("the param, interceptor can't be null!");
        }

        return mInterceptorArray.add(interceptor);
    }

    @Override
    public void clearAllInterceptors() {
        mInterceptorArray.clear();
    }

    /**
     * @param loginParams Params, post it to server for logging.
     */
    protected abstract void onLogin(ArrayMap<String, Object> loginParams);


    @Override
    public void onLoginWithPassWord(@NonNull String phoneNumKey, @NonNull String phoneNumValue,
                                    @NonNull String passWordKey, @NonNull String passWordValue) {

        for (ILoginInterceptor interceptor : mInterceptorArray) {
            if (!interceptor.checkLoginWithPassWord(mContext, phoneNumValue, passWordValue)) {
                return;
            }
        }

        mLoginParams.put(phoneNumKey, phoneNumValue);
        mLoginParams.put(passWordKey, passWordValue);
        onLogin(mLoginParams);
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

}