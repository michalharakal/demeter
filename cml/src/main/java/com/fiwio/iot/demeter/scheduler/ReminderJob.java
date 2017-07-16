package com.fiwio.iot.demeter.scheduler;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;

public class ReminderJob extends Job {

    public static final String TAG = "ReminderJob";

    private final ReminderEngine reminderEngine;

    private static final String EXTRA_ID = "EXTRA_ID";

    public ReminderJob(ReminderEngine reminderEngine) {
        this.reminderEngine = reminderEngine;
    }

    /*package*/
    static int schedule(@NonNull Reminder reminder) {
        PersistableBundleCompat extras = new PersistableBundleCompat();
        extras.putInt(EXTRA_ID, reminder.getId());

        long time = Math.max(1L, reminder.getTimestamp() - System.currentTimeMillis());

        return new JobRequest.Builder(TAG)
                .setExact(time)
                .setExtras(extras)
                .setPersisted(true)
                .setUpdateCurrent(false)
                .build()
                .schedule();
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        int id = params.getExtras().getInt(EXTRA_ID, -1);
        if (id < 0) {
            return Result.FAILURE;
        }

        Reminder reminder = reminderEngine.getReminderById(id);
        if (reminder == null) {
            return Result.FAILURE;
        }

        int index = reminderEngine.getReminders().indexOf(reminder);

        reminderEngine.triggerEvent(reminder);
        reminderEngine.removeReminder(index, false);

        return Result.SUCCESS;
    }
}
