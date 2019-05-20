/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80015
Source Host           : localhost:3306
Source Database       : laminous_activiti2

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-05-20 13:58:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for biz_mapping
-- ----------------------------
DROP TABLE IF EXISTS `biz_mapping`;
CREATE TABLE `biz_mapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `biz_key` varchar(255) DEFAULT NULL,
  `process_key` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `form_id` varchar(255) DEFAULT NULL,
  `deleted` int(10) DEFAULT NULL,
  `version` bigint(255) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_by` varchar(255) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  `ext_field1` varchar(255) DEFAULT NULL,
  `ext_field2` varchar(255) DEFAULT NULL,
  `ext_field3` varchar(255) DEFAULT NULL,
  `ext_field4` varchar(255) DEFAULT NULL,
  `ext_field5` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dynamic_form
-- ----------------------------
DROP TABLE IF EXISTS `dynamic_form`;
CREATE TABLE `dynamic_form` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `form_name` varchar(255) DEFAULT NULL,
  `form_html` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `form_code` varchar(255) DEFAULT NULL,
  `deleted` int(10) DEFAULT NULL,
  `version` bigint(255) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_by` varchar(255) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  `ext_field1` varchar(255) DEFAULT NULL,
  `ext_field2` varchar(255) DEFAULT NULL,
  `ext_field3` varchar(255) DEFAULT NULL,
  `ext_field4` varchar(255) DEFAULT NULL,
  `ext_field5` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_task
-- ----------------------------
DROP TABLE IF EXISTS `oa_task`;
CREATE TABLE `oa_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `biz_key` varchar(255) DEFAULT NULL,
  `proc_inst_id` varchar(255) DEFAULT NULL,
  `proc_def_id` varchar(255) DEFAULT NULL,
  `apply_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `apply_code` varchar(255) DEFAULT NULL,
  `apply_time` datetime DEFAULT NULL,
  `task_state` varchar(255) DEFAULT NULL,
  `data` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `deleted` tinyint(10) DEFAULT NULL,
  `version` int(255) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_by` varchar(255) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  `ext_field1` varchar(255) DEFAULT NULL,
  `ext_field2` varchar(255) DEFAULT NULL,
  `ext_field3` varchar(255) DEFAULT NULL,
  `ext_field4` varchar(255) DEFAULT NULL,
  `ext_field5` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for oa_task_approve
-- ----------------------------
DROP TABLE IF EXISTS `oa_task_approve`;
CREATE TABLE `oa_task_approve` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `oa_task_id` bigint(20) NOT NULL,
  `act_task_id` varchar(255) NOT NULL,
  `approve_content` varchar(255) DEFAULT NULL,
  `approver` varchar(255) DEFAULT NULL,
  `approve_time` datetime DEFAULT NULL,
  `approve_result` varchar(10) DEFAULT NULL,
  `deleted` tinyint(10) DEFAULT NULL,
  `version` int(255) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_by` varchar(255) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  `ext_field1` varchar(255) DEFAULT NULL,
  `ext_field2` varchar(255) DEFAULT NULL,
  `ext_field3` varchar(255) DEFAULT NULL,
  `ext_field4` varchar(255) DEFAULT NULL,
  `ext_field5` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `birth` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `deleted` int(10) DEFAULT NULL,
  `version` bigint(255) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_by` varchar(255) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  `ext_field1` varchar(255) DEFAULT NULL,
  `ext_field2` varchar(255) DEFAULT NULL,
  `ext_field3` varchar(255) DEFAULT NULL,
  `ext_field4` varchar(255) DEFAULT NULL,
  `ext_field5` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
