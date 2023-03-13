package com.jerrykcode.eagain.service.views;

import com.jerrykcode.eagain.enums.DBModelEnum;
import org.springframework.stereotype.Service;

@Service
public interface ViewsCountService {
    Long getViewsCount(DBModelEnum dbModel, String id);

    // 获取无重复阅读量（每个用户只统计一次）使用redis的hyperloglog
    Long getViewsCountNoDup(DBModelEnum dbModelEnum, String id);
}
