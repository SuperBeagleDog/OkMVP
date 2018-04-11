package com.lyf.okmvp.http;

import java.io.IOException;

import framework.utils.LogUtil;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author Lyf
 * @CreateTime 2018/4/11
 * @Description A Interceptor for logging request information.
 **/
public class LogInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        // You can print request information here...


        // Call the next Interceptor.
        return chain.proceed(chain.request());
    }
}
