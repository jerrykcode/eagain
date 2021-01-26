package com.jerrykcode.eagain.mapper;

import com.jerrykcode.eagain.model.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TagMapper {
    List<Tag> listAll();
}
