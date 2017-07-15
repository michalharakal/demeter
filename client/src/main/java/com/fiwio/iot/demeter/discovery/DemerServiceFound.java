package com.fiwio.iot.demeter.discovery;

public interface DemerServiceFound {
    void onServiceFound(String ip);
    void onServiceSearchFailed();
}
