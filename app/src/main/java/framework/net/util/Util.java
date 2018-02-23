package framework.net.util;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

/**
 * @Author Lyf
 * @CreateTime 2018/2/23
 * @Description A util for http.
 **/
public class Util {

    /**
     * As the name shows, This method can be used to compose params which you use to do request.
     * @param url An original url
     * @param paramMap A map stores a set of params.
     * @return return a url which composed url and params.
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

        StringBuilder paramsUrl = new StringBuilder();

        if (!url.contains("?")) {
            paramsUrl.append("?");
        }

        for (String key : paramMap.keySet()) {

            if (paramMap.get(key) == null) {
                continue; // the value is null, leave it.
            }
            paramsUrl.append("&").append(paramMap.get(key));
        }

        return url + paramsUrl.toString();
    }

}
