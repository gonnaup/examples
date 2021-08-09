package org.gonnaup.examples.springs.core.config;

import lombok.extern.slf4j.Slf4j;
import org.gonnaup.examples.springs.aop.aspectj.TimeSpentAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author gonnaup
 * @version created at 2021/8/9 12:55
 */
@Slf4j
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
@ComponentScan(basePackages = "org.gonnaup.examples.springs.service")
public class TimeSpentAspectConfig {

    @Bean
    public TimeSpentAspect timeSpentAspect() {
        return new TimeSpentAspect();
    }
}
