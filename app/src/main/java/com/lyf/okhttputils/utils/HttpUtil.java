package com.lyf.okhttputils.utils;

import android.support.annotation.NonNull;
import android.util.ArrayMap;

/**
 * @Author Lyf
 * @CreateTime 2018/1/23
 * @Description
 **/
public class HttpUtil {


    /**
     * 组合Http Get请求URL
     * @param url 请求的url
     * @param paramMap 请求参数
     * @return 返回拼装完参数的url
     */
    public static String composeParams(@NonNull String url, ArrayMap<String, Object> paramMap) {

        // URL为空，抛出异常。
        if(url == null){
            throw new RuntimeException("url不能为null");
        }

        // 参数为空，直接返回url。
        if (paramMap == null || paramMap.size() == 0) {
             return url;
        }

        StringBuilder parmasUrl = new StringBuilder();

        if(!url.contains("?")){
            parmasUrl.append("?");
        }

        if (paramMap != null) {

            for (String key : paramMap.keySet()) {

                if(paramMap.get(key) == null) {
                    continue; // 如果值为null，跳过
                }
                parmasUrl.append("&" + paramMap.get(key));
            }

        }

        return url + parmasUrl.toString();
    }

}
