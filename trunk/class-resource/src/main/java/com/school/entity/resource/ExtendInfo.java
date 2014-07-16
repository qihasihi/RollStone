package  com.school.entity.resource ;

import java.util.ArrayList;
import java.util.List;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class ExtendInfo implements java.io.Serializable {

	public void ExtendInfo (){}
    private java.lang.Integer enable;
    private java.lang.String extendname;
    private java.util.Date ctime;
    private java.util.Date mtime;
    private java.lang.String dependextendvalueid;
    private java.lang.String extendid;
    private java.lang.String dependextendid;
    private java.lang.Integer ordernumber;
    private java.lang.Integer isquery;
    private String dependname;
    private String extendvaluelist;
    private List<ExtendValueInfo> extendValueInfos;
    private Integer check;  // «∑Ò «…Û∫À ˜
    
	public List<ExtendValueInfo> getExtendValueInfos() {
		return (extendValueInfos=(extendValueInfos==null?new ArrayList<ExtendValueInfo>():extendValueInfos));
	}
	public void setExtendValueInfos(List<ExtendValueInfo> extendValueInfos) {
		this.extendValueInfos = extendValueInfos;
	}
	public String getExtendvaluelist() {
		return extendvaluelist;
	}
	public void setExtendvaluelist(String extendvaluelist) {
		this.extendvaluelist = extendvaluelist;
	}
	public String getDependname() {
		return dependname;
	}
	public void setDependname(String dependname) {
		this.dependname = dependname;
	}
	public java.lang.Integer getEnable(){
      return enable;
    }
	public Integer getCheck() {
		return check;
	}
	public void setCheck(Integer check) {
		this.check = check;
	}
	public void setEnable(java.lang.Integer enable){
      this.enable = enable;
    }
    public java.lang.String getExtendname(){
      return extendname;
    }
    public void setExtendname(java.lang.String extendname){
      this.extendname = extendname;
    }
    public String getExtendnameString(){
    	if(this.getExtendname()==null)
    		return null;
    	String returnVal="";
    	if(this.getExtendname().length()==2)
    		return this.getExtendname().substring(0,1)+"&nbsp;&nbsp;&nbsp;&nbsp;"+this.getExtendname().substring(1);    		
    	return this.getExtendname();
    	
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
    public java.lang.String getDependextendvalueid() {
		return dependextendvalueid;
	}
	public void setDependextendvalueid(java.lang.String dependextendvalueid) {
		this.dependextendvalueid = dependextendvalueid;
	}
	public java.lang.String getExtendid() {
		return extendid;
	}
	public void setExtendid(java.lang.String extendid) {
		this.extendid = extendid;
	}
	public java.lang.String getDependextendid() {
		return dependextendid;
	}
	public void setDependextendid(java.lang.String dependextendid) {
		this.dependextendid = dependextendid;
	}
	public java.lang.Integer getOrdernumber(){
      return ordernumber;
    }
    public void setOrdernumber(java.lang.Integer ordernumber){
      this.ordernumber = ordernumber;
    }
    public java.lang.Integer getIsquery(){
      return isquery;
    }
    public void setIsquery(java.lang.Integer isquery){
      this.isquery = isquery;
    }
    public String getCtimeString(){
    	return (ctime==null?"":UtilTool.DateConvertToString(ctime, DateType.type1)); 
    }
    public String getMtimeString(){
    	return (mtime==null?"":UtilTool.DateConvertToString(mtime, DateType.type1)); 
    }

}