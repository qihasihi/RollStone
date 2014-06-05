package  com.school.entity.resource ;

import javax.persistence.Entity;

@Entity
public class ExtendValueInfo implements java.io.Serializable {

	public void ExtendValueInfo (){}
   
    private java.lang.Integer enable;
    private java.util.Date ctime;
    private java.util.Date mtime;
    private java.lang.String valueid;
    private java.lang.String valuename;
    private java.lang.Integer ordernumber;
    
    private ExtendInfo extendInfo;
    
    

    public java.lang.String getValueid() {
		return valueid;
	}
	public void setValueid(java.lang.String valueid) {
		this.valueid = valueid;
	}
	public java.lang.Integer getOrdernumber() {
		return ordernumber;
	}
	public ExtendInfo getExtendInfo() {
		return (extendInfo=(extendInfo==null?new ExtendInfo():extendInfo));
	}
	public void setExtendInfo(ExtendInfo extendInfo) {
		this.extendInfo = extendInfo;
	}
	public java.lang.Integer getEnable(){
      return enable;
    }
    public void setEnable(java.lang.Integer enable){
      this.enable = enable;
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
    public java.lang.String getValuename(){
      return valuename;
    }
    public void setValuename(java.lang.String valuename){
      this.valuename = valuename;
    }
    
    //实体绑定ExtendInfo 表中的Ref
    public String getExtendid(){
      return this.getExtendInfo().getExtendid();
    }
    public void setExtendid(String extendid){
      this.getExtendInfo().setExtendid(extendid);
    }
    public void setExtendname(String extendname){
        this.getExtendInfo().setExtendname(extendname);
      }
    public String getExtendname(){
        return this.getExtendInfo().getExtendname();
      }
    public void setOrdernumber(java.lang.Integer ordernumber){
      this.ordernumber = ordernumber;
    }
  

}