package com.fiwio.iot.demeter.splash;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.fiwio.iot.demeter.discovery.DemerServiceFound;
import com.fiwio.iot.demeter.discovery.NdsDiscovery;
import com.fiwio.iot.demeter.remote.RemoteControlActivity;
import com.fiwo.iot.demeter.smart.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity implements DemerServiceFound {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            NdsDiscovery ndsDiscovery = new NdsDiscovery(this.getApplicationContext(),  new
                    Handler(), this);
            ndsDiscovery.discoverServices();
        }
    }

    @Override
    public void onServiceFound(String ipAddress) {
        Intent mainIntent = new Intent(this, RemoteControlActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("host", ipAddress);
        mainIntent.putExtras(bundle); //Put your id to your next Intent
        this.startActivity(mainIntent);
        this.finish();
    }

    @Override
    public void onServiceSearchFailed() {

    }
}
