package com.school.entity.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.school.entity.UserInfo;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;


/**
 * 
 * @author ������
 * @date 2013.3.27
 * @description ���Ϣʵ����
 *
 */
@Entity
public class ActivityInfo implements Serializable {

	private String ref;//��ʶ
	private String atname;//����
	private Date begintime;//��ʼʱ��
	private Date endtime;//����ʱ��
	private UserInfo userinfo;//�û���
	private Integer state;//״̬
	private Date ctime;//����ʱ��
	private Date mtime;//�޸�ʱ��
	private String content;//����
	private Integer estimationnum;//��������
	private String audiovisual;//�������
	private Integer issign;//�Ƿ���ñ����ƶ�   0:��  1����
	private String username;
	private List<SiteInfo> siteinfo;
	private Long num1;//ȷ�ϲμ���
	private Long num2;//������
	private Object attitude;//�ҵ�̬��
	private String cuserid;
	private Object isin;//�ж��û��Ƿ�����
	public Object getIsin() {
		return isin;
	}
	public void setIsin(Object isin) {
		this.isin = isin;
	}
	private List<ActivityUserInfo> activityuserinfo;
	
	public List<ActivityUserInfo> getActivityuserinfo() {
		return activityuserinfo;
	}
	public void setActivityuserinfo(List<ActivityUserInfo> activityuserinfo) {
		if(activityuserinfo==null)
			activityuserinfo = new ArrayList<ActivityUserInfo>();
		this.activityuserinfo = activityuserinfo;
	}
	public String getCuserid() {
		return cuserid;
	}
	public void setCuserid(String cuserid) {
		this.cuserid = cuserid;
	}
	public Object getAttitude() {
		return attitude;
	}
	public void setAttitude(Object attitude) {
		this.attitude = attitude;
	}
	private String oldname;//��ͻ�����
	public String getOldname() {
		return oldname;
	}
	public void setOldname(String oldname) {
		this.oldname = oldname;
	}
	private String oldref;//��ͻ���ʶ
	public String getOldref() {
		return oldref;
	}
	public void setOldref(String oldref) {
		this.oldref = oldref;
	}
	
	
	
	public Long getNum1() {
		return num1;
	}
	public void setNum1(Long num1) {
		this.num1 = num1;
	}
	public Long getNum2() {
		return num2;
	}
	public void setNum2(Long num2) {
		this.num2 = num2;
	}
	public List<SiteInfo> getSiteinfo() {
		return siteinfo;
	}
	public void setSiteinfo(List<SiteInfo> siteinfo) {
		if(siteinfo==null)
			siteinfo = new ArrayList<SiteInfo>();
		this.siteinfo = siteinfo;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	private String tmpsite;
	public String getTmpsite() {
		return tmpsite;
	}
	public void setTmpsite(String tmpsite) {
		this.tmpsite = tmpsite;
	}
	private String tmpuid;
	public String getTmpuid() {
		return tmpuid;
	}
	public void setTmpuid(String tmpuid) {
		this.tmpuid = tmpuid;
	}
	
	
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getAtname() {
		return atname;
	}
	public void setAtname(String atname) {
		this.atname = atname;
	}
	public Date getBegintime() {
		return begintime;
	}
	public String getBegintimestring() {
		if(this.begintime==null)
			return "";
		return UtilTool.DateConvertToString(begintime,DateType.type1);
	}
	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public String getEndtimestring() {
		if(this.endtime==null)
			return "";
		return UtilTool.DateConvertToString(endtime,DateType.type1);
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public UserInfo getUserinfo() {
		if (userinfo==null)
			userinfo = new UserInfo();
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public void setUserid(String ref){
		this.getUserinfo().setRef(ref);
	}
	public String getUserid(){
		return this.getUserinfo().getRef();
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getEstimationnum() {
		return estimationnum;
	}
	public void setEstimationnum(Integer estimationnum) {
		this.estimationnum = estimationnum;
	}
	public String getAudiovisual() {
		return audiovisual;
	}
	public void setAudiovisual(String audiovisual) {
		this.audiovisual = audiovisual;
	}
	public Integer getIssign() {
		return issign;
	}
	public void setIssign(Integer issign) {
		this.issign = issign;
	}
	
}
