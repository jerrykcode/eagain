package com.jerrykcode.eagain.service.views;

import com.jerrykcode.eagain.enums.DBModelEnum;
import com.jerrykcode.eagain.util.redis.impl.RedisConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class ViewsService {

    @Autowired
    private ViewsIncrService viewsIncrService;

    @Autowired
    private ViewsCountService viewsCountService;

    @Autowired
    private DBViewServiceFactory dbViewServiceFactory;

    @Autowired
    private RedisTemplate redisTemplate;

    public Long increaseViewsCount(DBModelEnum dbModelEnum, String id) {
        return viewsIncrService.increaseViewsCount(dbModelEnum, id);
    }

    public Long getViewsCount(DBModelEnum dbModelEnum, String id) {
        return viewsCountService.getViewsCount(dbModelEnum, id);
    }

    // 无重复统计阅读量，按用户ID增加阅读数，每个用户只统计一次，使用redis的hyperloglog
    public Long increaseViewsCountNoDup(DBModelEnum dbModelEnum, String id, Long userId) {
        return viewsIncrService.increaseViewsCountNoDup(dbModelEnum, id, userId);
    }

    // 获取无重复阅读量（每个用户只统计一次）使用redis的hyperloglog
    public Long getViewsCountNoDup(DBModelEnum dbModelEnum, String id) {
        return viewsCountService.getViewsCountNoDup(dbModelEnum, id);
    }

    public static String parseDBModelFromRedisHashKey(Object key) {
        String keyStr = (String) key;
        return keyStr.split(":")[0];
    }

    public static String parseIdFromRedisHashKey(Object key) {
        String keyStr = (String) key;
        return keyStr.split(":")[1];
    }

    public static String getRedisHashKey(DBModelEnum dbModel, String id) {
        if (dbModel.getName().indexOf(':') != -1 || id.indexOf(':') != -1) {
            throw new IllegalArgumentException();
        }
        return dbModel.getName()+":"+id;
    }

    public static String id2RedisHyperloglog(DBModelEnum dbModelEnum, String id) {
        return RedisConstants.VIEWS_HYPERLOGLOG + "-" + getRedisHashKey(dbModelEnum, id);
    }

    @Scheduled(cron = "${spring.time.cron.views}")
    void persistence() {
        Map<String, String> entries = redisTemplate.opsForHash().entries(RedisConstants.VIEWS_HASH);
        if (entries != null && !entries.isEmpty()) {
            entries.keySet().forEach(key->{
                DBViewsService dbViewService = dbViewServiceFactory.getDBViewService(parseDBModelFromRedisHashKey(key));
                String id = parseIdFromRedisHashKey(key);
                dbViewService.updateViewsCount(id, entries.get(key));
            });
            log.info(">>>Persist to Database: views count<<<");
        }
    }

}
