package com.jerrykcode.eagain.model;

import com.jerrykcode.eagain.enums.DBModelEnum;
import com.jerrykcode.eagain.enums.NotificationTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
public class Notification implements Serializable {
    private Long senderId;
    private Long receiverId;
    private NotificationTypeEnum notificationTypeEnum;
    private DBModelEnum dbModelEnum;
    private Long modelId;
    private Boolean read;
}
