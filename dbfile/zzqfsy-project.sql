/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50556
Source Host           : 192.168.140.128:3306
Source Database       : zzqfsy-project

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2018-09-06 21:54:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_project
-- ----------------------------
DROP TABLE IF EXISTS `t_project`;
CREATE TABLE `t_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `s_title` varchar(50) DEFAULT NULL COMMENT '产品名称',
  `dcm_total` decimal(10,2) DEFAULT NULL COMMENT '总数',
  `dcm_able` decimal(10,2) DEFAULT NULL COMMENT '剩余',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_project
-- ----------------------------
INSERT INTO `t_project` VALUES ('1', '产品1', '100000.00', '99900.00');
