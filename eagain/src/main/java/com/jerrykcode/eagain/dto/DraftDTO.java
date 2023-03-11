package com.jerrykcode.eagain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
public class DraftDTO implements Serializable {
    private Long id;
    private Long creatorId;
    private Character type;  // 'A"回答   'Q'提问
    private Long relatedId;  // 若type=A，relatedId为该回答草稿对应的问题ID
    private String content;
    private Long gmtUpdated;
}
