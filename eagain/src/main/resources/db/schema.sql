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
-- 以上为权限相关的表

-- -----------------------------------
-- 问题表
-- ------------------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    creator_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    content VARCHAR(1024) NOT NULL,
    gmt_create BIGINT,
    gmt_modified BIGINT,
    answers_count INTEGER DEFAULT 0,
    views_count INTEGER DEFAULT 0,
    likes_count INTEGER DEFAULT 0,
    focuses_count INTEGER DEFAULT 1
);

-- -----------------------------------
-- 标签表
-- -----------------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(50) NOT NULL,
    title VARCHAR(50) NOT NULL,
    color VARCHAR(50) NOT NULL
);

-- -----------------------------------
-- 问题-标签表
-- -----------------------------------
DROP TABLE IF EXISTS `question_tag`;
CREATE TABLE `question_tag` (
   question_id BIGINT NOT NULL,
   tag_id INTEGER NOT NULL
);

-- -----------------------------------
-- 回答表
-- -----------------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer` (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    question_id BIGINT NOT NULL,
    question_title VARCHAR(100) NOT NULL,
    creator_id BIGINT NOT NULL,
    content VARCHAR(1024) NOT NULL,
    gmt_create BIGINT,
    gmt_modified BIGINT,
    views_count INTEGER DEFAULT 0,
    likes_count INTEGER DEFAULT 0,
    collections_count INTEGER DEFAULT 0
);
CREATE INDEX question_id_index on answer(question_id);