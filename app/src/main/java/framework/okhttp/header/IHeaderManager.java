package framework.okhttp.header;


import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

/**
 * @Author Lyf
 * @CreateTime 2018/2/9
 * @Description
 **/
public interface IHeaderManager {

    ArrayMap<String, String> getHeaders();

    boolean addHeader(String headerName, String headerValue);

    boolean addHeaders(ArrayMap<String, String> params);

    boolean removeHeader(@Nullable String headerName);

    void clearHeaders();

    String getHeader(@Nullable String headerName);

}
