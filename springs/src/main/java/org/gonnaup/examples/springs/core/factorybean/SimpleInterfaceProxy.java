package org.gonnaup.examples.springs.core.factorybean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.ClassUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * {@link FactoryBean} 接口示例，并结合jdk动态代理实现接口的动态实现
 *
 * @author gonnaup
 * @version created at 2021/8/7 15:06
 */
@Slf4j
public class SimpleInterfaceProxy implements FactoryBean<Object>, InvocationHandler {

    private final Class<?> interfacetype;

    public SimpleInterfaceProxy(Class<?> type) {
        this.interfacetype = type;
    }

    @Override
    @SuppressWarnings("all")
    public Object invoke(Object proxy, Method method, Object[] args) {
        log.info("begin the {}#{}", interfacetype.getName(), method.getName());
        if (args != null && args.length == 1) {
            log.info("动态代理的方法执行逻辑，你好 {}", args[0]);
        } else {
            log.info("动态代理的方法执行逻辑，你好");
        }
        log.info("end the invoke");
        return null;
    }

    @Override
    public Object getObject() {
        log.info("动态生成代理接口 =>> {}", interfacetype.getName());
        return Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(), new Class[]{interfacetype}, this);
    }

    @Override
    public Class<?> getObjectType() {
        return interfacetype;
    }

}
