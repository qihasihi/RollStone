/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.5.32-log : Database - m_school
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `sync_class_stat_log` */

DROP TABLE IF EXISTS `sync_class_stat_log`;

CREATE TABLE `sync_class_stat_log` (
  `ref` int(11) NOT NULL AUTO_INCREMENT,
  `grade` varchar(100) DEFAULT NULL,
  `wx_num` int(11) DEFAULT NULL,
  `xx_num` int(11) DEFAULT NULL,
  `ax_num` int(11) DEFAULT NULL,
  `c_time` datetime DEFAULT NULL,
  `school_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`ref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sync_cls_user_stat_log` */

DROP TABLE IF EXISTS `sync_cls_user_stat_log`;

CREATE TABLE `sync_cls_user_stat_log` (
  `ref` int(11) NOT NULL AUTO_INCREMENT,
  `grade` varchar(100) DEFAULT NULL COMMENT '年级ID',
  `wx_stu_num` int(11) DEFAULT NULL COMMENT '网校学生数据量',
  `xx_stu_num` int(11) DEFAULT NULL COMMENT '学校学生数据量',
  `ax_stu_num` int(11) DEFAULT NULL COMMENT '爱学学生数据量',
  `c_time` datetime DEFAULT NULL,
  `school_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`ref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sync_course_stat_log` */

DROP TABLE IF EXISTS `sync_course_stat_log`;

CREATE TABLE `sync_course_stat_log` (
  `ref` int(11) NOT NULL AUTO_INCREMENT,
  `grade_id` int(11) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `new_course_num` int(11) DEFAULT NULL,
  `quote_course_num` int(11) DEFAULT NULL,
  `task_num` int(11) DEFAULT NULL,
  `c_time` datetime DEFAULT NULL,
  PRIMARY KEY (`ref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sync_dynamic_info` */

DROP TABLE IF EXISTS `sync_dynamic_info`;

CREATE TABLE `sync_dynamic_info` (
  `dynamic_id` int(11) NOT NULL AUTO_INCREMENT,
  `dynamic_name` varchar(50) DEFAULT NULL,
  `c_time` datetime DEFAULT NULL,
  PRIMARY KEY (`dynamic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Table structure for table `sync_task_rate_log` */

DROP TABLE IF EXISTS `sync_task_rate_log`;

CREATE TABLE `sync_task_rate_log` (
  `ref` int(11) NOT NULL AUTO_INCREMENT,
  `subject_id` int(11) DEFAULT NULL COMMENT '学科ID',
  `res_rate` int(11) DEFAULT NULL COMMENT '资源任务比率',
  `topic_rate` int(11) DEFAULT NULL COMMENT '互动论题比率',
  `question_rate` int(11) DEFAULT NULL COMMENT '试题比率',
  `tea_paper_rate` int(11) DEFAULT NULL COMMENT '成卷',
  `stu_paper_rate` int(11) DEFAULT NULL COMMENT '自主测试',
  `mic_video_rate` int(11) DEFAULT NULL COMMENT '微视频比率',
  `live_rate` int(11) DEFAULT NULL COMMENT '直播课',
  `app_rate` int(11) DEFAULT NULL COMMENT '移动端任务比率',
  `c_time` datetime DEFAULT NULL,
  PRIMARY KEY (`ref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sync_task_stat_log` */

DROP TABLE IF EXISTS `sync_task_stat_log`;

CREATE TABLE `sync_task_stat_log` (
  `ref` int(11) NOT NULL AUTO_INCREMENT,
  `school_id` int(11) DEFAULT NULL COMMENT '分校',
  `course_num` int(11) DEFAULT NULL COMMENT '专题数据',
  `task_num` int(11) DEFAULT NULL COMMENT '任务数据量',
  `task_rate` int(11) DEFAULT NULL COMMENT '任务完成率',
  `res_upload_num` int(11) DEFAULT NULL COMMENT '资源上传数量',
  `app_task_rate` int(11) DEFAULT NULL COMMENT '移动任务所占比率',
  `c_time` datetime DEFAULT NULL,
  PRIMARY KEY (`ref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sync_user_dynamic_app_log` */

DROP TABLE IF EXISTS `sync_user_dynamic_app_log`;

CREATE TABLE `sync_user_dynamic_app_log` (
  `ref` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `dynamic_id` int(11) DEFAULT NULL,
  `IP` varchar(100) DEFAULT NULL,
  `c_time` datetime DEFAULT NULL,
  `m_time` datetime DEFAULT NULL,
  PRIMARY KEY (`ref`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sync_user_dynamic_pc_log` */

DROP TABLE IF EXISTS `sync_user_dynamic_pc_log`;

CREATE TABLE `sync_user_dynamic_pc_log` (
  `ref` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `ett_user_id` int(11) DEFAULT NULL COMMENT 'JID',
  `user_type` int(11) DEFAULT NULL COMMENT '用户类型 1：教师  2：学生',
  `dynamic_id` int(11) DEFAULT NULL COMMENT '动作ID',
  `source` int(1) DEFAULT NULL COMMENT '1:数字化校园学习平台  2：网校爱学课堂栏目',
  `c_time` datetime DEFAULT NULL,
  PRIMARY KEY (`ref`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `sync_user_stat_log` */

DROP TABLE IF EXISTS `sync_user_stat_log`;

CREATE TABLE `sync_user_stat_log` (
  `ref` int(11) NOT NULL AUTO_INCREMENT,
  `school_id` int(11) DEFAULT NULL,
  `type` int(1) DEFAULT NULL,
  `class_num` int(11) DEFAULT NULL,
  `teacher_num` int(11) DEFAULT NULL,
  `student_num` int(11) DEFAULT NULL,
  `c_time` datetime DEFAULT NULL,
  `province` int(11) DEFAULT NULL,
  PRIMARY KEY (`ref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
