package com.jerrykcode.eagain.service.views.impl;

import com.jerrykcode.eagain.mapper.AnswerMapper;
import com.jerrykcode.eagain.model.Answer;
import com.jerrykcode.eagain.service.views.DBViewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerDBViewsService implements DBViewsService {

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public Long getViewsCount(String id) {
        Answer answer = answerMapper.queryById(Long.valueOf(id));
        if (answer == null)
            return null;
        return Long.valueOf("" + (answer.getViewsCount() + 1));
    }

    @Override
    public void updateViewsCount(String id, String count) {
        answerMapper.update(new Answer().setId(Long.valueOf(id)).setViewsCount(Integer.valueOf(count)));
    }
}
