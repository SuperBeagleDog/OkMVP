package framework.thread;

import framework.thread.interfaces.ObserverListener;
import framework.thread.interfaces.SubscribeListener;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author Lyf
 * @CreateTime 2018/2/6
 * @Description
 **/
public class ThreadManager {

    /**
     * This method is used to do something on two separately different threads(threads are called one after the other) in an async way.
     * For instance, you can do some heavy cpu operations on subThread and then updates your views on UI thread.
     *
     * @param subscribeListener do something in subThread(non-ui thread)
     * @param observerListener  do something in mainThread(ui thread)
     * @param <T>               after do something in subThread,
     *                          you may want to pass a T(bean) object as a result to ui thread to update views.
     */
    public static <T> void execute(SubscribeListener<T> subscribeListener,
                                   ObserverListener<T> observerListener) {

        Observable.create((ObservableOnSubscribe<T>) emitter -> {
            emitter.onNext(subscribeListener.runOnSubThread());
            emitter.onComplete();
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observerListener::runOnUiThread);

    }

    /**
     * Run on Ui Thread.
     */
    public static void runOnUiThread(Runnable runnable) {

        Observable.just(runnable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Runnable::run);
    }

}
