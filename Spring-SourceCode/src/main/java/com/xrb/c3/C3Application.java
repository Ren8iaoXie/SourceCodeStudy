package com.xrb.c3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author xieren8iao
 * @date 2022/8/31 20:41
 */
@SpringBootApplication
public class C3Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(C3Application.class);
        context.close();
    }
}