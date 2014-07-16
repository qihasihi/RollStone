package  com.school.entity.resource ;

import com.school.entity.UserInfo;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class DownloadInfo  implements java.io.Serializable{

	public void DownloadInfo (){}
   
    private java.util.Date ctime;
    private java.util.Date mtime;
    private java.lang.String ref;

    private UserInfo userinfo;
    private ResourceInfo resourceInfo;
    
    

    public UserInfo getUserinfo() {
		return (userinfo=(userinfo==null?new UserInfo():userinfo));
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public ResourceInfo getResourceInfo() {
		return (resourceInfo=(resourceInfo==null?new ResourceInfo():resourceInfo));
	}
	public void setResourceInfo(ResourceInfo resourceInfo) {
		this.resourceInfo = resourceInfo;
	}
	public Integer getUserid(){
      return this.getUserinfo().getUserid();
    }
    public void setUserid(Integer userid){
     this.getUserinfo().setUserid(userid);
    }
    public Long getResid(){
      return this.getResourceInfo().getResid();
    }
    public void setResid(Long resid){
    	this.getResourceInfo().setResid(resid);
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
}