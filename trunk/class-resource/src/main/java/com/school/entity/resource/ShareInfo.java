package com.school.entity.resource;

import com.school.entity.UserInfo;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

/**
 * 分享表
 * 
 * @author zhengzhou
 * 
 */
@Entity
public class ShareInfo implements java.io.Serializable {

	public void ShareInfo() {
	}

	private java.util.Date ctime;
	private java.util.Date mtime;
	private java.lang.String ref;

	private Integer state; // 分享状态： 0：等分享 1：已经分享

	private UserInfo userinfo;
	private ResourceInfo resourceInfo;

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public UserInfo getUserinfo() {
		return (userinfo = (userinfo == null ? new UserInfo() : userinfo));
	}

	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}

	public ResourceInfo getResourceInfo() {
		return (resourceInfo = (resourceInfo == null ? new ResourceInfo()
				: resourceInfo));
	}

	public void setResourceInfo(ResourceInfo resourceInfo) {
		this.resourceInfo = resourceInfo;
	}

	public String getUserref() {
		return this.getUserinfo().getRef();
	}

	public void setUserid(String userref) {
		this.getUserinfo().setRef(userref);
	}

	public Long getResid() {
		return this.getResourceInfo().getResid();
	}

	public void setResid(java.lang.Long resid) {
		this.getResourceInfo().setResid(resid);
	}

	public java.util.Date getCtime() {
		return ctime;
	}

	public void setCtime(java.util.Date ctime) {
		this.ctime = ctime;
	}

	public java.util.Date getMtime() {
		return mtime;
	}

	public void setMtime(java.util.Date mtime) {
		this.mtime = mtime;
	}

	public java.lang.String getRef() {
		return ref;
	}

	public void setRef(java.lang.String ref) {
		this.ref = ref;
	}

	public String getCtimeString() {
		return (ctime == null ? "" : UtilTool.DateConvertToString(ctime,
				DateType.type1));
	}

	public String getMtimeString() {
		return (mtime == null ? "" : UtilTool.DateConvertToString(mtime,
				DateType.type1));
	}

}