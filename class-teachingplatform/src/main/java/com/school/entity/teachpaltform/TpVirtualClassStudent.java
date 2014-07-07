package  com.school.entity.teachpaltform;

import java.io.Serializable;
import java.util.Date;

public class TpVirtualClassStudent implements Serializable {

	public void TpVirtualClassStudent (){}
   
    private java.lang.Integer virtualclassid;
    private java.lang.Integer ref;
    private java.util.Date ctime;
    private java.util.Date mtime;
    private java.lang.Integer userid;
    private java.lang.Integer cuserid;
    private Integer stateid;
    private Integer iscomplete;

    public Integer getIscomplete() {
        return iscomplete;
    }

    public void setIscomplete(Integer iscomplete) {
        this.iscomplete = iscomplete;
    }

    private java.lang.String stuname;
    private java.lang.String stuno;
    private java.lang.String virtualclassname;
    private  Object completenum;

    public Integer getStateid() {
        return stateid;
    }

    public void setStateid(Integer stateid) {
        this.stateid = stateid;
    }


    public Object getCompletenum() {
        return completenum;
    }

    public void setCompletenum(Object completenum) {
        this.completenum = completenum;
    }

    public String getStuno() {
        return stuno;
    }

    public void setStuno(String stuno) {
        this.stuno = stuno;
    }

    public String getVirtualclassname() {
        return virtualclassname;
    }

    public void setVirtualclassname(String virtualclassname) {
        this.virtualclassname = virtualclassname;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public java.lang.Integer getVirtualclassid(){
      return virtualclassid;
    }
    public void setVirtualclassid(java.lang.Integer virtualclassid){
      this.virtualclassid = virtualclassid;
    }
    public java.lang.Integer getRef(){
      return ref;
    }
    public void setRef(java.lang.Integer ref){
      this.ref = ref;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getCuserid() {
        return cuserid;
    }

    public void setCuserid(Integer cuserid) {
        this.cuserid = cuserid;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }
}