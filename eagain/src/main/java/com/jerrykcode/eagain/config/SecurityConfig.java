package com.jerrykcode.eagain.config;

import com.jerrykcode.eagain.filter.JwtLoginFilter;
import com.jerrykcode.eagain.filter.JwtValidationFilter;
import com.jerrykcode.eagain.mapper.PermissionMapper;
import com.jerrykcode.eagain.model.Permission;
import com.jerrykcode.eagain.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 设置用户账号信息和权限
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }


    /**
     * 配置HttpSecurity 拦截资源
     *
     * @param http
     * @throws Exception
     */
    protected void configure(HttpSecurity http) throws Exception {

        List<Permission> permissions = permissionMapper.listAll();
        permissions.forEach(permission -> {
            try {
                http.authorizeRequests()
                        .antMatchers(permission.getUrl())
                        .hasAnyAuthority(permission.getPermTag());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/**")
                .fullyAuthenticated()
                .and()
                .addFilter(new JwtValidationFilter(authenticationManager()))
                .addFilter(new JwtLoginFilter(authenticationManager())).csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
