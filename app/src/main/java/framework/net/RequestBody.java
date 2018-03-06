package framework.net;

import android.support.annotation.Nullable;

import framework.net.response.Callback;

/**
 * @Author Lyf
 * @CreateTime 2018/3/5
 * @Description
 **/
public class RequestBody<T> {

    private String url;
    private String method;
    private @Nullable Callback<T> responseCallback;

    public RequestBody<T> setUrl(String url) {
        this.url = url;
        return this;
    }

    public RequestBody<T> setMethod(String method) {
        this.method = method;
        return this;
    }

    public RequestBody<T> setResponseCallback(@Nullable Callback<T> responseCallback) {
        this.responseCallback = responseCallback;
        return this;
    }

}