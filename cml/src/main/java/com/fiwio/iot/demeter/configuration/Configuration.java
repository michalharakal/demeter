package com.fiwio.iot.demeter.configuration;

import android.content.Context;

import com.fiwo.iot.demeter.api.model.DayTime;
import com.fiwo.iot.demeter.api.model.ModelConfiguration;

public class Configuration {

    private static final Integer TEN_MINUTES_IN_SECS = 10 * 60;
    private ModelConfiguration configuration;
    private final Context context;

    public Configuration(Context context) {
        this.context = context;
        configuration = new ModelConfiguration();
        DayTime evening = new DayTime();
        evening.setHour(21);
        evening.setMinute(0);
        configuration.setFillingTime(evening);
        DayTime morning = new DayTime();
        morning.setHour(5);
        morning.setMinute(30);
        configuration.setIrrigatingTime(morning);
        configuration.setValve(20);
        configuration.setFillingDuration(TEN_MINUTES_IN_SECS);
        configuration.setIrrigatingDuration(TEN_MINUTES_IN_SECS);
    }

    public ModelConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(ModelConfiguration modelConfiguration) {
        this.configuration = modelConfiguration;
    }
}
