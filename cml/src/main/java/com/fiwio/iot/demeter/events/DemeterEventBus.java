package com.fiwio.iot.demeter.events;


import org.greenrobot.eventbus.EventBus;

/**
 * simple wrapper with interface for event bus implementation by greenrobot
 *
 * @author mharakal
 */
public class DemeterEventBus implements IEventBus {

    @Override
    public void register(final Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }


    @Override
    public void unregister(final Object subscriber) {
        EventBus.getDefault().unregister(subscriber);

    }


    @Override
    public void post(final Object event) {
        EventBus.getDefault().post(event);

    }

}