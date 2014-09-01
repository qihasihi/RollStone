package com.school.entity;

import java.util.Date;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class TermInfo implements java.io.Serializable {
	private String ref;
	private String termname;
	private Date semesterbegindate;
	private Date semesterenddate;
	private Date ctime;
	private Date mtime;
	private String year;
    private Object flag;        //当前学期

    public Object getFlag() {
        return flag;
    }

    public void setFlag(Object flag) {
        this.flag = flag;
    }

    public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getTermname() {
		return termname;
	}
	public void setTermname(String termname) {
		this.termname = termname;
	}
	public Date getSemesterbegindate() {
		return semesterbegindate;
	}
	public void setSemesterbegindate(Date semesterbegindate) {
		this.semesterbegindate = semesterbegindate;
	}
    public void setSemesterbegindatestring(String semesterbegindate){
        this.semesterbegindate=UtilTool.StringConvertToDate(semesterbegindate);
    }
	public Date getSemesterenddate() {
		return semesterenddate;
	}
	public void setSemesterenddate(Date semesterenddate) {
		this.semesterenddate = semesterenddate;
	}
    public void setSemesterenddatestring(String semesterenddate){
        this.semesterenddate=UtilTool.StringConvertToDate(semesterenddate);
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
	
	public String getCtimeString(){
		if(ctime==null)
			return "";
		return UtilTool.DateConvertToString(ctime, DateType.type1);
	}
	
	public String getBtimeString(){
		if(semesterbegindate==null)
			return "";
		return UtilTool.DateConvertToString(semesterbegindate, DateType.smollDATE);
	}
	
	public String getEtimeString(){
		if(semesterenddate==null)
			return "";
		return UtilTool.DateConvertToString(semesterenddate, DateType.smollDATE);
	}
	private String dYYear;
	public String getDYYear() {
		// TODO Auto-generated method stub
		return dYYear;
	}
	public void setDYYear(String dyyear){
		this.dYYear=dyyear;
	}

    private String xYYear;

    public String getxYYear() {
        return xYYear;
    }

    public void setxYYear(String xYYear) {
        this.xYYear = xYYear;
    }
}
