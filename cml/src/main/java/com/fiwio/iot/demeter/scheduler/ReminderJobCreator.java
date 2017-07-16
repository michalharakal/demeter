package com.fiwio.iot.demeter.scheduler;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class ReminderJobCreator implements JobCreator {
    private final ReminderEngine reminderEngine;

    public ReminderJobCreator(ReminderEngine reminderEngine) {
        this.reminderEngine = reminderEngine;
    }

    @Override
    public Job create(String tag) {
        switch (tag) {
            case ReminderJob.TAG:
                return new ReminderJob(reminderEngine);

  /*          case SyncJob.TAG:
                return new SyncJob();
                */

            default:
                return null;
        }
    }
}
