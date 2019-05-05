/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50641
Source Host           : localhost:3306
Source Database       : laminous_activiti

Target Server Type    : MYSQL
Target Server Version : 50641
File Encoding         : 65001

Date: 2019-05-05 19:07:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for oa_task
-- ----------------------------
DROP TABLE IF EXISTS `oa_task`;
CREATE TABLE `oa_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `biz_key` varchar(255) DEFAULT NULL,
  `proc_inst_id` varchar(255) DEFAULT NULL,
  `data` text,
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
  `proc_def_id` varchar(255) DEFAULT NULL,
  `apply_name` varchar(255) DEFAULT NULL,
  `apply_time` datetime DEFAULT NULL,
  `task_state` varchar(255) DEFAULT NULL,
  `apply_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
