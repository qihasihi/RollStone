package com.school.entity.resource.score;

public class UserScoreRank  implements java.io.Serializable{

	public void UserScoreRank (){}
   
    private java.lang.Float score;
    private java.lang.String schoolname;
    private java.lang.Integer operatetype;
    private java.lang.Long rank;
    private java.lang.Long schoolid;
    private java.lang.String userrealname;
    private java.lang.Long ref;
    private java.util.Date ctime;
    private java.lang.Long typeid;
    private java.lang.Long userid;
    private java.lang.Long modelid;

    public java.lang.Float getScore(){
      return score;
    }
    public void setScore(java.lang.Float score){
      this.score = score;
    }
    public java.lang.String getSchoolname(){
      return schoolname;
    }
    public void setSchoolname(java.lang.String schoolname){
      this.schoolname = schoolname;
    }
    public java.lang.Integer getOperatetype(){
      return operatetype;
    }
    public void setOperatetype(java.lang.Integer operatetype){
      this.operatetype = operatetype;
    }
    public java.lang.Long getRank(){
      return rank;
    }
    public void setRank(java.lang.Long rank){
      this.rank = rank;
    }
    public java.lang.Long getSchoolid(){
      return schoolid;
    }
    public void setSchoolid(java.lang.Long schoolid){
      this.schoolid = schoolid;
    }
    public java.lang.String getUserrealname(){
      return userrealname;
    }
    public void setUserrealname(java.lang.String userrealname){
      this.userrealname = userrealname;
    }
    public java.lang.Long getRef(){
      return ref;
    }
    public void setRef(java.lang.Long ref){
      this.ref = ref;
    }
    public java.util.Date getCtime(){
      return ctime;
    }
    public void setCtime(java.util.Date ctime){
      this.ctime = ctime;
    }
    public java.lang.Long getTypeid(){
      return typeid;
    }
    public void setTypeid(java.lang.Long typeid){
      this.typeid = typeid;
    }
    public java.lang.Long getUserid(){
      return userid;
    }
    public void setUserid(java.lang.Long userid){
      this.userid = userid;
    }
    public java.lang.Long getModelid(){
      return modelid;
    }
    public void setModelid(java.lang.Long modelid){
      this.modelid = modelid;
    }
  

}