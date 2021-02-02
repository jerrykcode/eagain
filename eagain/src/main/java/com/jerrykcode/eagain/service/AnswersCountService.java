package com.jerrykcode.eagain.service;

import org.springframework.stereotype.Service;

@Service
public interface AnswersCountService {
    Long increaseAnswersCount(Long questionId);
    Long getAnswersCount(Long questionId);
}
