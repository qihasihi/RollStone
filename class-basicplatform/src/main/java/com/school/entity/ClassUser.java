package com.school.entity;

import java.util.Date;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class ClassUser  implements java.io.Serializable{

   
    private String ref;
    private Date ctime;
    private ClassInfo classinfo;
    private UserInfo userinfo;
    private String relationtype;
    private Integer sportsex;
    private SubjectInfo subjectinfo;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String norelationtype;
    private Integer uid;
    private Object completenum; //任务完成率
    private Object stucount;

    public Integer getDctype(){
        return this.getClassinfo().getDctype();
    }
    public void setDctype(Integer dctype){
        this.getClassinfo().setDctype(dctype);
    }

    public Object getStucount() {
        return stucount;
    }

    public void setStucount(Object stucount) {
        this.stucount = stucount;
    }

    //历史学年
    private String historyyear;
    //当前及以后学年
    private String afteryear;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public String getSex() {
		return this.getUserinfo().getSex();
	}
	public void setSex(String sex) {
		this.getUserinfo().setSex(sex);
	}

    public void setHeadimage(String img){
        this.getUserinfo().setHeadimage(img);
    }
    public String getHeadimage(){
        return this.getUserinfo().getHeadimage();
    }

    public Integer getEttuserid(){
        return this.getUserinfo().getEttuserid();
    }

    public void setEttuserid(Integer ettuserid){
        this.getUserinfo().setEttuserid(ettuserid);
    }



    public String getAfteryear() {
		return afteryear;
	}
	public void setAfteryear(String afteryear) {
		this.afteryear = afteryear;
	}
	public String getHistoryyear() {
		return historyyear;
	}
	public void setHistoryyear(String historyyear) {
		this.historyyear = historyyear;
	}
	public String getNorelationtype() {
		return norelationtype;
	}
	public void setNorelationtype(String norelationtype) {
		this.norelationtype = norelationtype;
	}
	public SubjectInfo getSubjectinfo() {
    	if(subjectinfo==null)
    		subjectinfo=new SubjectInfo();
		return subjectinfo;
	}
	public void setSubjectinfo(SubjectInfo subjectinfo) {
		this.subjectinfo = subjectinfo;
	}
	public Integer getSubjectid() {
		return this.getSubjectinfo().getSubjectid();
	}
	public void setSubjectid(Integer subjectid) {
		this.getSubjectinfo().setSubjectid(subjectid);
	}
	public String getSubjectname() {
		return this.getSubjectinfo().getSubjectname();
	}
	public void setSubjectname(String subjectname) {
		this.getSubjectinfo().setSubjectname(subjectname);
	}
    public Object getCompletenum() {
        return completenum;
    }

    public void setCompletenum(Object completenum) {
        this.completenum = completenum;
    }
	
    public String getStuno(){
    	return this.getUserinfo().getStuno();
    }
    public void setStuno(String stuno){
    	this.getUserinfo().setStuno(stuno);
    }
    public String getYear(){
    	return this.getClassinfo().getYear();
    }
    public void setYear(String year){
    	this.getClassinfo().setYear(year);
    } 
     
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public ClassInfo getClassinfo() {
		if(classinfo==null)
			classinfo=new ClassInfo();
		return classinfo;
	}
	public void setClassinfo(ClassInfo classinfo) {
		this.classinfo = classinfo;
	}
	public UserInfo getUserinfo() {
		if(userinfo==null)
			userinfo=new UserInfo();
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public String getRelationtype() {
		return relationtype;
	}
	public void setRelationtype(String relationtype) {
		this.relationtype = relationtype;
	}
	public Integer getSportsex() {
		return sportsex;
	}
	public void setSportsex(Integer sportsex) {
		this.sportsex = sportsex;
	}
	
	public Integer getClassid(){
		return this.getClassinfo().getClassid();
	}

	public void setClassid(Integer classid){
		this.getClassinfo().setClassid(classid);
	}

	public String getClassgrade(){
		return this.getClassinfo().getClassgrade();
	}

	public void setClassgrade(String classgrade){
		this.getClassinfo().setClassgrade(classgrade);
	}
	
	public String getClassname(){
		return this.getClassinfo().getClassname();
	}

	public void setClassname(String classname){
		this.getClassinfo().setClassname(classname);
	}
	
	public String getPattern(){
		return this.getClassinfo().getPattern();
	}

	public void setPattern(String pattern){
		this.getClassinfo().setPattern(pattern);
	}
	public void setRealname(String realname){
		this.getUserinfo().setRealname(realname);
	}
	public String getRealname(){
		return this.getUserinfo().getRealname();
	}
	public String getUserid(){
		return this.getUserinfo().getRef();
	}
	public void setUserid(String userref){
		this.getUserinfo().setRef(userref);
	}
	
	public String getCtimeString(){
		if(ctime==null)
			return "";
		return UtilTool.DateConvertToString(ctime, DateType.type1);
	}
}