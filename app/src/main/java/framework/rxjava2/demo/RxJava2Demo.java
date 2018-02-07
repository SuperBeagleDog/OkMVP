package framework.rxjava2.demo;

import android.support.annotation.NonNull;
import android.util.Log;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.Iterator;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author Lyf
 * @CreateTime 2018/2/7
 * @Description This is a set of demos of RxJava2, which shows you how to simply use RxJava2.
 **/
public class RxJava2Demo {

    private static void log(String log) {
        Log.d("RxJava2Demo", log);
    }

    private static String getThreadName() {
        return "  |  ThreadName=" + Thread.currentThread().getName();
    }

    // Demos below.

    /**
     *  This method is used to do something on two separately same/different threads(threads are called one by one) in an async way.
     *  For instance, you can do some heavy cpu operations on subThread and then updates your views on UI thread.
     *
     *  Observable will firstly calls subscribe() method and then calls accept() method.
     *
     *  By setting the subscribe() method runs on which thread, calls subscribeOn() to set.
     *  By setting the accept() method runs on which thread, calls observeOn() to set.
     *
     */
    public static void create() {

        final String preFix = "create";

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                log(preFix + " - 1" + getThreadName());
                emitter.onNext(2);
                log(preFix + " - 2" + getThreadName());
                emitter.onComplete();
                log(preFix + " - 3" + getThreadName());
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        log(preFix + " - accept" + integer + getThreadName());
                    }
                });
    }

    /**
     * Converts an arbitrary Reactive-Streams Publisher into an Observable.
     * If possible, use {@link #create()} method to instead of it.
     */
    public static void fromPublisher() {

        final String preFix = "fromPublisher";

        Observable.fromPublisher(new Publisher<Integer>() {
            @Override
            public void subscribe(Subscriber<? super Integer> s) {
                log(preFix + " - 0" + getThreadName());
                s.onNext(1);
                log(preFix + " - 1" + getThreadName());
                s.onComplete();
                log(preFix + " - onComplete" + getThreadName());
                s.onNext(2);
                log(preFix + " - 2" + getThreadName());
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer o) throws Exception {
                log(preFix + " - accept=" + o + getThreadName());
            }
        });
    }

    /**
     * Creates two items as observables.
     * and the rest of things are similar the {@link #create()} method does.
     *
     * Note that:
     * The log will print "1" and then print "2".
     * These times(observables) are executed one after another.
     */
    public static void just() {

        final String preFix = "just";

        Observable.just(1, 2).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        log(preFix + " - accept" + integer + getThreadName());
                    }
                });

    }

    /**
     * The amb method only executes the first Observable in the List.
     * As the demo shows, the log will only print "1","2","3".
     * The rest of observables in the List will be abandoned.
     * */
    public static void amb() {

        final String preFix = "amb-";

        ArrayList<ObservableSource<String>> arrayList = new ArrayList<>();

        ObservableSource<String> firstObservable= Observable.just("1","2","3");

        arrayList.add(firstObservable);

        ObservableSource<String> secondObservable = Observable.just("4","5","6");

        arrayList.add(secondObservable);

        Observable.amb(arrayList).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                log(preFix + "accept=" + s + getThreadName());
            }
        });

    }

    /**
     * see another method{@link #amb()}
     * */
    public static void ambArray() {

        final String preFix = "ambArray-";

        Observable.ambArray(new Observable<String>() {
            @Override
            protected void subscribeActual(Observer<? super String> observer) {
                log(preFix + "onNext-1" + getThreadName());
                observer.onNext("onNext-1");
                observer.onComplete();
            }
        }, new Observable<String>() {
            @Override
            protected void subscribeActual(Observer<? super String> observer) {
                log(preFix + "onNext-2" + getThreadName());
                observer.onNext("onNext-2");
                observer.onComplete();
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                log(preFix + "accept=" + s + getThreadName());
            }
        });

    }


}
