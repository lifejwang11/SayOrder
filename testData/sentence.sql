/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 50744
 Source Host           : localhost:3306
 Source Schema         : sentence_data

 Target Server Type    : MySQL
 Target Server Version : 50744
 File Encoding         : 65001

 Date: 31/12/2023 20:42:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '账号',
  `pass_word` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '密码',
  `pass` int(11) NULL DEFAULT NULL COMMENT '账号是否通过，0未审核，1通过',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '账户拥有者姓名',
  PRIMARY KEY (`id`, `account`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for keyword_sql
-- ----------------------------
DROP TABLE IF EXISTS `keyword_sql`;
CREATE TABLE `keyword_sql`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '该关键词id',
  `sentence_id` int(11) NULL DEFAULT NULL COMMENT '该关键词对应的语句id',
  `keyword` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '关键词内容',
  `keyword_type_id` int(11) NULL DEFAULT NULL COMMENT '该关键词类型id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2875 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for keyword_type
-- ----------------------------
DROP TABLE IF EXISTS `keyword_type`;
CREATE TABLE `keyword_type`  (
  `keyword_type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '该关键词类型id',
  `type_id` int(11) NULL DEFAULT NULL COMMENT '该关键词类型对应得语句类别',
  `keyword_mes` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '该关键词对应的订单信息',
  `answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户语句缺失该关键词对应的回复问题',
  `type_number` int(11) NULL DEFAULT NULL COMMENT '该种类关键词标注数量',
  PRIMARY KEY (`keyword_type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for my_tree
-- ----------------------------
DROP TABLE IF EXISTS `my_tree`;
CREATE TABLE `my_tree`  (
  `type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类别id',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '类别名称',
  `sentence_nub` int(11) NULL DEFAULT NULL COMMENT '样本条目数',
  PRIMARY KEY (`type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for q_a
-- ----------------------------
DROP TABLE IF EXISTS `q_a`;
CREATE TABLE `q_a`  (
  `question` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '问题',
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '回答',
  UNIQUE INDEX `q_a_pk`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1023 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '聊天样本' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sentence
-- ----------------------------
DROP TABLE IF EXISTS `sentence`;
CREATE TABLE `sentence`  (
  `sentence_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '语句id',
  `word` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '语句内容',
  `type_id` int(11) NULL DEFAULT NULL COMMENT '类别id',
  `date` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '日期',
  `adminID` int(11) NULL DEFAULT NULL COMMENT '标注人id',
  PRIMARY KEY (`sentence_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4497 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
