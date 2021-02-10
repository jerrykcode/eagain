package com.jerrykcode.eagain.mapper;

import com.jerrykcode.eagain.model.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NotificationMapper {
    List<Notification> list(@Param("receiverId") Long receiverId,
                            @Param("limit")Integer limit,
                            @Param("offset")Integer offset);

    void insert(Notification notification);
}
