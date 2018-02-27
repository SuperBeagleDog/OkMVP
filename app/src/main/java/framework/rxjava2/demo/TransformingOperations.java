package framework.rxjava2.demo;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import framework.rxjava2.Utils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author Lyf
 * @CreateTime 2018/2/26
 * @Description A TransformingOperations class is including a set of methods of transforming action with RxJava2.
 **/
public class TransformingOperations {

    private static final String TAG = TransformingOperations.class.getSimpleName();

    /*
     * Note: All of methods are fixed by a prefix string 'action'.
     * For instance:
     * A transforming method of RxJava2 called map(). I will name it as actionMap().
     */

    /**
     * <p>
     * Official Comment:
     * Transform the items emitted by an Observable by applying a function to each of them.
     * <p/>
     * <p>
     * <p>
     * My Comment:
     * Transforming itemsA into another itemsB one by one.
     * itemsA and itemsB can be two different classes.
     * This method will show you how to transform objects of Integer into String objects.
     * <p/>
     */
    public static void actionMap() {

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 100; ++i) {
            list.add(i);
        }

        Observable.fromIterable(list)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        // Converts integer into string.
                        String str = String.valueOf(integer);
                        return str;
                    }
                }).subscribeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        // Logs the converted value
                        log("accept=" + s + Utils.getThreadName());
                    }
                });
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
     * FlatMap() is different from Map(). It converts an observable into observables.
     * In map() an item of list can be converted as a specify class.
     * <p>
     * But in flatMap(), an item of list will be converted as another observable.
     * and then, You may will return an observable to Consumer.
     * Finally, The consumer will receive a specify class' value.
     * <p>
     */
    public static void actionFlatMap() {

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 100; ++i) {
            list.add(i);
        }

        Observable.fromIterable(list)
                .flatMap(integer -> {
                    // Converts integer into string.
                    // Add a random delay time to fake a disorder printing.
                    //int  delayTime= (int) (1 + Math.random() * 10);
                    return Observable.just(String.valueOf(integer * 10));//.delay(delayTime, TimeUnit.MILLISECONDS);
                }).take(4)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    // Logs the converted value
                    log("accept=" + s + Utils.getThreadName());
                });

    }

    private static void log(String log) {
        Log.d(TAG, log);
    }

}
