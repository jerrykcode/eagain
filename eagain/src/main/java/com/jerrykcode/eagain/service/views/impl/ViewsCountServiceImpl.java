package com.jerrykcode.eagain.service.views.impl;

import com.jerrykcode.eagain.enums.DBModelEnum;
import com.jerrykcode.eagain.service.cache.CacheServiceTemplate;
import com.jerrykcode.eagain.service.views.DBViewServiceFactory;
import com.jerrykcode.eagain.service.views.DBViewsService;
import com.jerrykcode.eagain.service.views.ViewsCountService;
import com.jerrykcode.eagain.service.views.ViewsService;
import com.jerrykcode.eagain.util.redis.impl.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ViewsCountServiceImpl extends CacheServiceTemplate implements ViewsCountService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DBViewServiceFactory dbViewServiceFactory;

    @Override
    public Long getViewsCount(DBModelEnum dbModel, String id) {
        return Long.valueOf(""+get(ViewsService.getRedisHashKey(dbModel, id)));
    }

    @Override
    protected Object getFromCache(Object key) {
       return redisTemplate.opsForHash().get(RedisConstants.VIEWS_HASH, key);
    }

    @Override
    protected Object getFromMySql(Object key) {
        DBViewsService dbViewService = dbViewServiceFactory.getDBViewService(ViewsService.parseDBModelFromRedisHashKey(key));
        return dbViewService.getViewsCount(ViewsService.parseIdFromRedisHashKey(key));
    }

    @Override
    protected void storeInCache(Object key, Object value) {
        redisTemplate.opsForHash().putIfAbsent(RedisConstants.VIEWS_HASH, key, ""+value);
    }

    @Override
    protected void storeInCacheWithEmptyValue(Object key) {
        storeInCache(key, -1);
    }
}
