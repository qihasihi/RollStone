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
 * @author ������
 * @date 2013-04-26
 * @description �༶У������ʵ���� 
 */
@Entity
public class ClassEthosInfo implements Serializable{
	private String ref;//����
	private WeekInfo weekinfo;//�ܴζ���
	private String grade;//�꼶
	private ClassInfo classinfo;//�꼶����
	private String assemblyremark;//�������
	private Integer assemblyscore;//�����������
	private String hygieneremark;//�������
	private Integer hygienescore;//�����������
	private String moneyremark;//�Ʋ����
	private Integer moneyscore;//�Ʋ��������
	private String dormitoryremark;//���Ἧ�����
	private Integer dormitoryscore;//���Ἧ���������
	private String otherremark;//�������
	private Integer otherscore;//�����������
	private String awardremark;//�������
	private Integer awardscore;//��������ӷ�
	private UserInfo operateinfo;//���һ�β����˶���
	private Date ctime;//����ʱ��
	private Date mtime;//�޸�ʱ��
	
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
