package framework.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * @Author Lyf
 * @CreateTime 2018/2/5
 * @Description
 **/
public class ToastHelper {

    public static void toast(Context context, String text){

        Handler handler = new Handler(Looper.getMainLooper());
        if(context != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

}
