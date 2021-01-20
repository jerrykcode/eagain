package com.jerrykcode.eagain.mapper;

import com.jerrykcode.eagain.model.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PermissionMapper {
    List<Permission> listAll();
}
