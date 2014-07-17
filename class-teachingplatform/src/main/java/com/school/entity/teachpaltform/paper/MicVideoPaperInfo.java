package  com.school.entity.teachpaltform.paper;

public class MicVideoPaperInfo {

	public void MicVideoPaperInfo (){}
   
    private java.lang.Long paperid;
    private java.lang.Long micvideoid;
    private java.lang.Integer ref;
    private java.sql.Timestamp ctime;
    private String papername;

    public String getPapername() {
        return papername;
    }

    public void setPapername(String papername) {
        this.papername = papername;
    }

    public java.lang.Long getPaperid(){
      return paperid;
    }
    public void setPaperid(java.lang.Long paperid){
      this.paperid = paperid;
    }
    public java.lang.Long getMicvideoid(){
      return micvideoid;
    }
    public void setMicvideoid(java.lang.Long micvideoid){
      this.micvideoid = micvideoid;
    }
    public java.lang.Integer getRef(){
      return ref;
    }
    public void setRef(java.lang.Integer ref){
      this.ref = ref;
    }
    public java.sql.Timestamp getCtime(){
      return ctime;
    }
    public void setCtime(java.sql.Timestamp ctime){
      this.ctime = ctime;
    }
  

}