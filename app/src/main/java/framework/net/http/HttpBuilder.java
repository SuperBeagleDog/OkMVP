package framework.net.http;

import framework.net.consts.HttpCodes;

/**
 * @Author Lyf
 * @CreateTime 2018/2/23
 * @Description A HttpBuilder class is a Builder for creating a set of settings of http.
 **/
public final class HttpBuilder {

    private int readTimeOut = 30_000;
    private int writeTimeOut = 30_000;
    private int connectTimeOut = 30_000;

    /**
     * Sets Read, Write and Connect TimeOut.
     *
     * @param timeOut
     */
    public HttpBuilder setTimeOut(int timeOut) {

        setReadTimeOut(timeOut);
        setWriteTimeOut(timeOut);
        setConnectTimeOut(timeOut);
        return this;
    }

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public HttpBuilder setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
        return this;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public HttpBuilder setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
        return this;
    }

    public int getWriteTimeOut() {
        return writeTimeOut;
    }

    public HttpBuilder setWriteTimeOut(int writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return this;
    }

    public int getCodeSuccess() {
        return HttpCodes.CODE_SUCCESS;
    }

    public HttpBuilder setCodeSuccess(int codeSuccess) {
        HttpCodes.CODE_SUCCESS = codeSuccess;
        return this;
    }

    public int getCodeFailure() {
        return HttpCodes.CODE_FAILURE;
    }

    public HttpBuilder setCodeFailure(int codeFailure) {
        HttpCodes.CODE_FAILURE = codeFailure;
        return this;
    }

}
