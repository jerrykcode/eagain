package com.jerrykcode.eagain.service.views;

import org.springframework.stereotype.Service;

@Service
public interface DBViewsService {
    Long getViewsCount(String id);
    void updateViewsCount(String id, String count);
}
