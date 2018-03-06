package com.lyf.okmvp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.lyf.okmvp.R;
import com.lyf.okmvp.demo.rxjava2.RxJava2Demo;
import com.lyf.okmvp.demo.rxjava2.TransformingOperations;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import framework.bean.BaseBean;
import framework.mvp.model.get.GetAppRequest;
import framework.net.response.Callback;
import framework.net.response.Response;
import framework.utils.LogUtil;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ThreadManager.runOnUiThread(() ->
//                toast("test"));
        TransformingOperations.actionScan();
//
//        Observable.interval(1, TimeUnit.MILLISECONDS)
//                //.subscribeOn(Schedulers.newThread())
//                //将观察者的工作放在新线程环境中
//                .observeOn(Schedulers.newThread())
//                //观察者处理每1000ms才处理一个事件
//                .subscribe(aLong -> {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    log("---->" + aLong);
//                });

    }

    // 模仿事件积压
    private void doObservable() {

        Observable.create(new ObservableOnSubscribe<Double>() {
            @Override
            public void subscribe(ObservableEmitter<Double> emitter) throws Exception {
                for (double i = 0; i < 1_000_000_000_000D; ++i) {
                    emitter.onNext(1_000_000_000_000D);
                }
                emitter.onComplete();
            }
        }).observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<Double>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //   d.dispose();
                    }

                    @Override
                    public void onNext(Double aDouble) {
                        try {
                            log("integer-1=" + aDouble + RxJava2Demo.getThreadName());
                            // 模仿事件积压
                            Thread.sleep(100_000);
                        } catch (Exception e) {
                            log("onNextError=" + e.getMessage());
                        }
                        log("integer-2=" + aDouble + RxJava2Demo.getThreadName());
                    }

                    @Override
                    public void onError(Throwable t) {
                        log("onError=" + t.getMessage() + RxJava2Demo.getThreadName());
                    }

                    @Override
                    public void onComplete() {
                        log("onComplete=" + RxJava2Demo.getThreadName());
                    }
                });
    }

    private void doFlowable() {

        Flowable.create((FlowableOnSubscribe<Double>) emitter -> {

            for (double i = 0; i < 128; ++i) {
                emitter.onNext(i);
            }

            emitter.onComplete();
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Double>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(128);
                    }

                    @Override
                    public void onNext(Double aDouble) {

                        log("integer=" + aDouble + RxJava2Demo.getThreadName());
                        try {
                            Thread.sleep(1_500);
                        } catch (Exception e) {
                            log("onNext=" + e.getMessage());
                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        log("onError=" + t.getMessage() + RxJava2Demo.getThreadName());
                    }

                    @Override
                    public void onComplete() {
                        log("onComplete=" + RxJava2Demo.getThreadName());
                    }
                });
    }

    private void toast(String test) {
        Toast.makeText(getApplicationContext(), test, Toast.LENGTH_SHORT).show();
    }

    private void doNetWorkTest() {

        GetAppRequest.getAppConfig(new Callback<BaseBean>() {
            @Override
            public void onFailure(int errorCode, String errorMsg, Response<BaseBean> response) {
                LogUtil.log("onFailure=" + Thread.currentThread().getName());
            }

            @Override
            public void onResponse(BaseBean bean, Response<BaseBean> response) {
                LogUtil.log("onResponse=" + Thread.currentThread().getName());
            }
        });

    }


    private void log(String log) {
        Log.d(MainActivity.class.getSimpleName(), log);
    }

}
