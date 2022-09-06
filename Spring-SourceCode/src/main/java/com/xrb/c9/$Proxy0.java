package com.xrb.c9;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author xieren8iao
 * @date 2022/9/5 22:05
 */
public class $Proxy0 extends Proxy implements JdkProxySource.Foo {
//    private JdkProxySource.InvocationHandler h;
    static Method foo=null;
    static Method bar=null;
    static {
        try {
            foo = JdkProxySource.Foo.class.getDeclaredMethod("foo");
            bar = JdkProxySource.Foo.class.getDeclaredMethod("bar");
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodError(e.getMessage());
        }

    }
    public $Proxy0(InvocationHandler h) {
//        this.h = h;
        super(h);
    }

    @Override
    public void foo() throws Throwable {
        h.invoke(this,foo, new Object[0]);
    }

    @Override
    public int bar() throws Throwable {
        return (int) h.invoke(this,bar, new Object[0]);
    }
}