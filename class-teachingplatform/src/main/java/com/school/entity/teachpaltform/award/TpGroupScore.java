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
    private Integer score1;//����+1�����ڳ�Աȫ���������޳ٵ�����
    private Integer score2;//����+3������Ц��������ȫ���һ
    private Integer score3;//����+3������С����������ȫ���һ
    private Integer score4;//����-1������Υ�����ɴ�����ȫ���һ
    private Integer score5;//����+3�����������������ƽ������ȫ���һ
    private Integer score1flag;//���ڳ�Աȫ���������޳ٵ����� ��1 �� 0��
    private Integer score2total;//����Ц������
    private Integer score4total;//����Υ�����ɴ���
    private Integer score5avg; //���������������ƽ����
    private Integer submitflag;//1�������ύ 0 δ�ύ
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
