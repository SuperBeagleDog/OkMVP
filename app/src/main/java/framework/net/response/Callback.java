package framework.net.response;


/**
 * @Author Lyf
 * @CreateTime 2018/1/22
 * @Description Because we can't change the form of requesting callback,
 * So that creates an interface to extend the original callback for extension.
 **/
public interface Callback<T> {

    /**
     * Called when the request could not be executed due to cancellation, a connectivity problem or
     * timeout. Because networks can fail during an exchange, it is possible that the remote server
     * accepted the request before the failure.
     */
    void onFailure(int errorCode, String errorMsg, Response<T> response);

    /**
     * Called when the Http response was successfully returned by the remote server.
     * @param bean for easily getting bean.
     * @param response a whole object of ResultSuccess, You can store the rest of data in it.
     */
    void onResponse(T bean, Response<T> response);

}
