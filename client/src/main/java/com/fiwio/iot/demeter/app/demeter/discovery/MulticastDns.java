package com.fiwio.iot.demeter.app.demeter.discovery;

import android.os.Handler;

/**
 * Created by mharakal on 29.04.17.
 */

public interface MulticastDns {
    void discoverServices(DemerServiceFound demerServiceFound, Handler handler);
    void stopDiscovery();
}
