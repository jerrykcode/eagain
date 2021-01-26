package com.jerrykcode.eagain;

import com.jerrykcode.eagain.mapper.UserMapper;
import com.jerrykcode.eagain.model.Permission;
import com.jerrykcode.eagain.model.UserDetailsImpl;
import com.jerrykcode.eagain.util.redis.RedisSessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
class EagainApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RedisSessionUtils redisSessionUtils;

	@Test
	void findByUsername() {
		UserDetailsImpl user = userMapper.findByUsername("test_user");
		log.info(">>user: {}<<",user);
	}

	@Test
	void findPermissionsByUsername() {
		String username = "test_user";
		//查询用户
		UserDetailsImpl userDetails = userMapper.findByUsername(username);
		if (userDetails == null) {
			throw new UsernameNotFoundException("用户" + username + "不存在");
		}
		//查询权限
		List<Permission> permissions = userMapper.findPermmissionsByUserId(userDetails.getId());
		log.info(">>permissionList:{}<<",permissions);
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		permissions.forEach(permission -> {
			grantedAuthorities.add(new SimpleGrantedAuthority(permission.getPermTag()));
		});
		userDetails.setAuthorities(grantedAuthorities);
		log.info(">>user details: {}<<",userDetails);
	}

	@Test
	void redisSession() {
		String username = "Chtholly";
		redisSessionUtils.addUsername(username);
		Assert.isTrue(redisSessionUtils.usernameExists(username), "Assertion Failed");
		redisSessionUtils.removeUsername(username);
		Assert.isTrue( ! redisSessionUtils.usernameExists(username), "Assertion Failed");
	}

}
