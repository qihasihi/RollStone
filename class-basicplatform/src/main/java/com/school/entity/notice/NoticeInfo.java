package com.school.entity.notice;

import java.io.Serializable;
import java.util.Date;

import com.school.entity.UserInfo;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

/**
 * @author ������
 * @date 2013-05-22
 * @description ֪ͨ��������ʵ���� 
 */
@Entity
public class NoticeInfo implements Serializable{
	private String ref;//����
	private String noticetitle;//�������
	private String noticecontent;//��������
	private String noticetype;//��������
	private Integer istop;//�Ƿ��ö�
	private String noticerole;//���Ͷ����ɫ
	private String noticegrade;//���Ͷ����꼶----���ѧ����ɫ��ʱ��
	private Integer clickcount;//�����
	private Date begintime;//��ʼʱ��----��Թ������͵�ʱ��
	private Date endtime;//����ʱ��-----��Թ������͵�ʱ��
	private String titlelink;//��������
	private String cuserid;//������
	private UserInfo userinfo;//�û�ʵ��
	private Date ctime;//����ʱ��
	private Date mtime;//�޸�ʱ��-----���༭ʱ��
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
			return "��";
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
