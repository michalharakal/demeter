package com.fiwio.iot.demeter.events;

public interface IEventBus
{

    public void register(Object subscriber);


    public void unregister(Object subscriber);


    public void post(Object event);
}
