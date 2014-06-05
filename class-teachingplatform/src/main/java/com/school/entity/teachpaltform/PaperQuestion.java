package  com.school.entity.teachpaltform;

public class PaperQuestion {

	public void PaperQuestion (){}
   
    private java.lang.Integer questionid;
    private java.lang.Integer orderidx;
    private java.lang.Integer ref;
    private java.sql.Timestamp ctime;
    private java.lang.Integer paperid;
    private java.lang.Integer score;

    public java.lang.Integer getQuestionid(){
      return questionid;
    }
    public void setQuestionid(java.lang.Integer questionid){
      this.questionid = questionid;
    }
    public java.lang.Integer getOrderidx(){
      return orderidx;
    }
    public void setOrderidx(java.lang.Integer orderidx){
      this.orderidx = orderidx;
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
    public java.lang.Integer getPaperid(){
      return paperid;
    }
    public void setPaperid(java.lang.Integer paperid){
      this.paperid = paperid;
    }
    public java.lang.Integer getScore(){
      return score;
    }
    public void setScore(java.lang.Integer score){
      this.score = score;
    }
  

}