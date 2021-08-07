package org.gonnaup.examples.springs.core.placeholdconfigurer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;

/**
 * 自定义属性处理器
 *
 * @author gonnaup
 * @version created at 2021/8/7 18:23
 */
@Slf4j
public class CustomPropertySourcesPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer {

    @Override
    public String convertProperty(String propertyName, String propertyValue) {
        String PASSWORD_KEY = "password";
        if (PASSWORD_KEY.equals(propertyName) && StringUtils.hasLength(propertyValue)) {
            String realValue = new String(Base64.getDecoder().decode(propertyValue.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            log.info("解密password[{}]的值 => [{}]", propertyValue, realValue);
            return realValue;
        }
        return super.convertProperty(propertyName, propertyValue);
    }

    /**
     * 重写mergeProperties方法，在merge后添加转换方法
     * org.springframework.context.support.PropertySourcesPlaceholderConfigurer#postProcessBeanFactory方法中获取property只调用了mergeProperties方法
     * 导致转换方法无法使用，手动添加转换converttProperties方法
     *
     * @return
     * @throws IOException
     */
    @Override
    protected Properties mergeProperties() throws IOException {
        Properties properties = super.mergeProperties();
        convertProperties(properties);
        return properties;
    }
}
