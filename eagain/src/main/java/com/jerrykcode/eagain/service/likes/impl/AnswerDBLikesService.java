package com.jerrykcode.eagain.service.likes.impl;

import com.jerrykcode.eagain.mapper.AnswerMapper;
import com.jerrykcode.eagain.model.Answer;
import com.jerrykcode.eagain.service.likes.DBLikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerDBLikesService implements DBLikesService {

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public Long getLikesCount(String id) {
        Answer answer = answerMapper.queryById(Long.valueOf(id));
        if (answer != null) {
            return Long.valueOf(""+answer.getLikesCount());
        }
        return null;
    }

    @Override
    public void updateAnswersCount(String id, String count) {
        answerMapper.update(new Answer().setId(Long.valueOf(id)).setLikesCount(Integer.valueOf(count)));
    }

}
