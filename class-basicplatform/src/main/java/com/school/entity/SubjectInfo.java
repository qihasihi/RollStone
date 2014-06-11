package com.school.entity;

import java.util.Date;


import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;
/**
 * ѧ����Ϣ�?
 * @author zhushixiong
 *
 */
@Entity
public class SubjectInfo implements java.io.Serializable{

	public SubjectInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SubjectInfo(Integer subjectid, String subjectname, Date ctime, Date mtime,Integer subjecttype,Integer lzxsubjectid) {
		super();
		this.subjectid = subjectid;
		this.subjectname = subjectname;
		this.ctime = ctime;
		this.mtime = mtime;
		this.subjecttype=subjecttype;
        this.lzxsubjectid=lzxsubjectid;
	}
	
	private Integer subjectid;
	private String subjectname;
	private Integer subjecttype;
	private Date ctime;
	private Date mtime;
    private Integer lzxsubjectid;

    public Integer getLzxsubjectid() {
        return lzxsubjectid;
    }

    public void setLzxsubjectid(Integer lzxsubjectid) {
        this.lzxsubjectid = lzxsubjectid;
    }

    public Integer getSubjecttype() {
		return subjecttype;
	}

	public void setSubjecttype(Integer subjecttype) {
		this.subjecttype = subjecttype;
	}

	public Integer getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(Integer subjectid) {
		this.subjectid = subjectid;
	}

	public String getSubjectname() {
		return subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
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
	
	public String getMtimeString(){
		if(mtime==null)
			return null;
		return UtilTool.DateConvertToString(mtime, DateType.type1);
	}
	public String getCtimeString(){
		if(ctime==null)
			return null;
		return UtilTool.DateConvertToString(ctime, DateType.type1);
	}
	
}

