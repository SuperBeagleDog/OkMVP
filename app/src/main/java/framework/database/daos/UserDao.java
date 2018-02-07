package framework.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import framework.database.entities.UserInfo;

/**
 * @Author Lyf
 * @CreateTime 2018/2/6
 * @Description
 **/
@Dao
public interface UserDao {

    @Query("select * from UserInfo")
    List<UserInfo> getAll();

    @Query("select * from UserInfo where id in (:userIds)")
    List<UserInfo> loadAllByIds(int[] userIds);

    @Query("select * from UserInfo where first_name like :first and "
            + "last_name like :last limit 1")
    UserInfo findByName(String first, String last);

    @Query("select id,first_name,last_name from UserInfo where id = :id")
    UserInfo getUserInfoViaId(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[]  insertAll(UserInfo... users);

    @Update
    void Update(UserInfo... users);

    @Delete
    void delete(UserInfo user);
}
