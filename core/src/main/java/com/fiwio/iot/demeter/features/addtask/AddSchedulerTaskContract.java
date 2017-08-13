package com.fiwio.iot.demeter.features.addtask;

import com.fiwio.iot.mvp.BasePresenter;
import com.fiwio.iot.mvp.BaseView;

public class AddSchedulerTaskContract {
    public interface View extends BaseView<AddSchedulerTaskContract.Presenter> {
        void showDate(String value);

        void showTime(String value);

    }

    public interface Presenter extends BasePresenter {
        void addTask();

        void putDate(int year, int monthOfYear, int dayOfMonth);

        void putTime(int hourOfDay, int minute, int second);

        int getYear();

        int getMonth();

        int getDay();

        int getHour();

        int getMinute();
    }
}
