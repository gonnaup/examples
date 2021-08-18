package org.gonnaup.examples.springs.db.mybatis;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.gonnaup.examples.springs.db.DatasourceProperty;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * mybatis spring 配置类
 *
 * @author gonnaup
 * @version created at 2021/8/18 13:37
 * @see MapperScan
 * @see org.mybatis.spring.mapper.MapperScannerConfigurer
 */
@Configuration
@ComponentScan(basePackageClasses = MybatisConfig.class)
@PropertySource("classpath:mysql.properties")
@EnableTransactionManagement
@MapperScan(basePackageClasses = MybatisConfig.class, annotationClass = Repository.class)
public class MybatisConfig {

    @Bean
    public DatasourceProperty datasourceProperty() {
        return new DatasourceProperty();
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        DatasourceProperty property = datasourceProperty();
        hikariDataSource.setJdbcUrl(property.getUrl());
        hikariDataSource.setDriverClassName(property.getDrivername());
        hikariDataSource.setUsername(property.getUsername());
        hikariDataSource.setPassword(property.getPassword());
        return hikariDataSource;
    }

    @Bean
    public TransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public SqlSessionFactory sessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatisConfig.xml"));
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }
}
