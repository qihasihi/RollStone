package com.school.entity;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class ColumnInfo implements java.io.Serializable{

	public void ColumnInfo (){}
	public void ColumnInfo (String ref){this.ref=ref;}
    private String path;
    private String ref;
    private String columnname;
    private java.util.Date ctime;
    private java.util.Date mtime;
    private Integer columnid;
    private String muserid;

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
	public String getPath(){
      return path;
    }
    public void setPath(String path){
      this.path = path;
    }
    public String getRef(){
      return ref;
    }
    public void setRef(String ref){
      this.ref = ref;
    }
    public String getColumnname(){
      return columnname;
    }
    public void setColumnname(String columnname){
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
    public Integer getColumnid(){
      return columnid;
    }
    public void setColumnid(Integer columnid){
      this.columnid = columnid;
    }
    public String getMuserid(){
      return muserid;
    }
    public void setMuserid(String muserid){
      this.muserid = muserid;
    }
    public String getCtimeString(){
		if(ctime==null)
			return "";
		return UtilTool.DateConvertToString(ctime, DateType.type1);
	}
	

}