package org.gonnaup.examples.springs.core.config;

import lombok.extern.slf4j.Slf4j;
import org.gonnaup.examples.springs.core.lifecycle.LifecycleUniqueBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * @author gonnaup
 * @version created at 2021/8/6 22:52
 */
@Slf4j
@SpringJUnitConfig(value = LifecycleConfig.class)
public class LifecycleConfigTest {

    @Autowired
    LifecycleUniqueBean lifecycleUniqueBean;

    @Test
    public void testLifecycle() {
        lifecycleUniqueBean.message();
    }

}
