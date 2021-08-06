package org.gonnaup.examples.middleware.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author gonnaup
 * @version created at 2021/8/6 14:36
 */
@Slf4j
class LettuceReactiveApiTest {

    @Test
    void reactive() {
        log.info("========================== 开始测试 lettuce reactive API ====================");
        LettuceReactiveApi.reactive();
    }
}