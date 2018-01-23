package com.lyf.okhttputils.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import java.util.List;

/**
 * Created by Lyf on 2017/6/19.
 */

public class Utils {

    public static void startActivity(Activity from , Class to) {
        Intent intent = new Intent(from,to);
        from.startActivity(intent);
    }

    public static void startActivityForResult(Activity from , Class to, int requestCode) {
        Intent intent = new Intent(from,to);
        from.startActivityForResult(intent,requestCode);
    }

    public static <T> T getClassType(Class<T> cls) {

        try {
            return cls.newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断服务是否开启
     *
     * @return
     */
    public static boolean isServiceRunning(Context mContext, String className) {

        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)
                mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList
                = activityManager.getRunningServices(100);
        if (!(serviceList.size()>0)) {
            return false;
        }
        for (int i=0; i<serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

}