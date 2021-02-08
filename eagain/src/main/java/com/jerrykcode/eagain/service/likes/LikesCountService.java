package com.jerrykcode.eagain.service.likes;

import com.jerrykcode.eagain.enums.DBModelEnum;
import org.springframework.stereotype.Service;

@Service
public interface LikesCountService {
    Long getLikesCount(DBModelEnum dbModelEnum, String id);
}
