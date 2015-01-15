package com.school.entity.userlog;

import java.util.Date;

/**
 * Created by qihaishi on 15-1-14.
 */
public class UserDynamicInfo {
    private Integer dynamicid;
    private String dynamicname;
    private Date ctime;

    public static enum  DYNAMIC_NAME{
        /*教师*/
        ADD_COURSE{public Integer getVal(){return 1;}},
        ADD_TASK{public Integer getVal(){return 2;}},
        UPLOAD_RES{public Integer getVal(){return 3;}},
        QUOTE_RES{public Integer getVal(){return 4;}},
        COMMENT_RES{public Integer getVal(){return 5;}},
        ADD_TOPIC{public Integer getVal(){return 6;}},
        ADD_THEME{public Integer getVal(){return 7;}},
        ADD_QUES{public Integer getVal(){return 8;}},
        QUOTE_QUES{public Integer getVal(){return 9;}},
        ADD_PAPER{public Integer getVal(){return 10;}},

        LOGIN{public Integer getVal(){return 11;}},

        /*学生*/

        /**
         * 学习心得
         */
        TASK_XINDE{public Integer getVal(){return 12;}},
        /**
         * 任务发主帖
         */
        TASK_THEME{public Integer getVal(){return 13;}},
        /**
         * 答题
         */
        TASK_QUES{public Integer getVal(){return 14;}},
        /**
         * 答卷，成卷
         */
        TASK_CJ_PAPER{public Integer getVal(){return 15;}},
        /**
         * 答卷，自主测试
         */
        TASK_ZZ_PAPER{public Integer getVal(){return 16;}},
        /**
         * 微视频任务
         */
        TASK_MIC_PAPER{public Integer getVal(){return 17;}},
        /**
         * 专题资源评论
         */
        STU_COMMENT_RES{public Integer getVal(){return 18;}},
        /**
         * 专题资源上传
         */
        STU_ADD_RES{public Integer getVal(){return 19;}},
        /**
         * 互动空间添加主帖
         */
        STU_ADD_THEME{public Integer getVal(){return 20;}};
        public abstract Integer getVal();
    }


    public Integer getDynamicid() {
        return dynamicid;
    }

    public void setDynamicid(Integer dynamicid) {
        this.dynamicid = dynamicid;
    }

    public String getDynamicname() {
        return dynamicname;
    }

    public void setDynamicname(String dynamicname) {
        this.dynamicname = dynamicname;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}
