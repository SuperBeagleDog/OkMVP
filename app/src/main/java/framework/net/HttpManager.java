package framework.net;

import framework.net.http.HttpBuilder;
import framework.net.http.IHttpManager;
import framework.net.http.okHttp.OkHttpManager;
import framework.net.http.retrofit.RetrofitManager;

/**
 * @Author Lyf
 * @CreateTime 2018/2/8
 * @Description
 **/
public final class HttpManager {

    private final static String TAG = HttpManager.class.getSimpleName();

    // mHttpManager is the object of HttpManager's protocol.
    private static IHttpManager mHttpManager;

    // Init HttpManager.
    public static void initHttpManager(HttpBuilder httpBuilder) {

        mHttpManager = new OkHttpManager(httpBuilder);

        // Use Retrofit instead of OkHttp.
        // mHttpManager = new RetrofitManager(httpBuilder);
    }

    public static IHttpManager getHttpManager() {

        if (mHttpManager == null) {
            throw new RuntimeException("You have to invoke initHttpManager() to init HttpManager before you use it.");
        }

        return mHttpManager;
    }

}
