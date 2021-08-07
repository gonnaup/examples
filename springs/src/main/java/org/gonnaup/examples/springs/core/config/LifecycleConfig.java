package org.gonnaup.examples.springs.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 生命周期配置类
 * @author gonnaup
 * @version created at 2021/8/6 21:16
 */
@Configuration
@ComponentScan(basePackages = "org.gonnaup.examples.springs.core.lifecycle")
public class LifecycleConfig {
}
