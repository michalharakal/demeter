package com.fiwio.iot.demeter.app.data;

public class NetworkError {

    private Throwable throwable;

    public NetworkError(Throwable e) {
        this.throwable = e;
    }

    public String getAppErrorMessage() {
        return throwable.getMessage();
    }
}
