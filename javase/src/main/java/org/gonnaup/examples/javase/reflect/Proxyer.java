package org.gonnaup.examples.javase.reflect;

import java.lang.reflect.Proxy;

/**
 * @author gonnaup
 * @version created at 2021/12/21 12:46
 */
public class Proxyer {
    public static void main(String[] args) {
        SendService proxyInstance = (SendService) Proxy.newProxyInstance(Proxyer.class.getClassLoader(), new Class[]{SendService.class}, new SendHandler(new HelloSendService()));
        proxyInstance.send("this is a hello message");
    }
}
