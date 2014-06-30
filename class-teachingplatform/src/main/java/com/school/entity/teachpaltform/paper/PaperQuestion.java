package  com.school.entity.teachpaltform.paper;

import com.school.entity.teachpaltform.QuestionInfo;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity
public class PaperQuestion implements Serializable{

	public void PaperQuestion (){}

    private java.lang.Integer orderidx;
    private java.lang.Integer ref;
    private Date ctime;
    private Float score;

    private QuestionInfo questionInfo;

    public java.lang.Long getQuestionid(){
        return this.getQuestionInfo().getQuestionid();
    }
    public void setQuestionid(java.lang.Long questionid){
        this.getQuestionInfo().setQuestionid(questionid);
    }
    //question_type
    public Integer getQuestiontype(){
        return this.getQuestionInfo().getQuestiontype();
    }
    public void setQuestiontype(Integer questiontype){
        this.getQuestionInfo().setQuestiontype(questiontype);
    }
    //paper_type_id
    public Integer getPapertypeid(){
        return this.getQuestionInfo().getPapertypeid();
    }
    public void setPapertypeid(Integer paperid){
        this.getQuestionInfo().setPapertypeid(paperid);
    }
    //question_level
    public Integer getQuestionlevel(){
        return this.getQuestionInfo().getQuestionlevel();
    }
    public void setQuestionlevel(Integer level){
        this.getQuestionInfo().setQuestionlevel(level);
    }
    //question_level
    public Integer getStatus(){
        return this.getQuestionInfo().getStatus();
    }
    public void setStatus(Integer status){
        this.getQuestionInfo().setStatus(status);
    }
    //cloud_status
    public Integer getCloudstatus(){
        return this.getQuestionInfo().getCloudstatus();
    }
    public void setCloudstatus(Integer cstatus){
        this.getQuestionInfo().setCloudstatus(cstatus);
    }
    //content
    public String getContent(){
        return this.getQuestionInfo().getContent();
    }
    public void setContent(String cstatus){
        this.getQuestionInfo().setContent(cstatus);
    }
    //analysis
    public String getAnalysis(){
        return this.getQuestionInfo().getAnalysis();
    }
    public void setAnalysis(String cstatus){
        this.getQuestionInfo().setAnalysis(cstatus);
    }
    //use_count
    public Integer getUsecount(){
        return this.getQuestionInfo().getUsecount();
    }
    public void setUsecount(Integer cstatus){
        this.getQuestionInfo().setUsecount(cstatus);
    }
    //correct_answer
    public String getCorrectanswer(){
        return this.getQuestionInfo().getCorrectanswer();
    }
    public void setCorrectanswer(String cstatus){
        this.getQuestionInfo().setCorrectanswer(cstatus);
    }

    private PaperInfo paperInfo;
    public void setPaperid(Long paperid){
        this.getPaperInfo().setPaperid(paperid);
    }
    public Long getPaperid(){
        return this.getPaperInfo().getPaperid();
    }
    public String getPapername(){
        return this.getPaperInfo().getPapername();
    }
    public void setPapername(String pname){
        this.getPaperInfo().setPapername(pname);
    }
    //q.*,p.paper_name,p.score pscore,p.paper_type,p.paper_id
    public void setPscore(Float pscore){
        this.getPaperInfo().setScore(pscore);
    }
    public Float getPscore(){
        return this.getPaperInfo().getScore();
    }
    public Integer getPapertype(){
        return this.getPaperInfo().getPapertype();
    }
    public void setPapertype(Integer ptype){
        this.getPaperInfo().setPapertype(ptype);
    }
    public QuestionInfo getQuestionInfo() {
        return (questionInfo=(questionInfo==null?new QuestionInfo():questionInfo));
    }

    public void setQuestionInfo(QuestionInfo questionInfo) {
        this.questionInfo = questionInfo;
    }

    public PaperInfo getPaperInfo() {
        return (paperInfo=(paperInfo==null?new PaperInfo():paperInfo));
    }

    public void setPaperInfo(PaperInfo paperInfo) {
        this.paperInfo = paperInfo;
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
    public Date getCtime(){
      return ctime;
    }
    public void setCtime(Date ctime){
      this.ctime = ctime;
    }
    public java.lang.Float getScore(){
      return score;
    }
    public void setScore(java.lang.Float score){
      this.score = score;
    }


  

}