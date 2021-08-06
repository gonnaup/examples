package org.gonnaup.examples.middleware;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author gonnaup
 * @version created at 2021/8/6 8:56
 */
@Slf4j
public class Util {
    private Util() {
        throw new AssertionError("no Util instance for you!");
    }

    public static void sleepSeconds(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public static void sleepMilliseconds(long milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

}
