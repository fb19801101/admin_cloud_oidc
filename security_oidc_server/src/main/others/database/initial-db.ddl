


-- ----------------------------
-- Records of oauth_user
-- ----------------------------
truncate oauth_user;
INSERT INTO oauth_user (id, uuid, create_time, archived, version, username, password, phone, email, default_user)
VALUES
  (21, 'wR4XwW4UdCbfOWuMCYj8lafxApKZHtgl6uls55Ij2i', now(), 0, 0, 'admin', '$2a$10$aD9.x4DS90lryvXf3B2nseL/dgTFLwPzx/A8PjpmxF0v5E0XK1JAu', NULL,
   'admin@qc8.me', 1);

-- unity/RP_OIDC-2021
INSERT INTO oauth_user (id, uuid, create_time, archived, version, username, password, phone, email, default_user)
VALUES
  (22, 'A46bLT2S2OYSq3iI4oqd9rOG4K0o39DTiXBlqEGYv8', now(), 0, 0, 'unity', '$2a$10$rLcMsWW9UECgiLk9uLHxQuucw99XYeeV/qglaxc8yDvccoJAQm2Ca', NULL,
   'unity@qc8.me', 0);

-- mobile/RP_OIDC-2021
INSERT INTO oauth_user (id, uuid, create_time, archived, version, username, password, phone, email, default_user)
VALUES
  (23, 'RA8bWeVhPQjZduZoY0zvPbxBkSj5IiuyfVMicQEZbN', now(), 0, 0, 'mobile', '$2a$10$Q7LwJkXqbmFvpYzPV7AYZ./VoCc3HCB1yGyfxNYaxRJ1C5HGtSQka', NULL,
   'mobile@qc8.me', 0);

-- crcc/RP_OIDC-2021
INSERT INTO oauth_user (id, uuid, create_time, archived, version, username, password, phone, email, default_user)
VALUES
  (24, 'aER8KgBWeFyNQsLRSQcif59tJBhC2cb75G3KdG7MV4', now(), 0, 0, 'crcc', '$2a$10$Tee.E2CsuYXWjJ8M8AmzU.8nIY7gmcxBREkrGPmY7ed50qz4QGKl2', NULL,
   'crcc@qc8.me', 0);


-- ----------------------------
-- Records of oauth_privilege
-- ----------------------------
truncate oauth_privilege;
insert into oauth_privilege(uuid,create_time,user_id,privilege) values ('KysLHSX31gS5aELdWSNOtCvr45kHpc2PBUtJ2cv6mH',now(),22,'UNITY');
insert into oauth_privilege(uuid,create_time,user_id,privilege) values ('tV7wbHaSjWmdS9qy4AoSPaYQiVkeyO28kcLi28IQ50',now(),23,'MOBILE');
insert into oauth_privilege(uuid,create_time,user_id,privilege) values ('IfYTzyBsqwCvH4PRVeEttNSs2tHDrHObstGamzJKPF',now(),24,'CRCC');



-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
-- initial oauth client details test data
-- 'unity-client'   support browser, js(flash) visit,  secret:  unity
-- 'mobile-client'  only support mobile-device visit,  secret:  mobile
truncate  oauth_client_details;
insert into oauth_client_details
(client_id, resource_ids, client_secret, scope, authorized_grant_types,
 web_server_redirect_uri,authorities, access_token_validity,
 refresh_token_validity, additional_information, create_time, archived, trusted)
