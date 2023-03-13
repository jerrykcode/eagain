package com.jerrykcode.eagain.service.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class HyperloglogService {

    @Autowired
    private RedisTemplate redisTemplate;

    public Long pfadd(String key, String... elements) {
        return redisTemplate.opsForHyperLogLog().add(key, elements);
    }

    public Long pfcount(String... keys) {
        return redisTemplate.opsForHyperLogLog().size(keys);
    }
}
