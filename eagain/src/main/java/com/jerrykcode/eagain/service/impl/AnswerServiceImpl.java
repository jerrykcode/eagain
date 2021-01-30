package com.jerrykcode.eagain.service.impl;

import com.jerrykcode.eagain.mapper.AnswerMapper;
import com.jerrykcode.eagain.model.Answer;
import com.jerrykcode.eagain.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public void create(Answer answer) {
        answerMapper.create(answer);
    }

    @Override
    public List<Answer> listByQuestionId(Long questionId, Integer pageNo, Integer numPerPage) {
        Integer offset = (pageNo - 1) * numPerPage;
        return answerMapper.listByQuestionId(questionId, numPerPage, offset);
    }
}
