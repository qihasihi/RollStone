package com.school.entity.evalteacher;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.school.entity.UserInfo;

import javax.persistence.Entity;

/**
 * 评价日志表(数据)
 * 
 * @author zhusx
 * 
 */
@Entity
public class AppraiseLogsInfo implements Serializable {
	private Integer ref;
	private UserInfo userinfo = new UserInfo();
	private Date ctime;
	private AppraiseItemInfo iteminfo = new AppraiseItemInfo();
	private Integer optionid;
	private UserInfo targetuserinfo = new UserInfo();
	private float score;
	private Integer yearid;
	private Integer classid;
	private Integer targetidentitytype;
	private String targetclassuserref;			//被评价的教师 在Class_User表中的REF标识

	public Integer getClassid() {
		return classid;
	}

	public void setClassid(Integer classid) {
		this.classid = classid;
	}

	public Integer getUserid() {
		return this.getUserinfo().getUserid();
	}
	
	public void setUserid(Integer userid) {
		this.getUserinfo().setUserid(userid);
	}

	public void setUsername(Integer userid) {
		this.getUserinfo().setUserid(userid);
	}

	public UserInfo getUserinfo() {
		if (this.userinfo == null)
			this.userinfo = new UserInfo();
		return userinfo;
	}
	public void setUsername(String uname){
		this.getUserinfo().setUsername(uname);
		
	}
	public void setUserinfo(UserInfo userinfo) {
		if (this.userinfo == null)
			this.userinfo = new UserInfo();
		this.userinfo = userinfo;
	}

	public Integer getItemid() {
		return this.getIteminfo().getRef();
	}
	public void setItemname(String itemname){
		 this.getIteminfo().setName(itemname);		
	}

	public void setItemid(Integer itemid) {
		this.iteminfo.setRef(itemid);
	}

	public Integer getOptionid() {
		return optionid;
	}

	public void setOptionid(Integer optionid) {
		this.optionid = optionid;
	}

	public String getTargetuserref() {
		return this.getTargetuserinfo().getRef();
	}
	
	public void setTargetusername(String targetuname){
		this.getTargetuserinfo().setUsername(targetuname);		
	}

	public void setTargetuserref(String tarsetUserid) {
		this.getTargetuserinfo().setRef(tarsetUserid);
	}

	public UserInfo getTargetuserinfo() {
		return targetuserinfo;
	}

	public void setTargetuserinfo(UserInfo targetuserinfo) {
		this.targetuserinfo = targetuserinfo;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public Integer getYearid() {
		return yearid;
	}

	public void setYearid(Integer yearid) {
		this.yearid = yearid;
	}

	public Integer getTargetidentitytype() {
		return targetidentitytype;
	}

	public void setTargetidentitytype(Integer targetidentitytype) {
		this.targetidentitytype = targetidentitytype;
	}

	public AppraiseItemInfo getIteminfo() {
		if(this.iteminfo==null)
			this.iteminfo=new AppraiseItemInfo();
		return iteminfo;
	}

	public void setIteminfo(AppraiseItemInfo iteminfo) {
		if(this.iteminfo==null)
			this.iteminfo=new AppraiseItemInfo();
		this.iteminfo = iteminfo;
	}

	public Integer getRef() {
		return ref;
	}

	public void setRef(Integer ref) {
		this.ref = ref;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public String getTargetclassuserref() {
		return targetclassuserref;
	}

	public void setTargetclassuserref(String targetclassuserref) {
		this.targetclassuserref = targetclassuserref;
	}

}
