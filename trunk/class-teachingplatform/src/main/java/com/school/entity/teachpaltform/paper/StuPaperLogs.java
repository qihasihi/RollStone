package  com.school.entity.teachpaltform.paper;

public class StuPaperLogs {

	public void StuPaperLogs (){}
   
    private java.lang.Long paperid;
    private java.sql.Timestamp ctime;
    private java.lang.Integer userid;
    private java.lang.Long ref;
    private Float score;
    private Integer ismarking;
    private String stuname;
    private Integer isinpaper;

    public Integer getIsinpaper() {
        return isinpaper;
    }

    public void setIsinpaper(Integer isinpaper) {
        this.isinpaper = isinpaper;
    }

    public Integer getIsmarking() {
        return ismarking;
    }

    public void setIsmarking(Integer ismarking) {
        this.ismarking = ismarking;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public java.lang.Long getPaperid(){
      return paperid;
    }
    public void setPaperid(java.lang.Long paperid){
      this.paperid = paperid;
    }
    public java.sql.Timestamp getCtime(){
      return ctime;
    }
    public void setCtime(java.sql.Timestamp ctime){
      this.ctime = ctime;
    }
    public java.lang.Integer getUserid(){
      return userid;
    }
    public void setUserid(java.lang.Integer userid){
      this.userid = userid;
    }
    public java.lang.Long getRef(){
      return ref;
    }
    public void setRef(java.lang.Long ref){
      this.ref = ref;
    }
    public java.lang.Float getScore(){
      return score;
    }
    public void setScore(java.lang.Float score){
      this.score = score;
    }
  

}