package framework.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Base64;
import java.io.IOException;
import framework.utils.ApplicationUtils;
import framework.utils.parse.ParseUtil;

/**
 * @Author Lyf
 * @CreateTime 2018/3/1
 * @Description A BaseAppCaching class is caching data of app in SharePreference.
 *  data will be encrypted with Base64 when it cached in SP
 *  and decrypted with Base64 when it got from SP.
 **/
public class BaseAppCaching {

    private static BaseAppCaching mBaseAppCaching;

    protected static BaseAppCaching getAppCaching() {

        if (mBaseAppCaching == null) {
            synchronized (BaseAppCaching.class) {
                if (mBaseAppCaching == null) {
                    mBaseAppCaching = new BaseAppCaching();
                }
            }
        }

        return mBaseAppCaching;
    }

    /**
     * Uses a default key "BaseAppCaching" to save a class object in SP.
     */
    public <T> void saveAppCaching(T appCaching) {
        try {
            getSharedPreferences().edit().putString(this.getClass().getSimpleName(),
                    encryptCaching(ParseUtil.toJson(appCaching))).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> void saveAppCaching(String key, T appCaching) {
        try {
            getSharedPreferences().edit().putString(key,
                    encryptCaching(ParseUtil.toJson(appCaching))).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Uses a default key "BaseAppCaching" to get a object of T from SP.
     */
    @Nullable
    public <T> T getAppCaching(Class<T> tClass) {

        String json = getSharedPreferences().getString(this.getClass().getSimpleName(), null);

        if (json == null) {
            return null;
        }

        return ParseUtil.parseJson(decryptCaching(json), tClass);

    }

    @Nullable
    public <T> T getAppCaching(String key, Class<T> tClass) {

        String json = getSharedPreferences().getString(key, null);

        if (json == null) {
            return null;
        }

        return ParseUtil.parseJson(decryptCaching(json), tClass);

    }

    /**
     * encrypt json. You can rewrite this method in subclass.
     */
    protected String encryptCaching(String json) throws IOException {
        return new String(Base64.encode(json.getBytes(), Base64.DEFAULT));
    }

    /**
     * decrypt json. You can rewrite this method in subclass.
     */
    protected String decryptCaching(String json) {
        return new String(Base64.decode(json, Base64.DEFAULT));
    }

    private SharedPreferences getSharedPreferences() {

        if (ApplicationUtils.getApplication() == null) {
            throw new RuntimeException("You have to initialize the mContext before invoked this method!");
        }

        return ApplicationUtils.getApplication().getSharedPreferences(ApplicationUtils.getApplication().getPackageName(), Context.MODE_PRIVATE);
    }

}
