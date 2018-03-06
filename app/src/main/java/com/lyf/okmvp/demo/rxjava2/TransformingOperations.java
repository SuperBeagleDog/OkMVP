package com.lyf.okmvp.demo.rxjava2;


import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import framework.net.util.Util;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
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
     * and each of them will emit their observables.
     */
    public static void actionFlatMap() {

        List<Integer> list = Arrays.asList(1, 2, 3);

        // Uses lambda syntax here.
        Observable.fromIterable(list)
                .flatMap(integer -> {
                    log("Starting the " + integer + " task of circle list" + Utils.getThreadName());
                    return getObservable(integer);
                }).subscribe(s -> log("Finished the" + Utils.getThreadName()));

    }

    public static void actionContactMap() {

        List<Integer> list = Arrays.asList(1, 2, 3);

        // Uses lambda syntax here.
        Observable.fromIterable(list)
                .concatMap(integer -> {
                    log("Starting the " + integer + " task of circle list" + Utils.getThreadName());
                    return getObservable(integer);
                }).subscribe(s -> log("Finished the" + Utils.getThreadName()));
    }

    public static void actionFlatMapIterable() {

        List<Integer> list = Arrays.asList(1, 2, 3);

        Observable.fromIterable(list)
                .flatMapIterable(TransformingOperations::getList)
                .subscribe(s -> log("accept=" + s));
    }

    public static void actionSwitchMapWithinSameThreads() {

        List<Integer> list = Arrays.asList(1, 2, 3, 4);

        Observable.fromIterable(list)
                .switchMap(new Function<Integer, ObservableSource<String>>() {

                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        return Observable.just("integer=" + integer);
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                log("accept=" + s + Utils.getThreadName());
            }
        });
    }

    public static void actionSwitchMapWithinDifferentThreads() {

        List<Integer> list = Arrays.asList(1, 2, 3, 4);

        Observable.fromIterable(list)
                .switchMap(new Function<Integer, ObservableSource<String>>() {

                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        return Observable.just("integer=" + integer).subscribeOn(Schedulers.newThread());
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                log("accept=" + s + Utils.getThreadName());
            }
        });
    }

    public static void actionScan() {

        // Scan() without initialValue parameter.
//        Observable.just(1,2,3,4).scan(new BiFunction<Integer, Integer, Integer>() {
//            @Override
//            public Integer apply(Integer integer, Integer integer2) throws Exception {
//                log("apply="+integer+","+integer2);
//                return integer*integer2;
//            }
//        }).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                log("accept="+integer);
//            }
//        });

        // Scan() with initialValue as it's first parameter.
        Observable.just(1, 2, 3, 4).scan(5, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                log("apply=" + integer + "," + integer2);
                return integer * integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                log("accept=" + integer);
            }
        });

    }

    public static void actionGrougBy() {

        // 分割一组任务：将一组任务分割成几段，然后，依次执行。
        // 分成几组由apply方法的返回值决定。apply方法，返回多少个不同的值，则有多少段任务。
        Observable.range(0, 10).groupBy(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                log("apply=" + integer + ",sum=" + (integer / 4));
                if (integer < 5) {
                    return "test1";
                }
                return "test2";
            }
        }).subscribe(new Consumer<GroupedObservable<String, Integer>>() {
            @Override
            public void accept(GroupedObservable<String, Integer> groupedObservable) throws Exception {

                log("................getKey=" + groupedObservable.getKey());
                groupedObservable.subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        log("accept=" + integer);
                    }
                });
            }
        });
    }

    private static Observable<String> getObservable(int integer) {

        return Observable.create((ObservableOnSubscribe<String>) emitter -> {

            emitter.onNext("first task of the " + integer + "circle of list");
//            if (integer == 2) {
//                // Delay the second and third task.
//                Thread.sleep(5 * 1000);
//            }
            emitter.onNext("second task of the " + integer + "circle of list");
            emitter.onComplete();
        }).subscribeOn(Schedulers.newThread());
    }

    private static List<String> getList(int integer) {
        return Arrays.asList(String.valueOf("a" + integer), String.valueOf("b" + integer), String.valueOf("c" + integer));
    }

    private static void log(String log) {
        Log.d(TAG, log);
    }

}
