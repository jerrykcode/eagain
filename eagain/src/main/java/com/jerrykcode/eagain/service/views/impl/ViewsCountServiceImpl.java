package com.jerrykcode.eagain.service.views.impl;

import com.jerrykcode.eagain.enums.DBModelEnum;
import com.jerrykcode.eagain.service.cache.CacheServiceTemplate;
import com.jerrykcode.eagain.service.views.DBViewServiceFactory;
import com.jerrykcode.eagain.service.views.DBViewsService;
import com.jerrykcode.eagain.service.views.ViewsCountService;
import com.jerrykcode.eagain.util.redis.impl.RedisConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class ViewsCountServiceImpl extends CacheServiceTemplate implements ViewsCountService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DBViewServiceFactory dbViewServiceFactory;

    private static final long INVAILD_VALUE = -1;

    private String getRedisHashKey(DBModelEnum dbModel, String id) {
        if (dbModel.getName().indexOf(':') != -1 || id.indexOf(':') != -1) {
            throw new IllegalArgumentException();
        }
        return dbModel.getName()+":"+id;
    }

    private String parseDBModelFromRedisHashKey(Object key) {
        String keyStr = (String) key;
        return keyStr.split(":")[0];
    }

    private String parseIdFromRedisHashKey(Object key) {
        String keyStr = (String) key;
        return keyStr.split(":")[1];
    }

    @Override
    public Long increaseViewsCount(DBModelEnum dbModel, String id) {
        return (Long) get(getRedisHashKey(dbModel, id));
    }

    @Scheduled(cron = "${spring.time.cron}")
    void persistence() {
        Map<String, String> entries = redisTemplate.opsForHash().entries(RedisConstants.VIEWS_HASH);
        if (entries != null && !entries.isEmpty()) {
            entries.keySet().forEach(key->{
                DBViewsService dbViewService = dbViewServiceFactory.getDBViewService(parseDBModelFromRedisHashKey(key));
                String id = parseIdFromRedisHashKey(key);
                dbViewService.updateViewsCount(id, entries.get(key));
            });
            log.info(">>>Persist to Database<<<");
        }
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
        String dbModelName = parseDBModelFromRedisHashKey(key);
        return dbViewServiceFactory.getDBViewService(dbModelName).getViewsCount(parseIdFromRedisHashKey(key));
    }

    @Override
    protected void storeInCache(Object key, Object value) {
        //缓存中没有，把查询数据库的结果写入缓存
        //但是在写入缓存之前，可能有其他线程已经查询数据库并写入缓存了
        //使用hsetnx操作，缓存中没有才put，并且判断和put一起作为一个原子操作，相当于乐观锁
        redisTemplate.opsForHash().putIfAbsent(RedisConstants.VIEWS_HASH, key, "" + value);
    }

    @Override
    protected void storeInCacheWithEmptyValue(Object key) {
        storeInCache(key, "" + INVAILD_VALUE);
    }
}
