package com.school.entity;

import com.school.util.UtilTool;

import javax.persistence.Entity;
import java.util.Date;

/**
 * Created by yuechunyang on 15-1-12.
 */
@Entity
public class AdminPerformance  implements java.io.Serializable {
    private Integer ref;
    private Integer classid;
    private Long courseid;
    private Integer gradeid;
    private Integer subjectid;
    private String coursename;
    private Integer tasknum;
    private Integer endtasknum;
    private String completerate;
    private String evaluation;
    private String resourcetask;
    private String interactivetask;
    private String microtask;
    private String coilingtesttask;
    private String selftesttask;
    private String livetask;
    private String questask;
    private String generaltask;
    private Date coursetime;

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public Integer getClassid() {
        return classid;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }

    public Integer getGradeid() {
        return gradeid;
    }

    public void setGradeid(Integer gradeid) {
        this.gradeid = gradeid;
    }

    public Integer getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(Integer subjectid) {
        this.subjectid = subjectid;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public Integer getTasknum() {
        return tasknum;
    }

    public void setTasknum(Integer tasknum) {
        this.tasknum = tasknum;
    }

    public Integer getEndtasknum() {
        return endtasknum;
    }

    public void setEndtasknum(Integer endtasknum) {
        this.endtasknum = endtasknum;
    }

    public String getCompleterate() {
        return completerate;
    }

    public void setCompleterate(String completerate) {
        this.completerate = completerate;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getResourcetask() {
        return resourcetask;
    }

    public void setResourcetask(String resourcetask) {
        this.resourcetask = resourcetask;
    }

    public String getInteractivetask() {
        return interactivetask;
    }

    public void setInteractivetask(String interactivetask) {
        this.interactivetask = interactivetask;
    }

    public String getMicrotask() {
        return microtask;
    }

    public void setMicrotask(String microtask) {
        this.microtask = microtask;
    }

    public String getCoilingtesttask() {
        return coilingtesttask;
    }

    public void setCoilingtesttask(String coilingtesttask) {
        this.coilingtesttask = coilingtesttask;
    }

    public String getSelftesttask() {
        return selftesttask;
    }

    public void setSelftesttask(String selftesttask) {
        this.selftesttask = selftesttask;
    }

    public String getLivetask() {
        return livetask;
    }

    public void setLivetask(String livetask) {
        this.livetask = livetask;
    }

    public String getQuestask() {
        return questask;
    }

    public void setQuestask(String questask) {
        this.questask = questask;
    }

    public String getGeneraltask() {
        return generaltask;
    }

    public void setGeneraltask(String generaltask) {
        this.generaltask = generaltask;
    }

    public Date getCoursetime() {
        return coursetime;
    }

    public String getCoursetimestring(){
        if(coursetime==null)
            return null;
        return UtilTool.DateConvertToString(coursetime, UtilTool.DateType.type1);
    }

    public void setCoursetime(Date coursetime) {
        this.coursetime = coursetime;
    }
}
