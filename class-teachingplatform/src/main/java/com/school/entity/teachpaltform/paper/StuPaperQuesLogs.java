package  com.school.entity.teachpaltform.paper;

import com.school.util.UtilTool;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;
@Entity
public class StuPaperQuesLogs implements  Serializable {

	public void StuPaperQuesLogs (){}
   
    private java.lang.Integer isright;
    private java.lang.Long paperid;
    private java.lang.String answer;
    private java.lang.Integer ref;
    private Date ctime;
    private java.lang.Long quesid;
    private java.lang.Integer userid;
    private Integer ettuserid;
    private Float score;
    private Integer ismarking;
    private String annexName;

    private Integer classid;


    private String stuname;
    private String stuno;

    private Long taskid;

    private String ettHeadImgSrc;
    private String ettName;

    public String getEttHeadImgSrc() {
        return ettHeadImgSrc;
    }

    public void setEttHeadImgSrc(String ettHeadImgSrc) {
        this.ettHeadImgSrc = ettHeadImgSrc;
    }

    public String getEttName() {
        return ettName;
    }

    public void setEttName(String ettName) {
        this.ettName = ettName;
    }

    public Integer getEttuserid() {
        return ettuserid;
    }

    public void setEttuserid(Integer ettuserid) {
        this.ettuserid = ettuserid;
    }

    public Long getTaskid() {
        return taskid;
    }

    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }

    public Integer getClassid() {
        return classid;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public String getStuno() {
        return stuno;
    }

    public void setStuno(String stuno) {
        this.stuno = stuno;
    }

    public Integer getIsmarking() {
        return ismarking;
    }

    public void setIsmarking(Integer iamarking) {
        this.ismarking = iamarking;
    }

    public String getAnswerString(){
        String returnVal=answer;
        if(answer!=null&&answer.length()>0){
            returnVal=returnVal.replaceAll("\\\\r\\\\n","</br>").replaceAll("\\n","</br>");
        }
        return returnVal;
    }
    public String getAnnexNameFull(){
        String returnVal="";
        if(getAnnexName()!=null){
            returnVal=UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+getAnnexName();
        }
        return returnVal;
    }

    public String getAnnexName() {
        return annexName;
    }

    public void setAnnexName(String annexName) {
        this.annexName = annexName;
    }

    public java.lang.Integer getIsright(){
      return isright;
    }
    public void setIsright(java.lang.Integer isright){
      this.isright = isright;
    }
    public java.lang.Long getPaperid(){
      return paperid;
    }
    public void setPaperid(java.lang.Long paperid){
      this.paperid = paperid;
    }
    public java.lang.String getAnswer(){
      return answer;
    }
    public void setAnswer(java.lang.String answer){
      this.answer = answer;
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
    public java.lang.Long getQuesid(){
      return quesid;
    }
    public void setQuesid(java.lang.Long quesid){
      this.quesid = quesid;
    }
    public java.lang.Integer getUserid(){
      return userid;
    }
    public void setUserid(java.lang.Integer userid){
      this.userid = userid;
    }
    public java.lang.Float getScore(){
      return score;
    }
    public void setScore(java.lang.Float score){
      this.score = score;
    }
  

}