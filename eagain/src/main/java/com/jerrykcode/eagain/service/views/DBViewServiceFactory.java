package com.jerrykcode.eagain.service.views;

import org.springframework.stereotype.Component;

@Component
public interface DBViewServiceFactory {
    DBViewsService getDBViewService(String modelName);
}
