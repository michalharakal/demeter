package com.fiwio.iot.demeter.app.demeter.scheduler.di;


import com.fiwio.iot.demeter.app.demeter.scheduler.SchedulerFragment;

import dagger.Subcomponent;

@Subcomponent(
        modules = {SchedulerModule.class}
)

public interface SchedulerComponent {
    void injects(SchedulerFragment remoteControlFragment);
}
