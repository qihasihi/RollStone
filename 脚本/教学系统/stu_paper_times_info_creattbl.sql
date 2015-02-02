DELIMITER $$

USE `school201501`$$

CREATE TABLE `stu_paper_times_info` (
  `ref` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增',
  `task_id` bigint(20) NOT NULL COMMENT '任务ID',
  `user_id` bigint(20) NOT NULL COMMENT '做卷人',
  `paper_id` bigint(20) NOT NULL COMMENT '试卷ID',
  `begin_time` datetime NOT NULL COMMENT '做题开始时间',
  `c_time` date NOT NULL COMMENT '记录产生时间',
  PRIMARY KEY (`ref`),
  UNIQUE KEY `unique_spt_uidpidtid` (`task_id`,`user_id`,`paper_id`),
  KEY `idx_spt_userId` (`user_id`),
  KEY `idx_spt_paperId` (`paper_id`),
  KEY `idx_spt_task_id` (`task_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

DELIMITER ;