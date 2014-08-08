package com.school.entity.teachpaltform.award;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by zhengzhou on 14-6-24.
 */
@Entity
public class TpStuScore implements  Serializable{
    private Integer ref;
    private Long userid;
    private  Integer attendanceNum;
    private Integer similingNum;
    private Integer violationDisNum;
    private Long groupid;
    private Long courseid;
    private Long classid;
    private Long dcschoolid;
    private Long coursetotalscore;
    private Long groupscore;
    private Integer classtype;

    public Integer getClasstype() {
        return classtype;
    }

    public void setClasstype(Integer classtype) {
        this.classtype = classtype;
    }

    public Long getClassid() {
        return classid;
    }

    public void setClassid(Long classid) {
        this.classid = classid;
    }

    public Long getDcschoolid() {
        return dcschoolid;
    }

    public void setDcschoolid(Long dcschoolid) {
        this.dcschoolid = dcschoolid;
    }

    public Long getCoursetotalscore() {
        return coursetotalscore;
    }

    public void setCoursetotalscore(Long coursetotalscore) {
        this.coursetotalscore = coursetotalscore;
    }

    public Long getGroupscore() {
        return groupscore;
    }

    public void setGroupscore(Long groupscore) {
        this.groupscore = groupscore;
    }

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
