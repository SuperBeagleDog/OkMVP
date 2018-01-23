package framework.okhttp.interfaces;

import okhttp3.Call;
import okhttp3.Response;


/**
 * @Author Lyf
 * @CreateTime 2018/1/22
 * @Description Because we can't change the form of requesting callback,
 * So that creates an interface to extend the original callback for extension.
 **/
public interface Callback<T> {

    void onFailure(Call call, Exception e);

    void onResponse(Call call, Response response, T bean);

}
