package  com.school.entity.teachpaltform;

import com.school.entity.UserInfo;
import com.school.entity.teachpaltform.interactive.TpTopicThemeInfo;
import com.school.util.PageUtil.PageUtilTool;
import com.school.util.UtilTool;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
public class TpTaskInfo implements Serializable {

    public void TpTaskInfo (){}
    private String liveaddress;
    private String coursename;
    private java.lang.String taskname;
    private Long taskvalueid;
    private Date ctime;
    private Date mtime;
    private java.lang.String taskremark;
    private Long taskid;
    private java.lang.Integer cloudstatus;
    private java.lang.Integer tasktype;
    private UserInfo userinfo;
    private Integer criteria;
    private Long courseid;
    private Long quoteid;
    private Integer orderidx;
    private Integer quesnum;
    private Integer resourcetype;
    private Integer remotetype;
    private Object classid;
    private String imtaskcontent;
    private String imtaskattach;
    private Object isend;
    private String imtaskanalysis;
    private Integer imtaskattachtype;
    private Object unionflag;
    private Integer ettuserid;
    private Long paperid;
    private String papername;
    private Integer schoolid;
    private Integer subjectid;
    private Object taskflag;
    private String stuname;
    private Long groupid;

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public Object getTaskflag() {
        return taskflag;
    }

    public void setTaskflag(Object taskflag) {
        this.taskflag = taskflag;
    }

    public Integer getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(Integer subjectid) {
        this.subjectid = subjectid;
    }

    public Integer getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(Integer schoolid) {
        this.schoolid = schoolid;
    }

    private Integer allowCompleteTime;

    public Integer getAllowCompleteTime() {
        return allowCompleteTime;
    }

    public void setAllowCompleteTime(Integer allowCompleteTime) {
        this.allowCompleteTime = allowCompleteTime;
    }

    public String getPapername() {
        return papername;
    }

    public void setPapername(String papername) {
        this.papername = papername;
    }

    public Long getPaperid() {
        return paperid;
    }

    public void setPaperid(Long paperid) {
        this.paperid = paperid;
    }

    public Integer getEttuserid() {
        return ettuserid;
    }

    public void setEttuserid(Integer ettuserid) {
        this.ettuserid = ettuserid;
    }

    public Object getUnionflag() {
        return unionflag;
    }

    public void setUnionflag(Object unionflag) {
        this.unionflag = unionflag;
    }

    public String getLiveaddress() {
        return liveaddress;
    }

    public void setLiveaddress(String liveaddress) {
        this.liveaddress = liveaddress;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }


    public Integer getImtaskattachtype() {
        return imtaskattachtype;
    }

    public void setImtaskattachtype(Integer imtaskattachtype) {
        this.imtaskattachtype = imtaskattachtype;
    }

    public String getImtaskanalysis() {
        return imtaskanalysis;
    }

    public void setImtaskanalysis(String imtaskanalysis) {
        this.imtaskanalysis = imtaskanalysis;
    }

    public Object getIsend() {
        return isend;
    }

    public void setIsend(Object isend) {
        this.isend = isend;
    }

    public String getImtaskcontent() {
        return imtaskcontent;
    }

    public void setImtaskcontent(String imtaskcontent) {
        this.imtaskcontent = imtaskcontent;
    }

    public String getImtaskattach() {
        return imtaskattach;
    }

    public void setImtaskattach(String imtaskattach) {
        this.imtaskattach = imtaskattach;
    }

    public Object getClassid() {
        return classid;
    }

    public void setClassid(Object classid) {
        this.classid = classid;
    }

    public String getResourcename() {
        return resourcename;
    }

    public void setResourcename(String resourcename) {
        this.resourcename = resourcename;
    }

    private String resourcename;

    public Integer getResourcetype() {
        return resourcetype;
    }

    public void setResourcetype(Integer resourcetype) {
        this.resourcetype = resourcetype;
    }

    public Integer getRemotetype() {
        return remotetype;
    }

