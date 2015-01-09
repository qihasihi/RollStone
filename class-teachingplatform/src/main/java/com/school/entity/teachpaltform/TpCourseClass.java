package  com.school.entity.teachpaltform;

import com.school.util.UtilTool;

import java.util.Date;

public class TpCourseClass {

	public void TpCourseClass (){}

    private java.util.Date begintime;
    private java.util.Date endtime;

    public Integer getDctype() {
        return dctype;
    }

    public void setDctype(Integer dctype) {
        this.dctype = dctype;
    }

    private java.lang.Integer ref;
    private java.util.Date ctime;
    private java.lang.Integer classid;
    private java.lang.Integer classtype;
    private java.lang.Integer userid;
    private Long courseid;
    private String termid;
    private java.lang.Integer gradeid;
    private java.lang.Integer subjectid;

    private String teachername;
    private String coursename;
    private String materialname;
    private String classname;

    private String subjectname;
    private String gradename;

    private String classgrade;

    private Long quoteid;
    private Integer cuserid;
    private Integer dctype;

    public Integer getCuserid() {
        return cuserid;
    }

    public void setCuserid(Integer cuserid) {
        this.cuserid = cuserid;
    }

    public Integer getCourselevel() {
        return courselevel;
    }

    public void setCourselevel(Integer courselevel) {
        this.courselevel = courselevel;
    }

    private Integer courselevel;



    public String getSubjectname() {
        return subjectname;
    }

    public Long getQuoteid() {
        return quoteid;
    }

    public void setQuoteid(Long quoteid) {
        this.quoteid = quoteid;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public String getGradename() {
        return gradename;
    }

    public void setGradename(String gradename) {
        this.gradename = gradename;
    }

    public String getMaterialname() {
        return materialname;
    }

    public void setMaterialname(String materialname) {
        this.materialname = materialname;
    }

    public String getTeachername() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getClassgrade() {
        return classgrade;
    }

    public void setClassgrade(String classgrade) {
        this.classgrade = classgrade;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getTermid() {
        return termid;
    }

    public void setTermid(String termid) {
        this.termid = termid;
    }

    public Integer getGradeid() {
        return gradeid;
    }

    public void setGradeid(Integer gradeid) {
        this.gradeid = gradeid;
    }

    public Integer getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(Integer subjectid) {
        this.subjectid = subjectid;
    }

    public java.util.Date getBegintime(){
      return begintime;
    }
    public void setBegintime(java.util.Date begintime){
      this.begintime = begintime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public java.lang.Integer getRef(){
      return ref;
    }
    public void setRef(java.lang.Integer ref){
      this.ref = ref;
    }
    public java.util.Date getCtime(){
      return ctime;
    }
    public void setCtime(java.util.Date ctime){
      this.ctime = ctime;
    }
    public java.lang.Integer getClassid(){
      return classid;
    }
    public void setClassid(java.lang.Integer classid){
      this.classid = classid;
    }
    public java.lang.Integer getClasstype(){
      return classtype;
    }
    public void setClasstype(java.lang.Integer classtype){
      this.classtype = classtype;
    }
    public java.lang.Integer getUserid(){
      return userid;
    }
    public void setUserid(java.lang.Integer userid){
      this.userid = userid;
    }
    public Long getCourseid(){
      return courseid;
    }
    public void setCourseid(Long courseid){
      this.courseid = courseid;
    }

    public String getCtimeString() {
        return (ctime == null ? "" : UtilTool.DateConvertToString(ctime,
                UtilTool.DateType.type1));
    }

    public String getBegintimeString() {
        return (begintime == null ? "" : UtilTool.DateConvertToString(begintime,
                UtilTool.DateType.type1));
    }
    public String getEndtimeString() {
        return (endtime == null ? "" : UtilTool.DateConvertToString(endtime,
                UtilTool.DateType.type1));
    }
}