values
  ('44nwurCZOGi2AfFDyiR3LdTuWRce3yPP','oidc-resource', '$2a$10$S4AUiAcxATltNvnSKFmY2ut1T1MvxERys2Ee8fkUuRRhzUbtWs2V.', 'read,openid','authorization_code,refresh_token',
                  'http://localhost:8071/authorization_callback','ROLE_CLIENT,ROLE_USER,ROLE_UNITY',null,
                  null, '{\"appName\":\"unity-client\"}', now(), 0, 0),
  ('BCS4YS60gmYMEQMsYpNjBr27qOP9cHcL','oidc-resource', '$2a$10$XS/Bv/JmTkkRJhwlUhgkL.pUG/POkNT3nr.NUsVpBzlo006LROccS', 'read,openid','password,refresh_token',
                   'http://localhost:8071/authorization_callback','ROLE_CLIENT,ROLE_USER,ROLE_MOBILE',null,
                   null,'{\"appName\":\"mobile-client\"}', now(), 0, 0),
  ('nGlIY7lXDthnqcEOz0P1q8EMBIooAwiU','oidc-resource', '$2a$10$ajnFd.orulAtWVULZxMzyeWpsdnHEi.EVKHNqG1rlOIvdptHGBPA6', 'read,openid','authorization_code,password,implicit,refresh_token',
                  'http://localhost:8071/authorization_callback','ROLE_CLIENT,ROLE_USER,ROLE_CRCC',null,
                  null,'{\"appName\":\"crcc-client\"}', now(), 0, 0);



-- ----------------------------
-- Records of oauth_roles
-- ----------------------------
INSERT INTO oauth_roles VALUES (1, 'hr', 364035, 364032, '000010000100059', '中铁十二局集团有限公司', b'0', 'MODIFY', b'0', '2021-03-18 17:33:46');
INSERT INTO oauth_roles VALUES (2, 'hr', 365474, 364569, '00001000010005900001', '中铁十二局集团有限公司/中体十二局集团第一工程有限公司', b'0', 'MODIFY', b'0', '2021-03-18 17:33:46');
INSERT INTO oauth_roles VALUES (3, 'hr', 365475, 364570, '0000100001000590000101', '中铁十二局集团有限公司/中体十二局集团第一工程有限公司/机关', b'1', 'MODIFY', b'0', '2021-03-18 17:33:46');
INSERT INTO oauth_roles VALUES (4, 'hr', 365479, 364573, '000010000100059000010100077003', '中铁十二局集团有限公司/中体十二局集团第一工程有限公司/机关/人力资源部', b'1', 'MODIFY', b'0', '2021-03-18 17:33:46');
INSERT INTO oauth_roles VALUES (5, 'hr', 365478, 364573, '000010000100059000010100077003', '中铁十二局集团有限公司/中体十二局集团第一工程有限公司/机关/人力资源部', b'0', 'VIEW', b'0', '2021-03-18 17:33:46');
INSERT INTO oauth_roles VALUES (6, 'hr', 365473, 365265, '0000100001000590000177001', '中铁十二局集团有限公司/中体十二局集团第一工程有限公司/工程部', b'1', 'MODIFY', b'0', '2021-03-18 17:33:46');
INSERT INTO oauth_roles VALUES (7, 'hr', 365476, 365265, '0000100001000590000177001', '中铁十二局集团有限公司/中体十二局集团第一工程有限公司/工程部', b'0', 'VIEW', b'0', '2021-03-18 17:33:46');
INSERT INTO oauth_roles VALUES (8, 'hr', 365483, 365265, '0000100001000590000177001', '中铁十二局集团有限公司/中铁十二局集团第一工程有限公司/工程部', b'0', 'NONE', b'1', '2021-03-19 10:04:14');
INSERT INTO oauth_roles VALUES (46, 'hr', 365483, 364569, '00001000010005900001', '中铁十二局集团有限公司/中铁十二局集团第一工程有限公司', b'0', 'MODIFY', b'0', '2021-03-19 10:04:14');



