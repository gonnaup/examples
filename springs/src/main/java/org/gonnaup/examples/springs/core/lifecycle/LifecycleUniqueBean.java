package org.gonnaup.examples.springs.core.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Closeable;

/**
 * 生命周期唯一示例bean
 *
 * <p>
 *     全局周期
 *     <ul>
 *         <li>{@link org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor}</li>
 *     </ul>
 *     单bean周期，按执行顺序排序
 *     <ul>
 *         <li>{@link org.springframework.beans.factory.BeanNameAware}</li>
 *         <li>{@link org.springframework.beans.factory.BeanFactoryAware}</li>
 *         <li>{@link org.springframework.context.ApplicationContextAware}</li>
 *         <li>{@link javax.annotation.PostConstruct}</li>
 *         <li>{@link org.springframework.beans.factory.InitializingBean}</li>
 *         <li>xml配置的init-method属性方法</li>
 *         <li>{@link javax.annotation.PreDestroy}</li>
 *         <li>{@link org.springframework.beans.factory.DisposableBean}</li>
 *         <li>xml配置的destroy-method属性方法</li>
 *     </ul>
 *
 * </p>
 * @author gonnaup
 * @version created at 2021/8/6 17:11
 */
@Slf4j
@Service
public class LifecycleUniqueBean implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean, DisposableBean, Closeable {

    private ConfigurableApplicationContext applicationContext;

    public LifecycleUniqueBean() {
        log.info("================================>> 构造方法，实例化类 {}", this.getClass().getName());
    }

    @Override
    public void setBeanName(String name) {
        log.info("======================>> BeanNameAware#setBeanName [{}] <<===================", name);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        log.info("======================>> BeanFactoryAware#setBeanFactory [{}] <<====================", beanFactory.toString());
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }

    @PostConstruct
    public void postConstruct() {
        log.info("==================>> 带@postConstruct方法 <<=====================");
    }

    @Override
    public void afterPropertiesSet() {
        log.info("==========================>> InitializingBean#afterPropertiesSet <<=========================");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("==================>> 带@PreDestroy方法 <<========================");
    }

    @Override
    public void destroy() {
        log.info("=========================>> DisposableBean#destroy <<======================");
    }

    @Override
    public void close() {
        applicationContext.close();
        log.info("=========================>> close applicationContext <<====================");
    }

    public void message() {
        log.info("============ message ===========");
        log.info("|=========== message ==========|");
        log.info("|=========== message ==========|");
        log.info("|=========== message ==========|");
        log.info("================================");
    }
}
