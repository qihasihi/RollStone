package com.school.entity;

import javax.persistence.Entity;
import java.util.Date;
@Entity
public class SubjectUser implements java.io.Serializable {



    private Integer ref;
    private Date ctime;
    private UserInfo userinfo;
    private SubjectInfo subjectinfo;
    private Integer ismajor;	//Ö÷ÊÚÑ§¿Æ
     
    public Integer getSubjectid(){
    	return this.getSubjectinfo().getSubjectid();
    }
    public Integer getIsmajor() {
		return ismajor;
	}
	public void setIsmajor(Integer ismajor) {
		this.ismajor = ismajor;
	}
	public void setSubjectid(Integer subjectid){
    	this.getSubjectinfo().setSubjectid(subjectid);
    }
    public String getSubjectname(){
    	return this.getSubjectinfo().getSubjectname();
    }
    public void setSubjectname(String subejctname){
    	this.getSubjectinfo().setSubjectname(subejctname);
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
	public void setUserid(String uid){
		this.getUserinfo().setRef(uid);
	}
	public String getUserid(){
		return this.getUserinfo().getRef();
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public SubjectInfo getSubjectinfo() {
		if(subjectinfo==null)
        subjectinfo= new SubjectInfo();
		return subjectinfo;
	}
	public void setSubjectinfo(SubjectInfo subjectinfo) {
		this.subjectinfo = subjectinfo;
	}
	

  

}