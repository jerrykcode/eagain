package com.jerrykcode.eagain.service;

import com.jerrykcode.eagain.model.Answer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnswerService {
    void create(Answer answer);
    Answer queryById(Long answerId);
    List<Answer> listByQuestionId(Long questionId, Integer pageNo, Integer numPerPage);
}
