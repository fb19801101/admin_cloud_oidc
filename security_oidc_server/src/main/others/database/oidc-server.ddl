-- ###############
--    create database , if need create, cancel the comment
-- ###############
-- create database if not exists myoidc_server default character set utf8;
-- use myoidc_server set default character = utf8;

-- ###############
--    grant privileges  to oidc/oidc
-- ###############
-- GRANT ALL PRIVILEGES ON oidc.* TO oidc@localhost IDENTIFIED BY "oidc";

-- ###############
-- Domain:  User
-- ###############

-- ----------------------------
-- Table structure for demo_auth
-- ----------------------------
DROP TABLE IF EXISTS  demo_auth ;
CREATE TABLE  demo_auth   (
   id  int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '权限id',
   provider_id  varchar(20) NULL DEFAULT NULL COMMENT '操作人组织机构根节点ProviderId',
   operator_id  int(11) NULL DEFAULT NULL COMMENT '操作人ID',
   obj_provider  varchar(20) NULL DEFAULT NULL COMMENT '授权对象ProviderId',
   obj_id  int(11) NULL DEFAULT NULL COMMENT '授权对象id，与objType有关0为人员，1为岗位',
   obj_type  int(11) NULL DEFAULT NULL COMMENT '授权对象类型，0人员，1岗位',
   obj_node  int(11) NULL DEFAULT NULL COMMENT '授权对象组织机构节点',
   obj_name  varchar(255) NULL DEFAULT NULL COMMENT '授权对象名字',
   obj_post_id  int(11) NULL DEFAULT NULL COMMENT '授权对象岗位id，与objType有关，0位人员岗位，1与objId相同',
   obj_post_name  varchar(255) NULL DEFAULT NULL COMMENT '授权对象岗位名称，与objType有关，0位人员岗位，1与objName相同',
   obj_path  varchar(255) NULL DEFAULT NULL COMMENT '授权对象路径',
   node_provider  varchar(20) NULL DEFAULT NULL COMMENT '授权组织机构节点ProviderId',
   node_id  int(11) NULL DEFAULT NULL COMMENT '授权组织机构节点id',
   node_code  varchar(255) NULL DEFAULT NULL COMMENT '授权组织机构节点code',
   node_type  int(11) NULL DEFAULT NULL COMMENT '授权组织机构节点类型',
   node_path  varchar(255) NULL DEFAULT NULL COMMENT '授权组织机构节点路径',
   node_parent_provider  varchar(20) NULL DEFAULT NULL COMMENT '授权组织机构上级节点ProviderId',
   node_parent_id  int(11) NULL DEFAULT NULL COMMENT '授权组织机构上级节点id',
   node_parent_code  varchar(255) NULL DEFAULT NULL COMMENT '授权组织机构上级节点code',
   node_parent_type  int(11) NULL DEFAULT NULL COMMENT '授权组织机构上级节点类型',
   node_parent_path  varchar(255) NULL DEFAULT NULL COMMENT '授权组织机构上级节点路径',
   auth_role  enum('VIEW','MODIFY','NONE') NULL DEFAULT NULL COMMENT '授权对象权限类别 VIEW MODIFY NONE',
   auth_flag  bit(1) NULL DEFAULT NULL COMMENT '授权对象权限删除标识',
   auth_org  int(11) NULL DEFAULT NULL COMMENT '组织人员浏览权限，0无，1浏览，2管理',
   auth_op_auth  int(11) NULL DEFAULT NULL COMMENT '授权管理权限',
   auth_query_auth  int(11) NULL DEFAULT NULL COMMENT '授权查询权限',
   auth_log  int(11) NULL DEFAULT NULL COMMENT '系统日志权限',
   auth_post_change  int(11) NULL DEFAULT NULL COMMENT '岗位变动查询',
   auth_post_delete  int(11) NULL DEFAULT NULL COMMENT '岗位删除查询',
   create_time  timestamp(0) NULL DEFAULT NULL COMMENT '生成时间',
  INDEX  demo_auth_id_index ( id )
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;

-- ----------------------------
-- Table structure for demo_log
-- ----------------------------
DROP TABLE IF EXISTS  demo_log ;
CREATE TABLE  demo_log   (
    id  int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '日志表主键',
    content  varchar(255) NULL DEFAULT NULL COMMENT '日志内容',
    user_name  varchar(255) NULL DEFAULT NULL COMMENT '操作人名字',
    user_path  varchar(255) NULL DEFAULT NULL COMMENT '操作人路径',
    create_time  timestamp(0) NULL DEFAULT NULL COMMENT '生成时间',
  INDEX  demo_log_id_index ( id )
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;

-- ----------------------------
-- Table structure for demo_org_log
-- ----------------------------
DROP TABLE IF EXISTS  demo_org_log ;
CREATE TABLE  demo_org_log   (
   id  int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '组织删除表id',
   node_provider  varchar(20) NULL DEFAULT NULL COMMENT '授权组织机构节点ProviderId',
   node_id  int(11) NULL DEFAULT NULL COMMENT '授权组织机构节点id',
   node_code  varchar(255) NULL DEFAULT NULL COMMENT '授权组织机构节点code',
   node_path  varchar(255) NULL DEFAULT NULL COMMENT '被删除授权组织机构节点路径',
   content  varchar(255) NULL DEFAULT NULL COMMENT '日志内容',
   obj_name  varchar(255) NULL DEFAULT NULL COMMENT '授权对象',
   create_time  timestamp(0) NULL DEFAULT NULL COMMENT '日志生成时间',
  INDEX  demo_org_log_id_index ( id )
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;

-- ----------------------------
-- Table structure for demo_post_log
-- ----------------------------
DROP TABLE IF EXISTS  demo_post_log ;
CREATE TABLE  demo_post_log   (
   id  int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '岗位变化表id',
   node_provider  varchar(20) NULL DEFAULT NULL COMMENT '授权组织机构节点ProviderId',
   node_id  int(11) NULL DEFAULT NULL COMMENT '授权组织机构节点id',
   node_code  varchar(255) NULL DEFAULT NULL COMMENT '授权组织机构节点code',
   node_path  varchar(255) NULL DEFAULT NULL COMMENT '授权组织机构节点路径',
   content  varchar(255) NULL DEFAULT NULL COMMENT '内容',
   post_old  varchar(255) NULL DEFAULT NULL COMMENT '原岗位',
   post_new  varchar(255) NULL DEFAULT NULL COMMENT '现岗位',
   user_name  varchar(255) NULL DEFAULT NULL COMMENT '变动用户名称',
   create_time  timestamp(0) NULL DEFAULT NULL COMMENT '检测到的时间',
  INDEX  demo_post_log_id_index ( id )
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS  oauth_access_token ;
CREATE TABLE  oauth_access_token   (
   create_time  timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '数据的创建时间,精确到秒,由数据库在插入数据时取当前系统时间自动生成(扩展字段)',
   token_id  varchar(255) NULL DEFAULT NULL COMMENT '该字段的值是将access_token的值通过MD5加密后存储的',
   token  blob NULL COMMENT '存储将OAuth2AccessToken.java对象序列化后的二进制数据, 是真实的AccessToken的数据值',
   authentication_id  varchar(255) NULL DEFAULT NULL COMMENT '该字段具有唯一性, 其值是根据当前的username(如果有),client_id与scope通过MD5加密生成的. 具体实现请参考DefaultAuthenticationKeyGenerator.java类',
   user_name  varchar(255) NULL DEFAULT NULL COMMENT '登录时的用户名, 若客户端没有用户名(如grant_type=\"client_credentials\"),则该值等于client_id',
   client_id  varchar(255) NULL DEFAULT NULL COMMENT '用于唯一标识每一个客户端(client); 在注册时必须填写(也可由服务端自动生成). \r\n对于不同的grant_type,该字段都是必须的. 在实际应用中的另一个名称叫appKey,与client_id是同一个概念',
   authentication  blob NULL COMMENT '存储将OAuth2Authentication.java对象序列化后的二进制数据',
   refresh_token  varchar(255) NULL DEFAULT NULL COMMENT '该字段的值是将refresh_token的值通过MD5加密后存储的',
  INDEX  token_id_index ( token_id ),
  INDEX  authentication_id_index ( authentication_id ),
  INDEX  user_name_index ( user_name ),
  INDEX  client_id_index ( client_id ),
  INDEX  refresh_token_index ( refresh_token )
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS  oauth_client_details ;
CREATE TABLE  oauth_client_details   (
   client_id  varchar(255) PRIMARY KEY NOT NULL COMMENT '主键,必须唯一,不能为空. \r\n用于唯一标识每一个客户端(client); 在注册时必须填写(也可由服务端自动生成). \r\n对于不同的grant_type,该字段都是必须的. 在实际应用中的另一个名称叫appKey,与client_id是同一个概念',
   resource_ids  varchar(255) NULL DEFAULT NULL COMMENT '客户端所能访问的资源id集合,多个资源时用逗号(,)分隔,如: \"unity-resource,mobile-resource\". \r\n该字段的值必须来源于与security.xml中标签‹oauth2:resource-server的属性resource-id值一致. 在security.xml配置有几个‹oauth2:resource-server标签, 则该字段可以使用几个该值. \r\n在实际应用中, 我们一般将资源进行分类,并分别配置对应的‹oauth2:resource-server,如订单资源配置一个‹oauth2:resource-server, 用户资源又配置一个‹oauth2:resource-server. 当注册客户端时,根据实际需要可选择资源id,也可根据不同的注册流程,赋予对应的资源id',
   client_secret  varchar(255) NULL DEFAULT NULL COMMENT '用于指定客户端(client)的访问密匙; 在注册时必须填写(也可由服务端自动生成). \r\n对于不同的grant_type,该字段都是必须的. 在实际应用中的另一个名称叫appSecret,与client_secret是同一个概念',
   scope  varchar(255) NULL DEFAULT NULL COMMENT '指定客户端申请的权限范围,可选值包括read,write,trust;若有多个权限范围用逗号(,)分隔,如: \"read,write\". \r\nscope的值与security.xml中配置的‹intercept-url的access属性有关系. 如‹intercept-url的配置为\r\n‹intercept-url pattern=\"/m/**\" access=\"ROLE_MOBILE,SCOPE_READ\"/>\r\n则说明访问该URL时的客户端必须有read权限范围. write的配置值为SCOPE_WRITE, trust的配置值为SCOPE_TRUST. \r\n在实际应该中, 该值一般由服务端指定, 常用的值为read,write',
   authorized_grant_types  varchar(255) NULL DEFAULT NULL COMMENT '指定客户端支持的grant_type,可选值包括authorization_code,password,refresh_token,implicit,client_credentials, 若支持多个grant_type用逗号(,)分隔,如: \"authorization_code,password\". \r\n在实际应用中,当注册时,该字段是一般由服务器端指定的,而不是由申请者去选择的,最常用的grant_type组合有: \"authorization_code,refresh_token\"(针对通过浏览器访问的客户端); \"password,refresh_token\"(针对移动设备的客户端). \r\nimplicit与client_credentials在实际中很少使用',
   web_server_redirect_uri  varchar(255) NULL DEFAULT NULL COMMENT '客户端的重定向URI,可为空, 当grant_type为authorization_code或implicit时, 在Oauth的流程中会使用并检查与注册时填写的redirect_uri是否一致. 下面分别说明:\r\n    当grant_type=authorization_code时, 第一步 从 spring-oauth-server获取 \'code\'时客户端发起请求时必须有redirect_uri参数, 该参数的值必须与web_server_redirect_uri的值一致. 第二步 用 \'code\' 换取 \'access_token\' 时客户也必须传递相同的redirect_uri. \r\n    在实际应用中, web_server_redirect_uri在注册时是必须填写的, 一般用来处理服务器返回的code, 验证state是否合法与通过code去换取access_token值. \r\n    在spring-oauth-client项目中, 可具体参考AuthorizationCodeController.java中的authorizationCodeCallback方法.\r\n    当grant_type=implicit时通过redirect_uri的hash值来传递access_token值.如:\r\n    http://localhost:7777/spring-oauth-client/implicit#access_token=dc891f4a-ac88-4ba6-8224-a2497e013865&token_type=bearer&expires_in=43199\r\n    然后客户端通过JS等从hash值中取到access_token值',
   authorities  varchar(255) NULL DEFAULT NULL COMMENT '指定客户端所拥有的Spring Security的权限值,可选, 若有多个权限值,用逗号(,)分隔, 如: \"ROLE_UNITY,ROLE_USER\". \r\n对于是否要设置该字段的值,要根据不同的grant_type来判断, 若客户端在Oauth流程中需要用户的用户名(username)与密码(password)的(authorization_code,password), \r\n则该字段可以不需要设置值,因为服务端将根据用户在服务端所拥有的权限来判断是否有权限访问对应的API. \r\n但如果客户端在Oauth流程中不需要用户信息的(implicit,client_credentials), \r\n则该字段必须要设置对应的权限值, 因为服务端将根据该字段值的权限来判断是否有权限访问对应的API',
   access_token_validity  int(11) NULL DEFAULT NULL COMMENT '设定客户端的access_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 12, 12小时). \r\n在服务端获取的access_token JSON数据中的expires_in字段的值即为当前access_token的有效时间值. \r\n在项目中, 可具体参考DefaultTokenServices.java中属性accessTokenValiditySeconds. \r\n在实际应用中, 该值一般是由服务端处理的, 不需要客户端自定义',
   refresh_token_validity  int(11) NULL DEFAULT NULL COMMENT '设定客户端的refresh_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 24 * 30, 30天). \r\n若客户端的grant_type不包括refresh_token,则不用关心该字段 在项目中, 可具体参考DefaultTokenServices.java中属性refreshTokenValiditySeconds. \r\n在实际应用中, 该值一般是由服务端处理的, 不需要客户端自定义',
   additional_information  text NULL COMMENT '这是一个预留的字段,在Oauth的流程中没有实际的使用,可选,但若设置值,必须是JSON格式的数据,如:\r\n{\"country\":\"CN\",\"country_code\":\"086\"}\r\n按照spring-security-oauth项目中对该字段的描述 \r\nAdditional information for this client, not need by the vanilla OAuth protocol but might be useful, for example,for storing descriptive information. \r\n(详见ClientDetails.java的getAdditionalInformation()方法的注释)在实际应用中, 可以用该字段来存储关于客户端的一些其他信息,如客户端的国家,地区,注册时的IP地址等等.',
   create_time  timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '数据的创建时间,精确到秒,由数据库在插入数据时取当前系统时间自动生成(扩展字段)',
   archived  bit(1) NULL DEFAULT b'0' COMMENT '用于标识客户端是否已存档(即实现逻辑删除),默认值为\'0\'(即未存档). \r\n对该字段的具体使用请参考CustomJdbcClientDetailsService.java,在该类中,扩展了在查询client_details的SQL加上archived = 0条件 (扩展字段)',
   trusted  bit(1) NULL DEFAULT b'0' COMMENT '设置客户端是否为受信任的,默认为\'0\'(即不受信任的,1为受信任的). \r\n该字段只适用于grant_type=\"authorization_code\"的情况,当用户登录成功后,若该值为0,则会跳转到让用户Approve的页面让用户同意授权, \r\n若该字段为1,则在登录后不需要再让用户Approve同意授权(因为是受信任的). \r\n对该字段的具体使用请参考OauthUserApprovalHandler.java. (扩展字段)',
   autoapprove  varchar(255) NULL DEFAULT 'false' COMMENT '设置用户是否自动Approval操作, 默认值为 \'false\', 可选值包括 \'true\',\'false\', \'read\',\'write\'. \r\n该字段只适用于grant_type=\"authorization_code\"的情况,当用户登录成功后,若该值为\'true\'或支持的scope值,则会跳过用户Approve的页面, 直接授权. \r\n该字段与 trusted 有类似的功能, 是 spring-security-oauth2 的 2.0 版本后添加的新属性.'
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Table structure for oauth_client_token
-- ----------------------------
DROP TABLE IF EXISTS  oauth_client_token ;
CREATE TABLE  oauth_client_token   (
   create_time  timestamp(0) NULL DEFAULT NULL COMMENT '数据的创建时间,精确到秒,由数据库在插入数据时取当前系统时间自动生成(扩展字段)',
   token_id  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '从服务器端获取到的access_token的值',
   token  blob NULL COMMENT '这是一个二进制的字段, 存储的数据是OAuth2AccessToken.java对象序列化后的二进制数据',
   authentication_id  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '该字段具有唯一性, 是根据当前的username(如果有),client_id与scope通过MD5加密生成的. \r\n具体实现请参考DefaultClientKeyGenerator.java类',
   user_name  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用于唯一标识每一个客户端(client); 在注册时必须填写(也可由服务端自动生成). \r\n对于不同的grant_type,该字段都是必须的. 在实际应用中的另一个名称叫appKey,与client_id是同一个概念.',
   client_id  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录时的用户名'
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS  oauth_code ;
CREATE TABLE  oauth_code   (
   create_time  timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '数据的创建时间,精确到秒,由数据库在插入数据时取当前系统时间自动生成(扩展字段)',
   code  varchar(255) NULL DEFAULT NULL COMMENT '存储服务端系统生成的code的值(未加密)',
   authentication  blob NULL COMMENT '存储将AuthorizationRequestHolder.java对象序列化后的二进制数据',
  INDEX  code_index ( code )
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Table structure for oauth_privilege
-- ----------------------------
DROP TABLE IF EXISTS  oauth_privilege ;
CREATE TABLE  oauth_privilege   (
   id  int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '权限id',
   uuid  varchar(255) NOT NULL COMMENT '权限uuid',
   archived  tinyint(1) NULL DEFAULT 0 COMMENT '是否存档',
   version  int(11) NULL DEFAULT 0 COMMENT '权限版本',
   user_id  int(11) NULL DEFAULT NULL COMMENT '授权用户id',
   privilege  varchar(255) NULL DEFAULT NULL COMMENT '用户权限',
   create_time  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  UNIQUE INDEX  uuid ( uuid ),
  INDEX  uuid_index ( uuid )
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS  oauth_refresh_token ;
CREATE TABLE  oauth_refresh_token   (
   create_time  timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '数据的创建时间,精确到秒,由数据库在插入数据时取当前系统时间自动生成(扩展字段)',
   token_id  varchar(255) NULL DEFAULT NULL COMMENT '该字段的值是将refresh_token的值通过MD5加密后存储的',
   token  blob NULL COMMENT '存储将OAuth2RefreshToken.java对象序列化后的二进制数据',
   authentication  blob NULL COMMENT '存储将OAuth2Authentication.java对象序列化后的二进制数据',
  INDEX  token_id_index ( token_id )
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Table structure for oauth_roles
-- ----------------------------
DROP TABLE IF EXISTS  oauth_roles ;
CREATE TABLE  oauth_roles   (
   id  int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '权限id',
   provider_id  varchar(10) NULL DEFAULT NULL COMMENT '用户所在根节点，一般为“hr”',
   user_id  int(11) NULL DEFAULT NULL COMMENT '用户id',
   auth_node  int(11) NULL DEFAULT NULL COMMENT '分级授权节点id',
   auth_code  varchar(50) NULL DEFAULT NULL COMMENT '分级授权节点code，直接引用组织机构code',
   auth_path  varchar(50) NULL DEFAULT NULL COMMENT '分级授权节点路径',
   auth_type  bit(1) NULL DEFAULT NULL COMMENT '分级授权对象类型，0 人员，1 岗位',
   auth_role  enum('VIEW','MODIFY','NONE') NULL DEFAULT NULL COMMENT '分级授权，VIEW 浏览权限  MODIFY 管理权限  NONE 无权限',
   auth_flag  bit(1) NULL DEFAULT NULL COMMENT '分级授权变化标识， 0未变化 1变化',
   create_time  timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  INDEX  roles_id_index ( id )
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;

-- ----------------------------
-- Table structure for oauth_user
-- ----------------------------
DROP TABLE IF EXISTS  oauth_user ;
CREATE TABLE  oauth_user   (
   id  int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '用户id',
   uuid  varchar(255) NOT NULL COMMENT '用户uuid',
   archived  tinyint(1) NULL DEFAULT 0 COMMENT '是否存档',
   version  int(11) NULL DEFAULT 0 COMMENT '登录版本',
   username  varchar(255) NOT NULL COMMENT '用户名称',
   password  varchar(255) NOT NULL COMMENT '用户密码',
   phone  varchar(255) NULL DEFAULT NULL COMMENT '用户电话',
   email  varchar(255) NULL DEFAULT NULL COMMENT '用户邮箱',
   default_user  tinyint(1) NULL DEFAULT 0 COMMENT '是否默认用户',
   last_login_time  datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
   creator_id  int(11) NULL DEFAULT NULL COMMENT '创建用户的id',
   create_time  datetime(0) NULL DEFAULT NULL COMMENT '用户创建时间',
  UNIQUE INDEX  uuid ( uuid ),
  UNIQUE INDEX  username ( username ),
  INDEX  uuid_index ( uuid ),
  INDEX  creator_id_index ( creator_id )
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8;

-- ----------------------------
-- Procedure structure for sp_INIT_AUTO_INCREMENT
-- ----------------------------
DROP PROCEDURE IF EXISTS  sp_INIT_AUTO_INCREMENT ;
delimiter ;;
CREATE PROCEDURE  sp_INIT_AUTO_INCREMENT (tbl VARCHAR(50), num INT)
BEGIN
SET @stmt_alter := CONCAT('ALTER TABLE pig_oidc.', tbl, ' AUTO_INCREMENT = ', num);
PREPARE stmt FROM @stmt_alter;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
END
;;
delimiter ;

-- ----------------------------
-- Add indexes
-- ----------------------------
create index demo_auth_id_index on demo_auth (id);
create index demo_log_id_index on demo_log (id);
create index demo_org_log_id_index on demo_org_log (id);
create index demo_post_log_id_index on demo_post_log (id);




