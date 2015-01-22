DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `dept_proc_delete`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `dept_proc_delete`(
				            p_dept_id INT,
					OUT affect_row INT)
BEGIN
	DECLARE	EXIT HANDLER FOR SQLWARNING,NOT	FOUND,SQLEXCEPTION SET affect_row =0;
	SET affect_row = 0;
	
	Begin
		DECLARE tmp_dept_id INT;
		DECLARE cursor_done INT DEFAULT 0;
		DECLARE cur_dept CURSOR FOR SELECT dept_id FROM dept_info WHERE FIND_IN_SET(dept_id,getChildList(p_dept_id));
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET cursor_done=1;
		
		open cur_dept;
		dept_loop: loop
			FETCH cur_dept into tmp_dept_id;
			if cursor_done=1 then
				leave dept_loop;
			end if;
			delete from dept_info where dept_id = tmp_dept_id;
		end loop dept_loop;
		close cur_dept;
		
		
		delete FROM j_user_dept_info WHERE dept_id IN (SELECT dept_id FROM dept_info WHERE FIND_IN_SET(dept_id,getChildList(p_dept_id)));
	end;
	
	SET affect_row = 1;
END $$

DELIMITER ;
