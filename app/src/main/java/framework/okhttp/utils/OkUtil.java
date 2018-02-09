package framework.okhttp.utils;

import android.support.v4.util.ArrayMap;

import okhttp3.Headers;


/**
 * @Author Lyf
 * @CreateTime 2018/1/22
 * @Description OkUtil is a class for parsing or transferring some basic things into
 * those classes which can be used in OkHttp.
 **/
public class OkUtil{

    // Cache the Headers to avoid creating Headers for each request.
    private static Headers mOkHttpHeaders;

    /**
     * Transfers the original headers(Map<String, String> type) to OkHttp's headers.
     *
     * @param originalHeaders original headers
     * @return Headers.
     */
    public static Headers getOkHttpHeaders(ArrayMap<String, String> originalHeaders) {

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
