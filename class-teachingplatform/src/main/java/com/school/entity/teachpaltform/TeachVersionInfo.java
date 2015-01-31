package  com.school.entity.teachpaltform;

import java.util.Date;

public class TeachVersionInfo{

    public void TeachVersionInfo(){}

    private Date ctime;
    private java.lang.String versionname;
    private java.lang.Integer versionid;
    private java.lang.String cuserid;
    private java.lang.String remark;

    private String abbreviation;//¼ò³Æ

    public Date getCtime(){
        return ctime;
    }
    public void setCtime(Date ctime){
        this.ctime = ctime;
    }
    public java.lang.String getVersionname(){
        return versionname;
    }
    public void setVersionname(java.lang.String versionname){
        this.versionname = versionname;
    }
    public java.lang.Integer getVersionid(){
        return versionid;
    }
    public void setVersionid(java.lang.Integer versionid){
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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}