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
    private Integer groupid;
    private Integer awardnumber;
    private Integer courseid;
    private Date ctime;
    private Date mtime;

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public Integer getAwardnumber() {
        return awardnumber;
    }

    public void setAwardnumber(Integer awardnumber) {
        this.awardnumber = awardnumber;
    }

    public Integer getCourseid() {
        return courseid;
    }

    public void setCourseid(Integer courseid) {
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
