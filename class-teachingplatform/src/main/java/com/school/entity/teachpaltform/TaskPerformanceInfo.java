package com.school.entity.teachpaltform;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.school.entity.UserInfo;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class TaskPerformanceInfo  implements java.io.Serializable{

   
    private Object isright;
	private Integer ref;
    private Date ctime;
    private Object status;
	private UserInfo userinfo;
    private TpGroupInfo groupinfo;
    private String answercontent;
    private String classname;
    private Integer criteria;
    private Long taskid;
    private Long courseid;
    private Integer tasktype;
    private Object score;

    public Object getScore() {
        return score;
    }

    public void setScore(Object score) {
        this.score = score;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    //存放互动交流主题
    private List performanceList=new ArrayList();
    
    public List getPerformanceList() {
		return performanceList;
	}
	public void setPerformanceList(List performanceList) {
		this.performanceList = performanceList;
	}
    
    public String getAnswercontent() {
		return answercontent;
	}
	public void setAnswercontent(String answercontent) {
		this.answercontent = answercontent;
	}
	public Long getGroupid(){
    	return this.getGroupinfo().getGroupid();
    }
    public void setGroupid(Long groupid){
    	this.getGroupinfo().setGroupid(groupid);
    } 
    public String getUserid(){
    	return this.getUserinfo().getRef();
    }
    public void setUserid(String userid){
    	this.getUserinfo().setRef(userid);
    }
    
    public void setStuno(String stuno){
    	this.getUserinfo().setStuNo(stuno);
    }
    public void setStuname(String name){
    	this.getUserinfo().setStuname(name);
    }


	public Object getIsright() {
		return isright;
	}
	public void setIsright(Object isright) {
		this.isright = isright;
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
	public Object getStatus() {
		return status;
	}  
	public void setStatus(Object status) {
		this.status = status;
	}

	public UserInfo getUserinfo() {
		if(userinfo==null)
			userinfo=new UserInfo();
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}

    public Integer getCriteria() {
        return criteria;
    }

    public void setCriteria(Integer criteria) {
        this.criteria = criteria;
    }

    public Long getTaskid() {
        return taskid;
    }

    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }

    public Integer getTasktype() {
        return tasktype;
    }

    public void setTasktype(Integer tasktype) {
        this.tasktype = tasktype;
    }

    public TpGroupInfo getGroupinfo() {
		if(groupinfo==null)
			groupinfo=new TpGroupInfo();
		return groupinfo;
	}
	public void setGroupinfo(TpGroupInfo groupinfo) {
		this.groupinfo = groupinfo;
	}

	public String getCtimeString(){
		if(ctime==null)
			return null;
		return UtilTool.DateConvertToString(ctime, DateType.type1); 
	}
}