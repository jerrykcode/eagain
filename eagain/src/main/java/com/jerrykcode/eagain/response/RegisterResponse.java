package com.jerrykcode.eagain.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
public class RegisterResponse implements Serializable {
    private Boolean success;
    private Boolean usernameExists;
}
