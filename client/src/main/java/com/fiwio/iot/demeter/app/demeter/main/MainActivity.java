package com.fiwio.iot.demeter.app.demeter.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.fiwio.iot.demeter.app.app.EndpoitUrlProvider;
import com.fiwio.iot.demeter.app.demeter.app.DemeterApplication;
import com.fiwio.iot.demeter.app.demeter.remote.RemoteControlFragment;
import com.fiwio.iot.demeter.app.demeter.scheduler.SchedulerFragment;
import com.fiwo.iot.demeter.app.R;
import com.google.firebase.crash.FirebaseCrash;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;

    @Inject
    protected EndpoitUrlProvider endpoitUrlProvider;

    private void setupBottomNavigation() {

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_manual:
                        loadManualFragment();
                        return true;
                    case R.id.navigation_automatic:
                        loadSchedulesFragment();
                        return true;
                    case R.id.navigation_settings:
                        loadSettingsFragment();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseCrash.log("Main Activity created");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inject();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            endpoitUrlProvider.setUrl(bundle.getString("host"));
        }

        setupBottomNavigation();

        if (savedInstanceState == null) {
            loadManualFragment();
        }
    }

    private void loadManualFragment() {

        Fragment fragment = RemoteControlFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void inject() {
        DemeterApplication.get(this).getAppComponent().inject(this);
    }

    private void loadSchedulesFragment() {
        Fragment fragment = SchedulerFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadSettingsFragment() {

        /*
        SettingsFragment fragment = SettingsFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
        */
    }

}
