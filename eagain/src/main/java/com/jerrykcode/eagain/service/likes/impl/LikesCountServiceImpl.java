package com.jerrykcode.eagain.service.likes.impl;

import com.jerrykcode.eagain.enums.DBModelEnum;
import com.jerrykcode.eagain.service.cache.CacheServiceTemplate;
import com.jerrykcode.eagain.service.likes.DBLikesService;
import com.jerrykcode.eagain.service.likes.DBLikesServiceFactory;
import com.jerrykcode.eagain.service.likes.LikesCountService;
import com.jerrykcode.eagain.service.likes.LikesService;
import com.jerrykcode.eagain.util.redis.impl.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class LikesCountServiceImpl extends CacheServiceTemplate implements LikesCountService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DBLikesServiceFactory dbLikesServiceFactory;

    @Override
    public Long getLikesCount(DBModelEnum dbModelEnum, String id) {
        return Long.valueOf("" + get(LikesService.getRedisHashKey(dbModelEnum, id)));
    }

    @Override
    protected Object getFromCache(Object key) {
        Object o = redisTemplate.opsForHash().get(RedisConstants.ANSWERS_HASH, key);
        return o;
    }

    @Override
    protected Object getFromMySql(Object key) {
        DBLikesService dbLikesService = dbLikesServiceFactory.getDBLikesService(LikesService.parseDBModelFromRedisHashKey(key));
        return dbLikesService.getLikesCount(LikesService.parseIdFromRedisHashKey(key));
    }

    @Override
    protected void storeInCache(Object key, Object value) {
        redisTemplate.opsForHash().putIfAbsent(RedisConstants.ANSWERS_HASH, key, "" + value);
    }

    @Override
    protected void storeInCacheWithEmptyValue(Object key) {
        storeInCache(key, -1);
    }
}
