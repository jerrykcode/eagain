package com.jerrykcode.eagain.util.redis.impl;

import com.jerrykcode.eagain.util.redis.RedisMailCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisMailCodeUtilsImpl implements RedisMailCodeUtils {

    @Autowired
    private RedisTemplate redisTemplate;


    private static final int EXPIRE_SECONDS = 60*5; //5分钟

    @Override
    public void setMailAndCode(String email, String code) {
        redisTemplate.opsForValue().set(RedisConstants.KEY_PREFIX+email, code, EXPIRE_SECONDS, TimeUnit.SECONDS);
    }

    @Override
    public boolean verifyMailAndCode(String email, String code) {
        if (redisTemplate.hasKey(RedisConstants.KEY_PREFIX+email)) {
            return redisTemplate.opsForValue().get(RedisConstants.KEY_PREFIX+email).equals(code);
        }
        return false;
    }
}
