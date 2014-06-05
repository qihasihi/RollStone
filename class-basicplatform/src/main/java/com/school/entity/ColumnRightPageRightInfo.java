package  com.school.entity ;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class ColumnRightPageRightInfo  implements java.io.Serializable{

	public void ColumnRightPageRightInfo (){}
	public void ColumnRightPageRightInfo (String ref){this.ref=ref;}
    private java.lang.String ref;
    private java.util.Date ctime;
    private java.util.Date mtime;
    
    private ColumnRightInfo columnrightinfo;
    private ColumnInfo columninfo;
    private PageRightInfo pagerightinfo;
    private java.lang.String muserid;    
    

    public ColumnRightInfo getColumnrightinfo() {
		return (columnrightinfo=(columnrightinfo==null?new ColumnRightInfo():columnrightinfo));
	}
	public void setColumnrightinfo(ColumnRightInfo columnrightinfo) {
		this.columnrightinfo = columnrightinfo;
	}
	public ColumnInfo getColumninfo() {
		return (columninfo=(columninfo==null?new ColumnInfo():columninfo));
	}
	public void setColumninfo(ColumnInfo columninfo) {
		this.columninfo = columninfo;
	}
	public void setPrref(String ref){
		this.getPagerightinfo().setRef(ref);
	}
	public void setCrref(String cref){
		this.getColumnrightinfo().setRef(cref);
	}
	public PageRightInfo getPagerightinfo() {
		return (pagerightinfo=(pagerightinfo==null?new PageRightInfo():pagerightinfo));
	}
	public void setPagerightinfo(PageRightInfo pagerightinfo) {
		this.pagerightinfo = pagerightinfo;
	}
	public String getPagevalue() {
		return this.getPagerightinfo().getPagevalue();
	}
	public void setPagevalue(String pagevalue) {
		this.getPagerightinfo().setPagevalue(pagevalue);
	}
	public Integer getPagerighttype() {
		return this.getPagerightinfo().getPagerighttype();
	}
	public void setPagerighttype(Integer pagerighttype) {
		this.getPagerightinfo().setPagerighttype(pagerighttype);
	}
	public String getPagename() {
		return this.getPagerightinfo().getPagename();
	}
	public void setPagename(String pagename) {
		this.getPagerightinfo().setPagename(pagename);
	}
	public void setRemark(String remark){
		this.getPagerightinfo().setRemark(remark);
	}
	public java.lang.String getRef(){
      return ref;
    }
    public void setRef(java.lang.String ref){
      this.ref = ref;
    }
    public java.lang.Integer getPagerightid(){
      return this.getPagerightinfo().getPagerightid();
    }
    public void setPagerightid(java.lang.Integer pagerightid){
    	this.getPagerightinfo().setPagerightid(pagerightid);
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
    public java.lang.Integer getColumnrightid(){
      return this.getColumnrightinfo().getColumnrightid();
    }
    public void setColumnrightid(java.lang.Integer columnrightid){
    	 this.getColumnrightinfo().setColumnrightid(columnrightid);
    }
    public java.lang.Integer getColumnid(){
      return this.getColumninfo().getColumnid();
    }
    public void setColumnid(java.lang.Integer columnid){
    	this.getColumninfo().setColumnid(columnid);
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