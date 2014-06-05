package  com.school.entity.teachpaltform;

import com.school.util.UtilTool;

import java.io.Serializable;
import java.util.*;

public class QuestionInfo implements java.io.Serializable {

	public void QuestionInfo (){}
   
    private java.lang.String city;
    private Long questionid;
    private java.lang.String content;
    private java.lang.Integer examtype;
    private java.lang.String examyear;
    private Date ctime;
    private java.lang.String grade;
    private java.lang.Integer status;
    private java.lang.Integer examsubjecttype;
    private java.lang.Integer questiontype;
    private java.lang.Integer papertypeid;
    private java.lang.String cuserid;
    private java.lang.String axamarea;
    private java.lang.String cusername;
    private java.lang.Integer cloudstatus;
    private java.lang.Integer usecount;
    private java.lang.Integer questionlevel;
    private java.lang.String analysis;//�𰸽���������⽫����Ϊ��ȷ��
    private java.lang.String province;


    private String correctanswer;//��ȷ��

    public List<QuestionOption> questionOption;//ѡ��������ѡ��

    public List<QuestionOption> getQuestionOption() {
        return questionOption;
    }

    public void setQuestionOption(List<QuestionOption> questionOption) {
        this.questionOption = questionOption;
    }

    public String getCorrectanswer() {
        return correctanswer;
    }

    public void setCorrectanswer(String correctanswer) {
        this.correctanswer = correctanswer;
    }

    public java.lang.String getCity(){
      return city;
    }
    public void setCity(java.lang.String city){
      this.city = city;
    }
    public Long getQuestionid(){
      return questionid;
    }
    public void setQuestionid(Long questionid){
      this.questionid = questionid;
    }
    public java.lang.String getContent(){
        if(content!=null&&content.trim().length()>0){
            String t=UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")+"/"+this.getQuestionid()+"/";
            while(content.indexOf("_QUESTIONPIC+")!=-1)
                content=content.replace("_QUESTIONPIC+",t);
            while (content.indexOf("\n")!=-1||content.indexOf("\n\r")!=-1||content.indexOf("\t")!=-1){
                content=content.replace("\n\r", "<br>&nbsp;&nbsp;");
                content=content.replace("\n", "<br>");
                content=content.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
                //content=content.replace(" ", "&nbsp;");
                //s=s.replace("\"", "\\"+"\"");//���ԭ�ĺ���˫����
            }

        }
        return content;
    }

    public java.lang.String getSaveContent(){
        if(this.content!=null){
            this.setContent(this.content.replaceAll("_SZ_SCHOOL_IMG_PLACEHOLDER_","ueditor/jsp/../../"));
        }
        return content;
    }


    public void setContent(java.lang.String content){
      this.content = content;
    }
    public java.lang.Integer getExamtype(){
      return examtype;
    }
    public void setExamtype(java.lang.Integer examtype){
      this.examtype = examtype;
    }
    public java.lang.String getExamyear(){
      return examyear;
    }
    public void setExamyear(java.lang.String examyear){
      this.examyear = examyear;
    }
    public Date getCtime(){
      return ctime;
    }
    public void setCtime(Date ctime){
      this.ctime = ctime;
    }
    public java.lang.String getGrade(){
      return grade;
    }
    public void setGrade(java.lang.String grade){
      this.grade = grade;
    }
    public java.lang.Integer getStatus(){
      return status;
    }
    public void setStatus(java.lang.Integer status){
      this.status = status;
    }
    public java.lang.Integer getExamsubjecttype(){
      return examsubjecttype;
    }
    public void setExamsubjecttype(java.lang.Integer examsubjecttype){
      this.examsubjecttype = examsubjecttype;
    }
    public java.lang.Integer getQuestiontype(){
      return questiontype;
    }
    public void setQuestiontype(java.lang.Integer questiontype){
      this.questiontype = questiontype;
    }
    public java.lang.Integer getPapertypeid(){
      return papertypeid;
    }
    public void setPapertypeid(java.lang.Integer papertypeid){
      this.papertypeid = papertypeid;
    }
    public java.lang.String getCuserid(){
      return cuserid;
    }
    public void setCuserid(java.lang.String cuserid){
      this.cuserid = cuserid;
    }
    public java.lang.String getAxamarea(){
      return axamarea;
    }
    public void setAxamarea(java.lang.String axamarea){
      this.axamarea = axamarea;
    }
    public java.lang.String getCusername(){
      return cusername;
    }
    public void setCusername(java.lang.String cusername){
      this.cusername = cusername;
    }
    public java.lang.Integer getCloudstatus(){
      return cloudstatus;
    }
    public void setCloudstatus(java.lang.Integer cloudstatus){
      this.cloudstatus = cloudstatus;
    }
    public java.lang.Integer getUsecount(){
      return usecount;
    }
    public void setUsecount(java.lang.Integer usecount){
      this.usecount = usecount;
    }
    public java.lang.Integer getQuestionlevel(){
      return questionlevel;
    }
    public void setQuestionlevel(java.lang.Integer questionlevel){
      this.questionlevel = questionlevel;
    }
    public java.lang.String getAnalysis(){
        if(analysis!=null&&analysis.trim().length()>0){
            String t=UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")+"/"+this.getQuestionid()+"/";
            while(analysis.indexOf("_QUESTIONPIC+")!=-1)
                analysis=analysis.replace("_QUESTIONPIC+",t);
        }
        return analysis;
    }
    public java.lang.String getSaveAnalysis(){
        if(this.analysis!=null){
            this.setAnalysis(this.analysis.replaceAll("_SZ_SCHOOL_IMG_PLACEHOLDER_","ueditor/jsp/../../"));
        }
        return analysis;
    }
    public void setAnalysis(java.lang.String analysis){
      this.analysis = analysis;
    }
    public java.lang.String getProvince(){
      return province;
    }
    public void setProvince(java.lang.String province){
      this.province = province;
    }


    public String getCtimeString(){
        if(ctime==null)
            return null;
        return UtilTool.DateConvertToString(ctime, UtilTool.DateType.type1);
    }
   private Integer sourceType=1;
    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }
    public Integer getSourceType(){
        return this.sourceType;
    }


}