package framework.database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * @Author Lyf
 * @CreateTime 2018/2/6
 * @Description DataBaseManager
 **/
public class DataBaseManager {

    private static DataBaseManager INSTANCE = null;

    private void DataBaseManager(){}

    public static DataBaseManager getInstance() {

        if(INSTANCE == null) {
            INSTANCE = new DataBaseManager();
        }

        return INSTANCE;
    }

    public <T extends RoomDatabase> T getDataBase(Context context, String dataBaseName, Class<T> tClass) {
        return Room.databaseBuilder(context, tClass, dataBaseName).build();
    }

}