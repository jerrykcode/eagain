package com.jerrykcode.eagain.enums;

public enum RegisterErrorEnum {
    ERROR_USERNAME_EXISTS("该用户名已存在，换个试试~"),
    ERROR_INVALID_CODE("验证码错误!"),
    ERROR_INVALID_PARAM("参数错误");

    private String errorMessage;
    RegisterErrorEnum(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
