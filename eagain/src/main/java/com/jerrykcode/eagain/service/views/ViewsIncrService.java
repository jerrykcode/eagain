package com.jerrykcode.eagain.service.views;

import com.jerrykcode.eagain.enums.DBModelEnum;
import org.springframework.stereotype.Service;

@Service
public interface ViewsIncrService {
    Long increaseViewsCount(DBModelEnum dbModel, String id);

    // 无重复统计阅读量，按用户ID增加阅读数，每个用户只统计一次，使用redis的hyperloglog
    Long increaseViewsCountNoDup(DBModelEnum dbModelEnum, String id, Long userId);
}
