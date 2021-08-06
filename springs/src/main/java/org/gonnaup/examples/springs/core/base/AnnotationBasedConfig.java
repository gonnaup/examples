package org.gonnaup.examples.springs.core.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author gonnaup
 * @version 2021/7/13 16:31
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = "org.gonnaup.examples.springs.service")
public class AnnotationBasedConfig {


}
