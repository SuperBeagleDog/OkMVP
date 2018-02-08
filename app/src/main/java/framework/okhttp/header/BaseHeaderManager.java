package framework.okhttp.header;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import framework.okhttp.consts.HeaderConst;

import okhttp3.Headers;

/**
 * @Author Lyf
 * @CreateTime 2018/1/22
 * @Description BaseHeaderManager class is simply for getting and adding Headers to each request on OkHttp.
 **/
public class BaseHeaderManager {

    /**
     * This filed used to indicate the mOkHttpHeaders field Whether is need to update.
     */
    private boolean mNewHeaderAdded = false;
    /**
     * Cache the Headers to avoid creating Headers for each request.
     */
    private Headers mOkHttpHeaders;

    /**
     * Turns the null param's value into empty string if need.
     */
    private boolean mTurnsNullValueToEmpty = true;

    /**
     * The extra headers if the project need to add.
     */
    private ArrayMap<String, String> mExtraHeaders = new ArrayMap<>();

    private static BaseHeaderManager INSTANCE = null;

    private BaseHeaderManager() {
    }

    public static BaseHeaderManager getHeaderManager() {

        if (INSTANCE == null) {
            INSTANCE = new BaseHeaderManager();
        }
        return INSTANCE;
    }



    /**
     * @return returns an instance of Headers.
     */
    public Headers getHeaders() {

        // avoid to create the Headers for each requesting.
        if (!mNewHeaderAdded && mOkHttpHeaders != null) {
            return mOkHttpHeaders;
        }

        Headers.Builder headers = new Headers.Builder();
        headers.set(HeaderConst.TOKEN, "");

        for (String headerName : mExtraHeaders.keySet()) {
            headers.set(headerName, mExtraHeaders.get(headerName));
        }

        mOkHttpHeaders = headers.build();

        return mOkHttpHeaders;
    }

    public void addHeader(@NonNull String headerName, String headerValue) {

        // avoid to add a null as header's value.
        if (headerName == null) {
            throw new NullPointerException("name == null");
        }
        if (headerName.isEmpty()) {
            throw new IllegalArgumentException("name is empty");
        }

        // turns the null value into empty string.
        if (mTurnsNullValueToEmpty && headerValue == null) {
            headerValue = "";
        }

        // New header's param is added So that the mOkHttpHeaders field should be updated when the next request invoked.
        mNewHeaderAdded = true;

        mExtraHeaders.put(headerName, headerValue);
    }

    public ArrayMap<String, String> getExtraHeaders() {
        return mExtraHeaders;
    }

    public boolean isTurnsNullValueToEmpty() {
        return mTurnsNullValueToEmpty;
    }

    public void setTurnsNullValueToEmpty(boolean mTurnsNullValueToEmpty) {
        this.mTurnsNullValueToEmpty = mTurnsNullValueToEmpty;
    }

    public ArrayMap<String, String> getmExtraHeaders() {
        return mExtraHeaders;
    }

    public void setExtraHeaders(ArrayMap<String, String> mExtraHeaders) {
        this.mExtraHeaders = mExtraHeaders;
    }

}