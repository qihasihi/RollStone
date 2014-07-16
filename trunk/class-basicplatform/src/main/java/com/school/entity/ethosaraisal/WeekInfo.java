package com.school.entity.ethosaraisal;

import java.io.Serializable;
import java.util.Date;

import com.school.entity.TermInfo;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

/**
 * @author 岳春阳
 * @date 2013-04-26
 * @description 校风周次数据实体类 
 */
@Entity
public class WeekInfo implements Serializable{
	private Integer ref;//主键
	private String weekname;//周次名称
	private Date begintime;//开始时间
	private Date endtime;//结束时间
	private Date ctime;//创建时间
	private Date mtime;//修改时间
	private String remark;//备注
	private TermInfo terminfo;//学期对象
	
	public Integer getRef() {
		return ref;
	}
	public void setRef(Integer ref) {
		this.ref = ref;
	}
	public String getWeekname() {
		return weekname;
	}
	public void setWeekname(String weekname) {
		this.weekname = weekname;
	}
	public Date getBegintime() {
		return begintime;
	}
	public String getBegintimestring(){
		if(this.begintime==null)
			return "";
		return UtilTool.DateConvertToString(begintime,DateType.type1); 
	}
	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public String getEndtimestring(){
		if(this.endtime==null)
			return "";
		return UtilTool.DateConvertToString(endtime,DateType.type1); 
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public Date getCtime() {
		return ctime;
	}
	public String getCtimestring(){
		if(this.ctime==null)
			return "";
		return UtilTool.DateConvertToString(ctime,DateType.type1); 
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public Date getMtime() {
		return mtime;
	}
	public String getMtimestring(){
		if(this.mtime==null)
			return "";
		return UtilTool.DateConvertToString(mtime,DateType.type1); 
	}
	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public TermInfo getTerminfo() {
		if(terminfo==null)
			terminfo = new TermInfo();
		return terminfo;
	}
	public void setTerminfo(TermInfo terminfo) {
		this.terminfo = terminfo;
	}
	public String gettermid(){
		return this.getTerminfo().getRef();
	}
	public void settermid(String ref){
		this.terminfo.setRef(ref);
	}
}
