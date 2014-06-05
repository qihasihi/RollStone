package com.school.entity.peer;

import java.io.Serializable;
import java.util.Date;

import com.school.entity.UserInfo;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

/**
 * @author 岳春阳
 *@description 同行评价项实体类
 */
@Entity
public class PjPeerItemInfo implements Serializable {
	private Integer id;//主键、标识
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	private String name;//评价项名称
	private Integer score;//得分
	private Integer parentref;//父级标识，指题号
	private String remark;//备注
	private Integer type;//1：选择题:2：备注
	private String peerbaseref;//主题标识
	private Integer ptype;//1.自我评价 2.教师互评 3.职工互评
	private Date ctime;//创建时间
	private Date mtime;//修改时间
	private String lastoperateuserid;//最后操作人id
	private UserInfo userinfo;
	private Integer ref;//题号
	private Integer ordernum;//排序
	private Object childcount;//等级数量
	
	public String getNamestr(){
		if(this.name!=null&&this.name.length()>6)
			return this.name.substring(0,6);
		return this.name;
	}
	public Object getChildcount() {
		return childcount;
	}
	public void setChildcount(Object childcount) {
		this.childcount = childcount;
	}
	public Integer getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(Integer ordernum) {
		this.ordernum = ordernum;
	}
	public UserInfo getUserinfo() {
		if(userinfo==null)
			userinfo = new UserInfo();
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public Integer getRef() {
		return ref;
	}
	public void setRef(Integer ref) {
		this.ref = ref;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getParentref() {
		return parentref;
	}
	public void setParentref(Integer parentref) {
		this.parentref = parentref;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	public Date getCtime() {
		return ctime;
	}
	public String getCtimestring(){
		if(ctime==null)
			return null;
		return UtilTool.DateConvertToString(ctime, DateType.type1);
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public Date getMtime() {
		return mtime;
	}
	public String getMtimestring(){
		if(mtime==null)
			return null;
		return UtilTool.DateConvertToString(mtime, DateType.type1);
	}
	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}
	public String getLastoperateuserid() {
		return this.getUserinfo().getRef();
	}
	public void setLastoperateuserid(String lastoperateuserid) {
		this.getUserinfo().setRef(lastoperateuserid);
		//this.lastoperateuserid = lastoperateuserid;
	}
}
