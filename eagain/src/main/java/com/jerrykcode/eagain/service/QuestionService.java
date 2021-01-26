package com.jerrykcode.eagain.service;

import com.jerrykcode.eagain.dto.QuestionDTO;
import com.jerrykcode.eagain.dto.page.QuestionPage;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {
    Long create(QuestionDTO questionDTO);
    QuestionPage queryById(Long questionId);
}
