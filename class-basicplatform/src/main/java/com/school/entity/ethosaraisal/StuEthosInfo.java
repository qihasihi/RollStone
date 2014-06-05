package com.school.entity.ethosaraisal;

import java.io.Serializable;
import java.util.Date;

import com.school.entity.ClassInfo;
import com.school.entity.UserInfo;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

/**
 * @author 岳春阳
 * @date 2013-04-26
 * @description 学员校风数据实体类 
 */
@Entity
public class StuEthosInfo implements Serializable{
	private String ref;//主键
	private UserInfo userinfo;//用户对象
	private ClassInfo classinfo;//班级对象
	private WeekInfo weekinfo;//周次对象
	private String grade;//年级
	private String sickleave;//病假
	private Integer sickleavenum;//病假次数
	private Integer sickleavescore;//病假扣分
	private String thingleave;//事假
	private Integer thingleavenum;//事假次数
	private Integer thingleavescore;//事假扣分
	private String leaveearly;//早退
	private Integer leaveearlynum;//早退次数
	private Integer leaveearlyscore;//早退扣分
	private String absenteeism;//旷课
	private Integer absennum;//旷课次数
	private Integer absenscore;//旷课扣分
	private String late;//迟到
	private Integer latenum;//迟到次数
	private Integer latescore;//迟到扣分
	private String discipline;//违纪
	private Integer disciplinenum;//违纪次数
	private Integer disciplinescore;//违纪扣分
	private String goodthing;//好人好事
	private Integer goodthingnum;//好人好事次数
	private Integer goodthingscore;//好人好事扣分
	private String badge;//胸卡
	private Integer badgenum;//胸卡次数
	private Integer badgescore;//胸卡扣分
	private String uniforms;//校服
	private Integer uniformsnum;//校服次数
	private Integer uniformsscore;//校服扣分
	private String linere;//行规
	private Integer linerenum;//行规次数
	private Integer linerescore;//行规扣分
	private String rebook;//还书记录
	private Integer rebooknum;//还书记录次数
	private Integer rebookscore;//还书记录扣分
	private UserInfo operateinfo;//最后一次操作人对象
	private Date ctime;//创建时间
	private Date mtime;//修改时间
	
	private String stuno;
	private String stuname;
	
