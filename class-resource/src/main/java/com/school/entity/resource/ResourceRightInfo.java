package  com.school.entity.resource ;

import java.util.Date;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

/**
 * 资源权限表
 * @author zhengzhou
 *
 */
@Entity
public class ResourceRightInfo implements java.io.Serializable{

	public void ResourceRightInfo(){}
    private java.lang.String ref;
    private String cuserid;
    private java.lang.Integer righttype;
    private java.lang.Integer rightroletype;
    private Date ctime;
    private java.lang.String rightsubject;
    private Date mtime;
    private java.lang.String rightuserref;
    private Integer rightuserid;
    private ResourceInfo resourceInfo;    
    private String subjectid;
    private String realname;    

    
    public Integer getRightuserid() {
		return rightuserid;
	}
	public void setRightuserid(Integer rightuserid) {
		this.rightuserid = rightuserid;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getSubjectid() {
		return subjectid;
	}
	public void setSubjectid(String subjectid) {
		this.subjectid = subjectid;
	}
	public Long getResid() {
		return this.getResourceInfo().getResid();
	}
	public void setResid(Long resid) {
		this.getResourceInfo().setResid(resid);
	}
	public ResourceInfo getResourceInfo() {
		return (resourceInfo=(resourceInfo==null?new ResourceInfo():resourceInfo));
	}
	public void setResourceInfo(ResourceInfo resourceInfo) {
		this.resourceInfo = resourceInfo;
	}
	public java.lang.String getRef(){
      return ref;
    }
    public void setRef(java.lang.String ref){
      this.ref = ref;
    }
    public String getCuserid(){
      return cuserid;
    }
    public void setCuserid(String cuserid){
      this.cuserid = cuserid;
    }
    public java.lang.Integer getRighttype(){
      return righttype;
    }
    public void setRighttype(java.lang.Integer righttype){
      this.righttype = righttype;
    }
    public java.lang.Integer getRightroletype(){
      return rightroletype;
    }
    public void setRightroletype(java.lang.Integer rightroletype){
      this.rightroletype = rightroletype;
    }
    public Date getCtime(){
      return ctime;
    }
    public void setCtime(Date ctime){
      this.ctime = ctime;
    }
    public java.lang.String getRightsubject(){
      return rightsubject;
    }
    public void setRightsubject(java.lang.String rightsubject){
      this.rightsubject = rightsubject;
    }
    public Date getMtime(){
      return mtime;
    }
    public void setMtime(Date mtime){
      this.mtime = mtime;
    }
    public java.lang.String getRightuserref(){
      return rightuserref;
    }
    public void setRightuserref(java.lang.String rightuserref){
      this.rightuserref = rightuserref;
    }
    public String getCtimeString(){
    	return (ctime==null?"":UtilTool.DateConvertToString(ctime, DateType.type1)); 
    }
    public String getMtimeString(){
    	return (mtime==null?"":UtilTool.DateConvertToString(mtime, DateType.type1)); 
    }

}