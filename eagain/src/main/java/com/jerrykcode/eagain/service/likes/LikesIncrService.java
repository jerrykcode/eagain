package com.jerrykcode.eagain.service.likes;

import com.jerrykcode.eagain.enums.DBModelEnum;
import org.springframework.stereotype.Service;

@Service
public interface LikesIncrService {
    Long increaseLikesCount(DBModelEnum dbModelEnum, String id);
}
