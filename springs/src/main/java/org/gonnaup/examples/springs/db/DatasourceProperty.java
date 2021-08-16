package org.gonnaup.examples.springs.db;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * 数据源属性
 * @author gonnaup
 * @version created at 2021/8/16 16:06
 */
@Data
public class DatasourceProperty {

    @Value("${datasoruce.drivername}")
    private String drivername;

    @Value("${datasource.url}")
    private String url;

    @Value("${datasource.username}")
    private String username;

    @Value("${datasource.password}")
    private String password;
}
