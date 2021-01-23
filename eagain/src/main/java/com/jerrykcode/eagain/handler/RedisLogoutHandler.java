package com.jerrykcode.eagain.handler;

import com.jerrykcode.eagain.model.UserDetailsImpl;
import com.jerrykcode.eagain.util.JwtUtils;
import com.jerrykcode.eagain.util.RedisSessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class RedisLogoutHandler implements LogoutHandler {

    @Autowired
    private RedisSessionUtils redisSessionUtils;

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        String token = httpServletRequest.getHeader("token"); //jwt token
        if (StringUtils.isEmpty(token)) {
            return;
        }
        String username = JwtUtils.getUserName(token);
        if (StringUtils.isEmpty(username)) {
            return;
        }
        log.info(">>>{} log out !<<<", username);
        redisSessionUtils.removeUsername(username);
    }
}
