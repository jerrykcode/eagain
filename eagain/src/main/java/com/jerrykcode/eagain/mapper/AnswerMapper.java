package com.jerrykcode.eagain.mapper;

import com.jerrykcode.eagain.model.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AnswerMapper {
    void create(Answer answer);
    List<Answer> listByQuestionId(@Param("questionId") Long questionId,
                                  @Param("limit")Integer limit,
                                  @Param("offset")Integer offset);
    Answer queryById(Long id);
    void update(Answer answer);
}
