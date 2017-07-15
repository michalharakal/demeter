package com.fiwio.iot.demeter.discovery;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Handler;

/**
 * Created by mharakal on 28.04.17.
 */

public class NdsService {

    private static final String SERVICE_PREFIX = "cml";
    private static final String SERVICE_TYPE = "_socket._tcp.";

    private final NsdManager mNsdManager;
    private NsdManager.RegistrationListener serviceRegistrationListener;

    public NdsService(Context context) {
        mNsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
    }


    public void startServer(final String serviceName, final Handler handler) {

        String fullServiceName = SERVICE_PREFIX + serviceName;
        NsdServiceInfo serviceInfo = new NsdServiceInfo();
        serviceInfo.setServiceName(fullServiceName);
        serviceInfo.setServiceType(SERVICE_TYPE);
        serviceInfo.setPort(8080);

        serviceRegistrationListener = new RegistrationListener(fullServiceName, 8080,
                handler);
        mNsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, serviceRegistrationListener);

    }

    public void stopServer() {
        mNsdManager.unregisterService(serviceRegistrationListener);
        serviceRegistrationListener = null;
    }

}
