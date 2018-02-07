package framework.database.databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import framework.database.DataBaseManager;
import framework.database.daos.UserDao;
import framework.database.entities.UserInfo;

/**
 * @Author Lyf
 * @CreateTime 2018/2/6
 * @Description
 **/
@Database(version = 1, exportSchema = false,
        entities = {UserInfo.class})
public abstract class UserDataBase extends RoomDatabase {


    public abstract UserDao getUserDao();

    public static UserDataBase getInstance(Context context) {
        return DataBaseManager.getInstance().getDataBase(context,
                UserDataBase.class.getSimpleName(), UserDataBase.class);
    }


}
