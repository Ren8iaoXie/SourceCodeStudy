package com.xrb.c7.aop;

import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author xieren8iao
 * @date 2022/9/5 20:04
 */
@org.aspectj.lang.annotation.Aspect
public class Aspect {

    @Before("execution(* com.xrb.c7.servcie.MyService.foo())")
    public void before(){
        System.out.println("before...");
    }
}