-- ----------------------------
-- Records of demo_auth
-- ----------------------------
INSERT INTO  demo_auth  VALUES (1, 'hr', 364035, 'hr', 364035, 0, 364446, '甘忠忠', 364446, '部长', '中铁十二局集团有限公司/信息化管理部/部长', 'crcc', 2147483647, '00001', 0, '中国铁建', 'crcc', 2147483647, '00001', 0, '中国铁建', 'MODIFY', b'0', 1, 2, 1, 1, 1, 1, '2021-03-31 14:39:01');
INSERT INTO  demo_auth  VALUES (2, 'hr', 364035, 'hr', 365474, 0, 366148, '宇文宁军', 366148, '总经理', '股份公司/中铁十二局集团有限公司/中铁十二局集团第一工程有限公司/领导人员/总经理', 'hr', 364032, '000010000100059', 1, '股份公司/中铁十二局集团有限公司', 'hr', 986436, '0000100001', 1, '股份公司', 'VIEW', b'0', 1, 2, 1, 1, 1, 1, '2021-04-17 05:55:16');
INSERT INTO  demo_auth  VALUES (3, 'hr', 364035, 'hr', 169632, 0, 1164567, '王玉青（勿动）', 1164567, '处长', '股份公司/信息化管理部测试666/基础平台处0318/处长', 'crcc', 2147483647, '00001', 0, '中国铁建', 'crcc', 2147483647, '00001', 0, '中国铁建', 'VIEW', b'0', 1, 2, 1, 1, 1, 1, '2021-04-19 08:14:01');
INSERT INTO  demo_auth  VALUES (119, 'hr', 169632, 'hr', 169432, 0, 340308, '窦宏冰（勿删）', 340308, '测试', '股份公司/海外部/测试', 'hr', 373698, '000010000112', 1, '股份公司/测试420', 'hr', 986436, '0000100001', 1, '股份公司', 'VIEW', b'0', 1, 0, 1, 1, 0, 0, '2021-04-20 08:14:50');
INSERT INTO  demo_auth  VALUES (129, 'hr', 364035, 'CSCRCC11N', 2211607, 1, 2211607, '中转岗位', 2211607, '中转岗位', '中铁十一局（N）/中转部门/中转岗位', 'hr', 373698, '000010000112', 1, '股份公司', 'hr', 986436, '0000100001', 1, '股份公司', 'VIEW', b'0', 1, 0, 0, 0, 0, 0, '2021-04-23 02:10:34');
INSERT INTO  demo_auth  VALUES (142, 'hr', 364035, 'hr', 365478, 0, 364579, '谢雄翠', 364579, '部员', '股份公司/中铁十二局集团有限公司/中铁十二局集团第一工程有限公司/人力资源部/部员', 'hr', 364569, '00001000010005900001', 1, '股份公司/中铁十二局集团有限公司/中铁十二局集团第一工程有限公司', 'hr', 364032, '000010000100059', 1, '股份公司/中铁十二局集团有限公司', 'VIEW', b'0', 1, 2, 0, 0, 0, 0, '2021-04-23 04:03:11');
INSERT INTO  demo_auth  VALUES (149, 'hr', 364035, 'hr', 365479, 0, 364579, '扶炎', 364579, '部员', '股份公司/中铁十二局集团有限公司/中铁十二局集团第一工程有限公司/人力资源部/部员', 'crcc', 2147483647, '00001', 0, '中国铁建', 'crcc', 2147483647, '00001', 0, '中国铁建', 'VIEW', b'0', 1, 1, 1, 1, 1, 1, '2021-04-23 06:58:10');

