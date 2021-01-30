package com.jerrykcode.eagain.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
public class Answer implements Serializable {
    private Long id;
    private Long questionId;
    private String questionTitle;
    private Long creatorId;
    private String content;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer viewsCount;
    private Integer likesCount;
    private Integer collectionsCount;
}
