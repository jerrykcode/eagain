package com.jerrykcode.eagain.service.likes;

import org.springframework.stereotype.Service;

@Service
public interface DBLikesService {
    Long getLikesCount(String id);
    void updateAnswersCount(String id, String count);
}
