package com.jerrykcode.eagain.service.likes.impl;

import com.jerrykcode.eagain.enums.DBModelEnum;
import com.jerrykcode.eagain.service.cache.CacheServiceTemplate;
import com.jerrykcode.eagain.service.likes.DBLikesService;
import com.jerrykcode.eagain.service.likes.DBLikesServiceFactory;
import com.jerrykcode.eagain.service.likes.LikesIncrService;
import com.jerrykcode.eagain.service.likes.LikesService;
import com.jerrykcode.eagain.util.redis.impl.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class LikesIncrServiceImpl extends CacheServiceTemplate implements LikesIncrService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DBLikesServiceFactory dbLikesServiceFactory;

    private static final long INVALID_VAL = -1;

    @Override
    public Long increaseLikesCount(DBModelEnum dbModelEnum, String id) {
        return (Long) get(LikesService.getRedisHashKey(dbModelEnum, id));
    }

    @Override
    protected Object getFromCache(Object key) {
        Object o = redisTemplate.opsForHash().get(RedisConstants.ANSWERS_HASH, key);
        if (o != null) {
            if (Long.valueOf((String) o) == INVALID_VAL)
                return INVALID_VAL;
            Long increment = redisTemplate.opsForHash().increment(RedisConstants.ANSWERS_HASH, key, 1);
            if (increment <= 1) {
                redisTemplate.opsForHash().delete(RedisConstants.ANSWERS_HASH, key);
                return null;
            }
            return increment;
        }
        return null;
    }

    @Override
    protected Object getFromMySql(Object key) {
        DBLikesService dbLikesService = dbLikesServiceFactory.getDBLikesService(LikesService.parseDBModelFromRedisHashKey(key));
        Long likesCount = dbLikesService.getLikesCount(LikesService.parseIdFromRedisHashKey(key));
        if (likesCount != null)
            return likesCount + 1;
        else return null;
    }

    @Override
    protected void storeInCache(Object key, Object value) {
        redisTemplate.opsForHash().putIfAbsent(RedisConstants.ANSWERS_HASH, key, "" + value);
    }

    @Override
    protected void storeInCacheWithEmptyValue(Object key) {
        storeInCache(key, INVALID_VAL);
    }
}
