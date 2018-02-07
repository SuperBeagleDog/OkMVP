package framework.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import framework.database.entities.DollInfo;
import framework.database.entities.UserInfo;

/**
 * @Author Lyf
 * @CreateTime 2018/2/6
 * @Description
 **/
@Dao
public interface DollDao {

    @Query("SELECT * FROM DollInfo")
    List<DollInfo> getAll();

    @Query("SELECT * FROM DollInfo WHERE id IN (:userIds)")
    List<DollInfo> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM DollInfo WHERE doll_Name LIKE :dollName LIMIT 1")
    DollInfo findByName(String dollName);

    @Insert
    void insertAll(DollInfo... users);

    @Delete
    void delete(DollInfo user);
}
