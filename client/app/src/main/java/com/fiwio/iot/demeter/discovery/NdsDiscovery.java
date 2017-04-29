package com.fiwio.iot.demeter.discovery;


import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class NdsDiscovery implements NsdManager.DiscoveryListener, MulticastDns {
    private static final String LOG_TAG = NdsDiscovery.class.getSimpleName();
    private static final String SERVICE_PREFIX = "cml";
    private static final String SERVICE_TYPE = "_socket._tcp.";


    private final NsdManager nsdManager;
    private NsdManager.DiscoveryListener discoveryListener;

    private DemerServiceFound demerServiceFound;
    private Handler handler;

    public NdsDiscovery(Context context) {
        nsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
    }

    @Override
    public void discoverServices(DemerServiceFound demerServiceFound, Handler handler) {
        this.demerServiceFound = demerServiceFound;
        this.handler = handler;
        stopDiscovery();
        nsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, this);
    }

    @Override
    public void stopDiscovery() {
        if (discoveryListener != null) {
            try {
                nsdManager.stopServiceDiscovery(discoveryListener);
            } finally {
            }
            discoveryListener = null;
        }
    }

    @Override
    public void onStartDiscoveryFailed(String serviceType, int errorCode) {
        Log.d(LOG_TAG, "failed" + serviceType);
        demerServiceFound.onServiceSearchFailed();
    }

    @Override
    public void onStopDiscoveryFailed(String serviceType, int errorCode) {
        Log.d(LOG_TAG, "failed" + serviceType);
        demerServiceFound.onServiceSearchFailed();
    }

    @Override
    public void onDiscoveryStarted(String serviceType) {
        Log.d(LOG_TAG, "Started" + serviceType);

    }

    @Override
    public void onDiscoveryStopped(String serviceType) {
        Log.d(LOG_TAG, "onDiscoveryStopped" + serviceType);
    }

    @Override
    public void onServiceFound(NsdServiceInfo serviceInfo) {
        Log.d(LOG_TAG, serviceInfo.getServiceName());
        if (!serviceInfo.getServiceType().equals(SERVICE_TYPE) || !serviceInfo.getServiceName().startsWith(SERVICE_PREFIX)) {
            Log.d(LOG_TAG, "Discarding discovered service");
            return;
        }
        nsdManager.resolveService(serviceInfo, new ResolveListener(new ClientNetworkListener() {
            @Override
            public void onServiceDiscoveryError() {
                demerServiceFound.onServiceSearchFailed();
            }

            @Override
            public void onServiceDiscovered(HostInfo hostInfo) {
                demerServiceFound.onServiceFound("http://" + hostInfo.getAddress().getHostAddress() + ":" + hostInfo.getPort());
            }
        }, handler));

    }

    @Override
    public void onServiceLost(NsdServiceInfo serviceInfo) {
        demerServiceFound.onServiceSearchFailed();
    }
}

