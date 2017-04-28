package com.fiwio.iot.demeter.discovery;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Handler;
import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by mharakal on 28.04.17.
 */

public final class RegistrationListener implements NsdManager.RegistrationListener {

    private static final String LOG_TAG = RegistrationListener.class.getSimpleName();
    private final String serviceName;
    private final int serverPort;
    private final Handler handler;

    public RegistrationListener(String serviceName, int serverPort, Handler handler) {
        this.serviceName = serviceName;
        this.serverPort = serverPort;
        this.handler = handler;
    }


    @Override
    public void onServiceRegistered(final NsdServiceInfo serviceInfo) {
        Log.d(LOG_TAG, "Service registration success");
    }


    @Override
    public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
        Log.e(LOG_TAG, "Service registration failed (" + errorCode + ")");
    }


    @Override
    public void onServiceUnregistered(NsdServiceInfo serviceInfo) {
        Log.d(LOG_TAG, "Service unregistered");
    }


    @Override
    public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
        Log.e(LOG_TAG, "Service unregistration failed (" + errorCode + ")");
    }

}
