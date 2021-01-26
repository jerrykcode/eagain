package com.jerrykcode.eagain.util.redis;

import org.springframework.stereotype.Component;

@Component
public interface RedisUserUtils {
    Long findIdByUsername(String username);
    String findUsernameById(Long userId);
    void setIdOfUsername(String username, Long userId);
    void setUsernameOfId(Long userId, String username);
    void setUsernameAndId(String username, Long userId);
}
