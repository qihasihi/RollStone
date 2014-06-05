package  com.school.entity.teachpaltform;

import com.school.entity.ClassInfo;

import java.io.Serializable;
import java.util.Date;

public class TrusteeShipClass implements Serializable {

	public void TrusteeShipClass (){}
   
    private java.lang.Integer receiveteacherid;
    private java.lang.String receiveteachername;
    private java.lang.Integer ref;
    private java.util.Date ctime;
    private java.util.Date mtime;
    private java.lang.Integer trustteacherid;
    private java.lang.String trustteachername;
    private java.lang.Integer isaccept;
    private java.lang.Integer trustclassid;
    private java.lang.Integer trustclasstype;
    private ClassInfo classinfo;

    public ClassInfo getClassinfo() {
        if(classinfo==null)
            classinfo = new ClassInfo();
        return classinfo;
    }

    public void setClassinfo(ClassInfo classinfo) {
        this.classinfo = classinfo;
    }

    public void setClassname(String classname) {
        this.getClassinfo().setClassname(classname);
    }

    public Integer getTrustclasstype() {
        return trustclasstype;
    }

    public void setTrustclasstype(Integer trustclasstype) {
        this.trustclasstype = trustclasstype;
    }

    public String getClassname() {
        return this.getClassinfo().getClassname();
    }

    public void setClassgrade(String classgrade) {
        this.getClassinfo().setClassgrade(classgrade);
    }

    public String getClassgrade() {
        return this.getClassinfo().getClassgrade();
    }

    public void setYear(String year) {
        this.getClassinfo().setYear(year);
    }

    public String getYear() {
        return this.getClassinfo().getYear();
    }

    public String getTrustteachername() {
        return trustteachername;
    }

    public void setTrustteachername(String trustteachername) {
        this.trustteachername = trustteachername;
    }

    public String getReceiveteachername() {
        return receiveteachername;
    }

    public void setReceiveteachername(String receiveteachername) {
        this.receiveteachername = receiveteachername;
    }

    public Integer getReceiveteacherid() {
        return receiveteacherid;
    }

    public void setReceiveteacherid(Integer receiveteacherid) {
        this.receiveteacherid = receiveteacherid;
    }

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public Integer getTrustteacherid() {
        return trustteacherid;
    }

    public void setTrustteacherid(Integer trustteacherid) {
        this.trustteacherid = trustteacherid;
    }

    public Integer getIsaccept() {
        return isaccept;
    }

    public void setIsaccept(Integer isaccept) {
        this.isaccept = isaccept;
    }

    public Integer getTrustclassid() {
        return trustclassid;
    }

    public void setTrustclassid(Integer trustclassid) {
        this.trustclassid = trustclassid;
    }
}