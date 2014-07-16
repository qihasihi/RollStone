package com.school.entity.ethosaraisal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.school.entity.ClassInfo;
import com.school.entity.UserInfo;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

/**
 * @author 岳春阳
 * @date 2013-04-26
 * @description 班级校风数据实体类 
 */
@Entity
public class ClassEthosInfo implements Serializable{
	private String ref;//主键
	private WeekInfo weekinfo;//周次对象
	private String grade;//年级
	private ClassInfo classinfo;//年级对象
	private String assemblyremark;//集会情况
	private Integer assemblyscore;//集会情况分数
	private String hygieneremark;//卫生情况
	private Integer hygienescore;//卫生情况分数
	private String moneyremark;//财产情况
	private Integer moneyscore;//财产情况分数
	private String dormitoryremark;//宿舍集体情况
	private Integer dormitoryscore;//宿舍集体情况分数
	private String otherremark;//其他情况
	private Integer otherscore;//其他情况分数
	private String awardremark;//奖励情况
	private Integer awardscore;//奖励情况加分
	private UserInfo operateinfo;//最后一次操作人对象
	private Date ctime;//创建时间
	private Date mtime;//修改时间
	
	private String kqremark;
	private String wjremark;
	private String gdremark;
	private BigDecimal kqscore;
	private BigDecimal wjscore;
	private BigDecimal gdscore;
	
	
	public UserInfo getOperateinfo() {
		return operateinfo;
	}
	public void setOperateinfo(UserInfo operateinfo) {
		this.operateinfo = operateinfo;
	}
	public String getKqremark() {
		return kqremark;
	}
	public void setKqremark(String kqremark) {
		this.kqremark = kqremark;
	}
	public String getWjremark() {
		return wjremark;
	}
	public void setWjremark(String wjremark) {
		this.wjremark = wjremark;
	}
	public String getGdremark() {
		return gdremark;
	}
	public void setGdremark(String gdremark) {
		this.gdremark = gdremark;
	}
	public BigDecimal getKqscore() {
		return kqscore;
	}
	public void setKqscore(BigDecimal kqscore) {
		this.kqscore = kqscore;
	}
	public BigDecimal getWjscore() {
		return wjscore;
	}
	public void setWjscore(BigDecimal wjscore) {
		this.wjscore = wjscore;
	}
	public BigDecimal getGdscore() {
		return gdscore;
	}
	public void setGdscore(BigDecimal gdscore) {
		this.gdscore = gdscore;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
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
	public String getAssemblyremark() {
		return assemblyremark;
	}
	public void setAssemblyremark(String assemblyremark) {
		this.assemblyremark = assemblyremark;
	}
	public Integer getAssemblyscore() {
		return assemblyscore;
	}
	public void setAssemblyscore(Integer assemblyscore) {
		this.assemblyscore = assemblyscore;
	}
	public String getHygieneremark() {
		return hygieneremark;
	}
	public void setHygieneremark(String hygieneremark) {
		this.hygieneremark = hygieneremark;
	}
	public Integer getHygienescore() {
		return hygienescore;
	}
	public void setHygienescore(Integer hygienescore) {
		this.hygienescore = hygienescore;
	}
	public String getMoneyremark() {
		return moneyremark;
	}
	public void setMoneyremark(String moneyremark) {
		this.moneyremark = moneyremark;
	}
	public Integer getMoneyscore() {
		return moneyscore;
	}
	public void setMoneyscore(Integer moneyscore) {
		this.moneyscore = moneyscore;
	}
	public String getDormitoryremark() {
		return dormitoryremark;
	}
	public void setDormitoryremark(String dormitoryremark) {
		this.dormitoryremark = dormitoryremark;
	}
	public Integer getDormitoryscore() {
		return dormitoryscore;
	}
	public void setDormitoryscore(Integer dormitoryscore) {
		this.dormitoryscore = dormitoryscore;
	}
	public String getOtherremark() {
		return otherremark;
	}
	public void setOtherremark(String otherremark) {
		this.otherremark = otherremark;
	}
	public Integer getOtherscore() {
		return otherscore;
	}
	public void setOtherscore(Integer otherscore) {
		this.otherscore = otherscore;
	}
	public String getAwardremark() {
		return awardremark;
	}
	public void setAwardremark(String awardremark) {
		this.awardremark = awardremark;
	}
	public Integer getAwardscore() {
		return awardscore;
	}
	public void setAwardscore(Integer awardscore) {
		this.awardscore = awardscore;
	}
	public UserInfo getUserinfo() {
		if(operateinfo==null)
			operateinfo=new UserInfo();
		return operateinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.operateinfo = userinfo;
	}
	public String getOperateid(){
		return this.operateinfo.getRef();
	}
	public void setOperateid(String ref){
		this.getUserinfo().setRef(ref);
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
