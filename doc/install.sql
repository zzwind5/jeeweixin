
SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `t_cms_sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_cms_sys_user`;
CREATE TABLE `t_cms_sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `realname` varchar(50) NOT NULL,
  `gender` int(1) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `birthday` varchar(50) DEFAULT NULL,
  `headImage` varchar(250) DEFAULT NULL,
  `enable` int(5) DEFAULT NULL,
  `ruleid` int(5) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `openid` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `loginname` (`loginname`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_cms_sys_user
-- ----------------------------
INSERT INTO `t_cms_sys_user` VALUES ('1', 'jeeweixin', '5RpuDkfdbTs1ctwfT6MurA==', '管理员', '1', null, null, null, null, '1', null, '2014-12-20 22:58:30', null);

-- ----------------------------
-- Table structure for `t_wxcms_account`
-- ----------------------------
DROP TABLE IF EXISTS `t_wxcms_account`;
CREATE TABLE `t_wxcms_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `account` varchar(100) NOT NULL,
  `appid` varchar(100) DEFAULT NULL,
  `appsecret` varchar(100) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `msgCount` int(11) DEFAULT '1',
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_wxcms_account
-- ----------------------------
INSERT INTO `t_wxcms_account` VALUES ('3', null, 'vp_weixinpy', 'wx4b2844432595ce08', '86923b5aa5f0f42b90d1e41d850752f2 ', 'http://www.jeeweixin.com/jeeweixin/wxapi/vp_weixinpy/message.html', '72597b9628704ab09e8b9e8cbe9b540a', '5', '2015-01-27 21:38:31');

-- ----------------------------
-- Table structure for `t_wxcms_account_fans`
-- ----------------------------
DROP TABLE IF EXISTS `t_wxcms_account_fans`;
CREATE TABLE `t_wxcms_account_fans` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(100) DEFAULT NULL,
  `subscribeStatus` int(1) DEFAULT '1',
  `subscribeTime` varchar(50) DEFAULT NULL,
  `nickname` varchar(50) DEFAULT NULL,
  `gender` tinyint(4) DEFAULT '1',
  `language` varchar(50) DEFAULT NULL,
  `country` varchar(30) DEFAULT NULL,
  `province` varchar(30) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `headimgurl` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1',
  `remark` varchar(50) DEFAULT NULL,
  `wxid` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_wxcms_account_fans
-- ----------------------------

-- ----------------------------
-- Table structure for `t_wxcms_account_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_wxcms_account_menu`;
CREATE TABLE `t_wxcms_account_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mtype` varchar(50) DEFAULT NULL,
  `eventType` varchar(50) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `inputCode` varchar(255) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  `msgId` varchar(100) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `gid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_wxcms_account_menu
-- ----------------------------

-- ----------------------------
-- Table structure for `t_wxcms_account_menu_group`
-- ----------------------------
DROP TABLE IF EXISTS `t_wxcms_account_menu_group`;
CREATE TABLE `t_wxcms_account_menu_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `enable` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_wxcms_account_menu_group
-- ----------------------------

-- ----------------------------
-- Table structure for `t_wxcms_msg_base`
-- ----------------------------
DROP TABLE IF EXISTS `t_wxcms_msg_base`;
CREATE TABLE `t_wxcms_msg_base` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `msgType` varchar(20) DEFAULT NULL,
  `inputCode` varchar(20) DEFAULT NULL,
  `rule` varchar(20) DEFAULT NULL,
  `enable` int(11) DEFAULT NULL,
  `readCount` int(11) DEFAULT '0',
  `favourCount` int(11) unsigned zerofill DEFAULT '00000000000',
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_wxcms_msg_base
-- ----------------------------
INSERT INTO `t_wxcms_msg_base` VALUES ('1', 'text', 'subscribe ', null, null, null, null, '2015-03-21 11:13:54');
INSERT INTO `t_wxcms_msg_base` VALUES ('2', 'news', '1', null, null, null, null, '2015-03-21 11:19:48');
INSERT INTO `t_wxcms_msg_base` VALUES ('3', 'news', '11', null, null, null, null, '2015-03-21 11:26:21');

-- ----------------------------
-- Table structure for `t_wxcms_msg_news`
-- ----------------------------
DROP TABLE IF EXISTS `t_wxcms_msg_news`;
CREATE TABLE `t_wxcms_msg_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `brief` varchar(255) DEFAULT NULL,
  `description` longtext,
  `picPath` varchar(255) DEFAULT NULL,
  `showPic` int(11) DEFAULT '0',
  `url` varchar(255) DEFAULT NULL,
  `fromurl` varchar(255) DEFAULT NULL,
  `base_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_wxcms_msg_news
-- ----------------------------
INSERT INTO `t_wxcms_msg_news` VALUES ('1', '为什么是jeeweixin', 'jeeweixin', '', '', 'http://www.jeeweixin.com/res/upload/1426908565922.jpg', '1', 'http://www.weixinpy.com/wx/act/103/msgdetail/?mid=1367&mtype=news', '', '2');
INSERT INTO `t_wxcms_msg_news` VALUES ('2', '微信开发教程', 'jeeweixin', '', '', 'http://www.jeeweixin.com/res/upload/1426908381642.jpg', null, '', 'http://weixinpy.com/vp/jiaocheng/index/', '3');

-- ----------------------------
-- Table structure for `t_wxcms_msg_text`
-- ----------------------------
DROP TABLE IF EXISTS `t_wxcms_msg_text`;
CREATE TABLE `t_wxcms_msg_text` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` longtext,
  `base_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_wxcms_msg_text
-- ----------------------------
INSERT INTO `t_wxcms_msg_text` VALUES ('1', '感谢您关注我们，更多消息请回复 1', '1');