    public void setRemotetype(Integer remotetype) {
        this.remotetype = remotetype;
    }

    public Integer getQuesnum() {
        return quesnum;
    }

    public void setQuesnum(Integer quesnum) {
        this.quesnum = quesnum;
    }

    public Integer getOrderidx() {
        return orderidx;
    }

    public void setOrderidx(Integer orderidx) {
        this.orderidx = orderidx;
    }

    public List<String> getImtaskattachList(){
        List<String> returnList=new ArrayList<String>();
        if(this.imtaskattach!=null&&this.imtaskattach.trim().length()>0){
           String imattach=this.imtaskattach.replaceAll("\"","").replaceAll("\\[","").replaceAll("]","");
            String[] attachUrlArr=imattach.split(",");
            if(attachUrlArr!=null&&attachUrlArr.length>0){
                for (String url:attachUrlArr){
                    if(url!=null&&url.trim().length()>0){
                        returnList.add(url);
                    }
                }
            }
        }
        return returnList;
    }

    private Object totalcount;  //任务总人数
    private Object stucount;    //已做人数


    private Object taskobjname; //关联名称
    private Object flag;    //1：已发布
    private Object objflag;

    public Object getObjflag() {
        return objflag;
    }

    public void setObjflag(Object objflag) {
        this.objflag = objflag;
    }

    private Object questiontype; //任务类型
    private Integer cloudtype;  //查询使用
    private Integer selecttype; //查询使用
    private Object iscomplete;
    private Integer loginuserid;
    private Integer status;
    private Integer userid;
    private Object usertypeid;

    public Object getUsertypeid() {
        return usertypeid;
    }

    public void setUsertypeid(Object usertypeid) {
        this.usertypeid = usertypeid;
    }

    public Object getIscomplete() {
        return iscomplete;
    }

    public void setIscomplete(Object iscomplete) {
        this.iscomplete = iscomplete;
    }
    public Object getStucount() {
        return stucount;
    }

