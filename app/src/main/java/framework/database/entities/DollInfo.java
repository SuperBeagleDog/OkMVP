package framework.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

/**
 * @Author Lyf
 * @CreateTime 2018/2/6
 * @Description
 **/
@Entity
public class DollInfo extends BaseEntity{

    @ColumnInfo(name = "doll_name")
    private String dollName;

    @ColumnInfo(name = "doll_price")
    private String dollPrice;

    public String getDollName() {
        return dollName;
    }

    public void setDollName(String dollName) {
        this.dollName = dollName;
    }

    public String getDollPrice() {
        return dollPrice;
    }

    public void setDollPrice(String dollPrice) {
        this.dollPrice = dollPrice;
    }
}
