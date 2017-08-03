package com.fiwio.iot.demeter.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.fiwio.iot.demeter.di.ApplicationComponent;
import com.fiwio.iot.demeter.di.ApplicationModule;
import com.fiwio.iot.demeter.di.DaggerApplicationComponent;

public class DemeterApplication extends Application implements Application
        .ActivityLifecycleCallbacks {

    public static DemeterApplication get(Context context) {
        return (DemeterApplication) context.getApplicationContext();
    }

    private boolean isInForeground = false;

    private ApplicationComponent appComponent;


    protected void initAppComponent() {
        appComponent = DaggerApplicationComponent.builder().applicationModule(new
                ApplicationModule(this)).build();
    }

    public ApplicationComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        isInForeground = true;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        isInForeground = false;
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public boolean isInForeground() {
        return isInForeground;
    }
}
