package com.school.entity;

import javax.persistence.Entity;
import java.util.Date;
@Entity
public class StudentInfo implements java.io.Serializable {
	private Integer stuid;
    private String stuname;
    private String ref;
    private String stuaddress;
    private String stusex;
    private Date ctime;
    private Date mtime; 
    private UserInfo userinfo;
    private String stuno;
    private String linkmanphone; 
    private String linkman;
    private ClassInfo classinfo;
    private String islearnguide;
    
    public String getIslearnguide() {
		return islearnguide;
	}
	public void setIslearnguide(String islearnguide) {
		this.islearnguide = islearnguide;
	}
	public String getUserref() {
		return this.getUserinfo().getRef(); 
	}
    public void setDcSchoolId(Integer dcSchoolId){
        this.getUserinfo().setDcschoolid(dcSchoolId);
    }
    public Integer getDcSchoolId(){
        return this.getUserinfo().getDcschoolid();
    }
	public void setUserref(String userref) {
		this.getUserinfo().setRef(userref);
	}
    
    public void setUserid(Integer userid){
    	this.getUserinfo().setUserid(userid);
    }
    public Integer getUserid(){
    	return this.getUserinfo().getUserid();
    }
    
    public void setUsername(String name){
    	this.getUserinfo().setUsername(name);
    }
    public String getUsername(){
    	return this.getUserinfo().getUsername();
    }
    
    
    public ClassInfo getClassinfo() {
    	if(classinfo==null)
    		classinfo = new ClassInfo();
		return classinfo;
	}
	public void setClassinfo(ClassInfo classinfo) {
		this.classinfo = classinfo;
	}
	public Integer getStuid() {
		return stuid;
	}
	public void setStuid(Integer stuid) {
		this.stuid = stuid;
	}
	public String getStuname() {
		return stuname;
	}
	public void setStuname(String stuname) {
		this.stuname = stuname;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getStuaddress() {
		return stuaddress;
	}
	public void setStuaddress(String stuaddress) {
		this.stuaddress = stuaddress;
	}
	public String getStusex() {
		return stusex;
	}
	public void setStusex(String stusex) {
		this.stusex = stusex;
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
	public UserInfo getUserinfo() {
		if(userinfo==null)
			userinfo = new UserInfo();
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public String getStuno() {
		return stuno;
	}
	public void setStuno(String stuno) {
		this.stuno = stuno;
	}
	public String getLinkmanphone() {
		return linkmanphone;
	}
	public void setLinkmanphone(String linkmanphone) {
		this.linkmanphone = linkmanphone;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
}