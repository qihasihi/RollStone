package com.school.entity;

import javax.persistence.Entity;
import java.util.Date;
@Entity
public class ParentInfo implements java.io.Serializable{

   
    private String ref;
	private String parentname;
    private String parentphone;
    private UserInfo userinfo;
    private Integer parentid;
    private String parentsex;
    private StudentInfo studentinfo;
    private String cusername;
    private Date ctime;
    private Date mtime;
    
    /**
     * ”√ªß±Ì user_id
     */
    private Integer puserid;
     
	public Integer getPuserid() { 
		return puserid;
	} 
	public void setPuserid(Integer puserid) {
		this.puserid = puserid;
	} 
	public void setUserid(String userref){
    	this.getUserinfo().setRef(userref); 
    }
    public String getUserid() {
		return this.getUserinfo().getRef();
	}
    
    public void setUsername(String username){
    	this.getUserinfo().setUsername(username);
    } 
    public String getUsername() {
		return this.getUserinfo().getUsername();
	}
    
    
    public String getRef() {
		return ref;
	} 
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getParentname() {
		return parentname;
	}
	public void setParentname(String parentname) {
		this.parentname = parentname;
	}
	public String getParentphone() {
		return parentphone;
	}
	public void setParentphone(String parentphone) {
		this.parentphone = parentphone;
	}
	public UserInfo getUserinfo() {
		if(userinfo==null)
			userinfo=new UserInfo();
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public Integer getParentid() {
		return parentid;
	}
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	public String getParentsex() {
		return parentsex;
	}
	public void setParentsex(String parentsex) {
		this.parentsex = parentsex;
	}
	public StudentInfo getStudentinfo() {
		if(studentinfo==null)
			studentinfo=new StudentInfo();
		return studentinfo;
	}
	public void setStudentinfo(StudentInfo studentinfo) {
		this.studentinfo = studentinfo;
	}
	public String getCusername() {
		return cusername;
	}
	public void setCusername(String cusername) {
		this.cusername = cusername;
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
  

}