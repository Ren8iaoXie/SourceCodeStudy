package com.xrb.c8;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author xieren8iao
 * @date 2022/9/5 20:51
 */
public class CglibProxyDemo {
    static class Target {

        public void foo() {
            System.out.println("foo");
        }
    }
    public static void main(String[] args) {
        Target target = new Target();
        Target proxy = (Target) Enhancer.create(Target.class, (MethodInterceptor) (o, method, objects, methodProxy) -> {
            System.out.println("before");
            Object result = method.invoke(target, args);

            System.out.println("after...");
            return result;
        });
        proxy.foo();
    }
}