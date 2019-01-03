/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50556
Source Host           : 127.0.0.1:3306
Source Database       : zzqfsy-order

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2018-09-06 21:54:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `i_project_id` int(11) DEFAULT NULL COMMENT '产品i',
  `i_user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `s_order_no` varchar(50) DEFAULT NULL COMMENT '订单号 ',
  `dcm_amount` decimal(10,2) DEFAULT NULL COMMENT '订单金额',
  `i_status` tinyint(3) DEFAULT NULL COMMENT '订单状态, 0-初始化，1-已完成，2-已失败',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES ('1', '1', '1', 'PO0000000011808222303005046771', '100.00', '1');
INSERT INTO `t_order` VALUES ('2', '1', '1', 'PO0000000011808222336122907801', '100.00', '1');
