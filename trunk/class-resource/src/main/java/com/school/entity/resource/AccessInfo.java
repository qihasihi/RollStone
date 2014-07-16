package  com.school.entity.resource ;

import com.school.entity.UserInfo;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class AccessInfo  implements java.io.Serializable{

    private Integer isFromRank;

    public void AccessInfo (){}
   
    private java.lang.Integer enable;
    private java.util.Date ctime;
    private java.util.Date mtime;
    private java.lang.String ref;
    private ResourceInfo resourceInfo;
    private UserInfo userInfo;
    

    public ResourceInfo getResourceInfo() {
		return (resourceInfo=(resourceInfo==null?new ResourceInfo():resourceInfo));
	}
	public void setResourceInfo(ResourceInfo resourceInfo) {
		this.resourceInfo = resourceInfo;
	}
	public UserInfo getUserInfo() {
		return (userInfo=(userInfo==null?new UserInfo():userInfo));
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public java.lang.Integer getEnable(){
      return enable;
    }
    public void setEnable(java.lang.Integer enable){
      this.enable = enable;
    }
    public java.lang.Integer getUserid(){
      return this.getUserInfo().getUserid();
    }
    public void setUserid(java.lang.Integer userid){
      this.getUserInfo().setUserid(userid);
    }
    public void setHeadimage(String headimage){
        this.getUserInfo().setHeadimage(headimage);
    }
    public void setUsername(String username){
        this.getUserInfo().setUsername(username);
    }
    public void setRealname(String realname){
        this.getUserInfo().setRealname(realname);
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

    public Object getIsFromRank() {
        return isFromRank;
    }

    public void setIsFromRank(Integer isFromRank) {
        this.isFromRank = isFromRank;
    }

}