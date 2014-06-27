package  com.school.entity.teachpaltform.paper;

public class TpCoursePaper {

	public void TpCoursePaper (){}
   
    private java.lang.Long paperid;
    private java.lang.Integer ref;
    private java.lang.Long courseid;
    private String papername;

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