package com.jerrykcode.eagain.model;

import com.jerrykcode.eagain.dto.DraftDTO;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class Draft {
    private Long id;
    private Long creatorId;
    private Character type;  // 'A"回答   'Q'提问
    private Long relatedId;  // 若type=A，relatedId为该回答草稿对应的问题ID

    private String content;
    private Long gmtUpdated;

    private static final char ANSWER_DRAFT_TYPE = 'A';
    private static final char QUESTION_DRAFT_TYPE = 'Q';

    public Draft setAnswerDraftType() {
        type = ANSWER_DRAFT_TYPE;
        return this;
    }

    public Draft setQuestionDraftType() {
        type = QUESTION_DRAFT_TYPE;
        return this;
    }

    public boolean isAnswerDraftType() {
        return type == ANSWER_DRAFT_TYPE;
    }

    public boolean isQuestionDraftType() {
        return type == QUESTION_DRAFT_TYPE;
    }

    public DraftDTO toDTO() {
        return new DraftDTO().setId(id).setCreatorId(creatorId).setType(type).setRelatedId(relatedId)
                .setContent(content).setGmtUpdated(gmtUpdated);
    }
}
