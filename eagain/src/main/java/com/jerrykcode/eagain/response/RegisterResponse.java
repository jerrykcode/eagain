package com.jerrykcode.eagain.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
public class RegisterResponse extends BaseResponse implements Serializable {
    private String errorMessage;
}
