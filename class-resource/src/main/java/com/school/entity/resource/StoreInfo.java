package  com.school.entity.resource ;

import com.school.entity.UserInfo;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class StoreInfo  implements java.io.Serializable{

	public void StoreInfo (){}
   
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
	public java.lang.Integer getUserid(){
      return this.getUserinfo().getUserid();
    }
    public void setUserid(java.lang.Integer userid){
     this.getUserinfo().setUserid(userid);
    }
    public Long getResid(){
      return this.getResourceInfo().getResid();
    }
    public void setResid(Long resid){
    	this.getResourceInfo().setResid(resid);
    }
    
    public void setResname(java.lang.String resname){
        this.getResourceInfo().setResname(resname);
    }

    public String getResname(){
        return this.getResourceInfo().getResname();
    }

    public void setSchoolname(java.lang.String schoolname){
        this.getResourceInfo().setSchoolname(schoolname);
    }

    public String getSchoolname(){
        return this.getResourceInfo().getSchoolname();
    }

    public void setUsername(java.lang.String username){
        this.getResourceInfo().setUsername(username);
    }

    public String getUsername(){
        return this.getResourceInfo().getUsername();
    }

    public void setFilesuffixname(java.lang.String filesuffixname){
        this.getResourceInfo().setFilesuffixname(filesuffixname);
    }

    public String getFilesuffixname(){
        return this.getResourceInfo().getFilesuffixname();
    }

    public void setSubject(Integer subject){
        this.getResourceInfo().setSubject(subject);
    }

    public void setGrade(Integer grade){
        this.getResourceInfo().setGrade(grade);
    }

    public void setRestype(Integer restype){
        this.getResourceInfo().setRestype(restype);
    }

    public void setFiletype(Integer filetype){
        this.getResourceInfo().setFiletype(filetype);
    }

    public void setReskeyword(java.lang.String reskeyword){
    	this.getResourceInfo().setReskeyword(reskeyword);
    }

    public void setResintroduce(java.lang.String resintroduce){
    	this.getResourceInfo().setResintroduce(resintroduce);
    }

    public void setResstate(java.lang.Integer resstate){
    	this.getResourceInfo().setResstatus(resstate);
    }

    public void setStorenum(java.lang.Integer storenum){
    	this.getResourceInfo().setStorenum(storenum);
    }

    public void setAvgscore(java.math.BigDecimal avgscore){
    	this.getResourceInfo().setAvgscore(avgscore);
    }

    public void setClicks(java.lang.Integer clicks){
    	this.getResourceInfo().setClicks(clicks);
    }

    public void setDownloadnum(java.lang.Integer downloadnum){
        this.getResourceInfo().setDownloadnum(downloadnum);
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

    public void setRctime(java.util.Date rctime){
        this.getResourceInfo().setCtime(rctime);
      }
    
    public void setResscore(Float resscore){
    	this.getResourceInfo().setResscore(resscore);
    }

    public void setRestypename(String restypename) {
        this.getResourceInfo().setRestypename(restypename);
    }

    public void setFiletypename(String filetypename) {
        this.getResourceInfo().setFiletypename(filetypename);
    }

    public void setGradename(String gradename) {
        this.getResourceInfo().setGradename(gradename);
    }

    public void setSubjectname(String subjectname) {
        this.getResourceInfo().setSubjectname(subjectname);
    }

}