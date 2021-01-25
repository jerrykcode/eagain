package com.jerrykcode.eagain.dto;

import com.jerrykcode.eagain.model.Question;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class QuestionDTO {
    private Question question;
    private List<Integer> tagIds;
}
