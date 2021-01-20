package com.jerrykcode.eagain.service;

import com.jerrykcode.eagain.mapper.UserMapper;
import com.jerrykcode.eagain.model.Permission;
import com.jerrykcode.eagain.model.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户
        UserDetailsImpl userDetails = userMapper.findByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("用户" + username + "不存在");
        }
        //查询权限
        List<Permission> permissions = userMapper.findPermmissionsByUserId(userDetails.getId());
        log.info(">>permissionList:{}<<",permissions);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        permissions.forEach(permission -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(permission.getPermTag()));
        });
        userDetails.setAuthorities(grantedAuthorities);
        return userDetails;
    }
}
