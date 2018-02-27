package framework.net.http.okHttp;

import android.support.v4.util.ArrayMap;

import framework.net.http.IHttpManager;
import okhttp3.Headers;
import okhttp3.Request;

/**
 * @Author Lyf
 * @CreateTime 2018/2/23
 * @Description An IOkHttpManager interface defines What the okHttpManager can do.
 * It's better to keep this interface Since You can learn quickly what the OkHttpManager does in here.
 * Instead of reading entirely codes of OkHttpManager.
 **/
public interface IOkHttpManager extends IHttpManager{

    /**
     * Cancel requests by a tag.
     * @param tag a tag indicates requests.
     */
    void cancelRequestWithTag(Object tag);

    /**
     * Transfers the original headers(Map<String, String> type) to OkHttp's headers.
     *
     * @param originalHeaders original headers
     * @return Headers.
     */
    Headers getOkHttpHeaders(ArrayMap<String, String> originalHeaders);


//    /**
//     *
//     * @return
//     */
//    Request createRequest();

}
