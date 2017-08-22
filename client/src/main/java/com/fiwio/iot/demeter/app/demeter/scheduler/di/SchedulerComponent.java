package com.fiwio.iot.demeter.app.demeter.scheduler.di;


import com.fiwio.iot.demeter.app.demeter.di.ActivityScope;
import com.fiwio.iot.demeter.app.demeter.scheduler.SchedulerFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {SchedulerModule.class}
)

public interface SchedulerComponent {
    void injects(SchedulerFragment remoteControlFragment);
}
