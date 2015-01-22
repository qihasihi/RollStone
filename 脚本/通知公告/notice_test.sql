DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `notice_test`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `notice_test`()
BEGIN
	declare idx int default '';
	declare noticename varchar(1000) default '';
	set idx=0;
	while idx<10000 do
		set noticename=concat("测试通知公告",idx+1);
		CALL notice_proc_add(idx+1,noticename,'阿斯顿发生代收代付撒地方撒地方阿斯蒂芬撒地方阿斯蒂芬阿斯蒂芬阿斯蒂芬阿斯蒂芬阿斯蒂芬阿斯蒂芬阿斯蒂芬阿斯蒂芬<br />',0,'1|2|3|4|5|6|9|11|12','高一|高二|高三',NULL,NULL,NULL,NULL,'5728d839-1a60-4466-b206-6c0176c73581',NULL,@a);
		set idx=idx+1;
	end while;
	
END $$

DELIMITER ;
