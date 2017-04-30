package com.fiwio.iot.demeter.api;

/**
 * Created by mharakal on 17.04.17.
 */

public class NetworkError {

    private Throwable throwable;

    public NetworkError(Throwable e) {
        this.throwable = e;

    }

    public String getAppErrorMessage() {
        return throwable.getMessage();
    }
}
