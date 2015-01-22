DELIMITER $$

USE `m_school`$$

DROP PROCEDURE IF EXISTS `tp_theme_reply_info_pinglunshu`$$

CREATE DEFINER=`mytest`@`%` PROCEDURE `tp_theme_reply_info_pinglunshu`(
					  p_topic_id VARCHAR(10000),
					  p_cls_id BIGINT,
					  OUT outNUm VARCHAR(1000)
				          )
BEGIN
	SET outNUm=0;
	IF p_cls_id IS NULL THEN
		-- 得到评论数
		SELECT COUNT(rt1.reply_id) INTO @afficeRows FROM tp_theme_reply_info rt1 WHERE  theme_id IN (
					SELECT theme_id FROM tp_topic_theme_info WHERE course_id IN (
					SELECT COURSE_ID FROM tp_j_course_class cc
					,(SELECT term_id,grade_id,subject_id FROM tp_j_course_class cc WHERE course_id=
					(SELECT course_id FROM tp_topic_info WHERE topic_id=p_topic_id)) t
					WHERE cc.term_id=t.term_id AND cc.grade_id=t.grade_id AND cc.subject_id=t.subject_id
				) AND topic_id=p_topic_id
		) AND rt1.`to_replyid` IS NULL;
		IF @afficeRows IS NOT NULL THEN
		   SET outNUm=@afficeRows;
		END IF;
	ELSE
				-- 得到评论数
		SELECT COUNT(rt1.reply_id) INTO @afficeRows FROM tp_theme_reply_info rt1 WHERE  theme_id IN (
					SELECT theme_id FROM tp_topic_theme_info WHERE course_id IN (
					SELECT COURSE_ID FROM tp_j_course_class cc
					,(SELECT term_id,grade_id,subject_id FROM tp_j_course_class cc WHERE course_id=
					(SELECT course_id FROM tp_topic_info WHERE topic_id=p_topic_id)) t
					WHERE cc.term_id=t.term_id AND cc.grade_id=t.grade_id AND cc.subject_id=t.subject_id
					AND cc.class_id=p_cls_id
				) AND topic_id=p_topic_id
		) AND rt1.`to_replyid` IS NULL;
		IF @afficeRows IS NOT NULL THEN
		   SET outNUm=@afficeRows;
		END IF;
	END IF;
    END$$

DELIMITER ;