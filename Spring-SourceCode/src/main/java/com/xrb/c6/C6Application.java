package com.xrb.c6;

import org.springframework.context.support.GenericApplicationContext;

/**
 * @author xieren8iao
 * @date 2022/9/3 08:03
 */
public class C6Application {
    public static void main(String[] args) {
        GenericApplicationContext context=new GenericApplicationContext();
        context.registerBean("myBean",MyBean.class);
        context.refresh();
    }
}