package  com.school.entity ;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class UserIdentityInfo  implements java.io.Serializable{

	public void UserIdentityInfo (){}
	public void UserIdentityInfo (String ref){this.ref=ref;}
    private java.lang.String ref;
    private java.util.Date ctime;
    private java.util.Date mtime;		
    private java.lang.String muserid;			//最后操作人
    
    private UserInfo userinfo;				//关键用户
    private String  identityname;		
    

    public UserInfo getUserinfo() {    	
		return (userinfo=(userinfo==null?new UserInfo():userinfo));
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public String getRealname(){
		return this.getUserinfo().getRealname();
	}
	public void setRealname(String realname){
		this.getUserinfo().setRealname(realname);
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
    public void setIdentityname(java.lang.String identityref){
    	this.identityname=identityref;
    }
    public java.lang.String getUserid(){
      return this.getUserinfo().getRef();
    }
    public void setUserid(java.lang.String userid){
      this.getUserinfo().setRef(userid);
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