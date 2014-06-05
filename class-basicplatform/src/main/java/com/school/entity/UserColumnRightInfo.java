package  com.school.entity ;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class UserColumnRightInfo  implements java.io.Serializable{

	public void UserColumnRightInfo (){}
	public void UserColumnRightInfo (String ref){this.ref=ref;}
    private java.lang.String ref;
    private java.util.Date ctime;
    private java.util.Date mtime;
    private java.lang.String muserid;
    
    private ColumnInfo columninfo;
    private UserInfo userinfo;
    private ColumnRightInfo columnrightinfo;
    
    
    
    public ColumnInfo getColumninfo() {
		return (columninfo=(columninfo==null?new ColumnInfo():columninfo));
	}
	public void setColumninfo(ColumnInfo columninfo) {
		this.columninfo = columninfo;
	}
	public String getColumnname() {
		return this.getColumninfo().getColumnname();
	}
	public void setColumnname(String columnname) {
		this.getColumninfo().setColumnname(columnname);
	}
	public String getRealname(){
		return this.getUserinfo().getRealname();
	}
	public void setRealname(String realname){
		this.getUserinfo().setRealname(realname);
	}
	public UserInfo getUserinfo() {
		return (userinfo=(userinfo==null?new UserInfo():userinfo));
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public ColumnRightInfo getColumnrightinfo() {
		return (columnrightinfo=(columnrightinfo==null?new ColumnRightInfo():columnrightinfo));
	}
	public void setColumnrightinfo(ColumnRightInfo columnrightinfo) {
		this.columnrightinfo = columnrightinfo;
	}
	public String getColumnrightname() {
		return this.getColumnrightinfo().getColumnrightname();
	}
	public void setColumnrightname(String columnrightname) {
		this.getColumnrightinfo().setColumnrightname(columnrightname);
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
      return this.getColumnrightinfo().getColumnid();
    }
    public void setColumnrightid(java.lang.Integer columnrightid){
     this.getColumnrightinfo().setColumnrightid(columnrightid);
    }
    public java.lang.String getUserid(){
      return this.getUserinfo().getRef();
    }
    public void setUserid(java.lang.String userid){
     this.getUserinfo().setRef(userid);
    }
    public java.lang.Integer getColumnid(){
      return this.getColumninfo().getColumnid();
    }
    public void setColumnid(java.lang.Integer columnid){
    	this.getColumninfo().setColumnid(columnid);
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