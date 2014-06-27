package  com.school.entity.teachpaltform.paper;

public class PaperInfo {

	public void PaperInfo (){}
   
    private java.lang.Integer cuserid;
    private java.lang.Long paperid;
    private java.sql.Timestamp ctime;
    private java.lang.Integer papertype;
    private java.sql.Timestamp mtime;
    private java.lang.Long ref;
    private java.lang.String papername;
    private Float score;


    public java.lang.Integer getCuserid(){
      return cuserid;
    }
    public void setCuserid(java.lang.Integer cuserid){
      this.cuserid = cuserid;
    }
    public java.lang.Long getPaperid(){
      return paperid;
    }
    public void setPaperid(java.lang.Long paperid){
      this.paperid = paperid;
    }
    public java.sql.Timestamp getCtime(){
      return ctime;
    }
    public void setCtime(java.sql.Timestamp ctime){
      this.ctime = ctime;
    }
    public java.lang.Integer getPapertype(){
      return papertype;
    }
    public void setPapertype(java.lang.Integer papertype){
      this.papertype = papertype;
    }
    public java.sql.Timestamp getMtime(){
      return mtime;
    }
    public void setMtime(java.sql.Timestamp mtime){
      this.mtime = mtime;
    }
    public java.lang.Long getRef(){
      return ref;
    }
    public void setRef(java.lang.Long ref){
      this.ref = ref;
    }
    public java.lang.String getPapername(){
      return papername;
    }
    public void setPapername(java.lang.String papername){
      this.papername = papername;
    }
    public Float getScore(){
      return score;
    }
    public void setScore(java.lang.Float score){
      this.score = score;
    }
  

}