package org.gonnaup.examples.springs.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 基本注解配置类
 * @author gonnaup
 * @version 2021/7/13 16:31
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = "org.gonnaup.examples.springs.service")
public class AnnotationBasedConfig {

}
