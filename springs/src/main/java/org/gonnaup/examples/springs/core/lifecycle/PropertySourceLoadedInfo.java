package org.gonnaup.examples.springs.core.lifecycle;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * 使用{@link org.springframework.context.annotation.PropertySource}注解方式加载properties文件的原理信息
 * 使用{@link org.springframework.core.io.support.DefaultPropertySourceFactory}加载properties文件
 *
 * @author gonnaup
 * @version created at 2021/8/8 8:57
 */
@Data
public class PropertySourceLoadedInfo {

    @Value("${loaderway}")
    private String loaderway;

    @Value("${loaderclass}")
    private String loaderclass;
}
