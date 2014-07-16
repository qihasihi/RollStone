package  com.school.entity.teachpaltform;

import com.school.util.UtilTool;

import java.io.Serializable;
import java.util.Date;

public class QuestionOption implements Serializable{

	public void QuestionOption (){}
    private java.lang.String content;
    private java.lang.Integer isright;
    private java.lang.Integer ref;
    private Date ctime;
    private Date mtime;
    private java.lang.String optiontype;
    private java.lang.Integer score;
    private Long questionid;
    private Integer questiontype;

    public Long getQuestionid() {
        return questionid;
    }

    public void setQuestionid(Long questionid) {
        this.questionid = questionid;
    }

    public Integer getQuestiontype() {
        return questiontype;
    }

    public void setQuestiontype(Integer questiontype) {
        this.questiontype = questiontype;
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
                //s=s.replace("\"", "\\"+"\"");//如果原文含有双引号
            }
        }

        return content;
    }

    public java.lang.String getSaveContent(){
      return content;
    }
    public void setContent(java.lang.String content){
      this.content = content;
    }
    public java.lang.Integer getIsright(){
      return isright;
    }
    public void setIsright(java.lang.Integer isright){
      this.isright = isright;
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
    public Date getMtime(){
      return mtime;
    }
    public void setMtime(Date mtime){
      this.mtime = mtime;
    }
    public java.lang.String getOptiontype(){
      return optiontype;
    }
    public void setOptiontype(java.lang.String optiontype){
      this.optiontype = optiontype;
    }
    public java.lang.Integer getScore(){
      return score;
    }
    public void setScore(java.lang.Integer score){
      this.score = score;
    }
}