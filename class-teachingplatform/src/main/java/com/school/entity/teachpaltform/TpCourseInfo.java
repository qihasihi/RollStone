package  com.school.entity.teachpaltform;

import com.school.util.UtilTool;

import javax.persistence.Entity;
import java.text.ParseException;
import java.util.*;

@Entity
public class TpCourseInfo  implements java.io.Serializable{

    public void TpCourseInfo (){}

    private java.lang.String schoolname;
    private java.util.Date ctime;
    private java.lang.String teachername;
    private java.lang.Integer cloudstatus;
    private java.lang.Integer coursestatus;
    private java.util.Date mtime;
    private Integer cuserid;                   //课题创建者编号
    private Integer userid;                   //课题使用者编号
    private java.lang.Integer localstatus;
    private Long courseid;
    private java.lang.Integer sharetype;
    private java.lang.Integer courselevel;
    private java.lang.String coursename;
    private String introduction;
    private java.lang.Long quoteid;
    private Object isbegin;
    private Integer selectType;     //查询类型   1:查询已经开课的课程

    private Object courseclassref; // 专题班级关联标识ID
    private String termid;
    private Integer subjectid;
    private Integer gradeid;

    private Object commentnum;
    private Object avgscore;

    private String materialidvalues;   //传教材id ，多个以逗号隔开

    private Object classes;
    private Object classesid;
    private Object classtype;
    private Object classtimes;

    private boolean beginClass=true;
    private boolean classtimetype=true;

    private String receiveteachername;   // 受托管老师姓名
    private Object teacherid;   // 专题使用老师ID

    //private TeachingMaterialInfo teachingmaterialinfo;
    private String materialids;
    private String materialnames;

    //关联显示值
    private Object resnum;//资源数量
    private Object questionnum;//试题数量
    private String gradename;

    private Object iscomment; // 学生已评价：1没有， 0已评价

    private java.lang.Integer qcourselevel;
    private Integer qcuserid;

    private String realname;
    private Integer materialid;

    private Object taskcount;
    private Object rescount;
    private Object quescount;
    private Object topiccount;
    private Integer filterquote;    //去除被引用的专题
    private Integer uncompletenum;
    private Integer questiontype;

    public Integer getDcschoolid() {
        return dcschoolid;
    }

    public void setDcschoolid(Integer dcschoolid) {
        this.dcschoolid = dcschoolid;
    }

    private Integer dcschoolid;
    public Integer getQuestiontype() {
        return questiontype;
    }

    public void setQuestiontype(Integer questiontype) {
        this.questiontype = questiontype;
    }

    private String subjectvalues;

    private String versionvalues;
    private Long currentcourseid;

    public Long getCurrentcourseid() {
        return currentcourseid;
    }

    public void setCurrentcourseid(Long currentcourseid) {
        this.currentcourseid = currentcourseid;
    }

    public String getSubjectvalues() {
        return subjectvalues;
    }

    public void setSubjectvalues(String subjectvalues) {
        this.subjectvalues = subjectvalues;
    }

    public Integer getUncompletenum() {
        return uncompletenum;
    }

    public void setUncompletenum(Integer uncompletenum) {
        this.uncompletenum = uncompletenum;
    }

    public Integer getFilterquote() {
        return filterquote;
    }

    public void setFilterquote(Integer filterquote) {
        this.filterquote = filterquote;
    }

    public Object getIsbegin() {
        return isbegin;
    }
    public void setIsbegin(Object isbegin) {
        this.isbegin = isbegin;
    }
    public Object getTopiccount() {
        return topiccount;
    }

    public void setTopiccount(Object topiccount) {
        this.topiccount = topiccount;
    }

    public Object getQuescount() {
        return quescount;
    }

    public void setQuescount(Object quescount) {
        this.quescount = quescount;
    }

    public Object getRescount() {
        return rescount;
    }

    public void setRescount(Object rescount) {
        this.rescount = rescount;
    }

    public Object getTaskcount() {
        return taskcount;
    }

    public void setTaskcount(Object taskcount) {
        this.taskcount = taskcount;
    }



    public Integer getMaterialid() {
        return materialid;
    }

    public void setMaterialid(Integer materialid) {
        this.materialid = materialid;
    }

    public String getRealname() {
        if(this.realname==null)
            return "";
        return realname;
    }

