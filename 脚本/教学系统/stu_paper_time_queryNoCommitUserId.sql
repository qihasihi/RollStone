DELIMITER $$

USE `school201501`$$

DROP PROCEDURE IF EXISTS `stu_paper_time_queryNoCommitUserId`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `stu_paper_time_queryNoCommitUserId`(
					p_task_id BIGINT,
                                           p_paper_id BIGINT
               )
BEGIN

 SELECT 
    pt.* 
  FROM
    stu_paper_times_info pt,
    tp_task_info t 
  WHERE pt.task_id = t.TASK_ID
    AND pt.task_id = p_task_id
    AND pt.paper_id = p_paper_id 
    AND DATE_ADD(
      pt.begin_time,
      INTERVAL CONCAT(
        '00:',
        t.allowComplete_time,
        ':00'
      ) DAY_SECOND
    ) < NOW() 
    AND NOT EXISTS 
    (SELECT 
    1 
  FROM
    stu_paper_logs spl 
  WHERE spl.`task_id` =pt.`task_id` 
    AND spl.`user_id`=pt.`user_id`
    AND spl.`paper_id` = pt.`paper_id`);


    END$$

DELIMITER ;