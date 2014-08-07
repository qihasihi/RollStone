package  com.school.entity.teachpaltform.paper;

import com.school.entity.teachpaltform.TpCourseInfo;
import com.school.util.UtilTool;

import java.util.Date;

public class TpCoursePaper {

	public void TpCoursePaper (){}
   
    private java.lang.Long paperid;
    private java.lang.Integer ref;

    private Long filtercourseid;    //过滤掉的专题ID

    public Long getFiltercourseid() {
        return filtercourseid;
    }

    public void setFiltercourseid(Long filtercourseid) {
        this.filtercourseid = filtercourseid;
    }


    private String papername;
    private Integer localstatus;
    private Object subjectivenum; //主观题数量
    private Object objectivenum; //客观题数量
    private Integer quesnum;
    private Object taskflag;    //任务状态
    private Float score;
    private Integer selecttype; //4:成卷 5:自主测试 6:微视频

    private Integer papertype;  //paperinfo papertype
    private Integer seldatetype;    //查询用 时间条件
    private String coursepapername;

    public String getCoursepapername() {
        return coursepapername;
    }

    public void setCoursepapername(String coursepapername) {
        this.coursepapername = coursepapername;
    }

    public Integer getSeldatetype() {
        return seldatetype;
    }

    public void setSeldatetype(Integer seldatetype) {
        this.seldatetype = seldatetype;
    }

    public Integer getPapertype() {
        return papertype;
    }

    public void setPapertype(Integer papertype) {
        this.papertype = papertype;
    }

    public Integer getQuesnum() {
        return quesnum;
    }

    public void setQuesnum(Integer quesnum) {
        this.quesnum = quesnum;
    }

    public Integer getSelecttype() {
        return selecttype;
    }

    public void setSelecttype(Integer selecttype) {
        this.selecttype = selecttype;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Object getTaskflag() {
        return taskflag;
    }

    public void setTaskflag(Object taskflag) {
        this.taskflag = taskflag;
    }

    public Object getObjectivenum() {
        return objectivenum;
    }

    public void setObjectivenum(Object objectivenum) {
        this.objectivenum = objectivenum;
    }

    public Object getSubjectivenum() {
        return subjectivenum;
    }

    public void setSubjectivenum(Object subjectivenum) {
        this.subjectivenum = subjectivenum;
    }



    public Integer getLocalstatus() {
        return localstatus;
    }

    public void setLocalstatus(Integer localstatus) {
        this.localstatus = localstatus;
    }

    public String getPapername() {
        return papername;
    }

    public void setPapername(String papername) {
        this.papername = papername;
    }

    public java.lang.Long getPaperid(){
      return paperid;
    }
    public void setPaperid(java.lang.Long paperid){
      this.paperid = paperid;
    }
    public java.lang.Integer getRef(){
      return ref;
    }
    public void setRef(java.lang.Integer ref){
      this.ref = ref;
    }
    private Long courseid;
    private Integer gradeid;
    private Integer materialid;
    private Integer subjectid;
    private String  coursename;

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
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

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    private Date ctime;
    private Date mtime;

    public Date getCtime() {
        return ctime;
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
    public String getMtimeString(){
        if(mtime==null)
            return "";
        return UtilTool.DateConvertToString(mtime, UtilTool.DateType.type1);
    }
}