package framework.net.header;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.SimpleArrayMap;

import framework.utils.LogUtil;

/**
 * @Author Lyf
 * @CreateTime 2018/1/22
 * @Description A HeaderManager class is simply for getting and adding Headers to each request on Http.
 **/
public class HeaderManager implements IHeaderManager {

    private final static String TAG = HeaderManager.class.getSimpleName();

    /**
     * Turns the null param's value into empty string if need.
     */
    private boolean mTurnsNullValueToEmpty = true;

    /**
     * Saving All header params.
     */
    private ArrayMap<String, String> mHeaders = new ArrayMap<>();

    private static HeaderManager INSTANCE = null;
    private HeaderManager() {
    }

    public static HeaderManager getHeaderManager() {

        if (INSTANCE == null) {
            INSTANCE = new HeaderManager();
        }
        return INSTANCE;
    }

    @Override
    public ArrayMap<String, String> getHeaders() {
        return mHeaders;
    }

    @Override
    public boolean addHeader(@NonNull String headerName, String headerValue) {

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


        mHeaders.put(headerName, headerValue);

        return true;
    }

    @Override
    public boolean addHeaders(ArrayMap<String, String> params) {

        try {
            mHeaders.putAll((SimpleArrayMap<? extends String, ? extends String>) params);
        } catch (Exception e) {
            LogUtil.log("Add Headers failed = " + e.toString());
            return false;
        }
        return true;
    }

    @Override
    public boolean removeHeader(@Nullable String headerName) {
        mHeaders.remove(headerName);
        return true;
    }

    @Override
    public void clearHeaders() {
        mHeaders.clear();
    }

    @Override
    public String getHeader(String headerName) {

        if (headerName == null) {
            return null;
        }

        return mHeaders.get(headerName);
    }

}