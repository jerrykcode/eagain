package com.jerrykcode.eagain.mapper;

import com.jerrykcode.eagain.model.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionTagMapper {
    void insert(@Param("questionId")Long questionId, @Param("tagId")Integer tagId);
    List<Tag> listByQuestionId(@Param("questionId") Long questionId);
}
