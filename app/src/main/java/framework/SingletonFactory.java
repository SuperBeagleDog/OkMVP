package framework;

import android.app.Application;
import android.util.ArrayMap;

/**
 * @Author Lyf
 * @CreateTime 2018/2/1
 * @Description A SingletonFactory, which uses to produce Singleton Object of Class.
 * It's no matter the Class whether is singleton or not. Each Class will be stored
 * an unique object of itself in this class.
 **/
public class SingletonFactory {

    private static ArrayMap<Class,Object> mInstances;
    private static Application mApplication;

    public static void initSingletonFactory(Application application) {

        mApplication = application;
    }

    public static Application getApplication() {
        return mApplication;
    }


}
