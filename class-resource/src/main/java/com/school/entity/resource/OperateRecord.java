package  com.school.entity.resource ;

import javax.persistence.Entity;

@Entity
public class OperateRecord  implements java.io.Serializable{

	/**
	 * 
	 */

	public void OperateRecord (){}
   
	private java.lang.Integer ref;
    private java.lang.Integer userid;
    private java.lang.Long resid;
    private java.util.Date ctime;
    private java.util.Date mtime;
    private java.lang.Integer operatetype;

    
    
    public java.lang.Integer getRef() {
		return ref;
	}
	public void setRef(java.lang.Integer ref) {
		this.ref = ref;
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
    public java.lang.Integer getOperatetype(){
      return operatetype;
    }
    public void setOperatetype(java.lang.Integer operatetype){
      this.operatetype = operatetype;
    }
  

}