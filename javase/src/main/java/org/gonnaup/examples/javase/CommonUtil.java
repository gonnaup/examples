package org.gonnaup.examples.javase;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author gonnaup
 * @version created at 2021/12/31 12:41
 */
@Slf4j
public final class CommonUtil {

    private CommonUtil() {
        throw new AssertionError("no CommonUtil instance for you!");
    }

    public static void sleep(long time, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(time);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public static String currentThreadName() {
        return Thread.currentThread().getName();
    }

}
