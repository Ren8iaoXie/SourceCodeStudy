package com.xrb.c8;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author xieren8iao
 * @date 2022/9/5 20:43
 */

public class JdkProxyDemo {
    static interface Foo{
        void foo();
    }

    static class Target implements Foo{

        @Override
        public void foo() {
            System.out.println("foo");
        }
    }

    //jdk代理 只能针对接口
    public static void main(String[] args) {
        Target target = new Target();

        Foo proxy = (Foo) Proxy.newProxyInstance(JdkProxyDemo.class.getClassLoader(), new Class[]{Foo.class}, (p, method, args1) -> {
            System.out.println("before...");
            Object result = method.invoke(target, args);
            System.out.println("after");
            return result;
        });
        proxy.foo();
    }

}