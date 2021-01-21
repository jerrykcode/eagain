package com.jerrykcode.eagain.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
public class RegisterRequest implements Serializable {
    private String username;
    private String email;
    private String code; //验证码
    private String password;
}
