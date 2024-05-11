/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 11/05/2024 08:43:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for keyword_sql
-- ----------------------------
DROP TABLE IF EXISTS `keyword_sql`;
CREATE TABLE `keyword_sql`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '该关键词id',
  `sentence_id` int(0) NULL DEFAULT NULL COMMENT '该关键词对应的语句id',
  `keyword` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '关键词内容',
  `keyword_type_id` int(0) NULL DEFAULT NULL COMMENT '该关键词类型id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2929 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of keyword_sql
-- ----------------------------
INSERT INTO `keyword_sql` VALUES (2903, 4525, '增资', 25);
INSERT INTO `keyword_sql` VALUES (2904, 4526, '报废', 25);
INSERT INTO `keyword_sql` VALUES (2905, 4527, '编码', 25);
INSERT INTO `keyword_sql` VALUES (2906, 4528, '折旧', 25);
INSERT INTO `keyword_sql` VALUES (2907, 4529, '折旧', 25);
INSERT INTO `keyword_sql` VALUES (2908, 4530, '编码', 25);
INSERT INTO `keyword_sql` VALUES (2909, 4531, '资产', 25);
INSERT INTO `keyword_sql` VALUES (2910, 4532, '资产', 25);
INSERT INTO `keyword_sql` VALUES (2911, 4533, '编码', 25);
INSERT INTO `keyword_sql` VALUES (2912, 4534, '折旧', 25);
INSERT INTO `keyword_sql` VALUES (2913, 4535, '转资', 25);
INSERT INTO `keyword_sql` VALUES (2914, 4536, '转资', 25);
INSERT INTO `keyword_sql` VALUES (2915, 4537, '回退', 25);
INSERT INTO `keyword_sql` VALUES (2916, 4538, '回退', 25);
INSERT INTO `keyword_sql` VALUES (2917, 4539, '回退', 25);
INSERT INTO `keyword_sql` VALUES (2921, 4543, '编码', 25);
INSERT INTO `keyword_sql` VALUES (2922, 4544, '编码', 25);
INSERT INTO `keyword_sql` VALUES (2923, 4545, '编码', 25);
INSERT INTO `keyword_sql` VALUES (2925, 4547, '编码', 25);
INSERT INTO `keyword_sql` VALUES (2926, 4548, '编码', 25);
INSERT INTO `keyword_sql` VALUES (2927, 4549, '编码', 25);
INSERT INTO `keyword_sql` VALUES (2928, 4550, '编码', 25);

