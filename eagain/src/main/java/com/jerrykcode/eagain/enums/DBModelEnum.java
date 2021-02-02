package com.jerrykcode.eagain.enums;

public enum DBModelEnum {
    DB_QUESTION("question"), DB_ANSWER("answer");

    private String name;
    public String getName() {
        return name;
    }
    DBModelEnum(String name) {
        this.name = name;
    }
}
