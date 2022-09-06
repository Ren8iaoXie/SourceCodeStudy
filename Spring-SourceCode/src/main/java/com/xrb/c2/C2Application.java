package com.xrb.c2;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 常见ApplicationContext四种实现实现
 *
 * @author xieren8iao
 * @date 2022/8/31 19:58
 */
public class C2Application {
    public static void main(String[] args) {
        //从类路径下找到配置文件创建容器
//        testClassPathXmlApplicationContext();
        //从磁盘路径下根据xml配置文件创建容器
//        testFileSystemXmlApplicationContext();
        //使用注解 Java类配置的方式
//        testAnnotationConfigApplicationContext();
        //基于java配置类创建，用于web环境
//        testAnnotationConfigServletWebServerApplicationContext();
    }

    private static void testAnnotationConfigServletWebServerApplicationContext() {
        AnnotationConfigServletWebServerApplicationContext context =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
        seeContextBeanDefinitionNames(context);
    }

    private static void testAnnotationConfigApplicationContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        seeContextBeanDefinitionNames(context);

    }

    private static void testFileSystemXmlApplicationContext() {
        //绝对路径
//        FileSystemXmlApplicationContext context=
//                new FileSystemXmlApplicationContext("//Users/xierenbiao/workspace/IdeaProjects/Spring-SourceCode/src/main/resources/c2.xml");
        //相对路径 需要指定work directory
        FileSystemXmlApplicationContext context=
                new FileSystemXmlApplicationContext("src/main/resources/c2.xml");
        seeContextBeanDefinitionNames(context);
    }

    private static void testClassPathXmlApplicationContext() {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("c2.xml");
        seeContextBeanDefinitionNames(context);
    }

    private static void seeContextBeanDefinitionNames(ApplicationContext context) {
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println("beanDefinitionName = " + beanDefinitionName);
        }
//        System.out.println("context.getBean(Bean2.class).getBean1() = " + context.getBean(Bean2.class).getBean1());
    }

    static class WebConfig {
        @Bean
        public ServletWebServerFactory servletWebServerFactory() {
            return new TomcatServletWebServerFactory();
        }

        @Bean
        public DispatcherServlet dispatcherServlet() {
            return new DispatcherServlet();
        }

        @Bean
        public DispatcherServletRegistrationBean registrationBean(DispatcherServlet servlet) {
            return new DispatcherServletRegistrationBean(servlet, "/");
        }

        @Bean("/hello")
        public org.springframework.web.servlet.mvc.Controller controller() {
            return (request, response) -> {
                response.getWriter().println("hello");
                return null;
            };
        }
    }

    @Configuration
    static class Config{
        @Bean
        public Bean1 bean1(){
            return new Bean1();
        }

        @Bean
        public Bean2 bean2(Bean1 bean1){
            Bean2 bean2 = new Bean2();
            bean2.setBean1(bean1);
            return bean2;
        }
    }
    static class Bean1{

    }
    static class Bean2{
        private Bean1 bean1;

        public void setBean1(Bean1 bean1){
            this.bean1=bean1;
        }
        public Bean1 getBean1(){
            return bean1;
        }
    }

}