package com.jerrykcode.eagain.util.redis;

import org.springframework.stereotype.Component;

@Component
public interface RedisSessionUtils {
    void addUsername(String username);
    boolean usernameExists(String username);
    void removeUsername(String username);
}
