package com.jerrykcode.eagain.service;

import com.jerrykcode.eagain.model.Question;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {
    Long create(Question question);
}
