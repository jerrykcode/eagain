package com.jerrykcode.eagain.util;

import org.springframework.stereotype.Component;

@Component
public interface RedisMailCodeUtils {
    void setMailAndCode(String email, String code);
    boolean verifyMailAndCode(String email, String code);
}
