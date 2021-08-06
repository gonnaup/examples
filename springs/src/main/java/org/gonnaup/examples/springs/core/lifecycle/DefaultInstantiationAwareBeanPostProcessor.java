package org.gonnaup.examples.springs.core.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 全局生命周期处理器
 *
 * @author gonnaup
 * @version created at 2021/8/6 18:42
 */
@Slf4j
@Component
public class DefaultInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    //实例化前处理方法
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) {
        log.info("=======================>> postProcessBeforeInstantiation 实例化前处理方法 beanClass {}, beanName {}", beanClass.getSimpleName(), beanName);
        return null;//使用默认值
    }

    /**
     * 实例化后处理方法
     *
     * @return <code>true</code>设置bean的属性，<code>false</code>不设置bean属性
     * @throws BeansException
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) {
        log.info("=======================>> postProcessAfterInstantiation 实例化后处理方法 bean {}, beanName {}", bean.toString(), beanName);
        return InstantiationAwareBeanPostProcessor.super.postProcessAfterInstantiation(bean, beanName);
    }

    /**
     * 初始化前处理器
     *
     * @return
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        log.info("=======================>> postProcessBeforeInitialization 初始化前处理方法 bean {}, beanName {}", bean.toString(), beanName);
        return bean;
    }

    /**
     *  默认调用InstantiationAwareBeanPostProcessor#postProcessPropertyValues(PropertyValues, PropertyDescriptor[], Object, String)
     */
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) {
        return InstantiationAwareBeanPostProcessor.super.postProcessProperties(pvs, bean, beanName);
    }

    /**
     * 初始化后处理器
     *
     * @return
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        log.info("=======================>> postProcessAfterInitialization 初始化后处理方法 bean {}, beanName {}", bean.toString(), beanName);
        return InstantiationAwareBeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
