package com.school.entity.activity;

import java.io.Serializable;
import java.util.Date;

import com.school.entity.UserInfo;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;
import javax.persistence.Entity;
/**
 * 
 * @author 岳春阳
 * @date 2013.3.27
 * @description 活动场地关系信息实体类
 *
 */

@Entity
public class ActivitySiteInfo implements Serializable{
	private int ref;
	private SiteInfo siteinfo;
	private int siteid;
	private ActivityInfo activityinfo;
	private UserInfo userinfo;	
	private Date ctime;
	private Date mtime;
	private String siteIds;
	public String getSiteIds() {
		return siteIds;
	}
	public void setSiteIds(String siteIds) {
		this.siteIds = siteIds;
	}
	public int getRef() {
		return ref;
	}
	public void setRef(int ref) {
		this.ref = ref;
	}
	public SiteInfo getSiteinfo() {
		if(siteinfo==null)
			siteinfo = new SiteInfo();
		return siteinfo;
	}
	public void setSiteinfo(SiteInfo siteinfo) {
		this.siteinfo = siteinfo;
	}
	public ActivityInfo getActivityinfo() {
		if(activityinfo==null)
			activityinfo = new ActivityInfo();
		return activityinfo;
	}
	public String getActivityid(){
		return this.getActivityinfo().getRef();
	}
	public void setActivityid(String id){
		this.getActivityinfo().setRef(id);
	}
	
	public int getSiteid() {
		return this.getSiteinfo().getRef();
	}
	public void setSiteid(int siteid) {
		this.getSiteinfo().setRef(siteid);
	}
	public void setActivityinfo(ActivityInfo activityinfo) {
		this.activityinfo = activityinfo;
	}
	public UserInfo getUserinfo() {
		if(userinfo==null)
			userinfo = new UserInfo();
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public void setUserid(String ref){
		this.getUserinfo().setRef(ref);
	}
	public String getUserid(){
		return this.getUserinfo().getRef();
	}
	public Date getCtime() {
		return ctime;
	}
	public String getCtimeString(){
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
	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}
	public void setActivityref(String ref){
		this.activityinfo.setRef(ref);
	}
}
