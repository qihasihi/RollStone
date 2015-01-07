package  com.school.entity.teachpaltform.paper;

import com.school.entity.teachpaltform.QuesTeamRela;
import com.school.entity.teachpaltform.QuestionInfo;
import com.school.entity.teachpaltform.QuestionOption;
import com.school.util.UtilTool;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Entity
public class  PaperQuestion implements Serializable{

	public void PaperQuestion (){}

    private java.lang.Integer orderidx;
    private java.lang.Integer ref;
    private Date ctime;
    private Float score;
    private Object submitnum;//�����ύ����
    private Object markingnum;//������������

    public Object getSubmitnum() {
        return submitnum;
    }

    public void setSubmitnum(Object submitnum) {
        this.submitnum = submitnum;
    }

    public Object getMarkingnum() {
        return markingnum;
    }

    public void setMarkingnum(Object markingnum) {
        this.markingnum = markingnum;
    }

    /**
     * ��ȡ������ֶ�
     */
    public  Long getQuestionid(){
        return this.getQuestioninfo().getQuestionid();
    }
    public void setQuestionid(Long questionid){
        this.getQuestioninfo().setQuestionid(questionid);
    }

    public String getQuestiontypename(){
        if(this.getQuestiontype()==null)
            return null;
        String type=null;
        switch (this.getQuestiontype()){
            case 1:type="����";break;
            case 2:type="�����";break;
            case 3:type="��ѡ��";break;
            case 4:type="��ѡ��";break;
            case 6:
                switch (this.getExtension()){
                    case 0:type="�͹�";break;
                    case 1:type="����";break;
                    case 2:type="�Ķ����";break;
                    case 3:type="�������";break;
                    case 4:type="Ӣ������";break;
                    case 5:type="��ѡ��";break;
                    case 6:type="������";break;
                }
                break;
            case 7:
                switch (this.getExtension()){
                    case 0:type="�͹�";break;
                    case 1:type="����";break;
                    case 2:type="�Ķ����";break;
                    case 3:type="�������";break;
                    case 4:type="Ӣ������";break;
                    case 5:type="��ѡ��";break;
                    case 6:type="������";break;
                }
                break;
            case 8:
                switch (this.getExtension()){
                    case 0:type="�͹�";break;
                    case 1:type="����";break;
                    case 2:type="�Ķ����";break;
                    case 3:type="�������";break;
                    case 4:type="Ӣ������";break;
                    case 5:type="��ѡ��";break;
                    case 6:type="������";break;
                }
                break;
        }
        return type;
    }


    public java.lang.Integer getQuestiontype(){
        return this.getQuestioninfo().getQuestiontype();
    }
    public void setQuestiontype(java.lang.Integer questiontype){
        this.getQuestioninfo().setQuestiontype(questiontype);
    }
    public java.lang.Integer getExtension(){
        return this.getQuestioninfo().getExtension();
    }
    public void setExtension(java.lang.Integer extension){
        this.getQuestioninfo().setExtension(extension);
    }
    public java.lang.String getContent(){
        String content=this.getQuestioninfo().getContent();
       /* if(content!=null&&content.trim().length()>0){
            String t= UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")+"/"+this.getQuestionid()+"/";
            while(content.indexOf("_QUESTIONPIC+")!=-1)
                content=content.replace("_QUESTIONPIC+",t);
            while (content.indexOf("\n")!=-1||content.indexOf("\n\r")!=-1||content.indexOf("\t")!=-1){
                content=content.replace("\n\r", "<br>&nbsp;&nbsp;");
                content=content.replace("\n", "<br>");
                content=content.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
                //content=content.replace(" ", "&nbsp;");
                //s=s.replace("\"", "\\"+"\"");//���ԭ�ĺ���˫����
            }
        }*/
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


    public List<PaperQuestion>  questionTeam=new ArrayList<PaperQuestion>();  //������

    public List<PaperQuestion> getQuestionTeam() {
        return questionTeam;
    }

    public void setQuestionTeam(List<PaperQuestion> questionTeam) {
        this.questionTeam = questionTeam;
    }

    public List<QuestionOption> questionOption=new ArrayList<QuestionOption>();//ѡ��������ѡ��

    public List<QuestionOption> getQuestionOption() {
        return questionOption;
    }

    public void setQuestionOption(List<QuestionOption> questionOption) {
        this.questionOption = questionOption;
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

    public String getCity() {
        return this.getQuestioninfo().getCity();
    }

    public void setCity(String city) {
        this.getQuestioninfo().setCity(city);
    }

    public String getExamyear() {
        return this.getQuestioninfo().getExamyear();
    }

    public void setExamyear(String examyear) {
        this.getQuestioninfo().setExamyear(examyear);
    }

    public Integer getExamtype() {
        return this.getQuestioninfo().getExamtype();
    }

    public void setExamtype(Integer examtype) {
        this.getQuestioninfo().setExamtype(examtype);
    }
    public String getAxamarea() {
        return this.getQuestioninfo().getAxamarea();
    }

    public void setAxamarea(String axamarea) {
        this.getQuestioninfo().setAxamarea(axamarea);
    }
    public String getShowExamYearMsg(){
        return this.getQuestioninfo().getShowExamYearMsg();
    }

    public java.lang.Float getScore(){
      return score;
    }
    public void setScore(java.lang.Float score){
      this.score = score;
    }



}
