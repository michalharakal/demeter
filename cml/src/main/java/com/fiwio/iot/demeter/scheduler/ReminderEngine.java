package com.fiwio.iot.demeter.scheduler;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.evernote.android.job.JobManager;
import com.fiwio.iot.demeter.events.FireFsmEvent;
import com.fiwio.iot.demeter.events.IEventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReminderEngine {

    private static final String REMINDER_ID = "REMINDER_ID";
    private static final String REMINDERS = "REMINDERS";

    private final Context mContext;

    private final SharedPreferences mPreferences;

    private final List<Reminder> mReminders;

    private int mReminderId;

    private final IEventBus mEventBus;

    public ReminderEngine(Context context, IEventBus eventBus) {
        mContext = context;
        mEventBus = eventBus;
        mPreferences = mContext.getSharedPreferences("reminders", Context.MODE_PRIVATE); // poor-man's storage
        mReminderId = mPreferences.getInt(REMINDER_ID, 0);

        mReminders = new ArrayList<>();

        Set<String> reminders = mPreferences.getStringSet(REMINDERS, null);
        if (reminders != null) {
            for (String value : reminders) {
                mReminders.add(Reminder.fromString(value));
            }
        }
        Collections.sort(mReminders);
    }

    public List<Reminder> getReminders() {
        return Collections.unmodifiableList(mReminders);
    }

    @Nullable
    public Reminder getReminderById(int id) {
        for (Reminder reminder : mReminders) {
            if (reminder.getId() == id) {
                return reminder;
            }
        }
        return null;
    }


    public Reminder createNewReminder(long timestamp, String jobName) {
        mReminderId++;

        Reminder reminder = new Reminder(mReminderId, timestamp, jobName);
        mReminders.add(reminder);
        Collections.sort(mReminders);

        int jobId = ReminderJob.schedule(reminder);
        reminder.setJobId(jobId);

        mPreferences.edit().putInt(REMINDER_ID, mReminderId).apply();
        saveReminders();

        int position = mReminders.indexOf(reminder);
        return reminder;
    }

    public void removeReminder(int position) {
        removeReminder(position, true);
    }

    /*package*/ void removeReminder(int position, boolean cancelJob) {
        Reminder reminder = mReminders.remove(position);

        if (cancelJob) {
            JobManager.instance().cancel(reminder.getJobId());
        }

        saveReminders();
    }

    public void triggerEvent(Reminder reminder) {
        mEventBus.post(new FireFsmEvent(reminder.getJobName(), "garten"));
    }

    private void saveReminders() {
        Set<String> reminders = new HashSet<>();
        for (Reminder item : mReminders) {
            reminders.add(item.toPersistableString());
        }

        mPreferences.edit().putStringSet(REMINDERS, reminders).apply();
    }

}
