package framework.okhttp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.ArrayMap;

import framework.okhttp.interfaces.Callback;
import framework.okhttp.interfaces.IRequestMethods;

/**
 * @Author Lyf
 * @CreateTime 2018/1/23
 * @Description HttpManager for doing request, The real request is located in OkUtils class.
 **/
public abstract class BaseHttpManager implements IRequestMethods {

    /**
     * Add algorithms of sign.
     *
     * @param params the original requesting params.
     * @return The params with sign or the original params if you don't need to sign the params.
     */
    protected abstract ArrayMap<String, Object> getSignParams(ArrayMap<String, Object> params);

    @Override
    public <T> void doGet(@NonNull String url, @Nullable ArrayMap<String, Object> params, @Nullable Callback<T> responseCallback) {
        OkUtils.getInstance().doGet(url, getSignParams(params), responseCallback);
    }

    @Override
    public <T> void doPost(@NonNull String url, @Nullable ArrayMap<String, Object> params, @Nullable Callback<T> responseCallback) {
        OkUtils.getInstance().doPost(url, getSignParams(params), responseCallback);
    }

    // These methods below do request without params and listen.

    @Override
    public void doGet(@NonNull String url) {
        doGet(url, null, null);
    }

    @Override
    public void doPost(Object tag, String url) {
        doPost(url, null, null);
    }

    @Override
    public void doPut(Object tag, String url) {

    }

    @Override
    public void doDelete(Object tag, String url) {

    }

    // These methods below do request without params but with a listen.
    @Override
    public <T> void doGet(@NonNull String url, @Nullable Callback<T> responseCallback) {
        doGet(url, null, responseCallback);
    }

    @Override
    public <T> void doPost(@NonNull String url, @Nullable Callback<T> responseCallback) {
    }

}
