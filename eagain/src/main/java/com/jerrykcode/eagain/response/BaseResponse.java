package com.jerrykcode.eagain.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class BaseResponse {
    protected Boolean success;
}
