package com.jerrykcode.eagain.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisSessionUtilsImpl implements RedisSessionUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String KEY_NAME = "login_users";

    @Override
    public void addUsername(String username) {
        redisTemplate.opsForSet().add(KEY_NAME, username);
    }

    @Override
    public boolean usernameExists(String username) {
        return redisTemplate.opsForSet().isMember(KEY_NAME, username);
    }

    @Override
    public void removeUsername(String username) {
        redisTemplate.opsForSet().remove(KEY_NAME, username);
    }
}
