package com.school.entity.peer;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ������
 * @description ͬ�����ۼ�¼ʵ����
 */
@Entity
public class PjPeerLogInfo implements Serializable{
	private Integer ref;//������ʶ
	private Integer peeritemref;//�������ʶ
	private Integer score;//�÷�
	private String userid;//��������id
	private String cuserid;//������id
	private Integer uscore;//�޸ĺ����
	private String uuserid;//�޸���id
	private Date ctime;//����ʱ��
	private Date mtime;//�޸�ʱ��
	private Integer sysdelflag;//ϵͳɾ����ǣ�ȥ����߷� ��ͷֵĴ���
	private String peerbaseref;//������Ϣ��ref
	private Integer ptype;//1:��������2����ʦ���� 3��ְ������
	
	public Integer getRef() {
		return ref;
	}
	public void setRef(Integer ref) {
		this.ref = ref;
	}
	public Integer getPeeritemref() {
		return peeritemref;
	}
	public void setPeeritemref(Integer peeritemref) {
		this.peeritemref = peeritemref;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCuserid() {
		return cuserid;
	}
	public void setCuserid(String cuserid) {
		this.cuserid = cuserid;
	}
	public Integer getUscore() {
		return uscore;
	}
	public void setUscore(Integer uscore) {
		this.uscore = uscore;
	}
	public String getUuserid() {
		return uuserid;
	}
	public void setUuserid(String uuserid) {
		this.uuserid = uuserid;
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
	public Integer getSysdelflag() {
		return sysdelflag;
	}
	public void setSysdelflag(Integer sysdelflag) {
		this.sysdelflag = sysdelflag;
	}
	public String getPeerbaseref() {
		return peerbaseref;
	}
	public void setPeerbaseref(String peerbaseref) {
		this.peerbaseref = peerbaseref;
	}
	public Integer getPtype() {
		return ptype;
	}
	public void setPtype(Integer ptype) {
		this.ptype = ptype;
	}
}
