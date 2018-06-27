package com.fiwio.iot.demeter.scheduler;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReminderTest {

    @Test
    public void fromString() throws Exception {
        Reminder reminder = new Reminder(1, 123L, "fill", "garden");
        Reminder reminderFromString = Reminder.fromString("1/123/-1/fill");

        assertThat(reminderFromString.getId(), is(equalTo(reminder.getId())));
        assertThat(reminderFromString.getJobId(), is(equalTo(reminder.getJobId())));
        assertThat(reminderFromString.getTimestamp(), is(equalTo(reminder.getTimestamp())));
        assertThat(reminderFromString.getJobName(), is(equalTo(reminder.getJobName())));
        assertThat(reminderFromString.getBranchName(), is(equalTo(reminder.getBranchName())));
    }

    @Test
    public void toPersistableString() throws Exception {
        Reminder reminder = new Reminder(1, 123L, "fill", "flowers");
        String serialized = reminder.toPersistableString();
        assertThat(serialized, is(equalTo("1/123/-1/fill/flowers")));

    }

}