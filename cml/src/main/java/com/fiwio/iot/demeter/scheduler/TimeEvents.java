package com.fiwio.iot.demeter.scheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class TimeEvents {

    public TimeEvents(Context context) {

        // create the object
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intentAlarm = new Intent(context, AlarmReciever.class);

        long time = 0;
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(context, 1,
                intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
    }
}
