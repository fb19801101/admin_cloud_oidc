/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : dxerpsystem

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 17/05/2021 14:49:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bak_dx_erp_role
-- ----------------------------
DROP TABLE IF EXISTS `bak_dx_erp_role`;
CREATE TABLE `bak_dx_erp_role`  (
  `rid` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `description` varchar(225) NULL DEFAULT NULL COMMENT '角色描述',
  `flag` tinyint(4) NOT NULL COMMENT '角色状态/0失效，1有效',
  `remark` varchar(225) NULL DEFAULT NULL COMMENT '备注',
  `sid` smallint(6) NOT NULL COMMENT '父id',
  `order_index` smallint(6) NULL DEFAULT NULL COMMENT '排序',
  `unid` smallint(6) NULL DEFAULT NULL COMMENT '角色所属部门',
  `de_id` smallint(6) NULL DEFAULT NULL COMMENT '角色所属项目部门',
  `proid` smallint(6) NULL DEFAULT NULL COMMENT '角色所属项目部',
  `issupervisor` tinyint(4) NOT NULL DEFAULT 0 COMMENT '主管角色',
  INDEX `rid`(`rid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of bak_dx_erp_role
-- ----------------------------

-- ----------------------------
-- Table structure for bak_dx_erp_role_depart_perm
-- ----------------------------
DROP TABLE IF EXISTS `bak_dx_erp_role_depart_perm`;
CREATE TABLE `bak_dx_erp_role_depart_perm`  (
  `proid` smallint(6) NULL DEFAULT NULL COMMENT '项目部id',
  `unid` smallint(6) NULL DEFAULT NULL COMMENT '单位id',
  `rid` smallint(6) NULL DEFAULT NULL COMMENT '角色id',
  `pid` smallint(6) NULL DEFAULT NULL COMMENT '权限id',
  `isbasis` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否基础权限1:是，2:不是'
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of bak_dx_erp_role_depart_perm
-- ----------------------------

-- ----------------------------
-- Table structure for bak_dx_erp_user_role_depart
-- ----------------------------
DROP TABLE IF EXISTS `bak_dx_erp_user_role_depart`;
CREATE TABLE `bak_dx_erp_user_role_depart`  (
  `id` smallint(6) NULL DEFAULT NULL COMMENT '用户id',
  `rid` smallint(6) NULL DEFAULT NULL COMMENT '角色id',
  `proid` smallint(6) NULL DEFAULT NULL COMMENT '项目部id',
  `unid` smallint(6) NULL DEFAULT NULL COMMENT '单位id',
  `review_status` tinyint(4) NULL DEFAULT NULL COMMENT '审核状态',
  `apply_time` datetime(0) NULL DEFAULT NULL COMMENT '申请时间'
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of bak_dx_erp_user_role_depart
-- ----------------------------

-- ----------------------------
-- Table structure for bak_rdp
-- ----------------------------
DROP TABLE IF EXISTS `bak_rdp`;
CREATE TABLE `bak_rdp`  (
  `proid` smallint(6) NULL DEFAULT NULL COMMENT '项目部id,机关为0',
  `rid` smallint(6) NULL DEFAULT NULL COMMENT '角色id',
  `pid` smallint(6) NULL DEFAULT NULL COMMENT '权限id'
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of bak_rdp
-- ----------------------------

-- ----------------------------
-- Table structure for calendar_custom
-- ----------------------------
DROP TABLE IF EXISTS `calendar_custom`;
CREATE TABLE `calendar_custom`  (
  `date` date NOT NULL,
  UNIQUE INDEX `unique_date`(`date`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of calendar_custom
-- ----------------------------

-- ----------------------------
-- Table structure for dx_admin
-- ----------------------------
DROP TABLE IF EXISTS `dx_admin`;
CREATE TABLE `dx_admin`  (
  `k` varchar(25) NOT NULL COMMENT 'key',
  `v` varchar(255) NOT NULL COMMENT 'value',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  UNIQUE INDEX `ADMIN_K_UNIQUE`(`k`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_admin
-- ----------------------------

-- ----------------------------
-- Table structure for dx_app_info
-- ----------------------------
DROP TABLE IF EXISTS `dx_app_info`;
CREATE TABLE `dx_app_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) NOT NULL COMMENT '文件名',
  `version` varchar(10) NOT NULL COMMENT '版本',
  `force` tinyint(1) NOT NULL COMMENT '是否强制更新(0:否,1:是)',
  `fix_info` text NOT NULL COMMENT '更新信息',
  `device` int(10) NOT NULL COMMENT '设备(1:安卓,2:IOS)',
  `download_url` varchar(100) NOT NULL COMMENT '下载链接',
  `is_test` tinyint(1) NOT NULL COMMENT '是否测试版(0:否,1:是)',
  `api_host` varchar(5000) NOT NULL COMMENT '请求前缀',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_app_info
-- ----------------------------

-- ----------------------------
-- Table structure for dx_app_permission
-- ----------------------------
DROP TABLE IF EXISTS `dx_app_permission`;
CREATE TABLE `dx_app_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `business_name` varchar(255) NULL DEFAULT NULL COMMENT '业务名称',
  `business_url` varchar(255) NULL DEFAULT NULL COMMENT '业务地址',
  `business_photo_url` varchar(255) NULL DEFAULT NULL COMMENT '业务图片地址',
  `permission_id` varchar(255) NULL DEFAULT NULL COMMENT '权限id   code',
  `remark` text NULL COMMENT '功能名称',
  `orientation` int(11) NULL DEFAULT NULL COMMENT '方位',
  `is_site` int(255) NULL DEFAULT 0 COMMENT '是否是现场权限  1 是  0不是',
  `is_app` int(11) NULL DEFAULT 0 COMMENT '是否是app权限 1 是 0不是',
  `type_name` varchar(255) NOT NULL COMMENT '分类名称 必须要有  不能为空',
  `department_name` varchar(255) NULL DEFAULT NULL COMMENT '部门名称 有所属部门 必须填写  本级和相关的权限必须填写',
  `order_index` int(11) NULL DEFAULT NULL COMMENT '排序    ',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_app_permission
-- ----------------------------

-- ----------------------------
-- Table structure for dx_app_permission1
-- ----------------------------
DROP TABLE IF EXISTS `dx_app_permission1`;
CREATE TABLE `dx_app_permission1`  (
  `business_name` varchar(255) NULL DEFAULT NULL COMMENT '业务名称',
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父级id',
  `business_url` varchar(255) NULL DEFAULT NULL COMMENT '业务地址',
  `business_photo_url` varchar(255) NULL DEFAULT NULL COMMENT '业务图片地址',
  `permission_id` varchar(255) NULL DEFAULT NULL COMMENT '权限id   code',
  `remark` text NULL COMMENT '功能名称',
  `orientation` varchar(255) NULL DEFAULT NULL COMMENT '方位',
  `is_site` int(255) NULL DEFAULT 0 COMMENT '是否是现场权限  1 是  0不是',
  `is_app` int(11) NULL DEFAULT 0 COMMENT '是否是app权限 1 是 0不是',
  `type_name` varchar(255) NULL DEFAULT NULL COMMENT '分类名称 必须要有  不能为空',
  `department_name` varchar(255) NULL DEFAULT NULL COMMENT '部门名称 有所属部门 必须填写  本级和相关的权限必须填写',
  `department_id` int(11) NULL DEFAULT NULL COMMENT '部门id',
  `order_index` int(11) NOT NULL COMMENT '排序    ',
  `is_unit` int(11) NULL DEFAULT 1 COMMENT '1项目部 0公司 哪里的权限',
  `can_public` int(11) NULL DEFAULT 0 COMMENT '1 是公共的  0不是',
  `is_refresh` int(11) NULL DEFAULT NULL COMMENT '是否刷新  1刷 0 不刷',
  `is_ccreen` int(11) NULL DEFAULT NULL COMMENT '横竖屏 1 横屏  0竖屏',
  `is_scroll` int(11) NULL DEFAULT NULL COMMENT '滚动 1滚 0不滚',
  `is_title` int(11) NULL DEFAULT NULL COMMENT '头  1正常  0不要 2 切换头',
  `back_key` int(11) NULL DEFAULT NULL COMMENT '返回键  1上一页 2 首页 3 js ',
  `button_key` varchar(255) NULL DEFAULT NULL COMMENT '文字  1 不要 其他就是文字',
  `title` varchar(255) NULL DEFAULT NULL COMMENT '头文字',
  `color` varchar(255) NULL DEFAULT NULL COMMENT '颜色',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_app_permission1
-- ----------------------------

-- ----------------------------
-- Table structure for dx_approval_exception_info
-- ----------------------------
DROP TABLE IF EXISTS `dx_approval_exception_info`;
CREATE TABLE `dx_approval_exception_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `scheme_id` int(11) NOT NULL COMMENT '方案id',
  `progress` varchar(1000) NULL DEFAULT NULL COMMENT '项目进展情况',
  `exception` varchar(1000) NULL DEFAULT NULL COMMENT '异常情况概述',
  `exception_file` text NULL COMMENT '异常文件信息[{\"fileId:\"xx\",\"fileName\":\"xx\",\"fileUploader\":\"xx\",\"fileUploadTime\":\"xx\"\"}]',
  `measure` varchar(1000) NULL DEFAULT NULL COMMENT '采取措施',
  `measure_file` text NULL COMMENT '采取措施文件信息[{\"fileId:\"xx\",\"fileName\":\"xx\",\"fileUploader\":\"xx\",\"fileUploadTime\":\"xx\"\"}]',
  `commit_user` varchar(70) NULL DEFAULT NULL COMMENT '提交人',
  `commit_date` datetime(0) NULL DEFAULT NULL COMMENT '提交时间',
  `company_view` varchar(1000) NULL DEFAULT NULL COMMENT '公司意见',
  `company_user` varchar(70) NULL DEFAULT NULL COMMENT '公司提交用户',
  `company_date` varchar(70) NULL DEFAULT NULL COMMENT '公司提交时间',
  `company_status` tinyint(2) NULL DEFAULT 0 COMMENT '公司状态(0:待审查,1:已审查)',
  `expert_view` varchar(1000) NULL DEFAULT NULL COMMENT '专家意见',
  `expert_user` varchar(70) NULL DEFAULT NULL COMMENT '专家提交用户',
  `expert_date` datetime(0) NULL DEFAULT NULL COMMENT '专家提交时间',
  `expert_status` tinyint(2) NULL DEFAULT 0 COMMENT '专家状态(0:待审查,1:已审查)',
  `pro_id` int(11) NOT NULL COMMENT '项目部id',
  `create_user` varchar(70) NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(70) NOT NULL COMMENT '修改人',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '异常信息表';

-- ----------------------------
-- Records of dx_approval_exception_info
-- ----------------------------

-- ----------------------------
-- Table structure for dx_approval_external_expert
-- ----------------------------
DROP TABLE IF EXISTS `dx_approval_external_expert`;
CREATE TABLE `dx_approval_external_expert`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pro_id` int(11) NULL DEFAULT NULL COMMENT '项目部id，0：公司',
  `uId` varchar(70) NULL DEFAULT NULL COMMENT '专家用户id',
  `jId` int(11) NULL DEFAULT NULL COMMENT '职位id',
  `departId` int(11) NULL DEFAULT NULL COMMENT '部门id',
  `name` varchar(50) NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(15) NULL DEFAULT NULL COMMENT '联系电话',
  `technical_post` varchar(100) NULL DEFAULT NULL COMMENT '职称',
  `work_unit` varchar(150) NULL DEFAULT NULL COMMENT '工作单位',
  `current_post` varchar(100) NULL DEFAULT NULL COMMENT '现任职务',
  `label` varchar(1000) NULL DEFAULT NULL COMMENT '专家擅长专业领域,eg:\"道路、桥梁\"',
  `type` tinyint(2) NULL DEFAULT 0 COMMENT '0:公司录入，1：项目部录入',
  `create_time` datetime(0) NOT NULL COMMENT '录入时间',
  `create_user` varchar(255) NOT NULL COMMENT '创建人',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `is_delete` tinyint(2) NULL DEFAULT 0 COMMENT '是否删除 0:未删除、1：删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '施工方案审批专家库';

-- ----------------------------
-- Records of dx_approval_external_expert
-- ----------------------------

-- ----------------------------
-- Table structure for dx_approval_group_expert_audit
-- ----------------------------
DROP TABLE IF EXISTS `dx_approval_group_expert_audit`;
CREATE TABLE `dx_approval_group_expert_audit`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `scheme_id` int(11) NOT NULL COMMENT '方案id',
  `order_no` int(11) NOT NULL COMMENT '审核信息序号(用于标记第几次审核)',
  `update_opinion` varchar(100) NULL DEFAULT NULL COMMENT '修改意见',
  `status` tinyint(2) NULL DEFAULT NULL COMMENT '审核状态(0:通过,1:不通过)',
  `reply_comment` varchar(1000) NULL DEFAULT NULL COMMENT '整改回复意见',
  `create_time` datetime(0) NOT NULL COMMENT '录入时间',
  `create_user` varchar(255) NULL DEFAULT NULL COMMENT '创建人',
  `reply_time` datetime(0) NULL DEFAULT NULL COMMENT '回复时间',
  `reply_user` varchar(255) NULL DEFAULT NULL COMMENT '回复人',
  `is_reply` tinyint(2) NULL DEFAULT NULL COMMENT '该修改意见是否被回复(0:未回复，1：已回复)',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '集团专家审核信息表';

-- ----------------------------
-- Records of dx_approval_group_expert_audit
-- ----------------------------

-- ----------------------------
-- Table structure for dx_approval_internal_expert_audit
-- ----------------------------
DROP TABLE IF EXISTS `dx_approval_internal_expert_audit`;
CREATE TABLE `dx_approval_internal_expert_audit`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `scheme_id` int(11) NOT NULL COMMENT '方案id',
  `order_no` int(11) NOT NULL COMMENT '审核信息序号(用于标记第几次审核)',
  `update_opinion` varchar(1000) NULL DEFAULT NULL COMMENT '修改意见',
  `status` tinyint(2) NULL DEFAULT NULL COMMENT '审核状态(0:通过,1:不通过)',
  `reply_comment` varchar(1000) NULL DEFAULT NULL COMMENT '整改回复意见',
  `create_time` datetime(0) NOT NULL COMMENT '录入时间',
  `create_user` varchar(255) NOT NULL COMMENT '创建人',
  `reply_time` datetime(0) NULL DEFAULT NULL COMMENT '回复时间',
  `reply_user` varchar(255) NULL DEFAULT NULL COMMENT '回复人',
  `is_reply` tinyint(2) NULL DEFAULT NULL COMMENT '该修改意见是否被回复(0:未回复，1：已回复)',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '内部专家审核信息表';

-- ----------------------------
-- Records of dx_approval_internal_expert_audit
-- ----------------------------

-- ----------------------------
-- Table structure for dx_approval_key_process
-- ----------------------------
DROP TABLE IF EXISTS `dx_approval_key_process`;
CREATE TABLE `dx_approval_key_process`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `scheme_id` int(11) NOT NULL COMMENT '方案id',
  `process_info` varchar(1000) NULL DEFAULT NULL COMMENT '关键工序描述',
  `rectify_time` datetime(0) NULL DEFAULT NULL COMMENT '整改时间',
  `question` varchar(1000) NULL DEFAULT NULL COMMENT '问题描述',
  `question_file` text NULL COMMENT '问题描述文件信息[{\"fileId:\"xx\",\"fileName\":\"xx\",\"fileUploader\":\"xx\",\"fileUploadTime\":\"xx\"\"}]',
  `solution` varchar(1000) NULL DEFAULT NULL COMMENT '整改描述',
  `solution_file` text NULL COMMENT '整改描述文件信息[{\"fileId:\"xx\",\"fileName\":\"xx\",\"fileUploader\":\"xx\",\"fileUploadTime\":\"xx\"\"}]',
  `pro_id` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `create_user` varchar(70) NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(70) NOT NULL COMMENT '修改人',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '关键工序信息表';

-- ----------------------------
-- Records of dx_approval_key_process
-- ----------------------------

-- ----------------------------
-- Table structure for dx_approval_scheme_file
-- ----------------------------
DROP TABLE IF EXISTS `dx_approval_scheme_file`;
CREATE TABLE `dx_approval_scheme_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `scheme_id` int(11) NOT NULL COMMENT '方案id',
  `file_id` int(20) NOT NULL COMMENT '文件id',
  `file_name` varchar(255) NOT NULL COMMENT '文件名',
  `stage` tinyint(2) NOT NULL COMMENT '阶段(用来标记文件在哪个阶段上传，0：方案报审、1：内部整改回复、2：集团整改回复、3：外审、4：上传技术交底、5：专家审核上传、6：集团审核上传)',
  `type` tinyint(2) NULL DEFAULT NULL COMMENT '用来标识方案编制阶段，属于哪类文件,0:编制附件、1：内审附件',
  `order_no` int(11) NULL DEFAULT 1 COMMENT '用来记录第几次上传(默认第一次上传)',
  `uploader` varchar(15) NOT NULL COMMENT '上传人',
  `upload_time` datetime(0) NOT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '方案文件关联表';

-- ----------------------------
-- Records of dx_approval_scheme_file
-- ----------------------------

-- ----------------------------
-- Table structure for dx_approval_scheme_flow_diagrams
-- ----------------------------
DROP TABLE IF EXISTS `dx_approval_scheme_flow_diagrams`;
CREATE TABLE `dx_approval_scheme_flow_diagrams`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pro_id` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `project_name` varchar(255) NULL DEFAULT NULL COMMENT '项目名称',
  `scheme_name` varchar(255) NULL DEFAULT NULL COMMENT '方案名称',
  `scheme_level` tinyint(2) NULL DEFAULT NULL COMMENT '方案层级(0:集团,1:公司,2:项目)',
  `current_scheme_level` tinyint(2) NULL DEFAULT NULL COMMENT '现方案层级(0:集团,1:公司,2:项目)',
  `scheme_type` int(11) NULL DEFAULT NULL COMMENT '方案类型',
  `construction_site` int(11) NULL DEFAULT NULL COMMENT '施工部位',
  `plan_submits_date` datetime(0) NULL DEFAULT NULL COMMENT '计划上报日期',
  `plan_construction_date` datetime(0) NULL DEFAULT NULL COMMENT '计划开始施工时间',
  `scheme_info` varchar(1000) NULL DEFAULT NULL COMMENT '方案概述',
  `audit_date` datetime(0) NULL DEFAULT NULL COMMENT '内审日期',
  `update_finish_date` datetime(0) NULL DEFAULT NULL COMMENT '修改完成日期',
  `commit_user` varchar(70) NULL DEFAULT NULL COMMENT '提交人',
  `commit_user_name` varchar(255) NULL DEFAULT NULL COMMENT '提交人姓名',
  `commit_date` datetime(0) NULL DEFAULT NULL COMMENT '提交审核时间',
  `report_status` tinyint(2) NULL DEFAULT NULL COMMENT '上报状态(0:未上报;1:已上报)',
  `report_user` varchar(255) NULL DEFAULT NULL COMMENT '上报人',
  `report_user_name` varchar(255) NULL DEFAULT NULL COMMENT '上报人姓名',
  `report_date` datetime(0) NULL DEFAULT NULL COMMENT '上报时间',
  `actual_submits_date` datetime(0) NULL DEFAULT NULL COMMENT '实际上报日期',
  `audit_info` varchar(1000) NULL DEFAULT NULL COMMENT '审批备注',
  `audit_status` tinyint(2) NULL DEFAULT NULL COMMENT '审批状态(0:未审批,1:已审批)',
  `audit_uid` varchar(255) NULL DEFAULT NULL COMMENT '审核人',
  `audit_uname` varchar(255) NULL DEFAULT NULL COMMENT '审核人姓名',
  `audit_udate` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `is_new` tinyint(2) NULL DEFAULT 0 COMMENT '辅助字段，用来判断公司清单审核中公司人员是否已查看',
  `internal_user_id` varchar(70) NULL DEFAULT NULL COMMENT '内部专家用户id',
  `internal_pro_id` int(11) NULL DEFAULT NULL COMMENT '内部专家项目部id',
  `internal_depart_id` int(11) NULL DEFAULT NULL COMMENT '内部专家部门id',
  `internal_job_id` int(11) NULL DEFAULT NULL COMMENT '内部专家职位id',
  `internal_user_name` varchar(255) NULL DEFAULT NULL COMMENT '内部专家姓名',
  `internal_review_time` datetime(0) NULL DEFAULT NULL COMMENT '内部专家审查时间',
  `internal_general_opinion` varchar(1000) NULL DEFAULT NULL COMMENT '内部专家总体意见',
  `internal_reply_time` datetime(0) NULL DEFAULT NULL COMMENT '内部专家回复时间(用于记录最新回复时间)',
  `internal_status` tinyint(2) NULL DEFAULT 0 COMMENT '内部审核状态(0:未审核,1:整改中,2:已审核)',
  `internal_is_reply` tinyint(2) NULL DEFAULT 0 COMMENT '辅助字段、内部是否已回复,公司审核:0,项目部回复:1',
  `group_id` int(11) NULL DEFAULT NULL COMMENT '集团专家id',
  `group_user_id` varchar(70) NULL DEFAULT NULL COMMENT '集团专家用户id',
  `group_user_name` varchar(255) NULL DEFAULT NULL COMMENT '集团专家姓名',
  `group_review_time` datetime(0) NULL DEFAULT NULL COMMENT '集团专家审查时间',
  `group_general_opinion` varchar(1000) NULL DEFAULT NULL COMMENT '集团专家总体意见',
  `group_commit_user` varchar(70) NULL DEFAULT NULL COMMENT '集团审核提交人',
  `group_commit_date` datetime(0) NULL DEFAULT NULL COMMENT '集团审核提交时间',
  `group_reply_time` datetime(0) NULL DEFAULT NULL COMMENT '集团专家回复时间(用于记录最新回复时间)',
  `engineer_commit_user` varchar(70) NULL DEFAULT NULL COMMENT '总工审核人',
  `engineer_commit_date` datetime(0) NULL DEFAULT NULL COMMENT '总工审核时间',
  `group_status` tinyint(2) NULL DEFAULT 0 COMMENT '集团审核状态(0:未审核,1:总工待审核,2:总工已审核、3：已审核)',
  `group_is_reply` tinyint(2) NULL DEFAULT 0 COMMENT '辅助字段、集团是否已回复,集团审核:0,项目部回复:1',
  `group_reply_comment` varchar(1000) NULL DEFAULT NULL COMMENT '项目部回复信息',
  `group_reply_user` varchar(70) NULL DEFAULT NULL COMMENT '项目部回复人',
  `group_reply_date` datetime(0) NULL DEFAULT NULL COMMENT '项目部回复时间',
  `group_isUpload` tinyint(2) NULL DEFAULT NULL COMMENT '集团是否上传审查意见文件 0 : 未上传 、 1 ： 已上传',
  `group_uploadUser` varchar(70) NULL DEFAULT NULL COMMENT '集团文件上传人',
  `group_uploadTime` datetime(0) NULL DEFAULT NULL COMMENT '集团文件上传时间',
  `external_id` int(11) NULL DEFAULT NULL COMMENT '外部专家id',
  `external_user_id` varchar(70) NULL DEFAULT NULL COMMENT '外部专家用户id',
  `external_user_name` varchar(255) NULL DEFAULT NULL COMMENT '外部专家姓名',
  `external_review_time` datetime(0) NULL DEFAULT NULL COMMENT '外部审查时间',
  `external_general_opinion` varchar(1000) NULL DEFAULT NULL COMMENT '外部总体意见',
  `external_expert_opinion` varchar(1000) NULL DEFAULT NULL COMMENT '外审专家意见',
  `prop_opinion` varchar(1000) NULL DEFAULT NULL COMMENT '业主意见',
  `supervisor_opinion` varchar(1000) NULL DEFAULT NULL COMMENT '监理意见',
  `rectify_reply` varchar(1000) NULL DEFAULT NULL COMMENT '整改回复',
  `external_status` tinyint(2) NULL DEFAULT 0 COMMENT '外部审核状态(0:未审核,1:整改中,2:已审核)',
  `external_uid` varchar(70) NULL DEFAULT NULL COMMENT '外部提交人id',
  `external_time` datetime(0) NULL DEFAULT NULL COMMENT '外部提交时间',
  `is_upload` tinyint(2) NULL DEFAULT NULL COMMENT '是否上传技术交底文件(0:未上传,1:已上传)',
  `upload_user` varchar(70) NULL DEFAULT NULL COMMENT '技术交底文件上传人',
  `upload_time` datetime(0) NULL DEFAULT NULL COMMENT '技术交底文件上传时间',
  `actual_construction_date` datetime(0) NULL DEFAULT NULL COMMENT '实际开始施工时间',
  `construction_end_date` datetime(0) NULL DEFAULT NULL COMMENT '施工结束时间',
  `scheme_status` tinyint(2) NULL DEFAULT NULL COMMENT '方案状态,不同层级有不同的状态(0:未编制,1:已编制,2:公司审核,[3:集团审核],4:外审、监理、业务审核,5:施工准备,6:施工管理)',
  `in_approval` tinyint(2) NULL DEFAULT NULL COMMENT '审批中(0:未审批,1:审批中,2:已审批)',
  `create_time` datetime(0) NOT NULL COMMENT '录入时间',
  `create_user` varchar(255) NOT NULL COMMENT '创建人',
  `create_user_name` varchar(255) NOT NULL COMMENT '创建人姓名',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_user_name` varchar(255) NOT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`id`),
  INDEX `idx_pro_id`(`pro_id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '方案审批流程表';

-- ----------------------------
-- Records of dx_approval_scheme_flow_diagrams
-- ----------------------------

-- ----------------------------
-- Table structure for dx_approval_scheme_reqs_info
-- ----------------------------
DROP TABLE IF EXISTS `dx_approval_scheme_reqs_info`;
CREATE TABLE `dx_approval_scheme_reqs_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `scheme_id` int(11) NOT NULL COMMENT '方案id',
  `type_id` int(11) NOT NULL COMMENT '类型id',
  `req_id` int(11) NOT NULL COMMENT '要求id(这里指当前方案勾选的要求)',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '方案要求关联表(存储方案所选择的要求)';

-- ----------------------------
-- Records of dx_approval_scheme_reqs_info
-- ----------------------------

-- ----------------------------
-- Table structure for dx_approval_scheme_type
-- ----------------------------
DROP TABLE IF EXISTS `dx_approval_scheme_type`;
CREATE TABLE `dx_approval_scheme_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(100) NULL DEFAULT NULL COMMENT '方案类型名称',
  `parent_id` int(11) NULL DEFAULT 0 COMMENT '父节点id',
  `is_leaf` tinyint(2) NULL DEFAULT NULL COMMENT '是否叶子节点(0:是,1:否)',
  `create_time` datetime(0) NOT NULL COMMENT '录入时间',
  `create_user` varchar(255) NOT NULL COMMENT '创建人',
  `create_user_name` varchar(255) NOT NULL COMMENT '创建人姓名',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_user_name` varchar(255) NOT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '方案类型信息表';

-- ----------------------------
-- Records of dx_approval_scheme_type
-- ----------------------------

-- ----------------------------
-- Table structure for dx_approval_scheme_type_reqs
-- ----------------------------
DROP TABLE IF EXISTS `dx_approval_scheme_type_reqs`;
CREATE TABLE `dx_approval_scheme_type_reqs`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type_id` int(11) NOT NULL COMMENT '类型id',
  `require_info` varchar(1000) NULL DEFAULT NULL COMMENT '具体要求信息',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '录入时间',
  `create_user` varchar(255) NULL DEFAULT NULL COMMENT '创建人',
  `create_user_name` varchar(255) NULL DEFAULT NULL COMMENT '创建人姓名',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_user_name` varchar(255) NOT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '方案类型要求信息表';

-- ----------------------------
-- Records of dx_approval_scheme_type_reqs
-- ----------------------------

-- ----------------------------
-- Table structure for dx_approval_supervision_info
-- ----------------------------
DROP TABLE IF EXISTS `dx_approval_supervision_info`;
CREATE TABLE `dx_approval_supervision_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `scheme_id` int(11) NOT NULL COMMENT '方案id',
  `opinion` varchar(1000) NULL DEFAULT NULL COMMENT '意见及建议',
  `requirement` varchar(1000) NULL DEFAULT NULL COMMENT '整改要求说明',
  `requirement_file` text NULL COMMENT '整改要求说明文件信息[{\"fileId:\"xx\",\"fileName\":\"xx\",\"fileUploader\":\"xx\",\"fileUploadTime\":\"xx\"\"}]',
  `requirement_user` varchar(70) NULL DEFAULT NULL COMMENT '整改要求提交人',
  `requirement_date` datetime(0) NULL DEFAULT NULL COMMENT '整改要求提交时间',
  `status` tinyint(2) NULL DEFAULT 0 COMMENT '督导状态(0:待整改,1:待复查,2:不合格待整改,3:不合格待复查,4:已完成)',
  `create_user` varchar(70) NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(70) NOT NULL COMMENT '修改人',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '方案督导信息表';

-- ----------------------------
-- Records of dx_approval_supervision_info
-- ----------------------------

-- ----------------------------
-- Table structure for dx_approval_supervision_reply_info
-- ----------------------------
DROP TABLE IF EXISTS `dx_approval_supervision_reply_info`;
CREATE TABLE `dx_approval_supervision_reply_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `sup_id` int(11) NOT NULL COMMENT '方案督导信息id',
  `scheme_id` int(11) NOT NULL COMMENT '方案id',
  `pro_id` int(11) NOT NULL COMMENT '项目部id',
  `reply` varchar(1000) NULL DEFAULT NULL COMMENT '整改回复',
  `reply_file` text NULL COMMENT '整改回复文件信息[{\"fileId:\"xx\",\"fileName\":\"xx\",\"fileUploader\":\"xx\",\"fileUploadTime\":\"xx\"\"}]',
  `reply_user` varchar(70) NULL DEFAULT NULL COMMENT '整改回复提交人',
  `reply_depart` int(11) NULL DEFAULT NULL COMMENT '整改回复提交人部门id',
  `reply_job` int(11) NULL DEFAULT NULL COMMENT '整改回复提交人职位id',
  `reply_date` datetime(0) NULL DEFAULT NULL COMMENT '整改回复提交时间',
  `expert_view` varchar(1000) NULL DEFAULT NULL COMMENT '专家意见',
  `is_pass` tinyint(2) NULL DEFAULT 0 COMMENT '0:未通过,1:已通过',
  `expert_file` text NULL COMMENT '专家审查上传文件[{\"fileId:\"xx\",\"fileName\":\"xx\",\"fileUploader\":\"xx\",\"fileUploadTime\":\"xx\"\"}]',
  `expert_user` varchar(70) NULL DEFAULT NULL COMMENT '专家用户id',
  `expert_date` datetime(0) NULL DEFAULT NULL COMMENT '专家回复时间',
  `create_user` varchar(70) NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(70) NOT NULL COMMENT '修改人',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '方案督导回复信息表';

-- ----------------------------
-- Records of dx_approval_supervision_reply_info
-- ----------------------------

-- ----------------------------
-- Table structure for dx_butt_joint_dict
-- ----------------------------
DROP TABLE IF EXISTS `dx_butt_joint_dict`;
CREATE TABLE `dx_butt_joint_dict`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(255) NULL DEFAULT NULL COMMENT '总承包单位社会信用代码',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '总承包单位名称',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_butt_joint_dict
-- ----------------------------

-- ----------------------------
-- Table structure for dx_butt_joint_project
-- ----------------------------
DROP TABLE IF EXISTS `dx_butt_joint_project`;
CREATE TABLE `dx_butt_joint_project`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `construction_permit_no` varchar(255) NULL DEFAULT NULL COMMENT '施工许可证编号',
  `app_id` varchar(255) NULL DEFAULT NULL COMMENT 'appid福建省专用',
  `secret_key` varchar(255) NULL DEFAULT NULL COMMENT '秘钥key',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_butt_joint_project
-- ----------------------------

-- ----------------------------
-- Table structure for dx_butt_joint_team
-- ----------------------------
DROP TABLE IF EXISTS `dx_butt_joint_team`;
CREATE TABLE `dx_butt_joint_team`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `team_id` int(11) NOT NULL COMMENT '劳务队id',
  `social_credit_code` varchar(255) NULL DEFAULT NULL COMMENT '社会信用代码',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_butt_joint_team
-- ----------------------------

-- ----------------------------
-- Table structure for dx_class_addscore_config
-- ----------------------------
DROP TABLE IF EXISTS `dx_class_addscore_config`;
CREATE TABLE `dx_class_addscore_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) NULL DEFAULT NULL COMMENT 'm名称',
  `score` double NULL DEFAULT NULL COMMENT '分',
  `type` int(255) NULL DEFAULT NULL COMMENT '分类',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备用字段',
  `flagScore` int(11) NULL DEFAULT NULL COMMENT '加分还是扣分 1减 0 加',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_class_addscore_config
-- ----------------------------

-- ----------------------------
-- Table structure for dx_class_colorconfig
-- ----------------------------
DROP TABLE IF EXISTS `dx_class_colorconfig`;
CREATE TABLE `dx_class_colorconfig`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `green` int(11) NULL DEFAULT NULL COMMENT '绿色的个数',
  `yellow` int(11) NULL DEFAULT NULL COMMENT '黄色的个数',
  `red` int(11) NULL DEFAULT NULL COMMENT '红色的个数',
  `type` int(255) NULL DEFAULT NULL COMMENT '1 班组 2带班人',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_class_colorconfig
-- ----------------------------

-- ----------------------------
-- Table structure for dx_class_evaluation
-- ----------------------------
DROP TABLE IF EXISTS `dx_class_evaluation`;
CREATE TABLE `dx_class_evaluation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `class_id` int(11) NULL DEFAULT NULL COMMENT '班组id',
  `class_leader` varchar(255) NULL DEFAULT NULL COMMENT '班组长名称',
  `class_idcard` varchar(255) NULL DEFAULT NULL COMMENT '班组长身份证号',
  `team_id` int(11) NULL DEFAULT NULL COMMENT '劳务队id',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `construction_type_json` text NULL COMMENT '施工种类json',
  `level_id` int(11) NULL DEFAULT NULL COMMENT 'abc排名id',
  `level_name` varchar(255) NULL DEFAULT NULL COMMENT 'ABC名称',
  `level_soce` double NULL DEFAULT NULL COMMENT 'abc分数赋值得分',
  `peoplesize_id` int(11) NULL DEFAULT NULL COMMENT '队伍规模人数id',
  `people_size` int(11) NULL DEFAULT NULL COMMENT '队伍人数',
  `people_score` double NULL DEFAULT NULL COMMENT '队伍规模得分',
  `construction_year` double NULL DEFAULT NULL COMMENT '施工年限',
  `construction_score` double NULL DEFAULT NULL COMMENT '施工年限得分',
  `year_quarter` varchar(255) NULL DEFAULT NULL COMMENT '季度',
  `partid_json` text NULL COMMENT '部位多选json',
  `partid_score` double NULL DEFAULT NULL COMMENT '部位得分(施工作业难度)',
  `flag` int(11) NULL DEFAULT NULL COMMENT '状态 0 无效  1 有效',
  `construction_content` varchar(255) NULL DEFAULT NULL COMMENT '具体施工内容',
  `file_json` text NULL COMMENT '文件id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_id` varchar(255) NULL DEFAULT NULL COMMENT '创建id',
  `score` double NULL DEFAULT NULL COMMENT '项目得分',
  `credit_score` double NULL DEFAULT NULL COMMENT '信用评价扣分',
  `is_final` int(255) NULL DEFAULT 0 COMMENT '0可以修改 1不可以',
  `phone` varchar(255) NULL DEFAULT NULL COMMENT '班组长电话号',
  `contractor` varchar(255) NULL DEFAULT NULL COMMENT '劳务队负责人',
  `pay_type` int(255) NULL DEFAULT NULL COMMENT '薪酬方式',
  `goal_score` double NULL DEFAULT NULL COMMENT '信用评价得分',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_class_evaluation
-- ----------------------------

-- ----------------------------
-- Table structure for dx_class_evaluationunit
-- ----------------------------
DROP TABLE IF EXISTS `dx_class_evaluationunit`;
CREATE TABLE `dx_class_evaluationunit`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `class_id` int(11) NULL DEFAULT NULL COMMENT '班组id',
  `class_leader` varchar(255) NULL DEFAULT NULL COMMENT '班组长名称',
  `class_idcard` varchar(255) NULL DEFAULT NULL COMMENT '班组长身份证号',
  `team_id` int(11) NULL DEFAULT NULL COMMENT '劳务队id',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `construction_type_json` text NULL COMMENT '施工种类json',
  `level_id` int(11) NULL DEFAULT NULL COMMENT 'abc排名id',
  `level_name` varchar(255) NULL DEFAULT NULL COMMENT 'ABC名称',
  `level_soce` double NULL DEFAULT NULL COMMENT 'abc分数赋值得分',
  `peoplesize_id` int(11) NULL DEFAULT NULL COMMENT '队伍规模人数id',
  `people_size` int(11) NULL DEFAULT NULL COMMENT '队伍人数',
  `people_score` double NULL DEFAULT NULL COMMENT '队伍规模得分',
  `people_level` varchar(255) NULL DEFAULT NULL COMMENT '队伍规模级别',
  `construction_year` double NULL DEFAULT NULL COMMENT '施工年限',
  `construction_score` double NULL DEFAULT NULL COMMENT '施工年限得分',
  `year_quarter` varchar(255) NULL DEFAULT NULL COMMENT '季度',
  `partid_json` text NULL COMMENT '部位多选json',
  `partid_score` double NULL DEFAULT NULL COMMENT '部位得分(施工作业难度)',
  `flag` int(11) NULL DEFAULT NULL COMMENT '状态 0 无效  1 有效',
  `construction_content` varchar(255) NULL DEFAULT NULL COMMENT '具体施工内容',
  `file_json` text NULL COMMENT '文件id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_id` varchar(255) NULL DEFAULT NULL COMMENT '创建id',
  `credit_score` double NULL DEFAULT NULL COMMENT '信用评价扣分',
  `is_final` int(255) NULL DEFAULT 0 COMMENT '0可以修改 1不可以',
  `phone` varchar(255) NULL DEFAULT NULL COMMENT '班组长电话号',
  `contractor` varchar(255) NULL DEFAULT NULL COMMENT '劳务队负责人',
  `pay_type` int(255) NULL DEFAULT NULL COMMENT '薪酬方式',
  `goal_score` double NULL DEFAULT NULL COMMENT '信用评价得分',
  `project_json` text NULL COMMENT '参与项目部json',
  `special_id` int(11) NULL DEFAULT NULL COMMENT '特殊项目id',
  `special_score` double NULL DEFAULT NULL COMMENT '特殊项目加分',
  `unit_score` double NULL DEFAULT NULL COMMENT '公司层面加减分',
  `unit_json` text NULL COMMENT '加减分json',
  `score` double NULL DEFAULT NULL COMMENT '项目得分',
  `final_score` double NULL DEFAULT NULL COMMENT '最终得分',
  `size` int(11) NULL DEFAULT NULL COMMENT '参评次数',
  `history_score` double NULL DEFAULT NULL COMMENT '历史得分',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_class_evaluationunit
-- ----------------------------

-- ----------------------------
-- Table structure for dx_class_leader
-- ----------------------------
DROP TABLE IF EXISTS `dx_class_leader`;
CREATE TABLE `dx_class_leader`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '带班人姓名',
  `idcard` varchar(50) NULL DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(50) NULL DEFAULT NULL COMMENT '电话号码',
  `team_id` int(11) NULL DEFAULT NULL COMMENT '劳务队id',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `join_time` date NULL DEFAULT NULL COMMENT '入场时间',
  `end_time` date NULL DEFAULT NULL COMMENT '退场时间',
  `peopleSize` int(11) NULL DEFAULT NULL COMMENT '峰值人数',
  `teamtype` int(11) NULL DEFAULT NULL COMMENT '施工种类',
  `pay_type` int(11) NULL DEFAULT NULL COMMENT '支付方式  1 股份制 2工资',
  `updateUserid` varchar(255) NULL DEFAULT NULL COMMENT '操作人id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_class_leader
-- ----------------------------

-- ----------------------------
-- Table structure for dx_class_partid
-- ----------------------------
DROP TABLE IF EXISTS `dx_class_partid`;
CREATE TABLE `dx_class_partid`  (
  `partid` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '部位id',
  `part_name` varchar(30) NOT NULL COMMENT '部位名称',
  `pid` varchar(25) NULL DEFAULT NULL COMMENT 'parent',
  `lqs` smallint(6) NULL DEFAULT NULL COMMENT '分类标识:1(路),2(桥),3(隧)',
  `sl` smallint(6) NULL DEFAULT NULL COMMENT '类别:1(水),2(陆)',
  `score` double(6, 0) NULL DEFAULT NULL COMMENT '分数',
  `array_sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`partid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_class_partid
-- ----------------------------

-- ----------------------------
-- Table structure for dx_class_peoplesize
-- ----------------------------
DROP TABLE IF EXISTS `dx_class_peoplesize`;
CREATE TABLE `dx_class_peoplesize`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `level` varchar(255) NULL DEFAULT NULL COMMENT '级别',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `size` int(11) NULL DEFAULT NULL COMMENT '人数',
  `score` double NULL DEFAULT NULL COMMENT '得分',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_class_peoplesize
-- ----------------------------

-- ----------------------------
-- Table structure for dx_class_team
-- ----------------------------
DROP TABLE IF EXISTS `dx_class_team`;
CREATE TABLE `dx_class_team`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `class_leader` varchar(20) NULL DEFAULT NULL COMMENT '班组长姓名',
  `idcard` varchar(100) NULL DEFAULT NULL COMMENT '班组长身份证号',
  `phone` varchar(100) NULL DEFAULT NULL COMMENT '班组长负责人',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `team_id` int(11) NULL DEFAULT NULL COMMENT '劳务队id',
  `update_time` date NULL DEFAULT NULL COMMENT '新增时间',
  `update_userid` varchar(255) NULL DEFAULT NULL COMMENT '修改人',
  `peoplesize` int(11) NULL DEFAULT NULL COMMENT '队伍峰值人数',
  `pay_type` int(11) NULL DEFAULT NULL COMMENT '薪酬方式 1 承包制  2 工资制度',
  `type_of_construction` int(11) NULL DEFAULT NULL COMMENT '施工种类 单选',
  `construction_of_the_professional` int(11) NULL DEFAULT NULL COMMENT '施工专业',
  `join_time` date NULL DEFAULT NULL COMMENT '加入时间',
  `end_time` date NULL DEFAULT NULL COMMENT '退场时间',
  PRIMARY KEY (`id`),
  INDEX `index_type_of_construction`(`type_of_construction`),
  INDEX `index_construction_of_the_professional`(`construction_of_the_professional`),
  INDEX `index_proid`(`proid`),
  INDEX `index_team_id`(`team_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_class_team
-- ----------------------------

-- ----------------------------
-- Table structure for dx_class_team_credit_rating
-- ----------------------------
DROP TABLE IF EXISTS `dx_class_team_credit_rating`;
CREATE TABLE `dx_class_team_credit_rating`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `credit_name` varchar(255) NULL DEFAULT NULL COMMENT 'a1=因为施工原因发生重大安全事故',
  `score` double NULL DEFAULT NULL COMMENT '所有扣的分数',
  `class_id` int(11) NULL DEFAULT NULL COMMENT '班组id',
  `year_quarter` varchar(255) NULL DEFAULT NULL COMMENT '季度数',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `status` varchar(255) NULL DEFAULT NULL COMMENT '分类标识 CATEGORY_G',
  `type` int(11) NULL DEFAULT NULL COMMENT '1 班组 2 带班人',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_class_team_credit_rating
-- ----------------------------

-- ----------------------------
-- Table structure for dx_class_team_levelsize
-- ----------------------------
DROP TABLE IF EXISTS `dx_class_team_levelsize`;
CREATE TABLE `dx_class_team_levelsize`  (
  `id` smallint(6) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `A_plus` smallint(6) NULL DEFAULT NULL COMMENT 'A+',
  `size` smallint(6) NOT NULL COMMENT '次数',
  `A` smallint(6) NULL DEFAULT NULL COMMENT 'B+',
  `B_plus` smallint(6) NULL DEFAULT NULL,
  `B` smallint(6) NULL DEFAULT NULL,
  `C` smallint(6) NULL DEFAULT NULL,
  `D_plus` smallint(6) NULL DEFAULT NULL,
  `D` smallint(6) NULL DEFAULT NULL,
  `E_plus` smallint(6) NULL DEFAULT NULL,
  `E` smallint(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_class_team_levelsize
-- ----------------------------

-- ----------------------------
-- Table structure for dx_class_team_type
-- ----------------------------
DROP TABLE IF EXISTS `dx_class_team_type`;
CREATE TABLE `dx_class_team_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pid` int(11) NULL DEFAULT NULL COMMENT '父级id  0顶级父类',
  `construction_type_name` varchar(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_class_team_type
-- ----------------------------

-- ----------------------------
-- Table structure for dx_configure
-- ----------------------------
DROP TABLE IF EXISTS `dx_configure`;
CREATE TABLE `dx_configure`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父级id',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '名字',
  `order_by` int(11) NULL DEFAULT NULL COMMENT '排序',
  `is_bottom` tinyint(4) NULL DEFAULT 1 COMMENT '是否最底层',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '类别 1项目部分类 2单位类别',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_configure
-- ----------------------------

-- ----------------------------
-- Table structure for dx_daythings_interviewer
-- ----------------------------
DROP TABLE IF EXISTS `dx_daythings_interviewer`;
CREATE TABLE `dx_daythings_interviewer`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `taskId` int(20) NOT NULL COMMENT '任务id',
  `uid` varchar(200) NULL DEFAULT NULL COMMENT '可以访问的人id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_daythings_interviewer
-- ----------------------------

-- ----------------------------
-- Table structure for dx_daythings_personliable
-- ----------------------------
DROP TABLE IF EXISTS `dx_daythings_personliable`;
CREATE TABLE `dx_daythings_personliable`  (
  `userId` varchar(50) NULL DEFAULT NULL COMMENT '人员id',
  `targetId` int(20) NULL DEFAULT NULL COMMENT '目标id',
  `id` int(30) NOT NULL AUTO_INCREMENT COMMENT 'id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_daythings_personliable
-- ----------------------------

-- ----------------------------
-- Table structure for dx_daythings_replycontent
-- ----------------------------
DROP TABLE IF EXISTS `dx_daythings_replycontent`;
CREATE TABLE `dx_daythings_replycontent`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `taskId` int(10) NULL DEFAULT NULL COMMENT '任务id',
  `targerId` int(10) NULL DEFAULT NULL COMMENT '目标id',
  `parentId` int(10) NULL DEFAULT NULL COMMENT '父级id  0是对目标的回复',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `reply_to` varchar(55) NULL DEFAULT NULL COMMENT '回复的对象',
  `content` varchar(2000) NULL DEFAULT NULL COMMENT '回复内容',
  `replier_uid` varchar(70) NULL DEFAULT NULL COMMENT '回复人id',
  `replier` varchar(55) NULL DEFAULT NULL COMMENT '回复人',
  `replier_remark` varchar(55) NULL DEFAULT NULL COMMENT '回复人备注',
  `is_del` tinyint(4) NULL DEFAULT NULL COMMENT '删除标记 0未删除 1已删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_daythings_replycontent
-- ----------------------------

-- ----------------------------
-- Table structure for dx_daythings_targer
-- ----------------------------
DROP TABLE IF EXISTS `dx_daythings_targer`;
CREATE TABLE `dx_daythings_targer`  (
  `targerId` int(10) NOT NULL AUTO_INCREMENT COMMENT '目标id',
  `taskId` int(10) NULL DEFAULT NULL COMMENT '工作id',
  `targer_content` varchar(2000) NULL DEFAULT NULL COMMENT '目标内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `stop_time` date NULL DEFAULT NULL COMMENT '截止时间',
  `schedule` varchar(20) NULL DEFAULT NULL COMMENT '目标进度',
  `person_liable` text NULL COMMENT '责任人',
  `person_liable_name` text NULL COMMENT '责任人名',
  `person_number` smallint(6) NOT NULL DEFAULT 0 COMMENT '责任人数',
  `user_recordA` text NULL COMMENT '人员字段1',
  `user_recordB` text NULL COMMENT '人员字段2',
  `goalId` varchar(55) NULL DEFAULT NULL COMMENT '目标编号(目标1,目标2)',
  `finish_notes` varchar(2000) NULL DEFAULT NULL COMMENT '目标结果',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '目标状态 0未完结 1已完结',
  `is_del` tinyint(4) NULL DEFAULT NULL COMMENT '删除标记 0未删除 1已删除',
  PRIMARY KEY (`targerId`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_daythings_targer
-- ----------------------------

-- ----------------------------
-- Table structure for dx_daythings_task
-- ----------------------------
DROP TABLE IF EXISTS `dx_daythings_task`;
CREATE TABLE `dx_daythings_task`  (
  `taskId` int(10) NOT NULL AUTO_INCREMENT COMMENT '工作id',
  `task_name` varchar(2000) NULL DEFAULT NULL COMMENT '工作名称',
  `create_time` date NULL DEFAULT NULL COMMENT '创建时间',
  `stop_time` date NULL DEFAULT NULL COMMENT '截至日期',
  `create_uid` varchar(70) NULL DEFAULT NULL COMMENT '创建人员id',
  `create_user` varchar(55) NULL DEFAULT NULL COMMENT '创建人',
  `visit_users` text NULL COMMENT '访问人员的id',
  `user_number` smallint(6) NULL DEFAULT NULL COMMENT '访问人数',
  `visit_users_cun` text NULL COMMENT '查看可访问的人数组',
  `visit_users_del` text NULL COMMENT '删除可访问的人数组',
  `report_users` text NULL COMMENT '上报人的id',
  `report_users_number` smallint(6) NULL DEFAULT NULL COMMENT '上报人的数量',
  `report_users_cun` text NULL COMMENT '查看上报人的数组',
  `report_users_del` text NULL COMMENT '删除上报人的数组',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '工作状态 0未完结 1已完结',
  `is_del` tinyint(4) NULL DEFAULT NULL COMMENT '删除标记 0未删除 1已删除',
  PRIMARY KEY (`taskId`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_daythings_task
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_address
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_address`;
CREATE TABLE `dx_device_address`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `deviceId` int(20) NULL DEFAULT NULL COMMENT '设备id',
  `address` varchar(50) NULL DEFAULT NULL COMMENT '设备存放地点',
  `proid` int(20) NULL DEFAULT NULL COMMENT '项目部id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_address
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_allot
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_allot`;
CREATE TABLE `dx_device_allot`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `allot_date` datetime(0) NULL DEFAULT NULL COMMENT '调拨完成日期',
  `device_id` varchar(50) NULL DEFAULT NULL COMMENT '设备id',
  `handoverPerson` varchar(50) NULL DEFAULT NULL COMMENT '交接人id',
  `applyTime` datetime(0) NULL DEFAULT NULL COMMENT '申请时间',
  `allotType` int(2) NULL DEFAULT NULL COMMENT '调拨类型(1.调入2调出)',
  `allotState` int(2) NULL DEFAULT NULL COMMENT '调拨状态(1.调拨申请2,调拨接收3,调拨完成)',
  `allotFlowState` varchar(10) NULL DEFAULT NULL COMMENT '流程状态(1等待接收2等待审核3调拨成功4调拨失败)',
  `proid` int(10) NULL DEFAULT NULL COMMENT '设备所在项目id',
  `explain` varchar(500) NULL DEFAULT NULL COMMENT '调出备注说明',
  `outProid` int(10) NULL DEFAULT NULL COMMENT '调出项目部id',
  `recipient` varchar(20) NULL DEFAULT NULL COMMENT '预定接受人信息',
  `opinion` varchar(200) NULL DEFAULT NULL COMMENT '拒绝原因',
  `receiptTime` datetime(0) NULL DEFAULT NULL COMMENT '接受时间',
  `workCnt` varchar(255) NULL DEFAULT NULL COMMENT '设备生产总量',
  `outWard` varchar(255) NULL DEFAULT NULL COMMENT '外观描述',
  `natureFunction` varchar(255) NULL DEFAULT NULL COMMENT '性能描述',
  `otherFunction` varchar(255) NULL DEFAULT NULL COMMENT '其他描述',
  `partsList` varchar(255) NULL DEFAULT NULL COMMENT '配件列表',
  `toolsList` varchar(255) NULL DEFAULT NULL COMMENT '工具列表',
  `operationBook` varchar(255) NULL DEFAULT NULL COMMENT '使用说明书',
  `otherFile` varchar(255) NULL DEFAULT NULL COMMENT '其他资料',
  `groundPic` varchar(255) NULL DEFAULT NULL COMMENT '地基基础图',
  `checkConclusion` int(2) NULL DEFAULT NULL COMMENT '检查结论(1完好2堪用3待修4报废)',
  `reviceOpinion` text NULL COMMENT '接收意见',
  `verifyPersonId` varchar(50) NULL DEFAULT NULL COMMENT '公司审核人id',
  `realReviceId` varchar(50) NULL DEFAULT NULL COMMENT '实际接收人',
  `exceptTime` date NULL DEFAULT NULL COMMENT '预计时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_allot
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_cardnumber
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_cardnumber`;
CREATE TABLE `dx_device_cardnumber`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(20) NULL DEFAULT NULL COMMENT '人员id',
  `userCardNumber` varchar(30) NULL DEFAULT NULL COMMENT '人员证书编号',
  `proid` int(20) NULL DEFAULT NULL COMMENT '项目部名称',
  `technicalName` varchar(200) NULL DEFAULT NULL COMMENT '证书职称',
  `cardName` varchar(200) NULL DEFAULT NULL COMMENT '证书名称',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_cardnumber
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_check
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_check`;
CREATE TABLE `dx_device_check`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `supervision_report` int(20) NULL DEFAULT -1 COMMENT '检验报告',
  `use_card` int(10) NULL DEFAULT -1 COMMENT '使用登记卡',
  `device_id` int(11) NULL DEFAULT NULL COMMENT '设备id',
  `proid` int(20) NULL DEFAULT NULL COMMENT '项目部id',
  `check_date` date NULL DEFAULT NULL COMMENT '检验日期',
  `next_date` date NULL DEFAULT NULL COMMENT '下次检验日期',
  `supervision_reportName` varchar(50) NULL DEFAULT NULL COMMENT '监督检验报告',
  `use_cardName` varchar(50) NULL DEFAULT NULL COMMENT '使用登记卡',
  `belong` int(20) NULL DEFAULT NULL COMMENT '属于（1。公司项目部2.租赁，3.劳务）',
  `warnTime` int(20) NULL DEFAULT 0 COMMENT '预警次数',
  `isUpdate` int(2) NULL DEFAULT 1 COMMENT '判断预计次数是否修改(0 未修改过  1修改过)',
  `markcard` int(20) NULL DEFAULT NULL COMMENT '特种设备设备使用标志证',
  `markcardName` varchar(200) NULL DEFAULT NULL COMMENT '使用标志名称',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_check
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_costprice
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_costprice`;
CREATE TABLE `dx_device_costprice`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `deviceId` int(20) NULL DEFAULT NULL COMMENT '项目id',
  `driverUnitprice` double NULL DEFAULT NULL COMMENT '机长方量单价',
  `maintainUnitprice` double NULL DEFAULT NULL COMMENT '合同维保单价',
  `totalPrice` double NULL DEFAULT NULL COMMENT '合同总单价',
  `workingCost` double NULL DEFAULT NULL COMMENT '设备月使用费用',
  `mainRate` double NULL DEFAULT NULL COMMENT '维保分成比率',
  `earnRate` double NULL DEFAULT NULL COMMENT '收益分成比率',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_costprice
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_device
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_device`;
CREATE TABLE `dx_device_device`  (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `device_cost` varchar(50) NULL DEFAULT NULL COMMENT '设备原值',
  `device_name` varchar(100) NULL DEFAULT NULL COMMENT '设备名称',
  `manufacturer` varchar(100) NULL DEFAULT NULL COMMENT '生产厂家',
  `purchase_date` datetime(0) NULL DEFAULT NULL COMMENT '购置年月',
  `power` double NULL DEFAULT NULL COMMENT '功率',
  `load` double NULL DEFAULT NULL COMMENT '自重',
  `belong` int(2) NULL DEFAULT NULL COMMENT '属于(1.公司2.项目)',
  `proid` int(2) NULL DEFAULT 0 COMMENT '项目部id',
  `management_number` varchar(200) NULL DEFAULT NULL COMMENT '管理号码',
  `state` int(2) NULL DEFAULT NULL COMMENT '分类（1.常规设备2.特种设备）',
  `running_state` int(2) NULL DEFAULT NULL COMMENT '运行状态(1.再用2.闲置拟自留 3.闲置申请调拨 4.报废5.预警)',
  `labor_team` varchar(20) NULL DEFAULT NULL COMMENT '劳务队名称',
  `contract` int(20) NULL DEFAULT -1 COMMENT '采购合同',
  `drawing` int(20) NULL DEFAULT -1 COMMENT '图纸',
  `qualified_book` int(20) NULL DEFAULT -1 COMMENT '合格证书',
  `invoice` int(20) NULL DEFAULT -1 COMMENT '发票',
  `specification` varchar(100) NULL DEFAULT NULL COMMENT '规格型号',
  `isQuote` int(2) NULL DEFAULT NULL COMMENT '是否是从公司引用设备（1是 2 否)',
  `import_Date` datetime(0) NULL DEFAULT NULL COMMENT '引用时间',
  `original_proid` int(20) NULL DEFAULT NULL COMMENT '原项目部id',
  `contractName` varchar(50) NULL DEFAULT NULL COMMENT '合同名称',
  `drawingName` varchar(50) NULL DEFAULT NULL COMMENT '图纸名称',
  `qualified_bookName` varchar(50) NULL DEFAULT NULL COMMENT '合格证书名称',
  `invoiceName` varchar(50) NULL DEFAULT NULL COMMENT '发票名称',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `oldId` int(20) NULL DEFAULT NULL COMMENT '引入项目部后记录固资设备id',
  `carNumber` varchar(100) NULL DEFAULT NULL COMMENT '车牌号码',
  `deviceClassify` int(10) NULL DEFAULT NULL COMMENT '设备类型',
  `own` int(10) NULL DEFAULT NULL COMMENT '属于(1.公司2.项目部)',
  `leaveFactory_date` date NULL DEFAULT NULL COMMENT '出厂年月',
  `address` varchar(50) NULL DEFAULT NULL COMMENT '设备存放工点',
  `installState` int(20) NULL DEFAULT 2 COMMENT '安装状态',
  `dismantleState` int(20) NULL DEFAULT 2 COMMENT '拆除状态',
  `allotState` int(20) NULL DEFAULT 0 COMMENT '调拨状态',
  `isDistribution` int(20) NULL DEFAULT 0 COMMENT '是否分配',
  `distributionTime` date NULL DEFAULT NULL COMMENT '分配时间',
  `recipient` varchar(50) NULL DEFAULT NULL COMMENT '接受人',
  `recipientTime` date NULL DEFAULT NULL COMMENT '接受时间',
  `mainSpecifications` varchar(100) NULL DEFAULT NULL COMMENT '主要规格',
  `mixstate` int(2) NULL DEFAULT NULL,
  `existTime` date NULL DEFAULT NULL COMMENT '退场时间',
  `oldmanagementNumber` varchar(200) NULL DEFAULT NULL COMMENT '旧管理号码',
  `leaveCard` varchar(200) NULL DEFAULT NULL COMMENT '出厂编号',
  `olddeviceCost` varchar(200) NULL DEFAULT NULL COMMENT '财务原值（浪潮）',
  `kind` varchar(200) NULL DEFAULT NULL COMMENT '设备种类',
  `aliasName` varchar(200) NULL DEFAULT NULL COMMENT '设备别名',
  `listof` int(20) NULL DEFAULT -1 COMMENT '装车清单',
  `listofName` varchar(50) NULL DEFAULT NULL COMMENT '装车清单名称',
  `newdevicetype` varchar(50) NULL DEFAULT NULL COMMENT '设备类型',
  `device_id` varchar(50) NULL DEFAULT NULL COMMENT '设备辅助Id',
  INDEX `id`(`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_device
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_dismantle
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_dismantle`;
CREATE TABLE `dx_device_dismantle`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `people_number` varchar(255) NULL DEFAULT NULL COMMENT '配合人数',
  `mechanic_number` varchar(255) NULL DEFAULT NULL COMMENT '配合机械',
  `dismantle_day` varchar(50) NULL DEFAULT NULL COMMENT '拆除天数',
  `labor_cost` double NULL DEFAULT 0 COMMENT '人工费用',
  `mechanic_cost` double NULL DEFAULT 0 COMMENT '机械费用',
  `device_id` int(11) NULL DEFAULT NULL COMMENT '设备id',
  `proid` int(20) NULL DEFAULT NULL COMMENT '项目部id',
  `belong` int(20) NULL DEFAULT NULL COMMENT '属于（1。公司项目部2.租赁，3.劳务）',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_dismantle
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_dismantlecost
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_dismantlecost`;
CREATE TABLE `dx_device_dismantlecost`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `proid` int(20) NULL DEFAULT NULL COMMENT '项目部id',
  `deviceId` int(20) NULL DEFAULT NULL COMMENT '设备id',
  `dismantleCost` double NULL DEFAULT NULL COMMENT '拆除花费',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_dismantlecost
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_dismantlefile
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_dismantlefile`;
CREATE TABLE `dx_device_dismantlefile`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `deviceId` int(20) NULL DEFAULT NULL COMMENT '设备id',
  `userCard` varchar(200) NULL DEFAULT NULL COMMENT '人证合一照片',
  `record` varchar(200) NULL DEFAULT NULL COMMENT '旁站记录',
  `accomplish` varchar(200) NULL DEFAULT NULL COMMENT '完成效果图',
  `belong` int(2) NULL DEFAULT NULL COMMENT '属于（1公司项目部,2租赁,3.劳务）',
  `removeTime` date NULL DEFAULT NULL COMMENT '拆除时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_dismantlefile
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_driver
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_driver`;
CREATE TABLE `dx_device_driver`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userName` varchar(50) NULL DEFAULT NULL COMMENT '司机名称',
  `gender` int(2) NULL DEFAULT NULL COMMENT '1男  2  女',
  `idcard` varchar(18) NULL DEFAULT NULL COMMENT '身份证号',
  `address` varchar(50) NULL DEFAULT NULL COMMENT '家庭住址',
  `deviceId` int(20) NULL DEFAULT NULL COMMENT '设备id',
  `phone` varchar(15) NULL DEFAULT NULL COMMENT '手机号',
  `age` int(3) NULL DEFAULT NULL COMMENT '年龄',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_driver
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_drivercard
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_drivercard`;
CREATE TABLE `dx_device_drivercard`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `driver` varchar(20) NULL DEFAULT NULL COMMENT '责任司机',
  `user_card` varchar(50) NULL DEFAULT NULL COMMENT '人员编号',
  `deviceId` int(20) NULL DEFAULT NULL COMMENT '设备id',
  `proid` int(20) NULL DEFAULT NULL COMMENT '项目部id',
  `belong` int(20) NULL DEFAULT NULL COMMENT '属于',
  `special_card` int(20) NULL DEFAULT -1 COMMENT '司机特种作业证件',
  `network_check` int(20) NULL DEFAULT -1 COMMENT '网上验证',
  `network_checkName` varchar(50) NULL DEFAULT NULL COMMENT '网上验证名称',
  `special_cardName` varchar(50) NULL DEFAULT NULL COMMENT '司机特种作业证件名称',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_drivercard
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_entrance
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_entrance`;
CREATE TABLE `dx_device_entrance`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `device_id` int(20) NULL DEFAULT NULL COMMENT '公司项目设备id',
  `car_type` varchar(500) NULL DEFAULT NULL COMMENT '车型',
  `enter_cost` double NULL DEFAULT 0 COMMENT '运输费用',
  `kilometre` double NULL DEFAULT NULL COMMENT '运输公里数',
  `detailed_list` int(5) NULL DEFAULT -1 COMMENT '装车清单',
  `proid` int(10) NULL DEFAULT NULL COMMENT '项目id',
  `detailedlistName` varchar(50) NULL DEFAULT NULL COMMENT '装车清单',
  `entranceTime` date NULL DEFAULT NULL COMMENT '进场时间',
  `loadingCost` double NULL DEFAULT 0 COMMENT '装车费用',
  `unloadCost` double NULL DEFAULT 0 COMMENT '卸车费用',
  `loadingAddress` varchar(50) NULL DEFAULT NULL COMMENT '装车地点',
  `unloadAddress` varchar(50) NULL DEFAULT NULL COMMENT '卸车地点',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_entrance
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_entrancecost
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_entrancecost`;
CREATE TABLE `dx_device_entrancecost`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `proid` int(10) NULL DEFAULT NULL COMMENT '项目部id',
  `deviceId` int(20) NULL DEFAULT NULL COMMENT '设备id',
  `entranceCost` double NULL DEFAULT 0 COMMENT '进场费用历史记录',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_entrancecost
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_error_log
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_error_log`;
CREATE TABLE `dx_device_error_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `message` text NOT NULL COMMENT '错误信息',
  `error_from` int(255) NOT NULL COMMENT '错误信息来源(1:c#;2:java)',
  `insert_time` datetime(0) NOT NULL COMMENT '插入时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_error_log
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_inspection
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_inspection`;
CREATE TABLE `dx_device_inspection`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `examineDevice` varchar(50) NULL DEFAULT NULL COMMENT '巡检设备',
  `deviceClassify` int(10) NULL DEFAULT NULL COMMENT '设备类型',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_inspection
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_inspection_content
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_inspection_content`;
CREATE TABLE `dx_device_inspection_content`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `content` varchar(200) NULL DEFAULT NULL COMMENT '巡检内容',
  `conclusion` varchar(500) NULL DEFAULT NULL COMMENT '验收结论',
  `describe` varchar(500) NULL DEFAULT NULL COMMENT '描述',
  `deviceId` int(10) NULL DEFAULT NULL COMMENT '设备id',
  `belong` int(10) NULL DEFAULT NULL COMMENT '属于(1公司 2劳务 3 租赁)',
  `isExamined` int(2) NULL DEFAULT 0 COMMENT '是否已经检查过（0未检查1检查）',
  `picture` varchar(6000) NULL DEFAULT '' COMMENT '设备机容机貌',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `numbers` int(20) NULL DEFAULT 1 COMMENT '巡检次数',
  `proid` int(10) NULL DEFAULT NULL COMMENT '项目部id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_inspection_content
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_inspection_historycontent
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_inspection_historycontent`;
CREATE TABLE `dx_device_inspection_historycontent`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `content` varchar(200) NULL DEFAULT NULL COMMENT '巡检内容',
  `conclusion` varchar(200) NULL DEFAULT NULL COMMENT '内容选项',
  `describe` varchar(200) NULL DEFAULT NULL COMMENT '描述',
  `deviceId` int(20) NULL DEFAULT NULL COMMENT 'deviceId',
  `belong` int(2) NULL DEFAULT NULL COMMENT '属于',
  `isExamined` int(2) NULL DEFAULT NULL COMMENT '是否已检查',
  `picture` varchar(200) NULL DEFAULT NULL COMMENT '图片',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `numbers` int(2) NULL DEFAULT NULL COMMENT '巡检次数',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_inspection_historycontent
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_install
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_install`;
CREATE TABLE `dx_device_install`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `people_number` varchar(255) NULL DEFAULT NULL COMMENT '配合人数',
  `mechanic_number` varchar(255) NULL DEFAULT NULL COMMENT '配合机械',
  `install_day` varchar(11) NULL DEFAULT NULL COMMENT '天数',
  `labor_cost` double NULL DEFAULT 0 COMMENT '人工费',
  `mechanic_cost` double NULL DEFAULT 0 COMMENT '机械费',
  `device_id` int(11) NULL DEFAULT NULL COMMENT '设备id',
  `state` int(2) NULL DEFAULT NULL COMMENT '分类（1.常规设备2.特种设备）',
  `proid` int(10) NULL DEFAULT NULL COMMENT '项目id',
  `belong` int(2) NULL DEFAULT NULL COMMENT '属于（1。公司项目部2.租赁，3.劳务）',
  `installDate` date NULL DEFAULT NULL COMMENT '安装时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_install
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_installcost
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_installcost`;
CREATE TABLE `dx_device_installcost`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `proid` int(20) NULL DEFAULT NULL COMMENT '项目部id',
  `deviceId` int(20) NULL DEFAULT NULL COMMENT '设备id',
  `installCost` double NULL DEFAULT NULL COMMENT '安装费用',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_installcost
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_installfile
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_installfile`;
CREATE TABLE `dx_device_installfile`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `deviceId` int(20) NULL DEFAULT NULL COMMENT '设备id',
  `userCard` varchar(200) NULL DEFAULT NULL COMMENT '人证合一照片',
  `record` varchar(200) NULL DEFAULT NULL COMMENT '旁站记录',
  `accomplish` varchar(200) NULL DEFAULT NULL COMMENT '完成效果图',
  `belong` int(2) NULL DEFAULT NULL COMMENT '属于（1公司2.项目部3劳务,4.租赁）',
  `installTime` date NULL DEFAULT NULL COMMENT '安装时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_installfile
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_label
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_label`;
CREATE TABLE `dx_device_label`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `labelName` varchar(100) NULL DEFAULT NULL COMMENT '标签名',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_label
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_labor_device
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_labor_device`;
CREATE TABLE `dx_device_labor_device`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `labor_name` varchar(20) NULL DEFAULT NULL COMMENT '劳务队名称',
  `device_name` varchar(20) NULL DEFAULT NULL COMMENT '设备名称',
  `management_number` varchar(20) NULL DEFAULT NULL COMMENT '管理号码',
  `device_models` varchar(50) NULL DEFAULT NULL COMMENT '规格型号',
  `manufacturer` varchar(20) NULL DEFAULT NULL COMMENT '生产厂家',
  `running_state` int(2) NULL DEFAULT NULL COMMENT '运行状态（1.再用 2.闲置报停）',
  `proid` int(20) NULL DEFAULT NULL COMMENT '项目id',
  `leaveFactory_date` date NULL DEFAULT NULL COMMENT '出厂时间',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `belong` int(2) NOT NULL DEFAULT 3 COMMENT '属于劳务',
  `deviceClassify` int(10) NULL DEFAULT NULL COMMENT '设备类型',
  `address` varchar(50) NULL DEFAULT NULL COMMENT '设备存放工点',
  `installState` int(2) NULL DEFAULT 2 COMMENT '安装状态',
  `dismantleState` int(2) NULL DEFAULT 2 COMMENT '拆除状态',
  `state` int(2) NULL DEFAULT NULL COMMENT '1常规2特种',
  `carNumber` varchar(20) NULL DEFAULT NULL COMMENT '车牌号',
  `laborpeople` varchar(50) NULL DEFAULT NULL COMMENT '劳务队责任人',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_labor_device
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_lease
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_lease`;
CREATE TABLE `dx_device_lease`  (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `device_models` varchar(20) NULL DEFAULT NULL COMMENT '规则型号',
  `device_name` varchar(20) NULL DEFAULT NULL COMMENT '设备名称',
  `leaveFactory_date` date NULL DEFAULT NULL COMMENT '出场年月',
  `manufacturer` varchar(20) NULL DEFAULT NULL COMMENT '生产厂家',
  `manufacturerLease` varchar(20) NULL DEFAULT NULL COMMENT '租赁厂家',
  `running_state` int(2) NULL DEFAULT NULL COMMENT '运行状态（1.再用 2.闲置报停）',
  `lease_user` varchar(5) NULL DEFAULT NULL COMMENT '租赁人',
  `belong` int(5) NOT NULL DEFAULT 4 COMMENT '属于租赁',
  `management_number` varchar(50) NULL DEFAULT NULL COMMENT '管理号码（劳务LW+编码 项目ZL+编码）',
  `deviceClassify` int(2) NULL DEFAULT NULL COMMENT '设备分类（1.起重机2.泵陈）',
  `apply` int(2) NULL DEFAULT NULL COMMENT '租赁申报表',
  `proid` int(20) NULL DEFAULT NULL COMMENT '项目部id',
  `applyName` varchar(20) NULL DEFAULT NULL COMMENT '租赁申报表名称',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `deviceType` int(10) NULL DEFAULT NULL COMMENT '设备类型',
  `leasePrice` varchar(50) NULL DEFAULT NULL COMMENT '租赁单价',
  `address` varchar(50) NULL DEFAULT NULL COMMENT '设备存放工点',
  `installState` int(2) NULL DEFAULT 2 COMMENT '安装状态',
  `dismantleState` int(2) NULL DEFAULT 2 COMMENT '拆除状态',
  `state` int(2) NULL DEFAULT NULL COMMENT '状态(1常规2 特种)',
  `carNumber` varchar(20) NULL DEFAULT NULL COMMENT '车牌号',
  `leaseType` int(20) NULL DEFAULT NULL COMMENT '租赁状态 1  方/元   2 月/元',
  INDEX `id`(`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_lease
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_leisure
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_leisure`;
CREATE TABLE `dx_device_leisure`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `device_id` int(20) NULL DEFAULT NULL COMMENT '设备id',
  `leisure_photo` varchar(500) NULL DEFAULT NULL COMMENT '闲置照片上传',
  `cause` int(3) NULL DEFAULT NULL COMMENT '闲置原因',
  `remark` varchar(100) NULL DEFAULT NULL COMMENT '备注',
  `stopTime` date NULL DEFAULT NULL COMMENT '报停时间',
  `belong` int(2) NULL DEFAULT NULL COMMENT '属于',
  `state` int(2) NULL DEFAULT NULL COMMENT '状态(8.闲置拟留用9.闲置申请挑拨)',
  `applyState` int(2) NULL DEFAULT NULL COMMENT '申请状态',
  `applyTime` datetime(0) NULL DEFAULT NULL COMMENT '申请时间',
  `recipient` varchar(50) NULL DEFAULT NULL COMMENT '操作人',
  `opinion` varchar(100) NULL DEFAULT NULL COMMENT '拒绝原因',
  `proid` int(10) NULL DEFAULT NULL COMMENT '项目部id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_leisure
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_lifecycle
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_lifecycle`;
CREATE TABLE `dx_device_lifecycle`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `proid` int(20) NULL DEFAULT NULL COMMENT '项目id',
  `deviceId` int(20) NULL DEFAULT NULL COMMENT '设备id',
  `state` int(20) NULL DEFAULT NULL COMMENT '状态',
  `enterTime` date NULL DEFAULT NULL COMMENT '进场时间',
  `exitTime` date NULL DEFAULT NULL COMMENT '退场时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_lifecycle
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_maintain
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_maintain`;
CREATE TABLE `dx_device_maintain`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `maintainCost` double NULL DEFAULT 0 COMMENT '月维修费用',
  `maintainPhtot` varchar(200) NULL DEFAULT NULL COMMENT '维修时照片',
  `costList` int(20) NULL DEFAULT NULL COMMENT '费用清单',
  `invoice` int(20) NULL DEFAULT NULL COMMENT '发票',
  `creatTime` date NULL DEFAULT NULL COMMENT '添加时间',
  `remark` varchar(200) NULL DEFAULT NULL COMMENT '备注',
  `deviceId` int(20) NULL DEFAULT NULL COMMENT '设备id',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `belong` int(2) NULL DEFAULT NULL COMMENT '属于（1.公司项目部，2劳务，3租赁）',
  `numbers` int(20) NULL DEFAULT 0 COMMENT '维护次数',
  `isupdate` int(2) NULL DEFAULT 1 COMMENT '是否修改过（0 未修改 1修改过）',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_maintain
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_maintaincost
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_maintaincost`;
CREATE TABLE `dx_device_maintaincost`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `proid` int(20) NULL DEFAULT NULL COMMENT '项目部id',
  `deviceId` int(20) NULL DEFAULT NULL COMMENT '设备id',
  `maintainCost` double NULL DEFAULT NULL COMMENT '维修费用',
  `invoice` int(20) NULL DEFAULT NULL COMMENT '发票',
  `costList` int(20) NULL DEFAULT NULL COMMENT '费用清单',
  `maintainPhtot` varchar(200) NULL DEFAULT NULL COMMENT '维修照片',
  `createTime` date NULL DEFAULT NULL COMMENT '时间',
  `invoiceName` varchar(50) NULL DEFAULT NULL COMMENT '发票名',
  `costListName` varchar(50) NULL DEFAULT NULL COMMENT '清单名称',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_maintaincost
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_patrol
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_patrol`;
CREATE TABLE `dx_device_patrol`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `patrolType` varchar(20) NULL DEFAULT NULL COMMENT '巡检类型',
  `username` varchar(10) NULL DEFAULT NULL COMMENT '巡检人',
  `patrolTime` date NULL DEFAULT NULL COMMENT '巡检时间',
  `deviceName` varchar(10) NULL DEFAULT NULL COMMENT '设备名称',
  `deviceId` int(10) NULL DEFAULT NULL COMMENT '设备id',
  `proid` int(10) NULL DEFAULT NULL COMMENT '项目id',
  `isSubmit` int(2) NULL DEFAULT NULL COMMENT '是否为正式提交（1是2否）',
  `belong` int(2) NULL DEFAULT NULL COMMENT '属于（1公司项目部2劳务3租赁）',
  `managementNumber` varchar(50) NULL DEFAULT NULL COMMENT '管理号码',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_patrol
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_quantity
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_quantity`;
CREATE TABLE `dx_device_quantity`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `deviceId` int(20) NULL DEFAULT NULL COMMENT '设备id',
  `workload` double NULL DEFAULT NULL COMMENT '工作量',
  `createTime` date NULL DEFAULT NULL COMMENT '创建时间',
  `normed` double NULL DEFAULT NULL COMMENT '核算定额（元/方）',
  `proid` int(20) NULL DEFAULT NULL COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_quantity
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_quantityhistory
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_quantityhistory`;
CREATE TABLE `dx_device_quantityhistory`  (
  `deviceId` int(20) NULL DEFAULT NULL COMMENT '设备id',
  `workload` double NULL DEFAULT NULL COMMENT '工作量',
  `createTime` date NULL DEFAULT NULL COMMENT '创建时间',
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `normed` double NULL DEFAULT NULL COMMENT '核算定额（元/方）',
  `proid` int(20) NULL DEFAULT NULL COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_quantityhistory
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_safetyinspection_detail
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_safetyinspection_detail`;
CREATE TABLE `dx_device_safetyinspection_detail`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `deviceid` int(20) NULL DEFAULT NULL COMMENT '巡检设备id',
  `content` varchar(500) NULL DEFAULT NULL COMMENT '巡检内容',
  `conclusion` varchar(500) NULL DEFAULT NULL COMMENT '验收结论',
  `picture` varchar(500) NULL DEFAULT NULL COMMENT '验收图片',
  `accountfor` varchar(500) NULL DEFAULT NULL COMMENT '说明',
  `conclusiontwo` varchar(500) NULL DEFAULT NULL COMMENT '验收结论2',
  `isMultiple` int(20) NULL DEFAULT NULL COMMENT '是否多选',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_safetyinspection_detail
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_scrap
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_scrap`;
CREATE TABLE `dx_device_scrap`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `device_id` int(20) NULL DEFAULT NULL COMMENT '设备id',
  `photo` varchar(200) NULL DEFAULT NULL COMMENT '机容机貌',
  `cause` int(2) NULL DEFAULT NULL COMMENT '原因',
  `remark` varchar(200) NULL DEFAULT NULL COMMENT '备注',
  `applyState` int(10) NULL DEFAULT NULL COMMENT '申请状态',
  `applyTime` datetime(0) NULL DEFAULT NULL COMMENT '申请时间',
  `recipient` varchar(50) NULL DEFAULT NULL COMMENT '操作人',
  `opinion` varchar(200) NULL DEFAULT NULL COMMENT '拒绝原因',
  `proid` int(20) NULL DEFAULT NULL COMMENT '项目部id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_scrap
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_selfcheck
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_selfcheck`;
CREATE TABLE `dx_device_selfcheck`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `deviceId` int(20) NULL DEFAULT NULL COMMENT '设备id',
  `manufacturerSelfCheck` int(20) NULL DEFAULT NULL COMMENT '厂家自检',
  `proSelfCheck` int(20) NULL DEFAULT NULL COMMENT '项目自检',
  `belong` int(20) NULL DEFAULT NULL COMMENT '属于（1.公司2.项目部3.劳务4.租赁）',
  `manufacturerSelfCheckName` varchar(50) NULL DEFAULT NULL COMMENT '厂家自检名称',
  `proSelfCheckName` varchar(50) NULL DEFAULT NULL COMMENT '项目自检名称',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_selfcheck
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_template
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_template`;
CREATE TABLE `dx_device_template`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `template` int(20) NULL DEFAULT NULL COMMENT '模板',
  `templateName` varchar(20) NULL DEFAULT NULL COMMENT '模板名',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_template
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_train
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_train`;
CREATE TABLE `dx_device_train`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `traincontent` varchar(50) NULL DEFAULT NULL COMMENT '培训内容',
  `trainUser` varchar(20) NULL DEFAULT NULL COMMENT '培训负责人',
  `trainTime` datetime(0) NULL DEFAULT NULL COMMENT '培训时间',
  `content` text NULL COMMENT '培训内容概述',
  `trainCourseware` varchar(500) NULL DEFAULT NULL COMMENT '培训课件',
  `signIn` varchar(500) NULL DEFAULT NULL COMMENT '现场签到表',
  `trainPhtot` varchar(500) NULL DEFAULT NULL COMMENT '现场培训照片',
  `proId` int(20) NULL DEFAULT NULL COMMENT '项目部id',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `count` int(20) NULL DEFAULT 1,
  `warn` int(2) NULL DEFAULT -1 COMMENT '状态(-1正常   1  报警)',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_train
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_type
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_type`;
CREATE TABLE `dx_device_type`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `typeName` varchar(50) NULL DEFAULT NULL COMMENT '类型名称',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_type
-- ----------------------------

-- ----------------------------
-- Table structure for dx_device_warn
-- ----------------------------
DROP TABLE IF EXISTS `dx_device_warn`;
CREATE TABLE `dx_device_warn`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `monthWarn` varchar(200) NULL DEFAULT NULL COMMENT '月租赁费预警',
  `workWarn` varchar(200) NULL DEFAULT NULL COMMENT '方量单价预警',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_device_warn
-- ----------------------------

-- ----------------------------
-- Table structure for dx_dict
-- ----------------------------
DROP TABLE IF EXISTS `dx_dict`;
CREATE TABLE `dx_dict`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `pid` int(11) NOT NULL,
  `tag` varchar(255) NOT NULL,
  `remark` varchar(50) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_dict
-- ----------------------------

-- ----------------------------
-- Table structure for dx_dix_car_travel
-- ----------------------------
DROP TABLE IF EXISTS `dx_dix_car_travel`;
CREATE TABLE `dx_dix_car_travel`  (
  `trid` smallint(6) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `out_id` smallint(6) NOT NULL COMMENT '分车id',
  `line` text NOT NULL COMMENT '运行路线',
  `remark` varchar(100) NOT NULL COMMENT '备注',
  PRIMARY KEY (`trid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_dix_car_travel
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_attributes
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_attributes`;
CREATE TABLE `dx_erp_attributes`  (
  `tid` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '属性id',
  `identity_name` varchar(40) NULL DEFAULT NULL COMMENT '属性名称',
  `type_code` smallint(6) NULL DEFAULT NULL COMMENT '分类',
  `order_index` smallint(6) NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(100) NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`tid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_attributes
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_device_contract
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_device_contract`;
CREATE TABLE `dx_erp_device_contract`  (
  `device_file_id` varchar(50) NOT NULL COMMENT '设备合同文件Id',
  `device_id` varchar(50) NOT NULL COMMENT '设备Id',
  `file_type` int(255) NOT NULL COMMENT '文件类型(1合格证2采购合同3相关图纸4装车图纸)',
  `file_id` int(255) NOT NULL COMMENT '文件Id',
  `file_name` varchar(255) NOT NULL COMMENT '文件名称',
  PRIMARY KEY (`device_file_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_device_contract
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_feedback
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_feedback`;
CREATE TABLE `dx_erp_feedback`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` tinyint(1) NOT NULL COMMENT '是否表扬(0:否,1:是)',
  `first_type` int(11) NULL DEFAULT NULL COMMENT '一级分类',
  `second_type` int(11) NULL DEFAULT NULL COMMENT '二级分类',
  `modules` varchar(255) NULL DEFAULT NULL COMMENT '栏目地址',
  `data` varchar(255) NULL DEFAULT NULL COMMENT '具体数据',
  `question` varchar(4000) NOT NULL COMMENT '问题',
  `files` varchar(2000) NULL DEFAULT NULL COMMENT '附件:[{\"fid\":\"\"}]',
  `level` int(11) NULL DEFAULT NULL COMMENT '问题等级',
  `bonuses` decimal(10, 2) NULL DEFAULT NULL COMMENT '奖励金额',
  `bonuses_status` int(11) NOT NULL COMMENT '奖励状态(0:审核中,1:待发放,2:已发放,-1:无奖励)',
  `confirm_user` varchar(255) NULL DEFAULT NULL COMMENT '确认人',
  `confirm_username` varchar(255) NULL DEFAULT NULL COMMENT '确认人',
  `confirm_result_status` int(11) NULL DEFAULT NULL COMMENT '确认结果(0:无效反馈,1:普通反馈,2:已知反馈,3:被采纳反馈)',
  `confirm_result` varchar(4000) NULL DEFAULT NULL COMMENT '确认结果详情',
  `confirm_date` datetime(0) NULL DEFAULT NULL,
  `confirm_files` varchar(2000) NULL DEFAULT NULL,
  `handle_user` varchar(255) NULL DEFAULT NULL COMMENT '处理人',
  `handle_user_name` varchar(255) NULL DEFAULT NULL COMMENT '处理人姓名',
  `handle_result` varchar(2000) NULL DEFAULT NULL COMMENT '处理结果详情',
  `handle_date` datetime(0) NULL DEFAULT NULL COMMENT '处理日期',
  `handle_files` varchar(2000) NULL DEFAULT NULL COMMENT '处理附件:[{\"fid\":\"\"}]',
  `status` int(11) NOT NULL COMMENT '状态:(0:新反馈,1:处理中,2:已完结)',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  `project_name` varchar(255) NOT NULL COMMENT '项目部名称',
  `create_user_name` varchar(255) NOT NULL COMMENT '创建人',
  `phone` varchar(255) NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) NULL DEFAULT NULL COMMENT '邮箱',
  `we_chat` varchar(255) NULL DEFAULT NULL COMMENT '微信',
  `qq` varchar(255) NULL DEFAULT NULL COMMENT 'QQ',
  `create_user` varchar(255) NOT NULL COMMENT '创建人id',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_feedback
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_file
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_file`;
CREATE TABLE `dx_erp_file`  (
  `fid` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) NOT NULL COMMENT '文件名',
  `path` varchar(225) NOT NULL COMMENT '文件路径',
  `uploader` varchar(15) NOT NULL COMMENT '上传人',
  `upload_time` datetime(0) NOT NULL COMMENT '上传时间',
  `has_sync` tinyint(1) NOT NULL COMMENT '是否已同步(0:已同步,1:未同步)',
  `has_delete` tinyint(1) NOT NULL COMMENT '是否已删除(0:已同步,1:未同步)',
  PRIMARY KEY (`fid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_file
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_form_data_preview
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_form_data_preview`;
CREATE TABLE `dx_erp_form_data_preview`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `total` int(11) NULL DEFAULT NULL COMMENT '全员人数',
  `formal_total` int(11) NULL DEFAULT NULL COMMENT '正式员工_总人数',
  `formal_non_post` int(11) NULL DEFAULT NULL COMMENT '正式员工_非在岗人数',
  `formal_cadre` int(11) NULL DEFAULT NULL COMMENT '正式员工_干部人数',
  `formal_wright` int(11) NULL DEFAULT NULL COMMENT '正式员工_工人人数',
  `informal_contract_worker` int(11) NULL DEFAULT NULL COMMENT '非正式员工_合同工人数',
  `informal_others` int(11) NULL DEFAULT NULL COMMENT '非正式员工_其他',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_form_data_preview
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_help
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_help`;
CREATE TABLE `dx_erp_help`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `module` varchar(255) NOT NULL COMMENT '所属模块',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `fname` varchar(255) NOT NULL COMMENT '文件名',
  `fid` int(11) NOT NULL COMMENT '帮助文件',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_help
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_approval_table
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_approval_table`;
CREATE TABLE `dx_erp_hrs_approval_table`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` varchar(70) NULL DEFAULT NULL COMMENT '用户id',
  `name` varchar(50) NULL DEFAULT NULL COMMENT '姓名',
  `sex` varchar(2) NULL DEFAULT NULL COMMENT '性别',
  `birthday` date NULL DEFAULT NULL COMMENT '出生年月',
  `join_job_Time` date NULL DEFAULT NULL COMMENT '参加工作时间',
  `education` varchar(20) NULL DEFAULT NULL COMMENT '文化程度',
  `finish_school` varchar(30) NULL DEFAULT NULL COMMENT '毕业院校',
  `major` varchar(20) NULL DEFAULT NULL COMMENT '所学专业',
  `position` varchar(30) NULL DEFAULT NULL COMMENT '职务',
  `workType` varchar(30) NULL DEFAULT NULL COMMENT '工种',
  `skill_name` varchar(20) NULL DEFAULT NULL COMMENT '技术职称等级',
  `approval_type` varchar(30) NULL DEFAULT NULL COMMENT '审批表类型',
  `approval_num` varchar(30) NULL DEFAULT NULL COMMENT '审批表编号',
  `transfer_reason` varchar(20) NULL DEFAULT NULL COMMENT '调动原因',
  `former_unit` varchar(30) NULL DEFAULT NULL COMMENT '原单位',
  `apply_unit` varchar(30) NULL DEFAULT NULL COMMENT '申请调入单位',
  `appendix` int(11) NULL DEFAULT NULL COMMENT '附件',
  `appendix_name` varchar(30) NULL DEFAULT NULL COMMENT '附件名',
  `creator_uid` varchar(70) NULL DEFAULT NULL COMMENT '创建人员id',
  `create_time` date NULL DEFAULT NULL COMMENT '创建时间',
  `is_del` tinyint(2) NOT NULL DEFAULT 0 COMMENT '删除标记,0未删除,1已删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_approval_table
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_certified_allowance
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_certified_allowance`;
CREATE TABLE `dx_erp_hrs_certified_allowance`  (
  `cert_id` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '执业资格id',
  `cert_name` varchar(40) NULL DEFAULT NULL COMMENT '资格名称',
  `cert_type_id` smallint(6) NULL DEFAULT NULL COMMENT '资格分类id',
  `cert_typ` varchar(40) NULL DEFAULT NULL COMMENT '资格类型',
  `is_architect` tinyint(4) NULL DEFAULT NULL COMMENT '是否建造师',
  `first_allowance` double NULL DEFAULT NULL COMMENT '一次性奖励津贴',
  `monthly_allowance` double NULL DEFAULT NULL COMMENT '月补助津贴',
  PRIMARY KEY (`cert_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_certified_allowance
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_directories_config
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_directories_config`;
CREATE TABLE `dx_erp_hrs_directories_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id 机关0',
  `uid` varchar(70) NULL DEFAULT NULL COMMENT '人员id',
  `jid` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `flag` tinyint(4) NOT NULL DEFAULT 1 COMMENT '标记 1通讯录隐藏电话  2查看电话的角色或人员（项目部）',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_directories_config
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_document
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_document`;
CREATE TABLE `dx_erp_hrs_document`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `number` varchar(30) NULL DEFAULT NULL COMMENT '文件编号',
  `name` varchar(50) NULL DEFAULT NULL COMMENT '文件名称',
  `key_word` varchar(50) NULL DEFAULT NULL COMMENT '关键字',
  `summary` varchar(200) NULL DEFAULT NULL COMMENT '文件概述',
  `create_uid` varchar(70) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` date NULL DEFAULT NULL COMMENT '创建时间',
  `appendix` int(11) NULL DEFAULT NULL COMMENT '附件',
  `appendixName` varchar(30) NULL DEFAULT NULL COMMENT '附件名称',
  `is_del` tinyint(2) NOT NULL DEFAULT 0 COMMENT '删除标记,0未删除,1已删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_document
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_document_template
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_document_template`;
CREATE TABLE `dx_erp_hrs_document_template`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(30) NULL DEFAULT NULL COMMENT '模板名称',
  `typeId` smallint(6) NULL DEFAULT NULL COMMENT '模板类型id',
  `content` text NULL COMMENT '内容',
  `user` varchar(50) NULL DEFAULT NULL COMMENT '操作人员id',
  `create_time` date NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_document_template
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_file_template
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_file_template`;
CREATE TABLE `dx_erp_hrs_file_template`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(30) NULL DEFAULT NULL COMMENT '模板名称',
  `typeId` smallint(6) NULL DEFAULT NULL COMMENT '模板类型id',
  `appendixId` int(11) NULL DEFAULT NULL COMMENT '附件id',
  `user` varchar(50) NULL DEFAULT NULL COMMENT '操作人员id',
  `create_time` date NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_file_template
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_labor_contract
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_labor_contract`;
CREATE TABLE `dx_erp_hrs_labor_contract`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` varchar(70) NULL DEFAULT NULL COMMENT '人员id',
  `name` varchar(50) NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(15) NULL DEFAULT NULL COMMENT '联系方式',
  `number` varchar(40) NULL DEFAULT NULL COMMENT '合同编号',
  `contract_name` varchar(40) NULL DEFAULT NULL COMMENT '合同名称',
  `typeId` tinyint(2) NULL DEFAULT NULL COMMENT '合同类型id',
  `type` varchar(50) NULL DEFAULT NULL COMMENT '合同类型',
  `term` varchar(10) NULL DEFAULT NULL COMMENT '合同期限',
  `start_time` date NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` date NULL DEFAULT NULL COMMENT '结束时间',
  `appendixId` int(11) NULL DEFAULT NULL COMMENT '附件id',
  `appendix_name` varchar(50) NULL DEFAULT NULL COMMENT '附件名',
  `overdue` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否生效:0合同生效,1合同终止',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_labor_contract
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_missive
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_missive`;
CREATE TABLE `dx_erp_hrs_missive`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `number` varchar(30) NULL DEFAULT NULL COMMENT '函件编号',
  `title` varchar(60) NULL DEFAULT NULL COMMENT '函件标题',
  `typeId` tinyint(4) NULL DEFAULT NULL COMMENT '人函1，劳务派遣函2',
  `genreId` smallint(6) NULL DEFAULT NULL COMMENT '函件分类',
  `genre_name` varchar(30) NULL DEFAULT NULL COMMENT '函件类型',
  `key_word` varchar(255) NULL DEFAULT NULL COMMENT '关键字',
  `appendixId` int(11) NULL DEFAULT NULL COMMENT '附件id',
  `appendix_create_time` varchar(55) NULL DEFAULT NULL COMMENT '附件创建时间',
  `appendix_update_time` varchar(55) NULL DEFAULT NULL COMMENT '附件更新时间',
  `appendix_page_size` varchar(55) NULL DEFAULT NULL COMMENT '附件页数',
  `appendix_content` text NULL COMMENT '附件简要内容',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `creator_uid` varchar(70) NULL DEFAULT NULL COMMENT '创建人员id',
  `creator_user` varchar(30) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` date NULL DEFAULT NULL COMMENT '创建时间',
  `check_user` varchar(30) NULL DEFAULT NULL COMMENT '审核人员',
  `check_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `check_remark` varchar(255) NULL DEFAULT NULL COMMENT '审核意见',
  `flag` tinyint(2) NOT NULL DEFAULT 0 COMMENT '状态 -1审核未通过 0待审核 1同意',
  `is_del` tinyint(2) NOT NULL DEFAULT 0 COMMENT '0未删除,1已删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_missive
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_person_check
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_person_check`;
CREATE TABLE `dx_erp_hrs_person_check`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` varchar(70) NOT NULL COMMENT '人员id',
  `info_flag` smallint(4) NULL DEFAULT 0 COMMENT '基本信息审核 0否 1已审核',
  `info_remark` varchar(255) NULL DEFAULT NULL COMMENT '基本信息备注',
  `info_audit_uid` varchar(70) NULL DEFAULT NULL COMMENT '基本信息审核人员id',
  `info_audit_name` varchar(50) NULL DEFAULT NULL COMMENT '基本信息审核人',
  `info_audit_date` date NULL DEFAULT NULL COMMENT '基本信息审核时间',
  `experience_flag` smallint(4) NULL DEFAULT 0 COMMENT '经历信息审核 0否 1已审核',
  `experience_remark` varchar(255) NULL DEFAULT NULL COMMENT '经历信息备注',
  `experience_audit_uid` varchar(70) NULL DEFAULT NULL COMMENT '经历信息审核人员id',
  `experience_audit_name` varchar(50) NULL DEFAULT NULL COMMENT '经历信息审核人',
  `experience_audit_date` date NULL DEFAULT NULL COMMENT '经历信息审核时间',
  `certificate_flag` smallint(4) NULL DEFAULT 0 COMMENT '资质信息审核 0否 1已审核',
  `certificate_remark` varchar(255) NULL DEFAULT NULL COMMENT '资质信息备注',
  `certificate_audit_uid` varchar(70) NULL DEFAULT NULL COMMENT '资质信息审核人员id',
  `certificate_audit_name` varchar(50) NULL DEFAULT NULL COMMENT '资质信息审核人',
  `certificate_audit_date` date NULL DEFAULT NULL COMMENT '资质信息审核时间',
  `proresume_flag` smallint(4) NULL DEFAULT 0 COMMENT '项目标签审核 0否 1已审核',
  `proresume_remark` varchar(255) NULL DEFAULT NULL COMMENT '项目标签备注',
  `proresume_audit_uid` varchar(70) NULL DEFAULT NULL COMMENT '项目标签审核人员id',
  `proresume_audit_name` varchar(50) NULL DEFAULT NULL COMMENT '项目标签审核人',
  `proresume_audit_date` date NULL DEFAULT NULL COMMENT '项目标签审核时间',
  `wage_flag` smallint(4) NULL DEFAULT 0 COMMENT '薪酬标签审核 0否 1已审核',
  `wage_remark` varchar(255) NULL DEFAULT NULL COMMENT '薪酬标签备注',
  `wage_audit_uid` varchar(70) NULL DEFAULT NULL COMMENT '薪酬标签审核人员id',
  `wage_audit_name` varchar(50) NULL DEFAULT NULL COMMENT '薪酬标签审核人',
  `wage_audit_date` date NULL DEFAULT NULL COMMENT '薪酬标签审核时间',
  `sum_flag` smallint(4) NULL DEFAULT 0 COMMENT '是否审核完成 0否 1是',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_person_check
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_province
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_province`;
CREATE TABLE `dx_erp_hrs_province`  (
  `id` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `province` varchar(30) NULL DEFAULT NULL COMMENT '省',
  `city` varchar(1500) NULL DEFAULT NULL COMMENT '市/县',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_province
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_recommendation
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_recommendation`;
CREATE TABLE `dx_erp_hrs_recommendation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` date NULL DEFAULT NULL COMMENT '创建时间',
  `number` varchar(50) NULL DEFAULT NULL COMMENT '工资介绍信编号',
  `proId` smallint(6) NULL DEFAULT NULL COMMENT '项目部id',
  `uid` varchar(70) NULL DEFAULT NULL COMMENT '人员id',
  `name` varchar(30) NULL DEFAULT NULL COMMENT '姓名',
  `sex` varchar(5) NULL DEFAULT NULL COMMENT '性别',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `education` varchar(30) NULL DEFAULT NULL COMMENT '文化程度',
  `join_job_time` date NULL DEFAULT NULL COMMENT '参加工作时间',
  `join_com_time` date NULL DEFAULT NULL COMMENT '进入企业时间',
  `work` varchar(40) NULL DEFAULT NULL COMMENT '岗位',
  `skill_name` varchar(40) NULL DEFAULT NULL COMMENT '职称（技能等级）',
  `get_time` date NULL DEFAULT NULL COMMENT '获取时间',
  `output_company` varchar(50) NULL DEFAULT NULL COMMENT '调出单位',
  `input_company` varchar(50) NULL DEFAULT NULL COMMENT '调入单位',
  `transfer_type` varchar(30) NULL DEFAULT NULL COMMENT '调动类型',
  `output_salary_data` varchar(2000) NULL DEFAULT NULL COMMENT '调出工资数据',
  `input_salary_data` varchar(2000) NULL DEFAULT NULL COMMENT '调入工资数据',
  `travel_expenses` varchar(100) NULL DEFAULT NULL COMMENT '预借差旅费',
  `output_wages_time` varchar(30) NULL DEFAULT NULL COMMENT '调出工资发放时间',
  `input_wages_time` varchar(30) NULL DEFAULT NULL COMMENT '调入工资发放时间',
  `visit_vocation_start_year` varchar(10) NULL DEFAULT NULL COMMENT '探望假开始年',
  `visit_vocation_end_year` varchar(10) NULL DEFAULT NULL COMMENT '探望假结束年',
  `visit_vocation_flag` tinyint(2) NULL DEFAULT NULL COMMENT '探望假是否休假 0未休1已休',
  `year_vocation_flag` tinyint(2) NULL DEFAULT NULL COMMENT '当年是否休假',
  `endowment_insurance_time` varchar(30) NULL DEFAULT NULL COMMENT '养老保险时间',
  `housing_fund_time` varchar(30) NULL DEFAULT NULL COMMENT '住房公积金时间',
  `year_deduction_time` varchar(30) NULL DEFAULT NULL COMMENT '年金时间',
  `medical_insurance_time` varchar(30) NULL DEFAULT NULL COMMENT '医保金时间',
  `remark` varchar(500) NULL DEFAULT NULL COMMENT '补充说明',
  `appendixId` int(11) NULL DEFAULT NULL COMMENT '附件id',
  `appendix_name` varchar(50) NULL DEFAULT NULL COMMENT '附件名',
  `creatorUid` varchar(70) NULL DEFAULT NULL COMMENT '创建人员id',
  `type` smallint(6) NULL DEFAULT NULL COMMENT '介绍信类型',
  `is_del` tinyint(2) NULL DEFAULT 0 COMMENT '是否删除 0已删除 1未删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_recommendation
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_reduction
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_reduction`;
CREATE TABLE `dx_erp_hrs_reduction`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` varchar(70) NOT NULL COMMENT '人员id',
  `leaveId` smallint(6) NULL DEFAULT NULL COMMENT '减员原因id',
  `leave_reason` varchar(30) NULL DEFAULT NULL COMMENT '减员原因',
  `leave_date` date NULL DEFAULT NULL COMMENT '减员时间',
  `leave_going` varchar(30) NULL DEFAULT NULL COMMENT '减员去向',
  `archivesId` int(11) NULL DEFAULT NULL COMMENT '档案情况id',
  `archives` varchar(30) NULL DEFAULT NULL COMMENT '档案情况',
  `apply_date` date NULL DEFAULT NULL COMMENT '申请时间',
  `apply_type` tinyint(4) NULL DEFAULT NULL COMMENT '减员类型类型:0待减员,1减员',
  `check_date` date NULL DEFAULT NULL COMMENT '审核时间',
  `checkUser` varchar(70) NULL DEFAULT NULL COMMENT '审核人员',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '审核状态 -1拒绝,0待审核,1同意,2过期',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_reduction
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_relevant_config
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_relevant_config`;
CREATE TABLE `dx_erp_hrs_relevant_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `depart_id` smallint(6) NULL DEFAULT NULL COMMENT '机关部门id',
  `depart_name` varchar(55) NULL DEFAULT NULL COMMENT '机关部门名称',
  `unit_info` varchar(3000) NULL DEFAULT NULL COMMENT '机关层面配置信息',
  `project_info` varchar(3000) NULL DEFAULT NULL COMMENT '项目层面配置信息',
  `opt_uid` varchar(70) NULL DEFAULT NULL COMMENT '操作人uid',
  `opt_user` varchar(70) NULL DEFAULT NULL COMMENT '操作人',
  `opt_date` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_relevant_config
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_report
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_report`;
CREATE TABLE `dx_erp_hrs_report`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `report_type` varchar(20) NULL DEFAULT NULL COMMENT '报告类型',
  `report_num` varchar(30) NULL DEFAULT NULL COMMENT '报告编号',
  `report_name` varchar(30) NULL DEFAULT NULL COMMENT '报告名称',
  `key_word` varchar(30) NULL DEFAULT NULL COMMENT '关键字',
  `content` text NULL COMMENT '报告内容',
  `appendix` int(11) NULL DEFAULT NULL COMMENT '附件',
  `creator_uid` varchar(70) NULL DEFAULT NULL COMMENT '创建人员id',
  `create_time` date NULL DEFAULT NULL COMMENT '创建时间',
  `is_del` tinyint(2) NOT NULL DEFAULT 0 COMMENT '删除标记,0未删除,1已删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_report
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_resource_record
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_resource_record`;
CREATE TABLE `dx_erp_hrs_resource_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` varchar(70) NULL DEFAULT NULL COMMENT 'userid',
  `user_name` varchar(55) NULL DEFAULT NULL COMMENT '姓名',
  `proid` smallint(6) NULL DEFAULT NULL COMMENT '调动单位id',
  `project_name` varchar(100) NULL DEFAULT NULL COMMENT '调动单位',
  `departid` smallint(6) NULL DEFAULT NULL COMMENT '调动部门id',
  `depart` varchar(55) NULL DEFAULT NULL COMMENT '调动部门',
  `jid` smallint(6) NULL DEFAULT NULL COMMENT '调动职位id',
  `job_name` varchar(55) NULL DEFAULT NULL COMMENT '调动职位',
  `allowed_transfer_date` date NULL DEFAULT NULL COMMENT '允许调动时间',
  `transfer_date` date NULL DEFAULT NULL COMMENT '申请调动时间',
  `apply_uid` varchar(70) NULL DEFAULT NULL COMMENT '申请人uid',
  `apply_user` varchar(55) NULL DEFAULT NULL COMMENT '申请人',
  `apply_date` datetime(0) NULL DEFAULT NULL COMMENT '申请时间',
  `apply_remark` varchar(355) NULL DEFAULT NULL COMMENT '申请备注',
  `actual_transfer_date` date NULL DEFAULT NULL COMMENT '实际调动时间',
  `audit_user` varchar(55) NULL DEFAULT NULL COMMENT '审核人',
  `audit_date` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `audit_remark` varchar(355) NULL DEFAULT NULL COMMENT '审核备注',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '申请状态 0待审核 -1拒绝 1同意',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_resource_record
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_resource_share
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_resource_share`;
CREATE TABLE `dx_erp_hrs_resource_share`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` varchar(70) NOT NULL COMMENT '人员id',
  `user_name` varchar(55) NULL DEFAULT NULL COMMENT '人员姓名',
  `identityId` smallint(6) NULL DEFAULT NULL COMMENT '身份类型id',
  `identity` varchar(55) NULL DEFAULT NULL COMMENT '身份类型',
  `proid` smallint(6) NULL DEFAULT NULL COMMENT '项目id',
  `project_name` varchar(55) NULL DEFAULT NULL COMMENT '项目名称',
  `departid` smallint(6) NULL DEFAULT NULL COMMENT '单位id',
  `depart` varchar(55) NULL DEFAULT NULL COMMENT '单位名称',
  `jid` smallint(6) NULL DEFAULT NULL COMMENT '职位id',
  `job_name` varchar(55) NULL DEFAULT NULL COMMENT '职位',
  `job_remark` varchar(55) NULL DEFAULT NULL COMMENT '职位简称',
  `join_time` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `transfer_date` date NULL DEFAULT NULL COMMENT '可调动时间',
  `apply_count` smallint(6) NOT NULL DEFAULT 0 COMMENT '申请人数',
  `operate_uid` varchar(70) NULL DEFAULT NULL COMMENT '操作人员id',
  `operate_user` varchar(55) NULL DEFAULT NULL COMMENT '操作人员',
  `operate_date` datetime(0) NULL DEFAULT NULL COMMENT '上次操作时间',
  `key_word` varchar(255) NULL DEFAULT NULL COMMENT '关键字',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `is_open` tinyint(4) NULL DEFAULT NULL COMMENT '是否公开 是1 否0（仅人力可见）',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态 1可调动 0已调动',
  `history` text NULL COMMENT '历史记录',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_resource_share
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_resume_label
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_resume_label`;
CREATE TABLE `dx_erp_hrs_resume_label`  (
  `label_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签主键id',
  `label_name` varchar(100) NULL DEFAULT NULL COMMENT '标签名称',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父级id',
  `order_by` smallint(6) NULL DEFAULT NULL COMMENT '排序',
  `optime` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `is_del` tinyint(2) NULL DEFAULT 0 COMMENT '是否删除 0未删除,1已删除',
  PRIMARY KEY (`label_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_resume_label
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_role_record
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_role_record`;
CREATE TABLE `dx_erp_hrs_role_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `proid` smallint(6) NULL DEFAULT NULL COMMENT '项目部id',
  `project_name` varchar(55) NULL DEFAULT NULL COMMENT '项目部名称',
  `uid` varchar(70) NULL DEFAULT NULL COMMENT '被授权人id',
  `user_name` varchar(55) NULL DEFAULT NULL COMMENT '被授权人名称',
  `rid` smallint(6) NULL DEFAULT NULL COMMENT '角色id',
  `role_name` varchar(55) NULL DEFAULT NULL COMMENT '角色名称',
  `action_user` varchar(55) NULL DEFAULT NULL COMMENT '操作人',
  `action_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `record_flag` tinyint(4) NULL DEFAULT NULL COMMENT '是否生效：1生效 0失效',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_role_record
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_station
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_station`;
CREATE TABLE `dx_erp_hrs_station`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` varchar(70) NULL DEFAULT NULL COMMENT '人员id',
  `preProid` smallint(6) NULL DEFAULT NULL COMMENT '原单位id 项目部id 机关0',
  `preProName` varchar(30) NULL DEFAULT NULL COMMENT '原机关或项目部简称',
  `proid` smallint(6) NULL DEFAULT NULL COMMENT '变更后单位id 项目部id 机关0',
  `proName` varchar(30) NULL DEFAULT NULL COMMENT '变更后机关或项目部简称',
  `preDepartId` smallint(6) NULL DEFAULT NULL COMMENT '原部门id',
  `preDepartName` varchar(30) NULL DEFAULT NULL COMMENT '原部门名称',
  `departId` smallint(6) NULL DEFAULT NULL COMMENT '变更后部门id',
  `departName` varchar(30) NULL DEFAULT NULL COMMENT '变更后部门名称',
  `preJid` smallint(6) NULL DEFAULT NULL COMMENT '原职位id',
  `preJobName` varchar(30) NULL DEFAULT NULL COMMENT '原职位名称',
  `jid` smallint(6) NULL DEFAULT NULL COMMENT '变更后职位id',
  `jobName` varchar(30) NULL DEFAULT NULL COMMENT '变更后职位名称',
  `preIsSupervisor` tinyint(4) NULL DEFAULT NULL COMMENT '原部门是否主管',
  `isSupervisor` tinyint(4) NULL DEFAULT NULL COMMENT '变更后部门是否主管',
  `preWorkId` smallint(6) NULL DEFAULT NULL COMMENT '原岗位id',
  `preWorkName` varchar(50) NULL DEFAULT NULL COMMENT '原岗位名称',
  `workId` smallint(6) NULL DEFAULT NULL COMMENT '变更后岗位id',
  `workName` varchar(50) NULL DEFAULT NULL COMMENT '变更后岗位名称',
  `departureTime` date NULL DEFAULT NULL COMMENT '离开时间',
  `checkInTime` date NULL DEFAULT NULL COMMENT '报到时间',
  `missiveId` int(11) NULL DEFAULT NULL COMMENT '函件id',
  `missiveName` varchar(200) NULL DEFAULT NULL COMMENT '函件名称',
  `missiveFileId` int(11) NULL DEFAULT NULL COMMENT '函件文件id',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `creatorUid` varchar(50) NULL DEFAULT NULL COMMENT '操作人员id',
  `transferDate` date NULL DEFAULT NULL COMMENT '变更时间',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '调动类型',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_station
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_title_allowance
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_title_allowance`;
CREATE TABLE `dx_erp_hrs_title_allowance`  (
  `title_id` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '职称（技能）id',
  `parent_id` smallint(6) NULL DEFAULT NULL COMMENT '父id',
  `title_name` varchar(50) NULL DEFAULT NULL COMMENT '职称（技能）名称',
  `title_type` tinyint(4) NULL DEFAULT NULL COMMENT '分类：0职称 1技能',
  `title_allowance` double NULL DEFAULT NULL COMMENT '职称（技能）津贴',
  `material_allowance` double NULL DEFAULT NULL COMMENT '资料津贴',
  `remark` varchar(50) NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`title_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_title_allowance
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_trains
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_trains`;
CREATE TABLE `dx_erp_hrs_trains`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '培训id',
  `trains_name` varchar(50) NULL DEFAULT NULL COMMENT '培训名称',
  `trains_type` tinyint(2) NULL DEFAULT NULL COMMENT '培训类型',
  `trains_unit` varchar(50) NULL DEFAULT NULL COMMENT '培训单位',
  `trains_time` date NULL DEFAULT NULL COMMENT '培训时间',
  `content` varchar(500) NULL DEFAULT NULL COMMENT '培训概述',
  `remark` varchar(500) NULL DEFAULT NULL COMMENT '备注',
  `trains_record` text NULL COMMENT '培训成绩记录',
  `msg_flag` tinyint(2) NULL DEFAULT NULL COMMENT '通知培训人员,0否,1是',
  `career_flag` tinyint(2) NULL DEFAULT NULL COMMENT '同步到个人履历,0否,1是',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_trains
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_transfer_information
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_transfer_information`;
CREATE TABLE `dx_erp_hrs_transfer_information`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `sponsor_proid` smallint(6) NULL DEFAULT NULL COMMENT '发起人的proid',
  `sponsor_uid` varchar(70) NULL DEFAULT NULL COMMENT '发起人的uid',
  `sponsor` varchar(55) NULL DEFAULT NULL COMMENT '发起人',
  `sponsor_date` datetime(0) NULL DEFAULT NULL COMMENT '发起时间',
  `typeId` smallint(6) NULL DEFAULT NULL COMMENT '调动类型的id',
  `transfer_type` varchar(11) NULL DEFAULT NULL COMMENT '调动类型',
  `is_inside` tinyint(4) NULL DEFAULT NULL COMMENT '是否内部调动  0:否,1:是',
  `transfer_person` varchar(550) NULL DEFAULT NULL COMMENT '调动人员名单',
  `output_proid` smallint(6) NULL DEFAULT NULL COMMENT '调出单位id',
  `input_proid` smallint(6) NULL DEFAULT NULL COMMENT '调入单位id',
  `output_manger_audit` varchar(2000) NULL DEFAULT NULL COMMENT '调出项目经理审核信息 {\"uid\":\"\",\"userName\":\"\",\"sign\":\"\",\"comment\":\"\",\"date\":\"\"}',
  `output_manger_status` tinyint(4) NULL DEFAULT NULL COMMENT '调出经理 审核状态 0:待审核,-1:不同意,1:同意',
  `output_finance_audit` varchar(2000) NULL DEFAULT NULL COMMENT '调出财务审核信息 {\"uid\":\"\",\"userName\":\"\",\"sign\":\"\",\"comment\":\"\",\"date\":\"\"}',
  `output_finance_status` tinyint(4) NULL DEFAULT NULL COMMENT '调出财务 审核状态 0:待审核,-1:不同意,1:同意',
  `input_office_audit` varchar(2000) NULL DEFAULT NULL COMMENT '调入办公室审核信息 {\"uid\":\"\",\"userName\":\"\",\"sign\":\"\",\"comment\":\"\",\"date\":\"\"}',
  `input_office_status` tinyint(4) NULL DEFAULT NULL COMMENT '调入办公室 审核状态 0:待审核,-1:不同意,1:同意',
  `input_manger_audit` varchar(2000) NULL DEFAULT NULL COMMENT '调入经理审核信息 {\"uid\":\"\",\"userName\":\"\",\"sign\":\"\",\"comment\":\"\",\"date\":\"\"}',
  `input_manger_status` tinyint(4) NULL DEFAULT NULL COMMENT '调入经理 审核状态 0:待审核,-1:不同意,1:同意',
  `input_finance_audit` varchar(2000) NULL DEFAULT NULL COMMENT '调入财务主管审核 {\"uid\":\"\",\"userName\":\"\",\"sign\":\"\",\"comment\":\"\",\"date\":\"\"}',
  `input_finance_status` tinyint(4) NULL DEFAULT NULL COMMENT '调入财务 审核状态 0:待审核,-1:不同意,1:同意',
  `hrs_audit` varchar(2000) NULL DEFAULT NULL COMMENT '人力审核信息 {\"uid\":\"\",\"userName\":\"\",\"sign\":\"\",\"comment\":\"\",\"date\":\"\"}',
  `hrs_status` tinyint(4) NULL DEFAULT NULL COMMENT '人力 审核状态 0:待审核,-1:不同意,1:同意',
  `input_finance_plus_audit` varchar(2000) NULL DEFAULT NULL COMMENT '调入财务主管接收工资介绍信 {\"uid\":\"\",\"userName\":\"\",\"sign\":\"\",\"comment\":\"\",\"date\":\"\"}',
  `input_finance_plus_status` tinyint(4) NULL DEFAULT NULL COMMENT '调入财务主管 审核状态 0:待审核,-1:不同意,1:同意',
  `is_hrs_audit` tinyint(4) NULL DEFAULT NULL COMMENT '是否经由人力审核 是：1 否：0',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '已拒绝:-1,  调出项目经理审核:0, 调出财务主管审核:1, 调入办公室主管审核:2, 调入项目经理审核:3, 调入财务主管审核:4, 人力资源部审核:5, 接收工资介绍信:6, 已通过(生效):7',
  `is_del` tinyint(4) NULL DEFAULT NULL COMMENT '未删除:0, 已删除:1',
  `is_revoke` tinyint(4) NULL DEFAULT NULL COMMENT '是否撤回: 否:0,是:1',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_transfer_information
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_transfer_personinfo
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_transfer_personinfo`;
CREATE TABLE `dx_erp_hrs_transfer_personinfo`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `tranId` int(11) NOT NULL COMMENT '调动申请的id',
  `uid` varchar(70) NULL DEFAULT NULL COMMENT '人员id',
  `user_name` varchar(55) NULL DEFAULT NULL COMMENT '姓名',
  `identity_type` smallint(6) NULL DEFAULT NULL COMMENT '身份类型',
  `type_name` varchar(55) NULL DEFAULT NULL COMMENT '身份类型的名称',
  `output_departId` smallint(6) NULL DEFAULT NULL COMMENT '调出部门id',
  `output_depart` varchar(55) NULL DEFAULT NULL COMMENT '调出部门',
  `output_jobId` smallint(6) NULL DEFAULT NULL COMMENT '调出职位id',
  `output_job_name` varchar(55) NULL DEFAULT NULL COMMENT '调出职位名称',
  `output_is_supervisor` tinyint(4) NULL DEFAULT NULL COMMENT '调出是否主管',
  `output_workId` smallint(6) NULL DEFAULT NULL COMMENT '调出岗位id',
  `output_work` varchar(55) NULL DEFAULT NULL COMMENT '调出岗位',
  `output_date` date NULL DEFAULT NULL COMMENT '调出时间',
  `input_departId` smallint(6) NULL DEFAULT NULL COMMENT '调入部门id',
  `input_depart` varchar(55) NULL DEFAULT NULL COMMENT '调入部门',
  `input_jobId` smallint(6) NULL DEFAULT NULL COMMENT '调入职位id',
  `input_job_name` varchar(55) NULL DEFAULT NULL COMMENT '调入职位名称',
  `input_is_supervisor` tinyint(4) NULL DEFAULT NULL COMMENT '调入是否主管',
  `input_workId` smallint(6) NULL DEFAULT NULL COMMENT '调入岗位id',
  `input_work` varchar(55) NULL DEFAULT NULL COMMENT '调入岗位',
  `input_date` date NULL DEFAULT NULL COMMENT '调入时间',
  `lettersId` int(11) NULL DEFAULT NULL COMMENT '工资介绍信id',
  `lettersNo` varchar(70) NULL DEFAULT NULL COMMENT '工资介绍信编号',
  `is_receive` tinyint(4) NULL DEFAULT NULL COMMENT '是否接收 是:1 否:-1',
  `retain_original` tinyint(4) NULL DEFAULT NULL COMMENT '保留原职位 是:1 否:-1',
  `missiveId` int(11) NULL DEFAULT NULL COMMENT '函件id',
  `missive` varchar(70) NULL DEFAULT NULL COMMENT '函件名称',
  `is_change` tinyint(4) NULL DEFAULT NULL COMMENT '调动期间人员的信息是否发生变动 是:1 否:-1',
  `is_change_remark` varchar(500) NULL DEFAULT NULL COMMENT '变动信息备注',
  `is_effect` tinyint(4) NULL DEFAULT NULL COMMENT '是否生效 是:1 否:-1',
  PRIMARY KEY (`id`, `tranId`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_transfer_personinfo
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_user_identity
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_user_identity`;
CREATE TABLE `dx_erp_hrs_user_identity`  (
  `identity_id` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '身份id',
  `parent_id` smallint(6) NULL DEFAULT NULL COMMENT '父级id',
  `identity_name` varchar(40) NULL DEFAULT NULL COMMENT '身份名称',
  `order_index` smallint(6) NULL DEFAULT NULL COMMENT '排序',
  `finance_type` smallint(6) NULL DEFAULT NULL COMMENT '财务分类',
  PRIMARY KEY (`identity_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_user_identity
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_wage_allowance
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_wage_allowance`;
CREATE TABLE `dx_erp_hrs_wage_allowance`  (
  `wage_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` varchar(70) NULL DEFAULT NULL COMMENT '人员id',
  `starting_date` date NULL DEFAULT NULL COMMENT '起算日期',
  `name` varchar(50) NULL DEFAULT NULL COMMENT '姓名',
  `annual_wages` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '年工工资',
  `skill_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '技术职称津贴',
  `adm_position_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '技师津贴',
  `flow_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '流动津贴',
  `tunnel_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '隧道津贴',
  `sand_wind_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '风沙津贴',
  `plateau_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '高原津贴',
  `part_time_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '兼职津贴',
  `material_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '资料费补贴',
  `certified_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '执业资格证书津贴（注册师）',
  `telephone_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '电话费补贴',
  `meals_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '伙食费补贴',
  `woman_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '女工卫生费',
  `only_child_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '独生子女费',
  `wage_time` varchar(20) NULL DEFAULT NULL COMMENT '工资已发至',
  `vocation_start_year` varchar(20) NULL DEFAULT NULL COMMENT '探望假开始年',
  `vocation_end_year` varchar(20) NULL DEFAULT NULL COMMENT '探望假结束年',
  `vocation_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否休探望假',
  `partner_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否休配偶假',
  `wedding_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否休婚假',
  `maternity_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否休产假',
  `maternity_days` smallint(6) NULL DEFAULT NULL COMMENT '产假天数',
  `funeral_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否休丧假',
  `funeral_days` smallint(6) NULL DEFAULT NULL COMMENT '丧假天数',
  `injury_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否休工伤假',
  `injury_days` smallint(6) NULL DEFAULT NULL COMMENT '工伤假天数',
  `sick_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否休病假',
  `sick_days` smallint(6) NULL DEFAULT NULL COMMENT '病假天数',
  `absence_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否休事假',
  `absence_days` smallint(6) NULL DEFAULT NULL COMMENT '事假天数',
  `remark` varchar(500) NULL DEFAULT NULL COMMENT '备注',
  `flag` tinyint(4) NULL DEFAULT NULL COMMENT '状态 0上报标准 1对照标准 2审核标准',
  `checkFlag` tinyint(4) NULL DEFAULT 0 COMMENT '上报标准审核情况：0未审核 1已审核',
  `operation_name` varchar(50) NULL DEFAULT NULL COMMENT '审核人员',
  `operation_date` date NULL DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`wage_id`),
  INDEX `dehwa_uid`(`uid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_wage_allowance
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_wage_history
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_wage_history`;
CREATE TABLE `dx_erp_hrs_wage_history`  (
  `wage_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `uid` varchar(70) NULL DEFAULT NULL COMMENT '人员id',
  `starting_date` date NULL DEFAULT NULL COMMENT '生成日期',
  `annual_wages` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '年工工资',
  `skill_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '技术职称津贴',
  `adm_position_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '技师津贴',
  `flow_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '流动津贴',
  `tunnel_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '隧道津贴',
  `sand_wind_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '风沙津贴',
  `plateau_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '高原津贴',
  `part_time_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '兼职津贴',
  `material_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '资料费补贴',
  `certified_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '执业资格证书津贴（注册师）',
  `telephone_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '电话费补贴',
  `meals_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '伙食费补贴',
  `woman_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '女工卫生费',
  `only_child_allowance` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '独生子女费',
  `wage_time` varchar(20) NULL DEFAULT NULL COMMENT '工资已发至',
  `remark` varchar(500) NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`wage_id`),
  INDEX `dehwa_uid`(`uid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_wage_history
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_wage_record
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_wage_record`;
CREATE TABLE `dx_erp_hrs_wage_record`  (
  `rec_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `adjustment_time` datetime(0) NULL DEFAULT NULL COMMENT '调整时间',
  `starting_date` date NULL DEFAULT NULL COMMENT '起算日期',
  `uid` varchar(70) NULL DEFAULT NULL COMMENT '人员id',
  `proId` smallint(6) NULL DEFAULT NULL COMMENT '项目部id 机关0',
  `work_id` smallint(6) NULL DEFAULT NULL COMMENT '岗位id',
  `work_money` double NULL DEFAULT NULL COMMENT '岗位工资',
  `work_level` varchar(50) NULL DEFAULT NULL COMMENT '岗位档别',
  `work_score` double NULL DEFAULT NULL COMMENT '岗位标准',
  `annual_wages` double NULL DEFAULT NULL COMMENT '年工工资',
  `skill_allowance` double NULL DEFAULT NULL COMMENT '技术津贴',
  `adm_position_allowance` double NULL DEFAULT NULL COMMENT '技能津贴',
  `woman_allowance` double NULL DEFAULT NULL COMMENT '女工卫生费',
  `flow_allowance` double NULL DEFAULT NULL COMMENT '流动津贴',
  `tunnel_allowance` double NULL DEFAULT NULL COMMENT '隧道津贴',
  `sand_wind_allowance` double NULL DEFAULT NULL COMMENT '风沙津贴',
  `plateau_allowance` double NULL DEFAULT NULL COMMENT '高原津贴',
  `certified_allowance` double NULL DEFAULT NULL COMMENT '注册师津贴',
  `part_time_allowance` double NULL DEFAULT NULL COMMENT '兼职津贴',
  `material_allowance` double NULL DEFAULT NULL COMMENT '资料费补贴',
  `telephone_allowance` double NULL DEFAULT NULL COMMENT '通讯补贴',
  `meals_allowance` double NULL DEFAULT NULL COMMENT '餐费补贴',
  `only_child_allowance` double NULL DEFAULT NULL COMMENT '独生子女费',
  `remark` varchar(500) NULL DEFAULT NULL COMMENT '备注',
  `basis` varchar(500) NULL DEFAULT NULL COMMENT '修改依据',
  `adjusts` varchar(50) NULL DEFAULT NULL COMMENT '调整人',
  `licensor_uid` varchar(70) NULL DEFAULT NULL COMMENT '授权人id',
  `licensor` varchar(50) NULL DEFAULT NULL COMMENT '授权人',
  `recode_type` tinyint(4) NULL DEFAULT NULL COMMENT '记录类型 1对照标准 2审核标准',
  `audit_status` tinyint(4) NULL DEFAULT NULL COMMENT '审核状态 0待阅 1已阅',
  `take_effecte` tinyint(4) NULL DEFAULT 0 COMMENT '是否生效 0未生效 1已生效',
  PRIMARY KEY (`rec_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_wage_record
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_wageletter
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_wageletter`;
CREATE TABLE `dx_erp_hrs_wageletter`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `prescribe_proId` int(6) NULL DEFAULT NULL COMMENT '工资介绍信-开据单位id 机关为0 财务为-1 项目部id',
  `prescribe_project` varchar(55) NULL DEFAULT NULL COMMENT '开据单位',
  `create_time` date NULL DEFAULT NULL COMMENT '创建时间',
  `number` varchar(50) NULL DEFAULT NULL COMMENT '工资介绍信编号',
  `open_receipt` tinyint(4) NULL DEFAULT NULL COMMENT '工资介绍信-开据接收 开据1 接收2',
  `uid` varchar(70) NULL DEFAULT NULL COMMENT '人员id',
  `name` varchar(30) NULL DEFAULT NULL COMMENT '姓名',
  `sex` varchar(5) NULL DEFAULT NULL COMMENT '性别',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `education` varchar(30) NULL DEFAULT NULL COMMENT '文化程度',
  `join_job_time` date NULL DEFAULT NULL COMMENT '参加工作时间',
  `join_com_time` date NULL DEFAULT NULL COMMENT '进入企业时间',
  `work` varchar(40) NULL DEFAULT NULL COMMENT '岗位',
  `skill_name` varchar(40) NULL DEFAULT NULL COMMENT '职称（技能等级）',
  `get_time` date NULL DEFAULT NULL COMMENT '获取时间',
  `output_company` varchar(50) NULL DEFAULT NULL COMMENT '调出单位',
  `output_proId` smallint(6) NULL DEFAULT NULL COMMENT '调出单位id',
  `input_company` varchar(50) NULL DEFAULT NULL COMMENT '调入单位',
  `input_proId` smallint(6) NULL DEFAULT NULL COMMENT '调入单位id',
  `output_salary_data` varchar(2000) NULL DEFAULT NULL COMMENT '调出工资数据',
  `input_salary_data` varchar(2000) NULL DEFAULT NULL COMMENT '调入工资数据',
  `travel_expenses` varchar(100) NULL DEFAULT NULL COMMENT '预借差旅费',
  `output_wages_time` date NULL DEFAULT NULL COMMENT '调出工资发放时间',
  `input_wages_time` date NULL DEFAULT NULL COMMENT '调入工资发放时间',
  `visit_vocation_start_year` varchar(10) NULL DEFAULT NULL COMMENT '探望假开始年',
  `visit_vocation_end_year` varchar(10) NULL DEFAULT NULL COMMENT '探望假结束年',
  `visit_vocation_flag` tinyint(2) NULL DEFAULT NULL COMMENT '探望假是否休假 0未休1已休',
  `year_vocation_flag` tinyint(2) NULL DEFAULT NULL COMMENT '当年是否休假',
  `endowment_insurance_time` varchar(30) NULL DEFAULT NULL COMMENT '养老保险时间',
  `housing_fund_time` varchar(30) NULL DEFAULT NULL COMMENT '住房公积金时间',
  `year_deduction_time` varchar(30) NULL DEFAULT NULL COMMENT '年金时间',
  `medical_insurance_time` varchar(30) NULL DEFAULT NULL COMMENT '医保金时间',
  `remark` varchar(500) NULL DEFAULT NULL COMMENT '补充说明',
  `appendixId` int(11) NULL DEFAULT NULL COMMENT '附件id',
  `appendix_name` varchar(50) NULL DEFAULT NULL COMMENT '附件名',
  `action_user` varchar(50) NULL DEFAULT NULL COMMENT '操作人员',
  `action_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `typeId` smallint(6) NULL DEFAULT NULL COMMENT '介绍信类型id',
  `type` varchar(55) NULL DEFAULT NULL COMMENT '介绍信类型',
  `audit_user` varchar(55) NULL DEFAULT NULL COMMENT '部门主管审核',
  `audit_time` date NULL DEFAULT NULL COMMENT '审核时间',
  `audit_remark` varchar(255) NULL DEFAULT NULL COMMENT '审核备注',
  `is_transfer` tinyint(4) NULL DEFAULT NULL COMMENT '（关联调动流程）是否关联调动流程 是1 否0',
  `transfer_show` tinyint(4) NULL DEFAULT NULL COMMENT '（关联调动流程）接收单位是否可见 是1 否0',
  `flag` tinyint(4) NULL DEFAULT NULL COMMENT '审核状态 -1拒绝 0待审核 1同意',
  `is_del` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除 0已删除 1未删除',
  `is_opt` tinyint(4) NULL DEFAULT NULL COMMENT '是否可以操作 是1否0',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_wageletter
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_hrs_wageletter_project
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_hrs_wageletter_project`;
CREATE TABLE `dx_erp_hrs_wageletter_project`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `create_time` date NULL DEFAULT NULL COMMENT '创建时间',
  `number` varchar(50) NULL DEFAULT NULL COMMENT '工资介绍信编号',
  `proId` smallint(6) NULL DEFAULT NULL COMMENT '项目部id',
  `uid` varchar(70) NULL DEFAULT NULL COMMENT '人员id',
  `name` varchar(30) NULL DEFAULT NULL COMMENT '姓名',
  `sex` varchar(5) NULL DEFAULT NULL COMMENT '性别',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `education` varchar(30) NULL DEFAULT NULL COMMENT '文化程度',
  `join_job_time` date NULL DEFAULT NULL COMMENT '参加工作时间',
  `join_com_time` date NULL DEFAULT NULL COMMENT '进入企业时间',
  `work` varchar(40) NULL DEFAULT NULL COMMENT '岗位',
  `skill_name` varchar(40) NULL DEFAULT NULL COMMENT '职称（技能等级）',
  `get_time` date NULL DEFAULT NULL COMMENT '获取时间',
  `output_company` varchar(50) NULL DEFAULT NULL COMMENT '调出单位',
  `input_company` varchar(50) NULL DEFAULT NULL COMMENT '调入单位',
  `transfer_type` varchar(30) NULL DEFAULT NULL COMMENT '调动类型',
  `output_salary_data` varchar(2000) NULL DEFAULT NULL COMMENT '调出工资数据',
  `input_salary_data` varchar(2000) NULL DEFAULT NULL COMMENT '调入工资数据',
  `travel_expenses` varchar(100) NULL DEFAULT NULL COMMENT '预借差旅费',
  `output_wages_time` varchar(30) NULL DEFAULT NULL COMMENT '调出工资发放时间',
  `input_wages_time` varchar(30) NULL DEFAULT NULL COMMENT '调入工资发放时间',
  `visit_vocation_start_year` varchar(10) NULL DEFAULT NULL COMMENT '探望假开始年',
  `visit_vocation_end_year` varchar(10) NULL DEFAULT NULL COMMENT '探望假结束年',
  `visit_vocation_flag` tinyint(2) NULL DEFAULT NULL COMMENT '探望假是否休假 0未休1已休',
  `year_vocation_flag` tinyint(2) NULL DEFAULT NULL COMMENT '当年是否休假',
  `endowment_insurance_time` varchar(30) NULL DEFAULT NULL COMMENT '养老保险时间',
  `housing_fund_time` varchar(30) NULL DEFAULT NULL COMMENT '住房公积金时间',
  `year_deduction_time` varchar(30) NULL DEFAULT NULL COMMENT '年金时间',
  `medical_insurance_time` varchar(30) NULL DEFAULT NULL COMMENT '医保金时间',
  `remark` varchar(500) NULL DEFAULT NULL COMMENT '补充说明',
  `appendixId` int(11) NULL DEFAULT NULL COMMENT '附件id',
  `appendix_name` varchar(50) NULL DEFAULT NULL COMMENT '附件名',
  `creatorUid` varchar(70) NULL DEFAULT NULL COMMENT '创建人员id',
  `type` smallint(6) NULL DEFAULT NULL COMMENT '介绍信类型',
  `is_del` tinyint(2) NULL DEFAULT 0 COMMENT '是否删除 0已删除 1未删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_hrs_wageletter_project
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_job
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_job`;
CREATE TABLE `dx_erp_job`  (
  `Jid` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `job_name` varchar(50) NOT NULL COMMENT '职位名称',
  `remark` varchar(225) NULL DEFAULT NULL COMMENT '备注',
  `adm_level_tid` smallint(6) NULL DEFAULT NULL COMMENT '行政级别id',
  `departid` smallint(6) NULL DEFAULT NULL COMMENT '机关部门或项目部部门id',
  `proid` smallint(6) NULL DEFAULT NULL COMMENT '角色所属项目部，如果是机关为0,项目部为-1',
  `order_index` smallint(4) NULL DEFAULT NULL COMMENT '排序',
  `status` int(11) NULL DEFAULT 1 COMMENT '是否展示：0为否，1为是',
  PRIMARY KEY (`Jid`),
  INDEX `dej_order`(`order_index`),
  INDEX `dej_departid`(`departid`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '排序规则:公司:1-20公司领导级别，21-60公司各部门主管，61-100:公司各部门副主管，101及以后各科员职位/项目部:1-20部领导，21-60:各部长，61-100:各副部长，101及以后都是普通员工';

-- ----------------------------
-- Records of dx_erp_job
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_job_role
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_job_role`;
CREATE TABLE `dx_erp_job_role`  (
  `jid` smallint(6) NOT NULL COMMENT '职位id',
  `rid` smallint(6) NOT NULL COMMENT '角色id'
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_job_role
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_login_log
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_login_log`;
CREATE TABLE `dx_erp_login_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `phone` varchar(255) NOT NULL COMMENT '电话',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `login_date` datetime(0) NOT NULL COMMENT '登录日期',
  `login_type` int(11) NULL DEFAULT NULL COMMENT '登录类型',
  `ip` varchar(255) NULL DEFAULT NULL COMMENT 'IP地址',
  `user_agent` text NULL COMMENT '用户代理',
  `is_success` tinyint(4) NOT NULL DEFAULT 1 COMMENT '是否登陆成功',
  `error_log` varchar(255) NULL DEFAULT NULL COMMENT '错误日志',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_message_record
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_message_record`;
CREATE TABLE `dx_erp_message_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` varchar(70) NULL DEFAULT NULL COMMENT '用户id',
  `phone` varchar(15) NULL DEFAULT NULL COMMENT '手机号',
  `first_date` datetime(0) NULL DEFAULT NULL COMMENT '第一次登录时间',
  `last_date` datetime(0) NULL DEFAULT NULL COMMENT '最近登录时间',
  `count` smallint(6) NULL DEFAULT NULL COMMENT '短信次数',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_message_record
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_msg
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_msg`;
CREATE TABLE `dx_erp_msg`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `phone` varchar(11) NOT NULL COMMENT '接收用户电话',
  `type` int(1) NOT NULL COMMENT '消息类型',
  `title` varchar(20) NOT NULL COMMENT '标题',
  `msg` varchar(255) NOT NULL COMMENT '消息',
  `data` text NULL COMMENT '数据',
  `create_user` varchar(255) NULL DEFAULT NULL COMMENT '创建人id',
  `create_user_name` varchar(255) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `origin` varchar(10) NOT NULL COMMENT '来源',
  `status` int(1) NOT NULL COMMENT '状态(0:未读,1:已读,2:已处理)',
  PRIMARY KEY (`id`),
  INDEX `index_phone`(`phone`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_msg
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_msg_exchange
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_msg_exchange`;
CREATE TABLE `dx_erp_msg_exchange`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `content` varchar(2000) NULL DEFAULT NULL COMMENT '内容',
  `photos` varchar(2000) NULL DEFAULT NULL COMMENT '照片',
  `anonymous` tinyint(1) NOT NULL COMMENT '匿名发布',
  `interact` int(11) NULL DEFAULT NULL COMMENT '参与互动(0:不互动,1:单选,2:多选)',
  `options` varchar(2000) NULL DEFAULT NULL COMMENT '选项',
  `end_date` datetime(0) NULL DEFAULT NULL COMMENT '截止日期',
  `comments` bigint(20) NULL DEFAULT NULL COMMENT '评论数',
  `thumbs` bigint(20) NULL DEFAULT NULL COMMENT '点赞数',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  `project_name` varchar(255) NOT NULL COMMENT '项目部名称',
  `create_user_name` varchar(255) NOT NULL COMMENT '制表人',
  `create_user` varchar(255) NOT NULL COMMENT '创建人id',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_msg_exchange
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_msg_thumbs
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_msg_thumbs`;
CREATE TABLE `dx_erp_msg_thumbs`  (
  `exchange_id` int(11) NOT NULL COMMENT '互动id',
  `user_id` varchar(255) NOT NULL COMMENT '用户id',
  `thumb_date` datetime(0) NOT NULL COMMENT '点赞时间',
  INDEX `index_exchange_id`(`exchange_id`),
  INDEX `index_user_id`(`user_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_msg_thumbs
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_msg_user_comment
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_msg_user_comment`;
CREATE TABLE `dx_erp_msg_user_comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `exchange_id` int(11) NOT NULL COMMENT '互动Id',
  `content` varchar(2000) NULL DEFAULT NULL COMMENT '评论内容',
  `anonymous` tinyint(1) NOT NULL COMMENT '是否匿名',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  `create_user_name` varchar(255) NOT NULL COMMENT '制表人',
  `create_user` varchar(255) NOT NULL COMMENT '创建人id',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_msg_user_comment
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_msg_user_votes
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_msg_user_votes`;
CREATE TABLE `dx_erp_msg_user_votes`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `exchange_id` int(11) NOT NULL COMMENT '互动id',
  `choose_option` varchar(255) NOT NULL COMMENT '投票的选项',
  `vote_user` varchar(255) NOT NULL COMMENT '投票人',
  `vote_date` datetime(0) NOT NULL COMMENT '投票日期',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_msg_user_votes
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_per_achievement
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_per_achievement`;
CREATE TABLE `dx_erp_per_achievement`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` varchar(70) NULL DEFAULT NULL COMMENT '人员id',
  `typeId` smallint(6) NULL DEFAULT NULL COMMENT '业绩种类id',
  `type` varchar(50) NULL DEFAULT NULL COMMENT '业绩种类',
  `achievementName` varchar(125) NULL DEFAULT NULL COMMENT '业绩名称',
  `getTime` date NULL DEFAULT NULL COMMENT '获取时间',
  `content` varchar(255) NULL DEFAULT NULL COMMENT '详情',
  `writerInfo` varchar(125) NULL DEFAULT NULL COMMENT '作者次序',
  `remark` varchar(125) NULL DEFAULT NULL COMMENT 'remark',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_per_achievement
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_per_certified
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_per_certified`;
CREATE TABLE `dx_erp_per_certified`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `proId` smallint(6) NULL DEFAULT NULL COMMENT '项目部id',
  `uid` varchar(70) NOT NULL COMMENT '用户id',
  `register_id` smallint(6) NULL DEFAULT NULL COMMENT '资格id',
  `register_type` smallint(6) NULL DEFAULT NULL COMMENT '资格类型',
  `register_name` varchar(125) NULL DEFAULT NULL COMMENT '资格名称',
  `register_number` varchar(225) NULL DEFAULT NULL COMMENT '资格证书编号',
  `is_architect` tinyint(4) NULL DEFAULT NULL COMMENT '是否建造师',
  `regdate` date NULL DEFAULT NULL COMMENT '注册时间',
  `validityDate` date NULL DEFAULT NULL COMMENT '有效期',
  `organization` varchar(125) NULL DEFAULT NULL COMMENT '组织部门',
  `registeredUnit` varchar(125) NULL DEFAULT NULL COMMENT '注册单位',
  `remark` varchar(225) NULL DEFAULT NULL COMMENT '备注',
  `one_time_reward` double NULL DEFAULT NULL COMMENT '一次性奖励津贴',
  `is_receive` tinyint(4) NULL DEFAULT 0 COMMENT '是否领取 0未领取 1已领取',
  `operation_uid` varchar(70) NULL DEFAULT NULL COMMENT '操作人员id',
  `operation_time` date NULL DEFAULT NULL COMMENT '最近一次操作时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_per_certified
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_per_education
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_per_education`;
CREATE TABLE `dx_erp_per_education`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` varchar(70) NOT NULL COMMENT '人员id',
  `typeId` smallint(6) NULL DEFAULT NULL COMMENT '学历类别id',
  `education_type` varchar(55) NOT NULL COMMENT '学历类别',
  `type_code` tinyint(6) NULL DEFAULT NULL COMMENT '分类 1第一,2第二,3第三',
  `is_highest_degree` tinyint(2) NULL DEFAULT NULL COMMENT '是否最高学位 0否 1是',
  `finish_school` varchar(55) NOT NULL COMMENT '毕业院校',
  `learning_formId` smallint(6) NULL DEFAULT NULL COMMENT '学习形式id',
  `learning_form` varchar(55) NULL DEFAULT NULL COMMENT '学习形式',
  `major` varchar(55) NULL DEFAULT NULL COMMENT '所学专业',
  `start_time` date NULL DEFAULT NULL COMMENT '开始时间',
  `finish_time` date NULL DEFAULT NULL COMMENT '毕业时间',
  `certificate_num` varchar(55) NULL DEFAULT NULL COMMENT '证书编号',
  `certificate_photo` int(11) NULL DEFAULT NULL COMMENT '证书图片id',
  `photo_name` varchar(55) NULL DEFAULT NULL COMMENT '图片名称',
  PRIMARY KEY (`id`),
  INDEX `edu_uid`(`uid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_per_education
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_per_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_per_operation_log`;
CREATE TABLE `dx_erp_per_operation_log`  (
  `id` varchar(70) NOT NULL COMMENT '人员id',
  `concent` varchar(1000) NOT NULL COMMENT '操作内容',
  `uid` varchar(70) NOT NULL COMMENT '管理人员id',
  `indate` datetime(0) NOT NULL COMMENT '操作时间',
  `uid_ip` varchar(16) NOT NULL COMMENT '管理人员ip',
  `user_agent` varchar(200) NULL DEFAULT NULL COMMENT '浏览器',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_per_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_per_position_experience
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_per_position_experience`;
CREATE TABLE `dx_erp_per_position_experience`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` varchar(70) NOT NULL COMMENT '用户id',
  `skill_tid` smallint(6) NULL DEFAULT NULL COMMENT '技术职称id',
  `skill_name` varchar(30) NULL DEFAULT NULL COMMENT '技术职称',
  `get_time` date NULL DEFAULT NULL COMMENT '获取时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_per_position_experience
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_per_project_resume
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_per_project_resume`;
CREATE TABLE `dx_erp_per_project_resume`  (
  `resume_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` varchar(70) NULL DEFAULT NULL COMMENT '人员id',
  `project_id` smallint(6) NULL DEFAULT NULL COMMENT '项目部id',
  `join_come_project_time` date NULL DEFAULT NULL COMMENT '进入项目部时间',
  `leave_project_time` date NULL DEFAULT NULL COMMENT '离开项目部时间',
  `apply_label` varchar(500) NULL DEFAULT NULL COMMENT '申请的标签',
  `audit_label` text NULL COMMENT '审核后的标签',
  `label_remark` varchar(500) NULL DEFAULT NULL COMMENT '标签的备注信息',
  `apply_time` datetime(0) NULL DEFAULT NULL COMMENT '申请时间',
  `project_audit_uid` varchar(70) NULL DEFAULT NULL COMMENT '项目部审核id',
  `project_audit_time` datetime(0) NULL DEFAULT NULL COMMENT '项目部审核时间',
  `project_remark` varchar(300) NULL DEFAULT NULL COMMENT '项目部备注',
  `human_audit_uid` varchar(70) NULL DEFAULT NULL COMMENT '人力资源部审核id',
  `human_audit_time` datetime(0) NULL DEFAULT NULL COMMENT '人力资源部审核时间',
  `human_remark` varchar(300) NULL DEFAULT NULL COMMENT '人力资源部备注',
  `status` tinyint(2) NULL DEFAULT NULL COMMENT '0编辑中 1项目部审核 2人力资源部审核 3审核未通过 4审核通过',
  PRIMARY KEY (`resume_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_per_project_resume
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_per_qualification
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_per_qualification`;
CREATE TABLE `dx_erp_per_qualification`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` varchar(70) NOT NULL COMMENT '用户id',
  `skillTid` smallint(6) NULL DEFAULT NULL COMMENT '技术职称等级id',
  `skillName` varchar(50) NULL DEFAULT NULL COMMENT '技术职称等级',
  `getTime` date NULL DEFAULT NULL COMMENT '获取时间',
  `remark` varchar(30) NULL DEFAULT NULL COMMENT '备注',
  `photoId` int(11) NULL DEFAULT NULL COMMENT '证书图片id',
  `photoName` varchar(50) NULL DEFAULT NULL COMMENT '图片名称',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_per_qualification
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_per_skilltechnical
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_per_skilltechnical`;
CREATE TABLE `dx_erp_per_skilltechnical`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` varchar(70) NULL DEFAULT NULL COMMENT '用户id',
  `admPositionTid` smallint(6) NULL DEFAULT NULL COMMENT '技能等级id',
  `admPosition` varchar(50) NULL DEFAULT NULL COMMENT '技能等级',
  `getTime` date NULL DEFAULT NULL COMMENT '获取时间',
  `remark` varchar(225) NULL DEFAULT NULL COMMENT '备注',
  `photoId` int(11) NULL DEFAULT NULL COMMENT '证书图片id',
  `photoName` varchar(50) NULL DEFAULT NULL COMMENT '图片名称',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_per_skilltechnical
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_per_trains_resume
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_per_trains_resume`;
CREATE TABLE `dx_erp_per_trains_resume`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` varchar(70) NULL DEFAULT NULL COMMENT '人员id',
  `trains_name` varchar(100) NULL DEFAULT NULL COMMENT '培训名称',
  `trains_type` tinyint(2) NULL DEFAULT NULL COMMENT '培训类型',
  `trains_unit` varchar(50) NULL DEFAULT NULL COMMENT '培训单位',
  `content` varchar(500) NULL DEFAULT NULL COMMENT '培训内容',
  `trains_time` date NULL DEFAULT NULL COMMENT '培训时间',
  `result` tinyint(2) NULL DEFAULT NULL COMMENT '培训结果 0未参与 1良好 2优秀',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_per_trains_resume
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_per_work_experience
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_per_work_experience`;
CREATE TABLE `dx_erp_per_work_experience`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` varchar(70) NULL DEFAULT NULL COMMENT '人员id',
  `start_time` date NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` date NULL DEFAULT NULL COMMENT '结束时间',
  `other_type` smallint(6) NULL DEFAULT NULL COMMENT '单位类别',
  `type_details` varchar(125) NULL DEFAULT NULL COMMENT '类别详情',
  `project_typeId` smallint(6) NULL DEFAULT NULL COMMENT '项目部类型id',
  `project_type` varchar(125) NULL DEFAULT NULL COMMENT '项目部类型',
  `project_second_typeId` smallint(6) NULL DEFAULT NULL COMMENT '项目部二级类型id',
  `project_second_type` varchar(125) NULL DEFAULT NULL COMMENT '项目部二级类型',
  `proid` smallint(6) NULL DEFAULT NULL COMMENT '项目部id 0是机关',
  `abbreviation` varchar(50) NULL DEFAULT NULL COMMENT '单位简称',
  `job_id` smallint(6) NULL DEFAULT NULL COMMENT '职位id',
  `job_name` varchar(125) NULL DEFAULT NULL COMMENT '职位名称',
  `skill_tid` smallint(6) NULL DEFAULT NULL COMMENT '技术职称id',
  `skill_name` varchar(125) NULL DEFAULT NULL COMMENT '技术职称名称',
  `tasks` varchar(500) NULL DEFAULT NULL COMMENT '工作内容',
  `remark` varchar(500) NULL DEFAULT NULL COMMENT '备注',
  `fileId` int(11) NULL DEFAULT NULL COMMENT '文件id',
  `fileName` varchar(125) NULL DEFAULT NULL COMMENT '文件名称',
  `stateId` smallint(6) NULL DEFAULT NULL COMMENT '人员状态id',
  `personState` varchar(125) NULL DEFAULT NULL COMMENT '人员状态',
  `work_period` smallint(6) NULL DEFAULT 1 COMMENT '工作阶段 1正式经历（默认） 2实习  3准合同工 4临时工 5部队服役 6其他工作',
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '0未审核  1已审核  -1参考经历',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_per_work_experience
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_perm
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_perm`;
CREATE TABLE `dx_erp_perm`  (
  `pid` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `perm_code` varchar(50) NOT NULL COMMENT '权限码',
  `name` varchar(25) NOT NULL COMMENT '权限名称',
  `img` varchar(255) NULL DEFAULT NULL COMMENT '图片位置',
  `sid` smallint(6) NOT NULL COMMENT '父id',
  `type` int(11) NULL DEFAULT NULL COMMENT '分类：1机关，2项目部，3按钮',
  `order_index` smallint(6) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`pid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_perm
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_politics
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_politics`;
CREATE TABLE `dx_erp_politics`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `proid` smallint(6) NULL DEFAULT NULL COMMENT '项目部id',
  `uid` varchar(70) NOT NULL COMMENT '人员id',
  `politics` varchar(70) NULL DEFAULT NULL COMMENT '政治面貌',
  `join_time` date NULL DEFAULT NULL COMMENT '加入时间',
  `party_branch` varchar(125) NULL DEFAULT NULL COMMENT '党支部',
  `party_post` varchar(125) NULL DEFAULT NULL COMMENT '党内职务',
  `remark` varchar(2000) NULL DEFAULT NULL COMMENT '备注',
  `opt_uid` varchar(70) NULL DEFAULT NULL COMMENT '操作人uid',
  `opt_user` varchar(70) NULL DEFAULT NULL COMMENT '操作人',
  `opt_date` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `status` tinyint(4) NOT NULL COMMENT '0其他 1党员',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_politics
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_politics_experience
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_politics_experience`;
CREATE TABLE `dx_erp_politics_experience`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` varchar(70) NULL DEFAULT NULL COMMENT '人员id',
  `proid` smallint(6) NULL DEFAULT NULL COMMENT '项目部id',
  `project_name` varchar(255) NULL DEFAULT NULL COMMENT '项目部名称',
  `start_date` date NULL DEFAULT NULL COMMENT '开始时间',
  `end_date` date NULL DEFAULT NULL COMMENT '结束时间',
  `politics` varchar(125) NULL DEFAULT NULL COMMENT '政治面貌',
  `party_branch` varchar(125) NULL DEFAULT NULL COMMENT '党支部',
  `party_post` varchar(255) NULL DEFAULT NULL COMMENT '党内职务',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_politics_experience
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_project
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_project`;
CREATE TABLE `dx_erp_project`  (
  `proid` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '项目部id',
  `project_name` varchar(50) NOT NULL COMMENT '项目部名称',
  `abbreviation` varchar(50) NULL DEFAULT NULL COMMENT '项目简称',
  `order_index` smallint(6) NULL DEFAULT NULL COMMENT '排序',
  `uid` varchar(70) NULL DEFAULT NULL COMMENT '分区主管',
  `admin_rid` smallint(6) NULL DEFAULT NULL COMMENT '管理员角色id',
  `status` smallint(6) NULL DEFAULT NULL COMMENT '项目部状态',
  `regionId` smallint(6) NULL DEFAULT NULL COMMENT '项目部所属地区',
  `address` varchar(1000) NULL DEFAULT NULL COMMENT '详细地址',
  `latitude_longitude` varchar(550) NULL DEFAULT NULL COMMENT '经纬度',
  `admin_uid` varchar(70) NULL DEFAULT NULL COMMENT '管理员id（总工）',
  `managerId` varchar(70) NULL DEFAULT NULL COMMENT '项目经理id',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '项目部类别（项目部的分类）',
  `other_type` smallint(6) NULL DEFAULT NULL COMMENT '项目部的单位类别（项目部、专业化分公司、基地及附属单位等...）',
  `create_time` date NULL DEFAULT NULL COMMENT '项目进场时间',
  `end_time` date NULL DEFAULT NULL COMMENT '项目退场时间',
  `alias_one` varchar(255) NULL DEFAULT NULL COMMENT '别名1',
  `alias_two` varchar(255) NULL DEFAULT NULL COMMENT '别名2',
  `alias_three` varchar(255) NULL DEFAULT NULL COMMENT '别名3',
  `type_of_construction` varchar(255) NULL DEFAULT NULL COMMENT '1隧道 2 高原 3风沙子',
  `security_score` double(11, 0) NULL DEFAULT 1 COMMENT '安全质量评比得分',
  `peoplsize` int(11) NULL DEFAULT NULL COMMENT '项目定编人数',
  `all_year` int(11) NULL DEFAULT NULL COMMENT '项目合同施工工期（月）',
  `all_money` double(20, 2) NULL DEFAULT NULL COMMENT '项目合同总额（元）',
  `planned_start_date` date NULL DEFAULT NULL COMMENT '合同开工日期',
  `planned_end_date` date NULL DEFAULT NULL COMMENT '合同竣工日期',
  `actual_start_date` date NULL DEFAULT NULL COMMENT '实际开工日期',
  `actual_end_date` date NULL DEFAULT NULL COMMENT '实际竣工日期',
  `appendixId` int(11) NULL DEFAULT NULL COMMENT '文件id',
  `appendix_name` varchar(255) NULL DEFAULT NULL COMMENT '文件名称',
  PRIMARY KEY (`proid`),
  INDEX `proid`(`proid`),
  INDEX `dep_type`(`type`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_project
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_project_depart
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_project_depart`;
CREATE TABLE `dx_erp_project_depart`  (
  `de_id` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '部门Id',
  `depart_name` varchar(50) NOT NULL COMMENT '部门名称',
  `abbreviation` varchar(50) NULL DEFAULT NULL COMMENT '部门简称',
  `order_index` smallint(6) NULL DEFAULT NULL COMMENT '排序',
  `unid` smallint(6) NULL DEFAULT NULL COMMENT '对应机关部门id',
  `uid` smallint(6) NULL DEFAULT NULL COMMENT '相应部门机关负责人',
  `rid` smallint(6) NULL DEFAULT NULL COMMENT '负责人角色Id',
  PRIMARY KEY (`de_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_project_depart
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_reportforms
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_reportforms`;
CREATE TABLE `dx_erp_reportforms`  (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `business_name` varchar(20) NULL DEFAULT NULL COMMENT '业务名称',
  `business_type` smallint(6) NULL DEFAULT NULL COMMENT '业务类别id(type)  1劳务队信用评价  0供应商重新评价',
  `start_time` date NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` date NULL DEFAULT NULL COMMENT '结束时间',
  `reporting_period` varchar(10) NULL DEFAULT NULL COMMENT '报表周期',
  `isefficacious` tinyint(4) NULL DEFAULT NULL COMMENT '状态 1当前有效  0以往有效',
  `isdeadline` tinyint(4) NULL DEFAULT NULL COMMENT '是否是截至0截止 1不截止',
  `profession_starttime` datetime(0) NULL DEFAULT NULL COMMENT '业务发起时间',
  `profession_overtime` datetime(0) NULL DEFAULT NULL COMMENT '业务结束时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_reportforms
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_role
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_role`;
CREATE TABLE `dx_erp_role`  (
  `rid` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `remark` varchar(225) NULL DEFAULT NULL COMMENT '备注',
  `departId` smallint(6) NULL DEFAULT NULL COMMENT '机关部门id或项目部id',
  `is_supervisor` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否主管1:是，0:否',
  `proid` smallint(6) NULL DEFAULT NULL COMMENT '项目部id为0时是机关',
  `isbasis` tinyint(4) NOT NULL DEFAULT 1 COMMENT '是否公共角色1:是，0:不是',
  PRIMARY KEY (`rid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_role
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_role_depart_perm
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_role_depart_perm`;
CREATE TABLE `dx_erp_role_depart_perm`  (
  `proid` smallint(6) NULL DEFAULT NULL COMMENT '项目部id,机关为0',
  `rid` smallint(6) NULL DEFAULT NULL COMMENT '角色id',
  `pid` smallint(6) NULL DEFAULT NULL COMMENT '权限id'
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_role_depart_perm
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_sys_operation_log`;
CREATE TABLE `dx_erp_sys_operation_log`  (
  `id` varchar(70) NOT NULL COMMENT '人员id',
  `concent` varchar(1000) NOT NULL COMMENT '操作内容',
  `indate` datetime(0) NOT NULL COMMENT '操作时间',
  `uid_ip` varchar(16) NOT NULL COMMENT '人员ip',
  `user_agent` varchar(200) NULL DEFAULT NULL COMMENT '浏览器',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_sys_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_unit
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_unit`;
CREATE TABLE `dx_erp_unit`  (
  `unid` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '单位id',
  `identity_name` varchar(50) NOT NULL COMMENT '单位名称',
  `order_index` smallint(6) NULL DEFAULT NULL COMMENT '排序',
  `rid` smallint(6) NULL DEFAULT NULL COMMENT '负责人角色',
  INDEX `unid`(`unid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_unit
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_user
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_user`;
CREATE TABLE `dx_erp_user`  (
  `id` varchar(70) NOT NULL COMMENT '人员id',
  `phone` varchar(15) NOT NULL COMMENT '联系电话',
  `flag` tinyint(4) NOT NULL DEFAULT 1 COMMENT '人员状态 -1离职 0待审核 1在职 2待岗',
  `sflag` tinyint(4) NOT NULL DEFAULT 1 COMMENT '人员系统状态 0失效  1生效',
  `unit_project` tinyint(1) NULL DEFAULT 1 COMMENT '机关人员还是项目部人员1:机关，2：项目部',
  `review_status` tinyint(4) NULL DEFAULT NULL COMMENT '审核状态',
  `avatarpath` int(11) NULL DEFAULT NULL COMMENT '头像路径',
  `signature` int(11) NULL DEFAULT NULL COMMENT '签名',
  `photo_url` int(11) NULL DEFAULT NULL COMMENT '照片路径',
  `name` varchar(50) NULL DEFAULT NULL COMMENT '姓名',
  `wage` varchar(50) NULL DEFAULT NULL COMMENT '姓名拼音',
  `age` tinyint(4) NULL DEFAULT NULL COMMENT '年龄',
  `sex` varchar(2) NULL DEFAULT NULL COMMENT '性别',
  `birthplace` varchar(50) NULL DEFAULT NULL COMMENT '籍贯',
  `nationality` varchar(10) NULL DEFAULT NULL COMMENT '民族',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `id_card` varchar(20) NULL DEFAULT NULL COMMENT '身份证号码',
  `file_number` varchar(30) NULL DEFAULT NULL COMMENT '档案号',
  `insurance` varchar(30) NULL DEFAULT NULL COMMENT '社会保证号',
  `identity_type` tinyint(4) NULL DEFAULT NULL COMMENT '身份类型  0正式工 1正式工锦鲤 2合同工 3劳务工',
  `identity_tid` smallint(6) NULL DEFAULT NULL COMMENT '身份id',
  `identity_name` varchar(20) NULL DEFAULT NULL COMMENT '身份名称',
  `political` varchar(10) NULL DEFAULT NULL COMMENT '政治面貌',
  `joinTime` date NULL DEFAULT NULL COMMENT '加入时间',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `address` varchar(200) NULL DEFAULT NULL COMMENT '通讯地址',
  `person_relation` varchar(20) NULL DEFAULT NULL COMMENT '与本人关系',
  `family` varchar(20) NULL DEFAULT NULL COMMENT '紧急联系人',
  `family_phone` varchar(200) NULL DEFAULT NULL COMMENT '紧急联系方式',
  `census` varchar(200) NULL DEFAULT NULL COMMENT '户籍所在地',
  `company_unid` smallint(6) NULL DEFAULT NULL COMMENT '单位id',
  `company_name` varchar(50) NULL DEFAULT NULL COMMENT '单位',
  `prject_id` smallint(6) NULL DEFAULT NULL COMMENT '项目部id',
  `project_name` varchar(50) NULL DEFAULT NULL COMMENT '项目部名称',
  `join_job_Time` date NULL DEFAULT NULL COMMENT '参加工作时间',
  `join_com_Time` date NULL DEFAULT NULL COMMENT '进入企业时间',
  `registeredTime` date NULL DEFAULT NULL COMMENT '企龄时间',
  `disengagement` varchar(50) NULL DEFAULT NULL COMMENT '脱岗情况',
  `skill_tid` smallint(6) NULL DEFAULT NULL COMMENT '技术职称等级id',
  `skill_name` varchar(30) NULL DEFAULT NULL COMMENT '技术职称等级',
  `get_time` datetime(0) NULL DEFAULT NULL COMMENT '取得时间',
  `adm_position_tid` smallint(6) NULL DEFAULT NULL COMMENT '技能等级id',
  `adm_position` varchar(30) NULL DEFAULT NULL COMMENT '技能等级',
  `start_time` date NULL DEFAULT NULL COMMENT '技能取得时间',
  `adm_level_tid` smallint(6) NULL DEFAULT NULL COMMENT '行政级别id',
  `adm_level` varchar(30) NULL DEFAULT NULL COMMENT '行政级别',
  `server_time` date NULL DEFAULT NULL COMMENT '任职时间',
  `pro_position_tid` smallint(6) NULL DEFAULT NULL COMMENT '项目职务id',
  `pro_position` varchar(30) NULL DEFAULT NULL COMMENT '项目职务',
  `work_type_tid` smallint(6) NULL DEFAULT NULL COMMENT '工种id',
  `work_type` varchar(30) NULL DEFAULT NULL COMMENT '工种',
  `skill_type` varchar(50) NULL DEFAULT NULL COMMENT '技能可任工种',
  `qualification_tid` smallint(6) NULL DEFAULT NULL COMMENT '执业资格id',
  `qualification` varchar(30) NULL DEFAULT NULL COMMENT '执业资格',
  `increase_tid` smallint(6) NULL DEFAULT NULL COMMENT '增员原因id',
  `increase` varchar(30) NULL DEFAULT NULL COMMENT '增员原因',
  `inc_remark` varchar(225) NULL DEFAULT NULL COMMENT '增员备注',
  `happening` varchar(1000) NULL DEFAULT NULL COMMENT '奖惩情况',
  `end_time` date NULL DEFAULT NULL COMMENT '合同终止时间',
  `separation_time` date NULL DEFAULT NULL COMMENT '离职时间',
  `not_in_reason` varchar(30) NULL DEFAULT NULL COMMENT '不在岗原因',
  `data_id` varchar(200) NULL DEFAULT NULL COMMENT '数据Id',
  `resume` varchar(1000) NULL DEFAULT NULL COMMENT '工作简历',
  `remark` varchar(500) NULL DEFAULT NULL COMMENT '备注',
  `unit_jid` smallint(6) NULL DEFAULT NULL COMMENT '机关职位id',
  `unit_supervisor` smallint(6) NULL DEFAULT NULL COMMENT '机关职位是否主管',
  `project_jid` smallint(6) NULL DEFAULT NULL COMMENT '项目部职位',
  `project_supervisor` smallint(6) NULL DEFAULT NULL COMMENT '项目部职位是否主管',
  `order_by` tinyint(4) NULL DEFAULT 5 COMMENT '职位权重',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_user
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_user_job_project
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_user_job_project`;
CREATE TABLE `dx_erp_user_job_project`  (
  `id` varchar(70) NOT NULL COMMENT '主键',
  `uid` varchar(70) NOT NULL COMMENT '用户id',
  `jid` smallint(6) NOT NULL COMMENT '角色id',
  `proid` smallint(6) NOT NULL COMMENT '项目部id,机关为0',
  `status` smallint(6) NOT NULL COMMENT '审核状态0:待审核,1:通过,-1:拒绝',
  `apply_time` datetime(0) NOT NULL COMMENT '申请时间',
  `is_supervisor` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否主管1:是,0:不是',
  `is_fullTime` tinyint(4) NOT NULL DEFAULT 1 COMMENT '是否全职:1全职,0兼管',
  `check_type` tinyint(4) NULL DEFAULT NULL COMMENT '审核类型1:注册审核，2添加身份审核3，职位变更审核',
  `check_id` varchar(70) NULL DEFAULT NULL COMMENT '审核人id',
  `check_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_user_job_project
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_user_project
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_user_project`;
CREATE TABLE `dx_erp_user_project`  (
  `id` varchar(70) NOT NULL COMMENT '人员id',
  `phone` varchar(15) NOT NULL COMMENT '联系电话',
  `flag` tinyint(4) NOT NULL DEFAULT 1 COMMENT '人员状态 -1离职 0待审核 1在职 2待岗',
  `sflag` tinyint(4) NOT NULL DEFAULT 1 COMMENT '人员系统状态 0失效  1生效',
  `unit_project` tinyint(1) NULL DEFAULT 1 COMMENT '机关人员还是项目部人员1:机关，2：项目部',
  `review_status` tinyint(4) NULL DEFAULT NULL COMMENT '审核状态',
  `avatarpath` varchar(255) NULL DEFAULT NULL COMMENT '头像路径',
  `signature` int(11) NULL DEFAULT NULL COMMENT '签名',
  `photo_url` int(11) NULL DEFAULT NULL COMMENT '照片路径',
  `name` varchar(50) NULL DEFAULT NULL COMMENT '姓名',
  `wage` varchar(50) NULL DEFAULT NULL COMMENT '姓名拼音',
  `age` tinyint(4) NULL DEFAULT NULL COMMENT '年龄',
  `sex` varchar(2) NULL DEFAULT NULL COMMENT '性别',
  `birthplace` varchar(50) NULL DEFAULT NULL COMMENT '籍贯',
  `nationality` varchar(10) NULL DEFAULT NULL COMMENT '民族',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `id_card` varchar(20) NULL DEFAULT NULL COMMENT '身份证号码',
  `file_number` varchar(30) NULL DEFAULT NULL COMMENT '档案号',
  `insurance` varchar(30) NULL DEFAULT NULL COMMENT '社会保证号',
  `identity_type` tinyint(4) NULL DEFAULT NULL COMMENT '身份类型',
  `identity_tid` smallint(6) NULL DEFAULT NULL COMMENT '身份id',
  `identity_name` varchar(20) NULL DEFAULT NULL COMMENT '身份名称',
  `political` varchar(10) NULL DEFAULT NULL COMMENT '政治面貌',
  `joinTime` date NULL DEFAULT NULL COMMENT '加入时间',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `address` varchar(200) NULL DEFAULT NULL COMMENT '通讯地址',
  `person_relation` varchar(20) NULL DEFAULT NULL COMMENT '与本人关系',
  `family` varchar(20) NULL DEFAULT NULL COMMENT '紧急联系人',
  `family_phone` varchar(200) NULL DEFAULT NULL COMMENT '紧急联系方式',
  `census` varchar(200) NULL DEFAULT NULL COMMENT '户籍所在地',
  `company_unid` smallint(6) NULL DEFAULT NULL COMMENT '单位id',
  `company_name` varchar(50) NULL DEFAULT NULL COMMENT '单位',
  `prject_id` smallint(6) NULL DEFAULT NULL COMMENT '项目部id',
  `project_name` varchar(25) NULL DEFAULT NULL COMMENT '项目部名称',
  `join_job_Time` date NULL DEFAULT NULL COMMENT '参加工作时间',
  `join_com_Time` date NULL DEFAULT NULL COMMENT '进入企业时间',
  `registeredTime` date NULL DEFAULT NULL COMMENT '企龄时间',
  `disengagement` varchar(50) NULL DEFAULT NULL COMMENT '脱岗情况',
  `skill_tid` smallint(6) NULL DEFAULT NULL COMMENT '技术职称等级id',
  `skill_name` varchar(30) NULL DEFAULT NULL COMMENT '技术职称等级',
  `get_time` datetime(0) NULL DEFAULT NULL COMMENT '取得时间',
  `adm_position_tid` smallint(6) NULL DEFAULT NULL COMMENT '技能等级id',
  `adm_position` varchar(30) NULL DEFAULT NULL COMMENT '技能等级',
  `start_time` date NULL DEFAULT NULL COMMENT '技能等级获取时间',
  `adm_level_tid` smallint(6) NULL DEFAULT NULL COMMENT '行政级别id',
  `adm_level` varchar(30) NULL DEFAULT NULL COMMENT '行政级别',
  `server_time` date NULL DEFAULT NULL COMMENT '任职时间',
  `pro_position_tid` smallint(6) NULL DEFAULT NULL COMMENT '项目职务id',
  `pro_position` varchar(30) NULL DEFAULT NULL COMMENT '项目职务',
  `work_type_tid` smallint(6) NULL DEFAULT NULL COMMENT '工种id',
  `work_type` varchar(30) NULL DEFAULT NULL COMMENT '工种',
  `skill_type` varchar(50) NULL DEFAULT NULL COMMENT '技能可任工种',
  `qualification_tid` smallint(6) NULL DEFAULT NULL COMMENT '执业资格id',
  `qualification` varchar(30) NULL DEFAULT NULL COMMENT '执业资格',
  `increase_tid` smallint(6) NULL DEFAULT NULL COMMENT '增员原因id',
  `increase` varchar(30) NULL DEFAULT NULL COMMENT '增员原因',
  `inc_remark` varchar(225) NULL DEFAULT NULL COMMENT '增员备注',
  `happening` varchar(1000) NULL DEFAULT NULL COMMENT '奖惩情况',
  `end_time` date NULL DEFAULT NULL COMMENT '合同终止时间',
  `separation_time` date NULL DEFAULT NULL COMMENT '离职时间',
  `not_in_reason` varchar(30) NULL DEFAULT NULL COMMENT '不在岗原因',
  `data_id` varchar(200) NULL DEFAULT NULL COMMENT '数据Id',
  `resume` varchar(1000) NULL DEFAULT NULL COMMENT '工作简历',
  `remark` varchar(500) NULL DEFAULT NULL COMMENT '备注',
  `unit_jid` smallint(6) NULL DEFAULT NULL COMMENT '机关职位id',
  `unit_supervisor` smallint(6) NULL DEFAULT NULL COMMENT '机关职位是否主管',
  `project_jid` smallint(6) NULL DEFAULT NULL COMMENT '项目部职位',
  `project_supervisor` smallint(6) NULL DEFAULT NULL,
  `order_by` tinyint(5) NULL DEFAULT 5 COMMENT '职位权重',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_user_project
-- ----------------------------

-- ----------------------------
-- Table structure for dx_erp_user_role_depart
-- ----------------------------
DROP TABLE IF EXISTS `dx_erp_user_role_depart`;
CREATE TABLE `dx_erp_user_role_depart`  (
  `uid` varchar(70) NULL DEFAULT NULL COMMENT '用户id',
  `rid` smallint(6) NULL DEFAULT NULL COMMENT '角色id',
  `proid` smallint(6) NULL DEFAULT NULL COMMENT '项目部id,机关为0'
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_erp_user_role_depart
-- ----------------------------

-- ----------------------------
-- Table structure for dx_estimate_code_config
-- ----------------------------
DROP TABLE IF EXISTS `dx_estimate_code_config`;
CREATE TABLE `dx_estimate_code_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(100) NULL DEFAULT NULL COMMENT 'code',
  `code_name` varchar(1000) NULL DEFAULT NULL COMMENT '描述',
  `module` varchar(1000) NULL DEFAULT NULL COMMENT '模块',
  `module_name` varchar(1000) NULL DEFAULT NULL COMMENT '模块描述',
  `enable` tinyint(2) NULL DEFAULT 0 COMMENT '是否启用，0：是，1：否',
  `create_time` datetime(0) NOT NULL COMMENT '录入时间',
  `create_user` varchar(255) NOT NULL COMMENT '创建人',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uniq_code_index`(`code`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '标前测算CODE配置表';

-- ----------------------------
-- Records of dx_estimate_code_config
-- ----------------------------

-- ----------------------------
-- Table structure for dx_estimate_code_value_config
-- ----------------------------
DROP TABLE IF EXISTS `dx_estimate_code_value_config`;
CREATE TABLE `dx_estimate_code_value_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code_id` int(11) NOT NULL COMMENT 'code id',
  `code_value` int(11) NULL DEFAULT NULL COMMENT 'code对应的值',
  `code_value_name` varchar(1000) NULL DEFAULT NULL COMMENT 'code对应值的描述',
  `enable` tinyint(2) NULL DEFAULT 0 COMMENT '是否启用，0：是，1：否',
  `create_time` datetime(0) NOT NULL COMMENT '录入时间',
  `create_user` varchar(255) NOT NULL COMMENT '创建人',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '标前测算CODE_VALUE配置表';

-- ----------------------------
-- Records of dx_estimate_code_value_config
-- ----------------------------

-- ----------------------------
-- Table structure for dx_evaluate_assign_score
-- ----------------------------
DROP TABLE IF EXISTS `dx_evaluate_assign_score`;
CREATE TABLE `dx_evaluate_assign_score`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `join_size` int(11) NOT NULL COMMENT '参评个数',
  `a_plus` int(11) NOT NULL COMMENT 'A+',
  `a` int(11) NOT NULL COMMENT 'A',
  `b_plus` int(11) NOT NULL COMMENT 'B',
  `b` int(11) NOT NULL COMMENT 'B',
  `c` int(11) NOT NULL COMMENT 'C',
  `d_plus` int(11) NOT NULL COMMENT 'D+',
  `d` int(11) NOT NULL COMMENT 'D',
  `e_plus` int(11) NOT NULL COMMENT 'E+',
  `e` int(11) NOT NULL COMMENT 'E',
  PRIMARY KEY (`id`),
  INDEX `index_join_size`(`join_size`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '供方赋值分';

-- ----------------------------
-- Records of dx_evaluate_assign_score
-- ----------------------------

-- ----------------------------
-- Table structure for dx_evaluate_company_scoring
-- ----------------------------
DROP TABLE IF EXISTS `dx_evaluate_company_scoring`;
CREATE TABLE `dx_evaluate_company_scoring`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `report_id` int(11) NOT NULL COMMENT '报表ID',
  `survey_id` int(11) NOT NULL COMMENT '评价ID',
  `material_type_id` int(11) NOT NULL COMMENT '参评材料分类ID',
  `supplier_id` int(11) NOT NULL COMMENT '供方ID',
  `supplier_name` varchar(255) NOT NULL COMMENT '供方名称',
  `urgent_task` decimal(15, 2) NOT NULL COMMENT '配合紧急任务得分',
  `urgent_task_remark` varchar(2000) NULL DEFAULT NULL COMMENT '配合紧急任务备注',
  `refuse_urgent_task` decimal(15, 2) NOT NULL COMMENT '拒绝紧急任务得分',
  `refuse_urgent_task_remark` varchar(255) NULL DEFAULT NULL COMMENT '拒绝紧急任务备注',
  `research_task` decimal(15, 2) NOT NULL COMMENT '配合调研任务得分',
  `research_task_remark` varchar(2000) NULL DEFAULT NULL COMMENT '配合调研任务备注',
  `other_score` decimal(15, 2) NOT NULL COMMENT '其他加减分项',
  `other_score_remark` varchar(2000) NULL DEFAULT NULL COMMENT '其他加减分项说明',
  `create_user` varchar(255) NOT NULL COMMENT '创建人',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新时间',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `index_report_id`(`report_id`),
  INDEX `index_survey_id`(`survey_id`),
  INDEX `index_material_type_id`(`material_type_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_evaluate_company_scoring
-- ----------------------------

-- ----------------------------
-- Table structure for dx_evaluate_deduct_points
-- ----------------------------
DROP TABLE IF EXISTS `dx_evaluate_deduct_points`;
CREATE TABLE `dx_evaluate_deduct_points`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `report_id` int(11) NOT NULL COMMENT '报表ID',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  `supplier_id` int(11) NOT NULL COMMENT '供方ID',
  `material_type_id` int(11) NOT NULL COMMENT '材料类型ID',
  `deduction_id` int(11) NOT NULL COMMENT '扣分项id',
  `deduct` int(11) NOT NULL COMMENT '扣分值',
  `create_user` varchar(255) NOT NULL COMMENT '创建人',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新时间',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `index_report_id`(`report_id`),
  INDEX `index_supplier_id`(`supplier_id`),
  INDEX `index_project_id`(`project_id`),
  INDEX `index_material_type_id`(`material_type_id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '评价扣分';

-- ----------------------------
-- Records of dx_evaluate_deduct_points
-- ----------------------------

-- ----------------------------
-- Table structure for dx_evaluate_deduction
-- ----------------------------
DROP TABLE IF EXISTS `dx_evaluate_deduction`;
CREATE TABLE `dx_evaluate_deduction`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pid` int(11) NOT NULL DEFAULT 0 COMMENT '父类id',
  `title` varchar(200) NOT NULL COMMENT '扣分项',
  `deduct_type` int(11) NOT NULL COMMENT '扣分类型(0:不参与扣分,是最大值.1:直接扣完,2按次数扣分,3:填写扣分数)',
  `deduct_points` int(11) NOT NULL COMMENT '扣分数',
  `is_delete` tinyint(1) NOT NULL COMMENT '是否删除(0:否,1:是)',
  PRIMARY KEY (`id`),
  INDEX `index_pid`(`pid`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '评价扣分项';

-- ----------------------------
-- Records of dx_evaluate_deduction
-- ----------------------------

-- ----------------------------
-- Table structure for dx_evaluate_project_evaluation
-- ----------------------------
DROP TABLE IF EXISTS `dx_evaluate_project_evaluation`;
CREATE TABLE `dx_evaluate_project_evaluation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `report_id` int(11) NOT NULL COMMENT '报表ID',
  `project_id` int(11) NOT NULL COMMENT '项目部ID',
  `survey_id` int(11) NOT NULL COMMENT '评价ID',
  `supplier_id` int(11) NOT NULL COMMENT '供方ID',
  `supplier_name` varchar(255) NOT NULL COMMENT '供方名称',
  `material_type_id` varchar(255) NOT NULL COMMENT '材料类别ID',
  `material_type` varchar(255) NOT NULL COMMENT '材料类别',
  `material_ids` varchar(500) NOT NULL COMMENT '材料细目ID',
  `material_names` varchar(500) NOT NULL COMMENT '材料细目',
  `legal_person` varchar(255) NULL DEFAULT NULL COMMENT '法定代表人',
  `contract_person` varchar(255) NULL DEFAULT NULL COMMENT '合同签订人',
  `years` decimal(15, 2) NOT NULL COMMENT '供应年限',
  `supply` decimal(15, 2) NULL DEFAULT NULL COMMENT '供应额',
  `debt_type` int(11) NOT NULL COMMENT '垫资能力类型(1:钢材、沥青类.2:水泥、地材、线上料、模板类.3:外加剂、锚具、支座类,4:其他)',
  `debt` decimal(15, 2) NULL DEFAULT NULL COMMENT '欠款额',
  `deduct_points` int(15) NOT NULL COMMENT '评价扣分',
  `assign_score` int(15) NOT NULL COMMENT '赋值得分',
  `level` varchar(255) NOT NULL COMMENT '档次',
  `create_user` varchar(255) NOT NULL COMMENT '创建人',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新时间',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `index_material_type_id`(`material_type_id`),
  INDEX `index_report_id`(`report_id`),
  INDEX `index_project_id`(`project_id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '项目部评分汇总';

-- ----------------------------
-- Records of dx_evaluate_project_evaluation
-- ----------------------------

-- ----------------------------
-- Table structure for dx_evaluate_report
-- ----------------------------
DROP TABLE IF EXISTS `dx_evaluate_report`;
CREATE TABLE `dx_evaluate_report`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `join_type` varchar(255) NOT NULL COMMENT '参加评价供方类型(以逗号分隔)',
  `join_type_name` varchar(255) NOT NULL COMMENT '参加评价供方类型(以逗号分隔)',
  `start_time` date NOT NULL COMMENT '开始填报时间',
  `end_time` date NOT NULL COMMENT '结束填报时间',
  `block_time` tinyint(1) NOT NULL COMMENT '限制截至时间',
  `cycle_type` int(11) NOT NULL COMMENT '填报周期类型(0:季报)',
  `cycle_time` varchar(255) NOT NULL COMMENT '周期开始时间',
  `running` tinyint(1) NOT NULL COMMENT '报表状态(0:进行中,1:已结束)',
  `create_user` varchar(255) NOT NULL COMMENT '创建人',
  `create_user_name` varchar(255) NOT NULL COMMENT '创建人姓名',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `is_delete` tinyint(1) NOT NULL COMMENT '是否删除(0:否,1:是)',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_evaluate_report
-- ----------------------------

-- ----------------------------
-- Table structure for dx_factory_car
-- ----------------------------
DROP TABLE IF EXISTS `dx_factory_car`;
CREATE TABLE `dx_factory_car`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `no` varchar(255) NOT NULL COMMENT '编号',
  `car_no` varchar(255) NOT NULL COMMENT '车牌号',
  `driver` varchar(255) NULL DEFAULT NULL COMMENT '司机',
  `phone` varchar(255) NULL DEFAULT NULL COMMENT '联系电话',
  `task_count` int(11) NOT NULL COMMENT '已派单量',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  `create_user` varchar(255) NOT NULL COMMENT '创建人id',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  `is_delete` tinyint(1) NOT NULL COMMENT '是否删除(0:否,1:是)',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '车辆';

-- ----------------------------
-- Records of dx_factory_car
-- ----------------------------

-- ----------------------------
-- Table structure for dx_factory_component
-- ----------------------------
DROP TABLE IF EXISTS `dx_factory_component`;
CREATE TABLE `dx_factory_component`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `first_part_id` int(11) NOT NULL DEFAULT 0 COMMENT '一级部位ID',
  `first_part_name` varchar(255) NULL DEFAULT NULL COMMENT '二级部位名称',
  `second_part_id` int(11) NOT NULL DEFAULT 0 COMMENT '二级部位ID',
  `second_part_name` varchar(255) NULL DEFAULT NULL COMMENT '二级部位名称',
  `third_part_id` int(11) NOT NULL DEFAULT 0 COMMENT '三级部位ID',
  `third_part_name` varchar(255) NULL DEFAULT NULL COMMENT '三级部位名称',
  `fourth_part_id` int(11) NOT NULL DEFAULT 0 COMMENT '四级部位ID',
  `fourth_part_name` varchar(255) NULL DEFAULT NULL COMMENT '四级部位名称',
  `fifth_part_id` int(11) NOT NULL DEFAULT 0 COMMENT '五级部位ID',
  `fifth_part_name` varchar(255) NULL DEFAULT NULL COMMENT '五级部位名称',
  `sixth_part_id` int(11) NOT NULL DEFAULT 0 COMMENT '六级部位ID',
  `sixth_part_name` varchar(255) NULL DEFAULT NULL COMMENT '六级部位名称',
  `seventh_part_id` int(11) NOT NULL DEFAULT 0 COMMENT '七级部位ID',
  `seventh_part_name` varchar(255) NULL DEFAULT NULL COMMENT '七级部位名称',
  `eighth_part_id` int(11) NOT NULL DEFAULT 0 COMMENT '八级部位ID',
  `eighth_part_name` varchar(255) NULL DEFAULT NULL COMMENT '八级部位名称',
  `no` varchar(255) NOT NULL COMMENT '编号',
  `description` varchar(2000) NULL DEFAULT NULL COMMENT '构件描述',
  `photos` varchar(2000) NULL DEFAULT NULL COMMENT '构件大样图(数据格式:[{\"fid\":\"\"}])',
  `remark` varchar(2000) NULL DEFAULT NULL COMMENT '备注',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  `create_user` varchar(255) NOT NULL COMMENT '创建人id',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  `is_delete` tinyint(1) NOT NULL COMMENT '是否删除(0:否,1:是)',
  PRIMARY KEY (`id`),
  INDEX `index_project_id`(`project_id`),
  INDEX `index_first_part_id`(`first_part_id`),
  INDEX `index_second_part_id`(`second_part_id`),
  INDEX `index_third_part_id`(`third_part_id`),
  INDEX `index_fourth_part_id`(`fourth_part_id`),
  INDEX `index_fifth_part_id`(`fifth_part_id`),
  INDEX `index_sixth_part_id`(`sixth_part_id`),
  INDEX `index_seventh_part_id`(`sixth_part_id`),
  INDEX `index_eighth_part_id`(`eighth_part_id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '构件';

-- ----------------------------
-- Records of dx_factory_component
-- ----------------------------

-- ----------------------------
-- Table structure for dx_factory_component_item
-- ----------------------------
DROP TABLE IF EXISTS `dx_factory_component_item`;
CREATE TABLE `dx_factory_component_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `simple_image_tag` varchar(255) NOT NULL COMMENT '简图标识',
  `component_id` int(11) NOT NULL COMMENT '组件ID',
  `product_id` int(11) NOT NULL COMMENT '材料ID',
  `product_name` varchar(100) NOT NULL COMMENT '材料名称',
  `product_model_id` int(11) NULL DEFAULT NULL COMMENT '规格型号ID',
  `product_model` varchar(100) NOT NULL COMMENT '规格型号',
  `product_length` decimal(15, 2) NULL DEFAULT NULL COMMENT '单根长',
  `product_quantity` decimal(15, 3) NULL DEFAULT NULL COMMENT '根数',
  `total_length` decimal(15, 3) NULL DEFAULT NULL COMMENT '总长',
  `unit_weight` decimal(15, 3) NULL DEFAULT NULL,
  `total_weight` decimal(15, 3) NULL DEFAULT NULL COMMENT '总重',
  `work_hours` decimal(15, 2) NULL DEFAULT NULL COMMENT '工时',
  `photos` varchar(2000) NULL DEFAULT NULL COMMENT '构件简图(数据格式: {\"fid\":\"\"})',
  `is_delete` tinyint(1) NOT NULL COMMENT '是否删除(0:否,1:是)',
  PRIMARY KEY (`id`),
  INDEX `index_component_id`(`component_id`),
  INDEX `index_simple_image_tag`(`simple_image_tag`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '构件参数';

-- ----------------------------
-- Records of dx_factory_component_item
-- ----------------------------

-- ----------------------------
-- Table structure for dx_factory_order
-- ----------------------------
DROP TABLE IF EXISTS `dx_factory_order`;
CREATE TABLE `dx_factory_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_no` varchar(255) NOT NULL COMMENT '订单编号',
  `component_id` int(11) NOT NULL COMMENT '构件ID',
  `part_id` int(11) NOT NULL COMMENT '部位ID',
  `part_name` varchar(255) NOT NULL COMMENT '部位名称',
  `component_no` varchar(255) NOT NULL COMMENT '构件编号',
  `quantity` decimal(15, 2) NOT NULL COMMENT '数量',
  `urgent_level` int(11) NULL DEFAULT NULL COMMENT '紧急程度(0:一般,1:加急)',
  `expect_receive_time` datetime(0) NULL DEFAULT NULL COMMENT '预计领料时间',
  `receive_type` int(11) NOT NULL COMMENT '收料方式(0:厂家配送,1:到场领料)',
  `address` varchar(500) NULL DEFAULT NULL COMMENT '收货地址',
  `remark` varchar(500) NULL DEFAULT NULL COMMENT '备注',
  `audit_user_id` varchar(255) NULL DEFAULT NULL COMMENT '审核人ID',
  `audit_user_name` varchar(255) NULL DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  `audit_remark` varchar(500) NULL DEFAULT NULL COMMENT '审核备注',
  `batch_no` varchar(255) NULL DEFAULT NULL COMMENT '加工批号',
  `machining_time` datetime(0) NULL DEFAULT NULL COMMENT '加入生产任务时间',
  `machining_finish_time` datetime(0) NULL DEFAULT NULL COMMENT '加工完成时间',
  `send_car_id` int(11) NULL DEFAULT NULL COMMENT '配送车辆ID',
  `send_no` varchar(255) NULL DEFAULT NULL COMMENT '配送车辆编号',
  `send_car_no` varchar(50) NULL DEFAULT NULL COMMENT '配送车牌号',
  `send_user_name` varchar(255) NULL DEFAULT NULL COMMENT '配送司机',
  `send_user_phone` varchar(255) NULL DEFAULT NULL COMMENT '配送司机电话',
  `send_user_set_time` datetime(0) NULL DEFAULT NULL COMMENT '配送人员分配时间',
  `receive_user_id` varchar(255) NULL DEFAULT NULL COMMENT '领料人id',
  `receive_user_name` varchar(255) NULL DEFAULT NULL COMMENT '领料人',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT '领料时间',
  `status` int(11) NOT NULL COMMENT '状态(0:待审核,1:审核通过待生产,2:生产中,3:生产完成待配送,4:配送中,5:完成,-1:审核驳回)',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  `create_user` varchar(255) NOT NULL COMMENT '创建人id',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  `is_delete` tinyint(1) NOT NULL COMMENT '是否删除(0:否,1:是)',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_factory_order
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_attendance
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_attendance`;
CREATE TABLE `dx_fr_attendance`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '工人id',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目id',
  `year_quarter` varchar(255) NULL DEFAULT NULL COMMENT 'yyyy-mm-dd 打卡时间',
  `morning_time` datetime(0) NULL DEFAULT NULL COMMENT '上午打卡时间',
  `morning_address` varchar(255) NULL DEFAULT NULL COMMENT '上午打卡名称',
  `after_time` datetime(0) NULL DEFAULT NULL COMMENT '下午打卡时间',
  `after_address` varchar(255) NULL DEFAULT NULL COMMENT '下午打卡名称',
  `evening_time` datetime(0) NULL DEFAULT NULL COMMENT '晚上打卡时间',
  `evening_address` varchar(255) NULL DEFAULT NULL COMMENT '晚上打卡地方',
  `type` int(11) NULL DEFAULT NULL COMMENT '1小程序2 闸机  3 温控',
  PRIMARY KEY (`id`),
  INDEX `index_user_id`(`user_id`),
  INDEX `index_proid`(`proid`),
  INDEX `index_year_quarter`(`year_quarter`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_fr_attendance
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_attendance_config
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_attendance_config`;
CREATE TABLE `dx_fr_attendance_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `eight_for_xy` text NULL COMMENT '84坐标系',
  `zero_two_xy` text NULL COMMENT '02坐标系',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '围栏名称',
  `create_id` varchar(255) NULL DEFAULT NULL COMMENT '创建者id',
  `create_user` varchar(255) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_fr_attendance_config
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_attendance_detaile
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_attendance_detaile`;
CREATE TABLE `dx_fr_attendance_detaile`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `year_quarter` varchar(255) NULL DEFAULT NULL COMMENT '天   2019-09-10',
  `att_time` datetime(0) NULL DEFAULT NULL COMMENT '打卡时间',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '工人id',
  `fid` int(11) NULL DEFAULT NULL COMMENT '打卡记录照片',
  `address` varchar(255) NULL DEFAULT NULL COMMENT '打卡地点',
  `type` int(11) NULL DEFAULT NULL COMMENT '1.小程序 2 闸机 3 温控',
  `temperature` varchar(255) NULL DEFAULT NULL COMMENT '温度',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_fr_attendance_detaile
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_attendance_level
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_attendance_level`;
CREATE TABLE `dx_fr_attendance_level`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) NOT NULL COMMENT '姓名',
  `work_type` varchar(255) NULL DEFAULT NULL COMMENT '工种',
  `class_name` varchar(255) NULL DEFAULT NULL COMMENT '班组名称',
  `class_id` int(11) NULL DEFAULT NULL COMMENT '班组id',
  `phone` varchar(255) NULL DEFAULT NULL COMMENT '电话号',
  `team_name` varchar(255) NULL DEFAULT NULL COMMENT '劳务队',
  `leave_start_time` varchar(255) NULL DEFAULT NULL COMMENT '请假时间',
  `leave_end_time` varchar(255) NULL DEFAULT NULL COMMENT '销假时间',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '1 申请  2班组长同意 3班组长拒绝 4  计划部同意 5计划部不同意 6提出销假  7班组长同意 8计划员同意',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `team_id` int(11) NULL DEFAULT NULL COMMENT '劳务队id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) NULL DEFAULT NULL COMMENT '创建用户',
  `update_time` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '修改时间（销假记录时间）',
  `update_user` varchar(255) NULL DEFAULT NULL COMMENT '修改人(销假记录人)',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `sex` varchar(255) NULL DEFAULT NULL COMMENT '男性别',
  `num` varchar(255) NULL DEFAULT NULL COMMENT '身份证号',
  `type` varchar(255) NULL DEFAULT NULL COMMENT '请假类别',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci;

-- ----------------------------
-- Records of dx_fr_attendance_level
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_attendance_rat_config
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_attendance_rat_config`;
CREATE TABLE `dx_fr_attendance_rat_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rate` double NULL DEFAULT NULL COMMENT '出勤率',
  `create_id` varchar(255) NULL DEFAULT NULL,
  `create_name` varchar(255) NULL DEFAULT NULL COMMENT '创建名',
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_fr_attendance_rat_config
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_attendance_stranger
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_attendance_stranger`;
CREATE TABLE `dx_fr_attendance_stranger`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `year_quarter` varchar(255) NULL DEFAULT NULL COMMENT '打卡的天',
  `att_time` datetime(0) NULL DEFAULT NULL COMMENT '具体时间',
  `fid` int(11) NULL DEFAULT NULL COMMENT '抓拍的图片',
  `address` varchar(255) NULL DEFAULT NULL COMMENT '抓拍地点',
  `type` int(11) NULL DEFAULT NULL COMMENT '1.小程序 2 闸机 3 温控',
  `temperature` varchar(255) NULL DEFAULT NULL COMMENT '温度',
  `device_id` varchar(255) NULL DEFAULT NULL COMMENT '设备id',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_fr_attendance_stranger
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_attendance_warning
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_attendance_warning`;
CREATE TABLE `dx_fr_attendance_warning`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主鍵',
  `team_id` int(11) NULL DEFAULT NULL COMMENT '劳务队id',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `content` text NULL COMMENT '内容',
  `year_quarter` date NULL DEFAULT NULL COMMENT '时间 年月日',
  `create_name` varchar(255) NULL DEFAULT NULL COMMENT '填写人姓名',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '填写时间',
  `project_name` varchar(255) NULL DEFAULT NULL COMMENT '项目部名称',
  `monthly_attendance` double NULL DEFAULT NULL COMMENT '当天出勤率',
  `file_json` text NULL COMMENT '图片json',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_fr_attendance_warning
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_base_info
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_base_info`;
CREATE TABLE `dx_fr_base_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `num` varchar(100) NULL DEFAULT NULL COMMENT '身份证号',
  `start` varchar(255) NULL DEFAULT NULL COMMENT '有效期',
  `end` varchar(255) NULL DEFAULT NULL COMMENT '到期',
  `office` varchar(255) NULL DEFAULT NULL COMMENT '签证机关',
  `name` varchar(50) NULL DEFAULT NULL COMMENT '姓名',
  `nationality` varchar(50) NULL DEFAULT NULL COMMENT '民族',
  `sex` varchar(50) NULL DEFAULT NULL COMMENT '性别',
  `birth` varchar(50) NULL DEFAULT NULL COMMENT '生日',
  `address` varchar(100) NULL DEFAULT NULL COMMENT '住址',
  `face_featureLeft` text NULL COMMENT '左脸特征码',
  `face_featureRight` text NULL COMMENT '右脸特征码',
  `face_featureFront` text NULL COMMENT '正脸特征码',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `frontCardPhotoFid` int(11) NULL DEFAULT NULL COMMENT '正面身份证fid',
  `reverseCardPhotoFid` int(11) NULL DEFAULT NULL COMMENT '背面身份证fid',
  `leftFacePhotoFid` int(11) NULL DEFAULT NULL COMMENT '左脸人脸fid',
  `rightFacePhotoFid` int(11) NULL DEFAULT NULL COMMENT '右脸人脸fid',
  `frontFacePhotoFid` int(11) NULL DEFAULT NULL COMMENT '正脸fid',
  PRIMARY KEY (`id`),
  INDEX `index_num`(`num`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_fr_base_info
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_common_train
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_common_train`;
CREATE TABLE `dx_fr_common_train`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) NULL DEFAULT NULL COMMENT '标题',
  `principal` varchar(255) NULL DEFAULT NULL COMMENT '培训负责人',
  `training_content` varchar(255) NULL DEFAULT NULL COMMENT '培训内容',
  `training_time` date NULL DEFAULT NULL COMMENT '培训时间',
  `trainin_address` varchar(255) NULL DEFAULT NULL COMMENT '培训地点',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `courseware_fid` int(11) NULL DEFAULT NULL COMMENT '培训课件fid',
  `table_fid` int(11) NULL DEFAULT NULL COMMENT '签到表fid',
  `photo_fid` varchar(255) NULL DEFAULT NULL COMMENT '现场照片   例    55,666',
  `type` int(11) NULL DEFAULT NULL COMMENT '1.安质部  2.设备部  3.劳务实名制',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_fr_common_train
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_common_train_people
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_common_train_people`;
CREATE TABLE `dx_fr_common_train_people`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `train_id` int(11) NULL DEFAULT NULL COMMENT '培训记录表id',
  `user_id` varchar(255) NULL DEFAULT NULL COMMENT '体制内培训人员id',
  `farmers_id` int(11) NULL DEFAULT NULL COMMENT '工人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_fr_common_train_people
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_credential
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_credential`;
CREATE TABLE `dx_fr_credential`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '证书名称',
  `orderby` varchar(255) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_fr_credential
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_farmers
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_farmers`;
CREATE TABLE `dx_fr_farmers`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `class_id` int(11) NULL DEFAULT NULL COMMENT '班组id',
  `team_id` int(11) NULL DEFAULT NULL COMMENT '劳务队id',
  `face_id` int(11) NULL DEFAULT NULL COMMENT '工人人像信息',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '姓名',
  `idCard` varchar(255) NULL DEFAULT NULL COMMENT '身份证号',
  `sex` varchar(255) NULL DEFAULT NULL COMMENT '性别 男女',
  `famous` varchar(255) NULL DEFAULT NULL COMMENT '名族',
  `workName` int(11) NULL DEFAULT NULL COMMENT '岗位  1带班人   2 组长  3 组员',
  `workType` varchar(255) NULL DEFAULT NULL COMMENT '工种',
  `phone` varchar(255) NULL DEFAULT NULL COMMENT '本人电话',
  `contach_phone` varchar(255) NULL DEFAULT NULL COMMENT '第二联系人电话',
  `contact_name` varchar(255) NULL DEFAULT NULL COMMENT '第二联系人名字',
  `contach_relation` varchar(255) NULL DEFAULT NULL COMMENT '第二联系人关系',
  `address` varchar(255) NULL DEFAULT NULL COMMENT '家庭住址',
  `special_id` varchar(255) NULL DEFAULT NULL COMMENT '特种工证',
  `special_fid` int(11) NULL DEFAULT NULL COMMENT '特种工证件id',
  `special_type` varchar(255) NULL DEFAULT NULL COMMENT '证书职称3',
  `special_name` varchar(255) NULL DEFAULT NULL COMMENT '证书名称',
  `is_sign` varchar(255) NULL DEFAULT NULL COMMENT '是否签订劳务工合同',
  `join_time` date NULL DEFAULT NULL COMMENT '入场时间',
  `end_time` date NULL DEFAULT NULL COMMENT '退长时间',
  `remark` text NULL COMMENT '备注信息',
  `education` varchar(255) NULL DEFAULT NULL COMMENT '学历',
  `class_name` varchar(255) NULL DEFAULT NULL COMMENT '班组名称',
  `politics_status` varchar(255) NULL DEFAULT NULL COMMENT '政治面貌',
  `Types_of_labor` int(11) NULL DEFAULT NULL COMMENT '劳工种类  1是普通工人 2是特种工人',
  `bank_name` varchar(255) NULL DEFAULT NULL COMMENT '银行卡开户行',
  `bank_detaile` varchar(255) NULL DEFAULT NULL COMMENT '具体支行',
  `bank_num` varchar(255) NULL DEFAULT NULL COMMENT '卡号',
  `payroll_statement_fid` int(11) NULL DEFAULT NULL COMMENT '工资结算单',
  `sign_fid` int(11) NULL DEFAULT NULL COMMENT '用工合同fid',
  `bank_power_fid` int(11) NULL DEFAULT NULL COMMENT '委托书id',
  `bank_fid` int(11) NULL DEFAULT NULL COMMENT '银行卡id',
  `bank_cardholder` varchar(255) NULL DEFAULT NULL COMMENT '持卡人姓名',
  `special_num` varchar(255) NULL DEFAULT NULL COMMENT '特种工证件号',
  `is_investigation` int(11) NULL DEFAULT 1 COMMENT '征信',
  `tsgz_name` text NULL,
  PRIMARY KEY (`id`),
  INDEX `index_proid`(`proid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_fr_farmers
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_farmers_apply_for
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_farmers_apply_for`;
CREATE TABLE `dx_fr_farmers_apply_for`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `class_id` int(11) NULL DEFAULT NULL COMMENT '班组id',
  `team_id` int(11) NULL DEFAULT NULL COMMENT '劳务队id',
  `face_id` int(11) NULL DEFAULT NULL COMMENT '工人人像信息',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '姓名',
  `idCard` varchar(255) NULL DEFAULT NULL COMMENT '身份证号',
  `sex` varchar(255) NULL DEFAULT NULL COMMENT '性别 男女',
  `famous` varchar(255) NULL DEFAULT NULL COMMENT '名族',
  `workName` int(11) NULL DEFAULT NULL COMMENT '岗位  1带班人   2 组长  3 组员',
  `workType` varchar(255) NULL DEFAULT NULL COMMENT '工种',
  `phone` varchar(255) NULL DEFAULT NULL COMMENT '本人电话',
  `address` varchar(255) NULL DEFAULT NULL COMMENT '家庭住址',
  `join_time` date NULL DEFAULT NULL COMMENT '入场时间',
  `end_time` date NULL DEFAULT NULL COMMENT '退长时间',
  `remark` text NULL COMMENT '备注信息',
  `class_name` varchar(255) NULL DEFAULT NULL COMMENT '班组名称',
  `is_investigation` int(11) NULL DEFAULT 1 COMMENT '征信',
  `tsgz_name` text NULL,
  `status` int(11) NULL DEFAULT NULL COMMENT '1 未审批  2 班组长同意 3 带班人同意 4 项目同意 5 拒绝',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '申请时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '审批时间',
  `start` varchar(255) NULL DEFAULT NULL COMMENT '到期时间',
  `end` varchar(255) NULL DEFAULT NULL COMMENT '到期时间',
  `office` varchar(255) NULL DEFAULT NULL COMMENT '签证机关',
  `birth` varchar(255) NULL DEFAULT NULL COMMENT '生日',
  `face_feature_left` text NULL COMMENT '左脸特征码',
  `face_feature_right` text NULL COMMENT '有脸特征码',
  `face_feature_front` text NULL COMMENT '正脸特征码',
  `front_card_photo_fid` int(11) NULL DEFAULT NULL COMMENT '正面身份证id',
  `reverse_card_photo_fid` int(11) NULL DEFAULT NULL COMMENT '背面身份证id',
  `left_face_photo_fid` int(11) NULL DEFAULT NULL COMMENT '左脸fid',
  `right_face_photo_fid` int(11) NULL DEFAULT NULL COMMENT '右脸fid',
  `front_face_photo_fid` int(11) NULL DEFAULT NULL COMMENT '正脸fid',
  `team_name` varchar(255) NULL DEFAULT NULL COMMENT '劳务队名称',
  PRIMARY KEY (`id`),
  INDEX `index_proid`(`proid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_fr_farmers_apply_for
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_health
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_health`;
CREATE TABLE `dx_fr_health`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `farmers_id` int(11) NULL DEFAULT NULL COMMENT '劳工id',
  `health_time` date NULL DEFAULT NULL COMMENT '体检时间',
  `health_address` varchar(255) NULL DEFAULT NULL COMMENT '体检地点',
  `is_health` varchar(255) NULL DEFAULT NULL COMMENT '是否健康   1 健康 0不健康 ',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注信息',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `fid` int(11) NULL DEFAULT NULL COMMENT '体检记录Fid',
  `unhealthy_cause` text NULL COMMENT '不健康原因',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_fr_health
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_history
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_history`;
CREATE TABLE `dx_fr_history`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_fr_history
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_misconduct
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_misconduct`;
CREATE TABLE `dx_fr_misconduct`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `farmers_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `type` int(11) NULL DEFAULT NULL COMMENT '不良行为状态  1，一般 2，中等 3，重大',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '不良行为内容',
  `fid` int(11) NULL DEFAULT NULL COMMENT '附件照片fid',
  `address` varchar(255) NULL DEFAULT NULL COMMENT '发生地址',
  `year_quarter` varchar(255) NULL DEFAULT NULL COMMENT '时间',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '姓名',
  `num` varchar(255) NULL DEFAULT NULL COMMENT '身份证',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_fr_misconduct
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_misconduct_list
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_misconduct_list`;
CREATE TABLE `dx_fr_misconduct_list`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `team_id` int(11) NULL DEFAULT NULL COMMENT '劳务队id',
  `year_quarter` date NULL DEFAULT NULL COMMENT '发生时间',
  `type_json` text NULL COMMENT '不良行为分类json',
  `create_user` varchar(5000) NULL DEFAULT NULL COMMENT '创建时间',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `remark` text NULL COMMENT '备注',
  `file` text NULL COMMENT '图片json',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_fr_misconduct_list
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_misconduct_list_middle
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_misconduct_list_middle`;
CREATE TABLE `dx_fr_misconduct_list_middle`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mis_id` int(11) NULL DEFAULT NULL COMMENT '群体事件id',
  `user_name` varchar(255) NULL DEFAULT NULL COMMENT '用户名称',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `num` varchar(255) NULL DEFAULT NULL COMMENT '身份证号',
  `imgid` int(11) NULL DEFAULT NULL COMMENT '绑定图片id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci;

-- ----------------------------
-- Records of dx_fr_misconduct_list_middle
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_money
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_money`;
CREATE TABLE `dx_fr_money`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `farmers_id` int(11) NULL DEFAULT NULL COMMENT '工人id',
  `class_id` int(11) NULL DEFAULT NULL COMMENT '班组id',
  `team_id` int(11) NULL DEFAULT NULL COMMENT '劳务队id',
  `idcard` varchar(255) NULL DEFAULT NULL COMMENT '身份证号',
  `bank_name` varchar(255) NULL DEFAULT NULL COMMENT '银行账户',
  `bank_number` varchar(255) NULL DEFAULT NULL COMMENT '银行卡号',
  `att_day` double NULL DEFAULT NULL COMMENT '出勤天数',
  `update_att_day` double NULL DEFAULT NULL COMMENT '修改后出勤天数',
  `money` double NULL DEFAULT 0 COMMENT '工资元',
  `fid_json` text NULL COMMENT '工资凭证id',
  `year_quarter` varchar(255) NULL DEFAULT NULL COMMENT '发放月份 2019-01',
  `pay_time` datetime(0) NULL DEFAULT NULL COMMENT '发放时间',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '姓名',
  `job` varchar(255) NULL DEFAULT NULL COMMENT '工种 ',
  `work_name` int(255) NULL DEFAULT NULL COMMENT '1 代办人 2 班组长 3 班组员',
  `class_name` varchar(255) NULL DEFAULT NULL COMMENT '班组名称',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态 1保存 2有效',
  `pay_json` text NULL COMMENT '上面两个头',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_fr_money
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_output
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_output`;
CREATE TABLE `dx_fr_output`  (
  `id` int(11) NOT NULL COMMENT '主键',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_fr_output
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_robot_config
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_robot_config`;
CREATE TABLE `dx_fr_robot_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `robot_num` varchar(255) NULL DEFAULT NULL COMMENT '机器码',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `phone` varchar(255) NULL DEFAULT NULL COMMENT '手机标识',
  `type` int(11) NULL DEFAULT NULL COMMENT '1 路左  2 路右  3 正面',
  `status` varchar(11) NULL DEFAULT '0' COMMENT '状态枚举',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_fr_robot_config
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_special_type
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_special_type`;
CREATE TABLE `dx_fr_special_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '名称',
  `sort_type` smallint(6) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci;

-- ----------------------------
-- Records of dx_fr_special_type
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_type_work
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_type_work`;
CREATE TABLE `dx_fr_type_work`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '名称',
  `orderBy` int(255) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_fr_type_work
-- ----------------------------

-- ----------------------------
-- Table structure for dx_fr_wxopenid_code
-- ----------------------------
DROP TABLE IF EXISTS `dx_fr_wxopenid_code`;
CREATE TABLE `dx_fr_wxopenid_code`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `farmers_id` int(11) NULL DEFAULT NULL COMMENT '工人id',
  `num` varchar(255) NULL DEFAULT NULL COMMENT '工人身份证号',
  `openid` varchar(5000) NULL DEFAULT NULL COMMENT '微信标识符',
  `phone` varchar(255) NULL DEFAULT NULL COMMENT '手机号可选',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_fr_wxopenid_code
-- ----------------------------

-- ----------------------------
-- Table structure for dx_gbpart
-- ----------------------------
DROP TABLE IF EXISTS `dx_gbpart`;
CREATE TABLE `dx_gbpart`  (
  `partid` int(6) NOT NULL AUTO_INCREMENT COMMENT '部位id',
  `part_nmb` bigint(11) NULL DEFAULT NULL COMMENT '部位编号',
  `part_name` varchar(60) NOT NULL COMMENT '部位名称',
  `parentid` int(6) NULL DEFAULT NULL COMMENT 'parent',
  `lqs` tinyint(4) NULL DEFAULT NULL COMMENT '类别:0:其他，1:临建，2:路，3:桥，4:隧',
  `gld` tinyint(4) NULL DEFAULT NULL COMMENT '类别:0:其他，1:公路，2:铁路，3:地铁，4:房建',
  `part_url` int(255) NULL DEFAULT NULL COMMENT '部位上传位置',
  `ttype` tinyint(4) NULL DEFAULT NULL COMMENT '队伍类别',
  `order_by` int(11) NULL DEFAULT NULL COMMENT '排序',
  `explain` varchar(255) NULL DEFAULT NULL COMMENT '说明',
  `point` varchar(255) NULL DEFAULT NULL COMMENT '用户指引',
  `remark` varchar(20) NULL DEFAULT NULL COMMENT '1自动生成，2用户自定义',
  PRIMARY KEY (`partid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_gbpart
-- ----------------------------

-- ----------------------------
-- Table structure for dx_gbpart_coment
-- ----------------------------
DROP TABLE IF EXISTS `dx_gbpart_coment`;
CREATE TABLE `dx_gbpart_coment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NULL DEFAULT NULL COMMENT '名称',
  `numb` double(11, 2) NULL DEFAULT NULL COMMENT '数量',
  `sj_numb` double(11, 2) NULL DEFAULT NULL COMMENT '设计数量',
  `unit` varchar(255) NULL DEFAULT NULL COMMENT '单位',
  `formula` varchar(255) NULL DEFAULT NULL COMMENT '公式',
  `type` int(11) NULL DEFAULT NULL COMMENT '分类1:工程数量；2劳动要素；3设备要素；4物资材料',
  `xx_numb` double(11, 2) NULL DEFAULT NULL,
  `team` varchar(255) NULL DEFAULT NULL COMMENT '队伍名称',
  `status` int(11) NULL DEFAULT 1 COMMENT '状态（1正常，0删除）',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_gbpart_coment
-- ----------------------------

-- ----------------------------
-- Table structure for dx_gbpart_coment_project
-- ----------------------------
DROP TABLE IF EXISTS `dx_gbpart_coment_project`;
CREATE TABLE `dx_gbpart_coment_project`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `partid` int(11) NULL DEFAULT NULL,
  `gid` int(11) NULL DEFAULT NULL,
  `name` varchar(255) NULL DEFAULT NULL,
  `numb` double(11, 2) NULL DEFAULT NULL,
  `sj_numb` double(11, 2) NULL DEFAULT NULL,
  `unit` varchar(255) NULL DEFAULT NULL,
  `formula` varchar(255) NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL COMMENT '分类1:工程数量；2劳动要素；3设备要素；4物资材料',
  `xx_numb` double NULL DEFAULT NULL,
  `team` varchar(255) NULL DEFAULT NULL COMMENT '队伍名称',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_gbpart_coment_project
-- ----------------------------

-- ----------------------------
-- Table structure for dx_gbpart_project
-- ----------------------------
DROP TABLE IF EXISTS `dx_gbpart_project`;
CREATE TABLE `dx_gbpart_project`  (
  `partid` bigint(6) NOT NULL AUTO_INCREMENT COMMENT '部位id',
  `fid` int(11) NULL DEFAULT NULL,
  `part_nmb` bigint(11) NULL DEFAULT NULL COMMENT '部位编号',
  `part_name` varchar(255) NOT NULL COMMENT '部位名称',
  `parentid` bigint(6) NULL DEFAULT NULL COMMENT '父id',
  `lqs` tinyint(4) NULL DEFAULT NULL COMMENT '类别:0:其他，1:临建，2:路，3:桥，4:隧',
  `gld` tinyint(4) NULL DEFAULT NULL COMMENT '类别:0:其他，1:公路，2:铁路，3:地铁，4:房建',
  `ttype` tinyint(4) NULL DEFAULT NULL COMMENT '队伍类别',
  `order_by` bigint(11) NULL DEFAULT NULL COMMENT '排序',
  `explain` varchar(255) NULL DEFAULT NULL COMMENT '说明',
  `point` varchar(255) NULL DEFAULT NULL COMMENT '用户指引',
  `start_mileage` varchar(50) NULL DEFAULT NULL COMMENT '开始里程',
  `end_mileage` varchar(50) NULL DEFAULT NULL COMMENT '结束里程',
  `teams` varchar(1000) NULL DEFAULT NULL COMMENT '队伍id',
  `start_mileage_numb` double NULL DEFAULT NULL COMMENT '开始里程数字',
  `end_mileage_numb` double NULL DEFAULT NULL COMMENT '结束里程数字',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `sj_start_time` datetime(0) NULL DEFAULT NULL COMMENT '实际开始时间',
  `sj_end_time` datetime(0) NULL DEFAULT NULL COMMENT '实际结束时间',
  `contrast` int(11) NULL DEFAULT NULL COMMENT '0：正常，1：超前，2：逾期',
  `numb` int(11) NULL DEFAULT NULL COMMENT '数量',
  `unit` int(11) NULL DEFAULT NULL COMMENT '单位',
  `formula` varchar(255) NULL DEFAULT NULL COMMENT '公式',
  `day_numb` double NULL DEFAULT NULL COMMENT '工期',
  `remark` varchar(20) NULL DEFAULT NULL,
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `steps` varchar(255) NULL DEFAULT NULL COMMENT '工序数组',
  `pretask` varchar(255) NULL DEFAULT '' COMMENT '前置部位加时间',
  `after_task` varchar(255) NULL DEFAULT NULL COMMENT '后置任务',
  `freetime` double(11, 2) NULL DEFAULT 0.00 COMMENT '时标网络图时间',
  `subareay` int(11) NULL DEFAULT 0 COMMENT '时标网络图所需字段',
  `expect_starttime` datetime(0) NULL DEFAULT NULL COMMENT '预期计划开始时间',
  `expect_endtime` datetime(0) NULL DEFAULT NULL COMMENT '预期计划完成时间',
  `guide_startdate` datetime(0) NULL DEFAULT NULL COMMENT '指导开始时间',
  `guide_enddate` datetime(0) NULL DEFAULT NULL COMMENT '指导结束时间',
  `mainline` int(11) NULL DEFAULT NULL COMMENT '关键线路',
  `phbid` bigint(20) NULL DEFAULT NULL COMMENT '配合比id',
  `phb_time` datetime(0) NULL DEFAULT NULL COMMENT '配合比时间',
  `phb_stoptime` datetime(0) NULL DEFAULT NULL COMMENT '配合比到期时间',
  `phb_shujv` varchar(255) NULL DEFAULT NULL COMMENT '配合比数据',
  `type` int(11) NULL DEFAULT NULL COMMENT '分类：公路铁路',
  PRIMARY KEY (`partid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_gbpart_project
-- ----------------------------

-- ----------------------------
-- Table structure for dx_gbpart_step
-- ----------------------------
DROP TABLE IF EXISTS `dx_gbpart_step`;
CREATE TABLE `dx_gbpart_step`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `step_name` varchar(255) NULL DEFAULT NULL,
  `step_numb` varchar(255) NULL DEFAULT NULL COMMENT '工序编号',
  `spid` int(11) NULL DEFAULT NULL,
  `use_time` double NULL DEFAULT NULL,
  `order_by` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_gbpart_step
-- ----------------------------

-- ----------------------------
-- Table structure for dx_gbpart_three_project
-- ----------------------------
DROP TABLE IF EXISTS `dx_gbpart_three_project`;
CREATE TABLE `dx_gbpart_three_project`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `partid` bigint(6) NULL DEFAULT NULL COMMENT '部位id',
  `part_nmb` bigint(11) NULL DEFAULT NULL COMMENT '部位编号',
  `part_name` varchar(60) NULL DEFAULT NULL COMMENT '部位名称',
  `parentid` bigint(6) NULL DEFAULT NULL COMMENT 'parent',
  `lqs` tinyint(4) NULL DEFAULT NULL COMMENT '类别:0:其他，1:临建，2:路，3:桥，4:隧',
  `gld` tinyint(4) NULL DEFAULT NULL COMMENT '类别:0:其他，1:公路，2:铁路，3:地铁，4:房建',
  `part_url` int(255) NULL DEFAULT NULL COMMENT '部位上传位置',
  `ttype` tinyint(4) NULL DEFAULT NULL COMMENT '队伍类别',
  `order_by` bigint(11) NULL DEFAULT NULL COMMENT '排序',
  `explain` varchar(255) NULL DEFAULT NULL COMMENT '说明',
  `point` varchar(255) NULL DEFAULT NULL COMMENT '用户指引',
  `remark` varchar(20) NULL DEFAULT NULL,
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `part_status` int(11) NULL DEFAULT 1 COMMENT '部位状态:1正常；2不显示',
  `is_part` int(11) NULL DEFAULT 1 COMMENT '判断部位是否从公司层面拉去：1是；2否',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_gbpart_three_project
-- ----------------------------

-- ----------------------------
-- Table structure for dx_labor_addscore
-- ----------------------------
DROP TABLE IF EXISTS `dx_labor_addscore`;
CREATE TABLE `dx_labor_addscore`  (
  `aid` smallint(6) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `add_item` varchar(20) NULL DEFAULT NULL COMMENT '加分项名字',
  `project_score` float NULL DEFAULT NULL COMMENT '加分值',
  `status` varchar(15) NULL DEFAULT NULL COMMENT '1/等级计算(A+)\r\n2/参评个数计算\r\n3/特殊项目计算\r\n',
  `point_deduction_standard` varchar(20) NULL DEFAULT NULL COMMENT '扣分标准',
  `flagScore` tinyint(4) NULL DEFAULT NULL COMMENT '0加分 1减分',
  PRIMARY KEY (`aid`),
  INDEX `index_aid`(`aid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_labor_addscore
-- ----------------------------

-- ----------------------------
-- Table structure for dx_labor_blacklist
-- ----------------------------
DROP TABLE IF EXISTS `dx_labor_blacklist`;
CREATE TABLE `dx_labor_blacklist`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '姓名',
  `num` varchar(255) NULL DEFAULT NULL COMMENT '身份证号',
  `score` double NULL DEFAULT NULL COMMENT '得分',
  `year_quarter` varchar(255) NULL DEFAULT NULL COMMENT '评价时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_labor_blacklist
-- ----------------------------

-- ----------------------------
-- Table structure for dx_labor_credit_rating
-- ----------------------------
DROP TABLE IF EXISTS `dx_labor_credit_rating`;
CREATE TABLE `dx_labor_credit_rating`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `credit_name` varchar(20) NULL DEFAULT NULL COMMENT '信用名称 a1=因施工原因发生较大安全事故  a2=将劳务分包内容转包且不能有效管控的',
  `score` double NULL DEFAULT NULL COMMENT '所扣的分',
  `t_id` smallint(6) NULL DEFAULT NULL COMMENT '劳务队id',
  `year_quarter` varchar(30) NULL DEFAULT NULL COMMENT '季度数',
  `flag` smallint(6) NULL DEFAULT 0 COMMENT '信息有效状态 0无效 1有效',
  `proid` smallint(6) NULL DEFAULT NULL COMMENT '项目部id',
  `status` varchar(20) NULL DEFAULT NULL COMMENT '分类标识',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_labor_credit_rating
-- ----------------------------

-- ----------------------------
-- Table structure for dx_labor_dle_project
-- ----------------------------
DROP TABLE IF EXISTS `dx_labor_dle_project`;
CREATE TABLE `dx_labor_dle_project`  (
  `eid` smallint(6) NULL DEFAULT NULL COMMENT '机关层面汇总表id',
  `year_quarter` varchar(50) NULL DEFAULT NULL COMMENT '年份-季度',
  `pid` smallint(6) NULL DEFAULT NULL COMMENT '项目部id',
  `tid` smallint(6) NULL DEFAULT NULL COMMENT '施工队id',
  INDEX `index_tid`(`tid`),
  INDEX `index_eid`(`eid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_labor_dle_project
-- ----------------------------

-- ----------------------------
-- Table structure for dx_labor_evaluation
-- ----------------------------
DROP TABLE IF EXISTS `dx_labor_evaluation`;
CREATE TABLE `dx_labor_evaluation`  (
  `eid` int(6) NOT NULL AUTO_INCREMENT COMMENT 'id\r\n',
  `team_id` smallint(6) NOT NULL COMMENT '队伍id',
  `team_categoryid` smallint(6) NULL DEFAULT NULL COMMENT '队伍类别id',
  `level_id` smallint(6) NULL DEFAULT NULL COMMENT '等级排名id',
  `before_production` float NULL DEFAULT NULL COMMENT '以前产值',
  `before_production_score` float NULL DEFAULT NULL COMMENT '以前产值得分',
  `three_production` float NULL DEFAULT NULL COMMENT '近三年产值',
  `three_production_score` float NULL DEFAULT NULL COMMENT '近三年产值得分',
  `current_production` float NULL DEFAULT NULL COMMENT '当期产值',
  `current_production_score` float NULL DEFAULT NULL COMMENT '当期产值得分',
  `misconduct_score` float NULL DEFAULT NULL COMMENT '公司不良行为扣分',
  `project_size` smallint(11) NULL DEFAULT NULL COMMENT '参评项目个数',
  `team_size_level` int(11) NULL DEFAULT NULL COMMENT '队伍规模级别',
  `count` float NULL DEFAULT NULL COMMENT '总分',
  `spproject_id` smallint(6) NULL DEFAULT NULL COMMENT '特殊项目加分',
  `construction_year` smallint(6) NULL DEFAULT NULL COMMENT '施工年限',
  `construction_year_score` float NULL DEFAULT NULL COMMENT '施工年限得分',
  `high_scores` smallint(6) NULL DEFAULT NULL COMMENT '排名id',
  `year_quarter` varchar(50) NULL DEFAULT NULL COMMENT '年份-季度',
  `team_size_score` double NULL DEFAULT NULL COMMENT '队伍规模级别得分',
  `level_id_score` double NULL DEFAULT NULL COMMENT '队伍等级得分',
  `partid_score` double NULL DEFAULT NULL COMMENT '部位得分',
  `peoplesize` smallint(6) NULL DEFAULT NULL COMMENT '人数',
  `flag` tinyint(5) NULL DEFAULT NULL COMMENT '有效级别0无效 1有效',
  `construction_year_id` smallint(6) NULL DEFAULT NULL COMMENT '施工年限id',
  `outputflage_id` smallint(6) NULL DEFAULT NULL COMMENT '产值类别id(土石方，隧道)',
  `outptflag` varchar(20) NULL DEFAULT NULL COMMENT '产值类别(土石方，隧道)',
  `team_size_name` varchar(10) NULL DEFAULT NULL COMMENT 'ABC等级名称',
  `proid` smallint(11) NULL DEFAULT NULL COMMENT '部门id',
  `projectSize_score` double NULL DEFAULT NULL COMMENT '参评项目个数得分',
  `deduct_marks` double NULL DEFAULT NULL COMMENT '信用评价分',
  `deduct_marks_pro` double NULL DEFAULT NULL COMMENT '信用评价扣分',
  `is_update` smallint(6) NULL DEFAULT 0 COMMENT '是否可以修改 0可以 1不可以',
  `construction_content` varchar(50) NULL DEFAULT NULL COMMENT '具体施工内容',
  `representative` varchar(20) NULL DEFAULT NULL COMMENT '法定代表人',
  `fid` text NULL COMMENT '文件id',
  PRIMARY KEY (`eid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_labor_evaluation
-- ----------------------------

-- ----------------------------
-- Table structure for dx_labor_evaluationunit
-- ----------------------------
DROP TABLE IF EXISTS `dx_labor_evaluationunit`;
CREATE TABLE `dx_labor_evaluationunit`  (
  `eid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id\r\n',
  `team_id` smallint(6) NOT NULL COMMENT '队伍id',
  `team_categoryid` smallint(6) NULL DEFAULT NULL COMMENT '队伍类别id',
  `level_id` smallint(6) NULL DEFAULT NULL COMMENT '等级排名id',
  `before_production` float NULL DEFAULT NULL COMMENT '以前产值',
  `before_production_score` float NULL DEFAULT NULL COMMENT '以前产值得分',
  `three_production` float NULL DEFAULT NULL COMMENT '近三年产值',
  `three_production_score` float NULL DEFAULT NULL COMMENT '近三年产值得分',
  `current_production` float NULL DEFAULT NULL COMMENT '当期产值',
  `current_production_score` float NULL DEFAULT NULL COMMENT '当期产值得分',
  `misconduct_score` float NULL DEFAULT 0 COMMENT '公司不良行为扣分',
  `project_size` smallint(11) NULL DEFAULT NULL COMMENT '参评项目个数',
  `team_size_level` int(11) NULL DEFAULT NULL COMMENT '队伍规模级别',
  `count` float NULL DEFAULT NULL COMMENT '总分',
  `spproject_id` smallint(6) NULL DEFAULT NULL COMMENT '特殊项目加分id',
  `construction_year` smallint(6) NULL DEFAULT NULL COMMENT '施工年限',
  `construction_year_score` float NULL DEFAULT NULL COMMENT '施工年限得分',
  `high_scores` smallint(6) NULL DEFAULT NULL COMMENT '排名id',
  `year_quarter` varchar(50) NULL DEFAULT NULL COMMENT '年份-季度',
  `team_size_score` double NULL DEFAULT NULL COMMENT '队伍规模级别得分',
  `level_id_score` double NULL DEFAULT NULL COMMENT '队伍等级得分',
  `partid_score` double NULL DEFAULT NULL COMMENT '部位得分',
  `peoplesize` smallint(6) NULL DEFAULT NULL COMMENT '人数',
  `flag` tinyint(5) NULL DEFAULT NULL COMMENT '有效级别0无效 1有效',
  `construction_year_id` smallint(6) NULL DEFAULT NULL COMMENT '施工年限id',
  `outputflage_id` smallint(6) NULL DEFAULT NULL COMMENT '产值类别id(土石方，隧道)',
  `outptflag` varchar(20) NULL DEFAULT NULL COMMENT '产值类别(土石方，隧道)',
  `team_size_name` varchar(10) NULL DEFAULT NULL COMMENT 'ABC等级名称',
  `proid` smallint(11) NULL DEFAULT NULL COMMENT '部门id',
  `projectSize_score` double NULL DEFAULT NULL COMMENT '参评项目个数得分',
  `final_count` double NULL DEFAULT NULL COMMENT '最总得分',
  `is_update` tinyint(4) NULL DEFAULT 0 COMMENT '是否可以修改 0可以1 不行',
  PRIMARY KEY (`eid`),
  INDEX `index_team_id`(`team_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_labor_evaluationunit
-- ----------------------------

-- ----------------------------
-- Table structure for dx_labor_level_size
-- ----------------------------
DROP TABLE IF EXISTS `dx_labor_level_size`;
CREATE TABLE `dx_labor_level_size`  (
  `id` smallint(6) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `A+` smallint(6) NULL DEFAULT NULL COMMENT 'A+',
  `size` smallint(6) NOT NULL COMMENT '次数',
  `A` smallint(6) NULL DEFAULT NULL COMMENT 'B+',
  `B+` smallint(6) NULL DEFAULT NULL,
  `B` smallint(6) NULL DEFAULT NULL,
  `C` smallint(6) NULL DEFAULT NULL,
  `D+` smallint(6) NULL DEFAULT NULL,
  `D` smallint(6) NULL DEFAULT NULL,
  `E+` smallint(6) NULL DEFAULT NULL,
  `E` smallint(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_labor_level_size
-- ----------------------------

-- ----------------------------
-- Table structure for dx_labor_output_points
-- ----------------------------
DROP TABLE IF EXISTS `dx_labor_output_points`;
CREATE TABLE `dx_labor_output_points`  (
  `oid` smallint(11) NOT NULL AUTO_INCREMENT,
  `tid` smallint(6) NULL DEFAULT NULL,
  `year` varchar(10) NULL DEFAULT NULL,
  `quarter1` double NULL DEFAULT NULL,
  `quarter2` double NULL DEFAULT NULL,
  `quarter3` double NULL DEFAULT NULL,
  `quarter4` double NULL DEFAULT NULL,
  PRIMARY KEY (`oid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_labor_output_points
-- ----------------------------

-- ----------------------------
-- Table structure for dx_labor_team_category
-- ----------------------------
DROP TABLE IF EXISTS `dx_labor_team_category`;
CREATE TABLE `dx_labor_team_category`  (
  `tid` smallint(6) NULL DEFAULT NULL COMMENT '施工队id',
  `year_quarter` varchar(20) NULL DEFAULT NULL COMMENT '季度数',
  `proid` smallint(6) NULL DEFAULT NULL COMMENT '项目部id',
  `tcid` smallint(6) NULL DEFAULT NULL COMMENT '队伍类别id',
  `id` int(11) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_labor_team_category
-- ----------------------------

-- ----------------------------
-- Table structure for dx_labor_team_categoryunit
-- ----------------------------
DROP TABLE IF EXISTS `dx_labor_team_categoryunit`;
CREATE TABLE `dx_labor_team_categoryunit`  (
  `tid` smallint(6) NULL DEFAULT NULL COMMENT '施工队id',
  `year_quarter` varchar(20) NULL DEFAULT NULL COMMENT '季度数',
  `proid` smallint(6) NULL DEFAULT NULL COMMENT '项目部id',
  `tcid` smallint(6) NULL DEFAULT NULL COMMENT '队伍类别id',
  `id` int(11) NULL DEFAULT NULL,
  INDEX `index_tid`(`tid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_labor_team_categoryunit
-- ----------------------------

-- ----------------------------
-- Table structure for dx_labor_team_partid
-- ----------------------------
DROP TABLE IF EXISTS `dx_labor_team_partid`;
CREATE TABLE `dx_labor_team_partid`  (
  `tid` smallint(6) NULL DEFAULT NULL COMMENT '施工队id',
  `pid` smallint(6) NULL DEFAULT NULL COMMENT '部位id',
  `year_quarter` varchar(255) NULL DEFAULT NULL COMMENT '年份季度',
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `proid` smallint(6) NULL DEFAULT NULL COMMENT '项目部id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_labor_team_partid
-- ----------------------------

-- ----------------------------
-- Table structure for dx_labor_team_partidunit
-- ----------------------------
DROP TABLE IF EXISTS `dx_labor_team_partidunit`;
CREATE TABLE `dx_labor_team_partidunit`  (
  `tid` smallint(6) NULL DEFAULT NULL COMMENT '施工队id',
  `year_quarter` varchar(20) NULL DEFAULT NULL COMMENT '年份季度',
  `proid` smallint(6) NULL DEFAULT NULL COMMENT '项目部id',
  INDEX `index_tid`(`tid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_labor_team_partidunit
-- ----------------------------

-- ----------------------------
-- Table structure for dx_labor_team_project
-- ----------------------------
DROP TABLE IF EXISTS `dx_labor_team_project`;
CREATE TABLE `dx_labor_team_project`  (
  `ctid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  `team_name` varchar(30) NULL DEFAULT NULL COMMENT '公司名称',
  `principal` varchar(10) NULL DEFAULT NULL COMMENT '法定代表人',
  `principal_phone` varchar(15) NULL DEFAULT NULL COMMENT '合同签订人电话',
  `is_tunnel_team` tinyint(4) NULL DEFAULT 0 COMMENT '是否是隧道队',
  `approach_time` date NULL DEFAULT NULL,
  `out_of_time` date NULL DEFAULT NULL,
  `operator_id` varchar(255) NULL DEFAULT NULL,
  `operator_time` datetime(0) NULL DEFAULT NULL,
  `flag` smallint(6) NULL DEFAULT 1 COMMENT '是否退场  1 没退场有效  0  退场无效',
  `principaltwo` varchar(20) NULL DEFAULT NULL COMMENT '合同签订人2',
  INDEX `index_ctid`(`ctid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_labor_team_project
-- ----------------------------

-- ----------------------------
-- Table structure for dx_labor_team_score
-- ----------------------------
DROP TABLE IF EXISTS `dx_labor_team_score`;
CREATE TABLE `dx_labor_team_score`  (
  `tid` smallint(6) NULL DEFAULT NULL COMMENT '队伍id',
  `sid` smallint(6) NULL DEFAULT NULL COMMENT '得分id'
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_labor_team_score
-- ----------------------------

-- ----------------------------
-- Table structure for dx_labor_teamcategory
-- ----------------------------
DROP TABLE IF EXISTS `dx_labor_teamcategory`;
CREATE TABLE `dx_labor_teamcategory`  (
  `cid` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cname` varchar(20) NULL DEFAULT NULL COMMENT '名称',
  `cscore` double NULL DEFAULT NULL COMMENT '分项',
  PRIMARY KEY (`cid`),
  INDEX `index_cid`(`cid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_labor_teamcategory
-- ----------------------------

-- ----------------------------
-- Table structure for dx_labor_teamsize
-- ----------------------------
DROP TABLE IF EXISTS `dx_labor_teamsize`;
CREATE TABLE `dx_labor_teamsize`  (
  `eid` smallint(6) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `level` smallint(6) NULL DEFAULT NULL COMMENT '级别',
  `people_size` smallint(10) NULL DEFAULT NULL COMMENT '最少人数',
  `team_score` float NULL DEFAULT NULL COMMENT '分数',
  `pname` varchar(20) NULL DEFAULT NULL COMMENT '展示',
  PRIMARY KEY (`eid`),
  INDEX `index_eid`(`eid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_labor_teamsize
-- ----------------------------

-- ----------------------------
-- Table structure for dx_labor_year
-- ----------------------------
DROP TABLE IF EXISTS `dx_labor_year`;
CREATE TABLE `dx_labor_year`  (
  `id` smallint(6) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `year` tinyint(4) NULL DEFAULT NULL COMMENT '最小年份',
  `year_score` double NULL DEFAULT NULL COMMENT '分数',
  `year_name` varchar(50) NULL DEFAULT NULL COMMENT '年限name',
  `remark` varchar(50) NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_labor_year
-- ----------------------------

-- ----------------------------
-- Table structure for dx_ldssc_contract
-- ----------------------------
DROP TABLE IF EXISTS `dx_ldssc_contract`;
CREATE TABLE `dx_ldssc_contract`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contract_numb` varchar(255) NULL DEFAULT NULL COMMENT '合同清单编号',
  `contract_name` varchar(255) NULL DEFAULT NULL COMMENT '合同清单名称',
  `ratio_name` varchar(500) NULL DEFAULT NULL COMMENT '对比时需要的名称',
  `unit_id` int(11) NULL DEFAULT NULL COMMENT '单位id',
  `unit_name` varchar(255) NULL DEFAULT NULL COMMENT '单位名称',
  `contract_price` double(11, 2) NULL DEFAULT NULL COMMENT '综合单价',
  `contract_qty` double(11, 2) NULL DEFAULT NULL COMMENT '工程量',
  `contract_money` double(11, 2) NULL DEFAULT NULL COMMENT '合价',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `order_by` bigint(20) NULL DEFAULT NULL COMMENT '排序',
  `type` int(11) NULL DEFAULT NULL COMMENT '分类',
  `level_length` int(11) NULL DEFAULT NULL COMMENT '层级长度',
  `level_numb` varchar(255) NULL DEFAULT NULL COMMENT '层级numb',
  `level` int(11) NULL DEFAULT NULL COMMENT '层级',
  `contrast_status` int(11) NULL DEFAULT 0 COMMENT '1相同，2相似，3不同',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = 'DX_LDSSC_CONTRACT\r\n合同清单表';

-- ----------------------------
-- Records of dx_ldssc_contract
-- ----------------------------

-- ----------------------------
-- Table structure for dx_ldssc_detailedlist_formwork
-- ----------------------------
DROP TABLE IF EXISTS `dx_ldssc_detailedlist_formwork`;
CREATE TABLE `dx_ldssc_detailedlist_formwork`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `number` varchar(255) NOT NULL COMMENT '编号',
  `contract_numb` varchar(255) NULL DEFAULT NULL COMMENT '对比时需要的编号',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '名称',
  `unit` int(11) NULL DEFAULT NULL COMMENT '单位id',
  `unit_name` varchar(255) NULL DEFAULT NULL COMMENT '单位名称',
  `key_word` varchar(255) NULL DEFAULT NULL COMMENT '关键词',
  `estimate` varchar(255) NULL DEFAULT NULL COMMENT '工程计量',
  `work_content` varchar(255) NULL DEFAULT NULL COMMENT '工作内容',
  `type` int(11) NULL DEFAULT NULL COMMENT '分类  1:公路',
  `level` int(11) NULL DEFAULT NULL COMMENT '层级',
  `level_numb` varchar(255) NULL DEFAULT NULL COMMENT '层级分组',
  `order_by` int(11) NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '清单总库表';

-- ----------------------------
-- Records of dx_ldssc_detailedlist_formwork
-- ----------------------------

-- ----------------------------
-- Table structure for dx_ldssc_detailedlist_formwork_project
-- ----------------------------
DROP TABLE IF EXISTS `dx_ldssc_detailedlist_formwork_project`;
CREATE TABLE `dx_ldssc_detailedlist_formwork_project`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(255) NULL DEFAULT NULL COMMENT '标准清单编号',
  `number_ht` varchar(255) NULL DEFAULT NULL COMMENT '合同清单编号',
  `name_ht` varchar(255) NULL DEFAULT NULL COMMENT '合同清单名称',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '清单名称',
  `unit` int(11) NULL DEFAULT NULL COMMENT '单位id',
  `unit_name` varchar(255) NULL DEFAULT NULL COMMENT '单位名称',
  `type` int(11) NULL DEFAULT NULL COMMENT '分类  1:公路',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `order_by` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '清单总库表';

-- ----------------------------
-- Records of dx_ldssc_detailedlist_formwork_project
-- ----------------------------

-- ----------------------------
-- Table structure for dx_ldssc_guidance_formwork
-- ----------------------------
DROP TABLE IF EXISTS `dx_ldssc_guidance_formwork`;
CREATE TABLE `dx_ldssc_guidance_formwork`  (
  `guidanceid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `guidance_numb` varchar(255) NOT NULL COMMENT '指导价编号',
  `sign` varchar(255) NULL DEFAULT NULL COMMENT '标识',
  `guidance_name` varchar(255) NULL DEFAULT NULL COMMENT '指导价名称',
  `price` double(11, 2) NULL DEFAULT NULL COMMENT '单价',
  `unit_id` int(11) NULL DEFAULT NULL COMMENT '单位id',
  `unit_name` varchar(255) NULL DEFAULT NULL COMMENT '单位名称',
  `guidance_rate` double(11, 4) NULL DEFAULT NULL COMMENT '单位比率',
  `guidance_wark` varchar(1000) NULL DEFAULT NULL COMMENT '工作内容',
  `guidance_cost` varchar(1000) NULL DEFAULT NULL COMMENT '费用组成',
  `guidance_role` varchar(255) NULL DEFAULT NULL COMMENT '计量规则',
  `title_one` varchar(1000) NULL DEFAULT NULL COMMENT '标题一',
  `value_one` varchar(1000) NULL DEFAULT NULL COMMENT '值一',
  `title_two` varchar(1000) NULL DEFAULT NULL COMMENT '标题二',
  `value_two` varchar(1000) NULL DEFAULT NULL COMMENT '值二',
  `title_three` varchar(1000) NULL DEFAULT NULL COMMENT '标题三',
  `value_three` varchar(1000) NULL DEFAULT NULL COMMENT '值三',
  `type` int(11) NULL DEFAULT NULL COMMENT '分类1 路基；2路面',
  `level` varchar(20) NULL DEFAULT NULL COMMENT '分类层级',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `order_by` int(11) NULL DEFAULT NULL COMMENT '排序',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  PRIMARY KEY (`guidanceid`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '指导价表\r\n对下计量';

-- ----------------------------
-- Records of dx_ldssc_guidance_formwork
-- ----------------------------

-- ----------------------------
-- Table structure for dx_ldssc_guidance_formwork_project
-- ----------------------------
DROP TABLE IF EXISTS `dx_ldssc_guidance_formwork_project`;
CREATE TABLE `dx_ldssc_guidance_formwork_project`  (
  `guidance_numb` varchar(255) NOT NULL COMMENT '指导价编号',
  `sign` varchar(255) NULL DEFAULT NULL COMMENT '标识',
  `guidance_name` varchar(255) NULL DEFAULT NULL COMMENT '指导价名称',
  `price` double(11, 2) NULL DEFAULT NULL COMMENT '单价',
  `unit_id` int(11) NULL DEFAULT NULL COMMENT '单位id',
  `unit_name` varchar(255) NULL DEFAULT NULL COMMENT '单位名称',
  `guidance_rate` double(11, 4) NULL DEFAULT NULL COMMENT '单位比率',
  `guidance_wark` varchar(1000) NULL DEFAULT NULL COMMENT '工作内容',
  `guidance_cost` varchar(1000) NULL DEFAULT NULL COMMENT '费用组成',
  `guidance_role` varchar(255) NULL DEFAULT NULL COMMENT '计量规则',
  `type` int(11) NULL DEFAULT NULL COMMENT '分类1 路基；2路面',
  `level` varchar(10) NULL DEFAULT NULL COMMENT '分类层级',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `order_by` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`guidance_numb`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '指导价表\r\n对下计量';

-- ----------------------------
-- Records of dx_ldssc_guidance_formwork_project
-- ----------------------------

-- ----------------------------
-- Table structure for dx_ldssc_pwd
-- ----------------------------
DROP TABLE IF EXISTS `dx_ldssc_pwd`;
CREATE TABLE `dx_ldssc_pwd`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `partid` int(11) NULL DEFAULT NULL,
  `wcfid` int(11) NULL DEFAULT NULL COMMENT '工作内容id',
  `wcfid_name` varchar(255) NULL DEFAULT NULL COMMENT '工作内容名称',
  `wcfid_type` varchar(255) NULL DEFAULT NULL COMMENT '工作内容类别',
  `wcfid_unit` varchar(255) NULL DEFAULT NULL COMMENT '工作单位',
  `wcfid_ratio` double NULL DEFAULT NULL COMMENT '损耗系数',
  `wcfid_convert` double(11, 2) NULL DEFAULT NULL COMMENT '工作内容单位换算系数',
  `wcfid_finish` int(11) NULL DEFAULT NULL COMMENT '完成情况',
  `wcfid_amount` double(11, 2) NULL DEFAULT NULL COMMENT '工作内容数量',
  `dlfid` int(11) NULL DEFAULT NULL COMMENT '清单id',
  `dlfid_numb` varchar(255) NULL DEFAULT NULL COMMENT '清单编号',
  `dlfid_name` varchar(255) NULL DEFAULT NULL COMMENT '清单名称',
  `dlfid_unit` varchar(255) NULL DEFAULT NULL COMMENT '清单单位',
  `dlfid_amount` double(11, 2) NULL DEFAULT NULL COMMENT '清单数量',
  `dlfid_conversion_formula` varchar(255) NULL DEFAULT NULL COMMENT '清单换算公式',
  `dlfid_conversion_rule` varchar(255) NULL DEFAULT NULL COMMENT '清单换算规则',
  `dlfid_convert` double(11, 2) NULL DEFAULT NULL COMMENT '清单的单位换算',
  `guidanceid` int(11) NULL DEFAULT NULL COMMENT '对下计量id',
  `guidance_num` varchar(255) NULL DEFAULT NULL COMMENT '指导价编号',
  `guidance_name` varchar(255) NULL DEFAULT NULL COMMENT '对下计量名称',
  `guidance_unit` varchar(255) NULL DEFAULT NULL COMMENT '指导价单位',
  `guidance_convert` double(11, 4) NULL DEFAULT NULL COMMENT '指导价单位换算',
  `guidance_amount` double(11, 2) NULL DEFAULT NULL COMMENT '指导价数量',
  `guidandce_conversion_formula` varchar(255) NULL DEFAULT NULL COMMENT '指导价换算公式',
  `guidandce_conversion_rule` varchar(255) NULL DEFAULT NULL COMMENT '指导价换算规则',
  `guidance_rule` varchar(255) NULL DEFAULT NULL COMMENT '指导价生成规则',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '部位挂在内容挂在清单标准库';

-- ----------------------------
-- Records of dx_ldssc_pwd
-- ----------------------------

-- ----------------------------
-- Table structure for dx_ldssc_pwd_linshi
-- ----------------------------
DROP TABLE IF EXISTS `dx_ldssc_pwd_linshi`;
CREATE TABLE `dx_ldssc_pwd_linshi`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `pwdid` int(11) NULL DEFAULT NULL COMMENT '关系表id',
  `partid` bigint(11) NULL DEFAULT NULL,
  `wcfid` bigint(11) NULL DEFAULT NULL COMMENT '工作内容id',
  `wcfid_name` varchar(255) NULL DEFAULT NULL COMMENT '工作内容名称',
  `wcfid_unit` varchar(255) NULL DEFAULT NULL COMMENT '工作单位',
  `wcfid_ratio` double NULL DEFAULT NULL COMMENT '损耗系数',
  `wcfid_finish` int(11) NULL DEFAULT NULL COMMENT '完成情况',
  `wcfid_convert` double(11, 4) NULL DEFAULT NULL COMMENT '工作内容单位换算',
  `wcfid_amount` double(11, 4) NULL DEFAULT NULL COMMENT '工作内容数量',
  `dlfid` int(11) NULL DEFAULT NULL COMMENT '清单id',
  `dlfid_numb` varchar(255) NULL DEFAULT NULL COMMENT '清单编号',
  `dlfid_name` varchar(255) NULL DEFAULT NULL COMMENT '清单名称',
  `dlfid_unit` varchar(255) NULL DEFAULT NULL COMMENT '清单单位',
  `dlfid_convert` double(11, 4) NULL DEFAULT NULL COMMENT '清单的单位换算',
  `dlfid_amount` double(11, 4) NULL DEFAULT NULL COMMENT '清单数量',
  `guidanceid` int(11) NULL DEFAULT NULL COMMENT '对下计量id',
  `guidance_num` varchar(255) NULL DEFAULT NULL COMMENT '指导价编号',
  `guidance_name` varchar(255) NULL DEFAULT NULL COMMENT '对下计量名称',
  `guidance_unit` varchar(255) NULL DEFAULT NULL COMMENT '指导价单位',
  `guidance_rule` varchar(255) NULL DEFAULT NULL COMMENT '指导价生成规则',
  `guidance_amount` double(11, 4) NULL DEFAULT NULL COMMENT '指导价数量',
  `guidance_convert` double(11, 4) NULL DEFAULT NULL COMMENT '指导价单位换算',
  `contrast_status` int(11) NULL DEFAULT 1 COMMENT '对比的状态： 1没有对比；2已经对比；',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '部位挂在内容挂在清单标准\r\n这里作为一个临时存放清单关系\r\n当前端点击相同、新增之后会在DX_LDSSC_PWD_THREE这个表中创建新的关系\r\n';

-- ----------------------------
-- Records of dx_ldssc_pwd_linshi
-- ----------------------------

-- ----------------------------
-- Table structure for dx_ldssc_pwd_project
-- ----------------------------
DROP TABLE IF EXISTS `dx_ldssc_pwd_project`;
CREATE TABLE `dx_ldssc_pwd_project`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `pwdid` int(11) NULL DEFAULT NULL COMMENT '关系表id',
  `partid` bigint(11) NULL DEFAULT NULL,
  `wcfid` bigint(11) NULL DEFAULT NULL COMMENT '工作内容id',
  `wcfid_name` varchar(255) NULL DEFAULT NULL COMMENT '工作内容名称',
  `wcfid_unit` varchar(255) NULL DEFAULT NULL COMMENT '工作单位',
  `wcfid_ratio` double NULL DEFAULT NULL COMMENT '损耗系数',
  `wcfid_finish` int(11) NULL DEFAULT NULL COMMENT '完成情况',
  `wcfid_convert` double(11, 4) NULL DEFAULT NULL COMMENT '工作内容单位换算',
  `wcfid_amount` double(11, 4) NULL DEFAULT NULL COMMENT '工作内容数量',
  `dlfid` bigint(11) NULL DEFAULT NULL COMMENT '清单id',
  `dlfid_numb` varchar(255) NULL DEFAULT NULL COMMENT '清单编号',
  `dlfid_name` varchar(255) NULL DEFAULT NULL COMMENT '清单名称',
  `dlfid_unit` varchar(255) NULL DEFAULT NULL COMMENT '清单单位',
  `dlfid_convert` double(11, 4) NULL DEFAULT NULL COMMENT '清单的单位换算',
  `dlfid_amount` double(11, 4) NULL DEFAULT NULL COMMENT '清单数量',
  `dlfid_conversion_formula` varchar(255) NULL DEFAULT NULL COMMENT '清单换算公式',
  `dlfid_conversion_rule` varchar(255) NULL DEFAULT NULL COMMENT '清单换算规则',
  `guidanceid` int(11) NULL DEFAULT NULL COMMENT '对下计量id',
  `guidance_num` varchar(255) NULL DEFAULT NULL COMMENT '指导价编号',
  `guidance_name` varchar(255) NULL DEFAULT NULL COMMENT '对下计量名称',
  `guidance_unit` varchar(255) NULL DEFAULT NULL COMMENT '指导价单位',
  `guidance_rule` varchar(255) NULL DEFAULT NULL COMMENT '指导价生成规则',
  `guidance_amount` double(11, 4) NULL DEFAULT NULL COMMENT '指导价数量',
  `guidandce_conversion_formula` varchar(255) NULL DEFAULT NULL COMMENT '指导价换算公式',
  `guidandce_conversion_rule` varchar(255) NULL DEFAULT NULL COMMENT '指导价换算规则',
  `guidance_convert` double(11, 4) NULL DEFAULT NULL,
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '部位挂在内容挂在清单标准库';

-- ----------------------------
-- Records of dx_ldssc_pwd_project
-- ----------------------------

-- ----------------------------
-- Table structure for dx_ldssc_pwd_project_numb
-- ----------------------------
DROP TABLE IF EXISTS `dx_ldssc_pwd_project_numb`;
CREATE TABLE `dx_ldssc_pwd_project_numb`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `partid` bigint(11) NULL DEFAULT NULL COMMENT '部位id',
  `wcfid` bigint(11) NULL DEFAULT NULL COMMENT '工作内容id',
  `wcfid_name` varchar(255) NULL DEFAULT NULL COMMENT '工作内容名称',
  `wcfid_uintname` varchar(255) NULL DEFAULT NULL COMMENT '工作内容单位',
  `wcfid_shijinumb` double NULL DEFAULT NULL COMMENT '工作内容数量',
  `dlfid` bigint(20) NULL DEFAULT NULL COMMENT '清单id',
  `dlfid_numb` varchar(255) NULL DEFAULT NULL COMMENT '清单编号',
  `dlfid_shijinumb` double NULL DEFAULT NULL COMMENT '清单数量',
  `guidanceid` int(11) NULL DEFAULT NULL COMMENT '指导价id',
  `guidance_numb` varchar(255) NULL DEFAULT NULL COMMENT '指导价编号',
  `guidance_shijinumb` double NULL DEFAULT NULL COMMENT '指导价数量',
  `finish_time` datetime(0) NULL DEFAULT NULL COMMENT '完成时间',
  `uid` varchar(255) NULL DEFAULT NULL COMMENT '用户id',
  `uid_name` varchar(255) NULL DEFAULT NULL COMMENT '用户姓名',
  `uid_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `is_plancount` int(11) NOT NULL DEFAULT 0 COMMENT '0 未计量，1是计量',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_ldssc_pwd_project_numb
-- ----------------------------

-- ----------------------------
-- Table structure for dx_ldssc_pwd_three
-- ----------------------------
DROP TABLE IF EXISTS `dx_ldssc_pwd_three`;
CREATE TABLE `dx_ldssc_pwd_three`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pwdid` int(11) NULL DEFAULT NULL COMMENT '关系表id',
  `partid` bigint(11) NULL DEFAULT NULL,
  `wcfid` bigint(11) NULL DEFAULT NULL COMMENT '工作内容id',
  `wcfid_name` varchar(255) NULL DEFAULT NULL COMMENT '工作内容名称',
  `wcfid_unit` varchar(255) NULL DEFAULT NULL COMMENT '工作单位',
  `wcfid_ratio` double NULL DEFAULT NULL COMMENT '损耗系数',
  `wcfid_finish` int(11) NULL DEFAULT NULL COMMENT '完成情况',
  `wcfid_convert` double(11, 4) NULL DEFAULT NULL COMMENT '工作内容单位换算',
  `wcfid_amount` double(11, 4) NULL DEFAULT NULL COMMENT '工作内容数量',
  `dlfid` bigint(20) NULL DEFAULT NULL COMMENT '清单id',
  `dlfid_numb` varchar(255) NULL DEFAULT NULL COMMENT '清单编号',
  `dlfid_name` varchar(255) NULL DEFAULT NULL COMMENT '清单名称',
  `dlfid_unit` varchar(255) NULL DEFAULT NULL COMMENT '清单单位',
  `dlfid_convert` double(11, 4) NULL DEFAULT NULL COMMENT '清单的单位换算',
  `dlfid_amount` double(11, 4) NULL DEFAULT NULL COMMENT '清单数量',
  `dlfid_conversion_formula` varchar(255) NULL DEFAULT NULL COMMENT '清单换算公式',
  `dlfid_conversion_rule` varchar(255) NULL DEFAULT NULL COMMENT '清单换算规则',
  `guidanceid` int(11) NULL DEFAULT NULL COMMENT '对下计量id',
  `guidance_num` varchar(255) NULL DEFAULT NULL COMMENT '指导价编号',
  `guidance_name` varchar(255) NULL DEFAULT NULL COMMENT '对下计量名称',
  `guidance_unit` varchar(255) NULL DEFAULT NULL COMMENT '指导价单位',
  `guidance_rule` varchar(255) NULL DEFAULT NULL COMMENT '指导价生成规则',
  `guidance_amount` double(11, 4) NULL DEFAULT NULL COMMENT '指导价数量',
  `guidance_convert` double(11, 4) NULL DEFAULT NULL COMMENT '指导价单位换算',
  `contrast_status` int(11) NULL DEFAULT 1 COMMENT '对比的状态： 1没有对比；2已经对比；',
  `guidandce_conversion_formula` varchar(255) NULL DEFAULT NULL COMMENT '指导价换算公式',
  `guidandce_conversion_rule` varchar(255) NULL DEFAULT NULL COMMENT '指导价换算规则',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '部位挂在内容挂在清单标准库\r\n通过对比确定的关系会存放到表中。\r\n形成项目部自己的三级关系';

-- ----------------------------
-- Records of dx_ldssc_pwd_three
-- ----------------------------

-- ----------------------------
-- Table structure for dx_ldssc_qingdan_contrast
-- ----------------------------
DROP TABLE IF EXISTS `dx_ldssc_qingdan_contrast`;
CREATE TABLE `dx_ldssc_qingdan_contrast`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ht_id` bigint(20) NULL DEFAULT NULL COMMENT '合同清单id',
  `ht_name` varchar(255) NULL DEFAULT NULL COMMENT '合同清单名称',
  `ht_numb` varchar(255) NULL DEFAULT NULL COMMENT '合同清单编号',
  `ht_unit_name` varchar(255) NULL DEFAULT NULL COMMENT '合同清单单位',
  `ht_level_numb` varchar(255) NULL DEFAULT NULL,
  `bz_id` int(11) NULL DEFAULT NULL COMMENT '标准清单的主键',
  `bz_name` varchar(255) NULL DEFAULT NULL COMMENT '标准清单名称',
  `bz_numb` varchar(255) NULL DEFAULT NULL COMMENT '标准清单编号',
  `bz_unit_name` varchar(255) NULL DEFAULT NULL COMMENT '标准清单名称',
  `partids` varchar(255) NULL DEFAULT NULL COMMENT '部位id数组',
  `parids_update` varchar(255) NULL DEFAULT NULL COMMENT '部位id要被修改的一列',
  `falg` tinyint(1) NULL DEFAULT NULL COMMENT '是否存在多给部位',
  `con_status` int(11) NULL DEFAULT NULL COMMENT '比较状态：1相同；2相似；3不同',
  `show_status` int(11) NULL DEFAULT 1 COMMENT '展示状态1正常，2灰色',
  `level` int(11) NULL DEFAULT NULL,
  `order_by` bigint(20) NULL DEFAULT NULL COMMENT '排序',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_ldssc_qingdan_contrast
-- ----------------------------

-- ----------------------------
-- Table structure for dx_ldssc_wgd
-- ----------------------------
DROP TABLE IF EXISTS `dx_ldssc_wgd`;
CREATE TABLE `dx_ldssc_wgd`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `partid` int(11) NULL DEFAULT NULL COMMENT '部位id',
  `wcfid` int(11) NULL DEFAULT NULL COMMENT '工作内容id',
  `wcfid_name` varchar(255) NULL DEFAULT NULL COMMENT '工作内容名称',
  `guidanceid` int(11) NULL DEFAULT NULL COMMENT '对下计量id',
  `guidance_num` varchar(255) NULL DEFAULT NULL COMMENT '指导价编号',
  `guidance_name` varchar(255) NULL DEFAULT NULL COMMENT '对下计量名称',
  `guidance_unit` varchar(255) NULL DEFAULT NULL COMMENT '指导价单位',
  `wgd_sign` varchar(255) NULL DEFAULT NULL COMMENT '标识',
  `dlfid` int(11) NULL DEFAULT NULL COMMENT '清单id',
  `dlfid_numb` varchar(255) NULL DEFAULT NULL COMMENT '清单编号',
  `dlfid_name` varchar(255) NULL DEFAULT NULL COMMENT '清单名称',
  `dlfid_unit` varchar(255) NULL DEFAULT NULL COMMENT '清单单位',
  `wgd_keyword` varchar(255) NULL DEFAULT NULL COMMENT '关键词',
  `guid_keyword` varchar(255) NULL DEFAULT NULL COMMENT '根据关键词去对比清单（每条工作内容）',
  `wgd_conversion_formula` varchar(255) NULL DEFAULT NULL COMMENT '指导价换算公式',
  `wgd_conversion_rule` varchar(255) NULL DEFAULT NULL COMMENT '挂载规则 0两个都计量 1是选择一个计量',
  `wgd_rule` varchar(255) NULL DEFAULT NULL COMMENT '生成规则',
  `wgd_range` varchar(2000) NULL DEFAULT NULL COMMENT '范围',
  `wgd_title_one` varchar(255) NULL DEFAULT NULL COMMENT '标题 一',
  `wgd_value_one` varchar(255) NULL DEFAULT NULL COMMENT '值 一',
  `wgd_title_two` varchar(255) NULL DEFAULT NULL COMMENT '标题 二',
  `wgd_value_two` varchar(255) NULL DEFAULT NULL COMMENT '值 二',
  `wgd_title_three` varchar(255) NULL DEFAULT NULL COMMENT '标题 三',
  `wgd_value_three` varchar(255) NULL DEFAULT NULL COMMENT '值 三',
  `order_by` int(11) NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '部位挂在内容挂在清单标准库';

-- ----------------------------
-- Records of dx_ldssc_wgd
-- ----------------------------

-- ----------------------------
-- Table structure for dx_ldssc_wgd_project
-- ----------------------------
DROP TABLE IF EXISTS `dx_ldssc_wgd_project`;
CREATE TABLE `dx_ldssc_wgd_project`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `wgd_id` int(11) NULL DEFAULT NULL COMMENT '挂在关系id',
  `partid` bigint(11) NULL DEFAULT NULL COMMENT '部位id',
  `wcfid` int(11) NULL DEFAULT NULL COMMENT '工作内容id',
  `wcfid_name` varchar(255) NULL DEFAULT NULL COMMENT '工作内容名称',
  `guidanceid` int(11) NULL DEFAULT NULL COMMENT '对下计量id',
  `guidance_num` varchar(255) NULL DEFAULT NULL COMMENT '指导价编号',
  `guidance_name` varchar(255) NULL DEFAULT NULL COMMENT '对下计量名称',
  `guidance_unit` varchar(255) NULL DEFAULT NULL COMMENT '指导价单位',
  `wgd_sign` varchar(255) NULL DEFAULT NULL COMMENT '标识',
  `wgd_devise_numb` double NULL DEFAULT NULL COMMENT '工作内容设计数量',
  `wgd_reality_numb` double NULL DEFAULT NULL COMMENT '工作内容实际完成数量',
  `dlfid` bigint(11) NULL DEFAULT NULL COMMENT '清单id',
  `dlfid_numb` varchar(255) NULL DEFAULT NULL COMMENT '清单编号',
  `dlfid_name` varchar(255) NULL DEFAULT NULL COMMENT '清单名称',
  `dlfid_unit` varchar(255) NULL DEFAULT NULL COMMENT '清单单位',
  `dlfid_devise_numb` double NULL DEFAULT NULL COMMENT '清单设计数量',
  `dlfid_reality_numb` double NULL DEFAULT NULL COMMENT '清单实际完成数量',
  `wgd_keyword` varchar(255) NULL DEFAULT NULL COMMENT '关键词',
  `wgd_conversion_formula` varchar(255) NULL DEFAULT NULL COMMENT '指导价换算公式',
  `wgd_conversion_rule` varchar(255) NULL DEFAULT NULL COMMENT '指导价换算规则',
  `wgd_rule` varchar(255) NULL DEFAULT NULL COMMENT '生成规则',
  `wgd_title_one` varchar(255) NULL DEFAULT NULL COMMENT '标题 一',
  `wgd_value_one` varchar(255) NULL DEFAULT NULL COMMENT '值 一',
  `wgd_title_two` varchar(255) NULL DEFAULT NULL COMMENT '标题 二',
  `wgd_value_two` varchar(255) NULL DEFAULT NULL COMMENT '值 二',
  `wgd_title_three` varchar(255) NULL DEFAULT NULL COMMENT '标题 三',
  `wgd_value_three` varchar(255) NULL DEFAULT NULL COMMENT '值 三',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `order_by` bigint(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '部位挂在内容挂在清单标准库';

-- ----------------------------
-- Records of dx_ldssc_wgd_project
-- ----------------------------

-- ----------------------------
-- Table structure for dx_ldssc_wgd_project_numb
-- ----------------------------
DROP TABLE IF EXISTS `dx_ldssc_wgd_project_numb`;
CREATE TABLE `dx_ldssc_wgd_project_numb`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `wgd_id` int(11) NULL DEFAULT NULL COMMENT '挂在关系id',
  `partid` bigint(11) NULL DEFAULT NULL COMMENT '部位id',
  `wcfid` int(11) NULL DEFAULT NULL COMMENT '工作内容id',
  `wcfid_name` varchar(255) NULL DEFAULT NULL COMMENT '工作内容名称',
  `guidanceid` int(11) NULL DEFAULT NULL COMMENT '对下计量id',
  `guidance_num` varchar(255) NULL DEFAULT NULL COMMENT '指导价编号',
  `guidance_name` varchar(255) NULL DEFAULT NULL COMMENT '对下计量名称',
  `guidance_unit` varchar(255) NULL DEFAULT NULL COMMENT '指导价单位',
  `wgd_sign` varchar(255) NULL DEFAULT NULL COMMENT '标识',
  `wgd_devise_numb` double NULL DEFAULT NULL COMMENT '工作内容设计数量',
  `wgd_reality_numb` double NULL DEFAULT NULL COMMENT '工作内容实际完成数量',
  `wgd_plancount_numb` double NULL DEFAULT 0 COMMENT '工作内容计量数量',
  `dlfid` bigint(11) NULL DEFAULT NULL COMMENT '清单id',
  `dlfid_numb` varchar(255) NULL DEFAULT NULL COMMENT '清单编号',
  `dlfid_name` varchar(255) NULL DEFAULT NULL COMMENT '清单名称',
  `dlfid_unit` varchar(255) NULL DEFAULT NULL COMMENT '清单单位',
  `dlfid_devise_numb` double NULL DEFAULT NULL COMMENT '清单设计数量',
  `dlfid_reality_numb` double NULL DEFAULT NULL COMMENT '清单实际完成数量',
  `dlfid_plancount_numb` double NULL DEFAULT 0 COMMENT '清单计量数量',
  `wgd_rule` varchar(255) NULL DEFAULT NULL COMMENT '生成规则',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `finis_time` datetime(0) NULL DEFAULT NULL COMMENT '完成时间',
  `uid` varchar(255) NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) NULL DEFAULT NULL COMMENT '用户名称',
  `user_time` datetime(0) NULL DEFAULT NULL COMMENT '用户录入时间',
  `is_plancount` int(11) NOT NULL DEFAULT 0 COMMENT '0 未计价 1 已计价  ',
  `teams` int(11) NULL DEFAULT NULL COMMENT '哪个劳务队完成',
  `team_name` varchar(255) NULL DEFAULT NULL COMMENT '劳务对名称',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `order_by` bigint(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '部位挂在内容挂在清单标准库';

-- ----------------------------
-- Records of dx_ldssc_wgd_project_numb
-- ----------------------------

-- ----------------------------
-- Table structure for dx_ldssc_wgd_three
-- ----------------------------
DROP TABLE IF EXISTS `dx_ldssc_wgd_three`;
CREATE TABLE `dx_ldssc_wgd_three`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `wgd_id` int(11) NULL DEFAULT NULL COMMENT '挂在关系id',
  `partid` bigint(11) NULL DEFAULT NULL COMMENT '部位id',
  `wcfid` int(11) NULL DEFAULT NULL COMMENT '工作内容id',
  `wcfid_name` varchar(255) NULL DEFAULT NULL COMMENT '工作内容名称',
  `guidanceid` int(11) NULL DEFAULT NULL COMMENT '对下计量id',
  `guidance_num` varchar(255) NULL DEFAULT NULL COMMENT '指导价编号',
  `guidance_name` varchar(255) NULL DEFAULT NULL COMMENT '对下计量名称',
  `guidance_unit` varchar(255) NULL DEFAULT NULL COMMENT '指导价单位',
  `wgd_sign` varchar(255) NULL DEFAULT NULL COMMENT '标识',
  `dlfid` bigint(11) NULL DEFAULT NULL COMMENT '清单id',
  `dlfid_numb` varchar(255) NULL DEFAULT NULL COMMENT '清单编号',
  `dlfid_name` varchar(255) NULL DEFAULT NULL COMMENT '清单名称',
  `dlfid_unit` varchar(255) NULL DEFAULT NULL COMMENT '清单单位',
  `wgd_keyword` varchar(255) NULL DEFAULT NULL COMMENT '关键词',
  `wgd_conversion_formula` varchar(255) NULL DEFAULT NULL COMMENT '指导价换算公式',
  `wgd_conversion_rule` varchar(255) NULL DEFAULT NULL COMMENT '指导价换算规则',
  `wgd_rule` varchar(255) NULL DEFAULT NULL COMMENT '生成规则',
  `wgd_range` varchar(2000) NULL DEFAULT NULL,
  `wgd_title_one` varchar(255) NULL DEFAULT NULL COMMENT '标题 一',
  `wgd_value_one` varchar(255) NULL DEFAULT NULL COMMENT '值 一',
  `wgd_title_two` varchar(255) NULL DEFAULT NULL COMMENT '标题 二',
  `wgd_value_two` varchar(255) NULL DEFAULT NULL COMMENT '值 二',
  `wgd_title_three` varchar(255) NULL DEFAULT NULL COMMENT '标题 三',
  `wgd_value_three` varchar(255) NULL DEFAULT NULL COMMENT '值 三',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `order_by` bigint(11) NULL DEFAULT NULL COMMENT '排序',
  `show_status` int(11) NULL DEFAULT NULL COMMENT '展示状态 1相同2相似3未找到',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '部位挂在内容挂在清单标准库';

-- ----------------------------
-- Records of dx_ldssc_wgd_three
-- ----------------------------

-- ----------------------------
-- Table structure for dx_ldssc_workcontent__formwork
-- ----------------------------
DROP TABLE IF EXISTS `dx_ldssc_workcontent__formwork`;
CREATE TABLE `dx_ldssc_workcontent__formwork`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `partid` int(20) NULL DEFAULT NULL COMMENT '部位id',
  `name` varchar(255) NULL DEFAULT NULL,
  `uint_id` int(11) NULL DEFAULT NULL,
  `unit_name` varchar(255) NULL DEFAULT NULL,
  `ratio` double(11, 2) NULL DEFAULT NULL COMMENT '损耗系数',
  `finish` int(11) NULL DEFAULT NULL COMMENT '完成情况',
  `numb_type` varchar(255) NULL DEFAULT NULL COMMENT '数量类别',
  `type` varchar(255) NULL DEFAULT NULL COMMENT '类别',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '工作内容表';

-- ----------------------------
-- Records of dx_ldssc_workcontent__formwork
-- ----------------------------

-- ----------------------------
-- Table structure for dx_ldssc_workcontent__project
-- ----------------------------
DROP TABLE IF EXISTS `dx_ldssc_workcontent__project`;
CREATE TABLE `dx_ldssc_workcontent__project`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `wcid` bigint(11) NULL DEFAULT NULL COMMENT '工作内容id',
  `partid` bigint(11) NULL DEFAULT NULL COMMENT '部位id',
  `name` varchar(255) NULL DEFAULT NULL,
  `uint_id` int(11) NULL DEFAULT NULL,
  `unit_name` varchar(255) NULL DEFAULT NULL,
  `ratio` double(11, 2) NULL DEFAULT NULL COMMENT '损耗系数',
  `finish` int(11) NULL DEFAULT NULL COMMENT '完成情况',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '工作内容表';

-- ----------------------------
-- Records of dx_ldssc_workcontent__project
-- ----------------------------

-- ----------------------------
-- Table structure for dx_leader_addscore_config
-- ----------------------------
DROP TABLE IF EXISTS `dx_leader_addscore_config`;
CREATE TABLE `dx_leader_addscore_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) NULL DEFAULT NULL COMMENT 'm名称',
  `score` double NULL DEFAULT NULL COMMENT '分',
  `type` int(255) NULL DEFAULT NULL COMMENT '分类',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备用字段',
  `flagScore` int(11) NULL DEFAULT NULL COMMENT '加分还是扣分 1减 0 加',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_leader_addscore_config
-- ----------------------------

-- ----------------------------
-- Table structure for dx_leader_evaluation
-- ----------------------------
DROP TABLE IF EXISTS `dx_leader_evaluation`;
CREATE TABLE `dx_leader_evaluation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `class_id` int(11) NULL DEFAULT NULL COMMENT '班组id',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '班组长名称',
  `class_idcard` varchar(255) NULL DEFAULT NULL COMMENT '班组长身份证号',
  `team_id` int(11) NULL DEFAULT NULL COMMENT '劳务队id',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `construction_type_json` text NULL COMMENT '施工种类json',
  `level_id` int(11) NULL DEFAULT NULL COMMENT 'abc排名id',
  `level_name` varchar(255) NULL DEFAULT NULL COMMENT 'ABC名称',
  `level_soce` double NULL DEFAULT NULL COMMENT 'abc分数赋值得分',
  `peoplesize_id` int(11) NULL DEFAULT NULL COMMENT '队伍规模人数id',
  `people_size` int(11) NULL DEFAULT NULL COMMENT '队伍人数',
  `people_score` double NULL DEFAULT NULL COMMENT '队伍规模得分',
  `construction_year` double NULL DEFAULT NULL COMMENT '施工年限',
  `construction_score` double NULL DEFAULT NULL COMMENT '施工年限得分',
  `year_quarter` varchar(255) NULL DEFAULT NULL COMMENT '季度',
  `partid_json` text NULL COMMENT '部位多选json',
  `partid_score` double NULL DEFAULT NULL COMMENT '部位得分(施工作业难度)',
  `flag` int(11) NULL DEFAULT NULL COMMENT '状态 0 无效  1 有效',
  `construction_content` varchar(255) NULL DEFAULT NULL COMMENT '具体施工内容',
  `file_json` text NULL COMMENT '文件id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_id` varchar(255) NULL DEFAULT NULL COMMENT '创建id',
  `score` double NULL DEFAULT NULL COMMENT '项目得分',
  `credit_score` double NULL DEFAULT NULL COMMENT '信用评价扣分',
  `is_final` int(255) NULL DEFAULT 0 COMMENT '0可以修改 1不可以',
  `phone` varchar(255) NULL DEFAULT NULL COMMENT '班组长电话号',
  `contractor` varchar(255) NULL DEFAULT NULL COMMENT '劳务队负责人',
  `pay_type` int(255) NULL DEFAULT NULL COMMENT '薪酬方式',
  `goal_score` double NULL DEFAULT NULL COMMENT '信用评价得分',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_leader_evaluation
-- ----------------------------

-- ----------------------------
-- Table structure for dx_leader_evaluationunit
-- ----------------------------
DROP TABLE IF EXISTS `dx_leader_evaluationunit`;
CREATE TABLE `dx_leader_evaluationunit`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `class_id` int(11) NULL DEFAULT NULL COMMENT '班组id',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '班组长名称',
  `class_idcard` varchar(255) NULL DEFAULT NULL COMMENT '班组长身份证号',
  `team_id` int(11) NULL DEFAULT NULL COMMENT '劳务队id',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `construction_type_json` text NULL COMMENT '施工种类json',
  `level_id` int(11) NULL DEFAULT NULL COMMENT 'abc排名id',
  `level_name` varchar(255) NULL DEFAULT NULL COMMENT 'ABC名称',
  `level_soce` double NULL DEFAULT NULL COMMENT 'abc分数赋值得分',
  `peoplesize_id` int(11) NULL DEFAULT NULL COMMENT '队伍规模人数id',
  `people_size` int(11) NULL DEFAULT NULL COMMENT '队伍人数',
  `people_score` double NULL DEFAULT NULL COMMENT '队伍规模得分',
  `people_level` varchar(255) NULL DEFAULT NULL COMMENT '队伍规模级别',
  `construction_year` double NULL DEFAULT NULL COMMENT '施工年限',
  `construction_score` double NULL DEFAULT NULL COMMENT '施工年限得分',
  `year_quarter` varchar(255) NULL DEFAULT NULL COMMENT '季度',
  `partid_json` text NULL COMMENT '部位多选json',
  `partid_score` double NULL DEFAULT NULL COMMENT '部位得分(施工作业难度)',
  `flag` int(11) NULL DEFAULT NULL COMMENT '状态 0 无效  1 有效',
  `construction_content` varchar(255) NULL DEFAULT NULL COMMENT '具体施工内容',
  `file_json` text NULL COMMENT '文件id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_id` varchar(255) NULL DEFAULT NULL COMMENT '创建id',
  `credit_score` double NULL DEFAULT NULL COMMENT '信用评价扣分',
  `is_final` int(255) NULL DEFAULT 0 COMMENT '0可以修改 1不可以',
  `phone` varchar(255) NULL DEFAULT NULL COMMENT '班组长电话号',
  `contractor` varchar(255) NULL DEFAULT NULL COMMENT '劳务队负责人',
  `pay_type` int(255) NULL DEFAULT NULL COMMENT '薪酬方式',
  `goal_score` double NULL DEFAULT NULL COMMENT '信用评价得分',
  `project_json` text NULL COMMENT '参与项目部json',
  `special_id` int(11) NULL DEFAULT NULL COMMENT '特殊项目id',
  `special_score` double NULL DEFAULT NULL COMMENT '特殊项目加分',
  `unit_score` double NULL DEFAULT NULL COMMENT '公司层面加减分',
  `unit_json` text NULL COMMENT '加减分json',
  `score` double NULL DEFAULT NULL COMMENT '项目得分',
  `final_score` double NULL DEFAULT NULL COMMENT '最终得分',
  `size` int(11) NULL DEFAULT NULL COMMENT '参评次数',
  `history_score` double NULL DEFAULT NULL COMMENT '历史得分',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_leader_evaluationunit
-- ----------------------------

-- ----------------------------
-- Table structure for dx_leader_partid
-- ----------------------------
DROP TABLE IF EXISTS `dx_leader_partid`;
CREATE TABLE `dx_leader_partid`  (
  `partid` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '部位id',
  `part_name` varchar(30) NOT NULL COMMENT '部位名称',
  `pid` varchar(25) NULL DEFAULT NULL COMMENT 'parent',
  `lqs` smallint(6) NULL DEFAULT NULL COMMENT '分类标识:1(路),2(桥),3(隧)',
  `sl` smallint(6) NULL DEFAULT NULL COMMENT '类别:1(水),2(陆)',
  `score` double(6, 0) NULL DEFAULT NULL COMMENT '分数',
  `array_sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`partid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_leader_partid
-- ----------------------------

-- ----------------------------
-- Table structure for dx_leader_peoplesize
-- ----------------------------
DROP TABLE IF EXISTS `dx_leader_peoplesize`;
CREATE TABLE `dx_leader_peoplesize`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `level` varchar(255) NULL DEFAULT NULL COMMENT '级别',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `size` int(11) NULL DEFAULT NULL COMMENT '人数',
  `score` double NULL DEFAULT NULL COMMENT '得分',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_leader_peoplesize
-- ----------------------------

-- ----------------------------
-- Table structure for dx_leader_team_credit_rating
-- ----------------------------
DROP TABLE IF EXISTS `dx_leader_team_credit_rating`;
CREATE TABLE `dx_leader_team_credit_rating`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `credit_name` varchar(255) NULL DEFAULT NULL COMMENT 'a1=因为施工原因发生重大安全事故',
  `score` double NULL DEFAULT NULL COMMENT '所有扣的分数',
  `class_id` int(11) NULL DEFAULT NULL COMMENT '班组id',
  `year_quarter` varchar(255) NULL DEFAULT NULL COMMENT '季度数',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `status` varchar(255) NULL DEFAULT NULL COMMENT '分类标识 CATEGORY_G',
  `type` int(11) NULL DEFAULT NULL COMMENT '1 班组 2 带班人',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_leader_team_credit_rating
-- ----------------------------

-- ----------------------------
-- Table structure for dx_leader_team_levelsize
-- ----------------------------
DROP TABLE IF EXISTS `dx_leader_team_levelsize`;
CREATE TABLE `dx_leader_team_levelsize`  (
  `id` smallint(6) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `A_plus` smallint(6) NULL DEFAULT NULL COMMENT 'A+',
  `size` smallint(6) NOT NULL COMMENT '次数',
  `A` smallint(6) NULL DEFAULT NULL COMMENT 'B+',
  `B_plus` smallint(6) NULL DEFAULT NULL,
  `B` smallint(6) NULL DEFAULT NULL,
  `C` smallint(6) NULL DEFAULT NULL,
  `D_plus` smallint(6) NULL DEFAULT NULL,
  `D` smallint(6) NULL DEFAULT NULL,
  `E_plus` smallint(6) NULL DEFAULT NULL,
  `E` smallint(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_leader_team_levelsize
-- ----------------------------

-- ----------------------------
-- Table structure for dx_leader_teamcategory
-- ----------------------------
DROP TABLE IF EXISTS `dx_leader_teamcategory`;
CREATE TABLE `dx_leader_teamcategory`  (
  `cid` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cname` varchar(20) NULL DEFAULT NULL COMMENT '名称',
  `cscore` double NULL DEFAULT NULL COMMENT '分项',
  PRIMARY KEY (`cid`),
  INDEX `index_cid`(`cid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_leader_teamcategory
-- ----------------------------

-- ----------------------------
-- Table structure for dx_materials_contract_audit
-- ----------------------------
DROP TABLE IF EXISTS `dx_materials_contract_audit`;
CREATE TABLE `dx_materials_contract_audit`  (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `project_id` int(6) NOT NULL COMMENT '项目id',
  `survey_id` int(6) NOT NULL COMMENT '供方id',
  `contract_no` varchar(30) NULL DEFAULT NULL COMMENT '合同编号',
  `company_contract` tinyint(1) NULL DEFAULT NULL COMMENT '是否公司格式合同(0:是,1:否)',
  `load_distance` float(10, 2) NULL DEFAULT NULL COMMENT '运输距离(KM)',
  `price` varchar(5000) NOT NULL COMMENT '单价(json:{\"datail\":[{\"name\":\"\",\"model\":\"\",\"price\":\"\",\"freight\":\"\"}],\"remark\":\"\")',
  `honest_level` varchar(10) NULL DEFAULT NULL COMMENT '诚信状况',
  `datum` varchar(200) NOT NULL COMMENT '招标方式:1:询比价、2:竞争性谈判、3:邀请招标、4:公开招标资料',
  `datum_file` int(11) NOT NULL COMMENT '招标文件',
  `materials_principal` varchar(255) NULL DEFAULT NULL COMMENT '物资部负责人',
  `materials_principal_name` varchar(255) NULL DEFAULT NULL COMMENT '物资部负责人',
  `materials_comment_status` int(11) NULL DEFAULT NULL COMMENT '物资部意见状态(0:同意,1:不同意)',
  `materials_comment` varchar(150) NULL DEFAULT NULL COMMENT '物资部意见',
  `materials_comment_date` date NULL DEFAULT NULL COMMENT '物资部意见填写日期',
  `materials_sign_file` int(11) NULL DEFAULT NULL COMMENT '物资部签名文件',
  `plan_comment_principal` varchar(255) NULL DEFAULT NULL COMMENT '计划部负责人',
  `plan_comment_principal_name` varchar(255) NULL DEFAULT NULL COMMENT '计划部负责人',
  `plan_comment_status` int(11) NULL DEFAULT NULL COMMENT '计划部意见状态(0:同意,1:不同意)',
  `plan_comment` varchar(150) NULL DEFAULT NULL COMMENT '计划部意见',
  `plan_comment_date` date NULL DEFAULT NULL COMMENT '计划部意见填写日期',
  `plan_sign_file` int(11) NULL DEFAULT NULL COMMENT '计划部签名文件',
  `accounting_principal` varchar(255) NULL DEFAULT NULL COMMENT '财务部负责人',
  `accounting_principal_name` varchar(255) NULL DEFAULT NULL COMMENT '财务部负责人',
  `accounting_comment_status` int(11) NULL DEFAULT NULL COMMENT '财务部意见状态(0:同意,1:不同意)',
  `accounting_comment` varchar(150) NULL DEFAULT NULL COMMENT '财务部意见',
  `accounting_comment_date` date NULL DEFAULT NULL COMMENT '财务部意见填写日期',
  `accounting_sign_file` int(11) NULL DEFAULT NULL COMMENT '财务部签名文件',
  `secretary_principal` varchar(255) NULL DEFAULT NULL COMMENT '项目书记',
  `secretary_principal_name` varchar(255) NULL DEFAULT NULL COMMENT '项目书记',
  `secretary_comment_status` int(11) NULL DEFAULT NULL COMMENT '项目书记意见状态(0:同意,1:不同意)',
  `secretary_comment` varchar(150) NULL DEFAULT NULL COMMENT '项目书记意见',
  `secretary_comment_date` date NULL DEFAULT NULL COMMENT '项目书记意见日期',
  `secretary_sign_file` int(11) NULL DEFAULT NULL COMMENT '书记签名文件',
  `manage_principal` varchar(255) NULL DEFAULT NULL COMMENT '项目经理负责人',
  `manage_principal_name` varchar(255) NULL DEFAULT NULL COMMENT '项目经理负责人',
  `manage_comment_status` int(11) NULL DEFAULT NULL COMMENT '项目经理意见状态(0:同意,1:不同意)',
  `manage_comment` varchar(150) NULL DEFAULT NULL COMMENT '项目经理意见',
  `manage_comment_date` date NULL DEFAULT NULL COMMENT '项目经理意见日期',
  `manage_sign_file` int(11) NULL DEFAULT NULL COMMENT '项目经理签名文件',
  `company_materials_principal` varchar(255) NULL DEFAULT NULL COMMENT '公司物资部负责人',
  `company_materials_principal_name` varchar(255) NULL DEFAULT NULL COMMENT '公司物资部负责人',
  `company_materials_comment_status` int(11) NULL DEFAULT NULL COMMENT '公司物资部意见状态(0:同意,1:不同意)',
  `company_materials_comment` varchar(150) NULL DEFAULT NULL COMMENT '公司物资部意见',
  `company_materials_comment_date` date NULL DEFAULT NULL COMMENT '公司物资部意见填写日期',
  `company_materials_sign_file` int(11) NULL DEFAULT NULL COMMENT '公司物资部签名文件',
  `law_principal` varchar(255) NULL DEFAULT NULL COMMENT '法律事务室负责人',
  `law_principal_name` varchar(255) NULL DEFAULT NULL COMMENT '法律事务室负责人',
  `law_comment_status` int(255) NULL DEFAULT NULL COMMENT '法律事务室意见状态(0:同意,1:不同意)',
  `law_comment` varchar(150) NULL DEFAULT NULL COMMENT '法律事务室意见',
  `law_comment_date` date NULL DEFAULT NULL COMMENT '法律事务室意见填写日期',
  `law_sign_file` int(11) NULL DEFAULT NULL COMMENT '法律部签名文件',
  `status` int(1) NOT NULL COMMENT '审核状态(0:新合同审批,1:物资部审批,2:计划部审批,3:财务部审批4:项目书记审批,5:项目经理审批,6:公司物资部审批7:法律部审核,8:审核通过,-1:驳回)',
  `is_reject` tinyint(1) NOT NULL COMMENT '是驳回',
  `create_time` datetime(0) NOT NULL COMMENT '录入时间',
  `create_user` varchar(255) NOT NULL COMMENT '创建人',
  `create_user_name` varchar(255) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `is_delete` tinyint(1) NOT NULL COMMENT '是否删除(0:正常,1:删除)',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '物资合同审批流程表';

-- ----------------------------
-- Records of dx_materials_contract_audit
-- ----------------------------

-- ----------------------------
-- Table structure for dx_materials_handler_history
-- ----------------------------
DROP TABLE IF EXISTS `dx_materials_handler_history`;
CREATE TABLE `dx_materials_handler_history`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` int(10) NOT NULL COMMENT '记录类型:(0:发料,1:退料,2:周转)',
  `data` int(11) NULL DEFAULT NULL COMMENT '操作数据ID',
  `handle_content` varchar(4000) NULL DEFAULT NULL COMMENT '操作内容',
  `handle_user` varchar(255) NOT NULL COMMENT '操作人',
  `handle_username` varchar(100) NOT NULL COMMENT '操作人',
  `handle_date` datetime(0) NOT NULL COMMENT '创建时间',
  `project_id` int(11) NOT NULL COMMENT '项目部id',
  `project_name` varchar(255) NOT NULL COMMENT '项目部名称',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_materials_handler_history
-- ----------------------------

-- ----------------------------
-- Table structure for dx_materials_material
-- ----------------------------
DROP TABLE IF EXISTS `dx_materials_material`;
CREATE TABLE `dx_materials_material`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pid` int(11) NOT NULL COMMENT '父级id',
  `custom_no` varchar(255) NULL DEFAULT NULL COMMENT '自定义编号',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `alias` varchar(255) NULL DEFAULT NULL COMMENT '别名',
  `unit` int(11) NULL DEFAULT NULL COMMENT '计量单位',
  `editable_name` tinyint(1) NULL DEFAULT NULL COMMENT '名称可编辑性',
  `editable_unit` tinyint(1) NOT NULL COMMENT '单位可编辑性',
  `editable_model` tinyint(1) NOT NULL COMMENT '规格可编辑性',
  `type_no` int(11) NOT NULL COMMENT '分类号',
  `is_consumable` tinyint(1) NOT NULL COMMENT '是易耗品',
  `is_admixture` tinyint(1) NOT NULL COMMENT '是否外加剂',
  `is_floor` tinyint(1) NOT NULL COMMENT '是否地材',
  `join_collect` tinyint(1) NOT NULL COMMENT '参与汇总',
  `join_price_compare` tinyint(1) NOT NULL COMMENT '参与价格比较',
  `weight_scale` int(2) NOT NULL DEFAULT 3 COMMENT '称重计量位数',
  `use_times` bigint(20) NOT NULL COMMENT '使用次数',
  `type_one` varchar(255) NULL DEFAULT NULL COMMENT '分类一',
  `type_two` varchar(255) NULL DEFAULT NULL COMMENT '分类二',
  `type_three` varchar(255) NULL DEFAULT NULL COMMENT '分类三',
  `type_four` varchar(255) NULL DEFAULT NULL COMMENT '分类四',
  `type_five` varchar(255) NULL DEFAULT NULL COMMENT '分类五',
  `type_six` varchar(255) NULL DEFAULT NULL COMMENT '分类六',
  `is_parent` tinyint(1) NOT NULL COMMENT '是否有子级',
  `sort_no` int(11) NOT NULL COMMENT '排序',
  `is_delete` tinyint(1) NOT NULL COMMENT '是否删除(0:正常,1:删除)',
  PRIMARY KEY (`id`),
  INDEX `index_pid`(`pid`),
  INDEX `index_unit`(`unit`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_materials_material
-- ----------------------------

-- ----------------------------
-- Table structure for dx_materials_model
-- ----------------------------
DROP TABLE IF EXISTS `dx_materials_model`;
CREATE TABLE `dx_materials_model`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `material_id` int(11) NOT NULL COMMENT '所属物资id',
  `model` varchar(255) NOT NULL COMMENT '规格型号',
  `road_no` varchar(255) NULL DEFAULT NULL COMMENT '公路编号',
  `road_convert` varchar(255) NULL DEFAULT NULL COMMENT '公路换算',
  `train_no` varchar(255) NULL DEFAULT NULL COMMENT '铁路编号',
  `train_convert` varchar(255) NULL DEFAULT NULL COMMENT '铁路换算',
  `unit_weight` decimal(15, 3) NULL DEFAULT NULL COMMENT '单位重',
  `m_weight` decimal(15, 3) NULL DEFAULT NULL COMMENT '米重',
  `sort_no` int(11) NOT NULL COMMENT '排序',
  `is_delete` tinyint(1) NOT NULL COMMENT '是否删除(0:正常,1:删除)',
  PRIMARY KEY (`id`),
  INDEX `index_material_id`(`material_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_materials_model
-- ----------------------------

-- ----------------------------
-- Table structure for dx_materials_product
-- ----------------------------
DROP TABLE IF EXISTS `dx_materials_product`;
CREATE TABLE `dx_materials_product`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `unit` varchar(20) NULL DEFAULT NULL,
  `tid` int(11) NOT NULL,
  `classify` varchar(1) NULL DEFAULT NULL,
  `is_delete` tinyint(1) NOT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_materials_product
-- ----------------------------

-- ----------------------------
-- Table structure for dx_materials_supplier
-- ----------------------------
DROP TABLE IF EXISTS `dx_materials_supplier`;
CREATE TABLE `dx_materials_supplier`  (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) NOT NULL COMMENT '供方名称',
  `type` int(1) NOT NULL COMMENT '供方性质(0:生产厂家,1:流通领域)',
  `address_prefix` int(11) NULL DEFAULT NULL COMMENT '所属省份',
  `address_suffix` varchar(50) NULL DEFAULT NULL COMMENT '详细地址',
  `business_license` varchar(50) NULL DEFAULT NULL COMMENT '营业执照编号',
  `business_register_money` varchar(255) NULL DEFAULT NULL COMMENT '注册资金',
  `business_license_file` int(11) NULL DEFAULT NULL COMMENT '营业执照文件路径',
  `business_expire_date` date NULL DEFAULT NULL COMMENT '营业执照过期时间',
  `check_survey` tinyint(1) NOT NULL COMMENT '是否添加供方调查',
  `approve` varchar(5000) NULL DEFAULT NULL COMMENT '通过何种认证及编号(1:质量管理体系认证,2:安全管理体系认证,3:环境管理体系认证,4:职业健康证书).json格式[{\"type\":\"\",\"no\":\"\",\"file\":\"\"}]',
  `product_licence` varchar(50) NULL DEFAULT NULL COMMENT '生产许可证',
  `product_licence_file` varchar(50) NULL DEFAULT NULL COMMENT '生产许可证文件路径',
  `product_expire_date` date NULL DEFAULT NULL COMMENT '生产许可证过期时间',
  `credit_level` varchar(1) NULL DEFAULT NULL COMMENT '资信等级(1:1A,2:2A,3:3A)',
  `credit_level_file` varchar(50) NULL DEFAULT NULL COMMENT '资信等级证书文件路径',
  `other_info` varchar(255) NULL DEFAULT NULL COMMENT '其他资料',
  `other_info_file` int(11) NULL DEFAULT NULL COMMENT '其他资料文件',
  `star_level` double(10, 1) NOT NULL DEFAULT 0.0 COMMENT '星级得分',
  `business_scope` varchar(255) NOT NULL COMMENT '经营范围',
  `is_black` tinyint(1) NOT NULL COMMENT '是否黑名单(0:正常,1:删除)',
  `is_delete` tinyint(1) NOT NULL COMMENT '是否删除(0:正常,1:删除)',
  `create_time` datetime(0) NOT NULL COMMENT '录入时间',
  `create_user` varchar(255) NOT NULL COMMENT '创建人',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '物资供方';

-- ----------------------------
-- Records of dx_materials_supplier
-- ----------------------------

-- ----------------------------
-- Table structure for dx_materials_supplier_project
-- ----------------------------
DROP TABLE IF EXISTS `dx_materials_supplier_project`;
CREATE TABLE `dx_materials_supplier_project`  (
  `supplier_id` int(11) NOT NULL COMMENT '供方id',
  `type` int(1) NOT NULL COMMENT '供方性质(0:生产厂家,1:流通领域)',
  `check_survey` tinyint(1) NOT NULL COMMENT '是否添加供方调查',
  `approve` varchar(5000) NULL DEFAULT NULL COMMENT '通过何种认证及编号(1:质量管理体系认证,2:安全管理体系认证,3:环境管理体系认证,4:职业健康证书).json格式[{\"type\":\"\",\"no\":\"\",\"file\":\"\"}]',
  `product_licence` varchar(50) NULL DEFAULT NULL COMMENT '生产许可证',
  `product_licence_file` varchar(50) NULL DEFAULT NULL COMMENT '生产许可证文件路径',
  `product_expire_date` date NULL DEFAULT NULL COMMENT '生产许可证过期时间',
  `credit_level` varchar(1) NULL DEFAULT NULL COMMENT '资信等级(1:1A,2:2A,3:3A)',
  `credit_level_file` varchar(50) NULL DEFAULT NULL COMMENT '资信等级证书文件路径',
  `other_info` varchar(255) NULL DEFAULT NULL COMMENT '其他资料',
  `other_info_file` int(11) NULL DEFAULT NULL COMMENT '其他资料文件',
  `business_scope` varchar(255) NOT NULL COMMENT '经营范围',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '状态(0:未评价,1:评价中,2:评价通过,-1:驳回)',
  `is_delete` tinyint(1) NOT NULL COMMENT '是否删除(0:正常,1:删除)',
  `project_id` int(11) NOT NULL COMMENT '项目id',
  `create_user` varchar(255) NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  INDEX `index_supplier_id`(`supplier_id`),
  INDEX `indedx_project_id`(`project_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_materials_supplier_project
-- ----------------------------

-- ----------------------------
-- Table structure for dx_materials_supplier_resurvey
-- ----------------------------
DROP TABLE IF EXISTS `dx_materials_supplier_resurvey`;
CREATE TABLE `dx_materials_supplier_resurvey`  (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `survey_id` int(6) NOT NULL COMMENT '供方调查id',
  `quality_score` int(2) NULL DEFAULT NULL COMMENT '供货质量状况(满分40)',
  `delivery_score` int(2) NULL DEFAULT NULL COMMENT '供货交付情况(满分10)',
  `after_sale_score` int(2) NULL DEFAULT NULL COMMENT '售后服务质量(满分20)',
  `price_score` int(2) NULL DEFAULT NULL COMMENT '供货价格水平(满分30)',
  `materials_principal` varchar(255) NULL DEFAULT NULL COMMENT '物资部负责人',
  `materials_principal_name` varchar(255) NULL DEFAULT NULL COMMENT '物资部负责人',
  `materials_comment_status` int(11) NULL DEFAULT NULL COMMENT '物资意见状态(0:同意,1:不同意)',
  `materials_comment` varchar(150) NULL DEFAULT NULL COMMENT '物资部意见',
  `materials_comment_date` date NULL DEFAULT NULL COMMENT '物资意见填写日期',
  `materials_sign_file` int(11) NULL DEFAULT NULL COMMENT '物资部签名文件',
  `manage_principal` varchar(255) NULL DEFAULT NULL COMMENT '项目负责人',
  `manage_principal_name` varchar(255) NULL DEFAULT NULL COMMENT '项目负责人',
  `manage_comment_status` int(11) NULL DEFAULT NULL COMMENT '项目经理意见状态(0:同意,1:不同意)',
  `manage_comment` varchar(150) NULL DEFAULT NULL COMMENT '项目经理意见',
  `manage_comment_date` date NULL DEFAULT NULL COMMENT '项目经理意见填写日期',
  `manage_sign_file` int(11) NULL DEFAULT NULL COMMENT '项目经理签名文件',
  `company_principal` varchar(255) NULL DEFAULT NULL COMMENT '公司负责人',
  `company_principal_name` varchar(255) NULL DEFAULT NULL COMMENT '公司负责人',
  `company_comment_status` int(11) NULL DEFAULT NULL COMMENT '公司意见(0:同意,1:不同意)',
  `company_comment` varchar(150) NULL DEFAULT NULL COMMENT '公司意见',
  `company_comment_date` date NULL DEFAULT NULL COMMENT '公司意见填写日期',
  `company_sign_file` int(11) NULL DEFAULT NULL COMMENT '公司签名文件',
  `status` int(1) NOT NULL COMMENT '审核状态(0:开始重新评价,1:物资部审批,2:项目经理审批,3:公司审核,4:审核通过,-1:驳回)',
  `is_reject` tinyint(4) NOT NULL COMMENT '是驳回',
  `project_id` int(6) NOT NULL COMMENT '所属项目id',
  `create_user` varchar(255) NOT NULL COMMENT '录入人',
  `create_user_name` varchar(255) NULL DEFAULT NULL COMMENT '录入人',
  `create_time` datetime(0) NOT NULL COMMENT '录入时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `is_delete` tinyint(1) NOT NULL COMMENT '是否删除(0:正常,1:删除)',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '供方重新调查表';

-- ----------------------------
-- Records of dx_materials_supplier_resurvey
-- ----------------------------

-- ----------------------------
-- Table structure for dx_materials_supplier_survey
-- ----------------------------
DROP TABLE IF EXISTS `dx_materials_supplier_survey`;
CREATE TABLE `dx_materials_supplier_survey`  (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `supplier_id` int(6) NOT NULL COMMENT '供方id',
  `product_ids` varchar(500) NOT NULL COMMENT '产品id,逗号分隔',
  `product_classify` varchar(5000) NULL DEFAULT NULL COMMENT '物资类别',
  `product_type` varchar(5000) NULL DEFAULT NULL COMMENT '物资类别',
  `product` varchar(5000) NULL DEFAULT NULL COMMENT '主要产品',
  `liaison` varchar(15) NOT NULL COMMENT '联系人',
  `mobile` varchar(11) NOT NULL COMMENT '电话',
  `env_safe` varchar(255) NULL DEFAULT NULL COMMENT '供方环境及安全内容',
  `product_ability` varchar(50) NOT NULL COMMENT '生产能力/销售能力(单位:万元)',
  `price_compare` int(1) NOT NULL COMMENT '同类价格比较(1:较高,2:居中,3:较低)',
  `support_ability` int(1) NOT NULL COMMENT '应急供应能力(1,较强,2:一般,3:较差)',
  `transport_ability` int(1) NOT NULL COMMENT '代办运输能力(1:有,2:无)',
  `after_sale` varchar(255) NULL DEFAULT NULL COMMENT '售后服务项目',
  `survey` varchar(100) NOT NULL COMMENT '调查人',
  `survey_date` date NOT NULL COMMENT '调查日期',
  `appraiser` varchar(2000) NOT NULL COMMENT '参与评价人(json格式[{\"id\":\"\",\"phone\":\"\",\"name\":\"\",\"sign\":\"\",\"signDate\":\"\",\"signFileId\":\"\"}])',
  `evaluate_principal` varchar(255) NULL DEFAULT NULL COMMENT '评论负责人',
  `evaluate_principal_name` varchar(255) NULL DEFAULT NULL COMMENT '评论负责人',
  `evaluate_comment_status` int(11) NULL DEFAULT NULL COMMENT '评论意见状态(0:同意,1:不同意)',
  `evaluate_comment` varchar(150) NULL DEFAULT NULL COMMENT '评价结论',
  `evaluate_date` date NULL DEFAULT NULL COMMENT '评价日期',
  `evaluate_sign_file` int(11) NULL DEFAULT NULL COMMENT '评论人签名文件',
  `company_principal` varchar(255) NULL DEFAULT NULL COMMENT '公司负责人',
  `company_principal_name` varchar(255) NULL DEFAULT NULL COMMENT '公司负责人',
  `company_comment_status` int(11) NULL DEFAULT NULL COMMENT '公司意见状态(0:同意,1:不同意)',
  `company_comment` varchar(150) NULL DEFAULT '' COMMENT '公司意见',
  `company_comment_date` date NULL DEFAULT NULL COMMENT '公司评价时间',
  `company_sign_file` int(11) NULL DEFAULT NULL COMMENT '公司负责人签名文件',
  `status` int(1) NOT NULL COMMENT '运行状态(0:新申请评价,1:参评人签字,2:填写评议结论,3:公司审核,4:审核通过,-1:驳回)',
  `is_reject` tinyint(1) NOT NULL COMMENT '是驳回',
  `project_id` int(6) NOT NULL COMMENT '项目部id',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `create_user` varchar(255) NOT NULL COMMENT '创建人',
  `create_user_name` varchar(255) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `is_delete` tinyint(1) NOT NULL COMMENT '是否删除(0:正常,1:删除)',
  PRIMARY KEY (`id`),
  INDEX `index_project_id`(`project_id`),
  INDEX `index_supplier_id`(`supplier_id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '物资-供方调查表';

-- ----------------------------
-- Records of dx_materials_supplier_survey
-- ----------------------------

-- ----------------------------
-- Table structure for dx_menu_permission
-- ----------------------------
DROP TABLE IF EXISTS `dx_menu_permission`;
CREATE TABLE `dx_menu_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父级',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '菜单名字',
  `url` varchar(255) NULL DEFAULT NULL COMMENT '菜单连接',
  `code` varchar(255) NULL DEFAULT NULL COMMENT '权限编',
  `type` varchar(255) NULL DEFAULT NULL COMMENT '1公司 2 项目',
  `array_sort` int(11) NOT NULL DEFAULT 1 COMMENT '排序',
  `ico` varchar(255) NULL DEFAULT NULL COMMENT '符号',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_menu_permission
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_branch_stie
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_branch_stie`;
CREATE TABLE `dx_mix_branch_stie`  (
  `site_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '位置Id',
  `engineer_id` varchar(64) NOT NULL COMMENT '位置创建人Id',
  `site` text NULL COMMENT '施工位置',
  `site_details` text NULL COMMENT '详细施工位置',
  `site_xy` varchar(100) NULL DEFAULT NULL COMMENT '位置X,y轴 字符串',
  `site_create_time` datetime(0) NULL DEFAULT NULL COMMENT '位置创建时间',
  `site_image_id` int(12) NULL DEFAULT 0 COMMENT '位置图片',
  PRIMARY KEY (`site_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_branch_stie
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_car
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_car`;
CREATE TABLE `dx_mix_car`  (
  `carid` bigint(20) NOT NULL COMMENT '车辆id',
  `concent` varchar(14) NOT NULL COMMENT '车牌号',
  `can_cube` float NOT NULL COMMENT '可装方量',
  `owner_name` varchar(10) NULL DEFAULT NULL COMMENT '车主姓名',
  `owner_phone` varchar(15) NULL DEFAULT NULL COMMENT '车主电话',
  `create_time` datetime(0) NOT NULL COMMENT '添加时间',
  `create_id` varchar(64) NOT NULL COMMENT '创建人id',
  `project_id` smallint(12) NOT NULL COMMENT '项目部Id',
  `project_name` text NOT NULL COMMENT '项目部名字',
  `car_number` varchar(20) NOT NULL COMMENT '车号',
  `count` int(4) NULL DEFAULT 0 COMMENT '安排次数',
  `type` int(2) NULL DEFAULT 1 COMMENT '车辆状态 1 使用 2 停用',
  `mix_id` int(11) NULL DEFAULT NULL COMMENT '拌合站id',
  `status` int(11) NULL DEFAULT NULL COMMENT '1 正在装车 2 正在运输  3闲置 4正在空回',
  `binding_device` varchar(225) NULL DEFAULT NULL COMMENT '设备号码',
  `mix_xy` varchar(255) NULL DEFAULT NULL COMMENT '拌合站坐标',
  PRIMARY KEY (`carid`),
  INDEX `mix_id`(`mix_id`),
  CONSTRAINT `mix_id` FOREIGN KEY (`mix_id`) REFERENCES `dx_mix_station` (`mixingStation_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_car
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_car_locus
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_car_locus`;
CREATE TABLE `dx_mix_car_locus`  (
  `locus_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '车辆运行轨迹id',
  `id` bigint(20) NOT NULL COMMENT '车订单与运输表的id',
  `did` bigint(20) NOT NULL COMMENT '订单id',
  `type` smallint(6) NOT NULL DEFAULT 0 COMMENT '1 运输目的地 2 返回拌合站',
  `locus_xy` mediumtext NULL COMMENT '车辆运行轨迹',
  `create_time` datetime(0) NOT NULL COMMENT '轨迹记录表创建时间',
  `app_id` varchar(23) NOT NULL COMMENT 'appid',
  `carid` bigint(20) NOT NULL COMMENT '车辆id',
  PRIMARY KEY (`locus_id`),
  UNIQUE INDEX `haul_id`(`id`),
  CONSTRAINT `id` FOREIGN KEY (`id`) REFERENCES `dx_mix_demand_haul` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_car_locus
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_car_locus_xy
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_car_locus_xy`;
CREATE TABLE `dx_mix_car_locus_xy`  (
  `locus_xy_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '车辆坐标表id',
  `id` bigint(20) NULL DEFAULT NULL COMMENT '运输表的id',
  `did` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  `type` int(2) NULL DEFAULT NULL COMMENT '1 前往 2 返回',
  `xy` mediumtext NULL COMMENT '坐标组',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '轨迹记录表创建时间',
  `car_id` bigint(20) NULL DEFAULT NULL COMMENT '车辆id',
  `level` int(11) NULL DEFAULT NULL,
  `mix_xy` varchar(200) NULL DEFAULT NULL COMMENT '拌合站坐标',
  `mix_name` varchar(120) NULL DEFAULT NULL COMMENT '拌合站名字',
  `mix_id` int(11) NULL DEFAULT NULL COMMENT '拌合站id',
  `pour_xy` varchar(200) NULL DEFAULT NULL COMMENT '浇筑地点坐标',
  `mongodb_id` varchar(64) NULL DEFAULT NULL,
  PRIMARY KEY (`locus_xy_id`),
  INDEX `haul_id`(`id`),
  CONSTRAINT `haul_id` FOREIGN KEY (`id`) REFERENCES `dx_mix_demand_haul` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_car_locus_xy
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_car_record
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_car_record`;
CREATE TABLE `dx_mix_car_record`  (
  `car_record_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '车辆顺序记录表id',
  `did` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  `source` int(2) NOT NULL COMMENT '存入的数据来源 1 调度 2 搅拌机',
  `type` int(2) NULL DEFAULT 1 COMMENT '1 未读 2 已读',
  `mix_id` int(11) NULL DEFAULT NULL COMMENT '拌合站id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `unit` int(11) NULL DEFAULT NULL COMMENT '机组',
  PRIMARY KEY (`car_record_id`),
  INDEX `did_mesg_mix`(`did`),
  CONSTRAINT `did_mesg_mix` FOREIGN KEY (`did`) REFERENCES `dx_mix_demand` (`did`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_car_record
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_car_trans_type
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_car_trans_type`;
CREATE TABLE `dx_mix_car_trans_type`  (
  `trans_type_id` int(12) NOT NULL AUTO_INCREMENT COMMENT '运输方式Id',
  `trans_type_name` varchar(12) NOT NULL COMMENT '运输方式名字',
  `type_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`trans_type_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_car_trans_type
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_car_tree
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_car_tree`;
CREATE TABLE `dx_mix_car_tree`  (
  `id` bigint(20) NOT NULL COMMENT '主键Id',
  `part_id` bigint(20) NOT NULL COMMENT '父Id(车辆id)',
  `car_name` varchar(9) NOT NULL COMMENT '司机姓名',
  `car_phone` varchar(22) NULL DEFAULT NULL COMMENT '司机电话',
  `workshift` tinyint(4) NOT NULL COMMENT '1上白班，2夜班',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '1:开始运输，2停止运输,3 运输完成 4 等待装车 5 空车返回',
  `project_id` int(12) NOT NULL COMMENT '部门Id',
  `project_name` text NOT NULL COMMENT '部门名字',
  `is_work` tinyint(2) NULL DEFAULT 0 COMMENT '1 上班 2 下班',
  `mix_id` int(11) NULL DEFAULT NULL COMMENT '拌合站id',
  `app_id` varchar(225) NULL DEFAULT NULL COMMENT '司机appid',
  `mix_xy` varchar(255) NULL DEFAULT NULL COMMENT '拌合站坐标',
  PRIMARY KEY (`id`),
  INDEX `carid`(`part_id`),
  INDEX `mix_id_car`(`mix_id`),
  CONSTRAINT `carid` FOREIGN KEY (`part_id`) REFERENCES `dx_mix_car` (`carid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `mix_id_car` FOREIGN KEY (`mix_id`) REFERENCES `dx_mix_station` (`mixingStation_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_car_tree
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_construction_team
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_construction_team`;
CREATE TABLE `dx_mix_construction_team`  (
  `ctid` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '施工队id',
  `team_name` varchar(30) NULL DEFAULT NULL COMMENT '公司名称',
  `principal` varchar(30) NULL DEFAULT NULL COMMENT '法定代表人',
  `principal_phone` varchar(255) NULL DEFAULT NULL COMMENT '合同签订人电话',
  `contractor` varchar(30) NULL DEFAULT NULL COMMENT '合同签订人',
  `cardid` varchar(20) NULL DEFAULT NULL COMMENT '身份证',
  `people_size` smallint(11) NULL DEFAULT NULL COMMENT '队伍人数',
  `team_year` varchar(10) NULL DEFAULT NULL COMMENT '队伍年限',
  PRIMARY KEY (`ctid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_construction_team
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_demand
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_demand`;
CREATE TABLE `dx_mix_demand`  (
  `did` bigint(20) NOT NULL COMMENT '订单id',
  `number` varchar(20) NOT NULL COMMENT '订单编号',
  `status` int(4) NOT NULL DEFAULT 0 COMMENT '订单状态(0 下单成功 1开盘 2 待装车 3 配合比完成 4正在运输 5等待配合比 6运输完成 7 完成车辆安排 8不开盘  9 确认浇筑完成  10  取消订单',
  `order_create_time` datetime(0) NOT NULL COMMENT '订单创建时间',
  `plan_cube` float NOT NULL DEFAULT 0 COMMENT '需求方量',
  `sum_cube` float NULL DEFAULT 0 COMMENT '总方量',
  `project_cube` float NOT NULL DEFAULT 0 COMMENT '设计方量',
  `cube` float NULL DEFAULT 0 COMMENT '浇筑方量',
  `add_cube` float NULL DEFAULT 0 COMMENT '补方量',
  `remain` float NULL DEFAULT 0 COMMENT '剩余方量',
  `cube_model` varchar(20) NULL DEFAULT NULL COMMENT '标号',
  `jf_id` bigint(20) NULL DEFAULT NULL COMMENT '具体配方id',
  `jf_name` varchar(225) NULL DEFAULT NULL COMMENT '具体配比名称',
  `lister_id` smallint(6) NULL DEFAULT NULL COMMENT '制单员id',
  `lister_name` varchar(30) NULL DEFAULT NULL COMMENT '制单员名称',
  `submit_time` datetime(0) NULL DEFAULT NULL COMMENT '提单时间',
  `pour_time` datetime(0) NULL DEFAULT NULL COMMENT '浇筑时间',
  `pour_type_id` int(12) NULL DEFAULT NULL COMMENT '浇筑方式Id',
  `formula_time` datetime(0) NULL DEFAULT NULL COMMENT '配置时间',
  `average_time` datetime(0) NULL DEFAULT NULL COMMENT '平均用时',
  `fast_number` tinyint(4) NULL DEFAULT NULL COMMENT '最快车号',
  `fast_time` datetime(0) NULL DEFAULT NULL COMMENT '最快用时',
  `slow_number` tinyint(4) NULL DEFAULT NULL COMMENT '最慢车号',
  `slow_time` datetime(4) NULL DEFAULT NULL COMMENT '最慢用时',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '技术员备注',
  `modulus` tinyint(4) NULL DEFAULT NULL COMMENT '路面难度系数1-5',
  `distance` varchar(255) NULL DEFAULT NULL COMMENT '里程',
  `sy_remark` varchar(255) NULL DEFAULT NULL COMMENT '实验员备注',
  `bh_remark` varchar(255) NULL DEFAULT NULL COMMENT '拌合员备注',
  `ap_remark` varchar(255) NULL DEFAULT NULL COMMENT '安排员备注',
  `trans_time` datetime(0) NULL DEFAULT NULL COMMENT '运输时间',
  `environment` varchar(120) NULL DEFAULT NULL COMMENT '环境',
  `trans_type_id` varchar(32) NULL DEFAULT NULL COMMENT '运输方式id',
  `fall_highness` varchar(120) NULL DEFAULT NULL COMMENT '塌落度',
  `site_id` int(6) NULL DEFAULT NULL COMMENT '施工位置Id',
  `mixingStation_id` varchar(22) NOT NULL COMMENT '混合站Id 多个用逗号隔开',
  `create_user_id` varchar(64) NOT NULL COMMENT '订单创建人Id',
  `duty_person` varchar(18) NOT NULL COMMENT '负责人名字(订单创建人)',
  `duty_phone` varchar(22) NOT NULL COMMENT '负责人电话(订单创建人电话)',
  `project_id` smallint(12) NOT NULL COMMENT '项目部id',
  `project_name` text NOT NULL COMMENT '项目部名称',
  `part_id` varchar(225) NOT NULL COMMENT '部位Id',
  `part_name` varchar(255) NOT NULL COMMENT '部位名字',
  `plan_end_pour` datetime(0) NULL DEFAULT NULL COMMENT '完成浇筑时间',
  `plan_start_time` datetime(0) NULL DEFAULT NULL COMMENT '订单计划开始时间',
  `is_plan` int(2) NULL DEFAULT 0 COMMENT '是否计划 1 计划  0不计划  2 执行计划  4 推迟计划',
  `is_open_quotation` int(4) NULL DEFAULT 0 COMMENT '是否开盘 1开盘 2 不开盘 3 已询问开盘',
  `part_name_detailed` varchar(225) NULL DEFAULT NULL COMMENT '详细部位',
  `site_detailed` varchar(225) NULL DEFAULT NULL COMMENT '详细位置',
  `unloaded_time_id` smallint(10) NULL DEFAULT NULL COMMENT '预计卸载时间Id',
  `unloaded_time_name` varchar(12) NULL DEFAULT NULL COMMENT '预计卸载时间',
  `urgent_id` smallint(12) NULL DEFAULT 1 COMMENT '紧急程度id',
  `urgent_name` varchar(12) NULL DEFAULT NULL COMMENT '紧急程度名字',
  `is_product` int(2) NULL DEFAULT 2 COMMENT '是否商品 2不是 1 是',
  `pour_type_name` varchar(123) NULL DEFAULT NULL COMMENT '浇筑方式名字',
  `trans_type_name` varchar(123) NULL DEFAULT NULL COMMENT '运输方式名字',
  `site_xy` varchar(223) NULL DEFAULT NULL COMMENT '位置XY',
  `Image_id` int(12) NULL DEFAULT 0 COMMENT '开盘图片Id',
  `open_quotation_remakr` varchar(123) NULL DEFAULT NULL COMMENT '开盘或不开盘的备注',
  `app_id` text NULL COMMENT 'app下单的appid',
  `start_cube` float NULL DEFAULT 0 COMMENT '已拌方量',
  `wait_cube` float NULL DEFAULT 0 COMMENT '待拌方量',
  `open_quotation_name` varchar(225) NULL DEFAULT NULL COMMENT '开盘发起人名字',
  `open_quotation_id` varchar(64) NULL DEFAULT '0' COMMENT '开盘发起人id',
  `order_number` varchar(12) NULL DEFAULT NULL COMMENT '订单编号(废弃)',
  `mix_count` smallint(4) NULL DEFAULT 0 COMMENT '搅拌盘数',
  `particle_size` float NULL DEFAULT 0 COMMENT '粒径',
  `mix_time` varchar(120) NULL DEFAULT NULL COMMENT '总共搅拌时间',
  `impervious` smallint(6) NULL DEFAULT 0 COMMENT '抗渗等级',
  `is_ask` smallint(2) NULL DEFAULT 0 COMMENT '是否询问开盘 1已询问 2处理询问 默认0 没询问',
  `pour_start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始浇筑时间',
  `end_pour_time` datetime(0) NULL DEFAULT NULL COMMENT '确认完成浇筑时间',
  `put_off_msg` varchar(200) NULL DEFAULT NULL COMMENT '推迟原因',
  `put_off_user_id` varchar(64) NULL DEFAULT NULL COMMENT '订单推迟计划人员id',
  `cance_time` datetime(0) NULL DEFAULT NULL COMMENT '订单取消时间',
  `cancel_user_id` varchar(64) NULL DEFAULT NULL COMMENT '订单取消人员id',
  `start_order_user_id` varchar(64) NULL DEFAULT NULL COMMENT '订单启用人员id',
  `put_off_time` datetime(0) NULL DEFAULT NULL COMMENT '订单执行推迟时间',
  `cance_remark` varchar(200) NULL DEFAULT NULL COMMENT '订单取消备注',
  `jf_create_time` datetime(0) NULL DEFAULT NULL COMMENT '配方加入订单时间',
  `lister_phone` varchar(22) NULL DEFAULT NULL COMMENT '制单员电话',
  `labor_force_id` int(50) NULL DEFAULT NULL COMMENT '劳务队Id',
  `labor_force_str` varchar(255) NULL DEFAULT NULL COMMENT '劳务队名字',
  `phone_model` varchar(32) NULL DEFAULT NULL COMMENT '手机型号',
  `abbreviation` varchar(120) NULL DEFAULT NULL COMMENT '项目部简称',
  PRIMARY KEY (`did`),
  UNIQUE INDEX `number`(`number`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_demand
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_demand_addcube_logs
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_demand_addcube_logs`;
CREATE TABLE `dx_mix_demand_addcube_logs`  (
  `logs_id` int(12) NOT NULL AUTO_INCREMENT COMMENT '补方操作记录表',
  `did` bigint(20) NOT NULL COMMENT '订单id',
  `add_cube` float NOT NULL COMMENT '补方量',
  `record_time` datetime(0) NULL DEFAULT NULL COMMENT '记录补方时间',
  `user_id` varchar(64) NULL DEFAULT NULL COMMENT '操作人id',
  `remark` varchar(225) NULL DEFAULT NULL COMMENT '备注',
  `user_name` varchar(50) NULL DEFAULT NULL COMMENT '操作人名字',
  PRIMARY KEY (`logs_id`),
  INDEX `add_cube_did`(`did`),
  CONSTRAINT `add_cube_did` FOREIGN KEY (`did`) REFERENCES `dx_mix_demand` (`did`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_demand_addcube_logs
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_demand_haul
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_demand_haul`;
CREATE TABLE `dx_mix_demand_haul`  (
  `id` bigint(20) NOT NULL COMMENT '订单与运输表的主键',
  `did` bigint(20) NOT NULL COMMENT '订单id',
  `carid` bigint(20) NOT NULL COMMENT '车辆Id',
  `car_number` varchar(20) NOT NULL COMMENT '车号',
  `can_cube` float NOT NULL COMMENT '可载方量',
  `gear_cube` float NOT NULL COMMENT '安排方量',
  `gear_Time` datetime(0) NOT NULL COMMENT '安排时间',
  `count` int(6) NOT NULL DEFAULT 0 COMMENT '运输次数',
  `status` tinyint(4) NOT NULL COMMENT '车辆状态: 0报废 1:到达浇筑地点，2停止运输,3 运输完成 4 等待装车 5 内部调走 6 其他调走 7该车拉取的方量搅拌完成 8正在运输 9 空车返回  10完成车辆安排 11 完成浇筑  12 到达拌合站  13 开始浇筑 14 开始拌合',
  `car_name` varchar(8) NULL DEFAULT NULL COMMENT '司机姓名',
  `car_phone` varchar(22) NULL DEFAULT NULL COMMENT '司机电话',
  `concent` varchar(20) NULL DEFAULT NULL COMMENT '车牌号',
  `rating` tinyint(5) NULL DEFAULT 5 COMMENT '司机评价',
  `orders_time` datetime(0) NULL DEFAULT NULL COMMENT '司机接单时间',
  `avg_time` varchar(12) NULL DEFAULT NULL COMMENT '司机运输平均时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '司机订单完成时间',
  `sum_time` varchar(12) NULL DEFAULT NULL COMMENT '司机运输总共时间',
  `rating_content` varchar(20) NULL DEFAULT '0' COMMENT '司机评价类容',
  `layout_name` varchar(225) NULL DEFAULT NULL COMMENT '其他安排名字',
  `remain_cube` float NULL DEFAULT NULL COMMENT '剩余方量',
  `mix_cube` float NULL DEFAULT NULL COMMENT '搅拌方量',
  `end_mix_time` datetime(0) NULL DEFAULT NULL COMMENT '搅拌完成时间',
  `transfer_cube` float NULL DEFAULT 0 COMMENT '调走方量',
  `scrap_cube` float NULL DEFAULT 0 COMMENT '报废方量',
  `start_pour_time` datetime(0) NULL DEFAULT NULL COMMENT '开始浇筑时间',
  `end_pour_time` datetime(0) NULL DEFAULT NULL COMMENT '完成浇筑时间',
  `return_mix_time` datetime(0) NULL DEFAULT NULL COMMENT '开始返回拌合站时间',
  `get_to_mix_time` datetime(0) NULL DEFAULT NULL COMMENT '到达拌合站时间',
  `start_mix_time` datetime(0) NULL DEFAULT NULL COMMENT '开始拌合时间',
  `unit` varchar(150) NULL DEFAULT NULL COMMENT '机组名字',
  `mix_user_name` varchar(100) NULL DEFAULT NULL COMMENT '搅拌司机名字',
  `mix_user_id` varchar(64) NULL DEFAULT NULL COMMENT '搅拌司机id',
  `level` int(11) NULL DEFAULT 0 COMMENT '调度安排顺序',
  `mix_id` int(11) NULL DEFAULT NULL COMMENT '拌合站id',
  `mix_name` varchar(225) NULL DEFAULT NULL COMMENT '拌合站名字',
  `is_read` int(2) NULL DEFAULT 1 COMMENT '记录设备获取消息（1 未读 2 已读）',
  `err_type` int(2) NULL DEFAULT 0 COMMENT '1 异常 0 正常',
  `out_mix_time` datetime(0) NULL DEFAULT NULL COMMENT '离开拌合站时间',
  `car_mark` int(11) NULL DEFAULT 0 COMMENT '运输标识',
  `is_diaozou_type` int(11) NULL DEFAULT 0 COMMENT '1 内部调走',
  PRIMARY KEY (`id`),
  INDEX `did`(`did`),
  CONSTRAINT `did` FOREIGN KEY (`did`) REFERENCES `dx_mix_demand` (`did`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_demand_haul
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_demand_haul_jf
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_demand_haul_jf`;
CREATE TABLE `dx_mix_demand_haul_jf`  (
  `haul_jf_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '运输和配方中间表',
  `id` bigint(20) NOT NULL COMMENT '运输中间表id',
  `jf_id` bigint(20) NULL DEFAULT NULL COMMENT '配方id',
  `did` bigint(20) NULL DEFAULT NULL COMMENT '订单did',
  `a1` float NULL DEFAULT NULL COMMENT '水泥数量',
  `a2` float NULL DEFAULT NULL COMMENT '粉末灰数量',
  `a3` float NULL DEFAULT NULL COMMENT '矿粉数量',
  `a4` float NULL DEFAULT NULL COMMENT '粗砂数量',
  `a5` float NULL DEFAULT NULL COMMENT '细沙数量',
  `a6` float NULL DEFAULT NULL COMMENT '水数量',
  `a7_a` float NULL DEFAULT NULL COMMENT '碎石1数量',
  `a7_b` float NULL DEFAULT NULL COMMENT '碎石2数量',
  `a8` float NULL DEFAULT NULL COMMENT '粗骨料数量',
  `a9` float NULL DEFAULT NULL COMMENT '细骨料数量',
  `a10` float NULL DEFAULT NULL COMMENT '矿渣粉数量',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '记录创建时间',
  `operate_user_id` varchar(64) NULL DEFAULT NULL COMMENT '操作人id',
  `operate_user_name` varchar(20) NULL DEFAULT NULL COMMENT '操作人名字',
  `mix_id` int(11) NULL DEFAULT NULL COMMENT '拌合站id',
  PRIMARY KEY (`haul_jf_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_demand_haul_jf
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_demand_haul_transfer
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_demand_haul_transfer`;
CREATE TABLE `dx_mix_demand_haul_transfer`  (
  `transfer_id` int(16) NOT NULL AUTO_INCREMENT COMMENT '调走运输主键id',
  `transfer_time` datetime(0) NULL DEFAULT NULL COMMENT '方量变化的时间',
  `transfer_cube` float NULL DEFAULT 0 COMMENT '调走的方量',
  `id` bigint(20) NOT NULL COMMENT '订单和运输表中间ID',
  `reason` varchar(225) NULL DEFAULT NULL COMMENT '调走理由',
  `remark` text NULL COMMENT '调走备注',
  `scrap_cube` float NULL DEFAULT 0 COMMENT '报废方量',
  `project_id` int(11) NOT NULL COMMENT '项目部Id',
  `type` tinyint(2) NULL DEFAULT 0 COMMENT '1 内部调走 2 外部调走',
  `send_id` varchar(64) NULL DEFAULT NULL COMMENT '调走发起人',
  `send_name` varchar(8) NULL DEFAULT NULL COMMENT '调走人名字',
  `car_number` bigint(20) NULL DEFAULT NULL COMMENT '车号',
  `number` varchar(32) NULL DEFAULT NULL COMMENT '订单编号',
  `did` bigint(20) NULL DEFAULT NULL COMMENT '内部调走的订单id',
  PRIMARY KEY (`transfer_id`),
  INDEX `id`(`id`),
  INDEX `haul_did`(`did`),
  CONSTRAINT `haul_did` FOREIGN KEY (`did`) REFERENCES `dx_mix_demand` (`did`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_demand_haul_transfer
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_demand_mix
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_demand_mix`;
CREATE TABLE `dx_mix_demand_mix`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单和拌合站中间表Id',
  `did` bigint(20) NOT NULL COMMENT '订单id',
  `mixingstation_id` int(12) NOT NULL COMMENT '拌合站Id',
  `mix_project_id` varchar(12) NOT NULL COMMENT '调度员和拌合站的项目部部门Id',
  PRIMARY KEY (`id`),
  INDEX `did_mix`(`did`),
  CONSTRAINT `did_mix` FOREIGN KEY (`did`) REFERENCES `dx_mix_demand` (`did`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_demand_mix
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_demand_remark
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_demand_remark`;
CREATE TABLE `dx_mix_demand_remark`  (
  `remark_id` int(12) NOT NULL AUTO_INCREMENT COMMENT '订单备注表主键',
  `did` bigint(20) NOT NULL COMMENT '订单id',
  `create_user_id` varchar(64) NOT NULL COMMENT '备注创建人员id',
  `create_user_name` varchar(15) NOT NULL COMMENT '备注创建人姓名',
  `create_user_position` varchar(130) NOT NULL COMMENT '备注创建人职务',
  `create_time` datetime(0) NOT NULL COMMENT '备注创建时间',
  `remark` text NULL COMMENT '备注',
  PRIMARY KEY (`remark_id`),
  INDEX `remark_did`(`did`),
  CONSTRAINT `remark_did` FOREIGN KEY (`did`) REFERENCES `dx_mix_demand` (`did`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_demand_remark
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_jt_formula
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_jt_formula`;
CREATE TABLE `dx_mix_jt_formula`  (
  `jfid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '具体配方id',
  `formula_name` varchar(10) NOT NULL COMMENT '强度等级',
  `user_id` varchar(64) NOT NULL COMMENT '编制人员id',
  `user_name` varchar(10) NOT NULL COMMENT '编制人员姓名',
  `plait_time` datetime(0) NOT NULL COMMENT '配方创建时间',
  `remark` varchar(100) NULL DEFAULT NULL COMMENT '备注',
  `jf_number` varchar(120) NULL DEFAULT NULL COMMENT '报告编号',
  `part_id` smallint(6) NULL DEFAULT NULL COMMENT '部位Id',
  `part_name` varchar(120) NULL DEFAULT NULL COMMENT '配方部位名字',
  `admixturefloat` varchar(120) NULL DEFAULT NULL COMMENT '外加剂 数量',
  `water_gluefloat` varchar(120) NULL DEFAULT NULL COMMENT '水胶比 数量',
  `slumpfloat` varchar(120) NULL DEFAULT NULL COMMENT '塌落度 数量',
  `sand_ratefloat` varchar(120) NULL DEFAULT NULL COMMENT '体积砂率 数量',
  `gas_contentfloat` varchar(120) NULL DEFAULT NULL COMMENT '含气量 数量',
  `setting` varchar(120) NULL DEFAULT NULL COMMENT '环境',
  `project_id` int(12) NOT NULL COMMENT '项目部Id',
  `project_name` text NOT NULL COMMENT '项目部名字',
  `jf_name` varchar(255) NULL DEFAULT NULL COMMENT '配合比名称',
  PRIMARY KEY (`jfid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_jt_formula
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_jt_formula_clone
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_jt_formula_clone`;
CREATE TABLE `dx_mix_jt_formula_clone`  (
  `clone_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配方材料修改表id',
  `did` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  `jfid` bigint(20) NOT NULL COMMENT '配方id',
  `a1` float NULL DEFAULT 0 COMMENT '水泥数量',
  `a2` float NULL DEFAULT 0 COMMENT '粉末灰数量',
  `a3` float NULL DEFAULT 0 COMMENT '矿粉数量',
  `a4` float NULL DEFAULT 0 COMMENT '粗砂数量',
  `a5` float NULL DEFAULT 0 COMMENT '细沙数量',
  `a6` float NULL DEFAULT 0 COMMENT '水数量',
  `a7_a` float NULL DEFAULT 0 COMMENT '碎石1数量',
  `a7_b` float NULL DEFAULT 0 COMMENT '碎石2数量',
  `a8` float NULL DEFAULT 0 COMMENT '粗骨料数量',
  `a9` float NULL DEFAULT 0 COMMENT '细骨料数量',
  `a10` float NULL DEFAULT 0 COMMENT '矿渣粉数量',
  `mix_notes_logs` text NULL COMMENT '拌合站材料微调记录日志',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `source_type` int(11) NULL DEFAULT NULL COMMENT '1 主表 2 部位配方克隆表',
  PRIMARY KEY (`clone_id`),
  UNIQUE INDEX `did`(`did`),
  CONSTRAINT `jf_did` FOREIGN KEY (`did`) REFERENCES `dx_mix_demand` (`did`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_jt_formula_clone
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_jt_formula_pojo
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_jt_formula_pojo`;
CREATE TABLE `dx_mix_jt_formula_pojo`  (
  `pojo_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '配方中间表Id',
  `jfid` bigint(20) NOT NULL COMMENT '配方Id',
  `a1` float NULL DEFAULT NULL COMMENT '水泥数量',
  `a2` float NULL DEFAULT NULL COMMENT '粉末灰数量',
  `a3` float NULL DEFAULT NULL COMMENT '矿粉数量',
  `a4` float NULL DEFAULT NULL COMMENT '粗砂数量',
  `a5` float NULL DEFAULT NULL COMMENT '细沙数量',
  `a6` float NULL DEFAULT NULL COMMENT '水数量',
  `a7_a` float NULL DEFAULT NULL COMMENT '碎石1数量',
  `a7_b` float NULL DEFAULT NULL COMMENT '碎石2数量',
  `a8` float NULL DEFAULT NULL COMMENT '粗骨料数量',
  `a9` float NULL DEFAULT NULL COMMENT '细骨料数量',
  `a10` float NULL DEFAULT NULL COMMENT '矿渣粉数量',
  PRIMARY KEY (`pojo_id`),
  UNIQUE INDEX `formulaId`(`jfid`),
  CONSTRAINT `jfid` FOREIGN KEY (`jfid`) REFERENCES `dx_mix_jt_formula` (`jfid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_jt_formula_pojo
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_label
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_label`;
CREATE TABLE `dx_mix_label`  (
  `label_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '混凝土标号id',
  `label` varchar(100) NOT NULL COMMENT '标号',
  `apply_user_id` varchar(64) NOT NULL COMMENT '使用人id',
  `apply_user_name` varchar(20) NOT NULL COMMENT '使用人名字',
  `count` int(11) NOT NULL DEFAULT 0 COMMENT '使用次数',
  `label_create` datetime(0) NOT NULL COMMENT '标号创建时间',
  PRIMARY KEY (`label_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_label
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_msg
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_msg`;
CREATE TABLE `dx_mix_msg`  (
  `msg_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '消息主键',
  `title` varchar(20) NOT NULL COMMENT '消息标题',
  `msg` varchar(225) NOT NULL COMMENT '消息',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `did` bigint(20) NULL DEFAULT NULL COMMENT '订单Id',
  `data` varchar(228) NULL DEFAULT NULL COMMENT '数据',
  `type` tinyint(2) NULL DEFAULT 0 COMMENT '1 发送 2 没发送',
  `img_id` int(12) NULL DEFAULT NULL COMMENT '图片ID',
  `open_quotation_name` varchar(20) NULL DEFAULT NULL COMMENT '开盘发起人名字',
  PRIMARY KEY (`msg_id`),
  INDEX `did_msg`(`did`),
  CONSTRAINT `did_msg` FOREIGN KEY (`did`) REFERENCES `dx_mix_demand` (`did`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_msg
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_outgive_car
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_outgive_car`;
CREATE TABLE `dx_mix_outgive_car`  (
  `outid` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '分车id',
  `demand_did` smallint(6) NOT NULL COMMENT '需求id',
  `car_id` smallint(6) NOT NULL COMMENT '车辆id',
  `actual_number` float NOT NULL COMMENT '实际运输方量',
  `mix_time` datetime(0) NULL DEFAULT NULL COMMENT '拌合时间',
  `buser_id` smallint(6) NULL DEFAULT NULL COMMENT '拌合员id',
  `user_name` varchar(10) NULL DEFAULT NULL COMMENT '拌合员姓名',
  `gear_time` datetime(0) NULL DEFAULT NULL COMMENT '安排时间',
  `auser_id` smallint(6) NULL DEFAULT NULL COMMENT '安排员id',
  `gear_name` varchar(10) NULL DEFAULT NULL COMMENT '安排员姓名',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '回单确认（状态）',
  `load_finish_time` datetime(0) NULL DEFAULT NULL COMMENT '装车完成时间',
  `reach_time` datetime(0) NULL DEFAULT NULL COMMENT '到达时间',
  `return_time` datetime(0) NULL DEFAULT NULL COMMENT '返回时间',
  PRIMARY KEY (`outid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_outgive_car
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_partid
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_partid`;
CREATE TABLE `dx_mix_partid`  (
  `partid` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '部位id',
  `part_name` varchar(30) NOT NULL COMMENT '部位名称',
  `pid` varchar(25) NULL DEFAULT NULL COMMENT 'parent',
  `lqs` smallint(6) NULL DEFAULT NULL COMMENT '分类标识:1(路),2(桥),3(隧)',
  `sl` smallint(6) NULL DEFAULT NULL COMMENT '类别:1(水),2(陆)',
  `score` smallint(6) NULL DEFAULT NULL COMMENT '分数',
  PRIMARY KEY (`partid`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_partid
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_pour_type
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_pour_type`;
CREATE TABLE `dx_mix_pour_type`  (
  `pour_type_id` smallint(12) NOT NULL AUTO_INCREMENT COMMENT '浇筑方式Id',
  `type_name` varchar(12) NOT NULL COMMENT '类型名字',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '类型创建时间',
  PRIMARY KEY (`pour_type_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_pour_type
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_record_status
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_record_status`;
CREATE TABLE `dx_mix_record_status`  (
  `record_id` bigint(20) NOT NULL COMMENT '状态记录表id',
  `did` bigint(20) NOT NULL COMMENT '订单id',
  `id` bigint(20) NOT NULL COMMENT '运输表id',
  `order_status` int(11) NULL DEFAULT NULL COMMENT '订单状态',
  `haul_status` int(11) NOT NULL COMMENT '运输表状态',
  `record_time` datetime(0) NOT NULL COMMENT '记录时间',
  `type` int(11) NOT NULL DEFAULT 1 COMMENT '1 未读 2 已读',
  `mix_id` int(11) NOT NULL COMMENT '拌合站id',
  `unit` varchar(100) NULL DEFAULT NULL COMMENT '机组',
  `end_mix_time` datetime(0) NULL DEFAULT NULL COMMENT '开始搅拌时间',
  PRIMARY KEY (`record_id`),
  UNIQUE INDEX `id`(`id`),
  CONSTRAINT `record_id` FOREIGN KEY (`id`) REFERENCES `dx_mix_demand_haul` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_record_status
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_station
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_station`;
CREATE TABLE `dx_mix_station`  (
  `mixingStation_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '拌合站id',
  `station_name` varchar(30) NOT NULL COMMENT '拌合站名字',
  `mix_project_id` varchar(12) NOT NULL COMMENT '项目部Id',
  `isBool` tinyint(2) NULL DEFAULT 0,
  `mix_xy` varchar(30) NOT NULL COMMENT '拌合站位置XY',
  `is_true` tinyint(4) NULL DEFAULT 0 COMMENT '是否退场',
  `mix_url` varchar(225) NULL DEFAULT NULL COMMENT '拌合站路径',
  `mix_user_name` varchar(20) NULL DEFAULT NULL COMMENT '站长名字',
  `mix_user_phone` varchar(22) NULL DEFAULT NULL COMMENT '站长联系方式',
  `mix_xy_detailed` varchar(125) NULL DEFAULT NULL COMMENT '详细位置',
  `mix_user_id` varchar(64) NULL DEFAULT NULL COMMENT '站长id',
  `unit` varchar(225) NULL DEFAULT NULL COMMENT '机组逗号拼接字符串',
  `radius` varchar(100) NULL DEFAULT NULL COMMENT '范围',
  PRIMARY KEY (`mixingStation_id`),
  INDEX `uerid`(`mix_user_id`),
  CONSTRAINT `uerid` FOREIGN KEY (`mix_user_id`) REFERENCES `dx_erp_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_station
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_station_staff
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_station_staff`;
CREATE TABLE `dx_mix_station_staff`  (
  `mix_staff_id` varchar(70) NOT NULL COMMENT '拌合站员工表主键id',
  `mixingStation_id` int(11) NULL DEFAULT NULL COMMENT '拌合站id',
  `station_name` varchar(30) NULL DEFAULT NULL COMMENT '拌合站名字',
  `staff_id` varchar(64) NULL DEFAULT NULL COMMENT '员工id',
  `staff_phone` varchar(22) NULL DEFAULT NULL COMMENT '员工电话',
  `staff_name` varchar(15) NULL DEFAULT NULL COMMENT '员工姓名',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '员工加入拌合站时间',
  PRIMARY KEY (`mix_staff_id`),
  UNIQUE INDEX `staff`(`staff_id`, `staff_name`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_station_staff
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_unloaded_time
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_unloaded_time`;
CREATE TABLE `dx_mix_unloaded_time`  (
  `unloaded_time_id` smallint(12) NOT NULL AUTO_INCREMENT COMMENT '预计卸载时间Id',
  `unloaded_time` varchar(100) NOT NULL COMMENT '卸载时间',
  `branch` varchar(2) NOT NULL COMMENT '单位',
  PRIMARY KEY (`unloaded_time_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_unloaded_time
-- ----------------------------

-- ----------------------------
-- Table structure for dx_mix_urgent
-- ----------------------------
DROP TABLE IF EXISTS `dx_mix_urgent`;
CREATE TABLE `dx_mix_urgent`  (
  `urgent_id` smallint(12) NOT NULL AUTO_INCREMENT COMMENT '预警程度Id',
  `urgent_name` varchar(20) NOT NULL COMMENT '预计程度名字',
  PRIMARY KEY (`urgent_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_mix_urgent
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_applyfor_butie
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_applyfor_butie`;
CREATE TABLE `dx_money_applyfor_butie`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userId` varchar(2000) NULL DEFAULT NULL COMMENT '申请人员id（多个用逗号拼接）',
  `user_name` varchar(2000) NULL DEFAULT NULL COMMENT '申请人姓名（多个用逗号拼接）',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `project_name` varchar(255) NULL DEFAULT NULL COMMENT '项目部简称',
  `start_date` date NULL DEFAULT NULL COMMENT '起算时间',
  `workId` smallint(6) NULL DEFAULT NULL COMMENT '岗位id',
  `work_name` varchar(255) NULL DEFAULT NULL COMMENT '岗位名称',
  `work_money` double NULL DEFAULT NULL COMMENT '岗位工资',
  `work_level` varchar(55) NULL DEFAULT NULL COMMENT '岗位档别',
  `work_score` varchar(55) NULL DEFAULT NULL COMMENT '岗位系数',
  `annual_wages` double NULL DEFAULT NULL COMMENT '年工工资',
  `skill_allowance` double NULL DEFAULT NULL COMMENT '技术津贴',
  `adm_position_allowance` double NULL DEFAULT NULL COMMENT '技能津贴',
  `woman_allowance` double NULL DEFAULT NULL COMMENT '女工卫生费',
  `flow_allowance` double NULL DEFAULT NULL COMMENT '流动津贴',
  `tunnel_allowance` double NULL DEFAULT NULL COMMENT '隧道津贴',
  `sand_wind_allowance` double NULL DEFAULT NULL COMMENT '风沙津贴',
  `plateau_allowance` double NULL DEFAULT NULL COMMENT '高原津贴',
  `certified_allowance` double NULL DEFAULT NULL COMMENT '注册师津贴',
  `part_time_allowance` double NULL DEFAULT NULL COMMENT '兼职津贴',
  `material_allowance` double NULL DEFAULT NULL COMMENT '资料费补贴',
  `telephone_allowance` double NULL DEFAULT NULL COMMENT '通讯补贴',
  `meals_allowance` double NULL DEFAULT NULL COMMENT '餐费补贴',
  `only_child_allowance` double NULL DEFAULT NULL COMMENT '独生子女费',
  `create_userId` varchar(255) NULL DEFAULT NULL COMMENT '操作人id',
  `create_user` varchar(255) NULL DEFAULT NULL COMMENT '操作人姓名',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `apply_for_userId` varchar(255) NULL DEFAULT NULL COMMENT '审批人id',
  `apply_for_user` varchar(255) NULL DEFAULT NULL COMMENT '审批人姓名',
  `apply_for_time` datetime(0) NULL DEFAULT NULL COMMENT '审批时间',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '状态 0待审核 -1拒绝 1同意',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_applyfor_butie
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_applyfor_proconfig
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_applyfor_proconfig`;
CREATE TABLE `dx_money_applyfor_proconfig`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `projectName` varchar(255) NULL DEFAULT NULL COMMENT '项目部名称',
  `projectAbb` varchar(255) NULL DEFAULT NULL COMMENT '项目部简称',
  `typeLast` int(11) NULL DEFAULT NULL COMMENT '上次项目部类别',
  `typeNow` int(11) NULL DEFAULT NULL COMMENT '项目部类别',
  `allmoneyLast` double NULL DEFAULT NULL COMMENT '上次合同总额',
  `allmoneyNow` double NULL DEFAULT NULL COMMENT '合同总额',
  `peopleLast` int(11) NULL DEFAULT NULL COMMENT '上次定编人数',
  `peopleNow` int(11) NULL DEFAULT NULL COMMENT '定编人数',
  `yearLast` int(11) NULL DEFAULT NULL COMMENT '上次施工工期',
  `yearNow` int(11) NULL DEFAULT NULL COMMENT '施工工期',
  `fid` int(11) NULL DEFAULT NULL COMMENT '附件id',
  `hrStatus` tinyint(4) NULL DEFAULT NULL COMMENT '人力审核状态 1未审批 2 已同意 3 已拒绝',
  `jgStatus` tinyint(4) NULL DEFAULT NULL COMMENT '经管审核状态 1未审批 2 已同意 3 已拒绝',
  `createUid` varchar(255) NULL DEFAULT NULL COMMENT '提交申请人',
  `createName` varchar(255) NULL DEFAULT NULL COMMENT '提交申请人姓名',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '申请提交时间',
  `hrName` varchar(255) NULL DEFAULT NULL COMMENT '人力审批人姓名',
  `jgName` varchar(255) NULL DEFAULT NULL COMMENT '经管审批人姓名',
  `hrTime` datetime(0) NULL DEFAULT NULL COMMENT '人力审批时间',
  `jgTime` datetime(0) NULL DEFAULT NULL COMMENT '经管审批时间',
  `timeLast` datetime(0) NULL DEFAULT NULL COMMENT '上一次成立时间',
  `timeNow` datetime(0) NULL DEFAULT NULL COMMENT '申请成立时间',
  `planned_start_date` date NULL DEFAULT NULL COMMENT '本次合同开工时间',
  `planned_end_date` date NULL DEFAULT NULL COMMENT '本次合同竣工时间',
  `planned_start_date_last` date NULL DEFAULT NULL COMMENT '上次合同开工时间',
  `planned_end_date_last` date NULL DEFAULT NULL COMMENT '上次合同竣工时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_applyfor_proconfig
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_att_config
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_att_config`;
CREATE TABLE `dx_money_att_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `vid` int(11) NULL DEFAULT NULL COMMENT '假期id',
  `att_day` double NULL DEFAULT NULL COMMENT '考勤天数',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_att_config
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_att_vacation
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_att_vacation`;
CREATE TABLE `dx_money_att_vacation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `att_name` varchar(30) NULL DEFAULT NULL COMMENT '出勤状况',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '1 假期类型 0其他',
  `sort` tinyint(4) NULL DEFAULT NULL COMMENT '排序',
  `office_fid` int(11) NULL DEFAULT NULL COMMENT '图片id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_att_vacation
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_att_vacation_default
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_att_vacation_default`;
CREATE TABLE `dx_money_att_vacation_default`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `vid` int(11) NULL DEFAULT NULL COMMENT '分类',
  `att_day` double NULL DEFAULT NULL COMMENT '出勤率',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_att_vacation_default
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_attendance
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_attendance`;
CREATE TABLE `dx_money_attendance`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(255) NOT NULL COMMENT '用户id',
  `attendance_year` varchar(10) NOT NULL COMMENT '出勤月份',
  `proid` smallint(6) NOT NULL COMMENT '项目部id',
  `absenteeism_day` text NULL COMMENT '未出勤时间',
  `absenteeism_cause` text NULL COMMENT '未出勤原因',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `create_uid` varchar(255) NULL DEFAULT NULL COMMENT '操作人员',
  `att_day` tinyint(4) NULL DEFAULT NULL COMMENT '出勤天数',
  `registrationId` varchar(255) NULL DEFAULT NULL COMMENT ' 考勤人员的设备码',
  `isTrue` varchar(255) NULL DEFAULT '0' COMMENT '是否是由办公室人员确认的考勤信息 如果确认不能修改1确认  0未确认',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_attendance
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_attendance_details
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_attendance_details`;
CREATE TABLE `dx_money_attendance_details`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(255) NULL DEFAULT NULL COMMENT '用户id',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部',
  `year_quarter` varchar(255) NULL DEFAULT NULL COMMENT 'yyyy-MM-dd 打卡时间',
  `att_type` tinyint(4) NULL DEFAULT NULL COMMENT '打卡类别14种',
  `wifiName` varchar(255) NULL DEFAULT NULL COMMENT 'wifi名称',
  `addressName` varchar(255) NULL DEFAULT NULL COMMENT '坐标名称',
  `att_tim` datetime(0) NULL DEFAULT NULL COMMENT '打卡时间',
  `groupName` varchar(255) NULL DEFAULT NULL COMMENT '考勤组名称',
  `remark_qr` varchar(255) NULL DEFAULT NULL COMMENT '确认备注',
  `att_type_qr` tinyint(4) NULL DEFAULT NULL COMMENT '确认考勤信息',
  `fid` int(11) NULL DEFAULT NULL COMMENT '打卡成功照片',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_attendance_details
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_deductions
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_deductions`;
CREATE TABLE `dx_money_deductions`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `user_id` varchar(255) NULL DEFAULT NULL COMMENT '用户id',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '人员类别',
  `base_endowment` double NULL DEFAULT NULL COMMENT '基本养老保险',
  `housing_fund` double NULL DEFAULT NULL COMMENT '住房公积金',
  `firm_liveout` double NULL DEFAULT NULL COMMENT '补充养老金（企业年金）',
  `medical_insurance` double NULL DEFAULT NULL COMMENT '基本医疗保险',
  `labor_money` double NULL DEFAULT NULL COMMENT '工会会费',
  `mutual_aid_money` double NULL DEFAULT NULL COMMENT '互助金',
  `special_deduct` double NULL DEFAULT NULL COMMENT '专项扣除',
  `income_tax` double NULL DEFAULT NULL COMMENT '个人所得税当月扣除',
  `board_wages` double NULL DEFAULT NULL COMMENT '伙食费',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `year_quarter` varchar(20) NULL DEFAULT NULL COMMENT '发工资月份',
  `isShowbtn` int(11) NULL DEFAULT 0 COMMENT '是否显示btn',
  `income_tax_grand_total` double NULL DEFAULT NULL COMMENT '个人所得税累计扣除',
  `business_insurance` double NULL DEFAULT NULL COMMENT '失业保险',
  `large_medical_subsidies` double NULL DEFAULT NULL COMMENT '大额医疗补助',
  `pay_social_security` double NULL DEFAULT 0 COMMENT '补交社保',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_deductions
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_deductions_payment
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_deductions_payment`;
CREATE TABLE `dx_money_deductions_payment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目id',
  `user_id` varchar(255) NULL DEFAULT NULL COMMENT '用户id',
  `type` int(11) NULL DEFAULT NULL COMMENT '人员类别',
  `base_endowment` double NULL DEFAULT NULL COMMENT '基本养老保险',
  `housing_fund` double NULL DEFAULT NULL COMMENT '住房公积金',
  `firm_liveout` double NULL DEFAULT NULL COMMENT '企业年金',
  `medical_insurance` double NULL DEFAULT NULL COMMENT '基本医疗保险',
  `labor_money` double NULL DEFAULT NULL COMMENT '工会会费',
  `mutual_aid_money` double NULL DEFAULT NULL COMMENT '互助金',
  `business_insurance` double NULL DEFAULT NULL COMMENT '失业保险',
  `large_medical_subsidies` double NULL DEFAULT NULL COMMENT '大额医疗补助',
  `pay_social_security` double NULL DEFAULT NULL COMMENT '补交社保',
  `check_date` double NULL DEFAULT NULL COMMENT '考勤天数',
  `year_quarter` varchar(255) NULL DEFAULT NULL COMMENT '调整月份',
  `create_time` date NULL DEFAULT NULL COMMENT '创建时间',
  `isShowbtn` int(11) NULL DEFAULT NULL COMMENT '是否显示btn',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_deductions_payment
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_excel
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_excel`;
CREATE TABLE `dx_money_excel`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sheet` varchar(255) NULL DEFAULT NULL COMMENT 'sheet页',
  `rowandcell` varchar(255) NULL DEFAULT NULL COMMENT '行',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后操作时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_excel
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_facecode
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_facecode`;
CREATE TABLE `dx_money_facecode`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone` varchar(255) NULL DEFAULT NULL COMMENT '手机号',
  `code` text NULL COMMENT '特征码',
  `fid` int(11) NULL DEFAULT NULL COMMENT '图片id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_facecode
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_facecode_reissue
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_facecode_reissue`;
CREATE TABLE `dx_money_facecode_reissue`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(255) NULL DEFAULT NULL COMMENT '用户id',
  `phone` varchar(255) NULL DEFAULT NULL COMMENT '手机号',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `old_fid` int(11) NULL DEFAULT NULL COMMENT '之前的人像图片id',
  `new_fid` int(11) NULL DEFAULT NULL COMMENT '新的人像图片id',
  `code` text NULL COMMENT '人脸识别码',
  `status` int(11) NULL DEFAULT NULL COMMENT '审批状态  0 未审批 1已通过 2 已拒绝',
  `create_name` varchar(255) NULL DEFAULT NULL COMMENT '申请人姓名',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '申请时间',
  `approver_id` varchar(255) NULL DEFAULT NULL COMMENT '审批人id',
  `approver_name` varchar(255) NULL DEFAULT NULL COMMENT '审批人姓名',
  `approver_time` datetime(0) NULL DEFAULT NULL COMMENT '审批时间',
  `remark` varchar(5000) NULL DEFAULT NULL COMMENT '审批理由  （同意理由，拒绝理由。）',
  `timeKey` datetime(0) NULL DEFAULT NULL COMMENT '检索时间key',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_facecode_reissue
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_membership_config
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_membership_config`;
CREATE TABLE `dx_money_membership_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '业务名称（党费收缴）',
  `start_time` varchar(255) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` varchar(255) NULL DEFAULT NULL COMMENT '结束时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(500) NULL DEFAULT NULL COMMENT '创建人姓名',
  `create_id` varchar(500) NULL DEFAULT NULL COMMENT '创建者id',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '1 未收缴  2 已收缴完成',
  `remark` text NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci;

-- ----------------------------
-- Records of dx_money_membership_config
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_membership_sheet
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_membership_sheet`;
CREATE TABLE `dx_money_membership_sheet`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` varchar(255) NOT NULL COMMENT '用户id',
  `proid` int(11) NOT NULL COMMENT '项目部id',
  `year_quarter` varchar(255) NOT NULL COMMENT '时间',
  `work_money` double NULL DEFAULT NULL COMMENT '岗位工资',
  `year_money` double NULL DEFAULT NULL COMMENT '年功工资',
  `skill_money` double NULL DEFAULT NULL COMMENT '技术津贴',
  `technical_money` double NULL DEFAULT NULL COMMENT '技能津贴',
  `output_money` double NULL DEFAULT NULL COMMENT '安全产值工资',
  `five_insurance_gold` double NULL DEFAULT NULL COMMENT '五险二金',
  `individual_income_tax` double NULL DEFAULT NULL COMMENT '个税',
  `trade_union_dues` double NULL DEFAULT NULL COMMENT '工会会费',
  `mutual_aid_money` double NULL DEFAULT NULL COMMENT '互助金',
  `should_money` double NULL DEFAULT NULL COMMENT '应缴纳',
  `final_money` double NULL DEFAULT NULL COMMENT '实际缴纳',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_id` varchar(500) NULL DEFAULT NULL COMMENT '用户id',
  `create_name` varchar(255) NULL DEFAULT NULL COMMENT '创建姓名',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `status` int(11) NULL DEFAULT 1 COMMENT '状态  1.待上报 2已上报 3已确认',
  `fid` int(11) NULL DEFAULT NULL COMMENT '上报人电子签名id',
  `unit_fid` int(11) NULL DEFAULT NULL COMMENT '确认人电子签名',
  `app_time` date NULL DEFAULT NULL COMMENT '确认时间',
  `app_user` varchar(255) NULL DEFAULT NULL COMMENT '确认人',
  `app_id` varchar(255) NULL DEFAULT NULL COMMENT '确认id',
  `app_remark` text NULL COMMENT '上报备注',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_membership_sheet
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_other_output
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_other_output`;
CREATE TABLE `dx_money_other_output`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `year_quarter` varchar(255) NULL DEFAULT NULL COMMENT '期号',
  `last_money` double NULL DEFAULT NULL COMMENT '上期开累',
  `now_money` double NULL DEFAULT NULL COMMENT '本期',
  `all` double NULL DEFAULT NULL COMMENT '开累',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) NULL DEFAULT NULL COMMENT '修改人',
  `create_uid` varchar(255) NULL DEFAULT NULL COMMENT '修改人id',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_other_output
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_output
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_output`;
CREATE TABLE `dx_money_output`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `user_id` varchar(255) NULL DEFAULT NULL COMMENT '用户id',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '人员类别 1，正式工 2，合同工，3，下属 4，临时',
  `out_money_now` double NULL DEFAULT NULL COMMENT '当月产值',
  `sum` double NULL DEFAULT NULL COMMENT '小计',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `year_quarter` varchar(20) NULL DEFAULT NULL COMMENT '发工资月份',
  `work_id` int(11) NULL DEFAULT NULL COMMENT '岗位系数id',
  `isShowbtn` int(11) NULL DEFAULT 0 COMMENT '是否显示btn',
  `check_score` double NULL DEFAULT NULL COMMENT '考核的分',
  `check_date` double NULL DEFAULT NULL COMMENT '出勤天数',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_output
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_output_open
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_output_open`;
CREATE TABLE `dx_money_output_open`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `year_quarter` varchar(20) NULL DEFAULT NULL COMMENT '配置时间',
  `practical` double NULL DEFAULT NULL COMMENT '实际产值',
  `advance_production` double NULL DEFAULT NULL COMMENT '预支产值',
  `sum` double NULL DEFAULT NULL COMMENT '小计',
  `complete_the_proportion` double NULL DEFAULT NULL COMMENT '完成比例',
  `open_base_statistics` double NULL DEFAULT NULL COMMENT '开垒统计',
  `base_opening_statistics_surplus` double NULL DEFAULT NULL COMMENT '开垒剩余',
  `fid` int(11) NULL DEFAULT NULL COMMENT '产值文件id',
  `advance_statistics` double NULL DEFAULT NULL COMMENT '开累预支额',
  `advance_percent` double NULL DEFAULT NULL COMMENT '开累预支额百分比',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_output_open
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_phone_code
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_phone_code`;
CREATE TABLE `dx_money_phone_code`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` varchar(255) NULL DEFAULT NULL COMMENT '用户id',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `phone` varchar(255) NULL DEFAULT NULL COMMENT '手机号',
  `phone_code` varchar(255) NULL DEFAULT NULL COMMENT '设备码',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_phone_code
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_phone_config
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_phone_config`;
CREATE TABLE `dx_money_phone_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `work_time` datetime(0) NULL DEFAULT NULL COMMENT '打卡时间',
  `isTrue` varchar(255) NULL DEFAULT NULL COMMENT '是否开启打卡时间  1开启  0不开启',
  `time_scope` double NULL DEFAULT NULL COMMENT '打卡时间范围',
  `isFaceTrue` int(11) NULL DEFAULT NULL COMMENT '是否进行人脸识别',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_phone_config
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_phone_config_details
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_phone_config_details`;
CREATE TABLE `dx_money_phone_config_details`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `wifi_mac_address` varchar(255) NULL DEFAULT NULL COMMENT 'wifiMac地址',
  `wifi_name` varchar(255) NULL DEFAULT NULL COMMENT 'wifi名字',
  `address` varchar(255) NULL DEFAULT NULL COMMENT '地理位置坐标',
  `address_name` varchar(255) NULL DEFAULT NULL COMMENT '地址名称',
  `address_scope` varchar(255) NULL DEFAULT NULL COMMENT '打卡范围',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `addressLocation` text NULL COMMENT '地址',
  `groupName` varchar(255) NULL DEFAULT NULL COMMENT '考勤组名称',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_phone_config_details
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_phone_confirm
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_phone_confirm`;
CREATE TABLE `dx_money_phone_confirm`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(255) NULL DEFAULT NULL COMMENT '用户id',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `oldType` int(11) NULL DEFAULT NULL COMMENT '没更改之前的考勤类别信息',
  `newType` int(11) NULL DEFAULT NULL COMMENT '修改之后的考勤类别信息',
  `year_quarter` varchar(255) NULL DEFAULT NULL COMMENT '考勤时间',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '更改原因',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `colck_time` datetime(0) NULL DEFAULT NULL COMMENT '打卡时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_phone_confirm
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_phone_reissuecard
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_phone_reissuecard`;
CREATE TABLE `dx_money_phone_reissuecard`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(255) NULL DEFAULT NULL COMMENT '用户id',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '补卡原因',
  `reiss_time` datetime(0) NULL DEFAULT NULL COMMENT '补卡时间',
  `apply_for_time` datetime(0) NULL DEFAULT NULL COMMENT '补卡申请时间',
  `att_type` int(11) NULL DEFAULT NULL COMMENT '补卡类型',
  `over_time` datetime(0) NULL DEFAULT NULL COMMENT '回复时间',
  `update_user` varchar(255) NULL DEFAULT NULL COMMENT '操作人id',
  `status` int(11) NULL DEFAULT NULL COMMENT '补卡状态  1，未操作   2，同意  3，不同意',
  `refuse_case` varchar(255) NULL DEFAULT NULL COMMENT '拒绝原因',
  `user_name` varchar(255) NULL DEFAULT NULL COMMENT '补卡人姓名',
  `user_phone` varchar(255) NULL DEFAULT NULL COMMENT '补卡电话',
  `fid` int(11) NULL DEFAULT NULL COMMENT '图片id',
  `isTrue` varchar(255) NULL DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_phone_reissuecard
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_project_config
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_project_config`;
CREATE TABLE `dx_money_project_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `safety_score` double NULL DEFAULT NULL COMMENT '安全质量评比得分',
  `people_size` int(11) NULL DEFAULT NULL COMMENT '人数',
  `contrace_month` tinyint(255) NULL DEFAULT NULL COMMENT '工期月份',
  `contrace_money` double NULL DEFAULT NULL COMMENT '合同总额',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '配置时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_project_config
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_reissue_money
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_reissue_money`;
CREATE TABLE `dx_money_reissue_money`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(255) NULL DEFAULT NULL COMMENT '用户id',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `year_quarter` varchar(255) NULL DEFAULT NULL COMMENT 'yyyy-MM-dd 补发的月份',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) NULL DEFAULT NULL COMMENT '创建的用户',
  `examine_user` varchar(255) NULL DEFAULT NULL COMMENT '审批人',
  `status` int(11) NULL DEFAULT 0 COMMENT '1 未审批  2 已通过  3 已拒绝',
  `technical_allowance` double NULL DEFAULT 0 COMMENT '技术津贴',
  `skill_sllowance` double NULL DEFAULT 0 COMMENT '技能津贴',
  `woman_money` double NULL DEFAULT 0 COMMENT '女工卫生费',
  `flow_allowance` double NULL DEFAULT 0 COMMENT '浮动津贴',
  `tunnel_allowance` double NULL DEFAULT 0 COMMENT '隧道津贴',
  `sand_subsidies` double NULL DEFAULT 0 COMMENT '风沙补贴',
  `plateau_subsidies` double NULL DEFAULT 0 COMMENT '高原津贴',
  `registrar_money` double NULL DEFAULT 0 COMMENT '注册师津贴',
  `part_time_allowance` double NULL DEFAULT 0 COMMENT '兼职津贴',
  `materials` double NULL DEFAULT 0 COMMENT '资料费',
  `phone_allowance` double NULL DEFAULT 0 COMMENT '通讯补贴',
  `meal_subsidy` double NULL DEFAULT 0 COMMENT '伙食补贴',
  `single_child_fee` double NULL DEFAULT 0 COMMENT '独身子女费',
  `overtime_wage` double NULL DEFAULT 0 COMMENT '加班工资',
  `base_allmoney` double NULL DEFAULT 0 COMMENT '固定工资补发总额',
  `pay_other_wages` double NULL DEFAULT 0 COMMENT '补发其他工资',
  `output_money` double NULL DEFAULT 0 COMMENT '补发产值工资',
  `pay_all` double NULL DEFAULT 0 COMMENT '补发工资总额',
  `remark` text NULL COMMENT '补发工资明细  备注',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_reissue_money
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_sheet
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_sheet`;
CREATE TABLE `dx_money_sheet`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(255) NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(20) NULL DEFAULT NULL COMMENT '姓名',
  `proid` smallint(11) NULL DEFAULT NULL COMMENT '项目部id',
  `work_id` int(11) NULL DEFAULT NULL COMMENT '岗位系数id',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '人员类别 1、正式工 2、合同工 3、乾泰 4、临时工 5 准合同工 大专实习生',
  `send_time` date NULL DEFAULT NULL COMMENT '发工资时间 ',
  `year_quarter` varchar(30) NULL DEFAULT NULL COMMENT '发工资月份',
  `check_date` double NULL DEFAULT NULL COMMENT '考勤天数',
  `check_score` double NULL DEFAULT 0 COMMENT '考核得分',
  `work_money` double NULL DEFAULT 0 COMMENT '岗位工资',
  `year_money` double NULL DEFAULT 0 COMMENT '工龄工资',
  `registered_money` double NULL DEFAULT 0 COMMENT '注册师津贴',
  `flow_money` double NULL DEFAULT 0 COMMENT '流动津贴',
  `partjob_money` double NULL DEFAULT 0 COMMENT '兼职津贴',
  `data_money` double NULL DEFAULT 0 COMMENT '资料费',
  `phone_money` double NULL DEFAULT 0 COMMENT '电话费',
  `woman_money` double NULL DEFAULT 0 COMMENT '女工卫生费',
  `food_allowance` double NULL DEFAULT 0 COMMENT '伙食补助',
  `fixed_wage_money` double NULL DEFAULT 0 COMMENT '固定工资总额',
  `output_score` double NULL DEFAULT 0 COMMENT '产值工资岗位数',
  `output_money` double NULL DEFAULT 0 COMMENT '产值工资总额',
  `output_shar_score` double NULL DEFAULT 0 COMMENT '产值分配份额',
  `output_money_now` double NULL DEFAULT 0 COMMENT '本月产值安全质量工资',
  `legal_holiday_money` double NULL DEFAULT 0 COMMENT '法定节假日工资',
  `reissue_money` double NULL DEFAULT 0 COMMENT '补发工资',
  `should_money` double NULL DEFAULT 0 COMMENT '应发工资合计',
  `endowment_insurance` double NULL DEFAULT 0 COMMENT '养老保险',
  `housing_fund` double NULL DEFAULT 0 COMMENT '住房公积金',
  `firm_liveout` double NULL DEFAULT 0 COMMENT '企业补充养老保险',
  `medical_insurance` double NULL DEFAULT 0 COMMENT '基本医疗保险',
  `big_medical` double NULL DEFAULT 0 COMMENT '大额医疗补助',
  `unemployment` double NULL DEFAULT 0 COMMENT '失业保险',
  `labor_money` double NULL DEFAULT 0 COMMENT '工会会费',
  `mutual_aid_money` double NULL DEFAULT 0 COMMENT '互助金',
  `children_money` double NULL DEFAULT 0 COMMENT '子女教育',
  `support_the_old` double NULL DEFAULT 0 COMMENT '赡养老人',
  `hours_loans` double NULL DEFAULT 0 COMMENT '住房贷款',
  `study_money` double NULL DEFAULT 0 COMMENT '连续教育',
  `board_wages` double NULL DEFAULT 0 COMMENT '扣除伙食费',
  `income_tax` double NULL DEFAULT 0 COMMENT '个人所得税',
  `final_money` double NULL DEFAULT 0 COMMENT '最终实发工资',
  `assessment_of_wages` double NULL DEFAULT 0 COMMENT '考核工资',
  `deductions_combined` double NULL DEFAULT 0 COMMENT '扣款合计',
  `special_money` double NULL DEFAULT 0 COMMENT '特殊津贴',
  `isShowbtn` int(11) NULL DEFAULT 0 COMMENT '是否显示按钮',
  `one_time_reward` double NULL DEFAULT NULL COMMENT '一次性奖励',
  `deductions_combined_ment` double NULL DEFAULT NULL COMMENT '社保调整',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_sheet
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_sheet_base
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_sheet_base`;
CREATE TABLE `dx_money_sheet_base`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(255) NULL DEFAULT NULL COMMENT '用户id',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `work_id` int(11) NULL DEFAULT NULL COMMENT '岗位系数小id',
  `year_money` double NULL DEFAULT 0 COMMENT '年功工资',
  `registered_money` double NULL DEFAULT 0 COMMENT '注册师津贴',
  `partjob_money` double NULL DEFAULT 0 COMMENT '兼职津贴',
  `flow_money` double NULL DEFAULT 0 COMMENT '流动津贴',
  `legal_holiday_money` double NULL DEFAULT 0 COMMENT '节假日补贴',
  `woman_money` double NULL DEFAULT 0 COMMENT '女工卫生费',
  `tunnal_allowance` double NULL DEFAULT 0 COMMENT '隧道补贴',
  `data_money` double NULL DEFAULT 0 COMMENT '资料费',
  `phone_money` double NULL DEFAULT 0 COMMENT '电话费',
  `food_allowance` double NULL DEFAULT 0 COMMENT '伙食补贴',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `year_quarter` varchar(20) NULL DEFAULT NULL COMMENT '发工资月份',
  `subtotal_money` double NULL DEFAULT 0 COMMENT '小计',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '人员类别',
  `work_money` double NULL DEFAULT 0 COMMENT '岗位工资',
  `work_name` varchar(20) NULL DEFAULT NULL COMMENT '岗位名称',
  `user_name` varchar(20) NULL DEFAULT NULL COMMENT '人名',
  `join_time` date NULL DEFAULT NULL COMMENT '加入公司时间',
  `isShowbtn` int(11) NULL DEFAULT 0 COMMENT '是否显示按钮',
  `plateau_allowance` double NULL DEFAULT NULL COMMENT '高原补贴',
  `sand_allowance` double NULL DEFAULT NULL COMMENT '风沙',
  `skillMoney` double NULL DEFAULT NULL COMMENT '技术津贴',
  `technicalMoney` double NULL DEFAULT NULL COMMENT '技能津贴',
  `onlyChild` double NULL DEFAULT NULL COMMENT '独生子女费',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_sheet_base
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_social_sercurity
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_social_sercurity`;
CREATE TABLE `dx_money_social_sercurity`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(255) NULL DEFAULT NULL COMMENT '用户姓名',
  `user_id` varchar(255) NULL DEFAULT NULL COMMENT '用户id',
  `id_card` varchar(255) NULL DEFAULT NULL COMMENT '身份证号',
  `social_time` date NULL DEFAULT NULL COMMENT '参保时间',
  `year_quarter` varchar(255) NULL DEFAULT NULL COMMENT '工资时间',
  `money` double NULL DEFAULT NULL COMMENT '月均工资',
  `basic_pension` double NULL DEFAULT 0 COMMENT '基本养老金',
  `medical_insurance` double NULL DEFAULT 0 COMMENT '基本医疗保险',
  `house_money` double NULL DEFAULT 0 COMMENT '住房公积金',
  `business_insurance` double NULL DEFAULT 0 COMMENT '失业保险',
  `supplementary` double NULL DEFAULT 0 COMMENT '企业年金',
  `trade_union_dues` double NULL DEFAULT 0 COMMENT '工会会费',
  `mutual_aid_money` double NULL DEFAULT 0 COMMENT '工会互助金',
  `large_medical_subsidies` double NULL DEFAULT 0 COMMENT '大额医疗补助保险',
  `special_deduction` double NULL DEFAULT 0 COMMENT '专项扣除',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `create_user` varchar(255) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `type` int(11) NULL DEFAULT NULL COMMENT '参保地 1 山西 2 陕西 ',
  `endowment_insurance_number` varchar(255) NULL DEFAULT NULL COMMENT '养老保险号',
  `isTrue` int(11) NULL DEFAULT NULL COMMENT '1参保  2不参保',
  `all_money` double NULL DEFAULT NULL COMMENT '工资总额山西 除了基本医疗',
  `medical_all_money` double NULL DEFAULT NULL COMMENT '工资总额陕西 基本医疗',
  `medical_money` double NULL DEFAULT NULL COMMENT '月均工资（基本医疗）',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_social_sercurity
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_social_sercurity_config
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_social_sercurity_config`;
CREATE TABLE `dx_money_social_sercurity_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `max_money` double NULL DEFAULT 0 COMMENT '上行工资',
  `min_money` double NULL DEFAULT 0 COMMENT '下行工资',
  `create_username` varchar(255) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` date NULL DEFAULT NULL COMMENT '创建时间',
  `year_quarter` varchar(255) NULL DEFAULT NULL COMMENT '年份信息',
  `type` int(11) NULL DEFAULT NULL COMMENT '参保地类型 1山西 2 陕西',
  `medical_max` double NULL DEFAULT 0 COMMENT '上行（基本工资基数）',
  `medical_min` double NULL DEFAULT 0 COMMENT '下行（医疗工资基数）',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_social_sercurity_config
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_temporary_user
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_temporary_user`;
CREATE TABLE `dx_money_temporary_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` varchar(70) NULL DEFAULT NULL COMMENT '人员id',
  `proId` smallint(6) NULL DEFAULT NULL COMMENT '项目部id',
  `id_card` varchar(55) NULL DEFAULT NULL COMMENT '身份证号码',
  `work_money` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '工资标准',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态：1当前数据 2往期数据 3历史数据',
  `last_date` date NULL DEFAULT NULL COMMENT '截至日期',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_temporary_user
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_three_date
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_three_date`;
CREATE TABLE `dx_money_three_date`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `year_quarter` varchar(255) NULL DEFAULT NULL COMMENT '时间  yyyy-mm',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `output_money` double NULL DEFAULT 0 COMMENT '当期产值',
  `peoplesize` double NULL DEFAULT 0 COMMENT '当期人数',
  `finance_money` double NULL DEFAULT 0 COMMENT '工资总额',
  `management_fee` double NULL DEFAULT 0 COMMENT '本级管理费',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_three_date
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_unit_outputconfig
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_unit_outputconfig`;
CREATE TABLE `dx_money_unit_outputconfig`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键递增',
  `configJson` text NULL COMMENT '预支产值分配百分率json',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `handle_userId` int(11) NULL DEFAULT NULL COMMENT '操作者id',
  `handle_userName` varchar(20) NULL DEFAULT NULL COMMENT '操作者名称',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_unit_outputconfig
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_user
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_user`;
CREATE TABLE `dx_money_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` varchar(255) NOT NULL COMMENT '用户id',
  `join_time` date NULL DEFAULT NULL COMMENT '入职时间',
  `work_id` smallint(6) NULL DEFAULT NULL COMMENT '岗位系数id',
  `is_help_money` tinyint(4) NULL DEFAULT NULL COMMENT '是否有互助金',
  `is_labor_money` tinyint(4) NULL DEFAULT NULL COMMENT '是否有会费',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `technical_id` int(11) NULL DEFAULT NULL COMMENT '职称id',
  `skill_id` int(11) NULL DEFAULT NULL COMMENT '技能id',
  `idCard` varchar(30) NULL DEFAULT NULL COMMENT '身份证号',
  `workscore` double NULL DEFAULT NULL COMMENT 'abc产值系数',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '人员类别 1、正式工 2、合同工 3、乾泰 4、临时工 5 准合同工 6顶岗实习生',
  `work_type` varchar(255) NULL DEFAULT NULL COMMENT '作业类型  1隧道 2 风沙 3 高原',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态：1当前数据 2往期数据 3历史数据',
  `last_date` date NULL DEFAULT NULL COMMENT '截至日期',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_user
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_user_title
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_user_title`;
CREATE TABLE `dx_money_user_title`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title_name` varchar(20) NOT NULL COMMENT '补助名称',
  `title_money` double NULL DEFAULT NULL COMMENT '补助金额',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '补助类型  1 职称津贴   2技能津贴  ',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_user_title
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_work
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_work`;
CREATE TABLE `dx_money_work`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `work_name` varchar(20) NULL DEFAULT NULL COMMENT '岗位名称',
  `information_money` double NULL DEFAULT NULL COMMENT '资料费',
  `remark` varchar(20) NULL DEFAULT NULL COMMENT '备注',
  `type` tinyint(255) NULL DEFAULT NULL COMMENT '类型',
  `Technical_allowance` double NULL DEFAULT NULL COMMENT '技术津贴',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_work
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_work_salary
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_work_salary`;
CREATE TABLE `dx_money_work_salary`  (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `work_id` smallint(6) NULL DEFAULT NULL COMMENT '岗位id',
  `part_allo` double NULL DEFAULT NULL COMMENT '兼职津贴',
  `flow_allo` double NULL DEFAULT NULL COMMENT '流动津贴',
  `holiday_allo` double NULL DEFAULT NULL COMMENT '节假日补贴',
  `special_allo` double NULL DEFAULT NULL COMMENT '特殊津贴',
  `tunnel_allo` double NULL DEFAULT NULL COMMENT '隧道津贴',
  `sandwind_allo` double NULL DEFAULT NULL COMMENT '风沙津贴',
  `plateau_allo` double NULL DEFAULT NULL COMMENT '高原津贴',
  `material_allo` double NULL DEFAULT NULL COMMENT '资料费',
  `phone_allo` double NULL DEFAULT NULL COMMENT '电话费',
  `food_allo` double NULL DEFAULT NULL COMMENT '伙食补贴',
  `basic_pension` double NULL DEFAULT NULL COMMENT '基本养老金',
  `suppl_pension` double NULL DEFAULT NULL COMMENT '补充养老保险',
  `housing_fund` double NULL DEFAULT NULL COMMENT '住房公积金',
  `medical_insurance` double NULL DEFAULT NULL COMMENT '医疗保险',
  `union_dues` double NULL DEFAULT NULL COMMENT '工会会费',
  `mutual_fund` double NULL DEFAULT NULL COMMENT '互助金',
  `food_expenses` double NULL DEFAULT NULL COMMENT '伙食费',
  `personal_tax` double NULL DEFAULT NULL COMMENT '个人所得税',
  `special_deduction` double NULL DEFAULT NULL COMMENT '专项扣除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_work_salary
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_work_score
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_work_score`;
CREATE TABLE `dx_money_work_score`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `work_id` int(11) NOT NULL COMMENT '岗位id',
  `work_money` double NULL DEFAULT NULL COMMENT '岗位工资',
  `work_score_A` double NULL DEFAULT NULL COMMENT 'A级产值安全质量工资岗位系数',
  `work_score_B` double NULL DEFAULT NULL COMMENT 'B级产值安全质量工资岗位系数',
  `work_score_C` double NULL DEFAULT NULL COMMENT 'C级产值安全质量工资岗位系数',
  `remark` varchar(20) NULL DEFAULT NULL COMMENT '备注',
  `level` varchar(10) NULL DEFAULT NULL COMMENT '级别',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_work_score
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_work_score_new
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_work_score_new`;
CREATE TABLE `dx_money_work_score_new`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父类id',
  `level_Name` varchar(255) NULL DEFAULT '一级' COMMENT '级别名称  一级 二级 三级',
  `work_Money` double NULL DEFAULT NULL COMMENT '岗位工资',
  `score_Name` varchar(255) NULL DEFAULT NULL COMMENT '系数名称 ABC',
  `score` double NULL DEFAULT NULL COMMENT '系数值  1.5 1.6',
  `order_By` int(1) NULL DEFAULT NULL COMMENT '排序字段',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_work_score_new
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_work_user_unitl
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_work_user_unitl`;
CREATE TABLE `dx_money_work_user_unitl`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(255) NULL DEFAULT NULL COMMENT '用户id',
  `join_time` date NULL DEFAULT NULL COMMENT '入职时间',
  `work_id` int(11) NULL DEFAULT NULL COMMENT '岗位系数id',
  `technical_id` int(11) NULL DEFAULT NULL COMMENT '职称id',
  `skill_id` int(11) NULL DEFAULT NULL COMMENT '技能id',
  `idCard` varchar(50) NULL DEFAULT NULL COMMENT '身份证号',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '人员身份',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态：1当前数据 2往期数据 3历史数据',
  `last_date` date NULL DEFAULT NULL COMMENT '截至日期',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_work_user_unitl
-- ----------------------------

-- ----------------------------
-- Table structure for dx_money_workunitl
-- ----------------------------
DROP TABLE IF EXISTS `dx_money_workunitl`;
CREATE TABLE `dx_money_workunitl`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `work_name` varchar(20) NULL DEFAULT NULL COMMENT '岗位名称',
  `work_money` double NULL DEFAULT NULL COMMENT '岗位工资',
  `Safemass_money` double NULL DEFAULT NULL COMMENT '安全质量岗位工资',
  `work_score` double NULL DEFAULT NULL COMMENT '岗位系数',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_money_workunitl
-- ----------------------------

-- ----------------------------
-- Table structure for dx_office_configure
-- ----------------------------
DROP TABLE IF EXISTS `dx_office_configure`;
CREATE TABLE `dx_office_configure`  (
  `id` int(11) NOT NULL COMMENT '主键id',
  `proid` smallint(6) NOT NULL COMMENT '项目id',
  `departid` smallint(6) NULL DEFAULT NULL COMMENT '部门id',
  `leader_uid` varchar(3000) NULL DEFAULT NULL COMMENT '分管领导id',
  `opt_uid` varchar(70) NULL DEFAULT NULL COMMENT '操作人员id',
  `opt_name` varchar(70) NULL DEFAULT NULL COMMENT '操作人姓名',
  `opt_date` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_office_configure
-- ----------------------------

-- ----------------------------
-- Table structure for dx_office_homepage_log
-- ----------------------------
DROP TABLE IF EXISTS `dx_office_homepage_log`;
CREATE TABLE `dx_office_homepage_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projects_data` text NULL,
  `output_value_data` text NULL,
  `recent_data` text NULL,
  `date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_office_homepage_log
-- ----------------------------

-- ----------------------------
-- Table structure for dx_office_organization_configure
-- ----------------------------
DROP TABLE IF EXISTS `dx_office_organization_configure`;
CREATE TABLE `dx_office_organization_configure`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `proid` smallint(6) NULL DEFAULT NULL COMMENT '项目id',
  `departid` smallint(6) NULL DEFAULT NULL COMMENT '部门id',
  `leader_uid` varchar(255) NULL DEFAULT NULL COMMENT '分管领导id',
  `opt_uid` varchar(70) NULL DEFAULT NULL COMMENT '操作人员id',
  `opt_name` varchar(70) NULL DEFAULT NULL COMMENT '操作人姓名',
  `opt_date` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `is_del` tinyint(4) NULL DEFAULT NULL COMMENT '是否删除 0否 1是',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_office_organization_configure
-- ----------------------------

-- ----------------------------
-- Table structure for dx_office_output_value
-- ----------------------------
DROP TABLE IF EXISTS `dx_office_output_value`;
CREATE TABLE `dx_office_output_value`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `year` varchar(25) NULL DEFAULT NULL COMMENT '年',
  `jan_value` varchar(55) NULL DEFAULT NULL COMMENT '1月数据',
  `feb_value` varchar(55) NULL DEFAULT NULL COMMENT '2月数据',
  `mar_value` varchar(55) NULL DEFAULT NULL COMMENT '3月数据',
  `apr_value` varchar(55) NULL DEFAULT NULL COMMENT '4月数据',
  `may_value` varchar(55) NULL DEFAULT NULL COMMENT '5月数据',
  `jun_value` varchar(55) NULL DEFAULT NULL COMMENT '6月数据',
  `jul_value` varchar(55) NULL DEFAULT NULL COMMENT '7月数据',
  `aug_value` varchar(55) NULL DEFAULT NULL COMMENT '8月数据',
  `sep_value` varchar(55) NULL DEFAULT NULL COMMENT '9月数据',
  `oct_value` varchar(55) NULL DEFAULT NULL COMMENT '10月数据',
  `nov_value` varchar(55) NULL DEFAULT NULL COMMENT '11月数据',
  `dec_value` varchar(55) NULL DEFAULT NULL COMMENT '12月数据',
  `standard` double NULL DEFAULT NULL COMMENT '当年产值标准',
  `opt_uid` varchar(70) NULL DEFAULT NULL COMMENT '操作人员uid',
  `opt_user` varchar(70) NULL DEFAULT NULL COMMENT '操作人员',
  `opt_date` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_office_output_value
-- ----------------------------

-- ----------------------------
-- Table structure for dx_office_sqtable
-- ----------------------------
DROP TABLE IF EXISTS `dx_office_sqtable`;
CREATE TABLE `dx_office_sqtable`  (
  `id` varchar(30) NOT NULL COMMENT '主键id',
  `sq_name` varchar(255) NULL DEFAULT NULL COMMENT '申请人',
  `sq_date` datetime(4) NULL DEFAULT NULL COMMENT '用车时间',
  `sq_type` varchar(255) NULL DEFAULT NULL COMMENT '用车类型（往返，单程）',
  `backtime` datetime(4) NULL DEFAULT NULL COMMENT '归还时间',
  `use_length` varchar(255) NULL DEFAULT NULL COMMENT '使用时长',
  `reason` varchar(255) NULL DEFAULT NULL COMMENT '用车事由',
  `state` varchar(255) NULL DEFAULT NULL COMMENT '申请表状态',
  `leader_id` varchar(255) NULL DEFAULT NULL COMMENT '部门领导id',
  `opinion` varchar(255) NULL DEFAULT NULL COMMENT '审批意见（1同意 2拒绝）',
  `take_id` varchar(255) NULL DEFAULT NULL COMMENT '被搭乘id',
  `number` int(255) NULL DEFAULT NULL COMMENT '乘车人数',
  `destination` varchar(255) NULL DEFAULT NULL COMMENT '目的地',
  `driver_id` varchar(255) NULL DEFAULT NULL COMMENT '司机id',
  `passenger` varchar(255) NULL DEFAULT NULL COMMENT '乘车人',
  `vehicle_id` varchar(255) NULL DEFAULT NULL COMMENT '车辆id',
  `car_num` varchar(255) NULL DEFAULT NULL COMMENT '车牌号',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_office_sqtable
-- ----------------------------

-- ----------------------------
-- Table structure for dx_office_vehicle
-- ----------------------------
DROP TABLE IF EXISTS `dx_office_vehicle`;
CREATE TABLE `dx_office_vehicle`  (
  `id` varchar(50) NOT NULL,
  `management_number` varchar(50) NULL DEFAULT NULL COMMENT '管理号码',
  `device_name` varchar(255) NULL DEFAULT NULL COMMENT '设备名称',
  `specification` varchar(255) NULL DEFAULT NULL COMMENT '规格型号',
  `mainspecification` varchar(255) NULL DEFAULT NULL COMMENT '主要规格',
  `device_cost` varchar(11) NULL DEFAULT NULL COMMENT '设备原值（购买价格）',
  `carNumber` varchar(255) NULL DEFAULT NULL COMMENT '车牌号',
  `proid` varchar(10) NULL DEFAULT NULL COMMENT '管理单位（单位id）',
  `purchase_date` datetime(0) NULL DEFAULT NULL COMMENT '设备购置日期',
  `eqpsource` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '设备来源(新增or引用)',
  `deviceClassify` varchar(255) NULL DEFAULT NULL COMMENT '设备分类',
  `address` varchar(255) NULL DEFAULT NULL COMMENT '设备存放工点',
  `leaveCard` varchar(255) NULL DEFAULT NULL COMMENT '出厂编号',
  `existTime` datetime(6) NULL DEFAULT NULL COMMENT '退场时间',
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '设备属性（1常规设备2特种设备）',
  `zhuangtai` varchar(255) NULL DEFAULT NULL COMMENT '车辆状态',
  `belong` varchar(255) NULL DEFAULT NULL COMMENT '1.自有2.劳务3.租赁',
  `proid_name` varchar(255) NULL DEFAULT NULL COMMENT '管理单位名称',
  `driver_id` varchar(255) NULL DEFAULT NULL COMMENT '司机id',
  PRIMARY KEY (`id`),
  INDEX `carNumber`(`carNumber`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_office_vehicle
-- ----------------------------

-- ----------------------------
-- Table structure for dx_office_vocation_audit
-- ----------------------------
DROP TABLE IF EXISTS `dx_office_vocation_audit`;
CREATE TABLE `dx_office_vocation_audit`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `proid` smallint(6) NULL DEFAULT NULL COMMENT '项目id',
  `uid` varchar(70) NULL DEFAULT NULL COMMENT '申请人员id',
  `user_name` varchar(55) NULL DEFAULT NULL COMMENT '申请人员姓名',
  `apply_date` datetime(0) NULL DEFAULT NULL COMMENT '申请时间',
  `vocation_type` smallint(6) NULL DEFAULT NULL COMMENT '假期类型',
  `start_date` date NULL DEFAULT NULL COMMENT '开始时间',
  `end_date` date NULL DEFAULT NULL COMMENT '结束时间',
  `leave_days` varchar(55) NULL DEFAULT NULL COMMENT '请假时长',
  `leave_reasons` varchar(2000) NULL DEFAULT NULL COMMENT '请假事由',
  `is_arrange_work` tinyint(4) NULL DEFAULT NULL COMMENT '是否已安排工作 1是 0否',
  `is_use_car` tinyint(4) NULL DEFAULT NULL COMMENT '是否用车 1是 0否',
  `arrange_work` text NULL COMMENT '工作安排信息',
  `process_tab` tinyint(4) NULL DEFAULT NULL COMMENT '审核流程',
  `department_leader_audit` text NULL COMMENT '部门审核(json格式[{\"id\":\"\",\"phone\":\"\",\"name\":\"\",\"auditFlag\":\"\",\"auditDate\":\"\",\"signFileId\":\"\",\"comment\",\"\"}])',
  `department_leader_status` tinyint(4) NULL DEFAULT NULL COMMENT '部门审核状态 0:待审核,-1:不同意,1:同意',
  `manager_leader_date` datetime(0) NULL DEFAULT NULL COMMENT '经理审核时间',
  `manager_leader_uid` varchar(70) NULL DEFAULT NULL COMMENT '经理uid',
  `manager_leader` varchar(55) NULL DEFAULT NULL COMMENT '经理',
  `manager_leader_sign` int(11) NULL DEFAULT NULL COMMENT '经理签名',
  `manager_leader_status` tinyint(4) NULL DEFAULT NULL COMMENT '经理审核状态 0:待审核,-1:不同意,1:同意',
  `manager_leader_comment` varchar(500) NULL DEFAULT NULL COMMENT '经理意见',
  `cc_list` varchar(2000) NULL DEFAULT NULL COMMENT '抄送人的uid数组',
  `cc_name` varchar(2000) NULL DEFAULT NULL COMMENT '抄送人数组',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '-1 拒绝 0部门主管审批 1项目经理审批 2审核通过的',
  `is_delete` tinyint(4) NULL DEFAULT NULL COMMENT '是否删除 0:否 1:是',
  `is_report_work` tinyint(4) NULL DEFAULT NULL COMMENT '是否上报工作',
  `revoke_proof` varchar(3000) NULL DEFAULT NULL COMMENT '销假凭据',
  `revoke_remark` varchar(500) NULL DEFAULT NULL COMMENT '销假人',
  `revoke_date` date NULL DEFAULT NULL COMMENT '销假时间',
  `revoke_audit_info` varchar(3000) NULL DEFAULT NULL COMMENT '销假审核信息',
  `revoke_cc_list` varchar(2000) NULL DEFAULT NULL COMMENT '销假抄送人',
  `is_revoke` tinyint(4) NULL DEFAULT NULL COMMENT '销假 0:待审核 1:同意 -1:拒绝',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_office_vocation_audit
-- ----------------------------

-- ----------------------------
-- Table structure for dx_operation_config
-- ----------------------------
DROP TABLE IF EXISTS `dx_operation_config`;
CREATE TABLE `dx_operation_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `module` varchar(255) NOT NULL COMMENT '所属模块',
  `url` varchar(255) NOT NULL COMMENT 'url',
  `url_desc` varchar(255) NULL DEFAULT NULL COMMENT 'url描述',
  `need_reason` tinyint(1) NOT NULL COMMENT '需要原因',
  `require_reason` tinyint(1) NOT NULL COMMENT '是否必填原因',
  `operation_times` int(11) NULL DEFAULT NULL COMMENT '一天内可操作次数',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_operation_config
-- ----------------------------

-- ----------------------------
-- Table structure for dx_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `dx_operation_log`;
CREATE TABLE `dx_operation_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `url` varchar(255) NOT NULL COMMENT 'url',
  `url_desc` varchar(255) NULL DEFAULT NULL COMMENT '链接描述',
  `operator_id` varchar(255) NULL DEFAULT NULL COMMENT '操作人',
  `operator_name` varchar(255) NULL DEFAULT NULL COMMENT '操作人名称',
  `operate_date` datetime(0) NOT NULL COMMENT '操作时间',
  `is_pass` tinyint(1) NOT NULL COMMENT '是否通过',
  `operate_content` text NULL COMMENT '操作内容',
  `operate_reason` varchar(255) NULL DEFAULT NULL COMMENT '操作原因',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for dx_other_menu
-- ----------------------------
DROP TABLE IF EXISTS `dx_other_menu`;
CREATE TABLE `dx_other_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '菜单名称',
  `img_xy` varchar(255) NULL DEFAULT NULL COMMENT '精灵图坐标',
  `url` varchar(255) NULL DEFAULT NULL COMMENT '业务地址',
  `type` int(11) NULL DEFAULT NULL COMMENT '1.劳务队 2.供应商 3.设备租赁商',
  `order_index` int(11) NULL DEFAULT NULL COMMENT '菜单排序',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_other_menu
-- ----------------------------

-- ----------------------------
-- Table structure for dx_other_user
-- ----------------------------
DROP TABLE IF EXISTS `dx_other_user`;
CREATE TABLE `dx_other_user`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `phone` varchar(255) NOT NULL COMMENT '手机号',
  `password` varchar(255) NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '姓名',
  `photo_fid` int(11) NULL DEFAULT NULL COMMENT '图片id',
  `num` varchar(255) NULL DEFAULT NULL COMMENT '身份证号',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_other_user
-- ----------------------------

-- ----------------------------
-- Table structure for dx_other_user_job
-- ----------------------------
DROP TABLE IF EXISTS `dx_other_user_job`;
CREATE TABLE `dx_other_user_job`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `flag` int(11) NULL DEFAULT NULL COMMENT '状态 1 有效 0 失效',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `type` int(11) NULL DEFAULT NULL COMMENT '1.劳务队  2 供应商 3设备租赁商',
  `job_name` varchar(255) NULL DEFAULT NULL COMMENT '职位名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '生效时间',
  `create_name` varchar(255) NULL DEFAULT NULL COMMENT '审批通过人名',
  `create_userid` varchar(255) NULL DEFAULT NULL COMMENT '审批通过人id',
  `company_name` text NULL COMMENT '单位名称',
  `phone` varchar(255) NULL DEFAULT NULL COMMENT '手机号',
  `company_id` int(11) NULL DEFAULT NULL COMMENT '所属单位id  劳务队id  供应商id 设备租赁公司id',
  `project_name` varchar(255) NULL DEFAULT NULL COMMENT '项目部名称',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_other_user_job
-- ----------------------------

-- ----------------------------
-- Table structure for dx_other_user_job_apply_for
-- ----------------------------
DROP TABLE IF EXISTS `dx_other_user_job_apply_for`;
CREATE TABLE `dx_other_user_job_apply_for`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `photo_fid` int(11) NULL DEFAULT NULL COMMENT '照片',
  `identification_photo` int(11) NULL DEFAULT NULL COMMENT '证件照',
  `signature_fid` int(11) NULL DEFAULT NULL COMMENT '电子签名',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '姓名',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `project_name` varchar(255) NULL DEFAULT NULL COMMENT '项目部名称',
  `type` int(11) NULL DEFAULT NULL COMMENT '身份信息  1 劳务队 2 供应商 3 设备租赁商',
  `job_name` varchar(255) NULL DEFAULT NULL COMMENT '职位名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '提交时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '审批时间',
  `update_user` varchar(255) NULL DEFAULT NULL COMMENT '审批人',
  `status` int(11) NULL DEFAULT NULL COMMENT '审批状态 1 未审批 2 已同意 3 已拒绝',
  `company_name` varchar(255) NULL DEFAULT NULL COMMENT '单位名称',
  `phone` varchar(255) NULL DEFAULT NULL COMMENT '手机号',
  `company_id` int(11) NULL DEFAULT NULL COMMENT ' 劳务队id  供应商id 设备租赁公司id',
  `save_type` int(11) NULL DEFAULT NULL COMMENT '1 新增身份   2 变更身份 ',
  `change_id` int(11) NULL DEFAULT NULL COMMENT '变更身份的身份id    变更那条记录的id',
  `create_remark` varchar(255) NULL DEFAULT NULL COMMENT '申请意见',
  `update_remark` varchar(255) NULL DEFAULT NULL COMMENT '审批意见',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_other_user_job_apply_for
-- ----------------------------

-- ----------------------------
-- Table structure for dx_overall_config
-- ----------------------------
DROP TABLE IF EXISTS `dx_overall_config`;
CREATE TABLE `dx_overall_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `address_ip` varchar(5000) NULL DEFAULT NULL COMMENT '设备ip',
  `num` text NULL COMMENT '工控机设备id',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '设备名称',
  `is_update` int(11) NULL DEFAULT 0 COMMENT '是否需要跟新 0不需要 1需要',
  `is_restart` int(11) NULL DEFAULT 0 COMMENT '是否重启  0不需要 1需要',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `is_proof_time` int(11) NULL DEFAULT NULL COMMENT '是否重启 0 不需要 1 需要',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后一次修改时间',
  `remark` text NULL COMMENT '备注信息',
  `device_id` varchar(255) NULL DEFAULT NULL COMMENT '温控设备标识',
  `version` varchar(255) NULL DEFAULT NULL COMMENT '版本',
  `address` varchar(255) NULL DEFAULT NULL COMMENT '设备所在地方',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_overall_config
-- ----------------------------

-- ----------------------------
-- Table structure for dx_plan_config
-- ----------------------------
DROP TABLE IF EXISTS `dx_plan_config`;
CREATE TABLE `dx_plan_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '名称',
  `type` int(11) NULL DEFAULT NULL COMMENT '分类 1 类别 2 专业',
  `array_sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_plan_config
-- ----------------------------

-- ----------------------------
-- Table structure for dx_plan_contract
-- ----------------------------
DROP TABLE IF EXISTS `dx_plan_contract`;
CREATE TABLE `dx_plan_contract`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `examine_number` varchar(255) NULL DEFAULT NULL COMMENT '审批编号',
  `compile_date` date NULL DEFAULT NULL COMMENT '编制日期（系统时间）',
  `compile_name` varchar(255) NULL DEFAULT NULL COMMENT '编制人（当前用户）',
  `contract_number` varchar(5000) NULL DEFAULT NULL COMMENT '合同编号',
  `name_of_undertaking_project` varchar(255) NULL DEFAULT NULL COMMENT '承担工程名称 手输',
  `team_id` int(11) NULL DEFAULT NULL COMMENT '劳务队id',
  `team_name` varchar(255) NULL DEFAULT NULL COMMENT '劳务队名称',
  `signing_time` date NULL DEFAULT NULL COMMENT '签订日期',
  `principal` varchar(255) NULL DEFAULT NULL COMMENT '被委托人',
  `contractor` varchar(255) NULL DEFAULT NULL COMMENT '法定代表人（劳务队负责人）',
  `part_id` int(11) NULL DEFAULT NULL COMMENT '施工难度id  （注册工程类别）',
  `part_name` varchar(255) NULL DEFAULT NULL COMMENT '工程类别（注册工程类别） 名字',
  `credit_rating` varchar(255) NULL DEFAULT NULL COMMENT '信用评价等级（劳务队评价）',
  `type_of_engineering_id` int(11) NULL DEFAULT NULL COMMENT '工程类别id',
  `type_of_engineering` varchar(255) NULL DEFAULT NULL COMMENT '所属工程种类  部位id  部位名字 （所属专业）',
  `number_of_worksite` int(11) NULL DEFAULT NULL COMMENT '工点数',
  `leader_name` varchar(255) NULL DEFAULT NULL COMMENT '带班负责人名字',
  `leader_id` int(11) NULL DEFAULT NULL COMMENT '带班负责人id',
  `leader_idcard` varchar(255) NULL DEFAULT NULL COMMENT '带班负责人身份证号',
  `pm_name` varchar(255) NULL DEFAULT NULL COMMENT '项目经理名称',
  `leader_phone` varchar(255) NULL DEFAULT NULL COMMENT '带班负责人电话',
  `contract_mode` varchar(255) NULL DEFAULT NULL COMMENT '承包模式',
  `status` int(11) NULL DEFAULT NULL COMMENT '最近操作状态 0未审批 1计划员 2 计划部长 3项目经理 4经管部部员 5经管部部长 6法律事务部部长  7公司分管 8公司主管 9 完成',
  `last_status` int(11) NULL DEFAULT NULL COMMENT '已完成状态   1 同意  2 拒绝   3 驳回至发起人  4 驳回至上一级',
  `one_json` text NULL,
  `two_json` text NULL,
  `three_json` text NULL,
  `four_json` text NULL,
  `five_json` text NULL,
  `six_json` text NULL,
  `seven_json` text NULL,
  `eight_json` text NULL,
  `contract_year` int(11) NULL DEFAULT NULL COMMENT '施工月份',
  `join_time` datetime(0) NULL DEFAULT NULL COMMENT '进场日期',
  `main_construction_contents` varchar(255) NULL DEFAULT NULL COMMENT '主要施工内容',
  `tax_rate` double NULL DEFAULT NULL COMMENT '税率（百分之）',
  `fid` int(11) NULL DEFAULT NULL COMMENT '合同附件id',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `team_number` varchar(5000) NULL DEFAULT NULL COMMENT '劳务审批编号',
  `contract_id` int(11) NULL DEFAULT NULL COMMENT '合同id  如果有的话就是补充协议',
  `business_box` tinyint(20) NULL DEFAULT NULL COMMENT '核算按钮   1 核算 0 不核算',
  `shows_box` tinyint(20) NULL DEFAULT NULL COMMENT '账面显示 1 核算 0  不核算',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_plan_contract
-- ----------------------------

-- ----------------------------
-- Table structure for dx_plan_danjia
-- ----------------------------
DROP TABLE IF EXISTS `dx_plan_danjia`;
CREATE TABLE `dx_plan_danjia`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `guidance_numb` varchar(255) NULL DEFAULT NULL COMMENT '指导价编号',
  `guidanceid` int(11) NULL DEFAULT NULL COMMENT '指导价id',
  `guidance_name` varchar(255) NULL DEFAULT NULL COMMENT '工程名字',
  `unit_name` varchar(255) NULL DEFAULT NULL COMMENT '单位符号',
  `unit_price_excluding_tax` double NULL DEFAULT NULL COMMENT '不含税单价',
  `tax` double NULL DEFAULT NULL COMMENT '税金',
  `price` double NULL DEFAULT NULL COMMENT '含税单价',
  `order_id` int(11) NULL DEFAULT NULL COMMENT '合同id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) NULL DEFAULT NULL COMMENT '创建人',
  `fid` int(11) NULL DEFAULT NULL COMMENT '文件id',
  `guidance_cost` text NULL COMMENT '费用组成',
  `guidance_wark` text NULL COMMENT '工作内容',
  `remark` text NULL COMMENT '备注',
  `type` int(11) NULL DEFAULT NULL COMMENT '1.公司  2 新增',
  `level` varchar(255) NULL DEFAULT NULL COMMENT '层级 李倩回显用',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_plan_danjia
-- ----------------------------

-- ----------------------------
-- Table structure for dx_plan_valuation
-- ----------------------------
DROP TABLE IF EXISTS `dx_plan_valuation`;
CREATE TABLE `dx_plan_valuation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '计价',
  `number_id` int(11) NULL DEFAULT NULL COMMENT '指导价id',
  `number` varchar(255) NULL DEFAULT NULL COMMENT '指导价编号',
  `unit_name` varchar(255) NULL DEFAULT NULL COMMENT '单位',
  `price` double NULL DEFAULT 0 COMMENT '单价',
  `this_size` double NULL DEFAULT 0 COMMENT '本次数量',
  `this_money` double NULL DEFAULT 0 COMMENT '本次金额',
  `tired_size` double NULL DEFAULT 0 COMMENT '开累数量',
  `tired_money` double NULL DEFAULT 0 COMMENT '开累金额',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `team_id` int(11) NULL DEFAULT NULL COMMENT '劳务队id',
  `order_id` int(11) NULL DEFAULT NULL COMMENT '合同id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) NULL DEFAULT NULL COMMENT '创建人',
  `create_id` varchar(255) NULL DEFAULT NULL COMMENT '创建人id',
  `issue` varchar(255) NULL DEFAULT NULL COMMENT '期号',
  `name` varchar(255) NULL DEFAULT NULL COMMENT '作业项目',
  `ldss_id` text NULL COMMENT 'ldss number id 工程量表的id',
  `valuation_time` datetime(0) NULL DEFAULT NULL COMMENT '计价日期',
  `netease` text NULL COMMENT '本次计价内容',
  `remark` text NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_plan_valuation
-- ----------------------------

-- ----------------------------
-- Table structure for dx_plan_valuation_body
-- ----------------------------
DROP TABLE IF EXISTS `dx_plan_valuation_body`;
CREATE TABLE `dx_plan_valuation_body`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sum` double NULL DEFAULT NULL COMMENT '本次计价金额',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '计价时间',
  `order_id` int(11) NULL DEFAULT NULL COMMENT '合同id',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_plan_valuation_body
-- ----------------------------

-- ----------------------------
-- Table structure for dx_productivity_annual
-- ----------------------------
DROP TABLE IF EXISTS `dx_productivity_annual`;
CREATE TABLE `dx_productivity_annual`  (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` int(11) NULL DEFAULT NULL COMMENT '项目部类别 1.铁路工程 2.制梁工程 3.架梁工程 4.公路工程 5.路面工程 6.地下工程及轨道 7.市政工程 8.水利工程 9.其他工程',
  `per_capita_output` double NULL DEFAULT NULL COMMENT '人均产值',
  `wage_output` double NULL DEFAULT NULL COMMENT '工资产值',
  `per_capita_effect` double NULL DEFAULT NULL COMMENT '人均创收',
  `year` varchar(30) NULL DEFAULT NULL COMMENT '年度信息',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_productivity_annual
-- ----------------------------

-- ----------------------------
-- Table structure for dx_productivity_manager
-- ----------------------------
DROP TABLE IF EXISTS `dx_productivity_manager`;
CREATE TABLE `dx_productivity_manager`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `proid` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `money` double(255, 0) NULL DEFAULT NULL COMMENT '本机管理费用',
  `year_quarter` varchar(50) NULL DEFAULT NULL COMMENT '月份信息',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `fid` int(11) NULL DEFAULT NULL COMMENT '文件id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_productivity_manager
-- ----------------------------

-- ----------------------------
-- Table structure for dx_pullproduction_purchase
-- ----------------------------
DROP TABLE IF EXISTS `dx_pullproduction_purchase`;
CREATE TABLE `dx_pullproduction_purchase`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `cgsq_num` varchar(255) NULL DEFAULT NULL COMMENT '订单编号',
  `prodect_name` varchar(255) NULL DEFAULT NULL COMMENT '材料名称',
  `prodect_date` varchar(5000) NULL DEFAULT NULL COMMENT '材料详细数据',
  `ctid` int(6) NULL DEFAULT NULL COMMENT '劳务队id',
  `ctid_name` varchar(100) NULL DEFAULT NULL COMMENT '劳务队名称',
  `ctid_contractor` varchar(50) NULL DEFAULT NULL COMMENT '劳务队负责人',
  `ctid_bzcontractor` varchar(50) NULL DEFAULT NULL COMMENT '班组负责人',
  `phone` varchar(15) NULL DEFAULT NULL COMMENT '负责人联系人电话',
  `work_content` varchar(255) NULL DEFAULT NULL COMMENT '作业内容',
  `part_id` int(6) NULL DEFAULT NULL COMMENT '部位id',
  `part_name` varchar(50) NULL DEFAULT NULL COMMENT '部位名称',
  `jh_time` date NULL DEFAULT NULL COMMENT '计划使用时间',
  `sq_uid` varchar(70) NULL DEFAULT NULL COMMENT '申请人id',
  `sq_name` varchar(50) NULL DEFAULT NULL COMMENT '申请人姓名',
  `sq_phone` varchar(15) NULL DEFAULT NULL COMMENT '申请人电话',
  `gc_js_uid` varchar(70) NULL DEFAULT NULL COMMENT '技术员id',
  `gc_js_status` int(4) NULL DEFAULT NULL COMMENT '技术员意见状态(0:同意,1:不同意)',
  `gc_js_comment` varchar(150) NULL DEFAULT NULL COMMENT '技术员意见内容',
  `gc_js_time` date NULL DEFAULT NULL COMMENT '技术员意见填写日期',
  `gc_js_file` int(11) NULL DEFAULT NULL COMMENT '技术员签名id',
  `gc_bz_uid` varchar(70) NULL DEFAULT NULL COMMENT '工程部长id',
  `gc_bz_status` int(4) NULL DEFAULT NULL COMMENT '工程部长意见状态(0:同意,1:不同意)',
  `gc_bz_comment` varchar(150) NULL DEFAULT NULL COMMENT '工程部长意见内容',
  `gc_bz_time` date NULL DEFAULT NULL COMMENT '工程部意见填写日期',
  `gc_bz_file` int(11) NULL DEFAULT NULL COMMENT '工程部长签字文件',
  `fjl_uid` varchar(70) NULL DEFAULT NULL COMMENT '副经理id',
  `fjl_status` int(4) NULL DEFAULT NULL COMMENT '副经理审核状态',
  `fjl_comment` varchar(150) NULL DEFAULT NULL COMMENT '副经理意见内容',
  `fjl_time` date NULL DEFAULT NULL COMMENT '副经理意见填写时间',
  `fjl_file` int(11) NULL DEFAULT NULL COMMENT '副经理签字文件id',
  `zg_uid` varchar(70) NULL DEFAULT NULL COMMENT '总工id',
  `zg_status` int(11) NULL DEFAULT NULL COMMENT '总工意见状态0同意1否',
  `zg_comment` varchar(150) NULL DEFAULT NULL COMMENT '总工填写意见内容',
  `zg_time` date NULL DEFAULT NULL COMMENT '总工填写意见时间',
  `zg_file` int(11) NULL DEFAULT NULL COMMENT '总工签名文件id',
  `pro_id` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `is_xmjl` int(4) NULL DEFAULT 0 COMMENT '是否抄送项目经理0否，1是',
  `cgsq_status` int(4) NULL DEFAULT 0 COMMENT '材料订单状态',
  `is_delete` int(11) NULL DEFAULT 0 COMMENT '是否删除，0正常，1删除',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `create_user` varchar(11) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` varchar(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '收料';

-- ----------------------------
-- Records of dx_pullproduction_purchase
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_accepting
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_accepting`;
CREATE TABLE `dx_security_accepting`  (
  `accepting_id` bigint(20) NOT NULL COMMENT '安全验收id',
  `project_id` int(11) NOT NULL COMMENT '项目部id',
  `project_name` varchar(220) NOT NULL COMMENT '项目部名字',
  `labor_id` int(11) NULL DEFAULT NULL COMMENT '劳务队id',
  `labor_name` varchar(220) NULL DEFAULT NULL COMMENT '劳务队名字',
  `team_id` int(11) NULL DEFAULT NULL COMMENT '班组id',
  `team_name` varchar(220) NULL DEFAULT NULL COMMENT '班组名字',
  `accepting_directions` text NULL COMMENT '验收说明',
  `rectify_id` varchar(64) NULL DEFAULT NULL COMMENT '整改人id',
  `rectify_name` varchar(120) NULL DEFAULT NULL COMMENT '整改人名字',
  `rectify_phone` varchar(26) NULL DEFAULT NULL COMMENT '整改人电话',
  `rectify_directions` text NULL COMMENT '整改说明',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `status` int(3) NULL DEFAULT NULL COMMENT '1 待验收 2 待复查 3 已验收  4 已抽查',
  `part_id` int(11) NULL DEFAULT NULL COMMENT '部位id',
  `part_name` varchar(255) NULL DEFAULT NULL COMMENT '部位名字',
  `count` int(11) NULL DEFAULT NULL COMMENT '验收次数',
  `accepting_time` datetime(0) NULL DEFAULT NULL COMMENT '验收时间',
  `fc_name` varchar(120) NULL DEFAULT NULL COMMENT '复查人',
  `fc_time` datetime(0) NULL DEFAULT NULL COMMENT '复查时间',
  `accepting_name` varchar(120) NULL DEFAULT NULL COMMENT '验收人',
  `accepting_phone` varchar(26) NULL DEFAULT NULL COMMENT '验收电话',
  `survey_time` datetime(0) NULL DEFAULT NULL COMMENT '抽查时间',
  `survey_name` varchar(120) NULL DEFAULT NULL COMMENT '抽查人',
  `survey_phone` varchar(26) NULL DEFAULT NULL COMMENT '抽查人电话',
  `type` int(11) NULL DEFAULT NULL COMMENT '1 符合 2 不符合',
  `survey_count` int(11) NULL DEFAULT NULL COMMENT '抽查次数',
  `abbreviation` varchar(120) NULL DEFAULT NULL COMMENT '项目部简称',
  `Rectify_time` datetime(0) NULL DEFAULT NULL COMMENT '整改时间',
  `accepting_user_id` varchar(64) NULL DEFAULT NULL COMMENT '验收人id',
  PRIMARY KEY (`accepting_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_accepting
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_accepting_content
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_accepting_content`;
CREATE TABLE `dx_security_accepting_content`  (
  `accepting_content_id` bigint(20) NOT NULL COMMENT '验收内容id',
  `accepting_content` varchar(255) NULL DEFAULT NULL COMMENT '内容',
  `accepting_id` bigint(20) NOT NULL COMMENT '验收表主键',
  `type` int(11) NULL DEFAULT NULL COMMENT '1 合格 2 不合格',
  `data_base_id` bigint(20) NULL DEFAULT NULL COMMENT '安全验收和隐患数据库id',
  PRIMARY KEY (`accepting_content_id`),
  INDEX `accepting_id`(`accepting_id`),
  CONSTRAINT `accepting_id` FOREIGN KEY (`accepting_id`) REFERENCES `dx_security_accepting` (`accepting_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_accepting_content
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_accepting_logs
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_accepting_logs`;
CREATE TABLE `dx_security_accepting_logs`  (
  `accepting_logs_id` bigint(20) NOT NULL COMMENT '安全验收日志id',
  `accepting_id` bigint(20) NOT NULL COMMENT '安全验收id',
  `accepting_content` varchar(255) NULL DEFAULT NULL COMMENT '内容',
  `type` int(11) NULL DEFAULT NULL COMMENT '1 合格 2 不合格',
  `data_base_id` bigint(20) NULL DEFAULT NULL COMMENT '安全验收和隐患数据库id',
  `accepting_time` datetime(0) NULL DEFAULT NULL COMMENT '验收时间',
  `count` int(11) NOT NULL COMMENT '次数',
  PRIMARY KEY (`accepting_logs_id`),
  INDEX `acceptingId`(`accepting_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_accepting_logs
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_aq_manage_pro_t
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_aq_manage_pro_t`;
CREATE TABLE `dx_security_aq_manage_pro_t`  (
  `aq_manager_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '问题id',
  `aq_manager_level` varchar(20) NULL DEFAULT NULL COMMENT '问题级别 黄 蓝 红',
  `p_id` int(11) NULL DEFAULT NULL COMMENT '父级id',
  `p_name` varchar(255) NULL DEFAULT NULL COMMENT '父级name',
  `aq_manager_code` varchar(40) NULL DEFAULT NULL COMMENT '问题编码',
  `aq_manager_title` varchar(400) NULL DEFAULT NULL COMMENT '问题内容',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '所属项目部id',
  `project_name` varchar(255) NULL DEFAULT NULL COMMENT '所属项目部name（数据属于哪个项目部）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(40) NULL DEFAULT NULL COMMENT '创建人id',
  `create_user_name` varchar(255) NULL DEFAULT NULL COMMENT '创建用户名称',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(40) NULL DEFAULT NULL COMMENT '修改人id',
  `update_user_name` varchar(255) NULL DEFAULT NULL COMMENT '修改人名',
  `attribute1` varchar(255) NULL DEFAULT NULL,
  `attribute2` varchar(255) NULL DEFAULT NULL,
  `attribute3` int(10) NULL DEFAULT NULL COMMENT '存放编码最后小数点后的数字',
  PRIMARY KEY (`aq_manager_id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '安全督察-> 安全问题 问题汇总表';

-- ----------------------------
-- Records of dx_security_aq_manage_pro_t
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_aq_manager_tree_t
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_aq_manager_tree_t`;
CREATE TABLE `dx_security_aq_manager_tree_t`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `aq_manager_code` varchar(20) NULL DEFAULT NULL COMMENT '类型编码',
  `name` varchar(40) NOT NULL COMMENT '类型标题',
  `p_id` int(11) NOT NULL COMMENT '父id （1级=0 ）',
  `px_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_aq_manager_tree_t
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_congress
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_congress`;
CREATE TABLE `dx_security_congress`  (
  `congress_id` bigint(20) NOT NULL COMMENT '安全生产列会id',
  `congress_name` varchar(255) NULL DEFAULT NULL COMMENT '列会名称',
  `congress_time` datetime(0) NULL DEFAULT NULL COMMENT '列会时间',
  `congress_content` text NULL COMMENT '列会内容',
  `sign_in_table_id` varchar(220) NULL DEFAULT NULL COMMENT '签到表id',
  `fujian_id` varchar(220) NULL DEFAULT NULL COMMENT '附件id',
  `image1` int(11) NULL DEFAULT NULL COMMENT '图片1',
  `image2` int(11) NULL DEFAULT NULL COMMENT '图片2',
  `image3` int(11) NULL DEFAULT NULL COMMENT '图片3',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `project_name` varchar(120) NULL DEFAULT NULL COMMENT '项目部名字',
  `congress_end_time` datetime(0) NULL DEFAULT NULL COMMENT '会议结束时间',
  `abbreviation` varchar(120) NULL DEFAULT NULL COMMENT '项目部简称',
  `year` int(11) NULL DEFAULT NULL COMMENT '年',
  `month` int(11) NULL DEFAULT NULL COMMENT '月',
  `count` int(11) NULL DEFAULT NULL COMMENT '特殊字段 无实际意义',
  `persons` text NULL COMMENT '列会人员',
  PRIMARY KEY (`congress_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_congress
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_database_node
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_database_node`;
CREATE TABLE `dx_security_database_node`  (
  `data_base_node_id` bigint(20) NOT NULL COMMENT '安全数据子类 id',
  `content` varchar(255) NULL DEFAULT NULL COMMENT '安全数据子类容',
  `data_base_id` bigint(20) NULL DEFAULT NULL COMMENT '父 id',
  `img_id` varchar(225) NULL DEFAULT NULL COMMENT '图片id',
  `remark` text NULL COMMENT '备注',
  `censor1` varchar(225) NULL DEFAULT NULL COMMENT '验收1',
  `censor2` varchar(225) NULL DEFAULT NULL COMMENT '验收2',
  PRIMARY KEY (`data_base_node_id`),
  INDEX `partid`(`data_base_id`),
  CONSTRAINT `partid` FOREIGN KEY (`data_base_id`) REFERENCES `dx_security_database_part` (`data_base_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_database_node
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_database_part
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_database_part`;
CREATE TABLE `dx_security_database_part`  (
  `data_base_id` bigint(20) NOT NULL COMMENT '安全验收和隐患数据库id',
  `title` varchar(255) NOT NULL COMMENT '安全数据标题',
  `type` int(2) NULL DEFAULT NULL COMMENT '1 验收 2 隐患',
  `create_name` varchar(120) NULL DEFAULT NULL COMMENT '创建人名字',
  `create_user_id` varchar(64) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `project_Id` int(11) NULL DEFAULT NULL COMMENT '数据所属的项目部id',
  `projevt` varchar(225) NULL DEFAULT NULL COMMENT '项目部名字',
  PRIMARY KEY (`data_base_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_database_part
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_duty
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_duty`;
CREATE TABLE `dx_security_duty`  (
  `security_duty_id` bigint(40) NOT NULL COMMENT '安全责任表主键id',
  `duty_parent_str` varchar(225) NOT NULL COMMENT '责任范围',
  `duty_parent_id` int(6) NOT NULL COMMENT '部位id',
  `spot_duty_name` varchar(12) NOT NULL COMMENT '现场责任人名字',
  `spot_duty_phone` varchar(24) NULL DEFAULT NULL COMMENT '现场责任人电话',
  `spot_fz_name` varchar(12) NOT NULL COMMENT '现场负责人名字',
  `spot_fz_phone` varchar(24) NULL DEFAULT NULL COMMENT '现场负责人电话',
  `spot_review_name` varchar(12) NOT NULL COMMENT '现场复查人名字',
  `spot_review_phone` varchar(24) NULL DEFAULT NULL COMMENT '现场复查人电话',
  `project_fz_name` varchar(12) NOT NULL COMMENT '项目负责人名字',
  `project_fz_phone` varchar(24) NULL DEFAULT NULL COMMENT '项目负责人电话',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `project_id` int(20) NULL DEFAULT NULL COMMENT '项目部id',
  `project_name` varchar(225) NULL DEFAULT NULL COMMENT '项目部名字',
  `abbreviation` varchar(120) NULL DEFAULT NULL COMMENT '项目部简称',
  PRIMARY KEY (`security_duty_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_duty
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_education
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_education`;
CREATE TABLE `dx_security_education`  (
  `education_id` bigint(20) NOT NULL COMMENT '安全教育id',
  `project_id` int(11) NOT NULL COMMENT '项目部id',
  `project_name` varchar(120) NOT NULL COMMENT '项目部名字',
  `project_abbreviation` varchar(120) NOT NULL COMMENT '项目部名字简称',
  `labor_id` int(11) NOT NULL COMMENT '劳务队id',
  `labor_name` varchar(120) NOT NULL COMMENT '劳务队名字',
  `team_id` varchar(40) NULL DEFAULT NULL COMMENT '班组id 多个用，分隔',
  `team_json` varchar(255) NULL DEFAULT NULL COMMENT '班组json',
  `education_name` varchar(220) NULL DEFAULT NULL COMMENT '教育负责人名字',
  `education_content` text NULL COMMENT '教育内容',
  `image1` int(11) NULL DEFAULT NULL COMMENT '图片1',
  `image2` int(11) NULL DEFAULT NULL COMMENT '图片2',
  `image3` int(11) NULL DEFAULT NULL COMMENT '图片3',
  `is_place_total` int(11) NULL DEFAULT NULL COMMENT '在场人数（包含手动添加）',
  `not_place_total` int(11) NULL DEFAULT NULL COMMENT '在场数（图片识别）',
  `not_spot_total` int(11) NULL DEFAULT NULL COMMENT '不能识别数（未识别到人数）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `place_image` int(11) NULL DEFAULT NULL COMMENT '现场照片',
  `operation` text NULL COMMENT '作业',
  `virtue` int(11) NULL DEFAULT NULL COMMENT '1 班前讲话 2 三级教育',
  `aq_team_id_is` text NULL COMMENT '保存后的在场班组信息id',
  `aq_team_id_not` text NULL COMMENT '保存后的不在场班组信息id',
  `video_id` int(11) NULL DEFAULT NULL COMMENT '视频id',
  `train_id` int(11) NULL DEFAULT NULL COMMENT '鸿泰教育表主键',
  `remark` varchar(300) NULL DEFAULT NULL COMMENT '补录名单',
  PRIMARY KEY (`education_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_education
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_education_file_t
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_education_file_t`;
CREATE TABLE `dx_security_education_file_t`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '三级教育文件表 id',
  `project_id` int(11) NULL DEFAULT NULL,
  `education_id` varchar(50) NULL DEFAULT NULL COMMENT '三级教育Id',
  `file_id` int(11) NULL DEFAULT NULL COMMENT '文件表Id',
  `file_name` varchar(255) NULL DEFAULT NULL COMMENT '文件名',
  `upload_user` varchar(255) NULL DEFAULT NULL COMMENT '上传人',
  `upload_time` datetime(0) NULL DEFAULT NULL COMMENT '上传时间',
  `type` varchar(4) NULL DEFAULT NULL COMMENT '1==文件上传  空==历史文件',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_education_file_t
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_education_team_data
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_education_team_data`;
CREATE TABLE `dx_security_education_team_data`  (
  `aq_team_id` bigint(20) NOT NULL COMMENT '安全培训班组表id',
  `team_id` int(11) NULL DEFAULT NULL COMMENT '班组id',
  `team_name` varchar(120) NULL DEFAULT NULL COMMENT '班组名字',
  `position` varchar(120) NULL DEFAULT NULL COMMENT '职位',
  `user_name` varchar(120) NULL DEFAULT NULL COMMENT '人员名字',
  `labor_id` int(11) NULL DEFAULT NULL COMMENT '劳务队id',
  `labor_name` varchar(120) NULL DEFAULT NULL COMMENT '劳务队名字',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `type` int(11) NULL DEFAULT NULL COMMENT '1 在场 2 不在场',
  `fid` int(11) NULL DEFAULT NULL COMMENT '图片id',
  `education_id` bigint(20) NULL DEFAULT NULL COMMENT '安全教育id',
  `work_name` varchar(220) NULL DEFAULT NULL COMMENT '工种',
  `status` int(11) NULL DEFAULT NULL COMMENT '1 培训  2 未培训',
  `hg` int(11) NULL DEFAULT NULL COMMENT '1 合格 2 不合格',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `user_id` varchar(64) NULL DEFAULT NULL COMMENT '人员id',
  `virtue` int(4) NULL DEFAULT NULL COMMENT '1=班前讲话 2=三级教育',
  `add_state` int(4) NULL DEFAULT NULL COMMENT '手动添加在场（有值就是手动添加）',
  `user_score` varchar(6) NULL DEFAULT NULL COMMENT '考核得分',
  PRIMARY KEY (`aq_team_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_education_team_data
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_file_project
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_file_project`;
CREATE TABLE `dx_security_file_project`  (
  `file_project_id` bigint(20) NOT NULL COMMENT '文件关联项目部主键id',
  `file_id` bigint(20) NULL DEFAULT NULL COMMENT '文件表id',
  `project_id` int(11) NOT NULL COMMENT '项目部id',
  `project_name` varchar(225) NOT NULL COMMENT '项目部名字',
  `project_boss_name` varchar(10) NULL DEFAULT NULL COMMENT '项目经理名字',
  `project_boss_phone` varchar(24) NULL DEFAULT NULL COMMENT '项目经理电话',
  `project_security_id` int(11) NULL DEFAULT NULL COMMENT '安全部门id',
  `project_security_name` varchar(10) NULL DEFAULT NULL COMMENT '安全部负责人名字',
  `project_security_phone` varchar(24) NULL DEFAULT NULL COMMENT '安全部负责人电话',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '文件关联项目部时间',
  `file_name` varchar(120) NOT NULL COMMENT '文件名字',
  `begin_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `reply` int(2) NULL DEFAULT 2 COMMENT '1 回复 2 未回复',
  `report_user_id` varchar(64) NULL DEFAULT NULL COMMENT '上报人id',
  `report_user_name` varchar(12) NULL DEFAULT NULL COMMENT '上报人名字',
  `again_file_id` int(20) NULL DEFAULT NULL COMMENT '再次上传的文件id',
  `again_file_name` varchar(150) NULL DEFAULT NULL COMMENT '再次上传的文件名字',
  `again_file_create_time` datetime(0) NULL DEFAULT NULL COMMENT '再次上传的时间',
  `again_file_type` varchar(180) NULL DEFAULT NULL COMMENT '再次上传的文件模板名称',
  `report_user_phone` varchar(22) NULL DEFAULT NULL COMMENT '上报人电话',
  `abbreviation` varchar(120) NULL DEFAULT NULL COMMENT '项目部简称',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '项目部状态项目部状态 1 新建 2 续建 3 收尾 4 竣工 5 未确认',
  PRIMARY KEY (`file_project_id`),
  INDEX `fileId`(`file_id`),
  CONSTRAINT `fileId` FOREIGN KEY (`file_id`) REFERENCES `dx_security_file_upload` (`file_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_file_project
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_file_upload
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_file_upload`;
CREATE TABLE `dx_security_file_upload`  (
  `file_id` bigint(20) NOT NULL COMMENT '文件表id',
  `file_name` varchar(120) NULL DEFAULT NULL COMMENT '文件名称',
  `file_type` varchar(130) NULL DEFAULT NULL COMMENT '文件模板名称',
  `begin_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `create_time` datetime(0) NOT NULL COMMENT '上传时间',
  `create_user_id` varchar(64) NOT NULL COMMENT '上传人id',
  `create_user_name` varchar(12) NOT NULL COMMENT '上传人姓名',
  `remark` varchar(225) NULL DEFAULT NULL COMMENT '备注',
  `upload_file_id` int(20) NULL DEFAULT NULL COMMENT '文件上传id',
  `status` int(2) NULL DEFAULT 1 COMMENT '1 进行中 2 截止',
  PRIMARY KEY (`file_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_file_upload
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_keep_project
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_keep_project`;
CREATE TABLE `dx_security_keep_project`  (
  `aq_project_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '安全常用项目部id',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `project_name` varchar(225) NULL DEFAULT NULL COMMENT '项目部名字',
  `abbreviation` varchar(225) NULL DEFAULT NULL COMMENT '项目部简称',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '项目部状态 1 新建 2 续建 3 收尾 4 竣工 5 未确认',
  `keep_user_id` varchar(64) NULL DEFAULT NULL COMMENT '收藏人id',
  `keep_user_name` varchar(14) NULL DEFAULT NULL COMMENT '收藏人名字',
  `keep_user_phone` varchar(20) NULL DEFAULT NULL COMMENT '收藏人电话',
  PRIMARY KEY (`aq_project_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_keep_project
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_meeting
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_meeting`;
CREATE TABLE `dx_security_meeting`  (
  `meeting_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '视频会议详情表id',
  `meeting_title` varchar(225) NOT NULL COMMENT '会议标题',
  `upload_person_id` varchar(64) NOT NULL COMMENT '上传人id',
  `upload_person_name` varchar(12) NOT NULL COMMENT '上传人名字',
  `upload_person_phone` varchar(18) NULL DEFAULT NULL COMMENT '上传人电话',
  `upload_person_project_id` int(11) NOT NULL COMMENT '上传人项目部id',
  `upload_person_project` varchar(255) NOT NULL COMMENT '上传人项目部名字',
  `upload_time` datetime(0) NULL DEFAULT NULL COMMENT '上传时间',
  `upload_person_position` varchar(225) NULL DEFAULT NULL COMMENT '上传人职务',
  `presenter_id` varchar(64) NULL DEFAULT NULL COMMENT '主讲人id',
  `presenter_name` varchar(12) NOT NULL COMMENT '主讲人名字',
  `presenter_position` varchar(30) NOT NULL COMMENT '主讲人职务',
  `presenter_position_id` int(11) NOT NULL COMMENT '主讲人职务id',
  `presenter_project_id` int(11) NULL DEFAULT NULL COMMENT '主讲人项目部ID',
  `presenter_project_name` varchar(225) NULL DEFAULT NULL COMMENT '主讲人项目部名字',
  `presenter_project_abbreviation` varchar(225) NULL DEFAULT NULL COMMENT '主讲人项目部简称',
  `meeting_begin_time` datetime(0) NULL DEFAULT NULL COMMENT '会议开始时间',
  `meeting_end_time` datetime(0) NULL DEFAULT NULL COMMENT '会议结束时间',
  `part_name` varchar(120) NOT NULL COMMENT '部位名字',
  `meeting_sum_score` varchar(20) NULL DEFAULT '0' COMMENT '会议总分数',
  `meeting_avg_score` float NULL DEFAULT 0 COMMENT '会议平均分数',
  `meeting_total_score` int(11) NULL DEFAULT 0 COMMENT '会议评分次数',
  `meeting_join_person` longtext NULL COMMENT '会议参与人员',
  `status` int(11) NULL DEFAULT 1 COMMENT '1 进行中  2 截至',
  `node_id` int(11) NULL DEFAULT NULL COMMENT '子集部位id',
  `meeting_file_name` varchar(225) NULL DEFAULT NULL COMMENT '会议课件名称',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '1 分值打分 2 条件打分',
  `section_id` int(20) NULL DEFAULT NULL COMMENT '部门id',
  `section_name` varchar(225) NULL DEFAULT NULL COMMENT '部门名字',
  `company_score` varchar(22) NULL DEFAULT '0' COMMENT '公司分数',
  `meeting_person_total_score` int(11) NULL DEFAULT 1 COMMENT '会议评价的人数',
  `meeting_score_begin_time` datetime(0) NULL DEFAULT NULL,
  `meeting_score_end_time` datetime(0) NULL DEFAULT NULL,
  `score_status` int(11) NULL DEFAULT NULL,
  `ranking` int(11) NULL DEFAULT NULL,
  `param_json` text NULL,
  `is_join_person_score` int(11) NULL DEFAULT NULL COMMENT '1只能参与人进行打分   2所有人都可以打分',
  `join_meeting_remark` varchar(2000) NULL DEFAULT NULL COMMENT '参会说明',
  PRIMARY KEY (`meeting_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_meeting
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_meeting_files
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_meeting_files`;
CREATE TABLE `dx_security_meeting_files`  (
  `meeting_files_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '视频会议附件id',
  `meeting_id` int(11) NOT NULL COMMENT '视频会议详情表id',
  `file_name` varchar(255) NOT NULL COMMENT '视频会议附件名字',
  `file_upload_id` int(11) NULL DEFAULT NULL COMMENT '文件id',
  `file_upload_time` datetime(0) NULL DEFAULT NULL COMMENT '文件上传时间',
  PRIMARY KEY (`meeting_files_id`),
  INDEX `meeting_id`(`meeting_id`),
  CONSTRAINT `meeting_id` FOREIGN KEY (`meeting_id`) REFERENCES `dx_security_meeting` (`meeting_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_meeting_files
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_meeting_node
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_meeting_node`;
CREATE TABLE `dx_security_meeting_node`  (
  `node_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '子集id',
  `node_name` varchar(120) NOT NULL COMMENT '子集名字',
  `part_id` int(11) NULL DEFAULT NULL COMMENT '父级id',
  PRIMARY KEY (`node_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_meeting_node
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_meeting_part
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_meeting_part`;
CREATE TABLE `dx_security_meeting_part`  (
  `part_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '安全视频会议部位id',
  `part_name` varchar(120) NULL DEFAULT NULL COMMENT '部位名字',
  PRIMARY KEY (`part_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_meeting_part
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_meeting_rule
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_meeting_rule`;
CREATE TABLE `dx_security_meeting_rule`  (
  `meeting_rule_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '视频会议评分规则表id',
  `meeting_id` int(11) NULL DEFAULT NULL COMMENT '安全视频会议主键',
  `rule_content` varchar(255) NULL DEFAULT NULL COMMENT '规则内容',
  `rule_score` varchar(20) NULL DEFAULT '0' COMMENT '分值',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `get_score` float NULL DEFAULT 0 COMMENT '得分',
  `rule_total` int(11) NULL DEFAULT 0 COMMENT '评价次数',
  `rule_person_total` int(11) NULL DEFAULT 0 COMMENT '项目部评分人数',
  PRIMARY KEY (`meeting_rule_id`),
  INDEX `meeting_rule_id`(`meeting_id`),
  CONSTRAINT `meeting_rule_id` FOREIGN KEY (`meeting_id`) REFERENCES `dx_security_meeting` (`meeting_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_meeting_rule
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_meeting_score_person
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_meeting_score_person`;
CREATE TABLE `dx_security_meeting_score_person`  (
  `score_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '安全视频会议评分人员表主键',
  `meeting_id` int(11) NOT NULL COMMENT '安全视频会议表主键',
  `meeting_rule_id` int(11) NULL DEFAULT NULL COMMENT '视频会议评分规则表id',
  `person_id` varchar(64) NULL DEFAULT NULL COMMENT '评分人id',
  `person_name` varchar(14) NULL DEFAULT NULL COMMENT '评分人名字',
  `score_time` datetime(0) NULL DEFAULT NULL COMMENT '评分时间',
  `score` float NULL DEFAULT 0 COMMENT '评分',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '评分人项目部id',
  `project_name` varchar(225) NULL DEFAULT NULL COMMENT '评分人项目部名字',
  `position` varchar(225) NULL DEFAULT NULL COMMENT '评分人职务',
  `department` varchar(225) NULL DEFAULT NULL COMMENT '评分人部门',
  `abbreviation` varchar(225) NULL DEFAULT NULL COMMENT '评分人项目部简称',
  PRIMARY KEY (`score_id`),
  INDEX `score_meeting_id`(`meeting_id`),
  INDEX `score_meeting_rule_id`(`meeting_rule_id`),
  CONSTRAINT `score_meeting_id` FOREIGN KEY (`meeting_id`) REFERENCES `dx_security_meeting` (`meeting_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `score_meeting_rule_id` FOREIGN KEY (`meeting_rule_id`) REFERENCES `dx_security_meeting_rule` (`meeting_rule_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_meeting_score_person
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_note
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_note`;
CREATE TABLE `dx_security_note`  (
  `note_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '安全防范主体表id',
  `content` varchar(120) NOT NULL COMMENT '安全防范内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`note_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_note
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_note_merge
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_note_merge`;
CREATE TABLE `dx_security_note_merge`  (
  `note_node_id` int(11) NOT NULL COMMENT '行id',
  `a1` int(2) NULL DEFAULT 2,
  `a2` int(2) NULL DEFAULT 2,
  `a3` int(2) NULL DEFAULT 2,
  `a4` int(2) NULL DEFAULT 2,
  `a5` int(2) NULL DEFAULT 2,
  `a6` int(2) NULL DEFAULT 2,
  `a7` int(2) NULL DEFAULT 2,
  `a8` int(2) NULL DEFAULT 2,
  `a9` int(2) NULL DEFAULT 2,
  `a10` int(2) NULL DEFAULT 2,
  `a11` int(2) NULL DEFAULT 2,
  `a12` int(2) NULL DEFAULT 2,
  `a13` int(2) NULL DEFAULT 2,
  `a14` int(2) NULL DEFAULT 2,
  `project_id` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `project_name` varchar(225) NULL DEFAULT NULL COMMENT '项目部名字',
  `project_abbreviation` varchar(125) NULL DEFAULT NULL COMMENT '项目部简称',
  `remark` text NULL COMMENT '备注',
  PRIMARY KEY (`note_node_id`),
  UNIQUE INDEX `note_node_id`(`note_node_id`),
  CONSTRAINT `note_node_id` FOREIGN KEY (`note_node_id`) REFERENCES `dx_security_note_node` (`note_node_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_note_merge
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_note_node
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_note_node`;
CREATE TABLE `dx_security_note_node`  (
  `note_node_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '子级id',
  `note_node_name` varchar(120) NULL DEFAULT NULL COMMENT '子级名字',
  `level` smallint(6) NULL DEFAULT 2 COMMENT '等级',
  `note_part_id` int(11) NOT NULL COMMENT '父级id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`note_node_id`),
  INDEX `part_id`(`note_part_id`),
  CONSTRAINT `part_id` FOREIGN KEY (`note_part_id`) REFERENCES `dx_security_note_part` (`note_part_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_note_node
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_note_part
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_note_part`;
CREATE TABLE `dx_security_note_part`  (
  `note_part_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '安全防范结构表id',
  `note_part_name` varchar(120) NULL DEFAULT NULL COMMENT '安全防范总纲名字',
  `level` smallint(11) NULL DEFAULT 1 COMMENT '层级',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`note_part_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_note_part
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_party_remove_t
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_party_remove_t`;
CREATE TABLE `dx_security_party_remove_t`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(40) NULL DEFAULT NULL COMMENT '调动人id',
  `user_name` varchar(255) NULL DEFAULT NULL COMMENT '调动人姓名',
  `sex` varchar(10) NULL DEFAULT NULL COMMENT '性别',
  `nation` varchar(255) NULL DEFAULT NULL COMMENT '民族',
  `birth` datetime(0) NULL DEFAULT NULL COMMENT '生日',
  `ident_id` varchar(255) NULL DEFAULT NULL COMMENT '身份_id',
  `ident_name` varchar(255) NULL DEFAULT NULL COMMENT '身份',
  `id_card` varchar(255) NULL DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(15) NULL DEFAULT NULL COMMENT '电话',
  `from_project_id` int(11) NULL DEFAULT NULL COMMENT '调出单位id',
  `from_project_name` varchar(255) NULL DEFAULT NULL COMMENT '调查单位name',
  `to_project_id` int(11) NULL DEFAULT NULL COMMENT '调入单位id',
  `to_project_name` varchar(255) NULL DEFAULT NULL COMMENT '调入单位name',
  `leave_Time` datetime(0) NULL DEFAULT NULL COMMENT '离开时间',
  `come_time` datetime(0) NULL DEFAULT NULL COMMENT '报道时间',
  `dfjz_time` datetime(0) NULL DEFAULT NULL COMMENT '党费截缴日期',
  `address` varchar(400) NULL DEFAULT NULL COMMENT '通讯地址',
  `fax` varchar(255) NULL DEFAULT NULL COMMENT '传真',
  `postcard` varchar(10) NULL DEFAULT NULL COMMENT '邮编',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `js_idea` varchar(255) NULL DEFAULT NULL COMMENT '接收意见',
  `type` int(4) NULL DEFAULT NULL COMMENT '转接状态',
  `status` int(4) NULL DEFAULT NULL,
  `upload_user_id` varchar(40) NULL DEFAULT NULL COMMENT '操作人id',
  `upload_user_name` varchar(255) NULL DEFAULT NULL COMMENT '操作人名',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `accept_user_id` varchar(40) NULL DEFAULT NULL COMMENT '接收人id',
  `accept_user_name` varchar(255) NULL DEFAULT NULL COMMENT '接收人名',
  `accept_time` datetime(0) NULL DEFAULT NULL COMMENT '接收时间',
  `upload_user_phone` varchar(20) NULL DEFAULT NULL COMMENT '转接人电话',
  `accept_user_phone` varchar(20) NULL DEFAULT NULL COMMENT '接收人电话',
  `age` varchar(20) NULL DEFAULT NULL COMMENT '年龄',
  `yxq_days` varchar(20) NULL DEFAULT NULL COMMENT '有效期',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_party_remove_t
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_report
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_report`;
CREATE TABLE `dx_security_report`  (
  `aq_report_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '安全举报表id',
  `report_project_id` int(11) NULL DEFAULT NULL COMMENT '举报的项目部id',
  `report_project_name` varchar(120) NULL DEFAULT NULL COMMENT '举报的项目部名字',
  `report_site` varchar(120) NULL DEFAULT NULL COMMENT '事发位置',
  `report_content` text NULL COMMENT '举报内容',
  `image1` varchar(11) NULL DEFAULT NULL COMMENT '图片1',
  `image2` varchar(11) NULL DEFAULT NULL COMMENT '图片2',
  `image3` varchar(11) NULL DEFAULT NULL COMMENT '图片3',
  `image4` varchar(11) NULL DEFAULT NULL COMMENT '图片4',
  `report_name` varchar(120) NULL DEFAULT NULL COMMENT '举报人姓名',
  `report_phone` varchar(24) NULL DEFAULT NULL COMMENT '举报人电话',
  `report_create_time` datetime(0) NULL DEFAULT NULL COMMENT '举报信息创建时间',
  `report_abbreviation` varchar(120) NULL DEFAULT NULL COMMENT '简称',
  PRIMARY KEY (`aq_report_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_report
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_risk_count_table
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_risk_count_table`;
CREATE TABLE `dx_security_risk_count_table`  (
  `risk_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '安全风险统计表主键id',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `project_name` varchar(120) NULL DEFAULT NULL COMMENT '项目部名字',
  `risk_profile` varchar(225) NULL DEFAULT NULL COMMENT '单位工程名称及风险简况',
  `branch_project` varchar(225) NULL DEFAULT NULL COMMENT '危险性较大的分部分项工程',
  `risk_analysis` varchar(225) NULL DEFAULT NULL COMMENT '重大风险分析',
  `control_measure` varchar(225) NULL DEFAULT NULL COMMENT '管控措施',
  `risk_time_slot` varchar(225) NULL DEFAULT NULL COMMENT '预计风险时段',
  `risk_total` varchar(11) NULL DEFAULT NULL COMMENT '风险总数',
  `this_month_remove_count` varchar(11) NULL DEFAULT NULL COMMENT '本月消除',
  `add_up_remove_count` varchar(11) NULL DEFAULT NULL COMMENT '累计消除',
  `lave_count` varchar(11) NULL DEFAULT NULL COMMENT '剩余消除',
  `project_lead_name` varchar(225) NULL DEFAULT NULL COMMENT '项目主管',
  `spot_lead_name` varchar(225) NULL DEFAULT NULL COMMENT '现场主管',
  `problem` varchar(225) NULL DEFAULT NULL COMMENT '存在问题',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '数据入库时间',
  `rows` int(11) NULL DEFAULT NULL COMMENT '导出时需要用到的字段',
  `file_id` bigint(20) NULL DEFAULT NULL COMMENT '文件表id',
  PRIMARY KEY (`risk_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_risk_count_table
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_speech
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_speech`;
CREATE TABLE `dx_security_speech`  (
  `speech_id` bigint(20) NOT NULL COMMENT '安全班前讲话id',
  `project_id` int(20) NOT NULL COMMENT '项目部id',
  `project_name` varchar(120) NOT NULL COMMENT '项目部名字',
  `project_abbreviation` varchar(100) NOT NULL COMMENT '项目部名字简称',
  `labor_id` int(20) NOT NULL COMMENT '劳务队id',
  `labor_name` varchar(220) NOT NULL COMMENT '劳务队名字',
  `team_id` int(20) NOT NULL COMMENT '班组id',
  `team_name` varchar(220) NOT NULL COMMENT '班组名字',
  `speech_name` varchar(220) NULL DEFAULT NULL COMMENT '讲话负责人名字',
  `operation` text NULL COMMENT '当天作业',
  `speech_content` text NULL COMMENT '讲话内容',
  `image1` int(11) NULL DEFAULT NULL COMMENT '图片1',
  `image2` int(11) NULL DEFAULT NULL COMMENT '图片2',
  `image3` int(11) NULL DEFAULT NULL COMMENT '图片3',
  `is_place_total` int(10) NULL DEFAULT NULL COMMENT '在场数',
  `not_place_total` int(10) NULL DEFAULT NULL COMMENT '不在场数',
  `not_spot` int(10) NULL DEFAULT NULL COMMENT '不能识别',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `place_image` int(11) NULL DEFAULT NULL COMMENT '现场照片',
  `is_place_json` varchar(220) NULL DEFAULT NULL COMMENT '现场人员信息json',
  `not_place_json` varchar(220) NULL DEFAULT NULL COMMENT '不在场人员信息json',
  PRIMARY KEY (`speech_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_speech
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_supervision_img_t
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_supervision_img_t`;
CREATE TABLE `dx_security_supervision_img_t`  (
  `img_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `supervision_total_id` int(11) NULL DEFAULT NULL COMMENT '督察整改表id',
  `supervision_info_id` int(11) NULL DEFAULT NULL COMMENT '督察问题表id',
  `file_id` int(11) NULL DEFAULT NULL COMMENT '文件表id',
  `zg_id` int(11) NULL DEFAULT NULL COMMENT '整改id',
  `file_name` varchar(255) NULL DEFAULT NULL COMMENT '文件名',
  `sort_num` int(11) NULL DEFAULT NULL COMMENT '排序号',
  `type` varchar(255) NULL DEFAULT NULL COMMENT '1== 问题明细表   2==整改明细表',
  `upload_time` datetime(0) NULL DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`img_id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '安全督察图片文件表';

-- ----------------------------
-- Records of dx_security_supervision_img_t
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_supervision_info_t
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_supervision_info_t`;
CREATE TABLE `dx_security_supervision_info_t`  (
  `supervision_info_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `supervision_total_id` int(11) NULL DEFAULT NULL COMMENT '安全督察总表Id',
  `aq_manager_id` int(11) NULL DEFAULT NULL COMMENT '安全问题id',
  `aq_manager_level` varchar(255) NULL DEFAULT NULL COMMENT '安全问题级别',
  `aq_manager_code` varchar(255) NULL DEFAULT NULL COMMENT '安全问题编码',
  `aq_manager_title` varchar(400) NULL DEFAULT NULL COMMENT '安全问题内容',
  `not_accept_remark` varchar(400) NULL DEFAULT NULL COMMENT '不合格说明',
  `status` int(3) NULL DEFAULT NULL COMMENT '1==合格    2==不合格  3==待整改  4==已整改',
  `type` int(3) NULL DEFAULT NULL COMMENT '1==已整改  2==未整改',
  `labor_id` varchar(255) NULL DEFAULT NULL COMMENT '劳务队Id',
  `labor_name` varchar(255) NULL DEFAULT NULL COMMENT '劳务队名称',
  `team_id` varchar(255) NULL DEFAULT NULL COMMENT '班组Id',
  `team_name` varchar(255) NULL DEFAULT NULL COMMENT '班组名称',
  PRIMARY KEY (`supervision_info_id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '安全督察问题表';

-- ----------------------------
-- Records of dx_security_supervision_info_t
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_supervision_person_t
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_supervision_person_t`;
CREATE TABLE `dx_security_supervision_person_t`  (
  `uid` varchar(255) NULL DEFAULT '' COMMENT '（手动数据库添加数据）安全督察内，机关相关人员统计的督察数据的汇总表，此表作为辅助表，录入需要统计的人员及规则',
  `uname` varchar(255) NULL DEFAULT NULL COMMENT '单人统计 写人名  \r\n部门统计 写部门名称',
  `type` varchar(255) NULL DEFAULT NULL COMMENT '1==单人统计（非部长） 2==单人统计（部长） 3==部门统计（不含部长） 4==部门统计（含部长） ',
  `jobName` varchar(30) NULL DEFAULT NULL COMMENT '职位    type=2时必填   填 部长',
  `problemNum` int(10) NULL DEFAULT NULL COMMENT '考核问题数（发现问题的个数）',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '人员职位  或者其他',
  `pxNum` int(10) NULL DEFAULT NULL COMMENT '排序手动维护  升序'
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_supervision_person_t
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_supervision_reply_t
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_supervision_reply_t`;
CREATE TABLE `dx_security_supervision_reply_t`  (
  `zg_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `supervision_total_id` int(11) NULL DEFAULT NULL COMMENT '督察主表id',
  `supervision_info_id` int(11) NULL DEFAULT NULL COMMENT '问题明细表Id',
  `supervision_info` varchar(400) NULL DEFAULT NULL COMMENT '问题',
  `zg_remark` varchar(400) NULL DEFAULT NULL COMMENT '整改说明',
  `back_remark` varchar(400) NULL DEFAULT NULL COMMENT '复查说明',
  `is_stand` int(4) NULL DEFAULT NULL COMMENT '是否合格 1==合格  2==不合格',
  `status` int(3) NULL DEFAULT NULL COMMENT '1==保存    2==提交整改人',
  `type` int(4) NULL DEFAULT NULL COMMENT '1==未审批  2==已审批',
  `upload_user_id` varchar(40) NULL DEFAULT NULL COMMENT '整改人id',
  `upload_user_name` varchar(255) NULL DEFAULT NULL COMMENT '整改人名',
  `upload_user_phone` varchar(255) NULL DEFAULT NULL COMMENT '整改人phone',
  `upload_user_project_id` int(11) NULL DEFAULT NULL COMMENT '整改人部门id',
  `upload_user_project_name` varchar(255) NULL DEFAULT NULL COMMENT '整改人部门名',
  `reply_time` datetime(0) NULL DEFAULT NULL COMMENT '整改时间',
  PRIMARY KEY (`zg_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_supervision_reply_t
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_supervision_total_t
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_supervision_total_t`;
CREATE TABLE `dx_security_supervision_total_t`  (
  `supervision_total_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `project_name` varchar(255) NULL DEFAULT NULL COMMENT '项目部名称',
  `engineer_id` int(11) NULL DEFAULT NULL COMMENT '工程部位id',
  `engineer_name` varchar(255) NULL DEFAULT NULL COMMENT '工程部位名称',
  `problem_total_id` int(11) NULL DEFAULT NULL COMMENT '问题类别id',
  `problem_total_name` varchar(255) NULL DEFAULT NULL COMMENT '问题类别名称',
  `problem_classify_id` int(11) NULL DEFAULT NULL COMMENT '问题分类id',
  `problem_classify_name` varchar(255) NULL DEFAULT NULL COMMENT '问题分类name',
  `upload_user_id` varchar(255) NULL DEFAULT NULL COMMENT '发现人id（审核人）',
  `upload_user_phone` varchar(255) NULL DEFAULT NULL COMMENT '发现人phone',
  `upload_user_name` varchar(255) NULL DEFAULT NULL COMMENT '发现人name',
  `upload_user_depart` varchar(255) NULL DEFAULT NULL COMMENT '发现人部门',
  `upload_user_job` varchar(255) NULL DEFAULT NULL COMMENT '发现人职位',
  `find_time` datetime(0) NULL DEFAULT NULL COMMENT '发现问题时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '整改期限',
  `status` int(3) NULL DEFAULT NULL COMMENT '整改状态 1==待整改（新增的） 待核查（2==已整改未核查|| 4==核查未通过） 3==已完成（核查通过）',
  `accept_num` int(10) NULL DEFAULT NULL COMMENT '合格数量',
  `not_accept_num` int(10) NULL DEFAULT NULL COMMENT '不合格数量',
  `total_num` int(10) NULL DEFAULT NULL COMMENT '总数量',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(40) NULL DEFAULT NULL COMMENT '创建人',
  `create_user_name` varchar(255) NULL DEFAULT NULL COMMENT '创建人名',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(40) NULL DEFAULT NULL COMMENT '修改人Id',
  `update_user_name` varchar(255) NULL DEFAULT NULL COMMENT '修改人名',
  `attribute1` varchar(255) NULL DEFAULT NULL,
  `attribute2` varchar(255) NULL DEFAULT NULL,
  `attribute3` varchar(255) NULL DEFAULT NULL,
  `create_user_proj_id` varchar(20) NULL DEFAULT NULL COMMENT '创建人projectid',
  `update_user_proj_id` varchar(20) NULL DEFAULT NULL COMMENT '修改人projectid',
  `labor_id` varchar(255) NULL DEFAULT NULL COMMENT '劳务队Id',
  `labor_name` varchar(255) NULL DEFAULT NULL COMMENT '劳务队name',
  `team_id` varchar(255) NULL DEFAULT NULL COMMENT '班组Id',
  `team_name` varchar(255) NULL DEFAULT NULL COMMENT '班组名称',
  PRIMARY KEY (`supervision_total_id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '安全督察总表';

-- ----------------------------
-- Records of dx_security_supervision_total_t
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_supervision_total_t_zs
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_supervision_total_t_zs`;
CREATE TABLE `dx_security_supervision_total_t_zs`  (
  `supervision_total_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `project_name` varchar(255) NULL DEFAULT NULL COMMENT '项目部名称',
  `engineer_id` int(11) NULL DEFAULT NULL COMMENT '工程部位id',
  `engineer_name` varchar(400) NULL DEFAULT NULL COMMENT '工程部位名称',
  `problem_total_id` int(11) NULL DEFAULT NULL COMMENT '问题类别id',
  `problem_total_name` varchar(255) NULL DEFAULT NULL COMMENT '问题类别名称',
  `problem_classify_id` int(11) NULL DEFAULT NULL COMMENT '问题分类id',
  `problem_classify_name` varchar(255) NULL DEFAULT NULL COMMENT '问题分类name',
  `upload_user_id` varchar(255) NULL DEFAULT NULL COMMENT '发现人id（审核人）',
  `upload_user_phone` varchar(255) NULL DEFAULT NULL COMMENT '发现人phone',
  `upload_user_name` varchar(255) NULL DEFAULT NULL COMMENT '发现人name',
  `upload_user_depart` varchar(255) NULL DEFAULT NULL COMMENT '发现人部门',
  `upload_user_job` varchar(255) NULL DEFAULT NULL COMMENT '发现人职位',
  `find_time` datetime(0) NULL DEFAULT NULL COMMENT '发现问题时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '整改期限',
  `status` int(3) NULL DEFAULT NULL COMMENT '整改状态 1==待整改（新增的）  2==待核查（已整改未核查||核查未通过） 3==已完成（核查通过）',
  `accept_num` int(10) NULL DEFAULT NULL COMMENT '合格数量',
  `not_accept_num` int(10) NULL DEFAULT NULL COMMENT '不合格数量',
  `total_num` int(10) NULL DEFAULT NULL COMMENT '总数量',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(40) NULL DEFAULT NULL COMMENT '创建人',
  `create_user_name` varchar(255) NULL DEFAULT NULL COMMENT '创建人名',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(40) NULL DEFAULT NULL COMMENT '修改人Id',
  `update_user_name` varchar(0) NULL DEFAULT NULL COMMENT '修改人名',
  `attribute1` varchar(400) NULL DEFAULT NULL,
  `attribute2` varchar(400) NULL DEFAULT NULL,
  `attribute3` varchar(400) NULL DEFAULT NULL,
  `create_user_proj_id` varchar(20) NULL DEFAULT NULL COMMENT '创建人projectId',
  `update_user_proj_id` varchar(20) NULL DEFAULT NULL COMMENT '修改人projectId',
  PRIMARY KEY (`supervision_total_id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '安全督察总表';

-- ----------------------------
-- Records of dx_security_supervision_total_t_zs
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_table_remark_wjl
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_table_remark_wjl`;
CREATE TABLE `dx_security_table_remark_wjl`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_name` varchar(255) NULL DEFAULT NULL COMMENT '表名',
  `controller` varchar(255) NULL DEFAULT NULL COMMENT '主要增删改查对应的controller',
  `serviceImpl` varchar(255) NULL DEFAULT NULL COMMENT '对应的serviceImpl',
  `entity` varchar(255) NULL DEFAULT NULL COMMENT '实体',
  `table_remark` varchar(400) NULL DEFAULT NULL COMMENT '表说明',
  `attribute1` varchar(255) NULL DEFAULT NULL,
  `attribute2` varchar(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_table_remark_wjl
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_topic
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_topic`;
CREATE TABLE `dx_security_topic`  (
  `topic_id` bigint(20) NOT NULL COMMENT '安全检查问题表id',
  `topic` text NULL COMMENT '问题描述',
  `remedy` text NULL COMMENT '整改措施',
  `type` int(1) NULL DEFAULT NULL COMMENT '1 回复 2 未回复',
  `reason` text NULL COMMENT '未回复原因',
  `unit_level` int(11) NULL DEFAULT NULL COMMENT '发文单位序号',
  `duty_name` varchar(100) NULL DEFAULT NULL COMMENT '责任人名字',
  `duty_phone` varchar(26) NULL DEFAULT NULL COMMENT '责任人电话',
  `data_base_id` bigint(20) NULL DEFAULT NULL COMMENT '隐患类别id',
  `title` varchar(120) NULL DEFAULT NULL COMMENT '隐患类别名字',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `project_name` varchar(255) NULL DEFAULT NULL COMMENT '项目部名字',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gd_name` varchar(255) NULL DEFAULT NULL COMMENT '工点名称',
  `find_time` datetime(0) NULL DEFAULT NULL COMMENT '发现时间',
  `unit_name` varchar(100) NULL DEFAULT NULL COMMENT '单位名字',
  `is_look` tinyint(1) NULL DEFAULT 0 COMMENT 'fale 全员可看 true 项目部领导可看',
  `file_id` int(11) NULL DEFAULT NULL COMMENT '文件id',
  `file_name` varchar(225) NULL DEFAULT NULL COMMENT '文件名字',
  `file_str_id` varchar(225) NULL DEFAULT NULL COMMENT '没不用来存数据 但不能删除',
  `abbreviation` varchar(225) NULL DEFAULT NULL COMMENT '项目部简称',
  `file_type` bigint(20) NULL DEFAULT 1 COMMENT '1 选择后的文件  2 新上传的文件',
  `topic_code` varchar(255) NULL DEFAULT NULL COMMENT '发文编码',
  `remark` varchar(400) NULL DEFAULT NULL COMMENT '备注',
  `topic_index` int(10) NULL DEFAULT NULL COMMENT '问题序号',
  PRIMARY KEY (`topic_id`),
  INDEX `topid_file_id`(`file_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_topic
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_tour_education
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_tour_education`;
CREATE TABLE `dx_security_tour_education`  (
  `tour_id` bigint(20) NOT NULL COMMENT '巡回教育id',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '项目部id',
  `project_name` varchar(120) NULL DEFAULT NULL COMMENT '项目部名字',
  `project_abbreviation` varchar(120) NULL DEFAULT NULL COMMENT '项目部名字简称',
  `education_name` varchar(220) NULL DEFAULT NULL COMMENT '教育名字',
  `education_date` varchar(10) NULL DEFAULT NULL COMMENT '教育日期',
  `education_user_name` varchar(12) NULL DEFAULT NULL COMMENT '教育负责人名字',
  `labor_id` varchar(255) NULL DEFAULT NULL COMMENT '劳务队id',
  `labor_name` varchar(255) NULL DEFAULT NULL COMMENT '劳务队名字',
  `team_id` varchar(255) NULL DEFAULT NULL COMMENT '班组id',
  `team_name` varchar(255) NULL DEFAULT NULL COMMENT '班组名字',
  `content` text NULL COMMENT '教育内容',
  `image1` int(11) NULL DEFAULT NULL COMMENT '图片1',
  `image2` int(11) NULL DEFAULT NULL COMMENT '图片2',
  `image3` int(11) NULL DEFAULT NULL COMMENT '图片3',
  `image4` int(11) NULL DEFAULT NULL COMMENT '图片4',
  `image5` int(11) NULL DEFAULT NULL COMMENT '图片5',
  `table_file_id` varchar(120) NULL DEFAULT NULL COMMENT '签到表文件id',
  `courseware_file_id` varchar(120) NULL DEFAULT NULL COMMENT '课件id',
  `year` int(11) NULL DEFAULT NULL COMMENT '年',
  `month` int(11) NULL DEFAULT NULL COMMENT '月',
  `count` int(11) NULL DEFAULT NULL COMMENT '特殊字段',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(40) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_user_name` varchar(50) NULL DEFAULT NULL COMMENT '创建人Name',
  `status` int(4) NULL DEFAULT NULL COMMENT '完成状态（1==完成   2==未完成）',
  PRIMARY KEY (`tour_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_tour_education
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_tour_education_labor_log_t
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_tour_education_labor_log_t`;
CREATE TABLE `dx_security_tour_education_labor_log_t`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '巡回教育劳务队班组记录表  id',
  `tour_id` varchar(40) NULL DEFAULT NULL COMMENT '巡回教育主表id',
  `project_id` varchar(20) NULL DEFAULT NULL COMMENT '项目部id',
  `project_name` varchar(255) NULL DEFAULT NULL,
  `abbreviation` varchar(255) NULL DEFAULT NULL COMMENT '项目部简称',
  `labor_id` varchar(20) NULL DEFAULT NULL COMMENT '劳务队id',
  `labor_name` varchar(255) NULL DEFAULT NULL COMMENT '劳务队名',
  `team_id` varchar(20) NULL DEFAULT NULL COMMENT '班组id',
  `team_name` varchar(255) NULL DEFAULT NULL COMMENT '班组名称',
  `year` int(11) NULL DEFAULT NULL COMMENT '年',
  `month` int(11) NULL DEFAULT NULL COMMENT '月',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(40) NULL DEFAULT NULL COMMENT '创建人',
  `education_date` varchar(10) NULL DEFAULT NULL COMMENT '教育日期',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_tour_education_labor_log_t
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_tree
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_tree`;
CREATE TABLE `dx_security_tree`  (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NULL DEFAULT NULL,
  `pid` tinyint(4) NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_tree
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_yh_node_content
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_yh_node_content`;
CREATE TABLE `dx_security_yh_node_content`  (
  `yh_node_content_id` bigint(20) NOT NULL COMMENT '隐患子级内容id',
  `data_base_id` bigint(20) NULL DEFAULT NULL COMMENT '安全验收和隐患数据库id',
  `content` varchar(255) NULL DEFAULT NULL COMMENT '安全数据子类容',
  `a_img1_id` varchar(11) NULL DEFAULT NULL COMMENT '隐患图片1 id',
  `a_img2_id` varchar(11) NULL DEFAULT NULL COMMENT '隐患图片2 id',
  `a_img3_id` varchar(11) NULL DEFAULT NULL COMMENT '隐患图片3 id',
  `a_img4_id` varchar(11) NULL DEFAULT NULL COMMENT '隐患图片4 id',
  `yh_parent_id` bigint(20) NULL DEFAULT NULL COMMENT '隐患id',
  `title` varchar(220) NULL DEFAULT NULL COMMENT '类别名字',
  `type` int(11) NULL DEFAULT NULL COMMENT '1 待整改 2 完成整改 3 驳回 4 合格 5 不合格',
  `revise_over_user_id` varchar(64) NULL DEFAULT NULL COMMENT '整改完成人id',
  `revise_over_user_name` varchar(120) NULL DEFAULT NULL COMMENT '整改完成人名字',
  `revise_over_user_phone` varchar(26) NULL DEFAULT NULL COMMENT '整改完成人电话',
  `data_base_node_id` bigint(20) NULL DEFAULT NULL COMMENT '隐患描述表id',
  `b_img1_id` varchar(11) NULL DEFAULT NULL COMMENT '整改图片1 id',
  `b_img2_id` varchar(11) NULL DEFAULT NULL COMMENT '整改图片2 id',
  `b_img3_id` varchar(11) NULL DEFAULT NULL COMMENT '整改图片3 id',
  `b_img4_id` varchar(11) NULL DEFAULT NULL COMMENT '整改图片4 id',
  `revise_remark` text NULL COMMENT '整改备注',
  `fucha_remark` text NULL COMMENT '复查备注',
  `count` int(11) NULL DEFAULT NULL COMMENT '次数',
  PRIMARY KEY (`yh_node_content_id`),
  INDEX `yh_parent_id`(`yh_parent_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_yh_node_content
-- ----------------------------

-- ----------------------------
-- Table structure for dx_security_yh_parent
-- ----------------------------
DROP TABLE IF EXISTS `dx_security_yh_parent`;
CREATE TABLE `dx_security_yh_parent`  (
  `yh_parent_id` bigint(20) NOT NULL COMMENT '隐患id',
  `part_id` int(11) NULL DEFAULT NULL COMMENT '部位id',
  `part_name` varchar(225) NULL DEFAULT NULL COMMENT '部位名字',
  `labor_force_id` int(50) NULL DEFAULT NULL COMMENT '劳务队id',
  `labor_force_name` varchar(255) NULL DEFAULT NULL COMMENT '劳务队名字',
  `team_id` int(50) NULL DEFAULT NULL COMMENT '班组id',
  `team_name` varchar(255) NULL DEFAULT NULL COMMENT '班组名字',
  `revise_begin_time` datetime(0) NULL DEFAULT NULL COMMENT '整改开始时间',
  `revise_end_time` datetime(0) NULL DEFAULT NULL COMMENT '整改到期时间',
  `revise_add_user_id` varchar(64) NULL DEFAULT NULL COMMENT '发现人id',
  `revise_add_user_name` varchar(100) NULL DEFAULT NULL COMMENT '发现人名字',
  `revise_add_user_phone` varchar(26) NULL DEFAULT NULL COMMENT '发现人电话',
  `remarks` text NULL COMMENT '备注json',
  `project_id` int(11) NOT NULL COMMENT '项目部id',
  `project_name` varchar(220) NOT NULL COMMENT '项目部名字',
  `revise_over_user_id` varchar(64) NULL DEFAULT NULL COMMENT '整改完成人id',
  `revise_over_user_name` varchar(100) NULL DEFAULT NULL COMMENT '整改完成人名字',
  `revise_over_user_phone` varchar(26) NULL DEFAULT NULL COMMENT '整改完成人电话',
  `revise_over_time` datetime(0) NULL DEFAULT NULL COMMENT '整改完成时间',
  `type` int(4) NULL DEFAULT NULL COMMENT '1 整改完成 2 待整改 3 驳回 4 复查完成',
  `review_user_id` varchar(64) NULL DEFAULT NULL COMMENT '复查人id',
  `review_user_name` varchar(100) NULL DEFAULT NULL COMMENT '复查人名字',
  `review_user_phone` varchar(26) NULL DEFAULT NULL COMMENT '复查人电话',
  `review_time` datetime(0) NULL DEFAULT NULL COMMENT '复查时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `abbreviation` varchar(120) NULL DEFAULT NULL COMMENT '简称',
  PRIMARY KEY (`yh_parent_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_security_yh_parent
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_amortize
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_amortize`;
CREATE TABLE `dx_storage_amortize`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `issue_no` int(11) NOT NULL COMMENT '期号',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  `create_user_name` varchar(255) NOT NULL COMMENT '制表人',
  `create_user` varchar(255) NOT NULL COMMENT '创建人id',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_storage_amortize
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_amortize_item
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_amortize_item`;
CREATE TABLE `dx_storage_amortize_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `amortize_id` int(11) NOT NULL COMMENT '摊销ID',
  `turnover_id` int(11) NOT NULL COMMENT '周转材料id',
  `distribution_id` int(11) NOT NULL COMMENT '发料id',
  `distribution_item_id` int(11) NOT NULL COMMENT '发料详情id',
  `distribution_no` varchar(255) NOT NULL COMMENT '出库单号',
  `check_id` int(11) NOT NULL COMMENT '点验id',
  `check_item_id` int(11) NOT NULL COMMENT '点验详情ID',
  `procurement_id` int(11) NOT NULL COMMENT '收料id',
  `procurement_no` varchar(255) NOT NULL COMMENT '收料编号',
  `receiver_date` datetime(0) NOT NULL COMMENT '收料日期',
  `supplier_id` int(11) NOT NULL COMMENT '供方Id',
  `supplier_name` varchar(255) NOT NULL COMMENT '供方名称',
  `product_id` int(11) NULL DEFAULT NULL COMMENT '材料id',
  `product_name` varchar(255) NOT NULL COMMENT '材料名称',
  `product_model_id` int(11) NULL DEFAULT NULL COMMENT '规格型号',
  `product_model` varchar(255) NOT NULL COMMENT '规格型号',
  `product_unit` int(11) NOT NULL COMMENT '计量单位ID',
  `product_unit_name` varchar(255) NOT NULL COMMENT '计量单位',
  `turnover_quantity` decimal(10, 3) NOT NULL COMMENT '周转数量',
  `turnover_date` datetime(0) NOT NULL COMMENT '周转日期',
  `handle_status` int(11) NULL DEFAULT NULL COMMENT '处置情况(0:调出,1:变卖)',
  `performance_status` int(11) NULL DEFAULT NULL COMMENT '性能状况(0:完好,1:待修,2:报废)',
  `use_status` int(11) NULL DEFAULT NULL COMMENT '限制/在用(0:闲置,1:在用)',
  `tax_type` int(11) NULL DEFAULT NULL COMMENT '计税方式',
  `unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '原值总价',
  `remain_rate` decimal(10, 3) NULL DEFAULT NULL COMMENT '残值率',
  `remain` decimal(10, 2) NULL DEFAULT NULL COMMENT '残值额',
  `end_rate` decimal(10, 3) NULL DEFAULT NULL COMMENT '完成产值率',
  `total_end_rate` decimal(10, 3) NULL DEFAULT NULL COMMENT '开累完成产值率',
  `total_amortize` decimal(10, 2) NULL DEFAULT NULL COMMENT '开累应摊销',
  `has_amortize` decimal(10, 2) NULL DEFAULT NULL COMMENT '账面已摊',
  `current_amortize` decimal(10, 2) NULL DEFAULT NULL COMMENT '本次摊销',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_storage_amortize_item
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_car_supplier
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_car_supplier`;
CREATE TABLE `dx_storage_car_supplier`  (
  `car_no` varchar(255) NOT NULL,
  `car_no_back` varchar(255) NULL DEFAULT NULL COMMENT '车尾牌照',
  `project_id` int(11) NOT NULL,
  `supplier_id` int(11) NOT NULL,
  PRIMARY KEY (`car_no`, `project_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_storage_car_supplier
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_check
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_check`;
CREATE TABLE `dx_storage_check`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `check_no` varchar(100) NOT NULL COMMENT '点验单号',
  `order_type` int(11) NOT NULL COMMENT '单子类型(0:点验单,1:暂估点验单,2:暂估转点验)',
  `is_turnover` tinyint(1) NULL DEFAULT NULL COMMENT '是否周转(0:否,1:是)',
  `convert_date` datetime(0) NULL DEFAULT NULL COMMENT '转换日期',
  `tax_type` int(1) NOT NULL COMMENT '计税方式(0:简易计税,1:一般计税)',
  `supplier_id` int(11) NOT NULL COMMENT '供方ID',
  `supplier_name` varchar(100) NOT NULL COMMENT '供方名称',
  `check_user_id` varchar(255) NOT NULL COMMENT '点验人id',
  `check_user_name` varchar(100) NOT NULL COMMENT '点验人名称',
  `check_time` datetime(0) NOT NULL COMMENT '点验时间',
  `project_manager` varchar(100) NULL DEFAULT NULL COMMENT '项目经理',
  `materials_manager` varchar(100) NULL DEFAULT NULL COMMENT '物资主管',
  `adjust_total_price` decimal(10, 2) NOT NULL,
  `adjust_total_input_tax_price` decimal(10, 2) NOT NULL,
  `remark` varchar(5000) NULL DEFAULT NULL COMMENT '备注',
  `print_times` int(11) NOT NULL COMMENT '打印次数',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  `create_user_name` varchar(255) NOT NULL COMMENT '制表人',
  `create_user` varchar(255) NOT NULL COMMENT '创建人id',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '物资-点验';

-- ----------------------------
-- Records of dx_storage_check
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_check_item
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_check_item`;
CREATE TABLE `dx_storage_check_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `check_id` int(11) NOT NULL COMMENT '父类点验单ID',
  `statement_id` int(11) NOT NULL COMMENT '收料ID',
  `statement_item_id` int(11) NULL DEFAULT NULL COMMENT '点验单id',
  `product_id` int(11) NULL DEFAULT NULL COMMENT '物资ID',
  `product_name` varchar(100) NOT NULL COMMENT '产品名称',
  `product_model_id` int(11) NULL DEFAULT NULL COMMENT '规格型号',
  `product_model` varchar(255) NOT NULL COMMENT '规格型号',
  `product_unit` int(11) NOT NULL COMMENT '计量单位id',
  `product_unit_name` varchar(100) NOT NULL COMMENT '计量单位',
  `product_quantity` decimal(10, 3) NOT NULL COMMENT '数量',
  `unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '购入单价(不含税)',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '购入金额',
  `tax_unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '含税单价',
  `tax_total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '含税金额',
  `tax_rate` decimal(10, 3) NULL DEFAULT NULL COMMENT '税率',
  `input_tax_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '进项税额',
  `temp_unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '暂估含税单价',
  `temp_total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '暂估含税金额',
  `temp_tax_unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '暂估含税单价',
  `temp_tax_total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '暂估含税金额',
  `temp_tax_rate` decimal(10, 2) NULL DEFAULT NULL COMMENT '暂估税率',
  `temp_input_tax_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '暂估税率',
  `difference_total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '不含税金额差',
  `difference_input_tax_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '进项税额差',
  `custom_no` varchar(100) NULL DEFAULT NULL COMMENT '自定义批号',
  `invoice_no` varchar(100) NULL DEFAULT NULL COMMENT '发票号码',
  `remark` varchar(5000) NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  INDEX `index_check_id`(`check_id`),
  INDEX `index_statement_item_id`(`statement_item_id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '物资-点验-详情列';

-- ----------------------------
-- Records of dx_storage_check_item
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_closing_period
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_closing_period`;
CREATE TABLE `dx_storage_closing_period`  (
  `project_id` int(11) NOT NULL COMMENT '项目部id',
  `closing_period` int(11) NOT NULL COMMENT '结账周期(-1:最后一天,否则为具体的天)',
  PRIMARY KEY (`project_id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '收支存报表-结账周期';

-- ----------------------------
-- Records of dx_storage_closing_period
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_consume
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_consume`;
CREATE TABLE `dx_storage_consume`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `consume_no` varchar(255) NULL DEFAULT NULL COMMENT '消耗单号',
  `part_id` int(11) NULL DEFAULT NULL COMMENT '用料部位id',
  `part_name` varchar(255) NULL DEFAULT NULL COMMENT '用料部位',
  `product_name` varchar(255) NOT NULL COMMENT '发料名称',
  `product_model` varchar(255) NOT NULL COMMENT '规格型号',
  `product_unit_name` varchar(255) NOT NULL COMMENT '计量单位',
  `receiver_id` varchar(255) NULL DEFAULT NULL COMMENT '接收单位id',
  `receiver` varchar(255) NOT NULL COMMENT '接收单位',
  `consume_quantity` decimal(10, 3) NOT NULL COMMENT '消耗数量',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `print_times` int(11) NOT NULL COMMENT '打印次数',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  `create_user_name` varchar(255) NOT NULL COMMENT '制表人',
  `create_user` varchar(255) NOT NULL COMMENT '创建人id',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_storage_consume
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_consume_adjust
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_consume_adjust`;
CREATE TABLE `dx_storage_consume_adjust`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `adjust_no` varchar(255) NOT NULL COMMENT '调整单号',
  `check_id` int(11) NOT NULL COMMENT '点验id',
  `check_no` varchar(255) NOT NULL COMMENT '点验单号',
  `convert_date` datetime(0) NOT NULL COMMENT '调整日期',
  `product_names` varchar(2000) NULL DEFAULT NULL,
  `print_times` int(11) NULL DEFAULT NULL COMMENT '打印次数',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  `create_user_name` varchar(255) NOT NULL COMMENT '制表人',
  `create_user` varchar(255) NOT NULL COMMENT '创建人id',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_storage_consume_adjust
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_consume_item
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_consume_item`;
CREATE TABLE `dx_storage_consume_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `consumer_id` int(11) NOT NULL COMMENT '父类id',
  `distribution_id` int(11) NOT NULL COMMENT '父类id',
  `distribution_item_id` int(11) NOT NULL COMMENT '发料详情ID',
  `check_id` int(11) NULL DEFAULT NULL COMMENT '点验单id',
  `check_item_id` int(11) NULL DEFAULT NULL COMMENT '点验单详情id',
  `adjust_id` int(11) NULL DEFAULT NULL COMMENT '调整单id',
  `tax_type` int(11) NULL DEFAULT NULL COMMENT '计税方式(0:简易计税,1:一般计税)',
  `order_type` int(11) NOT NULL COMMENT '单子类型(0:消耗单,1:暂估消耗单,2:暂估转点验)',
  `convert_date` datetime(0) NULL DEFAULT NULL COMMENT '转换日期',
  `unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '购入单价(不含税)',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '购入金额',
  `temp_unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '暂估购入单价(不含税)',
  `temp_total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '暂估购入金额',
  `difference_total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '差额',
  `procurement_id` int(11) NOT NULL COMMENT '收料ID',
  `procurement_no` varchar(255) NOT NULL COMMENT '收料编号',
  `supplier_id` int(11) NOT NULL COMMENT '供方id',
  `supplier_name` varchar(255) NOT NULL COMMENT '供方名称',
  `product_id` int(11) NULL DEFAULT NULL COMMENT '材料id',
  `product_name` varchar(255) NOT NULL COMMENT '材料名称',
  `product_model_id` int(11) NULL DEFAULT NULL COMMENT '规格型号',
  `product_model` varchar(255) NOT NULL COMMENT '规格型号',
  `product_unit` int(11) NOT NULL COMMENT '计量单位ID',
  `product_unit_name` varchar(255) NOT NULL COMMENT '计量单位',
  `receiver_id` varchar(255) NULL DEFAULT NULL COMMENT '消耗单位id',
  `receiver` varchar(255) NOT NULL COMMENT '消耗单位',
  `consume_quantity` decimal(10, 3) NOT NULL COMMENT '消耗数量',
  `consume_date` datetime(0) NOT NULL COMMENT '消耗时间',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  INDEX `index_procurement_id`(`procurement_id`),
  INDEX `index_consumer_id`(`consumer_id`),
  INDEX `index_distribution_item_id`(`distribution_item_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_storage_consume_item
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_consume_price_adjust
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_consume_price_adjust`;
CREATE TABLE `dx_storage_consume_price_adjust`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `adjust_no` varchar(255) NOT NULL COMMENT '调整单号',
  `is_turnover` tinyint(1) NOT NULL COMMENT '是否周转',
  `unit_type` int(11) NOT NULL COMMENT '接收单位类型(0:发劳务队,1:发项目部,2:发加工厂,3:发其他)',
  `receiver_id` varchar(255) NULL DEFAULT NULL COMMENT '接收单位id',
  `receiver` varchar(255) NOT NULL COMMENT '接收单位',
  `product_id` int(11) NULL DEFAULT NULL COMMENT '材料id',
  `product_name` varchar(255) NOT NULL COMMENT '材料名称',
  `product_model_id` int(11) NULL DEFAULT NULL COMMENT '规格型号',
  `product_model` varchar(255) NOT NULL COMMENT '规格型号',
  `product_unit` int(11) NOT NULL COMMENT '计量单位ID',
  `product_unit_name` varchar(255) NOT NULL COMMENT '计量单位',
  `adjust_price` decimal(10, 2) NOT NULL COMMENT '调整价',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `print_times` int(11) NOT NULL COMMENT '打印次数',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  `create_user_name` varchar(255) NOT NULL COMMENT '制表人',
  `create_user` varchar(255) NOT NULL COMMENT '创建人id',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '消耗价格调整';

-- ----------------------------
-- Records of dx_storage_consume_price_adjust
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_device
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_device`;
CREATE TABLE `dx_storage_device`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `group_id` int(11) NOT NULL COMMENT '机组id',
  `no` varchar(100) NOT NULL COMMENT '编号',
  `main_product` varchar(100) NOT NULL COMMENT '主材料',
  `capacity` decimal(10, 2) NOT NULL COMMENT '容量',
  `surplus_capacity` decimal(10, 2) NOT NULL COMMENT '剩余量',
  `is_delete` tinyint(1) NOT NULL COMMENT '是否删除(0:正常,1:删除)',
  `create_user` varchar(255) NOT NULL COMMENT '创建人id',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_storage_device
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_device_group
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_device_group`;
CREATE TABLE `dx_storage_device_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `mix_id` int(11) NOT NULL COMMENT '所属拌合站',
  `mix_name` varchar(255) NOT NULL COMMENT '拌合站名称',
  `group_name` varchar(100) NOT NULL COMMENT '机组名称',
  `username` varchar(100) NULL DEFAULT NULL COMMENT '联系人',
  `mobile` varchar(100) NULL DEFAULT NULL COMMENT '联系电话',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  `is_delete` tinyint(1) NOT NULL COMMENT '是否删除(0:正常,1:删除)',
  `create_user` varchar(255) NOT NULL COMMENT '创建人id',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_storage_device_group
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_distribution
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_distribution`;
CREATE TABLE `dx_storage_distribution`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_no` varchar(255) NULL DEFAULT NULL COMMENT '发料单号',
  `unit_type` int(11) NOT NULL COMMENT '接收单位类型(0:发劳务队,1:发项目部,2:发加工厂,3:发其他)',
  `receiver_id` int(11) NULL DEFAULT NULL COMMENT '接收单位id',
  `receiver` varchar(100) NOT NULL COMMENT '接收单位',
  `product_name` varchar(100) NOT NULL COMMENT '发料名称',
  `product_model` varchar(100) NOT NULL COMMENT '规格型号',
  `product_unit_name` varchar(100) NOT NULL COMMENT '计量单位',
  `distribution_quantity` decimal(10, 3) NOT NULL COMMENT '发料数量',
  `distribution_date` datetime(0) NOT NULL COMMENT '发料时间',
  `distribution_user` varchar(100) NOT NULL COMMENT '发料人',
  `remark` varchar(5000) NULL DEFAULT NULL COMMENT '备注',
  `gave_back_quantity` decimal(10, 3) NULL DEFAULT NULL COMMENT '退料数量',
  `consume_quantity` decimal(10, 3) NULL DEFAULT NULL COMMENT '消耗数量',
  `print_times` int(11) NOT NULL COMMENT '打印次数',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  `create_user_name` varchar(255) NOT NULL COMMENT '制表人',
  `create_user` varchar(255) NOT NULL COMMENT '创建人id',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '分料';

-- ----------------------------
-- Records of dx_storage_distribution
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_distribution_history
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_distribution_history`;
CREATE TABLE `dx_storage_distribution_history`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `distribution_item_id` int(11) NOT NULL COMMENT '发料id',
  `procurement_id` int(11) NOT NULL COMMENT '收料id',
  `procurement_no` varchar(255) NOT NULL COMMENT '收料编号',
  `product_id` int(11) NULL DEFAULT NULL COMMENT '材料id',
  `product_name` varchar(100) NOT NULL COMMENT '材料名称',
  `product_model_id` int(11) NULL DEFAULT NULL COMMENT '规格型号',
  `product_model` varchar(100) NOT NULL COMMENT '规格型号',
  `product_unit` int(11) NOT NULL COMMENT '计量单位ID',
  `product_unit_name` varchar(100) NOT NULL COMMENT '计量单位',
  `store_date` datetime(0) NOT NULL COMMENT '存料时间',
  `type` int(10) NOT NULL COMMENT '记录类型:(0:发料,1:退料,2:周转)',
  `quantity` decimal(10, 3) NOT NULL COMMENT '数量',
  `is_turnover` tinyint(1) NOT NULL COMMENT '是否周转材料',
  `handle_username` varchar(100) NOT NULL COMMENT '操作人',
  `project_id` int(11) NOT NULL COMMENT '所属项目项目部',
  `create_user` varchar(255) NOT NULL COMMENT '创建人id',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_storage_distribution_history
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_distribution_item
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_distribution_item`;
CREATE TABLE `dx_storage_distribution_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `distribution_id` int(11) NOT NULL COMMENT '父类id',
  `store_id` int(11) NOT NULL COMMENT '存料id',
  `procurement_id` int(11) NOT NULL COMMENT '收料ID',
  `procurement_no` varchar(255) NOT NULL COMMENT '收料编号',
  `supplier_id` int(11) NOT NULL COMMENT '供方id',
  `supplier_name` varchar(255) NOT NULL COMMENT '供方名称',
  `product_id` int(11) NULL DEFAULT NULL COMMENT '材料id',
  `product_name` varchar(100) NOT NULL COMMENT '材料名称',
  `product_model_id` int(11) NULL DEFAULT NULL COMMENT '规格型号',
  `product_model` varchar(100) NOT NULL COMMENT '规格型号',
  `product_unit` int(11) NOT NULL COMMENT '计量单位ID',
  `product_unit_name` varchar(100) NOT NULL COMMENT '计量单位',
  `send_quantity` decimal(10, 3) NOT NULL COMMENT '发料数量',
  `is_turnover` tinyint(1) NOT NULL COMMENT '是否周转材料',
  `is_will_build` tinyint(1) NOT NULL COMMENT '是否临建',
  `is_security_fee` tinyint(1) NOT NULL COMMENT '是否安全生产费',
  `remark` varchar(5000) NULL DEFAULT NULL COMMENT '备注',
  `gave_back_quantity` decimal(10, 3) NOT NULL COMMENT '退料数量',
  `consume_quantity` double(10, 3) NOT NULL COMMENT '消耗数量',
  PRIMARY KEY (`id`),
  INDEX `index_procurement_id`(`procurement_id`),
  INDEX `index_distribution_id`(`distribution_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_storage_distribution_item
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_gave_back
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_gave_back`;
CREATE TABLE `dx_storage_gave_back`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_no` varchar(255) NULL DEFAULT NULL COMMENT '退库单号',
  `unit_type` int(11) NULL DEFAULT NULL COMMENT '接收单位类型(0:发劳务队,1:发项目部,2:发加工厂,3:发其他)',
  `receiver_id` int(11) NULL DEFAULT NULL COMMENT '接收单位id',
  `receiver` varchar(255) NULL DEFAULT NULL COMMENT '接收单位',
  `product_name` varchar(255) NOT NULL COMMENT '退料名称',
  `product_model` varchar(255) NOT NULL COMMENT '规格型号',
  `product_unit_name` varchar(255) NOT NULL COMMENT '计量单位',
  `gave_back_date` datetime(0) NOT NULL COMMENT '退料时间',
  `gave_back_user` varchar(255) NOT NULL COMMENT '退料人',
  `give_back_quantity` decimal(10, 3) NOT NULL COMMENT '退料数量',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `print_times` int(11) NOT NULL COMMENT '打印次数',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  `create_user_name` varchar(255) NOT NULL COMMENT '制表人',
  `create_user` varchar(255) NOT NULL COMMENT '创建人id',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_storage_gave_back
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_gave_back_item
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_gave_back_item`;
CREATE TABLE `dx_storage_gave_back_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `gave_back_id` int(11) NOT NULL COMMENT '退料id',
  `procurement_id` int(11) NOT NULL COMMENT '收料ID',
  `procurement_no` varchar(255) NOT NULL COMMENT '收料编号',
  `distribution_id` int(11) NOT NULL COMMENT '发料ID',
  `distribution_item_id` int(11) NOT NULL COMMENT '发料详情id',
  `product_id` int(11) NULL DEFAULT NULL COMMENT '材料id',
  `product_name` varchar(100) NOT NULL COMMENT '材料名称',
  `product_model` varchar(100) NOT NULL COMMENT '规格型号',
  `product_model_id` int(11) NULL DEFAULT NULL COMMENT '规格型号',
  `product_unit` int(11) NOT NULL COMMENT '计量单位ID',
  `product_unit_name` varchar(100) NOT NULL COMMENT '计量单位',
  `give_back_quantity` decimal(10, 3) NOT NULL COMMENT '退料量',
  `remark` varchar(5000) NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_storage_gave_back_item
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_market
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_market`;
CREATE TABLE `dx_storage_market`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `product_id` int(11) NULL DEFAULT NULL COMMENT '材料id',
  `product_name` varchar(255) NOT NULL COMMENT '材料名称',
  `product_model_id` int(11) NULL DEFAULT NULL COMMENT '规格型号',
  `product_model` varchar(255) NOT NULL COMMENT '材料型号',
  `product_unit` int(11) NOT NULL COMMENT '计量单位id',
  `product_unit_name` varchar(255) NOT NULL COMMENT '计量单位',
  `product_quantity` decimal(10, 3) NOT NULL COMMENT '数量',
  `contact_id` varchar(255) NOT NULL COMMENT '联系人id',
  `contact_name` varchar(255) NOT NULL COMMENT '联系人',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `files` text NULL COMMENT '附件JSON',
  `status` int(11) NOT NULL COMMENT '状态(0:发布中,1:已完成)',
  `project_id` int(11) NOT NULL COMMENT '创建项目部',
  `project_name` varchar(255) NOT NULL COMMENT '项目部名称',
  `create_user` varchar(255) NOT NULL COMMENT '创建人',
  `create_date` datetime(0) NOT NULL COMMENT '创建日期',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_storage_market
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_pound_device
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_pound_device`;
CREATE TABLE `dx_storage_pound_device`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `project_id` int(11) NOT NULL COMMENT '项目部ID',
  `project_name` varchar(255) NOT NULL COMMENT '项目部名称',
  `pound_house_no` varchar(255) NOT NULL COMMENT '磅房号',
  `front` varchar(255) NULL DEFAULT NULL COMMENT '车前摄像头',
  `after` varchar(255) NULL DEFAULT NULL COMMENT '车后摄像头',
  `top` varchar(255) NULL DEFAULT NULL COMMENT '车顶摄像头',
  `house` varchar(255) NULL DEFAULT NULL COMMENT '磅房摄像头',
  `front_pic` varchar(255) NULL DEFAULT NULL COMMENT '车前摄像头图片',
  `after_pic` varchar(255) NULL DEFAULT NULL COMMENT '车后摄像头图片',
  `top_pic` varchar(255) NULL DEFAULT NULL COMMENT '车顶摄像头图片',
  `house_pic` varchar(255) NULL DEFAULT NULL COMMENT '磅房摄像头图片',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '磅房IP';

-- ----------------------------
-- Records of dx_storage_pound_device
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_prepared_consume
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_prepared_consume`;
CREATE TABLE `dx_storage_prepared_consume`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `product_id` int(11) NOT NULL COMMENT '物资id',
  `product_name` varchar(255) NOT NULL COMMENT '物资',
  `product_model_id` int(11) NOT NULL COMMENT '规格型号id',
  `product_model_name` varchar(255) NOT NULL COMMENT '规格型号',
  `product_unit` int(11) NOT NULL COMMENT '计量单位id',
  `product_unit_name` varchar(255) NOT NULL COMMENT '计量单位',
  `quantity` decimal(15, 3) NOT NULL COMMENT '消耗数量',
  `consume_address` varchar(255) NULL DEFAULT NULL COMMENT '消耗地点',
  `origin` int(11) NOT NULL COMMENT '来源: 0:拌合站,1:加工厂',
  `status` int(11) NOT NULL COMMENT '状态(0:未处理,1:已处理)',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  `create_user` varchar(255) NOT NULL COMMENT '创建人id',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '预先消耗';

-- ----------------------------
-- Records of dx_storage_prepared_consume
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_procurement
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_procurement`;
CREATE TABLE `dx_storage_procurement`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `procurement_no` varchar(255) NOT NULL COMMENT '收料编号',
  `receiver_id` varchar(255) NOT NULL COMMENT '收料员ID',
  `receiver_name` varchar(100) NOT NULL COMMENT '收料员',
  `receive_date` datetime(0) NOT NULL COMMENT '收料时间',
  `receive_no` varchar(255) NULL DEFAULT NULL COMMENT '收料单号',
  `batch` varchar(100) NULL DEFAULT NULL COMMENT '批次',
  `supplier_id` int(11) NULL DEFAULT NULL COMMENT '供方ID',
  `supplier_name` varchar(100) NOT NULL COMMENT '供方名称',
  `liaison` varchar(100) NULL DEFAULT NULL COMMENT '联系人',
  `mobile` varchar(100) NULL DEFAULT NULL COMMENT '电话',
  `product_id` int(11) NULL DEFAULT NULL COMMENT '材料ID',
  `product_name` varchar(100) NOT NULL COMMENT '产品名称',
  `product_model_id` int(11) NULL DEFAULT NULL COMMENT '规格型号',
  `product_model` varchar(255) NOT NULL COMMENT '规格型号',
  `product_unit` int(11) NOT NULL COMMENT '计量单位id',
  `product_unit_name` varchar(100) NOT NULL COMMENT '计量单位',
  `product_quantity` decimal(10, 3) NOT NULL COMMENT '数量',
  `product_weigh_unit` int(11) NULL DEFAULT NULL COMMENT '过磅单位id',
  `product_weigh_unit_name` varchar(100) NULL DEFAULT NULL COMMENT '过磅单位',
  `product_weigh_rough` decimal(10, 3) NULL DEFAULT NULL COMMENT '过磅毛重',
  `product_weight_deduct_type` int(11) NULL DEFAULT NULL COMMENT '过磅扣重类型(0:扣皮重,1:扣净重)',
  `product_weight_deduct_way` int(11) NULL DEFAULT NULL COMMENT '过磅扣重方式(0:百分比,1:公斤)',
  `product_weight_deduct` decimal(10, 3) NULL DEFAULT NULL COMMENT '过磅扣重',
  `product_weight_tare` decimal(10, 3) NULL DEFAULT NULL COMMENT '过磅皮重',
  `product_weight_suttle` decimal(10, 3) NULL DEFAULT NULL COMMENT '过磅净重',
  `store_surplus_quantity` decimal(10, 3) NOT NULL COMMENT '剩余存料数量',
  `unit_price` decimal(25, 15) NULL DEFAULT NULL COMMENT '单价',
  `car_no` varchar(100) NULL DEFAULT NULL COMMENT '车牌号',
  `factory_no` varchar(100) NULL DEFAULT NULL COMMENT '厂家批号',
  `remark` varchar(5000) NULL DEFAULT NULL COMMENT '备注',
  `photos` text NULL COMMENT '照片(数据格式:[{\"part\":\"\",\"fid\":\"\"}])',
  `receive_status` tinyint(1) NOT NULL COMMENT '收料单状态(0:未开单,1:已开单)',
  `statement_status` tinyint(1) NOT NULL COMMENT '对账单状态(0:未开单,1:已开单)',
  `check_status` tinyint(1) NOT NULL COMMENT '点验单状态(0:未开单,1:已开单)',
  `is_last` tinyint(1) NOT NULL COMMENT '是否置后(0:是,1:否)',
  `origin` int(11) NOT NULL COMMENT '来源(0:PC,1:拌合站)',
  `pound_no` varchar(255) NULL DEFAULT NULL COMMENT '磅房编号',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  `create_user` varchar(255) NOT NULL COMMENT '创建人id',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_storage_procurement
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_receive
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_receive`;
CREATE TABLE `dx_storage_receive`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `receive_no` varchar(100) NOT NULL COMMENT '收料单号',
  `supplier_id` int(11) NOT NULL COMMENT '供方ID',
  `supplier_name` varchar(100) NOT NULL COMMENT '供方名称',
  `open_user_id` varchar(255) NOT NULL COMMENT '点验人id',
  `open_user_name` varchar(100) NOT NULL COMMENT '点验人名称',
  `open_date` datetime(0) NOT NULL COMMENT '开单时间',
  `remark` varchar(5000) NULL DEFAULT NULL COMMENT '备注',
  `print_times` int(11) NOT NULL COMMENT '打印次数',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  `create_user_name` varchar(255) NOT NULL COMMENT '制表人',
  `create_user` varchar(255) NOT NULL COMMENT '创建人id',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_storage_receive
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_receive_distribution_store
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_receive_distribution_store`;
CREATE TABLE `dx_storage_receive_distribution_store`  (
  `id` int(11) NOT NULL COMMENT 'id',
  `project_id` int(11) NOT NULL COMMENT '项目部ID',
  `year` int(11) NOT NULL COMMENT '年份',
  `month` int(11) NOT NULL COMMENT '月份',
  `zj` decimal(15, 3) NULL DEFAULT NULL COMMENT '总计数量',
  `zj_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '总计单价',
  `zj_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '总计消费数量',
  `zj_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '总计施工生产数量',
  `zj_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '总计消费其他用数量',
  `nyl` decimal(15, 3) NULL DEFAULT NULL COMMENT '能源类数量',
  `nyl_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '能源类单价',
  `nyl_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '能源类消费数量',
  `nyl_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '能源类施工生产数量',
  `nyl_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '能源类消费其他用数量',
  `mt` decimal(15, 3) NULL DEFAULT NULL COMMENT '煤炭数量',
  `mt_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '煤炭单价',
  `mt_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '煤炭消费数量',
  `mt_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '煤炭施工生产数量',
  `mt_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '煤炭消费其他用数量',
  `dl` decimal(15, 3) NULL DEFAULT NULL COMMENT '电力数量',
  `dl_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '电力单价',
  `dl_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '电力消费数量',
  `dl_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '电力施工生产数量',
  `dl_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '电力消费其他用数量',
  `qy` decimal(15, 3) NULL DEFAULT NULL COMMENT '汽油数量',
  `qy_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '汽油单价',
  `qy_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '汽油消费数量',
  `qy_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '汽油施工生产数量',
  `qy_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '汽油消费其他用数量',
  `cy` decimal(15, 3) NULL DEFAULT NULL COMMENT '柴油数量',
  `cy_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '柴油单价',
  `cy_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '柴油消费数量',
  `cy_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '柴油施工生产数量',
  `cy_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '柴油消费其他用数量',
  `rly` decimal(15, 3) NULL DEFAULT NULL COMMENT '燃料油数量',
  `rly_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '燃料油单价',
  `rly_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '燃料油消费数量',
  `rly_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '燃料油施工生产数量',
  `rly_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '燃料油消费其他用数量',
  `trq` decimal(15, 3) NULL DEFAULT NULL COMMENT '天然气数量',
  `trq_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '天然气单价',
  `trq_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '天然气消费数量',
  `trq_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '天然气施工生产数量',
  `trq_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '天然气消费其他用数量',
  `ycll` decimal(15, 3) NULL DEFAULT NULL COMMENT '原材料类数量',
  `ycll_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '原材料类单价',
  `ycll_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '原材料类消费数量',
  `ycll_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '原材料类施工生产数量',
  `ycll_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '原材料类消费其他用数量',
  `hsjs` decimal(15, 3) NULL DEFAULT NULL COMMENT '黑色金属数量',
  `hsjs_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '黑色金属单价',
  `hsjs_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '黑色金属消费数量',
  `hsjs_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '黑色金属施工生产数量',
  `hsjs_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '黑色金属消费其他用数量',
  `gc` decimal(15, 3) NULL DEFAULT NULL COMMENT '钢材数量',
  `gc_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '钢材单价',
  `gc_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '钢材消费数量',
  `gc_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '钢材施工生产数量',
  `gc_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '钢材消费其他用数量',
  `jzgc` decimal(15, 3) NULL DEFAULT NULL COMMENT '建筑钢材数量',
  `jzgc_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '建筑钢材单价',
  `jzgc_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '建筑钢材消费数量',
  `jzgc_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '建筑钢材施工生产数量',
  `jzgc_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '建筑钢材消费其他用数量',
  `xg` decimal(15, 3) NULL DEFAULT NULL COMMENT '型钢数量',
  `xg_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '型钢单价',
  `xg_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '型钢消费数量',
  `xg_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '型钢施工生产数量',
  `xg_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '型钢消费其他用数量',
  `qthsjs` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他黑色金属数量',
  `qthsjs_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他黑色金属单价',
  `qthsjs_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他黑色金属消费数量',
  `qthsjs_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他黑色金属施工生产数量',
  `qthsjs_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他黑色金属消费其他用数量',
  `ysjsl` decimal(15, 3) NULL DEFAULT NULL COMMENT '有色金属类数量',
  `ysjsl_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '有色金属类单价',
  `ysjsl_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '有色金属类消费数量',
  `ysjsl_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '有色金属类施工生产数量',
  `ysjsl_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '有色金属类消费其他用数量',
  `hgl` decimal(15, 3) NULL DEFAULT NULL COMMENT '化工类数量',
  `hgl_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '化工类单价',
  `hgl_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '化工类消费数量',
  `hgl_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '化工类施工生产数量',
  `hgl_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '化工类消费其他用数量',
  `hgp` decimal(15, 3) NULL DEFAULT NULL COMMENT '火工品数量',
  `hgp_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '火工品单价',
  `hgp_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '火工品消费数量',
  `hgp_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '火工品施工生产数量',
  `hgp_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '火工品消费其他用数量',
  `zy` decimal(15, 3) NULL DEFAULT NULL COMMENT '炸药数量',
  `zy_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '炸药单价',
  `zy_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '炸药消费数量',
  `zy_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '炸药施工生产数量',
  `zy_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '炸药消费其他用数量',
  `lg` decimal(15, 3) NULL DEFAULT NULL COMMENT '雷管数量',
  `lg_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '雷管单价',
  `lg_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '雷管消费数量',
  `lg_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '雷管施工生产数量',
  `lg_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '雷管消费其他用数量',
  `dhs` decimal(15, 3) NULL DEFAULT NULL COMMENT '导火索数量',
  `dhs_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '导火索单价',
  `dhs_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '导火索消费数量',
  `dhs_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '导火索施工生产数量',
  `dhs_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '导火索消费其他用数量',
  `fscl` decimal(15, 3) NULL DEFAULT NULL COMMENT '防水材料数量',
  `fscl_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '防水材料单价',
  `fscl_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '防水材料消费数量',
  `fscl_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '防水材料施工生产数量',
  `fscl_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '防水材料消费其他用数量',
  `fsb` decimal(15, 3) NULL DEFAULT NULL COMMENT '防水板数量',
  `fsb_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '防水板单价',
  `fsb_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '防水板消费数量',
  `fsb_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '防水板施工生产数量',
  `fsb_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '防水板消费其他用数量',
  `zst` decimal(15, 3) NULL DEFAULT NULL COMMENT '止水条数量',
  `zst_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '止水条单价',
  `zst_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '止水条消费数量',
  `zst_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '止水条施工生产数量',
  `zst_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '止水条消费其他用数量',
  `zsd` decimal(15, 3) NULL DEFAULT NULL COMMENT '止水带数量',
  `zsd_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '止水带单价',
  `zsd_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '止水带消费数量',
  `zsd_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '止水带施工生产数量',
  `zsd_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '止水带消费其他用数量',
  `qtfscl` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他防水材料数量',
  `qtfscl_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他防水材料单价',
  `qtfscl_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他防水材料消费数量',
  `qtfscl_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他防水材料施工生产数量',
  `qtfscl_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他防水材料消费其他用数量',
  `jcl` decimal(15, 3) NULL DEFAULT NULL COMMENT '建材类数量',
  `jcl_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '建材类单价',
  `jcl_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '建材类消费数量',
  `jcl_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '建材类施工生产数量',
  `jcl_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '建材类消费其他用数量',
  `sn` decimal(15, 3) NULL DEFAULT NULL COMMENT '水泥数量',
  `sn_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '水泥单价',
  `sn_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '水泥消费数量',
  `sn_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '水泥施工生产数量',
  `sn_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '水泥消费其他用数量',
  `s` decimal(15, 3) NULL DEFAULT NULL COMMENT '砂数量',
  `s_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '砂单价',
  `s_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '砂消费数量',
  `s_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '砂施工生产数量',
  `s_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '砂消费其他用数量',
  `zcjgs` decimal(15, 3) NULL DEFAULT NULL COMMENT '自产加工沙数量',
  `zcjgs_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '自产加工沙单价',
  `zcjgs_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '自产加工沙消费数量',
  `zcjgs_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '自产加工沙施工生产数量',
  `zcjgs_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '自产加工沙消费其他用数量',
  `wgs` decimal(15, 3) NULL DEFAULT NULL COMMENT '外购纱数量',
  `wgs_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '外购纱单价',
  `wgs_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '外购纱消费数量',
  `wgs_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '外购纱施工生产数量',
  `wgs_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '外购纱消费其他用数量',
  `ss` decimal(15, 3) NULL DEFAULT NULL COMMENT '碎石数量',
  `ss_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '碎石单价',
  `ss_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '碎石消费数量',
  `ss_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '碎石施工生产数量',
  `ss_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '碎石消费其他用数量',
  `zcjgss` decimal(15, 3) NULL DEFAULT NULL COMMENT '自产加工碎石数量',
  `zcjgss_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '自产加工碎石单价',
  `zcjgss_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '自产加工碎石消费数量',
  `zcjgss_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '自产加工碎石施工生产数量',
  `zcjgss_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '自产加工碎石消费其他用数量',
  `wgss` decimal(15, 3) NULL DEFAULT NULL COMMENT '外购碎石数量',
  `wgss_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '外购碎石单价',
  `wgss_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '外购碎石消费数量',
  `wgss_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '外购碎石施工生产数量',
  `wgss_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '外购碎石消费其他用数量',
  `wjj` decimal(15, 3) NULL DEFAULT NULL COMMENT '外加剂数量',
  `wjj_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '外加剂单价',
  `wjj_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '外加剂消费数量',
  `wjj_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '外加剂施工生产数量',
  `wjj_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '外加剂消费其他用数量',
  `jsj` decimal(15, 3) NULL DEFAULT NULL COMMENT '减水剂数量',
  `jsj_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '减水剂单价',
  `jsj_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '减水剂消费数量',
  `jsj_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '减水剂施工生产数量',
  `jsj_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '减水剂消费其他用数量',
  `snj` decimal(15, 3) NULL DEFAULT NULL COMMENT '速凝剂数量',
  `snj_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '速凝剂单价',
  `snj_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '速凝剂消费数量',
  `snj_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '速凝剂施工生产数量',
  `snj_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '速凝剂消费其他用数量',
  `qtwjj` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他外加剂数量',
  `qtwjj_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他外加剂单价',
  `qtwjj_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他外加剂消费数量',
  `qtwjj_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他外加剂施工生产数量',
  `qtwjj_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他外加剂消费其他用数量',
  `sphnt` decimal(15, 3) NULL DEFAULT NULL COMMENT '商品混凝土数量',
  `sphnt_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '商品混凝土单价',
  `sphnt_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '商品混凝土消费数量',
  `sphnt_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '商品混凝土施工生产数量',
  `sphnt_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '商品混凝土消费其他用数量',
  `lq` decimal(15, 3) NULL DEFAULT NULL COMMENT '沥青数量',
  `lq_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '沥青单价',
  `lq_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '沥青消费数量',
  `lq_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '沥青施工生产数量',
  `lq_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '沥青消费其他用数量',
  `jzlq` decimal(15, 3) NULL DEFAULT NULL COMMENT '基质沥青数量',
  `jzlq_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '基质沥青单价',
  `jzlq_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '基质沥青消费数量',
  `jzlq_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '基质沥青施工生产数量',
  `jzlq_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '基质沥青消费其他用数量',
  `gxlq` decimal(15, 3) NULL DEFAULT NULL COMMENT '改性沥青数量',
  `gxlq_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '改性沥青单价',
  `gxlq_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '改性沥青消费数量',
  `gxlq_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '改性沥青施工生产数量',
  `gxlq_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '改性沥青消费其他用数量',
  `tgcl` decimal(15, 3) NULL DEFAULT NULL COMMENT '土工材料数量',
  `tgcl_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '土工材料单价',
  `tgcl_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '土工材料消费数量',
  `tgcl_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '土工材料施工生产数量',
  `tgcl_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '土工材料消费其他用数量',
  `tgb` decimal(15, 3) NULL DEFAULT NULL COMMENT '土工布数量',
  `tgb_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '土工布单价',
  `tgb_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '土工布消费数量',
  `tgb_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '土工布施工生产数量',
  `tgb_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '土工布消费其他用数量',
  `tggs` decimal(15, 3) NULL DEFAULT NULL COMMENT '土工格栅数量',
  `tggs_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '土工格栅单价',
  `tggs_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '土工格栅消费数量',
  `tggs_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '土工格栅施工生产数量',
  `tggs_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '土工格栅消费其他用数量',
  `qttgcl` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他土工材料数量',
  `qttgcl_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他土工材料单价',
  `qttgcl_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他土工材料消费数量',
  `qttgcl_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他土工材料施工生产数量',
  `qttgcl_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他土工材料消费其他用数量',
  `mcl` decimal(15, 3) NULL DEFAULT NULL COMMENT '木材类数量',
  `mcl_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '木材类单价',
  `mcl_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '木材类消费数量',
  `mcl_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '木材类施工生产数量',
  `mcl_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '木材类消费其他用数量',
  `ym` decimal(15, 3) NULL DEFAULT NULL COMMENT '原木数量',
  `ym_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '原木单价',
  `ym_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '原木消费数量',
  `ym_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '原木施工生产数量',
  `ym_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '原木消费其他用数量',
  `jc` decimal(15, 3) NULL DEFAULT NULL COMMENT '锯材数量',
  `jc_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '锯材单价',
  `jc_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '锯材消费数量',
  `jc_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '锯材施工生产数量',
  `jc_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '锯材消费其他用数量',
  `jhb` decimal(15, 3) NULL DEFAULT NULL COMMENT '胶合板数量',
  `jhb_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '胶合板单价',
  `jhb_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '胶合板消费数量',
  `jhb_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '胶合板施工生产数量',
  `jhb_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '胶合板消费其他用数量',
  `jszpl` decimal(15, 3) NULL DEFAULT NULL COMMENT '金属制品类数量',
  `jszpl_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '金属制品类单价',
  `jszpl_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '金属制品类消费数量',
  `jszpl_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '金属制品类施工生产数量',
  `jszpl_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '金属制品类消费其他用数量',
  `gjx` decimal(15, 3) NULL DEFAULT NULL COMMENT '钢绞线数量',
  `gjx_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '钢绞线单价',
  `gjx_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '钢绞线消费数量',
  `gjx_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '钢绞线施工生产数量',
  `gjx_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '钢绞线消费其他用数量',
  `mj` decimal(15, 3) NULL DEFAULT NULL COMMENT '锚具数量',
  `mj_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '锚具单价',
  `mj_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '锚具消费数量',
  `mj_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '锚具施工生产数量',
  `mj_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '锚具消费其他用数量',
  `ycxzzjdl` decimal(15, 3) NULL DEFAULT NULL COMMENT '一次性转值机电类数量',
  `ycxzzjdl_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '一次性转值机电类单价',
  `ycxzzjdl_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '一次性转值机电类消费数量',
  `ycxzzjdl_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '一次性转值机电类施工生产数量',
  `ycxzzjdl_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '一次性转值机电类消费其他用数量',
  `qtl` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他类数量',
  `qtl_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他类单价',
  `qtl_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他类消费数量',
  `qtl_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他类施工生产数量',
  `qtl_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '其他类消费其他用数量',
  `mg` decimal(15, 3) NULL DEFAULT NULL COMMENT '锚杆数量',
  `mg_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '锚杆单价',
  `mg_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '锚杆消费数量',
  `mg_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '锚杆施工生产数量',
  `mg_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '锚杆消费其他用数量',
  `esxcl` decimal(15, 3) NULL DEFAULT NULL COMMENT '二三项材料数量',
  `esxcl_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '二三项材料单价',
  `esxcl_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '二三项材料消费数量',
  `esxcl_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '二三项材料施工生产数量',
  `esxcl_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '二三项材料消费其他用数量',
  `zzcll` decimal(15, 3) NULL DEFAULT NULL COMMENT '周转材料类数量',
  `zzcll_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '周转材料类单价',
  `zzcll_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '周转材料类消费数量',
  `zzcll_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '周转材料类施工生产数量',
  `zzcll_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '周转材料类消费其他用数量',
  `lmb` decimal(15, 3) NULL DEFAULT NULL COMMENT '梁模板数量',
  `lmb_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '梁模板单价',
  `lmb_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '梁模板消费数量',
  `lmb_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '梁模板施工生产数量',
  `lmb_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '梁模板消费其他用数量',
  `pmb` decimal(15, 3) NULL DEFAULT NULL COMMENT '平模板数量',
  `pmb_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '平模板单价',
  `pmb_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '平模板消费数量',
  `pmb_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '平模板施工生产数量',
  `pmb_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '平模板消费其他用数量',
  `dzmb` decimal(15, 3) NULL DEFAULT NULL COMMENT '墩柱模板数量',
  `dzmb_unit_price` decimal(15, 3) NULL DEFAULT NULL COMMENT '墩柱模板单价',
  `dzmb_consume` decimal(15, 3) NULL DEFAULT NULL COMMENT '墩柱模板消费数量',
  `dzmb_consume_product` decimal(15, 3) NULL DEFAULT NULL COMMENT '墩柱模板施工生产数量',
  `dzmb_consume_other` decimal(15, 3) NULL DEFAULT NULL COMMENT '墩柱模板消费其他用数量',
  PRIMARY KEY (`id`),
  INDEX `index_year`(`year`),
  INDEX `index_month`(`month`)
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '材料收支存消耗表';

-- ----------------------------
-- Records of dx_storage_receive_distribution_store
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_receive_item
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_receive_item`;
CREATE TABLE `dx_storage_receive_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `receive_id` int(11) NOT NULL COMMENT '父类id',
  `procurement_id` int(11) NOT NULL COMMENT '收料ID',
  `procurement_no` varchar(255) NOT NULL COMMENT '收料编号',
  `product_id` int(11) NULL DEFAULT NULL COMMENT '产品id',
  `product_name` varchar(100) NOT NULL COMMENT '产品名称',
  `product_model_id` int(11) NULL DEFAULT NULL COMMENT '规格型号',
  `product_model` varchar(100) NOT NULL COMMENT '规格型号',
  `product_unit` int(11) NOT NULL COMMENT '计量单位id',
  `product_unit_name` varchar(100) NOT NULL COMMENT '计量单位',
  `product_quantity` decimal(10, 3) NOT NULL COMMENT '数量',
  `product_weigh_unit` int(11) NULL DEFAULT NULL COMMENT '过磅单位id',
  `product_weigh_unit_name` varchar(100) NULL DEFAULT NULL COMMENT '过磅单位',
  `product_weigh_rough` decimal(10, 3) NULL DEFAULT NULL COMMENT '过磅毛重',
  `receiver_id` varchar(255) NOT NULL COMMENT '收料员ID',
  `receiver_name` varchar(100) NOT NULL COMMENT '收料员',
  `receive_date` datetime(0) NOT NULL COMMENT '收料时间',
  `car_no` varchar(100) NULL DEFAULT NULL COMMENT '车牌号',
  `remark` varchar(5000) NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_storage_receive_item
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_statement
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_statement`;
CREATE TABLE `dx_storage_statement`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `issue_no` int(11) NOT NULL COMMENT '期号',
  `order_type` int(11) NOT NULL COMMENT '单子类型(0:对账单,1:暂估对账单,2:暂估转对账)',
  `statement_no` varchar(100) NOT NULL COMMENT '对账单号',
  `supplier_id` int(11) NOT NULL COMMENT '供方id',
  `supplier_name` varchar(100) NOT NULL COMMENT '供方名称',
  `supply_time_start` datetime(0) NOT NULL COMMENT '供货时段起',
  `supply_time_end` datetime(0) NOT NULL COMMENT '供货时段止',
  `remark` text NULL COMMENT '备注',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  `project_name` varchar(100) NOT NULL COMMENT '所属项目部',
  `print_times` int(11) NOT NULL COMMENT '打印次数',
  `create_user_name` varchar(255) NOT NULL COMMENT '制表人',
  `create_user` varchar(255) NOT NULL COMMENT '创建人id',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '对账单';

-- ----------------------------
-- Records of dx_storage_statement
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_statement_item
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_statement_item`;
CREATE TABLE `dx_storage_statement_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `statement_id` int(11) NOT NULL COMMENT '对账id',
  `supplier_id` int(11) NOT NULL COMMENT '供方id',
  `supplier_name` varchar(255) NOT NULL COMMENT '供方名称',
  `product_id` int(11) NULL DEFAULT NULL COMMENT '产品ID',
  `product_name` varchar(100) NOT NULL COMMENT '产品名称',
  `product_model_id` int(11) NULL DEFAULT NULL COMMENT '规格型号',
  `product_model` varchar(100) NOT NULL COMMENT '规格型号',
  `product_unit` int(11) NOT NULL COMMENT '计量单位id',
  `product_unit_name` varchar(255) NOT NULL COMMENT '计量单位',
  `product_quantity` decimal(10, 3) NOT NULL COMMENT '数量',
  `unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '购入单价',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '本项金额',
  `temp_unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '暂估购入单价',
  `temp_total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '暂估本项金额',
  `difference_total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '金额差',
  `receive_date` varchar(4000) NULL DEFAULT NULL COMMENT '收料日期',
  `check_status` tinyint(1) NULL DEFAULT NULL COMMENT '是否点验(0:未点验,1:已点验)',
  `remark` varchar(5000) NULL DEFAULT NULL COMMENT '备注',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_storage_statement_item
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_statement_item_procurement
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_statement_item_procurement`;
CREATE TABLE `dx_storage_statement_item_procurement`  (
  `statement_item_id` int(11) NOT NULL COMMENT '对账单ID',
  `procurement_id` int(11) NOT NULL COMMENT '收料ID',
  `procurement_no` varchar(255) NOT NULL COMMENT '收料编号',
  `check_item_id` int(11) NOT NULL DEFAULT 0 COMMENT '点验单ID',
  PRIMARY KEY (`statement_item_id`, `procurement_id`),
  INDEX `index_procurement_id`(`procurement_id`),
  INDEX `index_statement_item_id`(`statement_item_id`),
  INDEX `index_check_item_id`(`check_item_id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '对账单-收料-中间表';

-- ----------------------------
-- Records of dx_storage_statement_item_procurement
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_store
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_store`;
CREATE TABLE `dx_storage_store`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `procurement_id` int(11) NOT NULL COMMENT '收料ID',
  `procurement_no` varchar(255) NOT NULL COMMENT '收料编号',
  `supplier_id` int(11) NOT NULL COMMENT '供方id',
  `supplier_name` varchar(100) NOT NULL COMMENT '供方名称',
  `store_username` varchar(100) NOT NULL COMMENT '入库员',
  `store_date` datetime(0) NOT NULL COMMENT '存料时间',
  `product_id` int(11) NULL DEFAULT NULL COMMENT '产品ID',
  `product_name` varchar(100) NOT NULL COMMENT '产品名称',
  `product_model_id` int(11) NULL DEFAULT NULL COMMENT '规格型号',
  `product_model` varchar(100) NOT NULL COMMENT '规格型号',
  `product_unit` int(11) NOT NULL COMMENT '计量单位id',
  `product_unit_name` varchar(100) NOT NULL COMMENT '计量单位',
  `store_quantity` decimal(10, 3) NOT NULL COMMENT '存料数量',
  `surplus_quantity` decimal(10, 3) NOT NULL COMMENT '剩余数量',
  `store_type` int(11) NOT NULL COMMENT '存料方式(0:粉状材料存罐,1:实体库,2:虚拟库)',
  `mix_id` int(11) NULL DEFAULT NULL COMMENT '拌合站ID',
  `mix_name` varchar(100) NULL DEFAULT NULL COMMENT '拌合站名称',
  `device_group_id` int(11) NULL DEFAULT NULL COMMENT '机组ID',
  `device_group_name` varchar(100) NULL DEFAULT NULL COMMENT '机组名称',
  `device_id` int(11) NULL DEFAULT NULL COMMENT '罐号id',
  `device_name` varchar(100) NULL DEFAULT NULL COMMENT '罐号',
  `other_place` int(100) NULL DEFAULT NULL COMMENT '其他存放点ID',
  `other_place_name` varchar(100) NULL DEFAULT NULL COMMENT '其他存放点',
  `photos` text NULL COMMENT '照片(数据格式:[{\"part\":\"\",\"fid\":\"\"}])',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  `create_user` varchar(255) NOT NULL COMMENT '创建人id',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '存料';

-- ----------------------------
-- Records of dx_storage_store
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_store_mix
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_store_mix`;
CREATE TABLE `dx_storage_store_mix`  (
  `mixingStation_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '拌合站id',
  `station_name` varchar(30) NOT NULL COMMENT '拌合站名字',
  `mix_project_id` varchar(12) NOT NULL COMMENT '项目部Id',
  `isBool` tinyint(2) NULL DEFAULT 0,
  `mix_xy` varchar(30) NULL DEFAULT NULL COMMENT '拌合站位置XY',
  `is_true` tinyint(4) NULL DEFAULT 0 COMMENT '是否退场',
  `mix_url` varchar(225) NULL DEFAULT NULL COMMENT '拌合站路径',
  `mix_user_name` varchar(20) NULL DEFAULT NULL COMMENT '站长名字',
  `mix_user_phone` varchar(22) NULL DEFAULT NULL COMMENT '站长联系方式',
  `mix_xy_detailed` varchar(125) NULL DEFAULT NULL COMMENT '详细位置',
  `mix_user_id` varchar(64) NULL DEFAULT NULL COMMENT '站长id',
  PRIMARY KEY (`mixingStation_id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_storage_store_mix
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_store_point
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_store_point`;
CREATE TABLE `dx_storage_store_point`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `address` varchar(255) NOT NULL COMMENT '存放地点',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  `is_real` tinyint(1) NOT NULL COMMENT '是否是真实库',
  `is_delete` tinyint(1) NOT NULL COMMENT '是否删除',
  `create_user` varchar(255) NOT NULL COMMENT '创建人',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_storage_store_point
-- ----------------------------

-- ----------------------------
-- Table structure for dx_storage_turnover
-- ----------------------------
DROP TABLE IF EXISTS `dx_storage_turnover`;
CREATE TABLE `dx_storage_turnover`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `turnover_no` varchar(255) NOT NULL COMMENT '周转字号',
  `prev_turnover_id` int(11) NOT NULL COMMENT '上一次所在周转id',
  `distribution_id` int(11) NOT NULL COMMENT '发料id',
  `distribution_no` varchar(255) NOT NULL COMMENT '出库单号',
  `distribution_item_id` int(11) NOT NULL COMMENT '发料详情id',
  `procurement_id` int(11) NOT NULL COMMENT '收料id',
  `procurement_no` varchar(255) NOT NULL COMMENT '收料编号',
  `supplier_id` int(11) NOT NULL COMMENT '供方Id',
  `supplier_name` varchar(255) NOT NULL COMMENT '供方名称',
  `product_id` int(11) NULL DEFAULT NULL COMMENT '材料id',
  `product_name` varchar(255) NOT NULL COMMENT '材料名称',
  `product_model_id` int(11) NULL DEFAULT NULL COMMENT '规格型号',
  `product_model` varchar(255) NOT NULL COMMENT '规格型号',
  `product_unit` int(11) NOT NULL COMMENT '计量单位ID',
  `product_unit_name` varchar(255) NOT NULL COMMENT '计量单位',
  `from_id` int(11) NULL DEFAULT NULL COMMENT '来源id',
  `from_name` varchar(255) NOT NULL COMMENT '来源名称',
  `to_type` int(11) NOT NULL COMMENT '来源类型(1:发劳务队,2:发项目部,3:发加工厂,4:发其他,5:发拌合站)',
  `to_id` int(11) NULL DEFAULT NULL COMMENT '接收id',
  `to_name` varchar(255) NOT NULL COMMENT '接收名称',
  `turnover_quantity` decimal(10, 3) NOT NULL COMMENT '周转数量',
  `turnover_date` datetime(0) NOT NULL COMMENT '周转时间',
  `surplus_quantity` decimal(10, 3) NULL DEFAULT NULL COMMENT '剩余数量',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `status` int(11) NOT NULL COMMENT '状态(0:正常,1:不可用)',
  `project_id` int(11) NOT NULL COMMENT '所属项目部',
  `create_user` varchar(255) NOT NULL COMMENT '创建人id',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  `update_date` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of dx_storage_turnover
-- ----------------------------

-- ----------------------------
-- Table structure for dx_system_handle_log
-- ----------------------------
DROP TABLE IF EXISTS `dx_system_handle_log`;
CREATE TABLE `dx_system_handle_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `handle_year` int(11) NOT NULL COMMENT '操作年份',
  `handle_date` datetime(0) NOT NULL COMMENT '操作日期',
  `module` int(255) NOT NULL COMMENT '模块',
  `project_id` int(11) NOT NULL COMMENT '项目部ID',
  `project_name` varchar(255) NULL DEFAULT NULL COMMENT '项目部名称',
  `handle_user_id` varchar(255) NOT NULL COMMENT '操作人id',
  `handle_user` varchar(255) NULL DEFAULT NULL COMMENT '操作人',
  `ip` varchar(255) NULL DEFAULT NULL COMMENT 'IP',
  `device` varchar(255) NULL DEFAULT NULL COMMENT '机器',
  `handle_type` varchar(255) NOT NULL COMMENT '操作类型',
  `handle_info` varchar(255) NULL DEFAULT NULL COMMENT '操作信息',
  `handle_result` varchar(255) NULL DEFAULT NULL COMMENT '结果',
  PRIMARY KEY (`id`, `handle_year`)
) ENGINE = InnoDB AUTO_INCREMENT = 230 CHARACTER SET = utf8 PARTITION BY RANGE (`handle_year`)
PARTITIONS 12
(PARTITION `p2020` VALUES LESS THAN (2020) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p2021` VALUES LESS THAN (2021) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p2022` VALUES LESS THAN (2022) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p2023` VALUES LESS THAN (2023) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p2024` VALUES LESS THAN (2024) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p2025` VALUES LESS THAN (2025) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p2026` VALUES LESS THAN (2026) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p2027` VALUES LESS THAN (2027) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p2028` VALUES LESS THAN (2028) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p2029` VALUES LESS THAN (2029) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `p2030` VALUES LESS THAN (2030) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 ,
PARTITION `pmore` VALUES LESS THAN (MAXVALUE) ENGINE = InnoDB MAX_ROWS = 0 MIN_ROWS = 0 )
;

-- ----------------------------
-- Records of dx_system_handle_log
-- ----------------------------

-- ----------------------------
-- Table structure for dx_tender_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `dx_tender_dictionary`;
CREATE TABLE `dx_tender_dictionary`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` varchar(45) NOT NULL COMMENT '字典类型',
  `code` varchar(20) NOT NULL COMMENT '字典code',
  `name` varchar(20) NOT NULL COMMENT '字典名称',
  `sort_index` int(11) NOT NULL COMMENT '排序',
  `tag` varchar(20) NULL DEFAULT NULL COMMENT '字典标记',
  `remark` varchar(45) NULL DEFAULT NULL COMMENT '字典备注',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uniq_type_code`(`type`, `code`) COMMENT '字典类型code',
  INDEX `idx_type`(`type`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '投标字典';

-- ----------------------------
-- Records of dx_tender_dictionary
-- ----------------------------

-- ----------------------------
-- Table structure for dx_tender_establish
-- ----------------------------
DROP TABLE IF EXISTS `dx_tender_establish`;
CREATE TABLE `dx_tender_establish`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `project_id` int(20) NOT NULL COMMENT '项目id',
  `group_type` varchar(11) NOT NULL COMMENT '编制组别',
  `section` varchar(500) NULL DEFAULT NULL COMMENT '编制标段',
  `establish_mode` varchar(20) NOT NULL COMMENT '编制方式',
  `terminate_date` date NULL DEFAULT NULL COMMENT '截止日期',
  `principal` varchar(1000) NULL DEFAULT NULL COMMENT '责任人',
  `establish_time` datetime(0) NULL DEFAULT NULL COMMENT '编制时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  INDEX `idx_project_group`(`project_id`, `group_type`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '编制';

-- ----------------------------
-- Records of dx_tender_establish
-- ----------------------------

-- ----------------------------
-- Table structure for dx_tender_establish_business_material
-- ----------------------------
DROP TABLE IF EXISTS `dx_tender_establish_business_material`;
CREATE TABLE `dx_tender_establish_business_material`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `project_id` int(20) NOT NULL COMMENT '项目id',
  `tender_files` varchar(500) NULL DEFAULT NULL COMMENT '招标文件',
  `buy_tender_files` varchar(500) NULL DEFAULT NULL COMMENT '投标邀请书',
  `buy_principal` varchar(1000) NULL DEFAULT NULL COMMENT '购买责任人',
  `other_files` varchar(500) NULL DEFAULT NULL COMMENT '其他资料',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uniq_project_id`(`project_id`),
  INDEX `idx_project_id`(`project_id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '商务编制上传资料';

-- ----------------------------
-- Records of dx_tender_establish_business_material
-- ----------------------------

-- ----------------------------
-- Table structure for dx_tender_establish_condition
-- ----------------------------
DROP TABLE IF EXISTS `dx_tender_establish_condition`;
CREATE TABLE `dx_tender_establish_condition`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `establish_id` int(20) NOT NULL COMMENT '编制id',
  `category` varchar(20) NOT NULL COMMENT '工作类型',
  `content` text NULL COMMENT '工作内容',
  `outside_job` varchar(255) NULL DEFAULT NULL COMMENT '外办工作',
  `node_date` datetime(0) NULL DEFAULT NULL COMMENT '外办节点时间',
  `complete_date` datetime(0) NULL DEFAULT NULL COMMENT '外办完成时间',
  `remark` varchar(500) NULL DEFAULT NULL COMMENT '备注',
  `files` varchar(500) NULL DEFAULT NULL COMMENT '上传文件',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_show` varchar(2) NULL DEFAULT '1' COMMENT '是否展示(合同评审表)1:展示;2:不展示',
  PRIMARY KEY (`id`),
  INDEX `idx_establish_id`(`establish_id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '自定义编制情况';

-- ----------------------------
-- Records of dx_tender_establish_condition
-- ----------------------------

-- ----------------------------
-- Table structure for dx_tender_frequency_person
-- ----------------------------
DROP TABLE IF EXISTS `dx_tender_frequency_person`;
CREATE TABLE `dx_tender_frequency_person`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` varchar(225) NOT NULL COMMENT '人员id',
  `name` varchar(45) NOT NULL COMMENT '人员姓名',
  `pro_id` varchar(45) NULL DEFAULT NULL COMMENT '项目部id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(45) NULL DEFAULT NULL COMMENT '创建人',
  `creator_id` varchar(45) NULL DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '常用人员';

-- ----------------------------
-- Records of dx_tender_frequency_person
-- ----------------------------

-- ----------------------------
-- Table structure for dx_tender_material
-- ----------------------------
DROP TABLE IF EXISTS `dx_tender_material`;
CREATE TABLE `dx_tender_material`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `folder_id` int(20) NULL DEFAULT NULL COMMENT '文件夹外键id',
  `file_id` int(20) NOT NULL COMMENT '文件id',
  `file_name` varchar(500) NOT NULL COMMENT '文件名称',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除(0:正常，1:已删除)',
  `upload_time` datetime(0) NOT NULL COMMENT '上传时间',
  `upload_user` varchar(20) NOT NULL COMMENT '上传人',
  `upload_phone` varchar(20) NOT NULL COMMENT '上传人手机号',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '投标资料库';

-- ----------------------------
-- Records of dx_tender_material
-- ----------------------------

-- ----------------------------
-- Table structure for dx_tender_material_folder
-- ----------------------------
DROP TABLE IF EXISTS `dx_tender_material_folder`;
CREATE TABLE `dx_tender_material_folder`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `material_project_id` int(20) NOT NULL COMMENT '项目外键id',
  `folder` varchar(45) NULL DEFAULT NULL COMMENT '文件夹',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uniq_projectId_folder`(`material_project_id`, `folder`),
  INDEX `idx_projectId`(`material_project_id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '资料库文件夹';

-- ----------------------------
-- Records of dx_tender_material_folder
-- ----------------------------

-- ----------------------------
-- Table structure for dx_tender_material_project
-- ----------------------------
DROP TABLE IF EXISTS `dx_tender_material_project`;
CREATE TABLE `dx_tender_material_project`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `project_name` varchar(255) NULL DEFAULT NULL COMMENT '项目名称',
  `tender_project_id` int(20) NULL DEFAULT NULL COMMENT '投标项目id',
  `predict_tender_date` date NULL DEFAULT NULL COMMENT '预计投标时间',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '资料库项目';

-- ----------------------------
-- Records of dx_tender_material_project
-- ----------------------------

-- ----------------------------
-- Table structure for dx_tender_person_organize
-- ----------------------------
DROP TABLE IF EXISTS `dx_tender_person_organize`;
CREATE TABLE `dx_tender_person_organize`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `project_id` int(20) NOT NULL COMMENT '项目id',
  `group_type` varchar(10) NOT NULL COMMENT '编制组',
  `establish_mode` varchar(10) NOT NULL COMMENT '编制方式',
  `establish_person` varchar(1000) NULL DEFAULT NULL COMMENT '统一编制方式编制人',
  `terminate_date` date NULL DEFAULT NULL COMMENT '截止日期',
  `create_user` varchar(20) NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(20) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  INDEX `idx_project_id`(`project_id`),
  INDEX `idx_project_group`(`project_id`, `group_type`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '投标人员组建';

-- ----------------------------
-- Records of dx_tender_person_organize
-- ----------------------------

-- ----------------------------
-- Table structure for dx_tender_person_section_organize
-- ----------------------------
DROP TABLE IF EXISTS `dx_tender_person_section_organize`;
CREATE TABLE `dx_tender_person_section_organize`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `person_organize_id` int(20) NOT NULL COMMENT '人员组建id',
  `section` varchar(500) NULL DEFAULT NULL COMMENT '标段',
  `establish_person` varchar(1000) NULL DEFAULT NULL COMMENT '编制人',
  `terminate_date` date NULL DEFAULT NULL COMMENT '截止日期',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_organize_id`(`person_organize_id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '按标段人员组建';

-- ----------------------------
-- Records of dx_tender_person_section_organize
-- ----------------------------

-- ----------------------------
-- Table structure for dx_tender_project
-- ----------------------------
DROP TABLE IF EXISTS `dx_tender_project`;
CREATE TABLE `dx_tender_project`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `project_name` varchar(225) NULL DEFAULT NULL COMMENT '项目名称',
  `project_type` varchar(11) NULL DEFAULT NULL COMMENT '项目类型',
  `project_property` varchar(11) NULL DEFAULT NULL COMMENT '项目性质',
  `status` varchar(11) NOT NULL COMMENT '项目状态',
  `project_address` varchar(255) NULL DEFAULT NULL COMMENT '项目地址',
  `build_unit` varchar(255) NULL DEFAULT NULL COMMENT '建设单位',
  `predict_tender_date` date NULL DEFAULT NULL COMMENT '预计投标时间',
  `predict_contract_amount` decimal(15, 4) NULL DEFAULT NULL COMMENT '预计投资额',
  `project_intro` varchar(1000) NULL DEFAULT NULL COMMENT '项目简介',
  `registrant` varchar(45) NOT NULL COMMENT '登记人',
  `register_time` datetime(0) NOT NULL COMMENT '登记时间',
  `modifier` varchar(45) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `job_event_ids` varchar(45) NULL DEFAULT NULL COMMENT '工作事项id',
  `is_inquiry` varchar(2) NULL DEFAULT NULL COMMENT '是否需要资格预审(1、需要，2、不需要)',
  `is_tender` varchar(2) NULL DEFAULT NULL COMMENT '是否投标(1、投标，2、弃标)',
  `establish_status` varchar(2) NULL DEFAULT '1' COMMENT '编制状态',
  `files` varchar(255) NULL DEFAULT NULL COMMENT '招标公告',
  `tender_time` datetime(0) NULL DEFAULT NULL COMMENT '投标时间',
  `bidding_time` datetime(0) NULL DEFAULT NULL COMMENT '中标/未中标时间',
  `unbid_time` datetime(0) NULL DEFAULT NULL COMMENT '弃标时间',
  PRIMARY KEY (`id`),
  INDEX `idx_status`(`status`),
  INDEX `idx_register_time`(`register_time`),
  INDEX `idx_project_name`(`project_name`),
  INDEX `idx_bidding_time`(`bidding_time`),
  INDEX `idx_tender_time`(`tender_time`),
  INDEX `idx_unbid_time`(`unbid_time`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '投标项目';

-- ----------------------------
-- Records of dx_tender_project
-- ----------------------------

-- ----------------------------
-- Table structure for dx_tender_record
-- ----------------------------
DROP TABLE IF EXISTS `dx_tender_record`;
CREATE TABLE `dx_tender_record`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `project_name` varchar(45) NOT NULL COMMENT '项目名称',
  `file_ids` varchar(500) NULL DEFAULT NULL COMMENT '记录文件',
  `file_names` varchar(1000) NULL DEFAULT NULL COMMENT '文件名字',
  `upload_type` varchar(45) NOT NULL COMMENT '上传类型',
  `upload_time` datetime(0) NOT NULL COMMENT '上传时间',
  `upload_user` varchar(45) NOT NULL COMMENT '上传人',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '开标记录';

-- ----------------------------
-- Records of dx_tender_record
-- ----------------------------

-- ----------------------------
-- Table structure for dx_tender_section_record
-- ----------------------------
DROP TABLE IF EXISTS `dx_tender_section_record`;
CREATE TABLE `dx_tender_section_record`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `record_id` int(20) NOT NULL COMMENT '开标记录id',
  `project_section` varchar(45) NOT NULL COMMENT '项目标段',
  `file_ids` varchar(500) NULL DEFAULT NULL COMMENT '记录文件',
  `file_names` varchar(1000) NULL DEFAULT NULL COMMENT '文件名字',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '按标段上传开标记录';

-- ----------------------------
-- Records of dx_tender_section_record
-- ----------------------------

-- ----------------------------
-- Table structure for 主键
-- ----------------------------
DROP TABLE IF EXISTS `主键`;
CREATE TABLE `主键`  (
  `id` int(11) NOT NULL COMMENT '主键',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8;

-- ----------------------------
-- Records of 主键
-- ----------------------------

-- ----------------------------
-- View structure for projectaqview
-- ----------------------------
DROP VIEW IF EXISTS `projectaqview`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `projectaqview` AS select `u`.`id` AS `id`,`u`.`name` AS `name`,`u`.`phone` AS `phone`,`j1`.`proid` AS `proid`,`j2`.`departid` AS `departid` from ((`dx_erp_user` `u` join `dx_erp_user_job_project` `j1` on(((`u`.`id` = `j1`.`uid`) and (`j1`.`is_supervisor` = 1)))) join `dx_erp_job` `j2` on((`j2`.`Jid` = `j1`.`jid`)));

-- ----------------------------
-- View structure for projectview
-- ----------------------------
DROP VIEW IF EXISTS `projectview`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `projectview` AS select `u`.`id` AS `id`,`u`.`name` AS `name`,`u`.`phone` AS `phone`,`c`.`project_name` AS `project_name`,`c`.`proid` AS `project_id`,`j`.`proid` AS `proid` from ((`dx_erp_user_job_project` `j` left join `dx_erp_user` `u` on(((`u`.`id` = `j`.`uid`) and (`j`.`jid` = 249)))) join `dx_erp_project` `c` on((`c`.`proid` = `j`.`proid`)));

-- ----------------------------
-- Procedure structure for create_calendar
-- ----------------------------
DROP PROCEDURE IF EXISTS `create_calendar`;
delimiter ;;
CREATE PROCEDURE `create_calendar`(s_date DATE, e_date DATE)
BEGIN
  
  -- 生成一个日历表
    SET @createSql = 'CREATE TABLE IF NOT EXISTS calendar_custom (
                      `date` date NOT NULL,
               UNIQUE KEY `unique_date` (`date`)
                   )ENGINE=InnoDB DEFAULT CHARSET=utf8';
    prepare stmt from @createSql;
    execute stmt;
  
    WHILE s_date <= e_date DO
        INSERT IGNORE INTO calendar_custom VALUES (DATE(s_date)) ;
        SET s_date = s_date + INTERVAL 1 DAY ;
    END WHILE ;
  
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for update_states
-- ----------------------------
DROP PROCEDURE IF EXISTS `update_states`;
delimiter ;;
CREATE PROCEDURE `update_states`()
BEGIN 
			IF EXISTS(SELECT id FROM `DX_OFFICE_SQTABLE` WHERE now()>backtime) THEN
					UPDATE `DX_OFFICE_SQTABLE` s LEFT JOIN `DX_OFFICE_SQTABLE` sq ON s.id=sq.id SET s.state=-1 WHERE now()>s.backtime;
END IF;
END
;;
delimiter ;

-- ----------------------------
-- Event structure for event_update_state
-- ----------------------------
DROP EVENT IF EXISTS `event_update_state`;
delimiter ;;
CREATE EVENT `event_update_state`
ON SCHEDULE
EVERY '1' DAY STARTS '2020-05-28 00:00:00'
DO call update_states()
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
