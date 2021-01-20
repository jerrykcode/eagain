INSERT INTO sys_permission(id, perm_name, perm_tag, url) values(1, '测试权限1', '测试权限1', '/test1');
INSERT INTO sys_permission(id, perm_name, perm_tag, url) values(2, '测试权限2', '测试权限2', '/test2');

INSERT INTO sys_role(id, role_name, role_desc) values(1, 'admin', '管理员');
INSERT INTO sys_role(id, role_name, role_desc) values(2, 'user', '普通用户');

INSERT INTO sys_role_permission(role_id, perm_id) values(1, 1);
INSERT INTO sys_role_permission(role_id, perm_id) values(1, 2);
INSERT INTO sys_role_permission(role_id, perm_id) values(2, 1);

INSERT INTO sys_user(username, email, password, account_opts) values ('test_user', 'xx@xx.com', '$2y$12$Jif94d9mBJg8pA33niCo8erqW5G3BF4Jo2OTyCErvgbnuX3kVp.6m', 15); -- 密码: test123
INSERT INTO sys_user_role(user_id, role_id) values (1, 2);