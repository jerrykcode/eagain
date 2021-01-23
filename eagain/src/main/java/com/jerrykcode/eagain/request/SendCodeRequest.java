package com.jerrykcode.eagain.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class SendCodeRequest {
    private String email;
}
