package com.jerrykcode.eagain.service.likes.impl;

import com.jerrykcode.eagain.mapper.QuestionMapper;
import com.jerrykcode.eagain.model.Question;
import com.jerrykcode.eagain.service.likes.DBLikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionDBLikesService implements DBLikesService {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public Long getLikesCount(String id) {
        Question question = questionMapper.queryById(Long.valueOf(id));
        if (question != null) {
            return Long.valueOf(""+question.getLikesCount());
        }
        return null;
    }

    @Override
    public void updateAnswersCount(String id, String count) {
        questionMapper.update(new Question().setId(Long.valueOf(id)).setLikesCount(Integer.valueOf(count)));
    }
}
