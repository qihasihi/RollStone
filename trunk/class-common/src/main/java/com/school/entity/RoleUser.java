package com.school.entity;

import javax.persistence.Entity;
import java.util.Date;
@Entity
public class RoleUser implements java.io.Serializable {

   
    private String ref;
    private Date ctime;
    private GradeInfo gradeinfo;
	private UserInfo userinfo;
    private RoleInfo roleinfo;
 
    private String identityname;
    
	public String getIdentityname() {
		return identityname;
	}
	public void setIdentityname(String identityname) {
		this.identityname = identityname;
	}
	/**
     * 对应用户表的USER_ID
     */
    private Integer ruserid; 
    
    
    public GradeInfo getGradeinfo() {
    	if(gradeinfo==null)
    		gradeinfo=new GradeInfo();
		return gradeinfo;
	}
	public void setGradeinfo(GradeInfo gradeinfo) {
		this.gradeinfo = gradeinfo;
	}
	
	public String getUserid(){
		return this.getUserinfo().getRef();
	}
	public void setUserid(String ref){
		this.getUserinfo().setRef(ref);
	}
	
	public String getGradename(){
		return this.getGradeinfo().getGradename();
	}
	public void setGradename(String name){
		this.getGradeinfo().setGradename(name);
	}
	public Integer getGradeid(){
		return this.getGradeinfo().getGradeid();
	}
	public void setGradeid(Integer id){
		this.getGradeinfo().setGradeid(id);
	}

    public Integer getRoleid() {
		return this.getRoleinfo().getRoleid();
	}
	public void setRoleid(Integer roleid) {
		this.getRoleinfo().setRoleid(roleid); 
	}
    
	public Integer getIsadmin() {
		return this.getRoleinfo().getIsadmin();
	}
	public void setIsadmin(Integer isadmin) {
		this.getRoleinfo().setIsadmin(isadmin); 
	}
	    
    
    public Integer getRuserid() {
		return ruserid;
	}
	public void setRuserid(Integer ruserid) {
		this.ruserid = ruserid;
	}
	/**
	 * 角色、班级、部门、职务、科目ID字符串（用于查询） 
	 * @return
	 */
	private String roleidstr;
	private String clsidstr;
	private String deptidstr;
	private String jobidstr;
	private String subjectidstr;   
	
	public String getRoleidstr() {
		return roleidstr;
	}
	public void setRoleidstr(String roleidstr) {
		this.roleidstr = roleidstr;
	}
	public String getClsidstr() {
		return clsidstr;
	}
	public void setClsidstr(String clsidstr) {
		this.clsidstr = clsidstr;
	}
	public String getDeptidstr() {
		return deptidstr;
	}
	public void setDeptidstr(String deptidstr) {
		this.deptidstr = deptidstr;
	}
	public String getJobidstr() {
		return jobidstr;
	}
	public void setJobidstr(String jobidstr) {
		this.jobidstr = jobidstr;
	}
	public String getSubjectidstr() {
		return subjectidstr;
	}
	public void setSubjectidstr(String subjectidstr) {
		this.subjectidstr = subjectidstr;
	}

	
	public String getUsername(){
		return this.getUserinfo().getUsername();
	}
	public void setUsername(String uname){
		this.getUserinfo().setUsername(uname);
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

	public UserInfo getUserinfo() {
		if(userinfo==null)
			userinfo=new UserInfo();
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public RoleInfo getRoleinfo() {
		if(roleinfo==null)
			roleinfo=new RoleInfo();
		return roleinfo;
	}
	
	public void setRoleinfo(RoleInfo roleinfo) {
		this.roleinfo = roleinfo;
	}
	
	public void setRolename(String rolename) {
		this.getRoleinfo().setRolename(rolename);
	}
	
	public String getRolename() {
		return this.getRoleinfo().getRolename();
	}  
	public void setFlag(Integer flag) {
		this.getRoleinfo().setFlag(flag);
	}
}