package  com.school.entity.resource ;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class ResourceFileInfo  implements java.io.Serializable{

	public void ResourceFileInfo(){}
     private java.lang.String filename;
    private Integer filesize;
    private Integer schoolid;
    private java.util.Date ctime;
    private java.util.Date mtime;
    private java.lang.String ref;
    private java.lang.String path;
    private ResourceInfo resourceInfo;  

    public java.lang.String getPath() {
    	/*if(path!=null&&path.indexOf("/")==-1)
    		return path+"/"; */
		return path;  
	}
	public void setPath(java.lang.String path) {
		this.path = path;
	}
	public ResourceInfo getResourceInfo() {
		return (resourceInfo=(resourceInfo==null?new ResourceInfo():resourceInfo));
	}
	public void setResourceInfo(ResourceInfo resourceInfo) {
		this.resourceInfo = resourceInfo;
	}
	public java.lang.String getFilename(){
      return filename;
    }
    public void setFilename(java.lang.String filename){
      this.filename = filename;
    }
    public Integer getFilesize(){
      return filesize;
    }
    public void setFilesize(Integer filesize){
      this.filesize = filesize;
    }
    public String getFileSizeFormat(){
    	if(filesize!=null)
    		return UtilTool.formatFileSize(filesize);
    	return filesize+"";
    }
    public Integer getSchoolid() {
		return schoolid;
	}
	public void setSchoolid(Integer schoolid) {
		this.schoolid = schoolid;
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