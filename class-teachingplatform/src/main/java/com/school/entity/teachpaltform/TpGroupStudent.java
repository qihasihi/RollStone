package  com.school.entity.teachpaltform;

import com.school.util.UtilTool;

import java.util.Date;

public class TpGroupStudent {

	public void TpGroupStudent (){}
   
    private java.lang.Integer ref;
    private java.lang.Integer userid;
    private TpGroupInfo tpgroupinfo;
    private java.util.Date ctime;
    private java.util.Date mtime;
    private java.lang.Integer isleader;
    private String stuname;
    private String stuno;
    private Object completenum;
    private Integer stateid;
    private String password;
    private Integer ettuserid;
    private Integer subjectid;

    public Integer getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(Integer subjectid) {
        this.subjectid = subjectid;
    }

    public Integer getEttuserid() {
        return ettuserid;
    }

    public void setEttuserid(Integer ettuserid) {
        this.ettuserid = ettuserid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGroupname(){
        return this.getGroupinfo().getGroupname();
    }
    public void setGroupname(String groupname){
        this.getGroupinfo().setGroupname(groupname);
    }

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

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public String getStuno() {
        return stuno;
    }

    public void setStuno(String stuno) {
        this.stuno = stuno;
    }

    public Integer getIsleader() {
        return isleader;
    }

    public void setIsleader(Integer isleader) {
        this.isleader = isleader;
    }

    public TpGroupInfo getTpgroupinfo() {
        return tpgroupinfo=(tpgroupinfo==null?new TpGroupInfo():tpgroupinfo);
    }

    public void setTpgroupinfo(TpGroupInfo tpgroupinfo) {
        this.tpgroupinfo = tpgroupinfo;
    }

    public java.lang.Integer getRef(){
      return ref;
    }
    public void setRef(java.lang.Integer ref){
        this.ref = ref;
    }

    public void setClassid(java.lang.Integer classid){
        this.getGroupinfo().setClassid(classid);
    }

    public Integer getClassid(){
        return this.getGroupinfo().getClassid();
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public Date getCtime() {
        return ctime;
    }
    public String getCtimestring(){
        if(this.ctime==null)
            return null;
        return UtilTool.DateConvertToString(this.ctime, UtilTool.DateType.type1);
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

    public Long getGroupid() {
        return getGroupinfo().getGroupid();
    }

    public void setGroupid(Long groupid) {
        this.getGroupinfo().setGroupid(groupid);
    }

    public TpGroupInfo getGroupinfo() {
        if(tpgroupinfo==null)
            tpgroupinfo=new TpGroupInfo();
        return tpgroupinfo;
    }
    public void setTpGroupinfo(TpGroupInfo groupinfo) {
        this.tpgroupinfo = groupinfo;
    }
}