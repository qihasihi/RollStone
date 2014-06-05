package  com.school.entity.resource ;

import javax.persistence.Entity;

@Entity
public class ResourceRecommend implements java.io.Serializable{

	public void ResourceRecommend(){}
   
    private java.util.Date endtime;
    private java.lang.String resid;
    private java.util.Date mtime;
    private java.lang.String ref;

   
    public java.util.Date getEndtime() {
		return endtime;
	}
	public void setEndtime(java.util.Date endtime) {
		this.endtime = endtime;
	}
	public java.lang.String getResid(){
      return resid;
    }
    public void setResid(java.lang.String resid){
      this.resid = resid;
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
  

}