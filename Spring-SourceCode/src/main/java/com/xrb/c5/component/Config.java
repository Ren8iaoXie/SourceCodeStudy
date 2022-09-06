package com.xrb.c5.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author xieren8iao
 * @date 2022/9/1 20:44
 */
@Configuration
@ComponentScan("com.xrb.c5.component")
public class Config {
    @Bean
    public Bean1 bean1() {
        System.out.println("<<<Config bean1");
        return new Bean1();
    }
}