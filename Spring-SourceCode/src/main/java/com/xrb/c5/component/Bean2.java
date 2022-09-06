package com.xrb.c5.component;

import org.springframework.stereotype.Component;

/**
 * @author xieren8iao
 * @date 2022/9/1 20:43
 */
@Component
public class Bean2 {
    public Bean2() {
        System.out.println(this + "<<<被构造啦...");
    }
}