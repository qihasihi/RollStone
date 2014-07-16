package  com.school.entity.resource ;

import com.school.entity.UserInfo;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class CheckInfo  implements java.io.Serializable{

	public void CheckInfo (){}
   
    private java.util.Date ctime;
    private java.util.Date mtime;
    private java.lang.String ref;
    private String operateuserid;
    private String operaterealname;
    private ExtendValueInfo extendValueInfo;
    private UserInfo userInfo;
    
    

    public String getOperateuserid() {
		return operateuserid;
	}
	public void setOperateuserid(String operateuserid) {
		this.operateuserid = operateuserid;
	}
	public String getOperaterealname() {
		return operaterealname;
	}
	public void setOperaterealname(String operaterealname) {
		this.operaterealname = operaterealname;
	}
	public ExtendValueInfo getExtendValueInfo() {
		return (extendValueInfo=(extendValueInfo==null?new ExtendValueInfo():extendValueInfo));
	}
	public void setExtendValueInfo(ExtendValueInfo extendValueInfo) {
		this.extendValueInfo = extendValueInfo;
	}
	public void setExtendValueInfo(String valuename) {
		this.getExtendValueInfo().setValuename(valuename);
	}
	public UserInfo getUserInfo() {
		return (userInfo=(userInfo==null?new UserInfo():userInfo));
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public String getUserid(){
      return this.getUserInfo().getRef();
    }
    public void setUserid(String userid){
     this.getUserInfo().setRef(userid);
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
    public java.lang.String getValueid(){
      return this.getExtendValueInfo().getValueid();
    }
    public void setValueid(java.lang.String valueid){
     this.getExtendValueInfo().setValueid(valueid);
    }
    public java.lang.String getRef(){
      return ref;
    }
    public void setRef(java.lang.String ref){
      this.ref = ref;
    }
    public String getCtimeString(){
    	return (ctime==null?"":UtilTool.DateConvertToString(ctime, DateType.type1)); 
    }
    public String getMtimeString(){
    	return (mtime==null?"":UtilTool.DateConvertToString(mtime, DateType.type1)); 
    }
    public String getRealname(){
    	return this.getUserInfo().getRealname();
    }
    public void setRealname(String realname){
    	this.getUserInfo().setRealname(realname);
    }

}