-- ----------------------------
-- Records of demo_log
-- ----------------------------
INSERT INTO  demo_log  VALUES (67, '更新用户授权对象：扶炎，对象所属组织：股份公司/中铁十二局集团有限公司/中铁十二局集团第一工程有限公司/人力资源部/部长，被授权组织：中国铁建，被授权功能：组织人员浏览[功能]：有[浏览权限]；授权管理[功能]：有[浏览权限]；授权查询[功能]：有[浏览权限]；系统日志[功能]：有[浏览权限]；岗位变化查询[功能]：有[浏览权限]；组织删除查询[功能]：有[浏览权限]；', '甘忠忠', '股份公司/中铁十二局集团有限公司/信息化管理部/部长', '2021-04-23 06:58:10');
INSERT INTO  demo_log  VALUES (68, '增加用户授权对象：赵其浩，对象所属组织：股份公司/中铁二十局1/人力资源部/部长，被授权组织：测量队，被授权功能：组织人员浏览[功能]：有[浏览权限]；', '甘忠忠', '股份公司/中铁十二局集团有限公司/信息化管理部/部长', '2021-04-23 07:07:25');
INSERT INTO  demo_log  VALUES (69, '增加用户授权对象：陈义，对象所属组织：股份公司/中铁十二局集团有限公司/中铁十二局集团第一工程有限公司/实验室/主任，被授权组织：中国铁建，被授权功能：组织人员浏览[功能]：有[浏览权限]；', '甘忠忠', '股份公司/中铁十二局集团有限公司/信息化管理部/部长', '2021-04-23 11:47:39');
INSERT INTO  demo_log  VALUES (70, '更新用户授权对象：陈义，对象所属组织：股份公司/中铁十二局集团有限公司/中铁十二局集团第一工程有限公司/实验室/主任，被授权组织：中国铁建，被授权功能：组织人员浏览[功能]：有[浏览权限]；授权管理[功能]：有[管理权限]，有[浏览权限]；授权查询[功能]：无[浏览权限]；系统日志[功能]：无[浏览权限]；岗位变化查询[功能]：无[浏览权限]；组织删除查询[功能]：无[浏览权限]；', '甘忠忠', '股份公司/中铁十二局集团有限公司/信息化管理部/部长', '2021-04-23 11:47:46');
INSERT INTO  demo_log  VALUES (71, '删除用户授权对象：陈义，对象所属组织：股份公司/中铁十二局集团有限公司/中铁十二局集团第一工程有限公司/实验室/主任，被授权组织：中国铁建', '甘忠忠', '股份公司/中铁十二局集团有限公司/信息化管理部/部长', '2021-04-23 12:25:31');
INSERT INTO  demo_log  VALUES (72, '增加用户授权对象：景姬，对象所属组织：股份公司/中铁十二局集团有限公司/中铁十二局集团第一工程有限公司/人力资源部/部长，被授权组织：计划部，被授权功能：组织人员浏览[功能]：有[浏览权限]；', '甘忠忠', '股份公司/中铁十二局集团有限公司/信息化管理部/部长', '2021-04-23 12:26:48');
INSERT INTO  demo_log  VALUES (73, '更新用户授权对象：景姬，对象所属组织：股份公司/中铁十二局集团有限公司/中铁十二局集团第一工程有限公司/人力资源部/部长，被授权组织：股份公司/中铁十二局集团有限公司/中铁十二局集团第一工程有限公司/计划部，被授权功能：组织人员浏览[功能]：有[浏览权限]；授权管理[功能]：有[浏览权限]；授权查询[功能]：无[浏览权限]；系统日志[功能]：无[浏览权限]；岗位变化查询[功能]：无[浏览权限]；组织删除查询[功能]：无[浏览权限]；', '甘忠忠', '股份公司/中铁十二局集团有限公司/信息化管理部/部长', '2021-04-23 12:26:57');

-- ----------------------------
-- Records of demo_org_log
-- ----------------------------
INSERT INTO  demo_org_log  VALUES (5, 'hr', 364569, '00001000010005900001', '股份公司/中铁十二局集团有限公司/中铁十二局集团第一工程有限公司/测量队', '授权组织机构删除变化! 授权对象：赵其浩，授权节点：股份公司/中铁十二局集团有限公司/中铁十二局集团第一工程有限公司/测量队', '赵其浩', '2021-04-23 07:08:08');
INSERT INTO  demo_org_log  VALUES (6, 'hr', 364570, '0000100001000590000101', '股份公司/中铁十二局集团有限公司/中铁十二局集团第一工程有限公司/计划部', '授权组织机构删除变化! 授权对象：景姬，授权节点：股份公司/中铁十二局集团有限公司/中铁十二局集团第一工程有限公司/计划部', '景姬', '2021-04-23 12:29:54');

