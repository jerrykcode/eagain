package com.jerrykcode.eagain.util.redis.impl;

import com.jerrykcode.eagain.util.redis.RedisSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisSessionUtilsImpl implements RedisSessionUtils {

    @Autowired
    private RedisTemplate redisTemplate;



    @Override
    public void addUsername(String username) {
        redisTemplate.opsForSet().add(RedisConstants.LOGIN_USERS, username);
    }

    @Override
    public boolean usernameExists(String username) {
        return redisTemplate.opsForSet().isMember(RedisConstants.LOGIN_USERS, username);
    }

    @Override
    public void removeUsername(String username) {
        redisTemplate.opsForSet().remove(RedisConstants.LOGIN_USERS, username);
    }
}
