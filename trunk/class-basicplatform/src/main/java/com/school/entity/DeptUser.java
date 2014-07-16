package com.school.entity;

import javax.persistence.Entity;
import java.util.Date;
@Entity
public class DeptUser implements java.io.Serializable{ 
    private String ref;
    private Date ctime;
    private Date mtime;
    private DeptInfo deptinfo; 
    private UserInfo userinfo;     
    private RoleInfo roleinfo;
    private String otheruserref;
    //部门类型用于区分用户是职工还是教师
    
    /**
     * 查询条件 ：roleid is not null;
     */
    public Integer roleflag;
     
	public Integer getRoleflag() {
		return roleflag;
	}
	public void setRoleflag(Integer roleflag) {
		this.roleflag = roleflag;
	}
	public Integer getTypeid() {
		return this.getDeptinfo().getTypeid();
	}
	public void setTypeid(Integer typeid) {
		this.getDeptinfo().setTypeid(typeid);
	}
	public String getOtheruserref() {
		return otheruserref;
	}
	public void setOtheruserref(String otheruserref) {
		this.otheruserref = otheruserref;
	}
	public void setDeptname(String deptname){
    	this.getDeptinfo().setDeptname(deptname);
    }
    public String getDeptname(){
    	return this.getDeptinfo().getDeptname();
    }
    public void setHeadimage(String headimage){
    	this.getUserinfo().setHeadimage(headimage);
    } 
    public String getHeadimage(){
    	return this.getUserinfo().getHeadimage();
    }
	public RoleInfo getRoleinfo() {
		if(roleinfo==null)
			roleinfo=new RoleInfo();
		return roleinfo;
	}
	public void setRoleinfo(RoleInfo roleinfo) {
		this.roleinfo = roleinfo;
	}
	public Integer getDeptid() {
		return this.getDeptinfo().getDeptid();
	}
	public void setDeptid(Integer deptid) {
		this.getDeptinfo().setDeptid(deptid);
	}
	public Integer getUserid() {
		return this.getUserinfo().getUserid();
	}
	public void setUserid(Integer userid) {
		this.getUserinfo().setUserid(userid);
	}
	public String getUserref() {
        return this.getUserinfo().getRef();
    }
    public void setUserref(String userid) {
        this.getUserinfo().setRef(userid);
    }
    public String getUsername() {
        return this.getUserinfo().getUsername();
    }
    public void setUsername(String username) {
        this.getUserinfo().setUsername(username);
    }
	public Integer getRoleid() {
		return this.getRoleinfo().getRoleid();
	}
	public void setRoleid(Integer roleid) {
		this.getRoleinfo().setRoleid(roleid);
	}
	public String getRolename() {
		return this.getRoleinfo().getRolename();
	}
	public void setRolename(String rolename) {
		this.getRoleinfo().setRolename(rolename);
	}
	public String getRealcardid() {
		return this.getUserinfo().getRealcardid();
	}
	public void setRealcardid(String realcardid) {
		this.getUserinfo().setRealcardid(realcardid);
	}
	public String getRealname() {
		return this.getUserinfo().getRealname();
	}
	public void setRealname(String realname) {
		this.getUserinfo().setRealname(realname);
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
	public Date getMtime() {
		return mtime;
	}
	public void setMtime(Date mtime) {
		this.mtime = mtime;
	} 
	public DeptInfo getDeptinfo() {
		if(deptinfo==null)
			deptinfo=new DeptInfo();
		return deptinfo; 
	}  
	public void setDeptinfo(DeptInfo deptinfo) {
		this.deptinfo = deptinfo; 
	}
	public UserInfo getUserinfo() {
		if(userinfo==null)  
			userinfo=new UserInfo();  
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
 
} 