    public void setStucount(Object stucount) {
        this.stucount = stucount;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLoginuserid() {
        return loginuserid;
    }

    public void setLoginuserid(Integer loginuserid) {
        this.loginuserid = loginuserid;
    }

    public Integer getSelecttype() {
        return selecttype;
    }

    public void setSelecttype(Integer selecttype) {
        this.selecttype = selecttype;
    }

    public Integer getCloudtype() {
        return cloudtype;
    }

    public void setCloudtype(Integer cloudtype) {
        this.cloudtype = cloudtype;
    }

    public Object getQuestiontype() {
        return questiontype;
    }
    public void setQuestiontype(Object questiontype) {
        this.questiontype = questiontype;
    }
    public Object getFlag() {
        return flag;
    }
    public void setFlag(Object flag) {
        this.flag = flag;
    }
    public Integer getCriteria() {
        return criteria;
    }
    public void setCriteria(Integer criteria) {
        this.criteria = criteria;
    }
    private String realname;

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * 手机任务提醒内容
     * @return
     */
    public Object getTaskobjnameremind() {
        if(taskobjname==null)return null;
        String content=taskobjname.toString();
       /* if(content!=null&&content.trim().length()>0){
//            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//            String localName=request.getSession().getAttribute("IP_PROC_NAME").toString();
//            String t=localName+UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")+"/"+this.getTaskvalueid()+"/";
//            while(content.indexOf("_QUESTIONPIC+")!=-1)
//                content=content.replaceAll("_QUESTIONPIC\\+",t);
            while (content.indexOf("\n")!=-1||content.indexOf("\n\r")!=-1||content.indexOf("\t")!=-1){
                content=content.replace("\n\r", "<br>&nbsp;&nbsp;");
                content=content.replace("\n", "<br>");
                content=content.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
                //content=content.replace(" ", "&nbsp;");
                //s=s.replace("\"", "\\"+"\"");//如果原文含有双引号
            }
        }*/
        return content;
    }
    public Object getTaskobjname() {
        if(taskobjname==null)return null;
        String content=taskobjname.toString();
        if(content!=null&&content.trim().length()>0){
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String localName=request.getSession().getAttribute("IP_PROC_NAME").toString();
            String t=UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")+"/"+this.getTaskvalueid()+"/";
            while(content.indexOf("_QUESTIONPIC+")!=-1)
                content=content.replaceAll("_QUESTIONPIC\\+",t);
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
    public void setTaskobjname(Object taskobjname) {
        this.taskobjname = taskobjname;
    }
    public Object getTotalcount() {
        return totalcount;
    }
    public void setTotalcount(Object totalcount) {
        this.totalcount = totalcount;
    }

    public UserInfo getUserinfo() {
        if(userinfo==null)
            userinfo=new UserInfo();
        return userinfo;
    }
    public void setUserinfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }


    public java.lang.String getTaskname(){
        return taskname;
    }
    public void setTaskname(java.lang.String taskname){
        this.taskname = taskname;
    }
    public java.lang.Long getTaskvalueid(){
        return taskvalueid;
    }
    public void setTaskvalueid(java.lang.Long taskvalueid){
        this.taskvalueid = taskvalueid;
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
    public void setMtime(java.sql.Timestamp mtime){
        this.mtime = mtime;
    }
    public java.lang.String getTaskremark(){
        if(taskremark==null)return null;
        String content=taskremark.toString();
        while (content.indexOf("\n")!=-1||content.indexOf("\n\r")!=-1||content.indexOf("\t")!=-1){
            content=content.replace("\n\r", "<br>&nbsp;&nbsp;");
            content=content.replace("\n", "<br>");
            content=content.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
        }
        return content;
    }
    public void setTaskremark(java.lang.String taskremark){
        this.taskremark = taskremark;
    }
    public java.lang.String getCuserid(){
        return this.getUserinfo().getRef();
    }
    public void setCuserid(java.lang.String cuserid){
        this.getUserinfo().setRef(cuserid);
    }

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }

    public Long getQuoteid() {
        return quoteid;
    }

    public void setQuoteid(Long quoteid) {
        this.quoteid = quoteid;
    }

    public java.lang.Long getTaskid(){
        return taskid;
    }
    public void setTaskid(java.lang.Long taskid){
        this.taskid = taskid;
    }
    public java.lang.Integer getCloudstatus(){
        return cloudstatus;
    }
    public void setCloudstatus(java.lang.Integer cloudstatus){
        this.cloudstatus = cloudstatus;
    }
    public java.lang.Integer getTasktype(){
        return tasktype;
    }
    public void setTasktype(java.lang.Integer tasktype){
        this.tasktype = tasktype;
    }



    private Object btime;
    private Object etime;



    public Object getBtime() {
        return btime;
    }

    public void setBtime(Object btime) {
        this.btime = btime;
    }


    public Object getEtime() {
        return etime;
    }

    public void setEtime(Object etime) {
        this.etime = etime;
    }

    public String getCtimeString(){
        if(ctime==null)
            return null;
        return UtilTool.DateConvertToString(ctime, UtilTool.DateType.type1);
    }

    public String getMtimeString(){
        if(mtime==null)
            return null;
        return UtilTool.DateConvertToString(mtime, UtilTool.DateType.type1);
    }

    public String getBtimeString(){
        if(btime==null)
            return null;
        return UtilTool.DateConvertToString(UtilTool.StringConvertToDate(btime.toString()), UtilTool.DateType.type1);
    }
    public String getBtimeSimpleString(){
        if(btime==null)
            return null;
        return UtilTool.DateConvertToString(UtilTool.StringConvertToDate(btime.toString()), UtilTool.DateType.type5);
    }

    public String getEtimeString(){
        if(etime==null)
            return null;
        return UtilTool.DateConvertToString(UtilTool.StringConvertToDate(etime.toString()), UtilTool.DateType.type1);
    }

    public String getTaskTypeName(){
        if(this.getTasktype()==null)
            return null;
        String tasktype=null;
        switch (this.getTasktype()){
            case 1:tasktype="资源学习";break;
            case 2:tasktype="互动交流";break;
            case 3:tasktype="试题";break;
            case 4:tasktype="成卷测试";break;
            case 5:tasktype="自主测试";break;
            case 6:tasktype="微课程学习";break;
            case 7:tasktype="移动端任务";break;
            case 8:tasktype="移动端任务";break;
            case 9:tasktype="移动端任务";break;
            case 10:tasktype="直播课";break;
        }
        return  tasktype;
    }


    public String getTaskstatus(){
        if(this.getBtimeString()==null||this.getEtimeString()==null)
            return "";
        try {
            return PageUtilTool.DateDiffNumber(this.getBtimeString(), this.getEtimeString());
        } catch (Exception e) {
            return "";
        }
    }

    private List<QuestionOption>questionOptionList;
    private List<QuestionInfo>questionList;
    private List<QuestionAnswer>questionAnswerList;
    private List<TaskPerformanceInfo>taskPerformanceList;
    private List<TpTopicThemeInfo>tpTopicThemeInfoList;

    //问题
    private List rightAnswerList;
    //问题选项
    private List rightOptionAnswerList;

    public List getRightOptionAnswerList() {
        if(rightOptionAnswerList==null)
            rightOptionAnswerList=new ArrayList();
        return rightOptionAnswerList;
    }

    public void setRightOptionAnswerList(List rightOptionAnswerList) {
        this.rightOptionAnswerList = rightOptionAnswerList;
    }
    public List getRightAnswerList() {
        if(rightAnswerList==null)
            rightAnswerList = new ArrayList();
        return rightAnswerList;
    }

    public void setRightAnswerList(List rightAnswerList) {
        this.rightAnswerList = rightAnswerList;
    }

    public List<QuestionAnswer> getQuestionAnswerList() {
        if(questionAnswerList==null)
            questionAnswerList=new ArrayList<QuestionAnswer>();
        return questionAnswerList;
    }

    public void setQuestionAnswerList(List<QuestionAnswer> questionAnswerList) {
        this.questionAnswerList = questionAnswerList;
    }

    public List<QuestionInfo> getQuestionList() {
        if(questionList==null)
            questionList=new ArrayList<QuestionInfo>();
        return questionList;
    }

    public void setQuestionList(List<QuestionInfo> questionList) {
        this.questionList = questionList;
    }

    public List<QuestionOption> getQuestionOptionList() {
        if(questionOptionList==null)
            questionOptionList=new ArrayList<QuestionOption>();
        return questionOptionList;
    }

    public void setQuestionOptionList(List<QuestionOption> questionOptionList) {
        this.questionOptionList = questionOptionList;
    }


    public List<TaskPerformanceInfo> getTaskPerformanceList() {
        if(taskPerformanceList==null)
            taskPerformanceList=new ArrayList<TaskPerformanceInfo>();
        return taskPerformanceList;
    }

    public void setTaskPerformanceList(List<TaskPerformanceInfo> taskPerformanceList) {
        this.taskPerformanceList = taskPerformanceList;
    }


    public List<TpTopicThemeInfo> getTpTopicThemeInfoList() {
        if(tpTopicThemeInfoList==null)
            tpTopicThemeInfoList=new ArrayList<TpTopicThemeInfo>();
        return tpTopicThemeInfoList;
    }

    public void setTpTopicThemeInfoList(List<TpTopicThemeInfo> tpTopicThemeInfoList) {
        this.tpTopicThemeInfoList = tpTopicThemeInfoList;
    }


    public static enum QUERY_TYPE{
        HOUR{public Integer getValue(){return 1;}},

        晚22至早8{public Integer getValue(){return 2;}},

        立即开始{public Integer getValue(){return 3;}};

        public abstract Integer getValue();
    }

}