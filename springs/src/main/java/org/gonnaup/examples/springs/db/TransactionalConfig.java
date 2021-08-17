package org.gonnaup.examples.springs.db;

import com.zaxxer.hikari.HikariDataSource;
import org.gonnaup.examples.springs.db.hibernate.HibernateConfig;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * jdbc事务配置
 *
 * @author gonnaup
 * @version created at 2021/8/16 15:55
 */
@Configuration
@PropertySource("classpath:mysql.properties")
@EnableTransactionManagement//开启注解事务
@ComponentScan(basePackageClasses = TransactionalConfig.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = HibernateConfig.class))
public class TransactionalConfig {

    @Bean
    public DatasourceProperty datasourceProperty() {
        return new DatasourceProperty();
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        DatasourceProperty property = datasourceProperty();
        hikariDataSource.setJdbcUrl(property.getUrl());
        hikariDataSource.setUsername(property.getUsername());
        hikariDataSource.setPassword(property.getPassword());
        hikariDataSource.setDriverClassName(property.getDrivername());
        return hikariDataSource;
    }

    @Bean
    public TransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }


}
