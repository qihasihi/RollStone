ALTER TABLE `m_school`.`class_info`
 
ADD COLUMN `activity_type` INT NULL DEFAULT NULL COMMENT '����� 1-ݼӢ�� 2-��ͨ��' AFTER `im_valdate_code`,

 ADD COLUMN `term_id` INT NULL DEFAULT NULL COMMENT '�ڴ�id' AFTER `activity_type`;