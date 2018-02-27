package framework.rxjava2;

import java.util.concurrent.TimeUnit;

import framework.utils.LogUtil;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * @Author Lyf
 * @CreateTime 2018/2/7
 * @Description
 **/
public class RxJava2Manager {

    public static void test() {

        LogUtil.log("TIME_VALUE =test");
        PublishProcessor<Integer> source = PublishProcessor.create();
        source.observeOn(Schedulers.computation())
                 .subscribe((v)-> compute(v));
//        PublishProcessor<Integer> source = PublishProcessor.create();
//
//        source
//                .observeOn(Schedulers.computation())
//                .subscribe(v -> compute(v),
//                        (exception)->   LogUtil.log("TIME_VALUE ="+exception.toString()));
//
//        for (int i = 0; i < 1_000_000; i++) {
//            source.onNext(i);
//        }
//
//        try{
//            Thread.sleep(10_000);
//        }catch (Exception e){
//            LogUtil.log("TIME_VALUE ="+e.toString());
//        }

        Flowable.range(1, 1_000_000)
                .subscribe(new DisposableSubscriber<Integer>() {
                    @Override
                    public void onStart() {
                        request(1);
                    }

                    public void onNext(Integer v) {
                        compute(v);

                        request(1);
                    }

                    @Override
                    public void onError(Throwable ex) {
                        ex.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.log("onComplete");
                    }
                });

    }

    private static void compute(Integer v) {

        LogUtil.log("TIME_VALUE ="+v);
    }


}

