package org.gonnaup.examples.springs.core.config;

import org.gonnaup.examples.springs.core.lifecycle.PropertySourceLoadedInfo;
import org.gonnaup.examples.springs.core.placeholdconfigurer.CustomPropertySourcesPlaceholderConfigurer;
import org.gonnaup.examples.springs.core.placeholdconfigurer.Information;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

/**
 * @author gonnaup
 * @version created at 2021/8/7 18:38
 */
@Configuration
@PropertySource("classpath:use_propertysource.properties")//使用DefaultPropertySourceFactory加载
public class PropertySourceConfig {

    /**
     * 注入自定义属性配置器
     *
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        CustomPropertySourcesPlaceholderConfigurer placeholderConfigurer = new CustomPropertySourcesPlaceholderConfigurer();
        placeholderConfigurer.setLocation(new ClassPathResource("spring.properties"));//需手动指定文件，当文件被注解@PropertySource指定时将被其覆盖
        return placeholderConfigurer;
    }

    @Bean
    public Information information() {
        return new Information();
    }


    @Bean
    public PropertySourceLoadedInfo propertySourceLoadedInfo() {
        return new PropertySourceLoadedInfo();
    }

}
