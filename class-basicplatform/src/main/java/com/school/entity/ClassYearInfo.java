package com.school.entity;

import java.util.Date;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class ClassYearInfo implements java.io.Serializable{
	  private String classyearname;
	  private Integer classyearid;
	  private String classyearvalue;
	  private Date btime;
	  private Date ctime;
	  private Date etime;

    private String dyEqClassyearvalue;

    public String getDyEqClassyearvalue() {
        return dyEqClassyearvalue;
    }

    public void setDyEqClassyearvalue(String dyEqClassyearvalue) {
        this.dyEqClassyearvalue = dyEqClassyearvalue;
    }

    public String getClassyearname() {
		return classyearname;
	}
	public void setClassyearname(String classyearname) {
		this.classyearname = classyearname;
	}
	public Integer getClassyearid() {
		return classyearid;
	}
	public void setClassyearid(Integer classyearid) {
		this.classyearid = classyearid;
	}
	public String getClassyearvalue() {
		return classyearvalue;
	}
	public void setClassyearvalue(String classyearvalue) {
		this.classyearvalue = classyearvalue;
	}
	public Date getBtime() {
		return btime;
	}
	
	/**
	 * ×ª³É yyyy-mm-DD HH:MM:SS
	 * @param s
	 */
	
	public String getBtimeString(){
		if(btime==null)
			return null;
		return UtilTool.DateConvertToString(btime, DateType.type1);
	}
	public void setBtime(Date btime) {
		this.btime = btime;
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
	
	public Date getEtime() {
		return etime;
	}
	public void setEtime(Date etime) {
		this.etime = etime;
	}
	
	public String getEtimeString(){
		if(etime==null) 
			return null;
		return UtilTool.DateConvertToString(etime, DateType.type1);
	}
  

   
}