package com.jerrykcode.eagain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
public class HomePageQuestionDTO implements Serializable {
    public static final int BREVIARY_SIZE = 100;
    private Long questionId;
    private String creatorName;
    private String title;
    private String breviary;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer answersCount;
    private Integer viewsCount;
    private Integer likesCount;
    private Integer focusesCount;
}
