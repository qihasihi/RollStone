package  com.school.entity.teachpaltform.paper;

import com.school.entity.teachpaltform.TpCourseInfo;

public class TpCoursePaper {

	public void TpCoursePaper (){}
   
    private java.lang.Long paperid;
    private java.lang.Integer ref;
    private TpCourseInfo courseinfo;
    private Long filtercourseid;    //过滤掉的专题ID

    public Long getFiltercourseid() {
        return filtercourseid;
    }

    public void setFiltercourseid(Long filtercourseid) {
        this.filtercourseid = filtercourseid;
    }

    public TpCourseInfo getCourseinfo() {
        if(courseinfo==null)
            courseinfo=new TpCourseInfo();
        return courseinfo;
    }

    public void setCourseinfo(TpCourseInfo courseinfo) {
        this.courseinfo = courseinfo;
    }

    private String papername;
    private Integer localstatus;
    private Object subjectivenum; //主观题数量
    private Object objectivenum; //客观题数量
    private Object taskflag;    //任务状态

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
    public java.lang.Long getCourseid(){
      return this.getCourseinfo().getCourseid();
    }
    public void setCourseid(java.lang.Long courseid){
      this.getCourseinfo().setCourseid(courseid);
    }
    public Integer getMaterialid(){
        return this.getCourseinfo().getMaterialid();
    }
    public void setMaterialid(Integer materialid){
        this.getCourseinfo().setMaterialid(materialid);
    }
    public Integer getGradeid(){
        return this.getCourseinfo().getGradeid();
    }
    public void setGradeid(Integer gradeid){
        this.getCourseinfo().setGradeid(gradeid);
    }
    public Integer getSubjectid(){
        return this.getCourseinfo().getSubjectid();
    }
    public void setSubjectid(Integer subjectid){
        this.getCourseinfo().setSubjectid(subjectid);
    }
    public String getCoursename(){
        return this.getCourseinfo().getCoursename();
    }
    public void setCoursename(String coursename){
        this.getCourseinfo().setCoursename(coursename);
    }



}