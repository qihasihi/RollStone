ALTER TABLE `m_school`.`class_info`
 
ADD COLUMN `activity_type` INT NULL DEFAULT NULL COMMENT '活动类型 1-菁英班活动 2-普通班' AFTER `im_valdate_code`,

 ADD COLUMN `term_id` INT NULL DEFAULT NULL COMMENT '期次id' AFTER `activity_type`;