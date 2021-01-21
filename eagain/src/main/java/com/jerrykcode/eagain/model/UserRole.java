package com.jerrykcode.eagain.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class UserRole {
    private Long userId;
    private Integer roleId;
}
