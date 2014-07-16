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
    private Integer loginuserid;
    private String taskname;
    private Long taskid;
    private String userid;
    private String realname;

    public Long getTaskid() {
        return taskid;
    }

    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public Integer getLoginuserid() {
        return loginuserid;
    }

    public void setLoginuserid(Integer loginuserid) {
        this.loginuserid = loginuserid;
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