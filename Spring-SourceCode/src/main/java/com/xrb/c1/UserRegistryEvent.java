package com.xrb.c1;

import org.springframework.context.ApplicationEvent;

/**
 * @author xieren8iao
 * @date 2022/8/30 22:08
 */
public class UserRegistryEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent} with its {@link #getTimestamp() timestamp}
     * set to {@link System#currentTimeMillis()}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     * @see #ApplicationEvent(Object, Clock)
     */
    public UserRegistryEvent(Object source) {
        super(source);
    }
}