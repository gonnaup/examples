package org.gonnaup.examples.middleware.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author gonnaup
 * @version created at 2021/7/22 15:45
 */
@Slf4j
class SimpleLettuceClientTest {

    @Test
    public void redisConnect() {
        SimpleLettuceClient.redisConnect();
    }
}