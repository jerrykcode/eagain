package com.jerrykcode.eagain.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class Question {
    private Long id;
    private Long creatorId;
    private String title;
    private String content;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer viewsCount;
    private Integer likesCount;
    private Integer focusesCount;
}
