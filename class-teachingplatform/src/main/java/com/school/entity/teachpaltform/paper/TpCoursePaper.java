package  com.school.entity.teachpaltform.paper;

public class TpCoursePaper {

	public void TpCoursePaper (){}
   
    private java.lang.Long paperid;
    private java.lang.Integer ref;
    private java.lang.Long courseid;
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
      return courseid;
    }
    public void setCourseid(java.lang.Long courseid){
      this.courseid = courseid;
    }
  

}