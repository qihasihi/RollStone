package com.school.entity.teachpaltform;

import java.util.Date;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class VideoTimeInfo implements java.io.Serializable {
	private Integer ref;
	private Integer flag;
	private Date begintime;
	private Date endtime;
	private Date ctime;
	
	public String getCtimeString(){
		if(ctime==null)
			return null;
		return UtilTool.DateConvertToString(ctime, DateType.type1); 
	}
	
	public String getBegintimeString(){
		if(begintime==null)
			return null;  
		return UtilTool.DateConvertToString(begintime, DateType.type3); 
	}
	    
	public String getEndtimeString(){  
		if(endtime==null)
			return null;  
		return UtilTool.DateConvertToString(endtime, DateType.type3); 
	}
	
	public Integer getRef() {
		return ref;
	}
	public void setRef(Integer ref) {
		this.ref = ref;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Date getBegintime() {
		return begintime;
	}
	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
}
