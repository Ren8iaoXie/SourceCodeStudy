package com.xrb.c4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @author xieren8iao
 * @date 2022/9/1 19:36
 */
public class Bean1 {
    private String javaVersion;

    @Autowired
    public void Bean2(Bean2 bean2) {
        System.out.println("@Autowired<<<<bean2");
    }

    @Resource
    public void Bean3(Bean3 bean2) {
        System.out.println("@Resource<<<<bean3");
    }

    @Autowired
    public void setJavaVersion(@Value("${java.version}") String javaVersion) {
        System.out.println("java_version<<<<" + javaVersion);
        this.javaVersion = javaVersion;
    }
    @PostConstruct
    public void postConstruct(){
        System.out.println("@PostConstruct<<<");
    }
    @PreDestroy
    public void preDestroy(){
        System.out.println("@PreDestroy<<<");
    }
}