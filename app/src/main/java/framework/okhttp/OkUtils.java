package framework.okhttp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.ArrayMap;

import framework.okhttp.header.BaseHeaderManager;
import framework.okhttp.interfaces.Callback;
import framework.okhttp.interfaces.IBaseRequestMethods;
import framework.okhttp.interfaces.IOkUtils;
import framework.utils.GsonTools;
import framework.utils.HttpUtil;
import framework.utils.LogUtil;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @Author Lyf
 * @CreateTime 2018/1/22
 * @Description A util for doing request
 **/
class OkUtils implements IBaseRequestMethods, IOkUtils {

    private final static String TAG = OkUtils.class.getSimpleName();

    private Headers mHeaders;
    private static OkUtils INSTANCE;

    private OkUtils() {
        this.mHeaders = BaseHeaderManager.getHeaderManager().getHeaders();
    }

    static OkUtils getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new OkUtils();
        }
        return INSTANCE;
    }

    @Override
    public OkHttpClient getOkHttpClient() {
        return OkFactory.getOkHttpClient();
    }

    @Override
    public <T> void doGet(@NonNull String url, @Nullable ArrayMap<String, Object> params, final @Nullable Callback<T> responseCallback) {

        // 生成request
        final Request request = new Request
                .Builder()
                .tag(url)
                .headers(mHeaders)
                .url(HttpUtil.composeParams(url, params))
                .build();

        // 异步请求网络
        getOkHttpClient()
                .newCall(request).
                enqueue(new okhttp3.Callback() {

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) {

                        try {
                            if (responseCallback != null && response.body() != null) {

                                // 获取服务器返回的Json
                                String json = response.body().string();
                                // 将json转成Bean
                                T bean = GsonTools.fromJson(json, (((ParameterizedType)
                                        (responseCallback.getClass().getGenericInterfaces())[0])
                                        .getActualTypeArguments()[0]));
                                // 返回数据
                                responseCallback.onResponse(call, response, bean);
                            }
                        } catch (Exception e) {
                            responseCallback.onFailure(call, e);
                            LogUtil.log(TAG, "Json转换失败=" + e.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        responseCallback.onFailure(call, e);
                    }

                });
    }

    @Override
    public <T> void doPost(@NonNull String url, @Nullable ArrayMap<String, Object> params, @Nullable Callback<T> responseCallback) {

    }


    /**
     * Cancel a request by using tag If this request is still requesting or waiting for posting.
     *
     * @param tag A tag for marking a request
     */
    @Override
    public void cancelCallWithTag(Object tag) {

        OkHttpClient okHttpClient = getOkHttpClient();

        if (okHttpClient != null && tag != null) {

            for (Call call : okHttpClient.dispatcher().queuedCalls()) {
                if (call.request().tag().equals(tag))
                    call.cancel();
            }

            for (Call call : okHttpClient.dispatcher().runningCalls()) {
                if (call.request().tag().equals(tag))
                    call.cancel();
            }
        }

    }

}
