package framework.mvp.model.get;

import com.lyf.okmvp.http.HttpUtils;

import framework.bean.BaseBean;
import framework.okhttp.consts.HttpUrlConst;
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
    public static void getAppConfig(final Callback<BaseBean> responseCallback) {

        HttpUtils.getInstance().doGet(HttpUrlConst.GET_APP_CONFIGS,
                new Callback<BaseBean>() {
                    @Override
                    public void onFailure(Call call, Exception e) {

                        if (responseCallback != null) {
                            responseCallback.onFailure(call, e);
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response, BaseBean bean) {

                        if (responseCallback != null) {
                            responseCallback.onResponse(call, response, bean);
                        }

                    }
                });

    }

}
