package com.school.entity.teachpaltform;

import java.util.Date;

import com.school.entity.UserInfo;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class TaskSuggestInfo  implements java.io.Serializable{

	
	private Integer ref;
    private Date ctime;
    private Integer isanonymous;
	private Long courseid;
    private String suggestcontent;
	private UserInfo userinfo;
	private TpTaskInfo taskinfo;
    private Integer loginuserid;

    public Integer getLoginuserid() {
        return loginuserid;
    }

    public void setLoginuserid(Integer loginuserid) {
        this.loginuserid = loginuserid;
    }

    public String getUserid(){
	   	return this.getUserinfo().getRef();  
	}
	public void setUserid(String userid){
		this.getUserinfo().setRef(userid);
	}
	public Long getTaskid(){
	  return this.getTaskinfo().getTaskid();
	}
	public void setTaskid(Long taskid){
		this.getTaskinfo().setTaskid(taskid);
	}
	public String getRealname(){
		return this.getUserinfo().getRealname();
	}
	public void setRealname(String realname){
		this.getUserinfo().setRealname(realname);
	}
    public  String getTaskname(){
        return this.getTaskinfo().getTaskname();
    }
    public void setTaskname(String taskname){
        this.getTaskinfo().setTaskname(taskname);
    }
 
	public UserInfo getUserinfo() {
		if(userinfo==null)
			userinfo=new UserInfo();
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public TpTaskInfo getTaskinfo() {
		if(taskinfo==null)
			taskinfo=new TpTaskInfo();
		return taskinfo;
	}
	public void setTaskinfo(TpTaskInfo taskinfo) {
		this.taskinfo = taskinfo;
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
	public Integer getIsanonymous() {
		return isanonymous;
	}
	public void setIsanonymous(Integer isanonymous) {
		this.isanonymous = isanonymous;
	}
	public Long getCourseid() {
		return courseid;
	}
	public void setCourseid(Long courseid) {
		this.courseid = courseid;
	}
	public String getSuggestcontent() {
		return suggestcontent;
	}
	public void setSuggestcontent(String suggestcontent) {
		this.suggestcontent = suggestcontent;
	}

	public String getCtimeString(){
		if(ctime==null)
			return null;
		return UtilTool.DateConvertToString(ctime, DateType.type1); 
	}
    
}