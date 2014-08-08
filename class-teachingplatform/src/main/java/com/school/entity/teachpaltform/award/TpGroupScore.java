package com.school.entity.teachpaltform.award;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhengzhou on 14-6-24.
 */
@Entity
public class TpGroupScore implements Serializable {
    private Integer ref;
    private Long groupid;
    private Integer awardnumber;
    private Long courseid;
    private Date ctime;
    private Date mtime;
    private Integer subjectid;
    private Long classid;
    private Long dcschoolid;
    private Integer score1;//分数+1：组内成员全部出勤且无迟到早退
    private Integer score2;//分数+3：本组笑脸总数排全班第一
    private Integer score3;//分数+3：本组小红旗总数排全班第一
    private Integer score4;//分数-1：本组违反纪律次数排全班第一
    private Integer score5;//分数+3：本组完成网上任务平均数排全班第一
    private Integer score1flag;//组内成员全部出勤且无迟到早退 （1 是 0否）
    private Integer score2total;//本组笑脸总数
    private Integer score4total;//本组违反纪律次数
    private Integer score5avg; //本组完成网上任务平均数
    private Integer submitflag;//1分数已提交 0 未提交
    private Integer classtype;

    public Integer getClasstype() {
        return classtype;
    }

    public void setClasstype(Integer classtype) {
        this.classtype = classtype;
    }

    public Integer getSubmitflag() {
        return submitflag;
    }

    public void setSubmitflag(Integer submitflag) {
        this.submitflag = submitflag;
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

    public Integer getScore1() {
        return score1;
    }

    public void setScore1(Integer score1) {
        this.score1 = score1;
    }

    public Integer getScore2() {
        return score2;
    }

    public void setScore2(Integer score2) {
        this.score2 = score2;
    }

    public Integer getScore3() {
        return score3;
    }

    public void setScore3(Integer score3) {
        this.score3 = score3;
    }

    public Integer getScore4() {
        return score4;
    }

    public void setScore4(Integer score4) {
        this.score4 = score4;
    }

    public Integer getScore5() {
        return score5;
    }

    public void setScore5(Integer score5) {
        this.score5 = score5;
    }

    public Integer getScore1flag() {
        return score1flag;
    }

    public void setScore1flag(Integer score1flag) {
        this.score1flag = score1flag;
    }

    public Integer getScore2total() {
        return score2total;
    }

    public void setScore2total(Integer score2total) {
        this.score2total = score2total;
    }

    public Integer getScore4total() {
        return score4total;
    }

    public void setScore4total(Integer score4total) {
        this.score4total = score4total;
    }

    public Integer getScore5avg() {
        return score5avg;
    }

    public void setScore5avg(Integer score5avg) {
        this.score5avg = score5avg;
    }

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
