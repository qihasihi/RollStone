package  com.school.entity ;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class IdentityInfo  implements java.io.Serializable{

	public void IdentityInfo (){}
	public void IdentityInfo (String ref){this.ref=ref;}
    private java.lang.String ref;
    private java.util.Date ctime;
    private java.util.Date mtime;
    private java.lang.String identityname; 
    private RoleInfo roleinfo;
    
    private java.lang.String muserid;    
    
    public Integer getIsadmin() {
		return this.getRoleinfo().getIsadmin();
	}
	public void setIsadmin(Integer isadmin) {
		this.getRoleinfo().setIsadmin(isadmin);
	}
    
    public String getRolename() {
		return this.getRoleinfo().getRolename();
	}
	public void setRolename(String rolename) {
		this.getRoleinfo().setRolename(rolename);
	}
	public RoleInfo getRoleinfo() {
		return (roleinfo=(roleinfo==null?new RoleInfo():roleinfo));
	}
	public void setRoleinfo(RoleInfo roleinfo) {
		this.roleinfo = roleinfo;
	}
	public java.lang.String getRef(){
      return ref;
    }
    public void setRef(java.lang.String ref){
      this.ref = ref;
    }
    public java.util.Date getCtime(){
      return ctime;
    }
    public void setCtime(java.util.Date ctime){
      this.ctime = ctime;
    }
    public java.util.Date getMtime(){
      return mtime;
    }
    public void setMtime(java.util.Date mtime){
      this.mtime = mtime;
    }
    public java.lang.String getIdentityname(){
      return identityname;
    }
    public void setIdentityname(java.lang.String identityname){
      this.identityname = identityname;
    }
    public java.lang.Integer getRoleid(){
      return this.getRoleinfo().getRoleid();
    }
    public void setRoleid(java.lang.Integer roleid){
    	this.getRoleinfo().setRoleid(roleid);
    }
    public java.lang.String getMuserid(){
      return muserid;
    }
    public void setMuserid(java.lang.String muserid){
      this.muserid = muserid;
    }
  
    public String getCtimeString(){
		if(ctime==null)
			return "";
		return UtilTool.DateConvertToString(ctime, DateType.type1);
	}
	
}