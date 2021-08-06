package org.gonnaup.examples.middleware.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author gonnaup
 * @version created at 2021/8/6 11:02
 */
@Slf4j
class LettuceAsynchronousApiTest {

    @Test
    void asynchronous() {
        log.info("========================== 开始测试 lettuce 异步API ====================");
        LettuceAsynchronousApi.asynchronous();
    }
}