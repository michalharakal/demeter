package com.fiwio.iot.demeter.app.demeter.addtask;

import com.fiwio.iot.demeter.app.app.EndpoitUrlProvider;
import com.fiwio.iot.demeter.app.data.NetworkError;
import com.fiwio.iot.demeter.app.data.SchedulerRepository;
import com.fiwio.iot.demeter.app.demeter.features.addtask.AddSchedulerTaskContract;
import com.fiwo.iot.demeter.api.model.ScheduledEvents;
import com.fiwo.iot.demeter.api.model.Task;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class AddSchedulerTaskPresenter implements AddSchedulerTaskContract.Presenter {
    private final DateTimeFormatter dtDateFormatter;
    private final DateTimeFormatter dtTimeFormatter;
    private DateTime dateTime;
    private final AddSchedulerTaskContract.View view;
    private final SchedulerRepository repository;


    public AddSchedulerTaskPresenter(SchedulerRepository repository, AddSchedulerTaskContract.View view, EndpoitUrlProvider endpoitUrlProvider) {
        this.view = view;
        dateTime = new DateTime();
        dtDateFormatter = DateTimeFormat.forPattern("dd.MM.yyyy");
        dtTimeFormatter = DateTimeFormat.forPattern("HH:mm:ss");
        this.repository = repository;
    }

    @Override
    public void addTask(boolean irrigate) {
        Task task = new Task();
        task.setFsm("garden");
        task.setCommand(irrigate ? "irrigate" : "fill");
        task.setTime(dateTime);
        repository.addTask(task, new SchedulerRepository.GetSchedulerCallback() {
            @Override
            public void onSuccess(ScheduledEvents demeter) {
                view.handleTaskInserted();

            }

            @Override
            public void onError(NetworkError networkError) {
                view.handleTaskInserted();
            }
        });

    }

    @Override
    public void putDate(int year, int monthOfYear, int dayOfMonth) {
        dateTime = new DateTime(year, monthOfYear, dayOfMonth, dateTime.getHourOfDay(), dateTime.getMinuteOfHour());
        view.showDate(getDateStr());
    }

    @Override
    public void putTime(int hourOfDay, int minute, int second) {
        dateTime = new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), hourOfDay, minute, second);
        view.showTime(getTimeStr());
    }

    @Override
    public int getYear() {
        return dateTime.getYear();
    }

    @Override
    public int getMonth() {
        return dateTime.getMonthOfYear();
    }

    @Override
    public int getDay() {
        return dateTime.getDayOfMonth();
    }

    @Override
    public int getHour() {
        return dateTime.getHourOfDay();
    }

    @Override
    public int getMinute() {
        return dateTime.getMinuteOfHour();
    }

    @Override
    public void onStart() {
        view.showDate(getDateStr());
        view.showTime(getTimeStr());
    }

    @Override
    public void onStop() {

    }

    private String getTimeStr() {
        return dtTimeFormatter.print(dateTime);
    }

    private String getDateStr() {
        return dtDateFormatter.print(dateTime);
    }
}
