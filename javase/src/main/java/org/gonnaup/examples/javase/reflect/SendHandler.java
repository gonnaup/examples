package org.gonnaup.examples.javase.reflect;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author gonnaup
 * @version created at 2021/12/21 12:42
 */
@Slf4j
public class SendHandler implements InvocationHandler {

    private Object target;

    public SendHandler(Object object) {
        this.target = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("begin send the message...");
        Object result = method.invoke(target, args);
        log.info("after send the message...");
        return result;
    }
}
