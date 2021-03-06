package com.jerrykcode.eagain.service.views;

import com.jerrykcode.eagain.enums.DBModelEnum;
import org.springframework.stereotype.Service;

@Service
public interface ViewsCountService {
    Long getViewsCount(DBModelEnum dbModel, String id);
}
