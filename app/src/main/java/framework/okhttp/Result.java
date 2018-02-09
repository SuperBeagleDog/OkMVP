package framework.okhttp;

import android.support.annotation.Nullable;

/**
 * @Author Lyf
 * @CreateTime 2018/2/9
 * @Description
 **/
public class Result<T> {

    private int code;

    private String msg;

    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(@Nullable T data) {
        this.data = data;
    }
}
