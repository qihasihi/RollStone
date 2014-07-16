package  com.school.entity.teachpaltform;

public class SynchroLogInfo {

	public void SynchroLogInfo (){}
   
    private java.lang.Integer errortype;
    private java.lang.Integer ref;
    private java.sql.Timestamp ctime;
    private java.lang.String errormsg;
    private java.lang.String cuserid;
    private java.lang.Integer intertype;

    public java.lang.Integer getErrortype(){
      return errortype;
    }
    public void setErrortype(java.lang.Integer errortype){
      this.errortype = errortype;
    }
    public java.lang.Integer getRef(){
      return ref;
    }
    public void setRef(java.lang.Integer ref){
      this.ref = ref;
    }
    public java.sql.Timestamp getCtime(){
      return ctime;
    }
    public void setCtime(java.sql.Timestamp ctime){
      this.ctime = ctime;
    }
    public java.lang.String getErrormsg(){
      return errormsg;
    }
    public void setErrormsg(java.lang.String errormsg){
      this.errormsg = errormsg;
    }
    public java.lang.String getCuserid(){
      return cuserid;
    }
    public void setCuserid(java.lang.String cuserid){
      this.cuserid = cuserid;
    }
    public java.lang.Integer getIntertype(){
      return intertype;
    }
    public void setIntertype(java.lang.Integer intertype){
      this.intertype = intertype;
    }
  

}