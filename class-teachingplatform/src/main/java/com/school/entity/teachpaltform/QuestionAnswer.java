package com.school.entity.teachpaltform;

import java.util.Date;

import com.school.entity.UserInfo;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class QuestionAnswer  implements java.io.Serializable{

    private Long quesid;
    private Integer ref;
    private Date ctime;
    private Long quesparentid;
	private String answercontent;
    private Integer rightanswer;
    private UserInfo userinfo;
    private Long taskid;
    private Long courseid;
    private Long groupid;

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }

    private Integer tasktype;

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

    public String getReplyuserid() {
        return replyuserid;
    }

    public void setReplyuserid(String replyuserid) {
        this.replyuserid = replyuserid;
    }

    public String getReplycontent() {
        return replycontent;
    }

    public void setReplycontent(String replycontent) {
        this.replycontent = replycontent;
    }

    /*资源心得*/
    private String replyuserid;
    private String replycontent;

    private UserInfo cuserinfo ;
    private UserInfo ruserinfo;
    private UserInfo touserinfo;
    public UserInfo getCuserinfo() {
        if(cuserinfo==null)
            cuserinfo=new UserInfo();
        return cuserinfo;
    }
    public void setCuserinfo(UserInfo cuserinfo) {
        this.cuserinfo = cuserinfo;
    }
    public UserInfo getRuserinfo() {
        if(ruserinfo==null)
            ruserinfo=new UserInfo();
        return ruserinfo;
    }
    public void setRuserinfo(UserInfo ruserinfo) {
        this.ruserinfo = ruserinfo;
    }
    public UserInfo getTouserinfo() {
        if(touserinfo==null)
            touserinfo=new UserInfo();
        return touserinfo;
    }
    public void setTouserinfo(UserInfo touserinfo) {
        this.touserinfo = touserinfo;
    }

    public String getTouserid() {
        return this.getTouserinfo().getRef();
    }
    public void setTouserid(String touserid) {
        this.getTouserinfo().setRef(touserid);
    }
    public String getTorealname() {
        return this.getTouserinfo().getRealname();
    }
    public void setTorealname(String tousername) {
        this.getTouserinfo().setRealname(tousername) ;
    }
    public void setCrealname(String realname){
        this.getCuserinfo().setRealname(realname);
    }
    public String getCrealname(){
        return this.getCuserinfo().getRealname();
    }
    public void setRrealname(String realname){
        this.getRuserinfo().setRealname(realname);
    }
    public String  getRrealname(){
        return this.getRuserinfo().getRealname();
    }

    public String getHeadimage(){
        return this.getCuserinfo().getHeadimage();
    }
    public void setHeadimage(String headimage){
        this.getCuserinfo().setHeadimage(headimage);
    }
    /*专题资源评论*/
    
    
	public Long getQuesparentid() {
		return quesparentid;
	}
	public void setQuesparentid(Long quesparentid) {
		this.quesparentid = quesparentid;
	}

    public String getUserid(){
    	return this.getUserinfo().getRef();
    }
    public void setUserid(String userid){
    	this.getUserinfo().setRef(userid);
    }

    public String getRealname(){
        return this.getUserinfo().getRealname();
    }
    public void setRealname(String realname){
        this.getUserinfo().setRealname(realname);
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
	public Long getQuesid() {
		return quesid;
	}
	public void setQuesid(Long quesid) {
		this.quesid = quesid;
	}
	
	public String getAnswercontent() {
		return answercontent;
	}
	public void setAnswercontent(String answercontent) {
		this.answercontent = answercontent;
	}
	public Integer getRightanswer() {
		return rightanswer;
	}
	public void setRightanswer(Integer rightanswer) {
		this.rightanswer = rightanswer;
	}
	public UserInfo getUserinfo() {
		if(userinfo==null)
			userinfo=new UserInfo();
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}


	
	public String getCtimeString(){
		if(ctime==null)
			return null;
		return UtilTool.DateConvertToString(ctime, DateType.type1); 
	}


}