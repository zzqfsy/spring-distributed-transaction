/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50556
Source Host           : 127.0.0.1:3306
Source Database       : zzqfsy-account

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2018-09-06 21:54:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_account
-- ----------------------------
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `i_user_id` int(11) NOT NULL COMMENT '用户id',
  `dcm_balance` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '余额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_account
-- ----------------------------
INSERT INTO `t_account` VALUES ('1', '1', '2600.00');
INSERT INTO `t_account` VALUES ('2', '2', '2700.00');

-- ----------------------------
-- Table structure for t_account_flow
-- ----------------------------
DROP TABLE IF EXISTS `t_account_flow`;
CREATE TABLE `t_account_flow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `i_user_id` int(11) NOT NULL,
  `dcm_balance_before` decimal(10,2) NOT NULL COMMENT '变更之前余额',
  `dcm_balance_change` decimal(10,2) NOT NULL COMMENT '变更额度',
  `dcm_balance_after` decimal(10,2) NOT NULL COMMENT '变更之后余额',
  `i_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态，0-未变更，1-已变更，2-撤销变更',
  `s_order_no` varchar(50) DEFAULT '' COMMENT '订单号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_account_flow
-- ----------------------------
INSERT INTO `t_account_flow` VALUES ('1', '1', '2400.00', '100.00', '2500.00', '1', 'order');
INSERT INTO `t_account_flow` VALUES ('2', '1', '2500.00', '100.00', '2600.00', '1', 'order');
INSERT INTO `t_account_flow` VALUES ('3', '1', '2600.00', '100.00', '2700.00', '1', 'order');
INSERT INTO `t_account_flow` VALUES ('4', '1', '2700.00', '-100.00', '2600.00', '1', 'PO0000000011808222336122907801');
