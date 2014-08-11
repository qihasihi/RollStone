package  com.school.entity.teachpaltform.paper;

import java.util.Date;

public class PaperQuesTeamScore {

	public void PaperQuesTeamScore (){}
   
    private java.lang.Long paperid;
    private java.lang.Integer ref;
    private Date ctime;
    private Date mtime;
    private Float score;
    private Long quesref;
    private Long courseid;

    public java.lang.Long getPaperid(){
      return paperid;
    }
    public void setPaperid(java.lang.Long paperid){
      this.paperid = paperid;
    }
    public java.lang.Integer getRef(){
      return ref;
    }
    public void setRef(java.lang.Integer ref){
      this.ref = ref;
    }
    public Date getCtime(){
      return ctime;
    }
    public void setCtime(java.sql.Timestamp ctime){
      this.ctime = ctime;
    }
    public Date getMtime(){
      return mtime;
    }
    public void setMtime(java.sql.Timestamp mtime){
      this.mtime = mtime;
    }
    public java.lang.Float getScore(){
      return score;
    }
    public void setScore(java.lang.Float score){
      this.score = score;
    }
    public java.lang.Long getQuesref(){
      return quesref;
    }
    public void setQuesref(java.lang.Long quesref){
      this.quesref = quesref;
    }

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }
}