package com.jerrykcode.eagain.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
public class NewAnswerRequest implements Serializable {
    private Long questionId;
    private String questionTitle;
    private Long creatorId;
    private String content;
}
