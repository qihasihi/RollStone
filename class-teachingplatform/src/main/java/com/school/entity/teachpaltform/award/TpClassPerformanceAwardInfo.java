package com.school.entity.teachpaltform.award;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhengzhou on 14-6-24.
 */
@Entity
public class TpClassPerformanceAwardInfo implements Serializable {
    private Integer ref;
    private Long groupid;
    private Integer awardnumber;
    private Long courseid;
    private Date ctime;
    private Date mtime;
    private Integer subjectid;
    public Integer getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(Integer subjectid) {
        this.subjectid = subjectid;
    }

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }

    public Integer getAwardnumber() {
        return awardnumber;
    }

    public void setAwardnumber(Integer awardnumber) {
        this.awardnumber = awardnumber;
    }

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }
}
