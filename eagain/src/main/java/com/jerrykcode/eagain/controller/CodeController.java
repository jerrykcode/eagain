package com.jerrykcode.eagain.controller;

import com.jerrykcode.eagain.request.SendCodeRequest;
import com.jerrykcode.eagain.response.BaseResponse;
import com.jerrykcode.eagain.service.MailService;
import com.jerrykcode.eagain.util.redis.RedisMailCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;

@RestController
public class CodeController {

    @Autowired
    private MailService mailService;

    @Autowired
    private RedisMailCodeUtils redisMailCodeUtils;

    private static final int CODE_LEN = 6;
    private static final String NUMBERS = "0123456789";

    @PostMapping("/sendCode")
    public BaseResponse sendCode(@RequestBody SendCodeRequest sendCodeRequest) {
        String code = "";
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < CODE_LEN; i++) {
            code += NUMBERS.charAt(random.nextInt(10));
        }
        String message = "您正在注册EAGAIN 验证码 " + code + " 有效期5分钟";
        if (mailService.sendMail(sendCodeRequest.getEmail(), "EAGAIN邮箱验证码", message)) {
            redisMailCodeUtils.setMailAndCode(sendCodeRequest.getEmail(), code);
            return new BaseResponse().setSuccess(true);
        }
        else
            return new BaseResponse().setSuccess(false);
    }
}
