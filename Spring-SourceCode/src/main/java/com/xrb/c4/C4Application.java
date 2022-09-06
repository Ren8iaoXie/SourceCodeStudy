package com.xrb.c4;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author xieren8iao
 * @date 2022/9/1 19:31
 */
public class C4Application {
    public static void main(String[] args) {
        //干净的容器
        GenericApplicationContext context=new GenericApplicationContext();
        context.registerBean("bean1",Bean1.class);
        context.registerBean("bean2",Bean2.class);
        context.registerBean("bean3",Bean3.class);

        context.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);

        context.registerBean(CommonAnnotationBeanPostProcessor.class);

        context.registerBean("bean4",Bean4.class);
        ConfigurationPropertiesBindingPostProcessor.register(context.getDefaultListableBeanFactory());
        DefaultListableBeanFactory beanFactory = context.getDefaultListableBeanFactory();
//        beanFactory.doResolveDependency()
        context.refresh();
        System.out.println("context.getBean(\"bean4\") = " + context.getBean("bean4"));

        context.close();

    }
}