package com.school.entity;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class ClassInfo implements java.io.Serializable {
    private String type;
    private Date ctime;
    private Date mtime;
    private Integer dctype; //1:学校班级，2：网校班级，3：爱学课堂
    private String classname;
    private Integer classid;
    private String pattern;
    private String year;
    private String classgrade;
    private Object num;  //班级人数
    private Integer islike; //是否模糊查询
    private SubjectInfo subjectinfo;
    private Integer isflag; //是否启用：  1：启用   2：禁用
    private String lzxclassid;
    private Integer allowjoin;  //学生是否允许加入 1：允许 2：不允许
    private Integer clsnum; //班级限额
    private Date verifytime;    //失效时间
    private Integer gradeid;
    private String subjectstr;
    private String invitecode;
    private Integer cuserid;  //创建人
    private String imvaldatecode;//邀请码 im1.1.3中用到
    private Integer dynamicCount;

    private Integer searchUid;  //根据c_user_id查询

    private Integer activitytype;
    private Integer termid;

    private String begintime;//管理员查询班级任务统计用的开始时间
    private String endtime;//管理员查询班级任务统计用的结束时间

    public String getBegintime() {
        return begintime;
    }

    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Integer getActivitytype() {
        return activitytype;
    }

    public void setActivitytype(Integer activitytype) {
        this.activitytype = activitytype;
    }

    public Integer getTermid() {
        return termid;
    }

    public void setTermid(Integer termid) {
        this.termid = termid;
    }

    public Integer getSearchUid() {
        return searchUid;
    }

    public void setSearchUid(Integer searchUid) {
        this.searchUid = searchUid;
    }

    public Integer getDynamicCount() {
        return dynamicCount;
    }

    public void setDynamicCount(Integer dynamicCount) {
        this.dynamicCount = dynamicCount;
    }

    public String getImvaldatecode() {
        return imvaldatecode;
    }

    public void setImvaldatecode(String imvaldatecode) {
        this.imvaldatecode = imvaldatecode;
    }

    public Integer getCuserid() {
        return cuserid;
    }
    public void setCuserid(Integer cuserid) {
        this.cuserid = cuserid;
    }
    public String getInvitecode() {
        return invitecode;
    }
    public void setInvitecode(String invitecode) {
        this.invitecode = invitecode;
    }

    public String getSubjectstr() {
        return subjectstr;
    }

    public void setSubjectstr(String subjectstr) {
        this.subjectstr = subjectstr;
    }

    public Integer getGradeid() {
        return gradeid;
    }

    public void setGradeid(Integer gradeid) {
        this.gradeid = gradeid;
    }

    public Integer getClsnum() {
        return clsnum;
    }

    public void setClsnum(Integer clsnum) {
        this.clsnum = clsnum;
    }

    public Date getVerifytime() {
        return verifytime;
    }

    public void setVerifytime(Date verifytime) {
        this.verifytime = verifytime;
    }

    public String getVerifyTimeString(){
        if(verifytime==null)
            return null;
        return UtilTool.DateConvertToString(verifytime, DateType.type1);
    }


    public Integer getAllowjoin() {
        return allowjoin;
    }

    public void setAllowjoin(Integer allowjoin) {
        this.allowjoin = allowjoin;
    }

    public Integer getDctype() {
        return dctype;
    }

    public void setDctype(Integer dctype) {
        this.dctype = dctype;
    }

    public Integer getDcschoolid() {
        return dcschoolid;
    }

    public void setDcschoolid(Integer dcschoolid) {
        this.dcschoolid = dcschoolid;
    }

    private Integer dcschoolid;

    private String classyearname;

    public String getLzxclassid() {
        return lzxclassid;
    }

    public void setLzxclassid(String lzxclassid) {
        this.lzxclassid = lzxclassid;
    }

    public Integer getIsflag() {
        return isflag;
    }
    public void setIsflag(Integer isflag) {
        this.isflag = isflag;
    }
    public String getClassyearname() {
        return classyearname;
    }
    public void setClassyearname(String classyearname) {
        this.classyearname = classyearname;
    }
    public SubjectInfo getSubjectinfo() {
        return (subjectinfo=(subjectinfo==null?new SubjectInfo():subjectinfo));
    }
    public void setSubjectinfo(SubjectInfo subjectinfo) {
        this.subjectinfo = subjectinfo;
    }
    public Integer getSubjectid() {
        return getSubjectinfo().getSubjectid();
    }
    public void setSubjectid(Integer subjectid) {
        getSubjectinfo().setSubjectid(subjectid);
    }
    public String getSubjectname() {
        return getSubjectinfo().getSubjectname();
    }
    public void setSubjectname(String subjectname) {
        getSubjectinfo().setSubjectname(subjectname);
    }
    public Integer getIslike() {
        return islike;
    }
    public void setIslike(Integer islike) {
        this.islike = islike;
    }
    private String userid;


    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Date getCtime() {
        return ctime;
    }
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
    public String getCtimeString(){
        if(ctime==null)
            return null;
        return UtilTool.DateConvertToString(ctime, DateType.type1);
    }
    public Date getMtime() {
        return mtime;
    }
    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }
    public String getClassname() {
        return classname;
    }
    public void setClassname(String classname) {
        this.classname = classname;
    }
    public Integer getClassid() {
        return classid;
    }
    public void setClassid(Integer classid) {
        this.classid = classid;
    }
    public String getPattern() {
        return pattern;
    }
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getClassgrade() {
        return classgrade;
    }
    public void setClassgrade(String classgrade) {
        this.classgrade = classgrade;
    }

    public Object getNum() {
        return num;
    }
    public void setNum(Object num) {
        this.num = num;
    }
}