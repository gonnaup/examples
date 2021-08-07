package org.gonnaup.examples.springs.core.config;

import lombok.extern.slf4j.Slf4j;
import org.gonnaup.examples.springs.core.factorybean.Hello;
import org.gonnaup.examples.springs.core.factorybean.Hi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.annotation.Resource;

/**
 * @author gonnaup
 * @version created at 2021/8/7 15:58
 */
@Slf4j
@SpringJUnitConfig(FactoryBeanConfig.class)
public class FactoryBeanConfigTest {

    @Resource
    @Qualifier("hello")
    Hello hello;

    @Resource
    @Qualifier("hi")
    Hi hi;

    @Test
    public void testFactoryBean() {
        hello.sayHello("gonnaup");
        hi.sayHi();
    }

}
