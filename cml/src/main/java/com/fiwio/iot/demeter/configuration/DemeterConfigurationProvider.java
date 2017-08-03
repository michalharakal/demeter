package com.fiwio.iot.demeter.configuration;

import org.joda.time.DateTime;

public class DemeterConfigurationProvider implements ConfigurationProvider {
    private final Configuration configuration;

    public DemeterConfigurationProvider(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public long getValveOpeningDuration() {
        return configuration.getConfiguration().getValve();
    }

    @Override
    public long getIrrigatingDuration() {
        DateTime current = new DateTime();
        if (current.getHourOfDay() < 12) {
            return configuration.getConfiguration().getIrrigatingDurationMorning();
        } else {
            return configuration.getConfiguration().getIrrigatingDurationEvening();
        }
    }

    @Override
    public long getBarrelFillingDuration() {
        return configuration.getConfiguration().getFillingDuration();
    }
}
