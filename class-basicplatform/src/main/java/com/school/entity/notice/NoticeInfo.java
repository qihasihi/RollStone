package com.school.entity.notice;

import java.io.Serializable;
import java.util.Date;

import com.school.entity.UserInfo;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

/**
 * @author 岳春阳
 * @date 2013-05-22
 * @description 通知公告数据实体类 
 */
@Entity
public class NoticeInfo implements Serializable{
	private String ref;//主键
	private String noticetitle;//公告标题
	private String noticecontent;//公告内容
	private String noticetype;//公告类型
	private Integer istop;//是否置顶
	private String noticerole;//发送对象角色
	private String noticegrade;//发送对象年级----针对学生角色的时候
	private Integer clickcount;//点击数
	private Date begintime;//开始时间----针对公告类型的时候
	private Date endtime;//结束时间-----针对公告类型的时候
	private String titlelink;//标题链接
	private String cuserid;//创建人
	private UserInfo userinfo;//用户实体
	private Date ctime;//创建时间
	private Date mtime;//修改时间-----最后编辑时间
	private String realname;
    private Integer istime;

    public Integer getDcschoolid() {
        return dcschoolid;
    }

    public void setDcschoolid(Integer dcschoolid) {
        this.dcschoolid = dcschoolid;
    }

    private Integer dcschoolid;
    public Integer getIstime() {
        return istime;
    }

    public void setIstime(Integer istime) {
        this.istime = istime;
    }

    public String getRealname() {
		return this.getUserinfo().getRealname();
	}
	public void setRealname(String realname) {
		this.getUserinfo().setRealname(realname);
	}
	public UserInfo getUserinfo() {
		if(userinfo==null)
			userinfo = new UserInfo();
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getNoticetitle() {
		return noticetitle;
	}
	public String getNoticetitle15String() {
		return (noticetitle!=null&&noticetitle.trim().length()>13)
						?noticetitle.substring(0,13)+"...":noticetitle;
	}
	public void setNoticetitle(String noticetitle) {
		this.noticetitle = noticetitle;
	}
	public String getNoticecontent() {
		return noticecontent;
	}
	public void setNoticecontent(String noticecontent) {
		this.noticecontent = noticecontent;
	}
	public String getNoticetype() {
		return noticetype;
	}
	public void setNoticetype(String noticetype) {
		this.noticetype = noticetype;
	}
	public Integer getIstop() {
		return istop;
	}
	public void setIstop(Integer istop) {
		this.istop = istop;
	}
	public String getNoticerole() {
		return noticerole;
	}
	public void setNoticerole(String noticerole) {
		this.noticerole = noticerole;
	}
	public String getNoticegrade() {
		return noticegrade;
	}
	public void setNoticegrade(String noticegrade) {
		this.noticegrade = noticegrade;
	}
	public Integer getClickcount() {
		return clickcount;
	}
	public void setClickcount(Integer clickcount) {
		this.clickcount = clickcount;
	}
	public Date getBegintime() {
		return begintime;
	}
	public String getBegintimestring(){
		if(this.begintime==null)
			return "";
		return UtilTool.DateConvertToString(begintime,DateType.type1);
	}
	public void setBegintime(Date begintime) {
	//	this.begintime = UtilTool.StringConvertToDate(begintime);
        this.begintime=begintime;
	}

    public void setBegintimeString(String begintime){
        this.begintime = UtilTool.StringConvertToDate(begintime);
    }
	public Date getEndtime() {
		return endtime;
	}
	public String getEndtimestring(){
		if(this.endtime==null)
			return "";
		return UtilTool.DateConvertToString(endtime,DateType.type1);
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
    public void setEndtimeString(String endtime){
        this.endtime = UtilTool.StringConvertToDate(endtime);
    }
	public String getTitlelink() {
		return titlelink;
	}
	public void setTitlelink(String titlelink) {
		this.titlelink = titlelink;
	}
	public String getCuserid() {
		return this.getUserinfo().getRef();
	}
	public void setCuserid(String cuserid) {
		this.getUserinfo().setRef(cuserid);
	}
	public Date getCtime() {
		return ctime;
	}
	public String getCtimestring(){
		if(this.ctime==null)
			return "";
		return UtilTool.DateConvertToString(ctime,DateType.type1); 
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public Date getMtime() {
		return mtime;
	}
	public String getMtimestring(){
		if(this.mtime==null)
			return "无";
		return UtilTool.DateConvertToString(mtime,DateType.type1); 
	}
	public String getCtimeChinaString(){
		if(this.ctime==null)
			return "";
		return UtilTool.DateConvertToString(ctime,DateType.YearForChina);
	}
	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}
	
	
}
