INSERT INTO sys_permission(id, perm_name, perm_tag, url) values(1, '测试权限1', '测试权限1', '/test1');
INSERT INTO sys_permission(id, perm_name, perm_tag, url) values(2, '测试权限2', '测试权限2', '/test2');
INSERT INTO sys_permission(id, perm_name, perm_tag, url) values (3, '新建问题', '新建问题', '/questions/new');

INSERT INTO sys_role(id, role_name, role_desc) values(1, 'admin', '管理员');
INSERT INTO sys_role(id, role_name, role_desc) values(2, 'user', '普通用户');

INSERT INTO sys_role_permission(role_id, perm_id) values(1, 1);
INSERT INTO sys_role_permission(role_id, perm_id) values(1, 2);
INSERT INTO sys_role_permission(role_id, perm_id) values(1, 3);
INSERT INTO sys_role_permission(role_id, perm_id) values(2, 1);
INSERT INTO sys_role_permission(role_id, perm_id) values(2, 3);

INSERT INTO sys_user(username, email, password, account_opts) values ('test_user', 'xx@xx.com', '$2y$12$Jif94d9mBJg8pA33niCo8erqW5G3BF4Jo2OTyCErvgbnuX3kVp.6m', 15); -- 密码: test123
INSERT INTO sys_user_role(user_id, role_id) values (1, 2);

-- 标签
INSERT INTO tag(type, title, color) values ('开发语言', 'java', '#FAA500');
INSERT INTO tag(type, title, color) values ('开发语言', 'javascript', '#FFFF00');
INSERT INTO tag(type, title, color) values ('开发语言', 'c', '#001950');
INSERT INTO tag(type, title, color) values ('开发语言', 'c++', '#DF1493');
INSERT INTO tag(type, title, color) values ('开发语言', 'python', '#4169E1');
INSERT INTO tag(type, title, color) values ('开发语言', 'css', '#4B0082');
INSERT INTO tag(type, title, color) values ('开发语言', 'html', '#FF1493');
INSERT INTO tag(type, title, color) values ('开发语言', 'html5', '#FD1493');
INSERT INTO tag(type, title, color) values ('开发语言', 'php', '#7B68EE');
INSERT INTO tag(type, title, color) values ('开发语言', 'c#', '#32CD32');
INSERT INTO tag(type, title, color) values ('开发语言', 'objective-c', '#00BFFF');
INSERT INTO tag(type, title, color) values ('开发语言', 'typescript', '#00CED1');
INSERT INTO tag(type, title, color) values ('前端', 'vue.js', '#3CB371');
INSERT INTO tag(type, title, color) values ('前端', 'react.js', '#FF6347');
INSERT INTO tag(type, title, color) values ('前端', 'node.js', '#FFD700');
INSERT INTO tag(type, title, color) values ('前端', 'jquery', '#4682B4');
INSERT INTO tag(type, title, color) values ('前端', 'bootstrap', '#8B008B');
INSERT INTO tag(type, title, color) values ('前端', 'angular', '#008080');
INSERT INTO tag(type, title, color) values ('后端', 'spring mvc', '#00FA9A');
INSERT INTO tag(type, title, color) values ('后端', 'spring cloud', '#228B22');
INSERT INTO tag(type, title, color) values ('后端', 'spring boot', '#DAA520');
INSERT INTO tag(type, title, color) values ('后端', 'spring security', '#8B0000');
INSERT INTO tag(type, title, color) values ('后端', 'eureka', '#00BFFF');
INSERT INTO tag(type, title, color) values ('后端', 'ribbon', '#006400');
INSERT INTO tag(type, title, color) values ('后端', 'hystrix', '#FF00FF');
INSERT INTO tag(type, title, color) values ('后端', 'zuul', '#5F9EA0');
INSERT INTO tag(type, title, color) values ('后端', 'dubbo', '#ADFF2F');
INSERT INTO tag(type, title, color) values ('后端', 'zookeeper', '#DA70D6');
INSERT INTO tag(type, title, color) values ('数据库', 'mysql', '#2F4F4F');
INSERT INTO tag(type, title, color) values ('数据库', 'nosql', '#4B0082');
INSERT INTO tag(type, title, color) values ('数据库', 'mongodb', '#808000');
INSERT INTO tag(type, title, color) values ('数据库', 'sql', '#FF7F50');
INSERT INTO tag(type, title, color) values ('数据库', 'redis', '#191960');
INSERT INTO tag(type, title, color) values ('数据库', 'neo4j', '#90EE90');