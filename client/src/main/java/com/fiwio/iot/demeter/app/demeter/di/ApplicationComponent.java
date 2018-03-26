package com.fiwio.iot.demeter.app.demeter.di;

import com.fiwio.iot.demeter.app.demeter.addtask.di.AddSchedulerTaskComponent;
import com.fiwio.iot.demeter.app.demeter.addtask.di.AddSchedulerTaskModule;
import com.fiwio.iot.demeter.app.demeter.main.MainActivity;
import com.fiwio.iot.demeter.app.demeter.remote.di.RemoteControlComponent;
import com.fiwio.iot.demeter.app.demeter.remote.di.RemoteControlModule;
import com.fiwio.iot.demeter.app.demeter.scheduler.di.SchedulerComponent;
import com.fiwio.iot.demeter.app.demeter.scheduler.di.SchedulerModule;
import com.fiwio.iot.demeter.app.demeter.splash.di.SplashComponent;
import com.fiwio.iot.demeter.app.demeter.splash.di.SplashModule;
import com.fiwio.iot.demeter.app.di.NetModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {ApplicationModule.class, NetModule.class}
)
public interface ApplicationComponent {
    RemoteControlComponent plus(RemoteControlModule remoteControlModule);

    SchedulerComponent plus(SchedulerModule schedulerModule);

    SplashComponent plus(SplashModule remoteControlModule);

    AddSchedulerTaskComponent plus(AddSchedulerTaskModule addSchedulerTaskModule);

    void inject(MainActivity mainActivity);
}