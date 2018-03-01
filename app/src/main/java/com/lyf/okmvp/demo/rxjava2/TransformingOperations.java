package com.lyf.okmvp.demo.rxjava2;


import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author Lyf
 * @CreateTime 2018/2/26
 * @Description A TransformingOperations class is including a set of methods of transforming action with RxJava2.
 * Note: A {@link Consumer} interface is a simply interface which used to instead of {@link io.reactivex.Observer} when
 * you want to simplify your {@link io.reactivex.Observer} and ignored it's another methods.
 **/
public class TransformingOperations {

    private static final String TAG = TransformingOperations.class.getSimpleName();

    /*
     * Note:
     * All of methods are fixed by a prefix string 'action'.
     * For instance:
     * A transforming method of RxJava2 called map(). I will name it as actionMap().
     */

    /**
     * <p>
     * Official Comment:
     * 1、Transform the items emitted by an Observable by applying a function to each of them.
     * 2、The Map operator applies a function of your choosing to each item emitted by the source Observable,
     * and returns an Observable that emits the results of these function applications.
     * </p>
     * <p>
     * Note that: The official comment means Map() will deal with data within a function.
     * and then return the result to its Observer/Consumer.
     * The result can be arbitrary class which you specified.
     * </p>
     * <p>
     * For instance:
     * itemA and itemB are two different classes.
     * This method will show you how to transform itemA into itemB in apply() function
     * and then return itemB to its Observer/Consumer.
     * </p>
     */
    public static void actionMap() {

        List<Integer> list = Arrays.asList(1, 2, 3);

        Observable.fromIterable(list)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        // Converts integer into string.
                        log("apply=" + Utils.getThreadName());
                        return String.valueOf(integer);
                    }
                }).subscribeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        // prints the converted value
                        log("accept=" + s + Utils.getThreadName());
                    }
                });

//        Observable.fromIterable(list)
//                .map(new Function<Integer, String>() {
//                    @Override
//                    public String apply(Integer integer) throws Exception {
//                        // Converts integer into string.
//                        log("apply=" + Utils.getThreadName());
//                        return String.valueOf(integer);
//                    }
//                }).subscribeOn(Schedulers.io())
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        // d.dispose(); Cancel Converts
//                        log("onSubscribe" + Utils.getThreadName());
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        // get 's' from apply().
//                        log("onNext=" + s + Utils.getThreadName());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        // invoked when an exception occurred.
//                        log("onError" + Utils.getThreadName());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        // invoked when transforming job done.
//                        log("onComplete" + Utils.getThreadName());
//                    }
//                });
    }

    /*
     * Three Methods below have the same official comment, I noted it here.
     * Anyway, I will explain and comment them one after another.
     *
     * Official Comment:
     * transform the items emitted by an Observable into Observables (or Iterables),
     * then flatten this into a single Observable
     */

    /**
     * FlatMap() is different from Map(). It converts an observable into observables
     * and each of them will emit their observables. Because each observable will emit their observables.
     * So that they may will internal
     */
    public static void actionFlatMap() {

        List<Integer> list = Arrays.asList(1, 2, 3);

        Observable.fromIterable(list)
                .flatMap(integer -> {
                    // Converts integer into string.
                    // Add a random delay time to fake a disorder printing.
                    //int  delayTime= (int) (1 + Math.random() * 10);
                    return Observable.just(String.valueOf(integer * 10));//.delay(delayTime, TimeUnit.MILLISECONDS);
                }).take(4)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private static void log(String log) {
        Log.d(TAG, log);
    }

}
