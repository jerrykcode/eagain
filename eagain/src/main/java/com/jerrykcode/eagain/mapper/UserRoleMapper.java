package com.jerrykcode.eagain.mapper;

import com.jerrykcode.eagain.model.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserRoleMapper {
    void insert(@Param("userRole")UserRole userRole);
}
