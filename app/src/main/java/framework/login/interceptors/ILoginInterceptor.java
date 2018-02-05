package framework.login.interceptors;

import android.app.Application;
import android.support.annotation.NonNull;

/**
 * @Author Lyf
 * @CreateTime 2018/2/5
 * @Description
 **/
public interface ILoginInterceptor {

    boolean checkLoginWithPassWord(@NonNull Application application, @NonNull String phoneNum, @NonNull String passWord);

    boolean checkLoginWithVerifyCode(@NonNull Application application,@NonNull String phoneNum, @NonNull String verificationCode);
}
