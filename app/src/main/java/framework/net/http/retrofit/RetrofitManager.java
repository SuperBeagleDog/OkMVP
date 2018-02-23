package framework.net.http.retrofit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

import framework.net.response.Callback;

/**
 * @Author Lyf
 * @CreateTime 2018/2/23
 * @Description
 **/
public class RetrofitManager implements IRetrofitManager {



    @Override
    public <T> void doGet(@NonNull String url, @Nullable ArrayMap<String, Object> params, @Nullable Callback<T> responseCallback) {

    }

    @Override
    public <T> void doPost(@NonNull String url, @Nullable ArrayMap<String, Object> params, @Nullable Callback<T> responseCallback) {

    }

}
