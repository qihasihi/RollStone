package com.school.entity.teachpaltform.award;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by zhengzhou on 14-6-24.
 */
@Entity
public class TpClsPerformanceInfo implements  Serializable{
    private Integer ref;
    private Integer userid;
    private  Integer attendanceNum;
    private Integer similingNum;
    private Integer violationDisNum;
    private Integer groupid;
    private Integer courseid;

    private String realName;
    private String groupName;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getAttendanceNum() {
        return attendanceNum;
    }

    public void setAttendanceNum(int attendanceNum) {
        this.attendanceNum = attendanceNum;
    }

    public Integer getSimilingNum() {
        return similingNum;
    }

    public void setSimilingNum(int similingNum) {
        this.similingNum = similingNum;
    }

    public Integer getViolationDisNum() {
        return violationDisNum;
    }

    public void setViolationDisNum(int violationDisNum) {
        this.violationDisNum = violationDisNum;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public Integer getCourseid() {
        return courseid;
    }

    public void setCourseid(Integer courseid) {
        this.courseid = courseid;
    }
}
