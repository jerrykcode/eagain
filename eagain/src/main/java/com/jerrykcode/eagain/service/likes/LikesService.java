package com.jerrykcode.eagain.service.likes;

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
public class LikesService {

    @Autowired
    private LikesCountService likesCountService;

    @Autowired
    private LikesIncrService likesIncrService;

    @Autowired
    private DBLikesServiceFactory dbLikesServiceFactory;

    @Autowired
    private RedisTemplate redisTemplate;

    public static String getRedisHashKey(DBModelEnum dbModelEnum, String id) {
        if (dbModelEnum.getName().indexOf(':') != -1 || id.indexOf(':') != -1) {
            throw new IllegalArgumentException();
        }
        return dbModelEnum.getName() + ":" + id;
    }

    public static String parseDBModelFromRedisHashKey(Object key) {
        String keyStr = (String) key;
        return keyStr.split(":")[0];
    }

    public static String parseIdFromRedisHashKey(Object key) {
        String keyStr = (String) key;
        return keyStr.split(":")[1];
    }

    public Long getLikesCount(DBModelEnum dbModelEnum, String id) {
        return likesCountService.getLikesCount(dbModelEnum, id);
    }

    public Long increaseLikesCount(String dbModelName, String id) {
        DBModelEnum dbModelEnum = null;
        if (dbModelName.equals(DBModelEnum.DB_QUESTION.getName()))
            dbModelEnum = DBModelEnum.DB_QUESTION;
        else if (dbModelName.equals(DBModelEnum.DB_ANSWER.getName()))
            dbModelEnum = DBModelEnum.DB_ANSWER;
        if (dbModelEnum != null)
            return likesIncrService.increaseLikesCount(dbModelEnum, id);
        else
            return null;
    }

    @Scheduled(cron = "${spring.time.cron.likes}")
    void persistence() {
        Map entries = redisTemplate.opsForHash().entries(RedisConstants.ANSWERS_HASH);
        entries.keySet().forEach(key->{
            DBLikesService dbLikesService = dbLikesServiceFactory.getDBLikesService(parseDBModelFromRedisHashKey(key));
            dbLikesService.updateAnswersCount(parseIdFromRedisHashKey(key), (String) entries.get(key));
        });
        log.info(">>>Persist to Database: likes count<<<");
    }
}
