package com.school.entity;

import java.util.Date;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class GradeInfo implements java.io.Serializable {

    private Integer gradeid;
    private String gradename;
    private String gradevalue;
    private Date ctime;
    private TeacherInfo teacherinfo;
    private String zhengleader; //正组长
    private String fuleader;    //副组长
    
    public String getZhengleader() {  
		return zhengleader;
	}
	public void setZhengleader(String zhengleader) {
		this.zhengleader = zhengleader;
	}
	public String getFuleader() {
		return fuleader;
	}
	public void setFuleader(String fuleader) {
		this.fuleader = fuleader;
	}
	public String getTeachername(){
    	return this.getTeacherinfo().getTeachername();
    }
    public void setTeachername(String teachername){
    	this.getTeacherinfo().setTeachername(teachername);
    } 
    
	public TeacherInfo getTeacherinfo() {
		if(teacherinfo==null)
			teacherinfo=new TeacherInfo();
		return teacherinfo;
	}
	public void setTeacherinfo(TeacherInfo teacherinfo) {
		this.teacherinfo = teacherinfo;
	}
	public Integer getGradeid() {
		return gradeid;
	}
	public void setGradeid(Integer gradeid) {
		this.gradeid = gradeid;
	}
	public String getGradename() {
		return gradename;
	}
	public void setGradename(String gradename) {
		this.gradename = gradename;
	}
	public String getGradevalue() {
		return gradevalue;
	}
	public void setGradevalue(String gradevalue) {
		this.gradevalue = gradevalue;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public String getCtimeString(){
		if(ctime==null)
			return null;
		return UtilTool.DateConvertToString(ctime, DateType.type1);
	}
}