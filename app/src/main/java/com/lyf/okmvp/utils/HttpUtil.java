package com.lyf.okmvp.utils;

import android.support.annotation.NonNull;
import android.util.ArrayMap;

/**
 * @Author Lyf
 * @CreateTime 2018/1/23
 * @Description
 **/
public class HttpUtil {


    /**
     * Compose the params to url
     *
     * @param url      request url
     * @param paramMap request params
     * @return A url with params.
     */
    public static String composeParams(@NonNull String url, ArrayMap<String, Object> paramMap) {

        // URL is null, throw a RuntimeException.
        if (url == null) {
            throw new RuntimeException("url can't be null");
        }

        // If the paramsMap is null or empty, return the url simply.
        if (paramMap == null || paramMap.size() == 0) {
            return url;
        }

        StringBuilder parmasUrl = new StringBuilder();

        if (!url.contains("?")) {
            parmasUrl.append("?");
        }

        for (String key : paramMap.keySet()) {

            if (paramMap.get(key) == null) {
                continue; // the value is null, leave it.
            }
            parmasUrl.append("&" + paramMap.get(key));
        }

        return url + parmasUrl.toString();
    }

}
