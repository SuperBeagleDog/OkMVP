package framework.bean;

/**
 * @Author Lyf
 * @CreateTime 2018/1/22
 * @Description
 **/
public class BaseBean<T> {

    private int code;
    private String msg;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

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

}
