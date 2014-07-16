package com.school.entity;

import javax.persistence.Entity;
import java.util.Date;
@Entity
public class RightUser implements java.io.Serializable { 
	
	private PageRightInfo pagerightinfo;
    private Integer ref;
    private Date ctime; 
    private UserInfo userinfo; 
     
    public void setPagename(String pagename){
    	this.getPagerightinfo().setPagename(pagename);
    }
    public String getPagename(){
    	return this.getPagerightinfo().getPagename();
    }
    
    public void setPagevalue(String pagevalue){
    	this.getPagerightinfo().setPagevalue(pagevalue);
    }
    public String getPagevalue(){
    	return this.getPagerightinfo().getPagevalue();
    }
    
    public void setPagerightid(Integer id){
    	this.getPagerightinfo().setPagerightid(id);
    }
    public Integer getPagerightid(){
    	return this.getPagerightinfo().getPagerightid();
    }
    
    
    
    public PageRightInfo getPagerightinfo() {
    	if(pagerightinfo==null)
    		pagerightinfo=new PageRightInfo();
		return pagerightinfo;
	}
	public void setPagerightinfo(PageRightInfo pagerightinfo) {
		this.pagerightinfo = pagerightinfo;
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
	

  

}