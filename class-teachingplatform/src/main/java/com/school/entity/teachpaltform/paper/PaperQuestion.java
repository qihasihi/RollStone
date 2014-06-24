package  com.school.entity.teachpaltform.paper;

public class PaperQuestion {

	public void PaperQuestion (){}
   
    private java.lang.Long paperid;
    private java.lang.Integer orderidx;
    private java.lang.Long questionid;
    private java.lang.Integer ref;
    private java.sql.Timestamp ctime;
    private java.lang.Integer score;

    public java.lang.Long getPaperid(){
      return paperid;
    }
    public void setPaperid(java.lang.Long paperid){
      this.paperid = paperid;
    }
    public java.lang.Integer getOrderidx(){
      return orderidx;
    }
    public void setOrderidx(java.lang.Integer orderidx){
      this.orderidx = orderidx;
    }
    public java.lang.Long getQuestionid(){
      return questionid;
    }
    public void setQuestionid(java.lang.Long questionid){
      this.questionid = questionid;
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
    public java.lang.Integer getScore(){
      return score;
    }
    public void setScore(java.lang.Integer score){
      this.score = score;
    }
  

}