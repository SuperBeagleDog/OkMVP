package framework.okhttp.http;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.ArrayMap;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

import framework.okhttp.Result;
import framework.okhttp.consts.HttpCodes;
import framework.okhttp.utils.OkUtil;
import framework.okhttp.header.HeaderManager;
import framework.okhttp.header.IHeaderManager;
import framework.okhttp.interfaces.Callback;
import framework.utils.GsonTools;
import framework.utils.LogUtil;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @Author Lyf
 * @CreateTime 2018/2/8
 * @Description A HttpManager is a final class which used to do real request.
 * All of functions the HttpManager provides, defined in {@link IHeaderManager}
 **/
public final class HttpManager implements IHttpManager {

    private final static String TAG = HttpManager.class.getSimpleName();

    // There is only an instance of OkHttpClient in this project.
    private OkHttpClient mOkHttpClient;

    // Manager Headers.
    private IHeaderManager mHeaderManager = HeaderManager.getHeaderManager();

    // INSTANCE is the object of HttpManager's protocol, not the object of HttpManager itself.
    private static IHttpManager INSTANCE;

    public static IHttpManager getHttpManager() {

        if (INSTANCE == null) {
            INSTANCE = new HttpManager();
        }

        return INSTANCE;
    }

    private HttpManager() {
        initHttpManager();
    }

    @Override
    public void setResponseCode(int successCode, int failureCode) {
        HttpCodes.CODE_SUCCESS = successCode;
        HttpCodes.CODE_FAILURE = failureCode;
    }

    @Override
    public void initHttpManager() {

        if (mOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
            mOkHttpClient = builder.build();
        }

    }

    @Override
    public <T> void doGet(@NonNull String url, @Nullable ArrayMap<String, Object> params, final @Nullable Callback<T> responseCallback) {

        // Create a basic request
        final Request request = new Request
                .Builder()
                .tag(url)
                .headers(OkUtil.getOkHttpHeaders(mHeaderManager.getHeaders()))
                .url(composeParams(url, params))
                .build();

        // Doing request in a async way.
        mOkHttpClient.newCall(request).
                enqueue(new okhttp3.Callback() {

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) {

                        Result<T> result = new Result<>();

                        try {

                            T bean = null;

                            if (responseCallback != null) {

                                // Save code and msg.
                                result.setCode(response.code());
                                result.setMsg(response.message());

                                if (response.code() == HttpCodes.CODE_SUCCESS) {

                                    if (response.body() != null) {
                                        // Get the raw json from server.
                                        String json = response.body().string();
                                        // Parse raw json into bean
                                        bean = GsonTools.fromJson(json, (((ParameterizedType)
                                                (responseCallback.getClass().getGenericInterfaces())[0])
                                                .getActualTypeArguments()[0]));
                                        // Save data.
                                        result.setData(bean);
                                    }

                                    // Return the data.
                                    responseCallback.onResponse(result, bean);

                                } else {

                                    // Return the data.
                                    responseCallback.onFailure(result);
                                }

                            }

                        } catch (Exception e) {

                            result.setMsg("请求网络失败");
                            result.setCode(HttpCodes.CODE_FAILURE);
                            responseCallback.onFailure(result);
                            LogUtil.log(TAG, "Parsing json failed = " + e.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                        if (responseCallback != null) {

                            Result<T> result = new Result<>();
                            result.setMsg("请求网络失败");
                            result.setCode(HttpCodes.CODE_FAILURE);
                            responseCallback.onFailure(result);
                        }
                    }

                });
    }

    @Override
    public <T> void doPost(@NonNull String url, @Nullable ArrayMap<String, Object> params, @Nullable Callback<T> responseCallback) {

    }

    @Override
    public String composeParams(@NonNull String url, ArrayMap<String, Object> paramMap) {

        // URL is null, throw a RuntimeException.
        if (url == null) {
            throw new RuntimeException("url can't be null");
        }

        // If the paramsMap is null or empty, return the url simply.
        if (paramMap == null || paramMap.size() == 0) {
            return url;
        }

        StringBuilder paramsUrl = new StringBuilder();

        if (!url.contains("?")) {
            paramsUrl.append("?");
        }

        for (String key : paramMap.keySet()) {

            if (paramMap.get(key) == null) {
                continue; // the value is null, leave it.
            }
            paramsUrl.append("&" + paramMap.get(key));
        }

        return url + paramsUrl.toString();
    }

    @Override
    public void cancelRequestWithTag(Object tag) {

        if (mOkHttpClient != null && tag != null) {

            for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
                if (call.request().tag().equals(tag))
                    call.cancel();
            }

            for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
                if (call.request().tag().equals(tag))
                    call.cancel();
            }
        }

    }

}
