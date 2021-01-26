package com.jerrykcode.eagain.util.redis.impl;

import com.jerrykcode.eagain.util.redis.RedisUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUserUtilsImpl implements RedisUserUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Long findIdByUsername(String username) {
        return (Long) redisTemplate.opsForValue().get(RedisConstants.ID_OF_USERNAME + username);
    }

    @Override
    public String findUsernameById(Long userId) {
        return (String) redisTemplate.opsForValue().get(RedisConstants.USERNAME_OF_ID + userId);
    }

    @Override
    public void setIdOfUsername(String username, Long userId) {
        redisTemplate.opsForValue().set(RedisConstants.ID_OF_USERNAME + username, userId);
    }

    @Override
    public void setUsernameOfId(Long userId, String username) {
        redisTemplate.opsForValue().set(RedisConstants.USERNAME_OF_ID + userId, username);
    }

    @Override
    public void setUsernameAndId(String username, Long userId) {
        redisTemplate.opsForValue().set(RedisConstants.ID_OF_USERNAME + username, userId);
        redisTemplate.opsForValue().set(RedisConstants.USERNAME_OF_ID + userId, username);
    }
}
