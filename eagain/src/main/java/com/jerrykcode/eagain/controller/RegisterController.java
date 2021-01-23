package com.jerrykcode.eagain.controller;

import com.jerrykcode.eagain.enums.RegisterErrorEnum;
import com.jerrykcode.eagain.enums.RoleEnum;
import com.jerrykcode.eagain.mapper.UserMapper;
import com.jerrykcode.eagain.mapper.UserRoleMapper;
import com.jerrykcode.eagain.model.UserDetailsImpl;
import com.jerrykcode.eagain.model.UserRole;
import com.jerrykcode.eagain.request.RegisterRequest;
import com.jerrykcode.eagain.response.RegisterResponse;
import com.jerrykcode.eagain.util.RedisMailCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
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

    @Autowired
    private RedisMailCodeUtils redisMailCodeUtils;

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        RegisterResponse response = new RegisterResponse();
        if (StringUtils.isEmpty(request.getUsername())
                || StringUtils.isEmpty(request.getEmail())
                || StringUtils.isEmpty(request.getPassword())) {
            //这个分支不应该被执行
            //前端应该判断出这种错误~
            response.setSuccess(false);
            response.setErrorMessage(RegisterErrorEnum.ERROR_INVALID_PARAM.getErrorMessage());
        }
        else if (!redisMailCodeUtils.verifyMailAndCode(request.getEmail(), request.getCode())) {
            response.setSuccess(false);
            response.setErrorMessage(RegisterErrorEnum.ERROR_INVALID_CODE.getErrorMessage());
        }
        else if (userMapper.findByUsername(request.getUsername()) != null) {
            response.setSuccess(false);
            response.setErrorMessage(RegisterErrorEnum.ERROR_USERNAME_EXISTS.getErrorMessage());
        }
        else {
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
