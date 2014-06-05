package  com.school.entity ;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class ColumnInfo  implements java.io.Serializable{

	public void ColumnInfo (){}
	public void ColumnInfo (String ref){this.ref=ref;}
    private java.lang.String path;
    private java.lang.String ref;
    private java.lang.String columnname;
    private java.util.Date ctime;
    private java.util.Date mtime;
    private java.lang.Integer columnid;
    private java.lang.String muserid;
    
    private String styleclassid;			//样式CLASS
    private Integer isopen;  //是否打开新窗口
    
    private String fnid;		//跟总校对应的FNID
    private String remark;

    public Integer getIsopen() {
        return isopen;
    }

    public void setIsopen(Integer isopen) {
        this.isopen = isopen;
    }
    public String getFnid() {
		return fnid;
	}
	public void setFnid(String fnid) {
		this.fnid = fnid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStyleclassid() {
		return styleclassid;
	}
	public void setStyleclassid(String styleclassid) {
		this.styleclassid = styleclassid;
	}
	public java.lang.String getPath(){
      return path;
    }
    public void setPath(java.lang.String path){
      this.path = path;
    }
    public java.lang.String getRef(){
      return ref;
    }
    public void setRef(java.lang.String ref){
      this.ref = ref;
    }
    public java.lang.String getColumnname(){
      return columnname;
    }
    public void setColumnname(java.lang.String columnname){
      this.columnname = columnname;
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
    public java.lang.Integer getColumnid(){
      return columnid;
    }
    public void setColumnid(java.lang.Integer columnid){
      this.columnid = columnid;
    }
    public java.lang.String getMuserid(){
      return muserid;
    }
    public void setMuserid(java.lang.String muserid){
      this.muserid = muserid;
    }
    public String getCtimeString(){
		if(ctime==null)
			return "";
		return UtilTool.DateConvertToString(ctime, DateType.type1);
	}
	

}