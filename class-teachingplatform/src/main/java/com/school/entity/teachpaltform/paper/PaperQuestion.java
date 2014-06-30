package  com.school.entity.teachpaltform.paper;

import com.school.entity.teachpaltform.QuestionInfo;
import com.school.util.UtilTool;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;
import java.util.Date;

@Entity
public class PaperQuestion implements Serializable{

	public void PaperQuestion (){}

    private java.lang.Integer orderidx;
    private java.lang.Integer ref;
    private Date ctime;
    private Float score;

    /**
     * 获取问题的字段
     */
    public  Long getQuestionid(){
        return this.getQuestioninfo().getQuestionid();
    }
    public void setQuestionid(Long questionid){
        this.getQuestioninfo().setQuestionid(questionid);
    }


    public java.lang.Integer getQuestiontype(){
        return this.getQuestioninfo().getQuestiontype();
    }
    public void setQuestiontype(java.lang.Integer questiontype){
        this.getQuestioninfo().setQuestiontype(questiontype);
    }
    public java.lang.String getContent(){
        String content=this.getQuestioninfo().getContent();
        if(content!=null&&content.trim().length()>0){
            String t= UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")+"/"+this.getQuestionid()+"/";
            while(content.indexOf("_QUESTIONPIC+")!=-1)
                content=content.replace("_QUESTIONPIC+",t);
            while (content.indexOf("\n")!=-1||content.indexOf("\n\r")!=-1||content.indexOf("\t")!=-1){
                content=content.replace("\n\r", "<br>&nbsp;&nbsp;");
                content=content.replace("\n", "<br>");
                content=content.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
                //content=content.replace(" ", "&nbsp;");
                //s=s.replace("\"", "\\"+"\"");//如果原文含有双引号
            }
        }
        return content;
    }
    public void setContent(java.lang.String content){
        this.getQuestioninfo().setContent(content);
    }
    public java.lang.String getAnalysis(){
        return  this.getQuestioninfo().getAnalysis();
    }
    public void setAnalysis(java.lang.String analysis){
        this.getQuestioninfo().setAnalysis(analysis);
    }
    public Integer getStatus(){
        return  this.getQuestioninfo().getStatus();
    }
    public void setStatus(Integer status){
        this.getQuestioninfo().setStatus(status);
    }
    public java.lang.Integer getCloudstatus(){
        return this.getQuestioninfo().getCloudstatus();
    }
    public void setCloudstatus(java.lang.Integer cloudstatus){
        this.getQuestioninfo().setCloudstatus(cloudstatus);
    }
    public java.lang.Integer getQuestionlevel(){
        return this.getQuestioninfo().getQuestionlevel();
    }
    public void setQuestionlevel(java.lang.Integer questionlevel){
        this.getQuestioninfo().setQuestionlevel(questionlevel);
    }




    private QuestionInfo questioninfo;

    public QuestionInfo getQuestioninfo() {
        if(questioninfo==null)
            questioninfo=new QuestionInfo();
        return questioninfo;
    }
    public void setQuestioninfo(QuestionInfo questioninfo) {
        this.questioninfo = questioninfo;
    }



    //paper_type_id
    public Integer getPapertypeid(){
        return this.getQuestioninfo().getPapertypeid();
    }
    public void setPapertypeid(Integer paperid){
        this.getQuestioninfo().setPapertypeid(paperid);
    }

    //use_count
    public Integer getUsecount(){
        return this.getQuestioninfo().getUsecount();
    }
    public void setUsecount(Integer cstatus){
        this.getQuestioninfo().setUsecount(cstatus);
    }
    //correct_answer
    public String getCorrectanswer(){
        return this.getQuestioninfo().getCorrectanswer();
    }
    public void setCorrectanswer(String cstatus){
        this.getQuestioninfo().setCorrectanswer(cstatus);
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