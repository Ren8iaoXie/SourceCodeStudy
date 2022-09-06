package com.xrb.c1;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author xieren8iao
 * @date 2022/8/30 21:48
 */
@Component
public class Component1 {
    @EventListener()
    public void receiveEvent(UserRegistryEvent obj){
        System.out.println("EventListener receive event= " + obj);
    }
}