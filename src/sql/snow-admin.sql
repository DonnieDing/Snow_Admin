/*
Navicat MySQL Data Transfer

Source Server         : localhost（MySQL）
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : snow-admin

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2021-10-12 09:38:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `pid` bigint(20) NOT NULL COMMENT '所属上级id',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '权限名称',
  `type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '类型(1:菜单,2:按钮)',
  `permission_value` varchar(50) DEFAULT NULL COMMENT '权限值',
  `path` varchar(100) DEFAULT NULL COMMENT '访问路径',
  `component` varchar(100) DEFAULT NULL COMMENT '组件路径',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态(0:禁止,1:正常)',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_pid` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=1031 DEFAULT CHARSET=utf8 COMMENT='权限';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', '0', '全部数据', '0', null, null, null, null, null, '0', '2019-11-15 17:13:06', '2019-11-15 17:13:06');
INSERT INTO `sys_permission` VALUES ('10', '1', '权限管理', '1', null, '/acl', 'Layout', null, null, '0', '2019-11-15 17:13:06', '2019-11-18 13:54:25');
INSERT INTO `sys_permission` VALUES ('11', '1', '讲师管理', '1', null, '/teacher', 'Layout', null, null, '0', '2019-11-15 22:34:49', '2019-11-15 22:34:49');
INSERT INTO `sys_permission` VALUES ('12', '1', '课程分类', '1', null, '/subject', 'Layout', null, null, '0', '2019-11-15 22:38:15', '2019-11-15 22:38:15');
INSERT INTO `sys_permission` VALUES ('13', '1', '课程管理', '1', null, '/course', 'Layout', null, null, '0', '2019-11-15 22:40:21', '2019-11-15 22:40:21');
INSERT INTO `sys_permission` VALUES ('14', '1', '统计分析', '1', null, '/statistics', 'Layout', null, null, '0', '2019-11-15 22:44:27', '2019-11-15 22:44:27');
INSERT INTO `sys_permission` VALUES ('15', '1', '内容管理', '1', null, '/cms', 'Layout', null, null, '0', '2019-11-15 22:47:11', '2021-04-27 14:29:47');
INSERT INTO `sys_permission` VALUES ('16', '1', '订单管理', '1', null, '/order', 'Layout', null, null, '0', '2019-11-15 22:53:15', '2019-11-15 22:53:15');
INSERT INTO `sys_permission` VALUES ('100', '10', '用户管理', '1', null, 'user/list', '/acl/user/list', null, null, '0', '2019-11-15 17:13:40', '2019-11-18 13:53:12');
INSERT INTO `sys_permission` VALUES ('101', '10', '角色管理', '1', null, 'role/list', '/acl/role/list', null, null, '0', '2019-11-15 17:14:21', '2019-11-15 17:14:21');
INSERT INTO `sys_permission` VALUES ('102', '10', '菜单管理', '1', null, 'menu/list', '/acl/menu/list', null, null, '0', '2019-11-15 17:14:46', '2019-11-15 17:14:46');
INSERT INTO `sys_permission` VALUES ('103', '11', '讲师列表', '1', null, 'table', '/edu/teacher/list', null, null, '0', '2019-11-15 22:35:52', '2019-11-15 22:35:52');
INSERT INTO `sys_permission` VALUES ('104', '11', '添加讲师', '1', null, 'save', '/edu/teacher/save', null, null, '0', '2019-11-15 22:36:18', '2019-11-15 22:36:18');
INSERT INTO `sys_permission` VALUES ('105', '12', '课程分类列表', '1', null, 'list', '/edu/subject/list', null, null, '0', '2019-11-15 22:38:38', '2019-11-15 22:38:38');
INSERT INTO `sys_permission` VALUES ('106', '12', '导入课程分类', '1', null, 'save', '/edu/subject/save', null, null, '0', '2019-11-15 22:39:03', '2019-11-15 22:39:03');
INSERT INTO `sys_permission` VALUES ('107', '13', '课程列表', '1', null, 'list', '/edu/course/list', null, null, '0', '2019-11-15 22:40:42', '2019-11-15 22:40:42');
INSERT INTO `sys_permission` VALUES ('108', '13', '发布课程', '1', null, 'info', '/edu/course/info', null, null, '0', '2019-11-15 22:41:06', '2019-11-15 22:41:06');
INSERT INTO `sys_permission` VALUES ('109', '14', '生成统计', '1', null, 'create', '/statistics/create', null, null, '0', '2019-11-15 22:44:53', '2019-11-15 22:44:53');
INSERT INTO `sys_permission` VALUES ('110', '14', '统计图表', '1', null, 'show', '/statistics/show', null, null, '0', '2019-11-15 22:45:13', '2019-11-15 22:45:13');
INSERT INTO `sys_permission` VALUES ('111', '14', '数据统计', '1', null, 'allDataShow', '/statistics/allDataShow', null, null, '0', '2021-02-21 11:42:53', '2021-02-21 11:42:57');
INSERT INTO `sys_permission` VALUES ('112', '15', 'Bander列表', '1', null, 'banner/list', '/cms/banner/list', null, null, '0', '2019-11-15 22:51:01', '2019-11-18 10:51:29');
INSERT INTO `sys_permission` VALUES ('113', '15', 'Bander预览', '1', null, 'banner/show', '/cms/banner/show', null, null, '0', '2021-04-27 14:24:29', '2021-04-27 14:24:31');
INSERT INTO `sys_permission` VALUES ('114', '16', '订单列表', '1', null, 'list', '/order/list', null, null, '0', '2019-11-15 22:53:33', '2019-11-15 22:53:58');
INSERT INTO `sys_permission` VALUES ('115', '16', '订单预览', '1', null, 'show', 'order/show', null, null, '0', '2021-04-27 14:27:53', '2021-04-27 14:27:53');
INSERT INTO `sys_permission` VALUES ('1000', '100', '查看', '2', 'user.list', '', '', null, null, '0', '2019-11-15 17:15:45', '2019-11-17 21:57:16');
INSERT INTO `sys_permission` VALUES ('1001', '100', '添加', '2', 'user.add', 'user/add', '/acl/user/form', null, null, '0', '2019-11-15 17:16:22', '2019-11-15 17:16:22');
INSERT INTO `sys_permission` VALUES ('1002', '100', '修改', '2', 'user.update', 'user/update/:id', '/acl/user/form', null, null, '0', '2019-11-15 17:17:04', '2019-11-15 17:17:04');
INSERT INTO `sys_permission` VALUES ('1003', '100', '删除', '2', 'user.remove', '', '', null, null, '0', '2019-11-15 17:17:22', '2019-11-15 17:17:22');
INSERT INTO `sys_permission` VALUES ('1004', '101', '修改', '2', 'role.update', 'role/update/:id', '/acl/role/form', null, null, '0', '2019-11-15 17:18:27', '2019-11-15 17:19:53');
INSERT INTO `sys_permission` VALUES ('1005', '101', '查看', '2', 'role.list', '', '', null, null, '0', '2019-11-15 17:18:47', '2019-11-15 17:18:47');
INSERT INTO `sys_permission` VALUES ('1006', '101', '添加', '2', 'role.add', 'role/add', '/acl/role/form', null, null, '0', '2019-11-15 17:19:19', '2019-11-18 11:05:42');
INSERT INTO `sys_permission` VALUES ('1007', '101', '删除', '2', 'role.remove', '', '', null, null, '0', '2019-11-15 17:20:55', '2019-11-15 17:20:55');
INSERT INTO `sys_permission` VALUES ('1008', '101', '角色权限', '2', 'role.acl', 'role/distribution/:id', '/acl/role/roleForm', null, null, '0', '2019-11-15 17:21:38', '2019-11-15 17:21:38');
INSERT INTO `sys_permission` VALUES ('1009', '102', '查看', '2', 'permission.list', '', '', null, null, '0', '2019-11-15 17:22:07', '2019-11-15 17:22:07');
INSERT INTO `sys_permission` VALUES ('1010', '102', '添加', '2', 'permission.add', '', '', null, null, '0', '2019-11-15 17:22:23', '2019-11-15 17:22:23');
INSERT INTO `sys_permission` VALUES ('1011', '102', '修改', '2', 'permission.update', '', '', null, null, '0', '2019-11-15 17:22:35', '2019-11-15 17:22:35');
INSERT INTO `sys_permission` VALUES ('1012', '102', '删除', '2', 'permission.remove', '', '', null, null, '0', '2019-11-15 17:22:41', '2019-11-15 17:22:41');
INSERT INTO `sys_permission` VALUES ('1013', '104', '添加', '2', 'teacher.add', '', '', null, null, '0', '2019-11-15 22:36:34', '2019-11-15 22:36:34');
INSERT INTO `sys_permission` VALUES ('1014', '103', '查看', '2', 'teacher.list', '', '', null, null, '0', '2019-11-15 22:36:58', '2019-11-15 22:36:58');
INSERT INTO `sys_permission` VALUES ('1015', '103', '修改', '2', 'teacher.update', 'edit/:id', '/edu/teacher/save', null, null, '0', '2019-11-15 22:37:31', '2019-11-15 22:37:31');
INSERT INTO `sys_permission` VALUES ('1016', '103', '删除', '2', 'teacher.remove', '', '', null, null, '0', '2019-11-15 22:37:48', '2019-11-15 22:37:48');
INSERT INTO `sys_permission` VALUES ('1017', '105', '查看', '2', 'subject.list', '', '', null, null, '0', '2019-11-15 22:39:29', '2019-11-15 22:39:29');
INSERT INTO `sys_permission` VALUES ('1018', '106', '导入', '2', 'subject.import', '', '', null, null, '0', '2019-11-15 22:39:47', '2019-11-15 22:39:47');
INSERT INTO `sys_permission` VALUES ('1019', '107', '完成发布', '2', 'course.publish', 'publish/:id', '/edu/course/publish', null, null, '0', '2019-11-15 22:41:40', '2019-11-15 22:44:01');
INSERT INTO `sys_permission` VALUES ('1020', '107', '编辑课程', '2', 'course.update', 'info/:id', '/edu/course/info', null, null, '0', '2019-11-15 22:42:19', '2019-11-15 22:42:19');
INSERT INTO `sys_permission` VALUES ('1021', '107', '编辑课程大纲', '2', 'chapter.update', 'chapter/:id', '/edu/course/chapter', null, null, '0', '2019-11-15 22:43:17', '2019-11-15 22:43:17');
INSERT INTO `sys_permission` VALUES ('1022', '110', '查看', '2', 'daily.list', '', '', null, null, '0', '2019-11-15 22:45:30', '2019-11-15 22:45:30');
INSERT INTO `sys_permission` VALUES ('1023', '109', '生成', '2', 'daily.add', '', '', null, null, '0', '2019-11-15 22:45:51', '2019-11-15 22:45:51');
INSERT INTO `sys_permission` VALUES ('1024', '112', '查看', '2', 'banner.list', '', '', null, null, '0', '2019-11-15 22:48:24', '2019-11-15 22:48:24');
INSERT INTO `sys_permission` VALUES ('1025', '112', '添加', '2', 'banner.add', 'banner/add', '/cms/banner/form', null, null, '0', '2019-11-15 22:48:37', '2019-11-18 10:52:10');
INSERT INTO `sys_permission` VALUES ('1026', '112', '修改', '2', 'banner.update', 'banner/update/:id', '/cms/banner/form', null, null, '0', '2019-11-15 22:49:11', '2019-11-18 10:52:05');
INSERT INTO `sys_permission` VALUES ('1027', '112', '删除', '2', 'banner.remove', '', '', null, null, '0', '2019-11-15 22:51:39', '2019-11-15 22:51:39');
INSERT INTO `sys_permission` VALUES ('1028', '114', '查看', '2', 'order.list', '', '', null, null, '0', '2019-11-15 22:54:12', '2019-11-15 22:54:12');
INSERT INTO `sys_permission` VALUES ('1029', '100', '分配角色', '2', 'user.assgin', 'user/role/:id', '/acl/user/roleForm', null, null, '0', '2019-11-18 13:38:56', '2019-11-18 13:38:56');
INSERT INTO `sys_permission` VALUES ('1030', '115', '查看', '2', 'select', '', '', null, null, '0', '2021-04-29 16:59:09', '2021-04-29 16:59:09');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(20) NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_code` varchar(20) DEFAULT NULL COMMENT '角色编码',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '系统管理员', null, null, '0', '2019-11-11 13:09:32', '2019-11-18 10:27:18');
INSERT INTO `sys_role` VALUES ('2', '课程管理员', null, null, '0', '2019-11-11 13:09:45', '2019-11-18 10:25:44');
INSERT INTO `sys_role` VALUES ('6', '测试角色', null, null, '0', '2021-08-27 11:13:06', '2021-08-27 11:13:06');
INSERT INTO `sys_role` VALUES ('7', '测试角色00', null, null, '0', '2021-09-15 10:39:33', '2021-09-15 10:40:30');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `permission_id` bigint(20) NOT NULL COMMENT '权限id',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 COMMENT='角色权限';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1', '1', '1030', '0', '2021-08-26 23:16:40', '2021-08-26 23:16:42');
INSERT INTO `sys_role_permission` VALUES ('2', '1', '1028', '0', '2021-08-26 23:16:01', '2021-08-26 23:16:03');
INSERT INTO `sys_role_permission` VALUES ('3', '1', '1029', '0', '2021-08-26 23:16:24', '2021-08-26 23:16:26');
INSERT INTO `sys_role_permission` VALUES ('4', '1', '1023', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('5', '1', '1024', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('6', '1', '1025', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('7', '1', '1026', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('8', '1', '15', '0', '2019-11-18 14:22:29', '2019-11-18 14:22:29');
INSERT INTO `sys_role_permission` VALUES ('9', '1', '1', '0', '2019-11-18 14:22:29', '2019-11-18 14:22:29');
INSERT INTO `sys_role_permission` VALUES ('10', '1', '10', '0', '2019-11-18 14:22:29', '2019-11-18 14:22:29');
INSERT INTO `sys_role_permission` VALUES ('11', '1', '11', '0', '2019-11-18 14:22:29', '2019-11-18 14:22:29');
INSERT INTO `sys_role_permission` VALUES ('12', '1', '12', '0', '2019-11-18 14:22:29', '2019-11-18 14:22:29');
INSERT INTO `sys_role_permission` VALUES ('13', '1', '13', '0', '2019-11-18 14:22:29', '2019-11-18 14:22:29');
INSERT INTO `sys_role_permission` VALUES ('14', '1', '14', '0', '2019-11-18 14:22:29', '2019-11-18 14:22:29');
INSERT INTO `sys_role_permission` VALUES ('15', '1', '1027', '0', '2021-05-10 15:10:28', '2021-05-10 15:10:31');
INSERT INTO `sys_role_permission` VALUES ('16', '1', '16', '0', '2019-11-18 14:22:29', '2019-11-18 14:22:29');
INSERT INTO `sys_role_permission` VALUES ('17', '1', '100', '0', '2019-11-18 14:22:29', '2019-11-18 14:22:29');
INSERT INTO `sys_role_permission` VALUES ('18', '1', '101', '0', '2019-11-18 14:22:29', '2019-11-18 14:22:29');
INSERT INTO `sys_role_permission` VALUES ('19', '1', '102', '0', '2019-11-18 14:22:29', '2019-11-18 14:22:29');
INSERT INTO `sys_role_permission` VALUES ('20', '1', '103', '0', '2019-11-18 14:22:29', '2019-11-18 14:22:29');
INSERT INTO `sys_role_permission` VALUES ('21', '1', '104', '0', '2019-11-18 14:22:29', '2019-11-18 14:22:29');
INSERT INTO `sys_role_permission` VALUES ('22', '1', '105', '0', '2019-11-18 14:22:29', '2019-11-18 14:22:29');
INSERT INTO `sys_role_permission` VALUES ('23', '1', '106', '0', '2019-11-18 14:22:29', '2019-11-18 14:22:29');
INSERT INTO `sys_role_permission` VALUES ('24', '1', '107', '0', '2019-11-18 14:22:29', '2019-11-18 14:22:29');
INSERT INTO `sys_role_permission` VALUES ('25', '1', '108', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('26', '1', '109', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('27', '1', '110', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('28', '1', '111', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('29', '1', '112', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('30', '1', '113', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('31', '1', '114', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('32', '1', '115', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('33', '1', '1000', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('34', '1', '1001', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('35', '1', '1002', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('36', '1', '1003', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('37', '1', '1004', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('38', '1', '1005', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('39', '1', '1006', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('40', '1', '1007', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('41', '1', '1008', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('42', '1', '1009', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('43', '1', '1010', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('44', '1', '1011', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('45', '1', '1012', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('46', '1', '1013', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('47', '1', '1014', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('48', '1', '1015', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('49', '1', '1016', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('50', '1', '1017', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('51', '1', '1018', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('52', '1', '1019', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('53', '1', '1020', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('54', '1', '1021', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('55', '1', '1022', '0', '2019-11-18 14:22:30', '2019-11-18 14:22:30');
INSERT INTO `sys_role_permission` VALUES ('56', '2', '12', '0', '2021-02-20 16:58:34', '2021-02-20 16:58:37');
INSERT INTO `sys_role_permission` VALUES ('57', '2', '13', '0', '2021-02-20 17:00:17', '2021-02-20 17:00:18');
INSERT INTO `sys_role_permission` VALUES ('58', '2', '1', '0', '2021-02-20 17:00:47', '2021-02-20 17:00:49');
INSERT INTO `sys_role_permission` VALUES ('59', '2', '105', '0', '2021-02-20 16:59:19', '2021-02-20 16:59:21');
INSERT INTO `sys_role_permission` VALUES ('60', '2', '106', '0', '2021-02-20 16:59:31', '2021-02-20 16:59:33');
INSERT INTO `sys_role_permission` VALUES ('61', '2', '107', '0', '2021-02-20 16:15:58', '2021-02-20 16:16:01');
INSERT INTO `sys_role_permission` VALUES ('62', '2', '108', '0', '2021-02-20 16:41:57', '2021-02-20 16:42:00');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名称',
  `password` varchar(64) NOT NULL DEFAULT '' COMMENT '用户密码',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '用户昵称',
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '$2a$10$0/E5EhjcDYUxm.WTuO0RpOsNlZGsHf3zdMEpI1zHiT8GWuFsAhY8G', 'admin', null, null, '男', 'http://192.168.0.196:8002/archive/2020/12/28/0a0c4774-cdea-42b8-b219-4801dc72380e.gif', '0', '2019-11-01 10:39:47', '2019-11-01 10:39:47');
INSERT INTO `sys_user` VALUES ('2', 'class_admin', '$2a$10$ZwcMZNPJ6uPzS60Rd09iye7P50RGm0uG/uJB9zHRaJd3X0rVCfM5W', 'nickname', null, null, '女', 'http://192.168.0.196:8002/archive/2020/12/28/0a0c4774-cdea-42b8-b219-4801dc72380e.gif', '0', '2021-04-27 09:21:48', '2021-05-19 10:17:17');
INSERT INTO `sys_user` VALUES ('3', 'Dcl_Snow', '$2a$10$ZwcMZNPJ6uPzS60Rd09iye7P50RGm0uG/uJB9zHRaJd3X0rVCfM5W', '昵称', null, null, '男', 'http://192.168.0.196:8002/archive/2020/12/28/0a0c4774-cdea-42b8-b219-4801dc72380e.gif', '0', '2021-04-13 15:47:02', '2021-04-13 15:47:02');
INSERT INTO `sys_user` VALUES ('5', 'test', '$2a$10$0/E5EhjcDYUxm.WTuO0RpOsNlZGsHf3zdMEpI1zHiT8GWuFsAhY8G', 'Dcl_Snow', '15000000001', 'null@163.com', 'MAN', 'http://192.168.0.196:8002/archive/2020/12/28/0a0c4774-cdea-42b8-b219-4801dc72380e.gif', '0', '2021-08-24 17:06:45', '2021-08-27 10:57:21');
INSERT INTO `sys_user` VALUES ('7', 'Snow', '$2a$10$0/E5EhjcDYUxm.WTuO0RpOsNlZGsHf3zdMEpI1zHiT8GWuFsAhY8G', '昵称', '13900000000', 'dcl_snow@163.com', '男', 'http://192.168.0.196:8002/archive/2020/12/28/0a0c4774-cdea-42b8-b219-4801dc72380e.gif', '0', '2021-08-24 22:51:18', '2021-08-24 22:51:18');
INSERT INTO `sys_user` VALUES ('8', 'test1', '$2a$10$0/E5EhjcDYUxm.WTuO0RpOsNlZGsHf3zdMEpI1zHiT8GWuFsAhY8G', 'Dcl_Snow', '15000000001', 'null@163.com', 'MAN', 'http://192.168.0.196:8002/archive/2020/12/28/0a0c4774-cdea-42b8-b219-4801dc72380e.gif', '0', '2021-08-27 11:11:29', '2021-08-31 11:35:45');
INSERT INTO `sys_user` VALUES ('9', 'test3', '$2a$10$ZwcMZNPJ6uPzS60Rd09iye7P50RGm0uG/uJB9zHRaJd3X0rVCfM5W', '昵称2', '13900000005', 'abc@163.com', '男', 'http://192.168.0.196:8002/archive/2020/12/28/0a0c4774-cdea-42b8-b219-4801dc72380e.gif', '0', '2021-09-10 14:43:38', '2021-09-10 14:43:38');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1', '0', '2019-11-11 13:09:53', '2019-11-11 13:09:53');
INSERT INTO `sys_user_role` VALUES ('2', '2', '2', '0', '2021-04-13 15:51:53', '2021-04-13 15:51:53');
INSERT INTO `sys_user_role` VALUES ('3', '1', '3', '0', '2021-02-19 14:06:57', '2021-02-19 14:06:59');
