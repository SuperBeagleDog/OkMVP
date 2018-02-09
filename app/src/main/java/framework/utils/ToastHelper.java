package framework.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @Author Lyf
 * @CreateTime 2018/2/5
 * @Description
 **/
public class ToastHelper {

    public void toast(Context context, String text){

        if(context != null) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    }

}
