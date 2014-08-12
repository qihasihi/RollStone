package com.school.entity.teachpaltform.award;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;
@Entity
public class TpStuScoreLogs implements  Serializable{

	public void TpStuScoreLogs(){}
   
    private java.lang.Long taskid;
    private Date ctime;
    private java.lang.Long courseid;
    private java.lang.Integer jewel;
    private java.lang.Long ref;
    private java.lang.Long classid;
    private java.lang.Long userid;
    private java.lang.Integer score;

    public java.lang.Long getTaskid(){
      return taskid;
    }
    public void setTaskid(java.lang.Long taskid){
      this.taskid = taskid;
    }
    public Date getCtime(){
      return ctime;
    }
    public void setCtime(Date ctime){
      this.ctime = ctime;
    }
    public java.lang.Long getCourseid(){
      return courseid;
    }
    public void setCourseid(java.lang.Long courseid){
      this.courseid = courseid;
    }
    public java.lang.Integer getJewel(){
      return jewel;
    }
    public void setJewel(java.lang.Integer jewel){
      this.jewel = jewel;
    }
    public java.lang.Long getRef(){
      return ref;
    }
    public void setRef(java.lang.Long ref){
      this.ref = ref;
    }
    public java.lang.Long getClassid(){
      return classid;
    }
    public void setClassid(java.lang.Long classid){
      this.classid = classid;
    }
    public java.lang.Long getUserid(){
      return userid;
    }
    public void setUserid(java.lang.Long userid){
      this.userid = userid;
    }
    public java.lang.Integer getScore(){
      return score;
    }
    public void setScore(java.lang.Integer score){
      this.score = score;
    }
  

}