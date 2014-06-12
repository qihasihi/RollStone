package com.school.entity;
import java.util.Date;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class ClassInfo implements java.io.Serializable {
	private String type;
    private Date ctime;
    private Date mtime;
    private String classname;
    private Integer classid;
    private String pattern;
    private String year;
    private String classgrade;
    private Object num;  //�༶����
    private Integer islike; //�Ƿ�ģ����ѯ
    private SubjectInfo subjectinfo;    
    private Integer isflag; //�Ƿ����ã�  1������   2������
    private Integer lzxclassid;
        
    private String classyearname;

    public Integer getLzxclassid() {
        return lzxclassid;
    }

    public void setLzxclassid(Integer lzxclassid) {
        this.lzxclassid = lzxclassid;
    }

    public Integer getIsflag() {
		return isflag;
	}
	public void setIsflag(Integer isflag) {
		this.isflag = isflag;
	}
	public String getClassyearname() {
		return classyearname;  
	}
	public void setClassyearname(String classyearname) {
		this.classyearname = classyearname;
	}
	public SubjectInfo getSubjectinfo() {
		return (subjectinfo=(subjectinfo==null?new SubjectInfo():subjectinfo));
	}
	public void setSubjectinfo(SubjectInfo subjectinfo) {
		this.subjectinfo = subjectinfo;
	}
	public Integer getSubjectid() {
		return getSubjectinfo().getSubjectid();
	}
	public void setSubjectid(Integer subjectid) {
		getSubjectinfo().setSubjectid(subjectid);
	}
	public String getSubjectname() {
		return getSubjectinfo().getSubjectname();
	}
	public void setSubjectname(String subjectname) {
		getSubjectinfo().setSubjectname(subjectname); 
	}
	public Integer getIslike() {
		return islike;
	}
	public void setIslike(Integer islike) {
		this.islike = islike;
	}
	private String userid;
 
   
    public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public String getCtimeString(){
		if(ctime==null)
			return null;
		return UtilTool.DateConvertToString(ctime, DateType.type1);
	}
	public Date getMtime() {
		return mtime;
	}
	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public Integer getClassid() {
		return classid;
	}
	public void setClassid(Integer classid) {
		this.classid = classid;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getClassgrade() {
		return classgrade;
	}
	public void setClassgrade(String classgrade) {
		this.classgrade = classgrade;
	}
	  
	public Object getNum() {
		return num;
	}
	public void setNum(Object num) {
		this.num = num;
	}
}