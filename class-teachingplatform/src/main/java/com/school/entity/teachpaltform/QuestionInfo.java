package  com.school.entity.teachpaltform;

import com.school.entity.teachpaltform.paper.StuPaperQuesLogs;
import com.school.util.UtilTool;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class QuestionInfo implements java.io.Serializable {
    /**
     * 该题的正确率
     */
    private float rightLv;
    /**
     * 每个选项的正确率
     */
    private List<Map<String, Object>> optTJMapList;

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
    private java.lang.String analysis;//答案解析，填空题将不作为正确答案
    private java.lang.String province;
    private Integer extension;
    private String questionidStr;
    private QuestionInfo parentQues;
    private List<StuPaperQuesLogs> stuPaperQuesLogsList;


    private Float score;
    /*学生当前题的答卷情况*/
    private StuPaperQuesLogs spqLogs;
    public StuPaperQuesLogs getSpqLogs() {
        return spqLogs;
    }

    public void setSpqLogs(StuPaperQuesLogs spqLogs) {
        this.spqLogs = spqLogs;
    }

    public QuestionInfo getParentQues() {
        return parentQues;
    }

    public void setParentQues(QuestionInfo parentQues) {
        this.parentQues = parentQues;
    }

//    private List<QuestionInfo> childQues=null;


    public List<StuPaperQuesLogs> getStuPaperQuesLogsList() {
        return stuPaperQuesLogsList;
    }

    public void setStuPaperQuesLogsList(List<StuPaperQuesLogs> stuPaperQuesLogsList) {
        this.stuPaperQuesLogsList = stuPaperQuesLogsList;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getQuestionidStr() {
        return questionidStr;
    }

    public void setQuestionidStr(String questionidStr) {
        this.questionidStr = questionidStr;
    }

//    public List<QuestionInfo> getChildQues() {
//        return childQues=(childQues==null?new ArrayList<QuestionInfo>():childQues);
//    }

//    public void setChildQues(List<QuestionInfo> childQues) {
//        this.childQues = childQues;
//    }

    private String correctanswer;//正确答案

    public Integer getExtension() {
        return extension;
    }

    public void setExtension(Integer extension) {
        this.extension = extension;
    }

    public List<QuestionOption> questionOption;//选择题试题选项

    public List<QuestionOption> getQuestionOption() {
        return questionOption;
    }

    public void setQuestionOption(List<QuestionOption> questionOption) {
        this.questionOption = questionOption;
    }

    public String getCorrectanswer() {
        if(correctanswer!=null&&correctanswer.trim().length()>0){
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String localName=request.getSession().getAttribute("IP_PROC_NAME").toString();
            String t=UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")+"/"+this.getQuestionid()+"/";
            while(correctanswer.indexOf("_QUESTIONPIC+")!=-1)
                correctanswer=correctanswer.replaceAll("_QUESTIONPIC\\+",t);
            while (correctanswer.indexOf("\r\n\t")!=-1||
                    correctanswer.indexOf("\r\n")!=-1||
                    correctanswer.indexOf("\n")!=-1
                    ||correctanswer.indexOf("\n\r")!=-1
                    ||correctanswer.indexOf("\t")!=-1
                    ||correctanswer.indexOf("'")!=-1){
                //correctanswer=correctanswer.replace("\r\n\t", "&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
                correctanswer=correctanswer.replace("\r\n\t", "");
                correctanswer=correctanswer.replace("\r\n", "&nbsp;&nbsp;<br>");
                correctanswer=correctanswer.replace("\n", "<br>");
                correctanswer=correctanswer.replace("\n\r", "<br>&nbsp;&nbsp;");
                correctanswer=correctanswer.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
                correctanswer=correctanswer.replace("'", "’");
                //s=s.replace("\"", "\\"+"\"");//如果原文含有双引号
            }

            if(correctanswer.indexOf("<p>")==0)
                correctanswer=correctanswer.replaceFirst("<p>","");
            if(correctanswer.lastIndexOf("</p>")!=-1){
                StringBuilder before=new StringBuilder(correctanswer.substring(0,correctanswer.lastIndexOf("</p>")));
                StringBuilder after=new StringBuilder(correctanswer.substring(correctanswer.lastIndexOf("</p>")+4));
                correctanswer=before.append(after).toString();
            }
        }
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
            content=content.trim();
            if(content.trim().equals("空"))content="";
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String localName=request.getSession().getAttribute("IP_PROC_NAME").toString();
            //String t=localName+UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")+"/"+this.getQuestionid()+"/";
            String t=UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")+"/"+this.getQuestionid()+"/";
           // String t=UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")+"/"+this.getQuestionid()+"/";
            while(content.indexOf("_QUESTIONPIC+")!=-1)
                content=content.replaceAll("_QUESTIONPIC\\+", t);
            while (content.indexOf("\r\n\t")!=-1||
                    content.indexOf("\r\n")!=-1||
                    content.indexOf("\n")!=-1||
                    content.indexOf("\n\r")!=-1||
                    content.indexOf("\t")!=-1||
                    content.indexOf("'")!=-1){
                //content=content.replace("\r\n\t", "&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
                content=content.replaceAll("\r\n\t", "");
                content=content.replaceAll("\r\n", "&nbsp;&nbsp;<br>");
                content=content.replaceAll("\n", "<br>");
                content=content.replaceAll("\n\r", "<br>&nbsp;&nbsp;");
                content=content.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
                content=content.replaceAll("'", "’");
            }
            if(content.indexOf("<p>")==0)
                content=content.replaceFirst("<p>","");
            if(content.lastIndexOf("</p>")!=-1){
                StringBuilder before=new StringBuilder(content.substring(0,content.lastIndexOf("</p>")));
                StringBuilder after=new StringBuilder(content.substring(content.lastIndexOf("</p>")+4));
                content=before.append(after).toString();
            }
        }
        return content;
    }

    public String getUpContent(){
        String returnVal=this.content;
        if(returnVal!=null)
            returnVal=returnVal.replaceAll("ueditor/jsp/../../","_SZ_SCHOOL_IMG_PLACEHOLDER_");
        while (returnVal.indexOf("\r\n\t")!=-1||
                returnVal.indexOf("\r\n")!=-1||
                returnVal.indexOf("\n")!=-1||
                returnVal.indexOf("\n\r")!=-1||
                returnVal.indexOf("\t")!=-1||
                returnVal.indexOf("'")!=-1){
            //content=content.replace("\r\n\t", "&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
            returnVal=returnVal.replaceAll("\r\n\t", "");
            returnVal=returnVal.replaceAll("\r\n", "&nbsp;&nbsp;<br>");
            returnVal=returnVal.replaceAll("\n", "<br>");
            returnVal=returnVal.replaceAll("\n\r", "<br>&nbsp;&nbsp;");
            returnVal=returnVal.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
            returnVal=returnVal.replaceAll("'", "’");
        }
        return returnVal;

    }
    public String getUpAnalysis(){
        String returnVal=this.analysis;
        if(returnVal!=null){
            returnVal=returnVal.replaceAll("ueditor/jsp/../../","_SZ_SCHOOL_IMG_PLACEHOLDER_");
            while (returnVal.indexOf("\r\n\t")!=-1||returnVal.indexOf("\n")!=-1||returnVal.indexOf("\n\r")!=-1
                    ||returnVal.indexOf("\t")!=-1||returnVal.indexOf("'")!=-1){
                //analysis=analysis.replaceAll("\r\n\t", "&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
                returnVal=returnVal.replaceAll("\r\n\t", "");
                returnVal=returnVal.replaceAll("\r\n", "&nbsp;&nbsp;<br>");
                returnVal=returnVal.replaceAll("\n", "<br>");
                returnVal=returnVal.replaceAll("\n\r", "<br>&nbsp;&nbsp;");
                returnVal=returnVal.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
                returnVal=returnVal.replaceAll("'", "’");
                //content=content.replaceAll(" ", "&nbsp;");
                //s=s.replace("\"", "\\"+"\"");//如果原文含有双引号
            }
        }
        return returnVal;
    }

    public java.lang.String getSaveContent(){
        if(this.content!=null){
            this.setContent(this.content.replaceAll("_SZ_SCHOOL_IMG_PLACEHOLDER_", "ueditor/jsp/../../"));
            while (content.indexOf("\r\n\t")!=-1||
                    content.indexOf("\r\n")!=-1||
                    content.indexOf("\n")!=-1||
                    content.indexOf("\n\r")!=-1||
                    content.indexOf("\t")!=-1||
                    content.indexOf("'")!=-1){
                //content=content.replace("\r\n\t", "&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
                content=content.replaceAll("\r\n\t", "");
                content=content.replaceAll("\r\n", "&nbsp;&nbsp;<br>");
                content=content.replaceAll("\n", "<br>");
                content=content.replaceAll("\n\r", "<br>&nbsp;&nbsp;");
                content=content.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
                content=content.replaceAll("'", "’");
            }
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
            analysis=analysis.trim();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String localName=request.getSession().getAttribute("IP_PROC_NAME").toString();
            String t=UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")+"/"+this.getQuestionid()+"/";

            while(analysis.indexOf("_QUESTIONPIC+")!=-1)
                analysis=analysis.replaceAll("_QUESTIONPIC\\+", t);
            while (analysis.indexOf("\r\n\t")!=-1||analysis.indexOf("\n")!=-1||analysis.indexOf("\n\r")!=-1||analysis.indexOf("\t")!=-1||analysis.indexOf("'")!=-1){
                //analysis=analysis.replaceAll("\r\n\t", "&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
                analysis=analysis.replaceAll("\r\n\t", "");
                analysis=analysis.replaceAll("\r\n", "&nbsp;&nbsp;<br>");
                analysis=analysis.replaceAll("\n", "<br>");
                analysis=analysis.replaceAll("\n\r", "<br>&nbsp;&nbsp;");
                analysis=analysis.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
                analysis=analysis.replaceAll("'", "’");
                //content=content.replaceAll(" ", "&nbsp;");
                //s=s.replace("\"", "\\"+"\"");//如果原文含有双引号
            }
            if(analysis.indexOf("<p>")==0)
                analysis=analysis.replaceFirst("<p>","");
            if(analysis.lastIndexOf("</p>")!=-1){
                StringBuilder before=new StringBuilder(analysis.substring(0,analysis.lastIndexOf("</p>")));
                StringBuilder after=new StringBuilder(analysis.substring(analysis.lastIndexOf("</p>")+4));
                analysis=before.append(after).toString();
            }
        }else
            analysis="无";
        return analysis;
    }
    public java.lang.String getSaveAnalysis(){
        if(this.analysis!=null){
            this.setAnalysis(this.analysis.replaceAll("_SZ_SCHOOL_IMG_PLACEHOLDER_","ueditor/jsp/../../"));
            while (analysis.indexOf("\r\n\t")!=-1||analysis.indexOf("\n")!=-1||analysis.indexOf("\n\r")!=-1||analysis.indexOf("\t")!=-1||analysis.indexOf("'")!=-1){
                //analysis=analysis.replaceAll("\r\n\t", "&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
                analysis=analysis.replaceAll("\r\n\t", "");
                analysis=analysis.replaceAll("\r\n", "&nbsp;&nbsp;<br>");
                analysis=analysis.replaceAll("\n", "<br>");
                analysis=analysis.replaceAll("\n\r", "<br>&nbsp;&nbsp;");
                analysis=analysis.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
                analysis=analysis.replaceAll("'", "’");
                //content=content.replaceAll(" ", "&nbsp;");
                //s=s.replace("\"", "\\"+"\"");//如果原文含有双引号
            }
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


    public void setRightLv(float rightLv) {
        this.rightLv = rightLv;
    }

    public float getRightLv() {
        return rightLv;
    }

    public void setOptTJMapList(List<Map<String, Object>> optTJMapList) {
        this.optTJMapList = optTJMapList;
    }

    public List<Map<String, Object>> getOptTJMapList() {
        return optTJMapList;
    }

    public String getShowExamYearMsg(){
        StringBuilder returnVal=null;
        if(((this.getCity()!=null&&this.getCity().trim().equals("-1"))
                ||(this.getAxamarea()!=null&&this.getAxamarea().trim().length()>0))
                &&this.getExamyear()!=null&&this.getExamyear().trim().length()>0
                &&this.getExamtype()!=null){
            if(this.getCity()==null||this.getCity().trim().equals("-1")){
                if(this.getAxamarea()!=null&&this.getAxamarea().trim().length()>0){
                    returnVal=new StringBuilder(this.getAxamarea());
                }
            }else{
                returnVal=new StringBuilder(this.getCity());
            }
            if(returnVal!=null){
                returnVal.append(" ").append(this.getExamyear());
                if(this.getExamtype()!=null){
                    String examTypeVar="其它";
                    switch(this.getExamtype()){
                        case 1:
                            examTypeVar="高考";
                            break;
                        case 2:
                            examTypeVar="中考";
                            break;
                        case 0:
                            examTypeVar="其它";
                            break;
                    }
                    if(examTypeVar.equals("其它")){
                        returnVal=null;
                    }else
                        returnVal.append(examTypeVar).append("题");

                }
            }
        }
        return returnVal==null?null:returnVal.toString();
    }
}