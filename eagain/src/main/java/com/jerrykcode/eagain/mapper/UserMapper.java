package com.jerrykcode.eagain.mapper;

import com.jerrykcode.eagain.model.Permission;
import com.jerrykcode.eagain.model.UserDetailsImpl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    UserDetailsImpl findByUsername(@Param("username") String username);

    UserDetailsImpl findById(@Param("id") Long id);

    List<Permission> findPermmissionsByUserId(@Param("userId") Long userId);

    void insert(@Param("user")UserDetailsImpl user);
}
