package com.xrb.c10;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * @author xieren8iao
 * @date 2022/9/6 20:39
 */
public class C10 {
    static interface I{
        void foo();
        void bar();
    }
    static class Target implements I{

        @Override
        public void foo() {
            System.out.println("foo");
        }

        @Override
        public void bar() {
            System.out.println("bar");
        }
    }

    /**
     * {@link org.springframework.aop.framework.ProxyConfig#proxyTargetClass}
     * @param args
     */
    public static void main(String[] args) {
        //1.切点
        AspectJExpressionPointcut pointcut=new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* foo())");
        //2.通知
        MethodInterceptor advice = methodInvocation -> {
            System.out.println("before...");
            Object result = methodInvocation.proceed();
            System.out.println("after...");
            return  result;
        };
        //3.切面
        DefaultPointcutAdvisor advisor=new DefaultPointcutAdvisor(pointcut,advice);

        //4.创建代理
        /**
         *  {@link org.springframework.aop.framework.ProxyConfig#proxyTargetClass}
         * a. proxyTargetclass = false, 目标实现了接口，用 jdk 实现
         * b. proxyTargetclass = false, 目标没有实现接口 cglib 实现
         * c. proxyTargetclass = true, 总是使用 cglib 实现
         */

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new Target());
        proxyFactory.addAdvisor(advisor);
        I proxy = (I) proxyFactory.getProxy();
        System.out.println(proxy.getClass());
        proxy.bar();
        proxy.foo();

    }
}