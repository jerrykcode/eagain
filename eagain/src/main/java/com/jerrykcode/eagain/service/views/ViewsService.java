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
