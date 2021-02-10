package com.jerrykcode.eagain.enums;

import java.io.Serializable;

public enum NotificationTypeEnum implements Serializable {

    NOTIFICATION_TYPE_LIKE("like"),
    NOTIFICATION_TYPE_COMMENT("comment");

    private String name;
    NotificationTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
