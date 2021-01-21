package com.jerrykcode.eagain.enums;

public enum RoleEnum {
    ROLE_ADMIN(1), ROLE_USER(2);

    private Integer roleId;
    RoleEnum(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRoleId() {
        return roleId;
    }
}
