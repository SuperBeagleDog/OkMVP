package framework.okhttp.http;

import android.support.annotation.NonNull;
import android.util.ArrayMap;

import framework.okhttp.interfaces.IBaseRequestMethods;

/**
 * @Author Lyf
 * @CreateTime 2018/2/8
 * @Description
 **/
public interface IHttpManager extends IBaseRequestMethods {

    int CONNECT_TIMEOUT = 30 * 1000;
    int READ_TIMEOUT = 30 * 1000;
    int WRITE_TIMEOUT = 30 * 1000;

    /**
     *
     */
    void initHttpManager();

    /**
     *
     */
    void setResponseCode(int successCode, int failureCode);

    /**
     * Compose the params to url
     *
     * @param url      request url
     * @param paramMap request params
     * @return A url with params.
     */
    String composeParams(@NonNull String url, ArrayMap<String, Object> paramMap);

    /**
     * 取消某一个正在请求中或是等待请求的连接
     * @param tag 连接所标识的字符串,一般用当前类
     */
    void cancelRequestWithTag(Object tag);

}
