package com.fiwio.iot.demeter.scheduler;

import android.support.annotation.NonNull;

public class Reminder implements Comparable<Reminder> {

    /*package*/
    static Reminder fromString(String value) {
        String[] split = value.split("/");
        if (split.length != 4) {
            return null;
        }
        return new Reminder(Integer.parseInt(split[0]), Long.parseLong(split[1]), Integer.parseInt(split[2]), split[3]);
    }

    private final int mId;
    private final long mTimestamp;
    private int mJobId;
    private final String mJobName;

    /*package*/ Reminder(int id, long timestamp, String jobName) {
        this(id, timestamp, -1, jobName);
    }

    private Reminder(int id, long timestamp, int jobId, String mJobName) {
        mId = id;
        mTimestamp = timestamp;
        mJobId = jobId;
        this.mJobName = mJobName;
    }

    public int getId() {
        return mId;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public int getJobId() {
        return mJobId;
    }

    /*package*/ void setJobId(int jobId) {
        mJobId = jobId;
    }

    public String getJobName() {
        return mJobName;
    }

    /*package*/ String toPersistableString() {
        return mId + "/" + mTimestamp + "/" + mJobId + "/" + mJobName;
    }

    @Override
    public int compareTo(@NonNull Reminder o) {
        return compare(mTimestamp, o.mTimestamp);
    }

    private static int compare(long x, long y) {
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }
}
