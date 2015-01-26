package com.school.entity;

import javax.persistence.Entity;
import java.util.Date;
@Entity
public class TeacherInfo implements java.io.Serializable{ 
 
	private String imgheardsrc;
    private Date teacherbirth;
    private String teachercardid;
    private Date ctime;
    private String password;
    private String teachername;
    private String teacheraddress;
    private String teacherphone;
    private String teachersex;
    private Date entrytime;
    private String teacherlevel;
    private Date mtime;
    private Integer teacherid;
    private UserInfo userinfo;
    private String teacherpost;  
    
    private String subjects;
    private String teacherno;

    public String getTeacherno() {
        return teacherno;
    }

    public void setTeacherno(String teacherno) {
        this.teacherno = teacherno;
    }

    public String getSubjects() {
		return subjects;
	}
	public void setSubjects(String subjects) {
		this.subjects = subjects;
	}
	public void setUserid(String userid){
    	this.getUserinfo().setRef(userid);
    } 
    public String getUserid(){ 
    	return this.getUserinfo().getRef();
    }
    public String getUsername(){
    	return this.getUserinfo().getUsername();
    }
    public void setUsername(String username){
    	this.getUserinfo().setUsername(username);
    }
    /**
     * 对应用户表的USER_ID
     */
    private Integer tuserid;
    
    public Integer getTuserid() {
		return tuserid;
	} 
	public void setTuserid(Integer tuserid) {
		this.tuserid = tuserid;
	}
	 
    public String getImgheardsrc() {
		return imgheardsrc;
	}
	public void setImgheardsrc(String imgheardsrc) {
		this.imgheardsrc = imgheardsrc;
	}
	public Date getTeacherbirth() {
		return teacherbirth;
	}
	public void setTeacherbirth(Date teacherbirth) {
		this.teacherbirth = teacherbirth;
	}
	public String getTeachercardid() {
		return teachercardid;
	}
	public void setTeachercardid(String teachercardid) {
		this.teachercardid = teachercardid;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTeachername() {
		return teachername;
	}
	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}
	public String getTeacheraddress() {
		return teacheraddress;
	}
	public void setTeacheraddress(String teacheraddress) {
		this.teacheraddress = teacheraddress;
	}
	public String getTeacherphone() {
		return teacherphone;
	}
	public void setTeacherphone(String teacherphone) {
		this.teacherphone = teacherphone;
	}
	public String getTeachersex() {
		return teachersex;
	}
	public void setTeachersex(String teachersex) {
		this.teachersex = teachersex;
	}
	public Date getEntrytime() {
		return entrytime;
	}
	public void setEntrytime(Date entrytime) {
		this.entrytime = entrytime;
	}
	public String getTeacherlevel() {
		return teacherlevel;
	}
	public void setTeacherlevel(String teacherlevel) {
		this.teacherlevel = teacherlevel;
	}
	public Date getMtime() {
		return mtime;
	}
	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}
	public Integer getTeacherid() {
		return teacherid;
	}
	public void setTeacherid(Integer teacherid) {
		this.teacherid = teacherid;
	}
	public UserInfo getUserinfo() {
		if(userinfo==null)
			userinfo=new UserInfo(); 
		return userinfo; 
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public String getTeacherpost() {
		return teacherpost;
	}
	public void setTeacherpost(String teacherpost) {
		this.teacherpost = teacherpost;
	}
	
    

}