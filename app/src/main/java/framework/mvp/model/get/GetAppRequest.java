package framework.mvp.model.get;

import framework.bean.BaseMsg;
import framework.okhttp.HttpManager;
import framework.okhttp.consts.HttpConst;
import framework.okhttp.interfaces.Callback;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @Author Lyf
 * @CreateTime 2018/1/23
 * @Description Do some requests about app.
 **/
public class GetAppRequest {

    /**
     * Get the setting of App
     */
    public static void getAppConfig(final Callback<BaseMsg> responseCallback) {

        HttpManager.getInstance().doGet(HttpConst.GET_APP_CONFIGS,
                new Callback<BaseMsg>() {
                    @Override
                    public void onFailure(Call call, Exception e) {

                        if (responseCallback != null) {
                            responseCallback.onFailure(call, e);
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response, BaseMsg bean) {

                        if (responseCallback != null) {
                            responseCallback.onResponse(call, response, bean);
                        }

                    }
                });

    }

}
