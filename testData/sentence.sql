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

-- ----------------------------
-- Table structure for sentence_config
-- ----------------------------
DROP TABLE IF EXISTS `sentence_config`;
create table sentence_config
(
    id                       int auto_increment
        primary key,
    title                    varchar(255) null comment '标题',
    remark                   varchar(255) null comment '备注',
    type_nub                 int          null comment '语义分类种类数与表my_tree数据条数对应',
    word_vector_dimension    int          null comment '语义分类词嵌入维度，该数字越大，支持的分类复杂度越高，支持数据量越多，越接近大模型，生成问答模型越稳定，但速度越慢',
    qa_word_Vector_dimension int          null comment '问答模型词嵌入维度，该数字越大速度越慢，越能支持更复杂的问答',
    max_word_length          int          null comment '用户输入语句最大长度',
    max_answer_length        int          null comment 'Ai最大回答长度',
    key_word_nerve_deep      int          null comment '/关键词敏感嗅探颗粒度大小',
    times                    int          null comment '训练次数',
    show_log                 tinyint(1)   null comment '是否显示训练日志（1显示，0显示）',
    param                    double       null,
    min_length               int          null,
    trust_power_th           double       null,
    sentence_trust_power_th  double       null,
    we_study_point           double       null,
    we_lparam                double       null,
    status                   char         null comment '状态（1 使用和0未使用）',
    base_dir                 varchar(255) null comment '模型目录',
    created_time             datetime     null comment '创建时间',
    updated_time             datetime     null comment '更新时间'
)
    comment '训练配置表';

SET FOREIGN_KEY_CHECKS = 1;
