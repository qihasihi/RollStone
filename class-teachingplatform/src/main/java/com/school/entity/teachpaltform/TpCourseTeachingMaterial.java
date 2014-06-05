package com.school.entity.teachpaltform;

public class TpCourseTeachingMaterial  implements java.io.Serializable{

	public void TpCourseTeachingMaterial (){}
   
    private java.lang.Integer teachingmaterialid;
    private java.lang.Long courseid;
    private java.lang.Long ref;
    private java.util.Date ctime;
    private String materialname;
    private Integer subjectid;
    private Integer gradeid;
    private String versionname;

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public Integer getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(Integer subjectid) {
        this.subjectid = subjectid;
    }

    public Integer getGradeid() {
        return gradeid;
    }

    public void setGradeid(Integer gradeid) {
        this.gradeid = gradeid;
    }

    public String getMaterialname() {
        return materialname;
    }

    public void setMaterialname(String materialname) {
        this.materialname = materialname;
    }

    public java.lang.Integer getTeachingmaterialid(){
      return teachingmaterialid;
    }
    public void setTeachingmaterialid(java.lang.Integer teachingmaterialid){
      this.teachingmaterialid = teachingmaterialid;
    }
    public java.lang.Long getCourseid(){
      return courseid;
    }
    public void setCourseid(java.lang.Long courseid){
      this.courseid = courseid;
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
  

}