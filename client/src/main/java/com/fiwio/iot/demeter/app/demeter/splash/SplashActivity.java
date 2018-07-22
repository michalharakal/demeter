package com.fiwio.iot.demeter.app.demeter.splash;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.fiwio.iot.demeter.app.demeter.app.DemeterApplication;
import com.fiwio.iot.demeter.app.demeter.discovery.DemerServiceFound;
import com.fiwio.iot.demeter.app.demeter.discovery.MulticastDns;
import com.fiwio.iot.demeter.app.demeter.main.MainActivity;
import com.fiwio.iot.demeter.app.demeter.splash.di.SplashModule;
import com.fiwo.iot.demeter.app.R;

import javax.inject.Inject;

public class SplashActivity extends AppCompatActivity implements DemerServiceFound {

    public static final int TWENTY_SECONDS = 5000;
    @Inject
    MulticastDns multicastDns;

    private Handler mHandler = new Handler();
    private int repeatCount = 0;
    private boolean started = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        inject();
    }

    private void inject() {
        DemeterApplication.get(this).getAppComponent().plus(new SplashModule()).injects(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (multicastDns != null) {
            multicastDns.discoverServices(this, mHandler);
            startWatchDog();
        } else {
            onServiceSearchFailed();
        }
    }

    private void startWatchDog() {
        new Thread(new Runnable() {


            @Override
            public void run() {
                try {
                    Thread.sleep(TWENTY_SECONDS);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onServiceSearchFailed();
                        }
                    });
                } catch (Exception e) {
                    ;
                }
            }
        }).start();
    }


    @Override
    public void onServiceFound(String ipAddress) {
        multicastDns.stopDiscovery();
        multicastDns = null;
        Intent mainIntent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("host", ipAddress);
        mainIntent.putExtras(bundle);
        this.startActivity(mainIntent);
        this.finish();
    }

    @Override
    public void onServiceSearchFailed() {
        if (repeatCount >= 3) {
            multicastDns.stopDiscovery();
            if (!started) {
                started = true;
                Intent mainIntent = new Intent(this, MainActivity.class);
                this.startActivity(mainIntent);
                this.finish();
            }
        } else {
            repeatCount++;
            if (multicastDns != null) {
                multicastDns.stopDiscovery();
                multicastDns.discoverServices(this, mHandler);
                startWatchDog();
            }

        }
    }
}
