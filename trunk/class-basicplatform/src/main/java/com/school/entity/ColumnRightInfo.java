package  com.school.entity ;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class ColumnRightInfo  implements java.io.Serializable{

	public void ColumnRightInfo (){}
	public void ColumnRightInfo (String ref){this.ref=ref;}
    private java.lang.String ref;
    private java.util.Date ctime;
    private java.util.Date mtime;
    private java.lang.Integer columnrightid;
    private java.lang.String columnrightname;
    private java.lang.String muserid;
    
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
	public java.lang.String getRef(){
      return ref;
    }
    public void setRef(java.lang.String ref){
      this.ref = ref;
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
      return columnrightid;
    }
    public void setColumnrightid(java.lang.Integer columnrightid){
      this.columnrightid = columnrightid;
    }
    public java.lang.String getColumnrightname(){
      return columnrightname;
    }
    public void setColumnrightname(java.lang.String columnrightname){
      this.columnrightname = columnrightname;
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