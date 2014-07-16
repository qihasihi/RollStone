package  com.school.entity.teachpaltform;

public class TpPaperInfo {

	public void TpPaperInfo (){}
   
    private java.sql.Timestamp ctime;
    private java.sql.Timestamp mtime;
    private java.lang.Integer paperid;
    private java.lang.String cuserid;
    private java.lang.Integer courseid;
    private java.lang.Integer cloudstatus;
    private java.lang.String papername;
    private java.lang.Integer usecount;

    public java.sql.Timestamp getCtime(){
      return ctime;
    }
    public void setCtime(java.sql.Timestamp ctime){
      this.ctime = ctime;
    }
    public java.sql.Timestamp getMtime(){
      return mtime;
    }
    public void setMtime(java.sql.Timestamp mtime){
      this.mtime = mtime;
    }
    public java.lang.Integer getPaperid(){
      return paperid;
    }
    public void setPaperid(java.lang.Integer paperid){
      this.paperid = paperid;
    }
    public java.lang.String getCuserid(){
      return cuserid;
    }
    public void setCuserid(java.lang.String cuserid){
      this.cuserid = cuserid;
    }
    public java.lang.Integer getCourseid(){
      return courseid;
    }
    public void setCourseid(java.lang.Integer courseid){
      this.courseid = courseid;
    }
    public java.lang.Integer getCloudstatus(){
      return cloudstatus;
    }
    public void setCloudstatus(java.lang.Integer cloudstatus){
      this.cloudstatus = cloudstatus;
    }
    public java.lang.String getPapername(){
      return papername;
    }
    public void setPapername(java.lang.String papername){
      this.papername = papername;
    }
    public java.lang.Integer getUsecount(){
      return usecount;
    }
    public void setUsecount(java.lang.Integer usecount){
      this.usecount = usecount;
    }
  

}