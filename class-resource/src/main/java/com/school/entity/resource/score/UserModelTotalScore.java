package com.school.entity.resource.score;

public class UserModelTotalScore  implements java.io.Serializable{

	public void UserModelTotalScore (){}
   
    private java.lang.Integer ref;
    private java.lang.Integer modelid;
    private java.util.Date mtime;
    private java.lang.Integer userid;
    private java.lang.Integer totalscore;
    private java.util.Date ctime;

    public java.lang.Integer getRef(){
      return ref;
    }
    public void setRef(java.lang.Integer ref){
      this.ref = ref;
    }
    public java.lang.Integer getModelid(){
      return modelid;
    }
    public void setModelid(java.lang.Integer modelid){
      this.modelid = modelid;
    }
    public java.util.Date getMtime(){
      return mtime;
    }
    public void setMtime(java.util.Date mtime){
      this.mtime = mtime;
    }
    public java.lang.Integer getUserid(){
      return userid;
    }
    public void setUserid(java.lang.Integer userid){
      this.userid = userid;
    }
    public java.lang.Integer getTotalscore(){
      return totalscore;
    }
    public void setTotalscore(java.lang.Integer totalscore){
      this.totalscore = totalscore;
    }
    public java.util.Date getCtime(){
      return ctime;
    }
    public void setCtime(java.util.Date ctime){
      this.ctime = ctime;
    }
  

}