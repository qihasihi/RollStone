DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `rs_hot_rank_save`$$

CREATE DEFINER=`schu`@`%` PROCEDURE `rs_hot_rank_save`(
				           p_resid BIGINT,
			p_school_id BIGINT,
			p_type INT,
			p_downloadnum BIGINT,
			p_clicks BIGINT,
			p_commentnum BIGINT,
			p_storenum BIGINT,
			p_praisenum BIGINT,
			p_recomendnum BIGINT,
			p_reportnum BIGINT,
			 OUT affect_row INT)
BEGIN
	DECLARE	EXIT HANDLER FOR NOT	FOUND,SQLEXCEPTION SET affect_row =0;
        
        INSERT INTO rs_hot_rank(res_id,school_id,TYPE,downloadnum,clicks,commentnum,storenum,praisenum,recomendnum,reportnum)
        VALUES(p_resid,p_school_id,p_type,p_downloadnum,p_clicks,p_commentnum,p_storenum,p_praisenum,p_recomendnum,p_reportnum);		
	SET affect_row=1;
END $$

DELIMITER ;
