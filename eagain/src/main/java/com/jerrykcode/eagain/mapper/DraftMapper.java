package com.jerrykcode.eagain.mapper;

import com.jerrykcode.eagain.model.Draft;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DraftMapper {

    void create(Draft draft);
    void update(Draft draft);
    Draft queryById(Long id);
    Draft queryByCreatorIdAndRelatedId(Long creatorId, Long relatedId);
    List<Draft> listByCreatorId(Long creatorId);
    void delete(Long id);
}
