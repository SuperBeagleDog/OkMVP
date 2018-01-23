package com.lyf.okhttputils.okhttp.interfaces;

import okhttp3.OkHttpClient;

/**
 * @Author Lyf
 * @CreateTime 2018/1/22
 * @Description OkUtils的方法定义
 **/
public interface IOkUtils {

    /**
     * @return OkHttpClient 实例
     */
    OkHttpClient getOkHttpClient();

    /**
     * 取消某一个正在请求中或是等待请求的连接
     * @param tag 连接所标识的字符串,一般用当前类
     */
    void cancelCallWithTag(Object tag);

}
