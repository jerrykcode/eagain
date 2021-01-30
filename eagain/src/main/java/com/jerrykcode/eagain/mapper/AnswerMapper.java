package com.jerrykcode.eagain.mapper;

import com.jerrykcode.eagain.model.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AnswerMapper {
    void create(Answer answer);
}
