package com.school.entity;

import java.util.Date;

import javax.persistence.Entity;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

/**
 * ”√ªß±Ì°£
 * @author zhushixiong
 *
 */
@Entity
public class JobInfo implements java.io.Serializable{

	public JobInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private Integer jobid;
	private String jobname;
	private Date ctime;
	private Date mtime;
	

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


	public JobInfo(Integer jobid, String jobname) {
		super();
		this.jobid = jobid;
		this.jobname = jobname;
	}


	public Integer getJobid() {
		return jobid;
	}


	public void setJobid(Integer jobid) {
		this.jobid = jobid;
	}


	public String getJobname() {
		return jobname;
	}


	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public String getMtimeString(){
		if(mtime==null)
			return null;
		return UtilTool.DateConvertToString(mtime, DateType.type1);
	}
	public String getCtimeString(){
		if(ctime==null)
			return null;
		return UtilTool.DateConvertToString(ctime, DateType.type1);
	}
	
}
