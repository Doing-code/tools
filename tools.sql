SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 2023-11-15

-- ----------------------------
-- Table structure for mnt_server
-- Redis、Zookeeper、Kafka、Linux... 等组件或服务器IP端口
-- ----------------------------
DROP TABLE IF EXISTS `mnt_server`;
CREATE TABLE `mnt_server` (
  `server_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `account` varchar(50) DEFAULT NULL COMMENT '账号',
  `ip` varchar(20) DEFAULT NULL COMMENT 'IP地址',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `port` int(11) DEFAULT NULL COMMENT '端口',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`server_id`) USING BTREE,
  KEY `idx_ip` (`ip`)
) ENGINE=InnoDB AUTO_INCREMENT=1 ROW_FORMAT=COMPACT COMMENT='服务器管理';

-- ----------------------------
-- Table structure for mnt_server_args
-- 主要服务于 MySQL 连接时的参数
-- ----------------------------
DROP TABLE IF EXISTS `mnt_server_args`;
CREATE TABLE `mnt_server` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `args` varchar(255) DEFAULT NULL COMMENT '参数',
  `server_id` bigint(20) DEFAULT NULL COMMENT 'server_id',
  
) ENGINE=InnoDB AUTO_INCREMENT=1 ROW_FORMAT=COMPACT COMMENT='服务器连接参数';
