-- -----------------------------------
-- 用户表
-- -----------------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, -- 用户唯一标识 用于与其他表关联
    username VARCHAR(50) NOT NULL UNIQUE, -- 用户名 用于登录
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,

    nickname VARCHAR(50) NULL,
    gmt_create BIGINT,
    gmt_last_login BIGINT,
    account_opts INTEGER,
    avatar_url VARCHAR(256) NULL
);
CREATE INDEX username_index on sys_user(username);

-- -----------------------------------
-- 角色表
-- -----------------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
    id INTEGER NOT NULL PRIMARY KEY,
    role_name varchar(50) DEFAULT NULL,
    role_desc varchar(50) DEFAULT NULL
);

-- -----------------------------------
-- 权限表
-- -----------------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
    id INTEGER NOT NULL PRIMARY KEY,
    perm_name varchar(50) DEFAULT NULL,
    perm_tag varchar(50) DEFAULT NULL,
    url varchar(255) DEFAULT NULL -- '请求url'
);

-- -----------------------------------
-- 用户-角色表
-- -----------------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
    user_id BIGINT NOT NULL PRIMARY KEY,
    role_id INTEGER NOT NULL
);

-- -----------------------------------
-- 角色-权限表
-- -----------------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
    role_id INTEGER NOT NULL,
    perm_id INTEGER NOT NULL
);