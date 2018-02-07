package framework.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

/**
 * @Author Lyf
 * @CreateTime 2018/2/6
 * @Description Descriptions on BaseEntity{@link BaseEntity}
 **/
@Entity
public class UserInfo extends BaseEntity{

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @Ignore
    private int sex; // I don't want to persist this field. So that added @Ignore annotation.

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}