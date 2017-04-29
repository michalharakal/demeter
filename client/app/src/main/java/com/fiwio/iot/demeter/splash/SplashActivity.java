package com.fiwio.iot.demeter.splash;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.fiwio.iot.demeter.app.DemeterApplication;
import com.fiwio.iot.demeter.discovery.DemerServiceFound;
import com.fiwio.iot.demeter.discovery.MulticastDns;
import com.fiwio.iot.demeter.remote.RemoteControlActivity;
import com.fiwio.iot.demeter.splash.di.SplashModule;
import com.fiwo.iot.demeter.smart.R;

import javax.inject.Inject;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity implements DemerServiceFound {

    public static final int TEN_SECONDS = 10000;
    @Inject
    MulticastDns multicastDns;

    private Handler mHandler = new Handler();


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
            multicastDns.discoverServices(this, new Handler());
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
                    Thread.sleep(TEN_SECONDS);
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
        Intent mainIntent = new Intent(this, RemoteControlActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("host", ipAddress);
        mainIntent.putExtras(bundle);
        this.startActivity(mainIntent);
        this.finish();
    }

    @Override
    public void onServiceSearchFailed() {
        Intent mainIntent = new Intent(this, RemoteControlActivity.class);
        this.startActivity(mainIntent);
        this.finish();
    }
}
