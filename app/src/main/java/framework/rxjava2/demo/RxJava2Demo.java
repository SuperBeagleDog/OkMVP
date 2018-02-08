package framework.rxjava2.demo;

import android.support.annotation.NonNull;
import android.util.Log;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import framework.utils.GsonTools;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.internal.observers.FutureObserver;
import io.reactivex.internal.observers.FutureSingleObserver;
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

    public static String getThreadName() {
        return "  |  ThreadName=" + Thread.currentThread().getName();
    }

    // Demos below.

    /**
     * This method is used to do something on two separately same/different threads(threads are called one after the other) in an async way.
     * For instance, you can do some heavy cpu operations on subThread and then updates your views on UI thread.
     * <p>
     * key takeaway:
     * Observable will firstly call subscribe() method and then call accept() method.
     * To set subscribe() method runs on specify thread by calling subscribeOn().
     * To set accept() method runs on specify thread by calling observeOn().
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
     * <p>
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
     */
    public static void amb() {

        final String preFix = "amb-";

        ArrayList<ObservableSource<String>> arrayList = new ArrayList<>();

        ObservableSource<String> firstObservable = Observable.just("1", "2", "3");

        arrayList.add(firstObservable);

        ObservableSource<String> secondObservable = Observable.just("4", "5", "6");

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
     */
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

    /**
     * This method can be used to combine a couple of Observable's data and return the sum.
     * <p>
     * Note: It also can pass an Observable List directly.
     * see {@link Observable#combineLatest(Iterable, Function)}
     */
    public static void combineLatest() {

        final String preFix = "combineLatest-";

        Observable<Integer> ob1 = Observable.just(1, 2, 3);
        Observable<Integer> ob2 = Observable.just(4, 5, 6);
        Observable<Integer> ob3 = Observable.just(7, 8, 9);

        ArrayList<Observable<Integer>> list = new ArrayList<>();

        list.add(ob1);
        list.add(ob2);
        list.add(ob3);

        // combines two Observable's value.
        Observable.combineLatest(ob1, ob2, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                log(preFix + "apply integer1=" + integer + "," + ",integer2=" + integer2 + getThreadName());
                return integer + integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                log(preFix + "accept=" + (integer) + getThreadName());
            }
        });

        // combines three Observable's value.
        Observable.combineLatest(ob1, ob2, ob3, new Function3<Integer, Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2, Integer integer3) throws Exception {
                return 1;
            }
        }).subscribe(new Consumer<Integer>() {

            @Override
            public void accept(Integer integer) throws Exception {

            }
        });

        // combines a set of Observables' value.
        Observable.combineLatest(list, new Function<Object[], Integer>() {
            @Override
            public Integer apply(Object[] objects) throws Exception {
                Integer sum = 0;
                for (Object obj : objects) {
                    sum += (Integer) obj;
                }
                return sum;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer sum) throws Exception {
                log("accept-sum=" + sum);
            }
        });

    }

    /**
     * This method executes a set of tasks one after the other.
     */
    public static void concat() {

        final String preFix = "concat-";

        Observable<Integer> ob1 = Observable.just(1, 2, 3);
        Observable<Integer> ob2 = Observable.just(4, 5, 6);

        Observable.concat(ob1, ob2).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                log(preFix + "accept = " + integer + getThreadName());
            }
        });

    }

    /**
     * Turns a FutureSingleObserver into Observable.
     * when you invoke the onSuccess function() of FutureSingleObserver .
     * and the Consumer will be executed immediately.
     */
    public static void fromFuture() {

        final String preFix = "fromFuture-";

        FutureSingleObserver<Integer> singleObserver = new FutureSingleObserver<>();

        Observable.fromFuture(singleObserver)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        log(preFix + " accept =" + integer + getThreadName() + "," + toString());
                    }
                });

        singleObserver.onSuccess(23);
    }

    /**
     * Using merge operator to combine Observable : merge does not maintain
     * the order of Observable.
     * It will emit all the 7 values may not be in order
     * Ex - "1", "4", "2", "3", "5", "7", "8".... - may be anything
     * <p>
     * for testing the disorder of printing, you can put lots of data.
     */
    public static void merge() {

        final String preFix = "merge-";

        Observable<Integer> ob1 = Observable.just(1, 2, 3);
        Observable<Integer> ob2 = Observable.just(4, 5, 6);
        Observable<Integer> ob3 = Observable.just(7, 8, 9);

        ArrayList<Observable<Integer>> list = new ArrayList<>();

        list.add(ob1);
        list.add(ob2);
        list.add(ob3);

        Observable.merge(list).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                log(preFix + " accept =" + integer + getThreadName() + "," + toString());
            }
        });
    }

    /**
     * Merges an array Observable.
     * you can specify each observable's subscribe and observe run on which thread.
     * The ob1 shows you how to specify that.
     */
    public static void mergeArray() {

        final String preFix = "mergeArray-";

        Observable<Object> ob1 = new Observable<Object>() {
            @Override
            protected void subscribeActual(Observer<? super Object> observer) {
                log(preFix + "ob1" + getThreadName());
                observer.onNext(1);
                observer.onComplete();
            }
        }.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<String> ob2 = new Observable<String>() {
            @Override
            protected void subscribeActual(Observer<? super String> observer) {
                log(preFix + "ob2" + getThreadName());
                observer.onNext("2");
                observer.onComplete();
            }
        };

        Observable<Integer> ob3 = new Observable<Integer>() {
            @Override
            protected void subscribeActual(Observer<? super Integer> observer) {
                log(preFix + "ob3" + getThreadName());
                observer.onNext(1);
                observer.onComplete();
            }
        };

        Observable.mergeArray(ob1, ob2, ob3).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                log(preFix + "accept " + o + getThreadName());
            }
        });

    }

    /**
     * When the observable is subscribed, the call() method will be invoked.
     * after that, the accept() will be invoked too.
     */
    public static void fromCallable() {

        final String preFix = "fromCallable-";

        Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log(preFix + " call" + getThreadName() + "," + toString());
                return 1;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                log(preFix + " accept =" + integer + getThreadName() + "," + toString());
            }
        });

    }

    /**
     * When the observable is subscribed, the call() method will be invoked.
     * after that, the accept() will be invoked too.
     */
    public static void fromIterable() {

        final String preFix = "fromIterable-";

        Integer[] ob1 ={1,2,3};
        Integer[] ob2 ={4,5,6};
        Integer[] ob3 ={7,8,9};

        ArrayList<Integer[]> list = new ArrayList<>();

        list.add(ob1);
        list.add(ob2);
        list.add(ob3);

        Observable.fromIterable(list).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Integer[] array = (Integer[]) o;
                log(preFix + " accept =" + GsonTools.toJson(array) + getThreadName() + "," + toString());
            }
        });
    }

}
