package com.jerrykcode.eagain.dto.page;

import com.jerrykcode.eagain.model.Tag;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Accessors(chain = true)
@Data
public class QuestionPage implements Serializable {
    private String creatorName;
    private String title;
    private String content;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer viewsCount;
    private Integer likesCount;
    private Integer focusesCount;
    private List<Tag> tags;
}
