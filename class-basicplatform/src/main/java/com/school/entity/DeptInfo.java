package com.school.entity;

import javax.persistence.Entity;
import java.util.Date;
@Entity
public class DeptInfo implements java.io.Serializable {
	private Integer deptid;
	private String deptname;
    private Integer typeid;
    private UserInfo userinfo;
    private DeptInfo parentdept;
    private String grade;
    private Date ctime;
    private String studyperiods;
	private SubjectInfo subjectinfo;
    
	
	public Integer getSubjectid(){
		return this.getSubjectinfo().getSubjectid();
	}
	public void setSubjectid(Integer subjectid){
		this.getSubjectinfo().setSubjectid(subjectid); 
	}  
	
	public String getSubjectname(){ 
		return this.getSubjectinfo().getSubjectname(); 
	} 
	
	public void setSubjectname(String subname){
		this.getSubjectinfo().setSubjectname(subname);
	}
	 
	public SubjectInfo getSubjectinfo() {
		if(subjectinfo==null)
			subjectinfo=new SubjectInfo();
		return subjectinfo;
	}

	public void setSubjectinfo(SubjectInfo subjectinfo) {
		this.subjectinfo = subjectinfo;
	} 
	
    public Integer getParentdeptid(){
    	if(this.parentdept==null)
    		return null;
    	return this.parentdept.getDeptid();
    }
    
    public void setParentdeptid(Integer parentdeptid){
    	if(this.parentdept==null)
    		this.parentdept=new DeptInfo();
    	this.parentdept.setDeptid(parentdeptid);
    }
    
	public Integer getDeptid() {
		return deptid;
	}
	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public Integer getTypeid() {
		return typeid;
	}
	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}
	public UserInfo getUserinfo() {
		if(userinfo==null)
			userinfo = new UserInfo();
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public DeptInfo getParentdept() {
		return parentdept;
	}
	public void setParentdept(DeptInfo parentdept) {
		this.parentdept = parentdept;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public String getStudyperiods() {
		return studyperiods;
	}
	public void setStudyperiods(String studyperiods) { 
		this.studyperiods = studyperiods;
	}
 
	

   
  

}