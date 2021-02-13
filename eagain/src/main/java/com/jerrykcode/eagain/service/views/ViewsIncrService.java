package com.jerrykcode.eagain.service.views;

import com.jerrykcode.eagain.enums.DBModelEnum;
import org.springframework.stereotype.Service;

@Service
public interface ViewsIncrService {
    Long increaseViewsCount(DBModelEnum dbModel, String id);
}
