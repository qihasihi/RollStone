package com.school.entity.activity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.school.entity.UserInfo;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;
/**
 * 
 * @author 岳春阳
 * @date 2013.3.28
 * @description 活动场地实体类
 *
 */
import javax.persistence.Entity;
@Entity
public class SiteInfo implements Serializable{
	private int ref;
	private String sitename;
	private String siteaddress;
	private int sitecontain;
	private int sitecontain2;//范围查询用
	public int getSitecontain2() {
		return sitecontain2;
	}
	public void setSitecontain2(int sitecontain2) {
		this.sitecontain2 = sitecontain2;
	}
	private UserInfo userinfo;
	private ActivityInfo activityinfo;
	public ActivityInfo getActivityinfo() {
		if (activityinfo==null)
			activityinfo = new ActivityInfo();
		return activityinfo;
	}
	public void setActivityinfo(ActivityInfo activityinfo) {
		this.activityinfo = activityinfo;
	}
	private Date ctime;
	private Date mtime;
	private String baseinfo;
	private int state;
	
	public Date getBegintime(){
		return activityinfo.getBegintime();
	}
	public String getBegintimeString(){
		return (this.getActivityinfo().getBegintime()==null?"":UtilTool.DateConvertToString(this.getActivityinfo().getBegintime(), DateType.type1));
	}
	
	public Date getEndtime(){
		return activityinfo.getEndtime();
	}
	public String getEdntimeString(){
		return (this.getActivityinfo().getEndtime()==null?"":UtilTool.DateConvertToString(this.getActivityinfo().getEndtime(), DateType.type1));
	}
	
	public int getRef() {
		return ref;
	}
	public void setRef(int ref) {
		this.ref = ref;
	}
	public String getSitename() {
		return sitename;
	}
	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
	public String getSiteaddress() {
		return siteaddress;
	}
	public void setSiteaddress(String siteaddress) {
		this.siteaddress = siteaddress;
	}
	public int getSitecontain() {
		return sitecontain;
	}
	public void setSitecontain(int sitecontain) {
		this.sitecontain = sitecontain;
	}
	public UserInfo getUserinfo() {
		if(userinfo==null)
			userinfo = new UserInfo();
		return userinfo;
	}
	public void setUserid(String ref){
		this.getUserinfo().setRef(ref);
	}
	public String getUserid(){
		return this.getUserinfo().getRef();
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	} 
	public Date getCtime() {
		return ctime;
	}
	public String getCtimeString(){
		return (ctime==null?"":UtilTool.DateConvertToString(this.ctime, DateType.type1));
	}
	public String getMtimeString(){
		return (mtime==null?"":UtilTool.DateConvertToString(this.mtime, DateType.type1));
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
	public String getBaseinfo() {
		return baseinfo;
	}
	public void setBaseinfo(String baseinfo) {
		this.baseinfo = baseinfo;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
}
