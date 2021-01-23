package org.gonnaup.examples.mybatisplus;

import org.gonnaup.examples.mybatisplus.entity.User;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * 获取实现函数的方法信息，需函数接口实现{@link Serializable}接口
 * @author gonnaup
 * @version 2021/1/23 11:58
 */
public class LambdaMagic {

    public static <T, R> SerializedLambda serializedLambda(CFunction<T, R> func) {
        try {
            Method method = func.getClass().getDeclaredMethod("writeReplace");//需要函数实现 Serializable接口才有此方法
            method.setAccessible(true);
            SerializedLambda invoke = (SerializedLambda) method.invoke(func);
            System.out.printf("函数实现类：%s%n", invoke.getImplClass());
            System.out.printf("函数实现方法：%s%n", invoke.getImplMethodName());
            return invoke;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        LambdaMagic.serializedLambda(Object::toString);
        LambdaMagic.serializedLambda(User::getName);
    }
}

/**
 * 能被序列化的函数，
 * @param <T>
 * @param <R>
 */
@FunctionalInterface
interface CFunction<T, R> extends Function<T, R>, Serializable {
}
