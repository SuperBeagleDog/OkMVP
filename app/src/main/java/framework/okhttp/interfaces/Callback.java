package framework.okhttp.interfaces;


import framework.okhttp.Result;

/**
 * @Author Lyf
 * @CreateTime 2018/1/22
 * @Description Because we can't change the form of requesting callback,
 * So that creates an interface to extend the original callback for extension.
 **/
public interface Callback<T> {

    void onFailure(Result<T> response);

    void onResponse(Result<T> response, T bean);

}
