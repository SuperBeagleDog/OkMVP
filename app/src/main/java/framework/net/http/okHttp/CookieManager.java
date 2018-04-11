package framework.net.http.okHttp;

import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * @Author Lyf
 * @CreateTime 2018/2/27
 * @Description A CookieManager class used to store a request. It will be restored when the same request now is failed.
 **/
public class CookieManager implements CookieJar {

    private ArrayMap<HttpUrl,List<Cookie>> mCookieArray;

    private static CookieManager INSTANCE = null;

    private CookieManager() {
        mCookieArray = new ArrayMap<>();
    }

    static CookieManager getCookieManager() {

        if (INSTANCE == null) {
            synchronized (CookieManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CookieManager();
                }
            }

        }
        return INSTANCE;
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        mCookieArray.put(url,cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookieList = mCookieArray.get(url);
        if(cookieList != null) {
            return cookieList;
        }
        return new ArrayList<>();
    }

}
