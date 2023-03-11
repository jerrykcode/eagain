package com.jerrykcode.eagain.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class SaveAnswerDraftRequest {
    private Long creatorId;
    private Long questionId;
    private String content;
}
