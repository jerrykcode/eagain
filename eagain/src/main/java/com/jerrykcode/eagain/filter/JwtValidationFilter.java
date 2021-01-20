package com.jerrykcode.eagain.filter;

import com.jerrykcode.eagain.util.JwtUtils;
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
import java.util.List;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
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
        String username = JwtUtils.getUserName(token);
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        List<SimpleGrantedAuthority> userRoleList = JwtUtils.getUserRole(token);
        return new UsernamePasswordAuthenticationToken(username, null, userRoleList);
    }
}
