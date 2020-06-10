/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : TemperatureCMS

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 11/06/2020 00:55:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for room_serving_list
-- ----------------------------
DROP TABLE IF EXISTS `room_serving_list`;
CREATE TABLE `room_serving_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `room_id` int(11) NOT NULL DEFAULT '0',
  `start_time` bigint(20) NOT NULL DEFAULT '0',
  `end_time` bigint(20) NOT NULL DEFAULT '0',
  `duration` int(255) NOT NULL DEFAULT '0',
  `spped` int(255) NOT NULL DEFAULT '0',
  `fee_rate` double(255,0) NOT NULL DEFAULT '0',
  `fee` double(255,0) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for room_status
-- ----------------------------
DROP TABLE IF EXISTS `room_status`;
CREATE TABLE `room_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '房间号',
  `current_temperature` double NOT NULL DEFAULT '0' COMMENT '当前温度',
  `target_temperature` double NOT NULL DEFAULT '0' COMMENT '目标温度',
  `fans_speed` double NOT NULL DEFAULT '0' COMMENT '风速',
  `fare_rate` double NOT NULL DEFAULT '0' COMMENT '费率',
  `start_up` bigint(20) NOT NULL DEFAULT '0' COMMENT '启动时间',
  `mode` int(11) NOT NULL DEFAULT '0' COMMENT '模式',
  `end_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '关机时间',
  `state` int(10) NOT NULL DEFAULT '0' COMMENT '工作状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of room_status
-- ----------------------------
BEGIN;
INSERT INTO `room_status` VALUES (2, 101, 32, 27, 20, 0, 0, 1, 0, 0);
INSERT INTO `room_status` VALUES (3, 101, 32, 27, 20, 0, 1590917744203, 1, 0, 0);
INSERT INTO `room_status` VALUES (4, 102, 32, 25, 20, 0, 1590917744203, 1, 1590917826834, 0);
INSERT INTO `room_status` VALUES (5, 105, 33, 27, 20, 0, 1590918287113, 1, 1590919930586, 0);
INSERT INTO `room_status` VALUES (6, 105, 33, 23, 20, 0, 1590996317618, 1, 1590996462237, 0);
INSERT INTO `room_status` VALUES (7, 110, 33, 27, 20, 0, 1590997883341, 1, 1590999629518, 0);
INSERT INTO `room_status` VALUES (8, 115, 33, 25, 20, 0, 1590998058731, 2, 1590999632926, 0);
INSERT INTO `room_status` VALUES (9, 105, 33, 25, 20, 0, 1590999652655, 2, 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '用户名',
  `pass_word` varchar(50) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '密码',
  `email` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '邮箱',
  `phone` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '手机',
  `role` int(11) NOT NULL DEFAULT '0' COMMENT '角色，1用户2管理员3经理',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of user_info
-- ----------------------------
BEGIN;
INSERT INTO `user_info` VALUES (1, 'test', '4b72f19ae432aff92a6947c725a69f25', 'test233@163.com', '132424', 1);
INSERT INTO `user_info` VALUES (2, 'root', '21232f297a57a5a743894a0e4a801fc3', 'test23322@163.com', '132424', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
