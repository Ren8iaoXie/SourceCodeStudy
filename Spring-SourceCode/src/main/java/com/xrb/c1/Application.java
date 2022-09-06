package com.xrb.c1;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author xieren8iao
 * @date 2022/8/30 20:33
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class);
        System.out.println("Application runs....");
        System.out.println("configurableApplicationContext = " + context);


        getSingletonObjectsByReflection(context);
        //classpath* 代表在jar下找
        resources(context);
        //环境
        enviroment(context);
        context.publishEvent(new UserRegistryEvent(context));
    }

    private static void enviroment(ConfigurableApplicationContext context) {
        System.out.println("\n");
        System.out.println("context.getEnvironment().getProperty(\"java_home\") = " + context.getEnvironment().getProperty("JAVA_HOME"));
        System.out.println("context.getEnvironment().getProperty(\"server.port\") = " + context.getEnvironment().getProperty("server.port"));
    }

    private static void resources(ConfigurableApplicationContext context) throws IOException {
        Resource[] resources = context.getResources("classpath*:META-INF/spring.factories");
        for (Resource resource : resources) {
            System.out.println(resource);
        }
    }

    private static void getSingletonObjectsByReflection(ConfigurableApplicationContext context) throws NoSuchFieldException, IllegalAccessException {
        System.out.println("\n");
        Field singletonObjects = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
        singletonObjects.setAccessible(true);
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Map<String, Object> map = (Map) singletonObjects.get(beanFactory);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey()+"--->"+entry.getValue());
        }
        map.entrySet().stream().filter(e -> e.getKey().startsWith("component"))
                .forEach((k) -> System.out.println(k.getKey() + "=" + k.getValue()));
    }
}