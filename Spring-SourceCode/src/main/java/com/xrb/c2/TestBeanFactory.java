package com.xrb.c2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author xieren8iao
 * @date 2022/8/31 19:22
 */
public class TestBeanFactory {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory=new DefaultListableBeanFactory();
        //构造bean定义信息
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(Config.class);
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.setScope("singleton").getBeanDefinition();
        beanFactory.registerBeanDefinition("config",beanDefinition);
        //给beanFactory添加一些常用的后置处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);
        //获取beanFactory后置处理器并执行
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        beanFactoryPostProcessorMap.values().forEach(beanFactoryPostProcessor -> {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });
        outputBeanDefinitionNames(beanFactory);

        //bean后置处理器
        beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(beanFactory::addBeanPostProcessor);
        //提前构造单例bean 就不必等到需要用时再创建
        beanFactory.preInstantiateSingletons();
        System.out.println("=======>");
        System.out.println("beanFactory.getBean(Bean1.class).getBean2() = " + beanFactory.getBean(Bean1.class).getBean2());
    }

    private static void outputBeanDefinitionNames(DefaultListableBeanFactory beanFactory) {

        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println("beanDefinitionName = " + beanDefinitionName);
        }
    }

    @Configuration
    static class Config{
        @Bean
        public Bean1 bean1(){
            return new Bean1();
        }
        @Bean
        public Bean2 bean2(){
            return new Bean2();
        }


    }


    static class Bean1{
        public Bean1(){
            System.out.println("Bean1 construct...");
        }
        @Autowired
        private Bean2 bean2;

        public Bean2 getBean2(){
            return bean2;
        }
    }
    static class Bean2{
        public Bean2(){
            System.out.println("Bean2 construct...");
        }
    }
}