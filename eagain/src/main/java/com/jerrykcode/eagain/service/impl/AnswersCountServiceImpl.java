package com.jerrykcode.eagain.service.impl;

import com.jerrykcode.eagain.service.AnswersCountService;
import com.jerrykcode.eagain.util.redis.RedisUtils;
import com.jerrykcode.eagain.util.redis.impl.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswersCountServiceImpl implements AnswersCountService {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Long increaseAnswersCount(Long questionId) {
        return redisUtils.incr(RedisConstants.ANSWERS_COUNT + questionId);
    }

    @Override
    public Long getAnswersCount(Long questionId) {
        Object count = redisUtils.get(RedisConstants.ANSWERS_COUNT + questionId);
        if (count == null)
            return new Long(0);
        return (Long) count;
    }
}
