package com.fiwio.iot.demeter.scheduler.di;


import com.fiwio.iot.demeter.di.ActivityScope;
import com.fiwio.iot.demeter.scheduler.SchedulerFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {SchedulerModule.class}
)

public interface SchedulerComponent {
    void injects(SchedulerFragment remoteControlFragment);
}
