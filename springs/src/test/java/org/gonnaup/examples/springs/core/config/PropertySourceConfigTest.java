package org.gonnaup.examples.springs.core.config;

import lombok.extern.slf4j.Slf4j;
import org.gonnaup.examples.springs.core.placeholdconfigurer.Information;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * @author gonnaup
 * @version created at 2021/8/7 18:41
 */
@Slf4j
@SpringJUnitConfig(PropertySourceConfig.class)
public class PropertySourceConfigTest {

    @Autowired
    Information information;

    @Test
    public void testCustomPropertySourcesPlaceholderConfigurer() {
        log.info("applicationname is {}, password is {}", information.getApplicationname(), information.getPassword());
        Assertions.assertEquals("password", information.getPassword());
    }
}
