package com.school.entity.teachpaltform.paper;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

/**
 * StuPaperTimesInfo
 * 学生做试题开始的时间存储
 * Created by zhengzhou on 15-2-2.
 */
@Entity
public class StuPaperTimesInfo implements  Serializable{
    private Integer ref;
    private Long taskId;
    private Long userId;
    private Long paperId;
    private Date beginTime;
    private Date ctime;

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}
