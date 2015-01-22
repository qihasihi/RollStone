DELIMITER $$

ALTER DEFINER=`mytest`@`%` EVENT `generator_for_admin_performance_event` ON SCHEDULE EVERY 1 DAY STARTS '2015-01-10 04:00:00' ON COMPLETION PRESERVE ENABLE DO BEGIN
	    CALL generator_for_admin_performance();
	END$$

DELIMITER ;