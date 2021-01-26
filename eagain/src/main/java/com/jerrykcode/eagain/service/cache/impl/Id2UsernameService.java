package com.jerrykcode.eagain.service.cache.impl;

import com.jerrykcode.eagain.mapper.UserMapper;
import com.jerrykcode.eagain.model.UserDetailsImpl;
import com.jerrykcode.eagain.service.cache.CacheService;
import com.jerrykcode.eagain.util.redis.RedisUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Id2UsernameService extends CacheService {

    @Autowired
    private RedisUserUtils redisUserUtils;

    @Autowired
    private UserMapper userMapper;

    public String id2Username(Long id) {
        return (String) get(id);
    }

    @Override
    protected Object getFromCache(Object key) {
        return redisUserUtils.findUsernameById((Long) key);
    }

    @Override
    protected Object getFromMySql(Object key) {
        UserDetailsImpl user = userMapper.findById((Long) key);
        if (user != null) {
            return user.getUsername();
        }
        return null;
    }

    @Override
    protected void storeInCache(Object key, Object value) {
        redisUserUtils.setUsernameOfId((Long) key, (String) value);
    }

    @Override
    protected void storeInCacheWithEmptyValue(Object key) {
        redisUserUtils.setUsernameOfId((Long) key, "");
    }
}