-- ----------------------------
-- Table structure for keyword_type
-- ----------------------------
DROP TABLE IF EXISTS `keyword_type`;
CREATE TABLE `keyword_type`  (
  `keyword_type_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '该关键词类型id',
  `type_id` int(0) NULL DEFAULT NULL COMMENT '该关键词类型对应得语句类别',
  `keyword_mes` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '该关键词对应的订单信息',
  `answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户语句缺失该关键词对应的回复问题',
  `type_number` int(0) NULL DEFAULT NULL COMMENT '该种类关键词标注数量',
  PRIMARY KEY (`keyword_type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of keyword_type
-- ----------------------------
INSERT INTO `keyword_type` VALUES (25, 23, '财务咨询', '请问你想咨询哪方面财务问题？', 29);

-- ----------------------------
-- Table structure for my_tree
-- ----------------------------
DROP TABLE IF EXISTS `my_tree`;
CREATE TABLE `my_tree`  (
  `type_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '类别id',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '类别名称',
  `sentence_nub` int(0) NULL DEFAULT NULL COMMENT '样本条目数',
  PRIMARY KEY (`type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of my_tree
-- ----------------------------
INSERT INTO `my_tree` VALUES (23, '财务咨询', 29);

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(0) NULL DEFAULT NULL,
  `refresh_token_validity` int(0) NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created_by` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created_time` datetime(0) NULL DEFAULT NULL,
  `created_date` date NULL DEFAULT NULL,
  `last_update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('test', NULL, '{noop}1234', 'all', 'password,mobile_password,authed_mobile,refresh_token', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oauth_client_details` VALUES ('web128d3xh1dd091', NULL, '{noop}x182z23mo4160ef2adc3bfkx4631b71p', 'all', 'password,mobile_password,authed_mobile,refresh_token', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oauth_client_details` VALUES ('web128de17az091', NULL, '{noop}989cz236o4180ef2adb3bfbx4630b91d', 'all', 'password,mobile_password,authed_mobile,refresh_token', 'https://www.getpostman.com/oauth2/callback', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oauth_client_details` VALUES ('wx103178648d20e85c', NULL, '{noop}189cf23694180ef2adb3bfbb4430b35c', 'all', 'password,mobile_password,authed_mobile,refresh_token', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oauth_client_details` VALUES ('wx4dd6e207511ac55d', NULL, '{noop}b6b8f27e8f7342959f6ef664c6977714', 'all', 'password,mobile_password,authed_mobile,refresh_token', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oauth_client_details` VALUES ('wx67cd320a7ff95cec', NULL, '{noop}88e45ddb0fd8600d342adb1e4c629675', 'all', 'password,mobile_password,authed_mobile,refresh_token', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oauth_client_details` VALUES ('wx72e647ba5dca4ab6', NULL, '{noop}d8ae71eecc601bb0371a3b017efe17b8', 'all', 'password,mobile_password,authed_mobile,refresh_token,wx_auth', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oauth_client_details` VALUES ('wxaa9954291a7cd363', NULL, '{noop}062139dedd7bb192e1abe9ac3e8cff68', 'all', 'password,mobile_password,authed_mobile,refresh_token,wx_auth', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for q_a
-- ----------------------------
DROP TABLE IF EXISTS `q_a`;
CREATE TABLE `q_a`  (
  `question` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '问题',
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '回答',
  UNIQUE INDEX `q_a_pk`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 451 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '聊天样本' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of q_a
-- ----------------------------

-- ----------------------------
-- Table structure for sentence
-- ----------------------------
DROP TABLE IF EXISTS `sentence`;
CREATE TABLE `sentence`  (
  `sentence_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '语句id',
  `word` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '语句内容',
  `type_id` int(0) NULL DEFAULT NULL COMMENT '类别id',
  `date` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '日期',
  `adminID` int(0) NULL DEFAULT NULL COMMENT '标注人id',
  PRIMARY KEY (`sentence_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4551 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sentence
-- ----------------------------
INSERT INTO `sentence` VALUES (4525, '增资流程', 23, '2024-05-09', NULL);
INSERT INTO `sentence` VALUES (4526, '报废原因导不进去', 23, '2024-05-09', NULL);
INSERT INTO `sentence` VALUES (4527, '资产编码被占用', 23, '2024-05-09', NULL);
INSERT INTO `sentence` VALUES (4528, '请先计提固定资产折旧', 23, '2024-05-09', NULL);
INSERT INTO `sentence` VALUES (4529, '当前业务期间没有计提折旧', 23, '2024-05-09', NULL);
INSERT INTO `sentence` VALUES (4530, '资产编码重复', 23, '2024-05-09', NULL);
INSERT INTO `sentence` VALUES (4531, '资产同步的单据经办人不对', 23, '2024-05-09', NULL);
INSERT INTO `sentence` VALUES (4532, '资产同步的单据待处理人不对', 23, '2024-05-09', NULL);
INSERT INTO `sentence` VALUES (4533, '设备身份证编码不可以重复', 23, '2024-05-09', NULL);
INSERT INTO `sentence` VALUES (4534, '年折旧率填写不了', 23, '2024-05-09', NULL);
INSERT INTO `sentence` VALUES (4535, '暂估转资明细多了', 23, '2024-05-09', NULL);
INSERT INTO `sentence` VALUES (4536, '暂估转资明细单重复', 23, '2024-05-09', NULL);
INSERT INTO `sentence` VALUES (4537, '回退前端系统失败', 23, '2024-05-09', NULL);
INSERT INTO `sentence` VALUES (4538, '变废单回退', 23, '2024-05-09', NULL);
INSERT INTO `sentence` VALUES (4539, '固定资产回退', 23, '2024-05-09', NULL);
INSERT INTO `sentence` VALUES (4543, '编码异常', 23, '2024-05-09', NULL);
INSERT INTO `sentence` VALUES (4544, '编码无效', 23, '2024-05-09', NULL);
INSERT INTO `sentence` VALUES (4545, '编码错误', 23, '2024-05-09', NULL);
INSERT INTO `sentence` VALUES (4547, '资产编码', 23, '2024-05-09', NULL);
INSERT INTO `sentence` VALUES (4548, '重复编码', 23, '2024-05-09', NULL);
INSERT INTO `sentence` VALUES (4549, '编码占用', 23, '2024-05-09', NULL);
INSERT INTO `sentence` VALUES (4550, '编码失效', 23, '2024-05-09', NULL);

-- ----------------------------
-- Table structure for sentence_config
-- ----------------------------
DROP TABLE IF EXISTS `sentence_config`;
CREATE TABLE `sentence_config`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '标题',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `type_nub` int(0) NULL DEFAULT NULL COMMENT '语义分类种类数与表my_tree数据条数对应',
  `word_vector_dimension` int(0) NULL DEFAULT NULL COMMENT '语义分类词嵌入维度，该数字越大，支持的分类复杂度越高，支持数据量越多，越接近大模型，生成问答模型越稳定，但速度越慢',
  `qa_word_Vector_dimension` int(0) NULL DEFAULT NULL COMMENT '问答模型词嵌入维度，该数字越大速度越慢，越能支持更复杂的问答',
  `max_word_length` int(0) NULL DEFAULT NULL COMMENT '用户输入语句最大长度',
  `max_answer_length` int(0) NULL DEFAULT NULL COMMENT 'Ai最大回答长度',
  `key_word_nerve_deep` int(0) NULL DEFAULT NULL COMMENT '/关键词敏感嗅探颗粒度大小',
  `times` int(0) NULL DEFAULT NULL COMMENT '训练次数',
  `show_log` tinyint(1) NULL DEFAULT NULL COMMENT '是否显示训练日志（1显示，0显示）',
  `param` double NULL DEFAULT NULL,
  `min_length` int(0) NULL DEFAULT NULL,
  `trust_power_th` double NULL DEFAULT NULL,
  `sentence_trust_power_th` double NULL DEFAULT NULL,
  `we_study_point` double NULL DEFAULT NULL,
  `we_lparam` double NULL DEFAULT NULL,
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '状态（1 使用和0未使用）',
  `base_dir` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '模型目录',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '训练配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sentence_config
-- ----------------------------
INSERT INTO `sentence_config` VALUES (2, '自定义', '自定义', 11, 21, 66, 20, 20, 3, 8, 0, 0.3, 5, 0.5, 0.3, 0.1, 0.001, '1', 'D:\\testModel\\m1\\', '2024-04-08 20:30:40', '2024-04-08 20:31:26');
INSERT INTO `sentence_config` VALUES (3, '默认', '默认', 11, 21, 66, 20, 20, 3, 8, 0, 0.3, 5, 0.5, 0.3, 0.01, 0.001, '0', 'D:\\testModel\\', '2024-05-08 10:32:34', NULL);

SET FOREIGN_KEY_CHECKS = 1;
