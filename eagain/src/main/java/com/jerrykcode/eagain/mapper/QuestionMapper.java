package com.jerrykcode.eagain.mapper;

import com.jerrykcode.eagain.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {
    void create(Question question);
    Question queryById(@Param("id") Long id);
    List<Question> list(@Param("limit") Integer limit, @Param("offset") Integer offset);
    Long countAll();
    void update(Question question);
    void incrAnswersCount(@Param("questionId") Long questionId, @Param("increment") Integer increment);
}
