package com.jerrykcode.eagain.service;

import com.jerrykcode.eagain.dto.QuestionDTO;
import com.jerrykcode.eagain.dto.page.QuestionPage;
import com.jerrykcode.eagain.model.Question;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {
    Long create(QuestionDTO questionDTO);
    Question queryById(Long questionId);
    QuestionPage getQuestionPage(Long questionId);
}