	public String getStuno() {
		return stuno;
	}
	public void setStuno(String stuno) {
		this.stuno = stuno;
	}
	public String getStuname() {
		return stuname;
	}
	public void setStuname(String stuname) {
		this.stuname = stuname;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public UserInfo getUserinfo() {
		if(userinfo==null)
			userinfo = new UserInfo();
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public String getUserid(){
		return this.getUserinfo().getRef();
	}
	public void setUserid(String ref){
		this.getUserinfo().setRef(ref);
	}
	public ClassInfo getClassinfo() {	
		if(classinfo==null)
			classinfo = new ClassInfo();
		return classinfo;
	}
	public void setClassinfo(ClassInfo classinfo) {		
		this.classinfo = classinfo;
	}
	public Integer getClassid(){
		return this.getClassinfo().getClassid();
	}
	public void setClassid(Integer classid){
		this.getClassinfo().setClassid(classid);
	}
	public WeekInfo getWeekinfo() {
		if(weekinfo==null)
			weekinfo = new WeekInfo();
		return weekinfo;
	}
	public void setWeekinfo(WeekInfo weekinfo) {
		this.weekinfo = weekinfo;
	}
	public Integer getWeekid(){
		return this.getWeekinfo().getRef();
	}
	public void setWeekid(Integer ref){
		this.getWeekinfo().setRef(ref);
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getSickleave() {
		return sickleave;
	}
	public void setSickleave(String sickleave) {
		this.sickleave = sickleave;
	}
	public Integer getSickleavenum() {
		return sickleavenum;
	}
	public void setSickleavenum(Integer sickleavenum) {
		this.sickleavenum = sickleavenum;
	}
	public Integer getSickleavescore() {
		return sickleavescore;
	}
	public void setSickleavescore(Integer sickleavescore) {
		this.sickleavescore = sickleavescore;
	}
	public String getThingleave() {
		return thingleave;
	}
	public void setThingleave(String thingleave) {
		this.thingleave = thingleave;
	}
	public Integer getThingleavenum() {
		return thingleavenum;
	}
	public void setThingleavenum(Integer thingleavenum) {
		this.thingleavenum = thingleavenum;
	}
	public Integer getThingleavescore() {
		return thingleavescore;
	}
	public void setThingleavescore(Integer thingleavescore) {
		this.thingleavescore = thingleavescore;
	}
	public String getLeaveearly() {
		return leaveearly;
	}
	public void setLeaveearly(String leaveearly) {
		this.leaveearly = leaveearly;
	}
	public Integer getLeaveearlynum() {
		return leaveearlynum;
	}
	public void setLeaveearlynum(Integer leaveearlynum) {
		this.leaveearlynum = leaveearlynum;
	}
	public Integer getLeaveearlyscore() {
		return leaveearlyscore;
	}
	public void setLeaveearlyscore(Integer leaveearlyscore) {
		this.leaveearlyscore = leaveearlyscore;
	}
	public String getAbsenteeism() {
		return absenteeism;
	}
	public void setAbsenteeism(String absenteeism) {
		this.absenteeism = absenteeism;
	}
	public Integer getAbsennum() {
		return absennum;
	}
	public void setAbsennum(Integer absennum) {
		this.absennum = absennum;
	}
	public Integer getAbsenscore() {
		return absenscore;
	}
	public void setAbsenscore(Integer absenscore) {
		this.absenscore = absenscore;
	}
	public String getLate() {
		return late;
	}
	public void setLate(String late) {
		this.late = late;
	}
	public Integer getLatenum() {
		return latenum;
	}
	public void setLatenum(Integer latenum) {
		this.latenum = latenum;
	}
	public Integer getLatescore() {
		return latescore;
	}
	public void setLatescore(Integer latescore) {
		this.latescore = latescore;
	}
	public String getDiscipline() {
		return discipline;
	}
	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}
	public Integer getDisciplinenum() {
		return disciplinenum;
	}
	public void setDisciplinenum(Integer disciplinenum) {
		this.disciplinenum = disciplinenum;
	}
	public Integer getDisciplinescore() {
		return disciplinescore;
	}
	public void setDisciplinescore(Integer disciplinescore) {
		this.disciplinescore = disciplinescore;
	}
	public String getGoodthing() {
		return goodthing;
	}
	public void setGoodthing(String goodthing) {
		this.goodthing = goodthing;
	}
	public Integer getGoodthingnum() {
		return goodthingnum;
	}
	public void setGoodthingnum(Integer goodthingnum) {
		this.goodthingnum = goodthingnum;
	}
	public Integer getGoodthingscore() {
		return goodthingscore;
	}
	public void setGoodthingscore(Integer goodthingscore) {
		this.goodthingscore = goodthingscore;
	}
	public String getBadge() {
		return badge;
	}
	public void setBadge(String badge) {
		this.badge = badge;
	}
	public Integer getBadgenum() {
		return badgenum;
	}
	public void setBadgenum(Integer badgenum) {
		this.badgenum = badgenum;
	}
	public Integer getBadgescore() {
		return badgescore;
	}
	public void setBadgescore(Integer badgescore) {
		this.badgescore = badgescore;
	}
	public String getUniforms() {
		return uniforms;
	}
	public void setUniforms(String uniforms) {
		this.uniforms = uniforms;
	}
	public Integer getUniformsnum() {
		return uniformsnum;
	}
	public void setUniformsnum(Integer uniformsnum) {
		this.uniformsnum = uniformsnum;
	}
	public Integer getUniformsscore() {
		return uniformsscore;
	}
	public void setUniformsscore(Integer uniformsscore) {
		this.uniformsscore = uniformsscore;
	}
	public String getLinere() {
		return linere;
	}
	public void setLinere(String linere) {
		this.linere = linere;
	}
	public Integer getLinerenum() {
		return linerenum;
	}
	public void setLinerenum(Integer linerenum) {
		this.linerenum = linerenum;
	}
	public Integer getLinerescore() {
		return linerescore;
	}
	public void setLinerescore(Integer linerescore) {
		this.linerescore = linerescore;
	}
	public String getRebook() {
		return rebook;
	}
	public void setRebook(String rebook) {
		this.rebook = rebook;
	}
	public Integer getRebooknum() {
		return rebooknum;
	}
	public void setRebooknum(Integer rebooknum) {
		this.rebooknum = rebooknum;
	}
	public Integer getRebookscore() {
		return rebookscore;
	}
	public void setRebookscore(Integer rebookscore) {
		this.rebookscore = rebookscore;
	}
	public UserInfo getOperateinfo() {
		if(operateinfo==null)
			operateinfo = new UserInfo();
		return operateinfo;
	}
	public void setOperateinfo(UserInfo operateinfo) {
		this.operateinfo = operateinfo;
	}
	public String getOperateid(){
		return this.operateinfo.getRef();
	}
	public void setOperateid(String ref){
		this.getOperateinfo().setRef(ref);
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
			return "";
		return UtilTool.DateConvertToString(mtime,DateType.type1);
	}
	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}
}
