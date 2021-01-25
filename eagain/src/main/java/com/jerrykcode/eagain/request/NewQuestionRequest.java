package com.jerrykcode.eagain.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class NewQuestionRequest {
    private Long creatorId;
    private String title;
    private String content;
}
