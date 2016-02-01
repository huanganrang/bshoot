package jb.service;

import component.redis.model.CounterType;

/**
 * Created by john on 16/2/1.
 */
public interface CounterHandler {
    CounterType getCounterType();
    void handle(String message);
}
