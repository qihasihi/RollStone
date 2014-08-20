package  com.school.entity.teachpaltform.paper;

import java.util.Date;

public class StuViewMicVideoLog {

	public void StuViewMicVideoLog (){}
   
    private java.lang.Long micvideoid;
    private java.lang.Integer ref;
    private Date ctime;
    private java.lang.Integer userid;
    private Long taskid;

    public Long getTaskid() {
        return taskid;
    }

    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }

    public java.lang.Long getMicvideoid(){
      return micvideoid;
    }
    public void setMicvideoid(java.lang.Long micvideoid){
      this.micvideoid = micvideoid;
    }
    public java.lang.Integer getRef(){
      return ref;
    }
    public void setRef(java.lang.Integer ref){
      this.ref = ref;
    }
    public Date getCtime(){
      return ctime;
    }
    public void setCtime(Date ctime){
      this.ctime = ctime;
    }
    public java.lang.Integer getUserid(){
      return userid;
    }
    public void setUserid(java.lang.Integer userid){
      this.userid = userid;
    }
  

}