package com.xrb.c3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author xieren8iao
 * @date 2022/8/31 20:42
 */
@Component
public class LifeCircleBean {
    public LifeCircleBean(){
        System.out.println("LifeCircle构造...");
    }
//    @Value("${classpath}")
    private String javaHome;

    @PostConstruct
    public void init(){
        System.out.println("init初始化...");
    }
    @PreDestroy
    public void destroy(){
        System.out.println("依赖注入："+javaHome);

        System.out.println("destroy销毁...");
    }
}