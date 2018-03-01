package com.lyf.okmvp.demo.rxjava2;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lyf.okmvp.ui.BaseActivity;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @Author Lyf
 * @CreateTime 2018/2/27
 * @Description
 **/
public class RxJava2DemoActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        runRxJava2Demo();

        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {

            }
        }).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        Flowable.create(new FlowableOnSubscribe<Object>() {
            @Override
            public void subscribe(FlowableEmitter<Object> emitter) throws Exception {

            }
        }, BackpressureStrategy.DROP).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {

            }
        });
    }

    private void runRxJava2Demo() {

//        RxJava2Demo.create();
//        RxJava2Demo.fromPublisher();
//        RxJava2Demo.just();
//        RxJava2Demo.amb();
//        RxJava2Demo.ambArray();
//        RxJava2Demo.combineLatest();
//        RxJava2Demo.concat();
//        RxJava2Demo.fromCallable();
//        RxJava2Demo.fromFuture();
//        RxJava2Demo.merge();
//        RxJava2Demo.mergeArray();
//        RxJava2Demo.fromCallable();
//        RxJava2Demo.fromIterable();

    }

}
