package com.fiwio.iot.demeter.app.demeter.discovery;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class ResolveListener implements NsdManager.ResolveListener {

    private static final String LOG_TAG = ResolveListener.class.getSimpleName();
    private final ClientNetworkListener clientNetworkListener;
    private final Handler handler;

    public ResolveListener(ClientNetworkListener clientNetworkListener, Handler handler) {
        this.clientNetworkListener = clientNetworkListener;
        this.handler = handler;
    }


    @Override
    public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
        Log.e(LOG_TAG, "Service resolve error (" + errorCode + ")");
        handler.post(new Runnable() {
            @Override
            public void run() {
                clientNetworkListener.onServiceDiscoveryError();
            }
        });
    }


    @Override
    public void onServiceResolved(final NsdServiceInfo serviceInfo) {
        Log.d(LOG_TAG, "Service resolve success (" + serviceInfo + ")");
        handler.post(new Runnable() {
            @Override
            public void run() {
                clientNetworkListener.onServiceDiscovered(
                        new HostInfo(
                                serviceInfo.getServiceName(),
                                serviceInfo.getHost(),
                                serviceInfo.getPort()));
            }
        });
    }
}
