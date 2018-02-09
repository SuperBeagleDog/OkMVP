package framework.okhttp.consts;

/**
 * @Author Lyf
 * @CreateTime 2018/1/22
 * @Description Const of Http request
 **/
public interface HttpUrlConst {

    // server url
    String SERVER_URL = "http://112.74.48.33:8018/";

    // root address
    String APP = "app/";

    // root address
    String LOGIN = "login/";

    // Entirely request url
    String GET_APP_CONFIGS = SERVER_URL + APP + "configs";

    String POST_LOGIN_REQUEST = SERVER_URL + LOGIN + "phone";

}
