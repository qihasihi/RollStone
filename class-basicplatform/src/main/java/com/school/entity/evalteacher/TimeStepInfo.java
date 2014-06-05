package com.school.entity.evalteacher;

import java.io.Serializable;
import java.util.Date;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;


/**
 * 评价时间设置
 * 
 * @author zhengzhou
 * 
 */
@Entity
public class TimeStepInfo implements Serializable {
	
	private Integer ref;
	private Integer yearid; // 年份 例如：2011~2012

	private Date pjstarttime; // 开始评价的时间
	private Date pjendtime; // 结束评价的时间
	private String pjdesc; // 信息描述
	private String classyearname;
	private String status;
	
	public Integer getRef() {
		return ref;
	}

	public void setRef(Integer ref) {
		this.ref = ref;
	}

	public Integer getYearid() {
		return yearid;
	}

	public void setYearid(Integer yearid) {
		this.yearid = yearid;
	}

	public String getClassyearname() {
		return classyearname;
	}

	public void setClassyearname(String classyearname) {
		this.classyearname = classyearname;
	}

	public Date getPjstarttime() {
		return pjstarttime;
	}

	/**
	 * 评价开始时间 格式: YYYY-MM-DD hh24:mi:ss
	 * 
	 * @return
	 */
	public String getPjstarttimeString() {
		if (pjstarttime == null)
			return null;
		return UtilTool.DateConvertToString(pjstarttime, DateType.type1);
	}

	public void setPjstarttime(Date pjstarttime) { 
		this.pjstarttime = pjstarttime;
	}
	
	public void setPjstarttimeString(String pjstarttime) {
		if(this.pjstarttime==null||pjstarttime.trim().length()<1)
			this.pjstarttime=null;
		this.pjstarttime = UtilTool.StringConvertToDate(pjstarttime);
	}

	public Date getPjendtime() {
		return pjendtime;
	}

	/**
	 * 评价开始时间 格式: YYYY-MM-DD hh24:mi:ss
	 * 
	 * @return
	 */
	public String getPjendtimeString() {
		if (pjendtime == null)
			return null;
		return UtilTool.DateConvertToString(pjendtime, DateType.type1);
	}

	public void setPjendtime(Date pjendtime) {
		this.pjendtime = pjendtime;
	}

	public void setPjendtimeString(String pjendtime) {
		if(pjendtime==null||pjendtime.trim().length()<1)
			this.pjendtime=null;
		this.pjendtime = UtilTool.StringConvertToDate(pjendtime);
	}

	
	public String getPjdesc() {
		return pjdesc;
	}

	public void setPjdesc(String pjdesc) {
		this.pjdesc = pjdesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
