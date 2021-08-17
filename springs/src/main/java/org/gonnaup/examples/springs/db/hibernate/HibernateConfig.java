package org.gonnaup.examples.springs.db.hibernate;

import com.zaxxer.hikari.HikariDataSource;
import org.gonnaup.examples.springs.db.DatasourceProperty;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @author gonnaup
 * @version created at 2021/8/16 22:41
 */
@Configuration
@ComponentScan(basePackageClasses = HibernateConfig.class)
@PropertySource("classpath:mysql.properties")
@EnableTransactionManagement(proxyTargetClass = true)
public class HibernateConfig {

    @Bean
    public DatasourceProperty datasourceProperty() {
        return new DatasourceProperty();
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        DatasourceProperty property = datasourceProperty();
        hikariDataSource.setDriverClassName(property.getDrivername());
        hikariDataSource.setJdbcUrl(property.getUrl());
        hikariDataSource.setUsername(property.getUsername());
        hikariDataSource.setPassword(property.getPassword());
        return hikariDataSource;
    }

    @Bean
    public SessionFactory sessionFactory() throws IOException {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan(Student.class.getPackageName());
        Properties properties = new Properties();
        properties.load(new ClassPathResource("hibernate.properties").getInputStream());
        sessionFactoryBean.setHibernateProperties(properties);
        sessionFactoryBean.afterPropertiesSet();
        return sessionFactoryBean.getObject();
    }

    @Bean
    public TransactionManager transactionManager() throws IOException {
        return new HibernateTransactionManager(sessionFactory());
    }

    @Bean
    public HibernateTemplate hibernateTemplate() throws IOException {
        return new HibernateTemplate(sessionFactory());
    }

}
