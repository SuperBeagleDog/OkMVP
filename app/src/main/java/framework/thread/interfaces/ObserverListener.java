package framework.thread.interfaces;

import android.support.annotation.Nullable;

/**
 * @Author Lyf
 * @CreateTime 2018/2/6
 * @Description
 **/
public interface ObserverListener<T> {

    /**
     * You can change or update your UI in here. But you can't do any heavy operations.
     * Such as, networks、read and write on database and so on.
     * @param t
     */
    void runOnUiThread(@Nullable T t);
}