package  com.school.entity.resource ;

import com.school.util.UtilTool;

import javax.persistence.Entity;

@Entity
public class ResourceReport implements java.io.Serializable{

	public void ResourceReport(){}
   
    private java.lang.String content;
    private java.lang.Integer userid;
    private java.lang.Long resid;
    private java.util.Date ctime;
    private java.util.Date mtime;
    private java.lang.Integer ref;
    private java.lang.String realname;

    
    public java.lang.String getRealname() {
		return realname;
	}
	public void setRealname(java.lang.String realname) {
		this.realname = realname;
	}
	public java.lang.String getContent(){
      return content;
    }
    public void setContent(java.lang.String content){
      this.content = content;
    }
    public java.lang.Integer getUserid(){
      return userid;
    }
    public void setUserid(java.lang.Integer userid){
      this.userid = userid;
    }

    public Long getResid() {
        return resid;
    }

    public void setResid(Long resid) {
        this.resid = resid;
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
    public java.lang.Integer getRef(){
      return ref;
    }
    public void setRef(java.lang.Integer ref){
      this.ref = ref;
    }

    public String getCtimeString() {
        return (ctime == null ? "" : UtilTool.DateConvertToString(ctime,
                UtilTool.DateType.type1));
    }

}