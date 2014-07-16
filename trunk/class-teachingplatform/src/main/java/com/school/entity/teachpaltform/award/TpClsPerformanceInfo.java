package com.school.entity.teachpaltform.award;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by zhengzhou on 14-6-24.
 */
@Entity
public class TpClsPerformanceInfo implements  Serializable{
    private Integer ref;
    private Long userid;
    private  Integer attendanceNum;
    private Integer similingNum;
    private Integer violationDisNum;
    private Long groupid;
    private Long courseid;

    private String realName;
    private String groupName;
    private Integer subjectid;
    public Integer getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(Integer subjectid) {
        this.subjectid = subjectid;
    }
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

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
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

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }
}
