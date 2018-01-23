package com.lyf.okhttputils.okhttp.header;

import com.lyf.okhttputils.okhttp.consts.HeaderConst;

import okhttp3.Headers;

/**
 * @Author Lyf
 * @CreateTime 2018/1/22
 * @Description A manager for getting Headers.
 **/
public class HeaderManager {

    /**
     * @return returns an instance of Headers.
     */
    public static Headers getHeaders() {

        Headers.Builder headers = new Headers.Builder();
        headers.set(HeaderConst.TOKEN, "");
        return headers.build();
    }

}
