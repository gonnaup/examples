package org.gonnaup.examples.springs.core.config;

import lombok.extern.slf4j.Slf4j;
import org.gonnaup.examples.springs.core.factorybean.Hello;
import org.gonnaup.examples.springs.core.factorybean.Hi;
import org.gonnaup.examples.springs.core.factorybean.SimpleInterfaceProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link org.springframework.beans.factory.FactoryBean} 接口配置类
 *
 * @author gonnaup
 * @version created at 2021/8/7 15:49
 */
@Slf4j
@Configuration
public class FactoryBeanConfig {

    @Bean
    public SimpleInterfaceProxy hello() {
        return new SimpleInterfaceProxy(Hello.class);
    }

    @Bean
    public SimpleInterfaceProxy hi() {
        return new SimpleInterfaceProxy((Hi.class));
    }

}
