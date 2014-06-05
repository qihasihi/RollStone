package com.school.entity.peer;

import java.io.Serializable;
import java.util.Date;

import com.school.entity.UserInfo;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

/**
 * @author 岳春阳
 *@description 评价主题实体类
 */
@Entity
public class PjPeerBaseInfo implements Serializable {
	private String ref;//流水号，标识
	private String name;//评价标题、主题
	private Date btime;//评价开始时间
	private Date etime;//评价结束时间
	private String year;//评价年份
	private Date ctime;//创建时间
	private Date mtime;//修改时间
	private String cuserid;//创建人
	private UserInfo userinfo;	
	private String deptref;//部门标识
	private String remark;//主题描述
	private String deptname;//部门名称
	private Date ntime;
	
	public Date getNtime() {
		return ntime;
	}
	public String getNtimestring(){
		if(ntime==null)
			return null;
		return UtilTool.DateConvertToString(ntime, DateType.type1);
	}
	public void setNtime(Date ntime) {
		this.ntime = ntime;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public UserInfo getUserinfo() {
		if(userinfo==null)
			userinfo = new UserInfo();
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBtime() {
		return btime;
	}
	public String getBtimestring(){
		if(btime==null)
			return null;
		return UtilTool.DateConvertToString(btime, DateType.type1);
	}
	
	public void setBtimestring(String timestr){
		if(timestr!=null&&timestr.length()>0)
			this.setBtime(UtilTool.StringConvertToDate(timestr));
			
	}
	public void setBtime(Date btime) {
		this.btime = btime;
	}
	public Date getEtime() {
		return etime;
	}
	public String getEtimestring(){
		if(etime==null)
			return null;
		return UtilTool.DateConvertToString(etime, DateType.type1);
	}
	
	public void setEtimestring(String timestr){
		if(timestr!=null&&timestr.length()>0)
			this.setEtime(UtilTool.StringConvertToDate(timestr));
			
	}
	public void setEtime(Date etime) {
		this.etime = etime;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Date getCtime() {
		return ctime;
	}
	public String getCtimestring(){
		if(ctime==null)
			return null;
		return UtilTool.DateConvertToString(ctime, DateType.type1);
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public Date getMtime() {
		return mtime;
	}
	public String getMtimestring(){
		if(mtime==null)
			return null;
		return UtilTool.DateConvertToString(mtime, DateType.type1);
	}
	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}
	public String getCuserid() {
		return this.getUserinfo().getRef();
	}
	public void setCuserid(String cuserid) {
		this.getUserinfo().setRef(cuserid);
	}
	public String getDeptref() {
		return deptref;
	}
	public void setDeptref(String deptref) {
		this.deptref = deptref;
	}
	
}
