package com.jerrykcode.eagain.service.cache.impl;

import com.jerrykcode.eagain.mapper.UserMapper;
import com.jerrykcode.eagain.model.UserDetailsImpl;
import com.jerrykcode.eagain.service.cache.CacheServiceTemplate;
import com.jerrykcode.eagain.util.redis.RedisUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Username2IdService extends CacheServiceTemplate {

    @Autowired
    private RedisUserUtils redisUserUtils;

    @Autowired
    private UserMapper userMapper;

    public Long username2Id(String username) {
        return (Long) get(username);
    }

    @Override
    protected Object getFromCache(Object key) {
        return redisUserUtils.findIdByUsername((String) key);
    }

    @Override
    protected Object getFromMySql(Object key) {
        UserDetailsImpl user = userMapper.findByUsername((String) key);
        if (user != null)
            return user.getId();
        return null;
    }

    @Override
    protected void storeInCache(Object key, Object value) {
        redisUserUtils.setIdOfUsername((String) key, (Long) value);
    }

    @Override
    protected void storeInCacheWithEmptyValue(Object key) {
        Long emptyId = Long.valueOf("0");
        redisUserUtils.setIdOfUsername((String) key, emptyId);
    }
}