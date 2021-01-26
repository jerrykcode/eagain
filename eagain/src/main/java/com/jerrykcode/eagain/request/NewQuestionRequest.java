package com.jerrykcode.eagain.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
public class NewQuestionRequest implements Serializable {
    private Long creatorId;
    private String title;
    private String content;
    private Integer[] tagIds;
}
