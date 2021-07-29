package org.gonnaup.examples.middleware.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author gonnaup
 * @version created at 2021/7/26 10:34
 */
@Slf4j
class LettuceRedisCommandsTest {

    @Test
    void stringStructCommand() {
        LettuceRedisCommands.stringStructCommand();
    }

    @Test
    void listStructCommand() {
        LettuceRedisCommands.listStructCommand();
    }

    @Test
    void setStructCommand() {
        LettuceRedisCommands.setStructCommand();
    }

    @Test
    void sortedsetStructCommand() {
        LettuceRedisCommands.sortedsetStructCommand();
    }

    @Test
    void hashStructCommand() {
        LettuceRedisCommands.hashStructCommand();
    }
}