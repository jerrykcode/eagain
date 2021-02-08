package com.jerrykcode.eagain.service.likes;

import org.springframework.stereotype.Component;

@Component
public interface DBLikesServiceFactory {
    DBLikesService getDBLikesService(String dbModelName);
}
