package com.fiwio.iot.demeter.configuration;

import android.content.Context;

import com.fiwo.iot.demeter.api.model.DayTime;
import com.fiwo.iot.demeter.api.model.ModelConfiguration;

public class Configuration {

    private static final Integer TEN_MINUTES_IN_SECS = 10 * 60;
    private static final Integer THIRTY_MINUTES_IN_SECS = 30 * 60;
    private ModelConfiguration configuration;
    private final Context context;

    public Configuration(Context context) {
        this.context = context;
        configuration = new ModelConfiguration();

        // evening filling
        DayTime eveningFillingTime = new DayTime();
        eveningFillingTime.setHour(21);
        eveningFillingTime.setMinute(0);
        configuration.setFillingTime(eveningFillingTime);
        // evening irrigating
        DayTime eveningIrrigationgTime = new DayTime();
        eveningIrrigationgTime.setHour(19);
        eveningIrrigationgTime.setMinute(0);
        configuration.setIrrigatingTimeEvening(eveningIrrigationgTime);
        // morning irigating
        DayTime morningIrrigationgTime = new DayTime();
        morningIrrigationgTime.setHour(7);
        morningIrrigationgTime.setMinute(0);
        configuration.setIrrigatingTimeMorning(morningIrrigationgTime);

        configuration.setValve(20);
        configuration.setFillingDuration(THIRTY_MINUTES_IN_SECS);
        configuration.setIrrigatingDurationEvening(TEN_MINUTES_IN_SECS);
        configuration.setIrrigatingDurationMorning(TEN_MINUTES_IN_SECS);
    }

    public ModelConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(ModelConfiguration modelConfiguration) {
        this.configuration = modelConfiguration;
    }
}
