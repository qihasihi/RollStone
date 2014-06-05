package  com.school.entity.resource ;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class ExtendResource  implements java.io.Serializable{

	public void ExtendResource(){}
   
    private java.util.Date ctime;
    private java.util.Date mtime;
    private java.lang.String ref;
    private String posisions;
    private String rootextendid;
    private ExtendValueInfo extendValueInfo;
    private ResourceInfo resourceInfo;
    private String fullname;
    
    public String getRootextendid() {
		return rootextendid;
	}
	public void setRootextendid(String rootextendid) {
		this.rootextendid = rootextendid;
	}	
	public String getPosisions() {
		return posisions;
	}
	public void setPosisions(String posisions) {
		this.posisions = posisions;
	}
	public ExtendValueInfo getExtendValueInfo() {
		return (extendValueInfo=(extendValueInfo==null?new ExtendValueInfo():extendValueInfo));
	}
	public void setExtendValueInfo(ExtendValueInfo extendValueInfo) {
		this.extendValueInfo = extendValueInfo;
	}
	public ResourceInfo getResourceInfo() {
		return (resourceInfo=(resourceInfo==null?new ResourceInfo():resourceInfo));
	}
	public void setResourceInfo(ResourceInfo resourceInfo) {
		this.resourceInfo = resourceInfo;
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
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

}