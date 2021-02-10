package com.jerrykcode.eagain.dto;

import com.jerrykcode.eagain.model.Notification;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
public class NotificationDTO implements Serializable {
    private Long senderId;
    private String senderName;
    private String notificationType;
    private String dbModelType;
    private Long modelId;
    private String modelContent;
}
