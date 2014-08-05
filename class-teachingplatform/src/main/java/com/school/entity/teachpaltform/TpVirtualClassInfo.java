package  com.school.entity.teachpaltform;

import java.io.Serializable;

public class TpVirtualClassInfo implements Serializable {

	public void TpVirtualClassInfo (){}
   
    private java.lang.Integer virtualclassid;
    private java.sql.Timestamp ctime;
    private java.lang.String virtualclassname;
    private java.lang.Integer cuserid;
    private java.lang.Integer status;

    public Integer getDcschoolid() {
        return dcschoolid;
    }

    public void setDcschoolid(Integer dcschoolid) {
        this.dcschoolid = dcschoolid;
    }

    private Integer dcschoolid;

    public java.lang.Integer getVirtualclassid(){
      return virtualclassid;
    }
    public void setVirtualclassid(java.lang.Integer virtualclassid){
      this.virtualclassid = virtualclassid;
    }
    public java.sql.Timestamp getCtime(){
      return ctime;
    }
    public void setCtime(java.sql.Timestamp ctime){
      this.ctime = ctime;
    }
    public java.lang.String getVirtualclassname(){
      return virtualclassname;
    }
    public void setVirtualclassname(java.lang.String virtualclassname){
      this.virtualclassname = virtualclassname;
    }

    public Integer getCuserid() {
        return cuserid;
    }

    public void setCuserid(Integer cuserid) {
        this.cuserid = cuserid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}