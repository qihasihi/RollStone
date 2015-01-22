USE school;
UPDATE tp_course_info SET SHARE_TYPE=1 WHERE SHARE_TYPE=2 AND course_id<0 AND course_level=1;
