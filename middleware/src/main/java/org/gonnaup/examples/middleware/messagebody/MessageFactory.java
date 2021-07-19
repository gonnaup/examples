package org.gonnaup.examples.middleware.messagebody;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 消息体工厂
 *
 * @author hy
 * @version 1.0
 * @Created on 2021/7/19 16:23
 */
public final class MessageFactory {

    private MessageFactory() {
        throw new AssertionError("no instance for you");
    }

    public static Product randomProduct() {
        return new Product(
                RandomUtils.nextInt(99999999),
                RandomStringUtils.randomAlphabetic(6),
                BigDecimal.valueOf(RandomUtils.nextDouble() * 10000).setScale(2, RoundingMode.HALF_UP).doubleValue(),
                RandomStringUtils.randomAlphabetic(10),
                RandomUtils.nextInt(1000) + 10,
                RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(90) + 10),
                LocalDate.now(),
                LocalDateTime.now()
        );
    }

}
