package org.gonnaup.examples.javase.reflect;

import lombok.extern.slf4j.Slf4j;

/**
 * @author gonnaup
 * @version created at 2021/12/21 12:45
 */
@Slf4j
public class HelloSendService implements SendService {
    @Override
    public void send(String message) {
        log.info("hello, the message is {}", message);
    }
}
