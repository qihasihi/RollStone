ALTER  TABLE tp_theme_reply_info 
ADD to_replyid   BIGINT    COMMENT '批阅评语';
ALTER  TABLE tp_theme_reply_info 
ADD to_real_name   VARCHAR(100)    COMMENT '发送给的姓名';