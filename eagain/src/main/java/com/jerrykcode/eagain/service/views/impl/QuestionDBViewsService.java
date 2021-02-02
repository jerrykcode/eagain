package com.jerrykcode.eagain.service.views.impl;

import com.jerrykcode.eagain.mapper.QuestionMapper;
import com.jerrykcode.eagain.model.Question;
import com.jerrykcode.eagain.service.views.DBViewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionDBViewsService implements DBViewsService {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public Long getViewsCount(String id) {
        Question question = questionMapper.queryById(Long.valueOf(id));
        if (question == null)
            return null;
        int viewCount = question.getViewsCount() + 1;
        return Long.valueOf("" + viewCount);
    }

    @Override
    public void updateViewsCount(String id, String count) {
        questionMapper.update(new Question().setId(Long.valueOf(id)).setViewsCount(Integer.valueOf(count)));
    }
}
