package com.jerrykcode.eagain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface QuestionTagMapper {
    void insert(@Param("questionId")Long questionId, @Param("tagId")Integer tagId);
}
