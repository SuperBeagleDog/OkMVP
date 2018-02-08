package framework.okhttp;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @Author Lyf
 * @CreateTime 2018/1/22
 * @Description A factory of OkHttp, which used to produce Instance of OkHttpClient
 **/
class OkFactory {

    private final static int CONNECT_TIMEOUT = 30 * 1000;
    private final static int READ_TIMEOUT =  30 * 1000;
    private final static int WRITE_TIMEOUT =  30 * 1000;

    // There is only an instance of OkHttpClient in this project.
    private static OkHttpClient OK_INSTANCE;

    static OkHttpClient getOkHttpClient() {

        try {
            if (OK_INSTANCE == null) {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                        .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
                OK_INSTANCE = builder.build();
            }

            return OK_INSTANCE;
        } catch (Exception e) {
            return null;
        }

    }


}
