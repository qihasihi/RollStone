package  com.school.entity ;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class RoleColumnRightInfo  implements java.io.Serializable{

	public void RoleColumnRightInfo (){}
	public void RoleColumnRightInfo (String ref){this.ref=ref;}
   
    private java.lang.Integer rolecolumnrightid;
    private java.lang.String ref;
    private java.util.Date ctime;
    private java.util.Date mtime;
    private java.lang.String muserid;
    
    private ColumnRightInfo columnrightinfo;
    private RoleInfo roleinfo;
    private ColumnInfo columninfo;
    
    public Integer getColumnid(){
    	return this.getColumninfo().getColumnid();
    }
    
    public void setColumnid(Integer columnid){
    	this.getColumninfo().setColumnid(columnid);
    }
     
    public String getColumnname(){
    	return this.getColumninfo().getColumnname();
    }

    public void setColumnname(String columnname){
    	this.getColumninfo().setColumnname(columnname);
    }
    public ColumnInfo getColumninfo() {
    	if(columninfo==null)
    		columninfo=new ColumnInfo();
		return columninfo;
	}
	public void setColumninfo(ColumnInfo columninfo) {
		this.columninfo = columninfo;
	}
	public ColumnRightInfo getColumnrightinfo() {
		return (columnrightinfo=(columnrightinfo==null?new ColumnRightInfo():columnrightinfo));
	}
	public void setColumnrightinfo(ColumnRightInfo columnrightinfo) {
		this.columnrightinfo = columnrightinfo;
	}
	public String getColumnrightname() {
		return getColumnrightinfo().getColumnrightname();
	}
	public void setColumnrightname(String columnrightname) {
		getColumnrightinfo().setColumnrightname( columnrightname);
	}
	public RoleInfo getRoleinfo() {
		return (roleinfo=(roleinfo==null?new RoleInfo():roleinfo));
	}
	public void setRoleinfo(RoleInfo roleinfo) {
		this.roleinfo = roleinfo;
	}
	public String getRolename() {
		return this.getRoleinfo().getRolename();
	}
	public void setRolename(String rolename) {
		this.getRoleinfo().setRolename(rolename);;
	}
	public java.lang.Integer getRolecolumnrightid(){
      return rolecolumnrightid;
    }
    public void setRolecolumnrightid(java.lang.Integer rolecolumnrightid){
      this.rolecolumnrightid = rolecolumnrightid;
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
    public java.lang.Integer getColumnrightid(){
      return this.getColumnrightinfo().getColumnrightid();
    }
    public void setColumnrightid(java.lang.Integer columnrightid){
    	this.getColumnrightinfo().setColumnrightid(columnrightid);
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