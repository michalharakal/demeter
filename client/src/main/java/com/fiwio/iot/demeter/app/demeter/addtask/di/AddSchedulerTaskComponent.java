package com.fiwio.iot.demeter.app.demeter.addtask.di;

import com.fiwio.iot.demeter.app.demeter.addtask.AddSchedulerTaskActivity;
import com.fiwio.iot.demeter.app.demeter.di.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {AddSchedulerTaskModule.class}
)

public interface AddSchedulerTaskComponent {
    void injects(AddSchedulerTaskActivity addSchedulerTaskActivity);
}
