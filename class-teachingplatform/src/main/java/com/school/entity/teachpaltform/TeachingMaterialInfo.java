package  com.school.entity.teachpaltform;

import java.util.Date;

public class TeachingMaterialInfo implements java.io.Serializable{

	public void TeachingMaterialInfo (){}
   
    private java.lang.Integer gradeid;
    private java.lang.String materialname;
    private java.lang.Integer subjectid;
    private Date ctime;
    private java.lang.Integer versionid;
    private java.lang.String cuserid;
    private java.lang.Integer materialid;
    private java.lang.String remark;
    private String subjectname;
    private String versionname;
    private String gradename;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGradename() {
        return gradename;
    }

    public void setGradename(String gradename) {
        this.gradename = gradename;
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public java.lang.Integer getGradeid(){
      return gradeid;
    }
    public void setGradeid(java.lang.Integer gradeid){
      this.gradeid = gradeid;
    }


    public String getMaterialname() {
        return materialname;
    }

    public void setMaterialname(String materialname) {
        this.materialname = materialname;
    }

    public Integer getMaterialid() {
        return materialid;
    }

    public void setMaterialid(Integer materialid) {
        this.materialid = materialid;
    }

    public java.lang.Integer getSubjectid(){
      return subjectid;
    }
    public void setSubjectid(java.lang.Integer subjectid){
      this.subjectid = subjectid;
    }
    public Date getCtime(){
      return ctime;
    }
    public void setCtime(Date ctime){
      this.ctime = ctime;
    }
    public java.lang.Integer getVersionid(){
      return versionid;
    }

    public void setVersionid(Integer versionid) {
        this.versionid = versionid;
    }

    public java.lang.String getCuserid(){
      return cuserid;
    }
    public void setCuserid(java.lang.String cuserid){
      this.cuserid = cuserid;
    }

    public java.lang.String getRemark(){
      return remark;
    }
    public void setRemark(java.lang.String remark){
      this.remark = remark;
    }
  

}