package  com.school.entity.teachpaltform.paper;

public class StuPaperLogs {

	public void StuPaperLogs (){}
   
    private java.lang.Long paperid;
    private java.sql.Timestamp ctime;
    private java.lang.Integer userid;
    private java.lang.Long ref;
    private java.lang.Integer score;

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
    public java.lang.Integer getUserid(){
      return userid;
    }
    public void setUserid(java.lang.Integer userid){
      this.userid = userid;
    }
    public java.lang.Long getRef(){
      return ref;
    }
    public void setRef(java.lang.Long ref){
      this.ref = ref;
    }
    public java.lang.Integer getScore(){
      return score;
    }
    public void setScore(java.lang.Integer score){
      this.score = score;
    }
  

}