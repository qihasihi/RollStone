package com.school.entity;

import javax.persistence.Entity;
import java.util.Date;
@Entity
public class JobUser implements java.io.Serializable{
	private Integer ref;
    private Date ctime;
    private JobInfo jobinfo;
    private UserInfo userinfo;
    
    public void setUserid(String userid){
    	this.getUserinfo().setRef(userid);
    }
    public String getUserid(){
    	return this.getUserinfo().getRef();
    }
    public void setJobid(Integer jobid){
    	this.getJobinfo().setJobid(jobid);
    }
    public Integer getJobid(){
    	return this.getJobinfo().getJobid();
    }
    
    public void setJobname(String jobname){
    	this.getJobinfo().setJobname(jobname);
    }
    public String getJobname(){
    	return this.getJobinfo().getJobname();
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
	public JobInfo getJobinfo() {
		if(jobinfo==null)
			jobinfo=new JobInfo();
		return jobinfo;
	}
	public void setJobinfo(JobInfo jobinfo) {
		this.jobinfo = jobinfo;
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