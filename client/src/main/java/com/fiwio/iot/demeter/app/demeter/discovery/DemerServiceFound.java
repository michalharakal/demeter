package com.fiwio.iot.demeter.app.demeter.discovery;

public interface DemerServiceFound {
    void onServiceFound(String ip);
    void onServiceSearchFailed();
}
