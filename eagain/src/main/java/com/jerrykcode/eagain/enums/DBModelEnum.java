package com.jerrykcode.eagain.enums;

import java.io.Serializable;

public enum DBModelEnum implements Serializable {
    DB_QUESTION("question"), DB_ANSWER("answer");

    private String name;
    public String getName() {
        return name;
    }
    DBModelEnum(String name) {
        this.name = name;
    }
}
