package com.school.entity;

import javax.persistence.Entity;
import java.util.Date;
@Entity
public class PageRightInfo implements java.io.Serializable{

   
    private String pagename;
    private String ref;
    private Integer pagerightid;
    private Date ctime;
    private Integer pagerightparentid;
    private Date mtime;
    private Integer pagerighttype;
    private String pagevalue;
    private String remark;//±¸×¢
    
    private ColumnInfo columninfo;
    
	public ColumnInfo getColumninfo() {
		return (columninfo=(columninfo==null?new ColumnInfo():columninfo));
	}
	public void setColumninfo(ColumnInfo columninfo) {
		this.columninfo = columninfo;
	}
	public String getColumnname() {
		return this.getColumninfo().getColumnname();
	}
	public void setColumnname(String columnname) {
		this.getColumninfo().setColumnname(columnname);
	}
	public String getColumnid() {
		if(this.getColumninfo().getColumnid()==null)
			return null;
		return this.getColumninfo().getColumnid().toString();
	}
	public void setColumnid(String columnid) {
		if(columnid==null)
			this.getColumninfo().setColumnid(null);
		this.getColumninfo().setColumnid(Integer.parseInt(columnid));
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPagename() {
		return pagename;
	}
	public void setPagename(String pagename) {
		this.pagename = pagename;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public Integer getPagerightid() {
		return pagerightid;
	}
	public void setPagerightid(Integer pagerightid) {
		this.pagerightid = pagerightid;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public Integer getPagerightparentid() {
		return pagerightparentid;
	}
	public void setPagerightparentid(Integer pagerightparentid) {
		this.pagerightparentid = pagerightparentid;
	}
	public Date getMtime() {
		return mtime;
	}
	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}
	public Integer getPagerighttype() {
		return pagerighttype;
	}
	public void setPagerighttype(Integer pagerighttype) {
		this.pagerighttype = pagerighttype;
	}
	public String getPagevalue() {
		return pagevalue;
	}
	public void setPagevalue(String pagevalue) {
		this.pagevalue = pagevalue;
	}
   
	//²éÑ¯     
	private String pagenamevalue;
	public String getPagenamevalue() {
		return pagenamevalue;
	}
	public void setPagenamevalue(String pagenamevalue) {
		this.pagenamevalue = pagenamevalue;
	}
	
	

}