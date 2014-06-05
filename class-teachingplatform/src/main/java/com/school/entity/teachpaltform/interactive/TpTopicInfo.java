package com.school.entity.teachpaltform.interactive;

import com.school.util.UtilTool;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity
public class TpTopicInfo implements Serializable{

	public void TpTopicInfo (){}
   
    private java.lang.Integer orderidx;
    private java.lang.String topickeyword;
    private java.util.Date ctime;
    private java.lang.Integer status;
    private java.lang.String topictitle;
    private Integer cuserid;
    private java.lang.String topiccontent;
    private Long courseid;
    private java.lang.Integer cloudstatus;
    private Long topicid;
    private Object flag;
    private Object isPublishTask;
    private Date mtime;

    
    private Long quoteid;

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }
    public String getMtimeString(){
        if(this.mtime==null)return "";
        return UtilTool.DateConvertToString(this.mtime, UtilTool.DateType.type1);
    }

    public Long getQuoteid() {
		return quoteid;
	}

	public void setQuoteid(Long quoteid) {
		this.quoteid = quoteid;
	}

	public Object getIsPublishTask() {
		return isPublishTask;
	}

	public void setIsPublishTask(Object isPublishTask) {
		this.isPublishTask = isPublishTask;
	}

	private Object lastFb;

    public Object getLastFb() {
		return lastFb;
	}

	public void setLastFb(Object lastFb) {
		this.lastFb = lastFb;
	}

	public Object getFlag() {
        return flag;
    }

    public void setFlag(Object flag) {
        this.flag = flag;
    }

    public String getTopictitle25word(){
        String returnWord=this.getTopictitle();
        if(returnWord!=null&&returnWord.trim().length()>25){
            returnWord=returnWord.substring(0,25)+"……";
        }
        return returnWord;
    }
    public java.lang.Integer getOrderidx(){
      return orderidx;
    }
    public void setOrderidx(java.lang.Integer orderidx){
      this.orderidx = orderidx;
    }
    public java.lang.String getTopickeyword(){
      return topickeyword;
    }
    public void setTopickeyword(java.lang.String topickeyword){
      this.topickeyword = topickeyword;
    }
    public java.util.Date getCtime(){
      return ctime;
    }
    public void setCtime(java.util.Date ctime){
      this.ctime = ctime;
    }
    public java.lang.Integer getStatus(){
      return status;
    }
    public void setStatus(java.lang.Integer status){
      this.status = status;
    }
    public java.lang.String getTopictitle(){
      return topictitle;
    }
    public void setTopictitle(java.lang.String topictitle){
      this.topictitle = topictitle;
    }
    public Integer getCuserid(){
      return cuserid;
    }
    public void setCuserid(Integer cuserid){
      this.cuserid = cuserid;
    }
    public java.lang.String getTopiccontent(){
//        if(this.topiccontent!=null){
//            this.setTopiccontent(this.getTopiccontent().replaceAll("ueditor\\/jsp\\/\\.\\.\\/\\.\\.\\/","_SZ_SCHOOL_IMG_PLACEHOLDER_"));
//            this.setTopiccontent(this.getTopiccontent().replaceAll("_SZ_SCHOOL_IMG_PLACEHOLDER_","ueditor/jsp/../../"));
//        }
//
        return topiccontent;
    }
    public void setTopiccontent(java.lang.String topiccontent){


        this.topiccontent = topiccontent;
    }
    public Long getCourseid(){
      return courseid;
    }
    public void setCourseid(Long courseid){
      this.courseid = courseid;
    }
    public java.lang.Integer getCloudstatus(){
      return cloudstatus;
    }
    public void setCloudstatus(java.lang.Integer cloudstatus){
      this.cloudstatus = cloudstatus;
    }
    public Long getTopicid(){
      return topicid;
    }
    public void setTopicid(Long topicid){
      this.topicid = topicid;
    }

    // 主题数量
    private Long themecount;
    //回复数据
    private Long restorecount;
    //创建人
    private String crealname;
    //创建时间（YYYY-MM-DD）
    public String getCtimeShortString(){
        if(this.ctime==null)
            return "";
        return UtilTool.DateConvertToString(this.ctime, UtilTool.DateType.smollDATE);
    }
    //创建时间(YYYY-MM-DD hh24:mi:ss)
    public String getCtimeString(){
    	if(this.ctime==null)
            return "";
        return UtilTool.DateConvertToString(this.ctime, UtilTool.DateType.type1);
    }
    public Long getRestorecount() {
        return restorecount;
    }

    public void setRestorecount(Long restorecount) {
        this.restorecount = restorecount;
    }

    public String getCrealname() {
        return crealname;
    }

    public void setCrealname(String crealname) {
        this.crealname = crealname;
    }

    public Long getThemecount() {
        return themecount;
    }

    public void setThemecount(Long themecount) {
        this.themecount = themecount;
    }

    //查询条件
    private Integer loginuserid;
    public Integer getLoginuserid() {
        return loginuserid;
    }
    public void setLoginuserid(Integer loginuserid) {
        this.loginuserid = loginuserid;
    }
    //查询类型  1:status<>3  没有被删除的  2:
    private Integer selectType;

    public Integer getSelectType() {
        return selectType;
    }

    public void setSelectType(Integer selectType) {
        this.selectType = selectType;
    }

    public void setCtimeString(String ctimeString){
        if(ctimeString!=null){
            this.ctime=UtilTool.StringConvertToDate(ctimeString);
        }
    }
}