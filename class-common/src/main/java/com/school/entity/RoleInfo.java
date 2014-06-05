package com.school.entity;

import java.util.Date;

import javax.persistence.Entity;


import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

/**
 * 角色实体
 * @author qihaishi
 *
 */
@Entity
public class RoleInfo implements java.io.Serializable {
	
	public final static int  STUDENT=1;  
	public final static int  TEACHER=2;
	public final static int  PARENT=3;
	
	private Integer roleid;
	private String rolename;
	private Date ctime;
	private Date mtime;
	private Integer flag;
	private Integer isadmin;
	private String remark;
	private String identityname;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getIdentityname() {
		return identityname;
	}
	public void setIdentityname(String identityname) {
		this.identityname = identityname;
	} 
	public Integer getIsadmin() {
		return isadmin;
	}
	public void setIsadmin(Integer isadmin) {
		this.isadmin = isadmin;
	}
	public Integer getFlag() {
		return flag;    
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Integer getRoleid() {
		return roleid;
	}
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	

	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	/**
	 * 获取yyyy-mm-dd HH24:mi:ss
	 * @return 
	 */
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
	/** 
	 * 获取yyyy-mm-dd HH24:mi:ss
	 * @return
	 */
	public String getMtimeString(){
		if(mtime==null)
			return null;
		return UtilTool.DateConvertToString(mtime, DateType.type1);
	}
}
