package com.jerrykcode.eagain.dto;

import com.jerrykcode.eagain.model.Answer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
public class AnswerDTO implements Serializable {
    private Answer answer;
    private String creatorName;
}
