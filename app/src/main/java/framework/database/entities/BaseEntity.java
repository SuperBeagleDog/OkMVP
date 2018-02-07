package framework.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * @Author Lyf
 * @CreateTime 2018/2/6
 * @Description An Entity class, You can use tableName attribute to set the tableName.
 * By default, the tableName uses the class' name.@Entity(tableName = "UserInfo")
 **/
@Entity
public class BaseEntity {

    @PrimaryKey
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
