package  com.school.entity.teachpaltform;

import com.school.util.UtilTool;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TpCourseQuestion implements Serializable{

	public void TpCourseQuestion (){}
    private java.lang.Long ref;
    private java.util.Date ctime;
    public Object flag;
    private Object Isreference;
    private Long courseid;


    public Integer getLocalstatus() {
        return localstatus;
    }

    public void setLocalstatus(Integer localstatus) {
        this.localstatus = localstatus;
    }

    private Integer localstatus;
    private Date operatetime;

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }

    public Object getFlag() {
        return flag;
    }
    public void setFlag(Object flag) {
        this.flag = flag;
    }
    private Integer userid;
    private Long referenceid;
    private Long quoteid;

    public Long getQuoteid() {
        return quoteid;
    }

    public void setQuoteid(Long quoteid) {
        this.quoteid = quoteid;
    }

    public Long getReferenceid() {
        return referenceid;
    }
    public void setReferenceid(Long referenceid) {
        this.referenceid = referenceid;
    }
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    //private TpCourseInfo tpCourseinfo;
    private QuestionInfo questioninfo;

//    public TpCourseInfo getTpCourseinfo() {
//        if(tpCourseinfo==null)
//            tpCourseinfo=new TpCourseInfo();
//        return tpCourseinfo;
//    }
//
//    public void setTpCourseinfo(TpCourseInfo tpCourseinfo) {
//        this.tpCourseinfo = tpCourseinfo;
//    }

    public QuestionInfo getQuestioninfo() {
        if(questioninfo==null)
            questioninfo=new QuestionInfo();
        return questioninfo;
    }
    public void setQuestioninfo(QuestionInfo questioninfo) {
        this.questioninfo = questioninfo;
    }
    public Long getQuestionid(){
      return this.getQuestioninfo().getQuestionid();
    }
    public void setQuestionid(Long questionid){
      this.getQuestioninfo().setQuestionid(questionid);
    }
    public java.lang.Long getRef(){
      return ref;
    }
    public void setRef(java.lang.Long ref){
      this.ref = ref;
    }
    public java.util.Date getCtime(){
      return ctime;
    }
    public void setCtime(java.util.Date ctime){
      this.ctime = ctime;
    }

    /**
     * ��ȡ������ֶ�
     */


    public java.lang.Integer getQuestiontype(){
        return this.getQuestioninfo().getQuestiontype();
    }
    public void setQuestiontype(java.lang.Integer questiontype){
        this.getQuestioninfo().setQuestiontype(questiontype);
    }
    public java.lang.String getContent(){
        return  this.getQuestioninfo().getContent();
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

    public List<QuestionOption>questionOptionList;

    public Date getOperatetime() {
        return operatetime;
    }

    public void setOperatetime(Date operatetime) {
        this.operatetime = operatetime;
    }

    public List<QuestionOption> getQuestionOptionList() {
        return questionOptionList;
    }

    public void setQuestionOptionList(List<QuestionOption> getQuestionOptionList) {
        this.questionOptionList = getQuestionOptionList;
    }

    public Object getIsreference() {
        return Isreference;
    }

    public void setIsreference(Object isreference) {
        Isreference = isreference;
    }

    public String getMtimeString(){
        if(operatetime==null)
            return "";
        return UtilTool.DateConvertToString(operatetime, UtilTool.DateType.type1);
    }
}