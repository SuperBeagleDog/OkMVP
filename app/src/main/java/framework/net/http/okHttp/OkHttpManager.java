package framework.net.http.okHttp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

import framework.net.consts.HttpCodes;
import framework.net.header.HeaderManager;

import framework.net.header.IHeaderManager;
import framework.net.http.HttpBuilder;
import framework.net.response.Callback;
import framework.net.response.Response;
import framework.net.util.Util;
import framework.utils.GsonTools;
import framework.utils.LogUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @Author Lyf
 * @CreateTime 2018/2/8
 * @Description
 **/
public final class OkHttpManager implements IOkHttpManager {

    private final static String TAG = OkHttpManager.class.getSimpleName();

    // Cache the Headers to avoid creating Headers for each request.
    private static Headers mOkHttpHeaders;

    // It's better to use a single instance of OkHttpClient in a certain project.
    private final OkHttpClient mOkHttpClient = new OkHttpClient();

    // Manager Headers.
    private IHeaderManager mHeaderManager = HeaderManager.getHeaderManager();

    // Sets some settings of http to OkHttpClient with a HttpBuilder.
    public OkHttpManager(HttpBuilder httpBuilder) {

        OkHttpClient.Builder okHttpBuilder = mOkHttpClient.newBuilder();
        okHttpBuilder.readTimeout(httpBuilder.getReadTimeOut(), TimeUnit.MILLISECONDS)
                .writeTimeout(httpBuilder.getWriteTimeOut(), TimeUnit.MILLISECONDS)
                .connectTimeout(httpBuilder.getConnectTimeOut(), TimeUnit.MILLISECONDS);
        okHttpBuilder.build();

    }

    @Override
    public <T> void doGet(@NonNull String url, @Nullable ArrayMap<String, Object> params, final @Nullable Callback<T> responseCallback) {

        // Creates a basic request
        final Request request = new Request
                .Builder()
                .tag(url)
                .headers(getOkHttpHeaders(mHeaderManager.getHeaders()))
                .url(Util.composeParams(url, params))
                .build();


        // Uses RxJava2 to post a request.
        Observable.create(new ObservableOnSubscribe<Response<T>>() {
            @Override
            public void subscribe(final ObservableEmitter<Response<T>> emitter) throws Exception {

                mOkHttpClient.newCall(request).
                        enqueue(new okhttp3.Callback() {

                            @Override
                            public void onResponse(Call call, okhttp3.Response response) {

                                Response<T> result = new Response<>();

                                try {

                                    if (responseCallback != null) {

                                        if (response.body() != null) {
                                            // Get the raw json from server.
                                            String json = response.body().string();
                                            // Parse raw json into bean
                                            T bean = GsonTools.fromJson(json, (((ParameterizedType)
                                                    (responseCallback.getClass().getGenericInterfaces())[0])
                                                    .getActualTypeArguments()[0]));
                                            // Save data.
                                            result.setData(bean);
                                        }
                                        // Return the data.
                                        emitter.onNext(result);
                                    }

                                } catch (Exception e) {
                                    emitter.onNext(new Response<>());
                                    LogUtil.log(TAG, "Parsing json failed = " + e.toString());
                                }
                            }

                            @Override
                            public void onFailure(Call call, IOException e) {
                                if (responseCallback != null) {
                                    emitter.onNext(new Response<>());
                                }
                            }

                        });


            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<T>>() {
                    @Override
                    public void accept(Response<T> response) throws Exception {
                        if (responseCallback != null) {
                            if (response.getCode() == HttpCodes.CODE_SUCCESS) {
                                responseCallback.onResponse(response.getData(), response);
                            } else {
                                responseCallback.onFailure(response.getCode(), response.getMsg(), response);
                            }
                        }
                    }
                });

    }

    @Override
    public <T> void doPost(@NonNull String url, @Nullable ArrayMap<String, Object> params, @Nullable Callback<T> responseCallback) {

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                        GsonTools.toJson(params));

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        // Uses RxJava2 to post a request.
        Observable.create(new ObservableOnSubscribe<Response<T>>() {
            @Override
            public void subscribe(final ObservableEmitter<Response<T>> emitter) throws Exception {

                mOkHttpClient.newCall(request).
                        enqueue(new okhttp3.Callback() {

                            @Override
                            public void onResponse(Call call, okhttp3.Response response) {

                                Response<T> result = new Response<>();

                                try {

                                    if (responseCallback != null) {

                                        if (response.body() != null) {
                                            // Get the raw json from server.
                                            String json = response.body().string();
                                            // Parse raw json into bean
                                            T bean = GsonTools.fromJson(json, (((ParameterizedType)
                                                    (responseCallback.getClass().getGenericInterfaces())[0])
                                                    .getActualTypeArguments()[0]));
                                            // Save data.
                                            result.setData(bean);
                                        }
                                        // Return the data.
                                        emitter.onNext(result);
                                    }

                                } catch (Exception e) {
                                    emitter.onNext(new Response<>());
                                    LogUtil.log(TAG, "Parsing json failed = " + e.toString());
                                }
                            }

                            @Override
                            public void onFailure(Call call, IOException e) {
                                if (responseCallback != null) {
                                    emitter.onNext(new Response<>());
                                }
                            }

                        });


            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<T>>() {
                    @Override
                    public void accept(Response<T> response) throws Exception {
                        if (responseCallback != null) {
                            if (response.getCode() == HttpCodes.CODE_SUCCESS) {
                                responseCallback.onResponse(response.getData(), response);
                            } else {
                                responseCallback.onFailure(response.getCode(), response.getMsg(), response);
                            }
                        }
                    }
                });

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

    @Override
    public Headers getOkHttpHeaders(ArrayMap<String, String> originalHeaders) {

        // Avoid to create the Headers for each requesting.
        if (mOkHttpHeaders != null) {
            return mOkHttpHeaders;
        }

        Headers.Builder headers = new Headers.Builder();

        for (String headerName : originalHeaders.keySet()) {
            headers.set(headerName, originalHeaders.get(headerName));
        }

        mOkHttpHeaders = headers.build();

        return mOkHttpHeaders;
    }

}
