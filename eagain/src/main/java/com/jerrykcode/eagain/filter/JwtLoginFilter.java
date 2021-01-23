package com.jerrykcode.eagain.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerrykcode.eagain.model.UserDetailsImpl;
import com.jerrykcode.eagain.util.JwtUtils;
import com.jerrykcode.eagain.util.RedisSessionUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    /**
     * 获取授权管理
     */
    private AuthenticationManager authenticationManager;
    private RedisSessionUtils redisSessionUtils;

    public JwtLoginFilter(AuthenticationManager authenticationManager, RedisSessionUtils redisSessionUtils) {
        this.authenticationManager = authenticationManager;
        this.redisSessionUtils = redisSessionUtils;
        /**
         *  后端登陆接口
         */
        super.setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) {
        try {
            UserDetailsImpl user = new ObjectMapper()
                    .readValue(req.getInputStream(), UserDetailsImpl.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            logger.error(e.getMessage());
            return  null;
        }
    }

    @Override
    /**
     * 用户登陆成功之后生成Jwt token
     */
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl user = (UserDetailsImpl) authResult.getPrincipal();
        String jwtToken = JwtUtils.generateJwtToken(user);
        redisSessionUtils.addUsername(user.getUsername());
        response.addHeader("token", jwtToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print("账号或者密码错误");
    }
}
