package com.jerrykcode.eagain.controller;

import com.jerrykcode.eagain.enums.RoleEnum;
import com.jerrykcode.eagain.mapper.UserMapper;
import com.jerrykcode.eagain.mapper.UserRoleMapper;
import com.jerrykcode.eagain.model.UserDetailsImpl;
import com.jerrykcode.eagain.model.UserRole;
import com.jerrykcode.eagain.request.RegisterRequest;
import com.jerrykcode.eagain.response.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        RegisterResponse response = new RegisterResponse();
        if (userMapper.findByUsername(request.getUsername()) != null) {
            response.setSuccess(false);
            response.setUsernameExists(true);
        }
        else {
            response.setUsernameExists(false);
            UserDetailsImpl user = new UserDetailsImpl();
            user.setUsername(request.getUsername())
                    .setEmail(request.getEmail())
                    .setPassword(passwordEncoder.encode(request.getPassword()))
                    .setNickname(request.getUsername()) //昵称暂时与用户名相同
                    .setGmtCreate(System.currentTimeMillis())
                    .setGmtLastLogin(System.currentTimeMillis())
                    .setAccountOpts(UserDetailsImpl.ACCOUNT_DEFAULT_SETTING)
                    .setAvatarUrl("");
            userMapper.insert(user);
            UserRole userRole = new UserRole();
            userRole.setUserId(userMapper.findByUsername(user.getUsername()).getId())
                    .setRoleId(RoleEnum.ROLE_USER.getRoleId());
            userRoleMapper.insert(userRole);
            response.setSuccess(true);
        }
        return response;
    }
}