    public Integer getSelectType() {
        return selectType;
    }

    public void setSelectType(Integer selectType) {
        this.selectType = selectType;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Object getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(Object commentnum) {
        this.commentnum = commentnum;
    }

    public Object getAvgscore() {
        return avgscore;
    }

    public void setAvgscore(Object avgscore) {
        this.avgscore = avgscore;
    }

    public Integer getQcourselevel() {
        return qcourselevel;
    }

    public void setQcourselevel(Integer qcourselevel) {
        this.qcourselevel = qcourselevel;
    }

    public Integer getQcuserid() {
        return qcuserid;
    }

    public void setQcuserid(Integer qcuserid) {
        this.qcuserid = qcuserid;
    }



    private String materialname;

    public String getMaterialname() {
        return materialname;
    }

    public void setMaterialname(String materialname) {
        this.materialname = materialname;
    }
    private String versionname;

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public String getMaterialnames() {
        return materialnames;
    }

    public void setMaterialnames(String materialnames) {
        this.materialnames = materialnames;
    }

    public String getMaterialids() {
        return materialids;
    }

    public void setMaterialids(String materialids) {
        this.materialids = materialids;
    }

    public Long getQuoteid() {
        return quoteid;
    }

    public void setQuoteid(Long quoteid) {
        this.quoteid = quoteid;
    }

    public Object getIscomment() {
        return iscomment;
    }

    public void setIscomment(Object iscomment) {
        this.iscomment = iscomment;
    }

    public Object getCourseclassref() {
        return courseclassref;
    }

    public void setCourseclassref(Object courseclassref) {
        this.courseclassref = courseclassref;
    }

    public Object getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(Integer teacherid) {
        this.teacherid = teacherid;
    }

    public String getReceiveteachername() {
        return receiveteachername;
    }

    public Object getQuestionnum() {
        return questionnum;
    }

    public void setQuestionnum(Object questionnum) {
        this.questionnum = questionnum;
    }

    public void setReceiveteachername(String receiveteachername) {
        this.receiveteachername = receiveteachername;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getGradename() {
        return gradename;
    }

    public void setGradename(String gradename) {
        this.gradename = gradename;
    }

    public Object getResnum() {
        return resnum;
    }

    public void setResnum(Object resnum) {
        this.resnum = resnum;
    }

    public Integer getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(Integer subjectid) {
        this.subjectid = subjectid;
    }

    public Integer getGradeid() {
        return gradeid;
    }

    public void setGradeid(Integer gradeid) {
        this.gradeid = gradeid;
    }

    public boolean isClasstimetype() {
        return classtimetype;
    }

    public void setClasstimetype(boolean classtimetype) {
        this.classtimetype = classtimetype;
    }

    public boolean isBeginClass() {
        return beginClass;
    }

    public void setBeginClass(boolean beginClass) {
        this.beginClass = beginClass;
    }

    public String getTermid() {
        return termid;
    }

    public void setTermid(String termid) {
        this.termid = termid;
    }

    public String getMaterialidvalues() {
        return materialidvalues;
    }

    public void setMaterialidvalues(String materialidvalues) {
        this.materialidvalues = materialidvalues;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public java.lang.String getSchoolname(){
        return schoolname;
    }
    public void setSchoolname(java.lang.String schoolname){
        this.schoolname = schoolname;
    }
    public void setCtime(java.sql.Timestamp ctime){
        this.ctime = ctime;
    }

    public java.lang.String getTeachername(){
        return teachername;
    }
    public void setTeachername(java.lang.String teachername){
        this.teachername = teachername;
    }
    public java.lang.Integer getCloudstatus(){
        return cloudstatus;
    }
    public void setCloudstatus(java.lang.Integer cloudstatus){
        this.cloudstatus = cloudstatus;
    }
    public java.lang.Integer getCoursestatus(){
        return coursestatus;
    }
    public void setCoursestatus(java.lang.Integer coursestatus){
        this.coursestatus = coursestatus;
    }

    public Date getCtime() {
        return ctime;
    }
    public String getCtimestring(){
        if(this.ctime==null)
            return "";
        return UtilTool.DateConvertToString(ctime, UtilTool.DateType.type1);
    }
    public String getMtimestring(){
        if(this.mtime==null)
            return "";
        return UtilTool.DateConvertToString(mtime, UtilTool.DateType.type1);
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public void setMtime(java.sql.Timestamp mtime){
        this.mtime = mtime;
    }

    public Integer getCuserid() {
        return cuserid;
    }

    public void setCuserid(Integer cuserid) {
        this.cuserid = cuserid;
    }

    public java.lang.Integer getLocalstatus(){
        return localstatus;
    }
    public void setLocalstatus(java.lang.Integer localstatus){
        this.localstatus = localstatus;
    }

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }

    public java.lang.Integer getSharetype(){
        return sharetype;
    }
    public void setSharetype(java.lang.Integer sharetype){
        this.sharetype = sharetype;
    }
    public java.lang.Integer getCourselevel(){
        return courselevel;
    }
    public void setCourselevel(java.lang.Integer courselevel){
        this.courselevel = courselevel;
    }
    public java.lang.String getCoursename(){
        return coursename;
    }
    public void setCoursename(java.lang.String coursename){
        this.coursename = coursename;
    }

    public Object getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public Object getClassesid() {
        return classesid;
    }

    public void setClassesid(String classesid) {
        this.classesid = classesid;
    }

    public Object getClasstype() {
        return classtype;
    }

    public void setClasstype(String classtype) {
        this.classtype = classtype;
    }

    public Object getClasstimes() {
        return classtimes;
    }

    public void setClasstimes(String classtimes) {
        this.classtimes = classtimes;
    }

    public Object[] getClassIdArray(){
        if(getClassesid()==null)
            return null;
        return getClassesid().toString().split(",");
    }
    public String[] getClassesNameArray(){
        if(getClasses()==null)
            return null;
        return getClasses().toString().split(",");
    }
    public String[] getClassTypeArray(){
        if(getClasses()==null)
            return null;
        return getClasstype().toString().split(",");
    }
    public String[] getClassTimeArray(){
        if(getClasstimes()==null)
            return null;
        return getClasstimes().toString().split(",");
    }

    public String getVersionvalues() {
        return versionvalues;
    }

    public void setVersionvalues(String versionvalues) {
        this.versionvalues = versionvalues;
    }

    /**
     * 得到集合
     * @return
     */
    public List<Map<String, Object>> getClassEntity(){
        Object[] classidArray=this.getClassIdArray();
        String[] classnameArray=this.getClassesNameArray();
        String[] classTypeArray=this.getClassTypeArray();
        String[] classTimeArray=this.getClassTimeArray();
        List<Map<String, Object>> returnListMap=null;
        if(classidArray!=null
                &&classnameArray!=null
                &&classTypeArray!=null
                &&classidArray.length>0
                &&classidArray.length==classnameArray.length
                &&classidArray.length==classTypeArray.length){
            returnListMap=new ArrayList<Map<String, Object>>();
            for (int i = 0; i < classidArray.length; i++){
                Map<String, Object> tmpMap=new HashMap<String, Object>();
                tmpMap.put("CLASS_ID", classidArray[i]);
                tmpMap.put("CLASS_NAME", classnameArray[i]);
                tmpMap.put("CLASS_TYPE", classTypeArray[i]);
                if(classTimeArray!=null&&classidArray.length==classTimeArray.length){
                    tmpMap.put("CLASS_TIME", classTimeArray[i]);
                    java.text.SimpleDateFormat formatDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date cd = null;
                    try {
                        cd = formatDate.parse(classTimeArray[i]);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(cd!=null&&cd.after(new Date(System.currentTimeMillis())))
                        tmpMap.put("HANDLE_FLAG", true);
                    else{
                        tmpMap.put("HANDLE_FLAG", false);
                        this.beginClass = false;
                    }
                    if(i!=0 && !classTimeArray[i].equals(classTimeArray[i-1])){
                        this.classtimetype = false;
                    }
                }
                returnListMap.add(tmpMap);
            }
        }
        return returnListMap;
    }

    // 专题来源类型   1：四中标准库资源  2:四中共享资源  3： 校内共享的
    public Integer getSourceType(){
        int type=0;
        if(this.courselevel==1)
            type=1;
        if(this.courselevel==2)
            type=2;
        if(this.courselevel==3&&this.sharetype==1)
            type=3;
        return type;
    }
}