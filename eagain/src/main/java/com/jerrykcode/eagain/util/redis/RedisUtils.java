package com.jerrykcode.eagain.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    public void set(Object key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object get(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Long incr(Object key) {
        return redisTemplate.opsForValue().increment(key);
    }
}
