package com.jerrykcode.eagain.filter;

import com.jerrykcode.eagain.util.JwtUtils;
import com.jerrykcode.eagain.util.RedisSessionUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    private RedisSessionUtils redisSessionUtils;

    public JwtValidationFilter(AuthenticationManager authenticationManager, RedisSessionUtils redisSessionUtils) {
        super(authenticationManager);
        this.redisSessionUtils = redisSessionUtils;
    }

    /**
     * 过滤请求验证
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(setAuthentication(request.getHeader("token")));
        super.doFilterInternal(request, response, chain);
    }

    /**
     * 验证token 并且验证权限
     * @param token
     * @return
     */
    private UsernamePasswordAuthenticationToken setAuthentication(String token) {
        if (StringUtils.isEmpty(token)) {
            //没有token 就没有任何权限(没有登录就是这种情况)
            //返回空ArrayList
            //只能访问不需要任何权限的接口(SecurityConfig中permitAll的接口)
            return new UsernamePasswordAuthenticationToken(null, null, new ArrayList<SimpleGrantedAuthority>());
        }
        String username = JwtUtils.getUserName(token);
        if (StringUtils.isEmpty(username))
            return null;
        if ( ! redisSessionUtils.usernameExists(username)) {
            //redis中没有username, 说明该用户已经退出了，相当于没有登录
            return new UsernamePasswordAuthenticationToken(null, null, new ArrayList<SimpleGrantedAuthority>());
        }
        List<SimpleGrantedAuthority> userRoleList = JwtUtils.getUserRole(token);
        return new UsernamePasswordAuthenticationToken(username, null, userRoleList);
    }
}
