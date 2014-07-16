package com.school.entity.peer;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 岳春阳
 * @description 同行评价记录实体类
 */
@Entity
public class PjPeerLogInfo implements Serializable{
	private Integer ref;//主键标识
	private Integer peeritemref;//评价项标识
	private Integer score;//得分
	private String userid;//被评价人id
	private String cuserid;//评价人id
	private Integer uscore;//修改后分数
	private String uuserid;//修改人id
	private Date ctime;//创建时间
	private Date mtime;//修改时间
	private Integer sysdelflag;//系统删除标记（去掉最高分 最低分的处理）
	private String peerbaseref;//基础信息表ref
	private Integer ptype;//1:自我评价2：教师互评 3：职工互评
	
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
