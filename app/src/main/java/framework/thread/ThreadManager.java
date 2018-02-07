package framework.thread;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import framework.thread.interfaces.ObserverListener;
import framework.thread.interfaces.SubscribeListener;

/**
 * @Author Lyf
 * @CreateTime 2018/2/6
 * @Description
 **/
public class ThreadManager {

    private static ThreadManager INSTANCE = null;
    private final static ExecutorService mThreadPool = Executors.newFixedThreadPool(2);

    public void execute(final Runnable runnable) {
        execute(runnable, null, android.os.Process.THREAD_PRIORITY_BACKGROUND);
    }

    public void execute(final Runnable runnable,
                               final Runnable callbac) {
        execute(runnable, callbac, android.os.Process.THREAD_PRIORITY_BACKGROUND);
    }

    public static ThreadManager getThreadManager() {

        if (INSTANCE == null) {
            INSTANCE = new ThreadManager();
        }

        return INSTANCE;
    }

    public <T> void execute(final SubscribeListener<T> subscribeListener, final ObserverListener<T> observerListener) {

        final SubscribeBean<T> subThread = new SubscribeBean<>();

        execute(new Runnable() {
            @Override
            public void run() {
                try {
                    subThread.setBean(subscribeListener.runOnSubThread());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }, new Runnable() {
            @Override
            public void run() {
                observerListener.runOnUiThread(subThread.getBean());
            }
        });
    }


    /**
     * 将Runnable放入线程池执行
     *
     * @param runnable
     * @param callback 回调到execute函数所运行的线程中
     * @param priority android.os.Process中指定的线程优先级
     */
    public void execute(final Runnable runnable,
                               final Runnable callback, final int priority) {
        try {
            if (!mThreadPool.isShutdown()) {
                Handler handler = null;
                if (callback != null) {
                    handler = new Handler(Looper.myLooper());
                }

                final Handler finalHandler = handler;
                mThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        android.os.Process.setThreadPriority(priority);
                        try {
                            runnable.run();
                            if (finalHandler != null && callback != null) {
                                finalHandler.post(callback);
                            }
                        } catch (final Throwable t) {
                            t.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class SubscribeBean<T> {

        private T bean;

        public T getBean() {
            return bean;
        }

        public void setBean(T bean) {
            this.bean = bean;
        }
    }

}
