package com.fiwio.iot.demeter.configuration;

public interface ConfigurationProvider {
    long getValveOpeningDuration();

    long getIrrigatingDuration();

    long getBarrelFillingDuration();
}