-- ----------------------------
-- Records of demo_post_log
-- ----------------------------
INSERT INTO  demo_post_log  VALUES (3, 'crcc', 2147483647, '00001', '中国铁建', '授权对象岗位发生变化! 授权对象：扶炎，原岗位：部长，现岗位：队长，授权节点：中国铁建', '部长', '队长', '扶炎', '2021-04-23 07:01:25');
INSERT INTO  demo_post_log  VALUES (4, 'crcc', 2147483647, '00001', '中国铁建', '授权对象岗位发生变化! 授权对象：扶炎，原岗位：队长，现岗位：部长，授权节点：中国铁建', '队长', '部长', '扶炎', '2021-04-23 07:02:15');
INSERT INTO  demo_post_log  VALUES (5, 'crcc', 2147483647, '00001', '中国铁建', '授权对象岗位发生变化! 授权对象：扶炎，原岗位：股份公司/中铁十二局集团有限公司/中铁十二局集团第一工程有限公司/人力资源部/部长，现岗位：股份公司/中铁十二局集团有限公司/中铁十二局集团第一工程有限公司/人力资源部/部员，授权节点：中国铁建', '部长', '部员', '扶炎', '2021-04-23 07:14:37');
INSERT INTO  demo_post_log  VALUES (6, 'crcc', 2147483647, '00001', '中国铁建', '授权对象岗位发生变化! 授权对象：扶炎，原岗位：股份公司/中铁十二局集团有限公司/中铁十二局集团第一工程有限公司/人力资源部/部员，现岗位：股份公司/中铁十二局集团有限公司/中铁十二局集团第一工程有限公司/人力资源部/部长，授权节点：中国铁建', '部员', '部长', '扶炎', '2021-04-23 07:17:17');
INSERT INTO  demo_post_log  VALUES (7, 'crcc', 2147483647, '00001', '中国铁建', '授权对象岗位发生变化! 授权对象：扶炎，原岗位：股份公司/中铁十二局集团有限公司/中铁十二局集团第一工程有限公司/人力资源部/部长，现岗位：股份公司/中铁十二局集团有限公司/中铁十二局集团第一工程有限公司/人力资源部/部员，授权节点：中国铁建', '部长', '部员', '扶炎', '2021-04-23 11:46:03');



ClientId  unity-client
    44nwurCZOGi2AfFDyiR3LdTuWRce3yPP
ClientSecret
    Euq1LJESEtJkArDn3GLcEsbKDgCh5FkpMSdNHBeT
Redirect Uri
    http://localhost:8071/authorization_callback
Scope
    openid
GrantType
    authorization_code,refresh_token
Discovery-Endpoint
    http://localhost:8070/.well-known/openid-configuration


ClientId  mobile-client
    BCS4YS60gmYMEQMsYpNjBr27qOP9cHcL
ClientSecret
    K63fjsKZzsVJOyOHCcYEztAP1RyiDhIEcbSPbatq
Redirect Uri
    http://localhost:8071/authorization_callback
Scope
    openid
GrantType
    password,refresh_token
Discovery-Endpoint
    http://localhost:8070/.well-known/openid-configuration


ClientId  crcc-client
    nGlIY7lXDthnqcEOz0P1q8EMBIooAwiU
ClientSecret
    MHAtSRlnpunprtI0dB5vxhmh74J5zImYd4JQLgzE
Redirect Uri
    http://localhost:8071/authorization_callback
Scope
    openid
GrantType
    password,refresh_token
Discovery-Endpoint
    http://localhost:8070/.well-known/openid-configuration


