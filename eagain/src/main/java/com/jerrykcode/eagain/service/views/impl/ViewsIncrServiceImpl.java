package com.jerrykcode.eagain.service.views.impl;

import com.jerrykcode.eagain.enums.DBModelEnum;
import com.jerrykcode.eagain.service.cache.CacheServiceTemplate;
import com.jerrykcode.eagain.service.views.DBViewServiceFactory;
import com.jerrykcode.eagain.service.views.DBViewsService;
import com.jerrykcode.eagain.service.views.ViewsIncrService;
import com.jerrykcode.eagain.service.views.ViewsService;
import com.jerrykcode.eagain.util.redis.impl.RedisConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class ViewsIncrServiceImpl extends CacheServiceTemplate implements ViewsIncrService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DBViewServiceFactory dbViewServiceFactory;

    private static final long INVAILD_VALUE = -1;

    @Override
    public Long increaseViewsCount(DBModelEnum dbModel, String id) {
        return (Long) get(ViewsService.getRedisHashKey(dbModel, id));
    }

    @Override
    protected Object getFromCache(Object key) {
        Object o = redisTemplate.opsForHash().get(RedisConstants.VIEWS_HASH, key);
        if (o != null) {
            Long viewCount = Long.valueOf((String) o);
            if (viewCount == INVAILD_VALUE) return viewCount;
            Long increment = redisTemplate.opsForHash().increment(RedisConstants.VIEWS_HASH, key, 1);
            if (increment <= 1) {
                redisTemplate.opsForHash().delete(RedisConstants.VIEWS_HASH, key);
                return null;
            }
            return increment;
        }
        return null;
    }

    @Override
    protected Object getFromMySql(Object key) {
        String dbModelName = ViewsService.parseDBModelFromRedisHashKey(key);
        return dbViewServiceFactory.getDBViewService(dbModelName).getViewsCount(ViewsService.parseIdFromRedisHashKey(key));
    }

    @Override
    protected void storeInCache(Object key, Object value) {
        redisTemplate.opsForHash().putIfAbsent(RedisConstants.VIEWS_HASH, key, "" + value);
    }

    @Override
    protected void storeInCacheWithEmptyValue(Object key) {
        storeInCache(key, "" + INVAILD_VALUE);
    }
}
