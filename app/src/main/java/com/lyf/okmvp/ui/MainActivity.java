package com.lyf.okmvp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lyf.okmvp.R;

import framework.rxjava2.demo.RxJava2Demo;
import framework.thread.ThreadManager;
import framework.database.daos.UserDao;
import framework.database.databases.UserDataBase;
import framework.database.entities.UserInfo;
import framework.thread.interfaces.ObserverListener;
import framework.thread.interfaces.SubscribeListener;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {


    private Consumer<Integer> mConsumer;
    private ObservableOnSubscribe<Integer> mObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                runRxJava2Demo();
            }
        });


    }

    private void runRxJava2Demo() {

        RxJava2Demo.create();
//        Observable.create()
        RxJava2Demo.fromPublisher();
//        Observable.fromPublisher();
        RxJava2Demo.just();
//        Observable.just();
        RxJava2Demo.amb();
//        Observable.amb();
        RxJava2Demo.ambArray();
//        Observable.ambArray();
//        Observable.combineLatest();
//        Observable.concat();
//        Observable.fromFuture();
//        Observable.merge();
//        Observable.mergeArray();
//        Observable.fromCallable();
//        Observable.fromIterable();

    }


    private void toastUserBean() {

        ThreadManager.getThreadManager().execute(new SubscribeListener<UserInfo>() {
            @Override
            public UserInfo runOnSubThread() throws Exception {
                // Do something on subThread and then return the T bean.
                UserDao userDao = UserDataBase.getInstance(MainActivity.this).getUserDao();
                // query a user
                return userDao.getUserInfoViaId(100);
            }
        }, new ObserverListener<UserInfo>() {
            @Override
            public void runOnUiThread(@Nullable UserInfo userInfo) {
                // Do something on UiThread with UserInfo class.
                if (userInfo == null) {
                    Toast.makeText(MainActivity.this, "query failed!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "userName=" + userInfo.getFirstName() + userInfo.getLastName(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        Observable.create(new ObservableOnSubscribe<UserInfo>() {
            @Override
            public void subscribe(ObservableEmitter<UserInfo> emitter) throws Exception {

                UserDao userDao = UserDataBase.getInstance(MainActivity.this).getUserDao();

                emitter.onNext(userDao.getUserInfoViaId(2330));

                // Save a user bean.
                UserInfo tempUser = new UserInfo();
                tempUser.setId(2330);
                tempUser.setFirstName("张");
                tempUser.setLastName("三");
                userDao.Update(tempUser);

                emitter.onNext(userDao.getUserInfoViaId(2330));
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserInfo>() {
                    @Override
                    public void accept(UserInfo userInfo) throws Exception {

                        if (userInfo == null) {
                            Toast.makeText(MainActivity.this, "query failed!", Toast.LENGTH_SHORT).show();
                        } else {
                            log("userName=" + userInfo.getFirstName() + userInfo.getLastName());
//                            Toast.makeText(MainActivity.this, "userName=" + userInfo.getFirstName() + userInfo.getLastName(),
//                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void log(String log) {
        Log.d(MainActivity.class.getSimpleName(), log);
    }

}
