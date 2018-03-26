package com.fiwio.iot.demeter.app.demeter.addtask.di;

import com.fiwio.iot.demeter.app.demeter.addtask.AddSchedulerTaskActivity;

import dagger.Subcomponent;

@Subcomponent(
        modules = {AddSchedulerTaskModule.class}
)

public interface AddSchedulerTaskComponent {
    void injects(AddSchedulerTaskActivity addSchedulerTaskActivity);
}
