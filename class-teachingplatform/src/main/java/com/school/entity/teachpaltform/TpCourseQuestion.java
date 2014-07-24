package  com.school.entity.teachpaltform;

import com.school.util.UtilTool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TpCourseQuestion implements Serializable{

    public void TpCourseQuestion (){}
    private java.lang.Long ref;
    private java.util.Date ctime;
    public Object flag;
    private Object Isreference;
    private Long courseid;
    private String coursename;
    private Long paperid;
    private Object paperquesflag;
    private Long currentCourseid;   //当前专题ID
    private Integer gradeid;
    private Integer subjectid;
    private Integer materialid;
    private Integer seldatetype;        //查询用 时间条件

    public Integer getSeldatetype() {
        return seldatetype;
    }

    public void setSeldatetype(Integer seldatetype) {
        this.seldatetype = seldatetype;
    }

    public Integer getGradeid() {
        return gradeid;
    }

    public void setGradeid(Integer gradeid) {
        this.gradeid = gradeid;
    }

    public Integer getMaterialid() {
        return materialid;
    }

    public void setMaterialid(Integer materialid) {
        this.materialid = materialid;
    }

    public Integer getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(Integer subjectid) {
        this.subjectid = subjectid;
    }

    public Long getCurrentCourseid() {
        return currentCourseid;
    }

    public void setCurrentCourseid(Long currentCourseid) {
        this.currentCourseid = currentCourseid;
    }

    public Object getPaperquesflag() {
        return paperquesflag;
    }

    public void setPaperquesflag(Object paperquesflag) {
        this.paperquesflag = paperquesflag;
    }

    public Long getPaperid() {
        return paperid;
    }

    public void setPaperid(Long paperid) {
        this.paperid = paperid;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    private int state;//区分是否选过，引用试题用的


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
     * 获取问题的字段
     */
    public String getQuestiontypename(){
        if(this.getQuestiontype()==null)
            return null;
        String type=null;
        switch (this.getQuestiontype()){
            case 1:type="其他";break;
            case 2:type="填空题";break;
            case 3:type="单选题";break;
            case 4:type="多选题";break;
            case 6:
                switch (this.getExtension()){
                    case 0:type="客观";break;
                    case 1:type="主观";break;
                    case 2:type="阅读理解";break;
                    case 3:type="完型填空";break;
                    case 4:type="英语听力";break;
                    case 5:type="七选五";break;
                    case 6:type="组试题";break;
                }
                break;
            case 7:
                switch (this.getExtension()){
                    case 0:type="客观";break;
                    case 1:type="主观";break;
                    case 2:type="阅读理解";break;
                    case 3:type="完型填空";break;
                    case 4:type="英语听力";break;
                    case 5:type="七选五";break;
                    case 6:type="组试题";break;
                }
                break;
            case 8:
                switch (this.getExtension()){
                    case 0:type="客观";break;
                    case 1:type="主观";break;
                    case 2:type="阅读理解";break;
                    case 3:type="完型填空";break;
                    case 4:type="英语听力";break;
                    case 5:type="七选五";break;
                    case 6:type="组试题";break;
                }
                break;
        }
        return type;
    }

    public java.lang.Integer getExtension(){
        return this.getQuestioninfo().getExtension();
    }
    public void setExtension(java.lang.Integer extension){
        this.getQuestioninfo().setExtension(extension);
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



    public Date getOperatetime() {
        return operatetime;
    }

    public void setOperatetime(Date operatetime) {
        this.operatetime = operatetime;
    }


    public List<TpCourseQuestion>  questionTeam=new ArrayList<TpCourseQuestion>();  //试题组

    public List<TpCourseQuestion> getQuestionTeam() {
        return questionTeam;
    }

    public void setQuestionTeam(List<TpCourseQuestion> questionTeam) {
        this.questionTeam = questionTeam;
    }


    public List<QuestionOption>questionOptionList=new ArrayList<QuestionOption>();

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