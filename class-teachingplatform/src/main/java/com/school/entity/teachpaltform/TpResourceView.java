package com.school.entity.teachpaltform;

import java.util.Date;

import com.school.entity.UserInfo;
import com.school.entity.resource.ResourceFileInfo;
import com.school.entity.resource.ResourceInfo;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class TpResourceView  implements java.io.Serializable{

   
    private Integer ref;
    private Date ctime;
    private UserInfo userinfo;
    private Long courseid;
    private ResourceInfo resourceinfo;
    
    public String getUserid(){
    	return this.getUserinfo().getRef();
    }
    public void setUserid(String userid){
    	this.getUserinfo().setRef(userid);
    }
  
    public Long getResid(){
    	return this.getResourceinfo().getResid();
    }
    public void setResid(Long  resid){
    	this.getResourceinfo().setResid(resid);
    }
    public String getFilename(){
    	return this.getResourceinfo().getFilename();
    }
    public void setFilename(String filename){
    	this.getResourceinfo().setFilename(filename);
    }
    public Long getFilesize(){
    	return this.getResourceinfo().getFilesize();
    }
    public void setFilesize(Long filesize){
    	this.getResourceinfo().setFilesize(filesize);
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
	public UserInfo getUserinfo() {
		if(userinfo==null)
			userinfo=new UserInfo();
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public Long getCourseid() {
		return courseid;
	}
	public void setCourseid(Long courseid) {
		this.courseid = courseid;
	}
	public ResourceInfo getResourceinfo() {
		if(resourceinfo==null)
            resourceinfo=new ResourceInfo();
		return resourceinfo;
	}
	public void setResourceinfo(ResourceInfo resourceinfo) {
		this.resourceinfo = resourceinfo;
	}
	public String getCtimeString(){
		if(ctime==null)
			return null;
		return UtilTool.DateConvertToString(ctime, DateType.type1); 
	}
}