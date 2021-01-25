package com.jerrykcode.eagain.service.impl;

import com.jerrykcode.eagain.dto.QuestionDTO;
import com.jerrykcode.eagain.mapper.QuestionMapper;
import com.jerrykcode.eagain.mapper.QuestionTagMapper;
import com.jerrykcode.eagain.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionTagMapper questionTagMapper;

    @Override
    public Long create(QuestionDTO questionDTO) {
        questionMapper.create(questionDTO.getQuestion());
        Long questionId = questionDTO.getQuestion().getId();
        questionDTO.getTagIds().forEach(tagId->{
            questionTagMapper.insert(questionId, tagId);
        });
        return questionId;
    }
}
