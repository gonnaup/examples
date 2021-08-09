package org.gonnaup.examples.springs.aop.aspectj;

import lombok.ToString;

/**
 * @author gonnaup
 * @version created at 2021/8/9 12:54
 */
@ToString
public class TimeSpenter {
    private final long start;

    private long stop;

    private long timeSpent;

    private TimeSpenter() {
        this.start = System.currentTimeMillis();
    }

    public static TimeSpenter create() {
        return new TimeSpenter();
    }

    public TimeSpenter stop() {
        this.stop = System.currentTimeMillis();
        this.timeSpent = this.stop - this.start;
        return this;
    }

    public long stopTime() {
        return this.stop;
    }

    public long timeSpent() {
        return this.timeSpent;
    }

}
