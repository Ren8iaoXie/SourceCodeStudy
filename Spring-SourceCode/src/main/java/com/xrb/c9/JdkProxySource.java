package com.xrb.c9;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author xieren8iao
 * @date 2022/9/5 22:02
 */
public class JdkProxySource {


    public static void main(String[] args) throws Throwable {
        $Proxy0 proxy = new $Proxy0(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
                System.out.println("before。。。");
                Object result = method.invoke(new Target(), args);
                System.out.println("after");
                return result;
            }
        });
        System.out.println("proxy.bar() = " + proxy.bar());
        proxy.foo();
    }

    static class Target implements Foo {

        @Override
        public void foo() {
            System.out.println("foo");
        }

        @Override
        public int bar() {
            System.out.println("bar");
            return 100;
        }
    }

    //共同父接口
    public interface Foo {
        void foo() throws Throwable;

        int bar() throws Throwable;
    }
    //具体实现增强的方法接口
//    interface InvocationHandler{
//        Object invoke(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException;
//    }

}