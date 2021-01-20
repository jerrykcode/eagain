package com.jerrykcode.eagain.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
public class Permission implements Serializable {
    private Integer id;
    private String permName;
    private String permTag;
    private String url;
}
