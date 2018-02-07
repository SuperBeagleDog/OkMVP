package framework.database;

import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * @Author Lyf
 * @CreateTime 2018/2/6
 * @Description
 **/
public abstract class BaseRoom<T> extends RoomDatabase {

    public abstract void getDao(Context context, onGetDaoListener<T> listener);

    public interface onGetDaoListener<T>{
        T onGetDao(T dao);
    }

}
