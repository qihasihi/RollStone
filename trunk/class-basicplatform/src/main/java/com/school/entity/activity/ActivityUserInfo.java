package com.school.entity.activity;

import java.io.Serializable;
import java.util.Date;

import com.school.entity.UserInfo;

/**
 * 
 * @author 岳春阳
 * @date 2013.3.27
 * @description 活动参与人员关系信息实体类
 *
 */
import javax.persistence.Entity;
@Entity
public class ActivityUserInfo implements Serializable{
	private int ref;
	private UserInfo userinfo;
	private ActivityInfo activityinfo;
	private Date ctime;
	private Date mtime;
	private Integer attitude;
	private String userids;
	private String userid;
	private String realname;
	
	public String getUserid(){
		return userid;
	}
	
	public void setUserid(String userid){
		this.userid=userid;
	}
	
	public String getRealname(){
		return this.getUserinfo().getRealname();
	}
	
	public void setRealname(String realname){
		this.getUserinfo().setRealname(realname);
	}
	
	public String getUserids() {
		return userids;
	}
	public void setUserids(String userids) {
		this.userids = userids;
	}
	public int getRef() {
		return ref;
	}
	public void setRef(int ref) {
		this.ref = ref;
	}
	public UserInfo getUserinfo() {
		return (userinfo=(userinfo==null?new UserInfo():userinfo));
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public ActivityInfo getActivityinfo() {
		if(activityinfo==null)
			activityinfo = new ActivityInfo();
		return activityinfo;
	}
	public void setActivityinfo(ActivityInfo activityinfo) {
		this.activityinfo = activityinfo;
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
	public Integer getAttitude() {
		return attitude;
	}
	public void setAttitude(Integer attitude) {
		this.attitude = attitude;
	}
	
	public String getCuserid(){
		return this.getUserinfo().getRef();
	}
	public void setCuserid(String ref){
		this.getUserinfo().setRef(ref);
	}
	public String getActivityref(){
		return this.getActivityinfo().getRef();
	}
	public void setActivityref(String ref){
		this.getActivityinfo().setRef(ref);
	}
}
