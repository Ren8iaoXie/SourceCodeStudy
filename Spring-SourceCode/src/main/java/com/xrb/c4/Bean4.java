package com.xrb.c4;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xieren8iao
 * @date 2022/9/1 19:50
 */
@ConfigurationProperties(prefix = "java")
@Data
public class Bean4 {

    private String home;

    private String version;

    public void output(){
        System.out.println("home = " + home);
        System.out.println("version = " + version);
    }
}