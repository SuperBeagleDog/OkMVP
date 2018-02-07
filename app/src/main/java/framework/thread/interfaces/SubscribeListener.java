package framework.thread.interfaces;

/**
 * @Author Lyf
 * @CreateTime 2018/2/6
 * @Description A SubscribeListener
 **/
public interface SubscribeListener<T> {

    /**
     * Do some works on SubThread, You can't change or update your UI within it.
     * @return return null if any of errors occurs.
     * @throws Exception
     */
    T runOnSubThread() throws Exception;
}