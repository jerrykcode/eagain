package com.jerrykcode.eagain.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
public class Tag implements Serializable {
    private Integer id;
    private String type;
    private String title;
    private String color;
}
