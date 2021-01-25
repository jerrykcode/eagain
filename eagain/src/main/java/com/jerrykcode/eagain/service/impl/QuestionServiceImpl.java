package com.jerrykcode.eagain.service.impl;

import com.jerrykcode.eagain.mapper.QuestionMapper;
import com.jerrykcode.eagain.model.Question;
import com.jerrykcode.eagain.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public Long create(Question question) {
        questionMapper.create(question);
        return question.getId();
    }
}
