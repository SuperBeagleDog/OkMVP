package com.lyf.okmvp.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.lyf.okmvp.ui.BaseActivity;
import com.lyf.okmvp.ui.MainActivity;

import framework.database.daos.UserDao;
import framework.database.databases.UserDataBase;
import framework.thread.ThreadManager;

/**
 * @Author Lyf
 * @CreateTime 2018/2/27
 * @Description A demo shows you how to use Room DataBase to query a user via UserDao.
 **/
public class RoomDemoActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ThreadManager.execute(() -> {
            // Do something on subThread and then return the T bean.
            UserDao userDao = UserDataBase.getInstance(RoomDemoActivity.this).getUserDao();
            // query a user
            return userDao.getUserInfoViaId(100);
        }, userInfo -> {
            // Do something on UiThread with UserInfo class.
            if (userInfo == null) {
                Toast.makeText(RoomDemoActivity.this, "query failed!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RoomDemoActivity.this, "userName=" + userInfo.getFirstName() + userInfo.getLastName(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

}
