package com.fiwio.iot.demeter.addtask.di;

import com.fiwio.iot.demeter.addtask.AddSchedulerTaskActivity;
import com.fiwio.iot.demeter.di.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {AddSchedulerTaskModule.class}
)

public interface AddSchedulerTaskComponent {
    void injects(AddSchedulerTaskActivity addSchedulerTaskActivity);
}
