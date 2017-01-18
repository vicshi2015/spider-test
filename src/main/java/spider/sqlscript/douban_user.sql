/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2017-01-18 15:58:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `douban_user`
-- ----------------------------
DROP TABLE IF EXISTS `douban_user`;
CREATE TABLE `douban_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `join_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `head_portrait` varchar(200) DEFAULT NULL,
  `intro` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of douban_user
-- ----------------------------
