package framework.login;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import framework.login.interceptors.ILoginInterceptor;

/**
 * @Author Lyf
 * @CreateTime 2018/2/1
 * @Description An Interface for defining actions what the Login can do.
 **/
public interface ILogin {

    /**
     * @param interceptor An interceptor uses to filter params if you need.
     * @return return true if added interceptor success.
     */
    boolean addInterceptor(ILoginInterceptor interceptor);

    /**
     * Clears all Interceptors if you don't want to check param whether is legal.
     */
    void clearAllInterceptors();

    /**
     * login with phoneNum and passWord.
     * @param phoneNumKey request name
     * @param phoneNumValue request value
     * @param passWordKey request name
     * @param passWordValue request value
     */
    void onLoginWithPassWord(@NonNull String phoneNumKey,@NonNull String phoneNumValue,
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

}
