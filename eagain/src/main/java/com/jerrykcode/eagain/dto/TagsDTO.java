package com.jerrykcode.eagain.dto;

import com.jerrykcode.eagain.model.Tag;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Accessors(chain = true)
@Data
public class TagsDTO implements Serializable {
    //同一类型(type)的tag
    private String type;
    private List<Tag> tags;
}