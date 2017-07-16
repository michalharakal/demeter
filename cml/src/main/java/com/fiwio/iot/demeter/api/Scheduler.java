package com.fiwio.iot.demeter.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Scheduler {
    @SerializedName("tasks")
    @Expose
    private List<Task> tasks = null;

    public List<Task> getTasks() {
        return tasks;
    }

    public void setFsm(List<Task> tasks) {
        this.tasks = tasks;
    }
}
