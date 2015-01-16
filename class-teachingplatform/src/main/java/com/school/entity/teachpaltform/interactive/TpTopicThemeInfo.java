package com.school.entity.teachpaltform.interactive;

import com.school.util.UtilTool;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class TpTopicThemeInfo  implements Serializable {

    private Integer clsType;
    private Integer clsid;
    private Object searchRoleType;

    public void TpTopicThemeInfo (){}


    private java.lang.Integer viewcount;
    private java.util.Date ctime;
    private Integer cuserid;
    private java.lang.String commentuserid;
    private java.lang.Integer cloudstatus;
    private java.lang.Integer isessence;
    private java.lang.String themetitle;
    private java.lang.String themecontent;
    private java.util.Date mtime;
    private java.util.Date commentmtime;
    private java.lang.String commenttitle;
    private Long themeid;
    private Long courseid;
    private java.lang.Integer istop;
    private Long topicid;
    private java.lang.String commentcontent;
    private Long status;	//引用专题下  1：显示  2：不显示


    private String imattach;
    private Integer imattachtype;
    private Integer sourceid;
    
    
    
    //查询参数
    private String loginuserref;
    private Integer selectType;     //查询类型 NULL OR 1：不查text属性值  2:是查

    //显示参数
    private Long restorecount; //回复数量
    private Long isread;
    private String crealname;
    private String headimage;
    private String lastfabiao;  //最后发表
    private Long quoteid;    //引用的共享，标准ID
    private Long isquote;   //是否被引用了
    private String schoolname;
    
    private Object cfatieshu;	//创建人，发帖数
    private Object cpinglunshu;	//创建人，评论数
    private String cusername;	//创建人的用户名
    private String croleType;
    
    private Object ispraise;	//是否已赞  0:未赞     1：已赞 
    private Long praisecount;
    private Object pinglunshu;


    private String classname;
    private String classgrade;
    private String groupname;

    private String classGroup;

    public String getClassGroup() {
        return classGroup;
    }

    public void setClassGroup(String classGroup) {
        this.classGroup = classGroup;
        if(this.classGroup!=null&&this.classGroup.length()>0){
            String[] infoTmp=this.classGroup.split("\\|");
            if(infoTmp!=null&&infoTmp.length>0){
                if(infoTmp[0]!=null&&infoTmp[0].length()>0)
                    this.setClassgrade(infoTmp[0]);
                if(infoTmp.length>1&&infoTmp[1]!=null&&infoTmp[1].length()>0)
                    this.setClassname(infoTmp[1]);
                if(infoTmp.length>2&&infoTmp[2]!=null&&infoTmp[2].length()>0)
                    this.setGroupname(infoTmp[2]);
            }
        }
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getClassgrade() {
        return classgrade;
    }

    public void setClassgrade(String classgrade) {
        this.classgrade = classgrade;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    /**
     * 得到附件数组
     * @return
     */
    public String[] getImattachArray(){
        if(this.getImattach()!=null){
            if(this.getImattach().indexOf(",")!=-1){
               return this.getImattach().split(",");
            }else{
                return new String[]{this.getImattach()};
            }
        }
        return null;
    }

    public String getImattach() {
        return imattach;
    }

    public void setImattach(String imattach) {
        this.imattach = imattach;
    }

    public Integer getImattachtype() {
        return imattachtype;
    }

    public void setImattachtype(Integer imattachtype) {
        this.imattachtype = imattachtype;
    }

    public Integer getSourceid() {
        return sourceid;
    }

    public void setSourceid(Integer sourceid) {
        this.sourceid = sourceid;
    }

    public Long getPraisecount() {
		return praisecount;
	}

	public void setPraisecount(Long praisecount) {
		this.praisecount = praisecount;
	}

	public Object getIspraise() {
		return ispraise;
	}

	public void setIspraise(Object ispraise) {
		this.ispraise = ispraise;
	}

	public Object getPinglunshu() {
		return pinglunshu;
	}

	public void setPinglunshu(Object pinglunshu) {
		this.pinglunshu = pinglunshu;
	}

	public String getCroleType() {
		return croleType;
	}

	public void setCroleType(String croleType) {
		this.croleType = croleType;
	}

	public String getCusername() {
		return cusername;
	}

	public void setCusername(String cusername) {
		this.cusername = cusername;
	}

	public Object getCfatieshu() {
		return cfatieshu;
	}

	public void setCfatieshu(Object cfatieshu) {
		this.cfatieshu = cfatieshu;
	}

	public Object getCpinglunshu() {
		return cpinglunshu;
	}

	public void setCpinglunshu(Object cpinglunshu) {
		this.cpinglunshu = cpinglunshu;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getQuoteid() {
		return quoteid;
	}

	public void setQuoteid(Long quoteid) {
		this.quoteid = quoteid;
	}

	public Long getIsquote() {
		return isquote;
	}

	public void setIsquote(Long isquote) {
		this.isquote = isquote;
	}

	public String getSchoolname() {
		return schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	public Integer getSelectType() {
        return selectType;
    }

    public void setSelectType(Integer selectType) {
        this.selectType = selectType;
    }

    public java.lang.Integer getViewcount(){
      return viewcount;
    }
    public void setViewcount(java.lang.Integer viewcount){
      this.viewcount = viewcount;
    }
    public java.util.Date getCtime(){
      return ctime;
    }
    public void setCtime(java.util.Date ctime){
      this.ctime = ctime;
    }
    public Integer getCuserid(){
      return cuserid;
    }
    public void setCuserid(Integer cuserid){
      this.cuserid = cuserid;
    }
    public java.lang.String getCommentuserid(){
      return commentuserid;
    }
    public void setCommentuserid(java.lang.String commentuserid){
      this.commentuserid = commentuserid;
    }
    public java.lang.Integer getCloudstatus(){
      return cloudstatus;
    }
    public void setCloudstatus(java.lang.Integer cloudstatus){
      this.cloudstatus = cloudstatus;
    }
    public java.lang.Integer getIsessence(){
      return isessence;
    }
    public void setIsessence(java.lang.Integer isessence){
      this.isessence = isessence;
    }
    public java.lang.String getThemetitle(){
      return themetitle;
    }
    public void setThemetitle(java.lang.String themetitle){
        this.themetitle = themetitle;
    }

    public String getThemetitle25word(){
        String returnTitle=this.getThemetitle();
        if(returnTitle!=null&&returnTitle.length()>25)
            returnTitle=returnTitle.substring(0,25)+"……";
        return returnTitle;
    }

    public java.lang.String getThemecontent(){
        return themecontent;
    }
    public java.lang.String getThemebycontent(){
        if(this.themecontent!=null)
            this.setThemecontent(this.themecontent.replaceAll("_SZ_SCHOOL_IMG_PLACEHOLDER_","ueditor/jsp/../../"));
        return themecontent;
    }
    public void setThemecontent(java.lang.String themecontent){
      this.themecontent = themecontent;
    }
    public java.util.Date getMtime(){
      return mtime;
    }
    public void setMtime(java.util.Date mtime){
      this.mtime = mtime;
    }
    public java.util.Date getCommentmtime(){
      return commentmtime;
    }
    public void setCommentmtime(java.util.Date commentmtime){
      this.commentmtime = commentmtime;
    }
    public java.lang.String getCommenttitle(){
      return commenttitle;
    }

    public String getCommenttitle25word(){
        String returnTitle=this.getCommenttitle();
        if(returnTitle!=null&&returnTitle.length()>25)
            returnTitle=returnTitle.substring(0,25)+"……";
        return (returnTitle==null?"":returnTitle);
    }

    public void setCommenttitle(java.lang.String commenttitle){
      this.commenttitle = commenttitle;
    }
    public Long getThemeid(){
      return themeid;
    }
    public void setThemeid(Long themeid){
      this.themeid = themeid;
    }
    public Long getCourseid(){
      return courseid;
    }
    public void setCourseid(Long courseid){
      this.courseid = courseid;
    }
    public java.lang.Integer getIstop(){
      return istop;
    }
    public void setIstop(java.lang.Integer istop){
      this.istop = istop;
    }
    public Long getTopicid(){
      return topicid;
    }
    public void setTopicid(Long topicid){
      this.topicid = topicid;
    }
    public java.lang.String getCommentcontent(){
      return commentcontent;
    }

    public java.lang.String getCommentbycontent(){
        if(this.commentcontent!=null)
            this.setCommentcontent(this.commentcontent.replaceAll("_SZ_SCHOOL_IMG_PLACEHOLDER_", "ueditor/jsp/../../"));
        return commentcontent;
    } 
    public void setCommentcontent(java.lang.String commentcontent){
      this.commentcontent = commentcontent;
    }

    public String getLoginuserref() {
        return loginuserref;
    }

    public void setLoginuserref(String loginuserref) {
        this.loginuserref = loginuserref;
    }

    public Long getRestorecount() {
        return restorecount;
    }

    public void setRestorecount(Long restorecount) {
        this.restorecount = restorecount;
    }

    public Long getIsread() {
        return isread;
    }

    public void setIsread(Long isread) {
        this.isread = isread;
    }

    public String getCrealname() {
        return crealname;
    }

    public void setCrealname(String crealname) {
        this.crealname = crealname;
    }

    public String getHeadimage() {
        return headimage;
    }

    public void setCheadimage(String headimage) {
        this.headimage = headimage;
    }

    public void setHeadimage(String headimage) {
        this.headimage = headimage;
    }

    public String getLastfabiao() {
        return lastfabiao;
    }

    public void setLastfabiao(String lastfabiao) {
        this.lastfabiao = lastfabiao;
    }

    public String getCtimeString(){
        String returnVal="";
        if(this.getCtime()!=null)
            returnVal=UtilTool.DateConvertToString(this.getCtime(), UtilTool.DateType.type1);
        return returnVal;
    }
    public String getAutoCtimeString(){
        String returnVal="";
        if(this.getCtime()!=null){
            Long dt=System.currentTimeMillis()-this.getCtime().getTime();
            int dLong=(int)(dt/60000);//分钟
            String sHtml="刚刚";
            if(dLong<1){
                if(((int)(dt%60000)/1000)>0){
                    sHtml=(int)(dt%60000)/1000+"秒前";
                }
            }else if(dLong>=1&&dLong<60)
                sHtml=dLong+"分钟前";
            else if(dLong>=60){
                dLong=(int)(dLong/60);
                if(dLong<24)
                    sHtml=dLong+"小时前";
                else
                    sHtml=UtilTool.DateConvertToString(this.getCtime(), UtilTool.DateType.type1);
            }
            returnVal=sHtml;
        }
        return returnVal;
    }

    public Integer getClsType() {
        return clsType;
    }

    public void setClsType(Integer clsType) {
        this.clsType = clsType;
    }
    public Integer getClsid() {
        return clsid;
    }

    public void setClsid(Integer clsid) {
        this.clsid = clsid;
    }

    public Object getSearchRoleType() {
        return searchRoleType;
    }
    public void setSearchRoleType(Integer roletype){
        this.searchRoleType=roletype;
    }
}