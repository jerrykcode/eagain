package com.jerrykcode.eagain.mapper;

import com.jerrykcode.eagain.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface QuestionMapper {
    void create(Question question);
}
