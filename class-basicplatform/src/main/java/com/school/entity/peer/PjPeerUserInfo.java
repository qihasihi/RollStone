package com.school.entity.peer;

import java.io.Serializable;
import java.util.Date;

import com.school.entity.UserInfo;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

/**
 * @author ������
 * @description ͬ��������Ա��
 */
@Entity
public class PjPeerUserInfo implements Serializable {
	private Integer ref;//������ʶ
	private String userid;//������id
	private UserInfo userinfo;
	public UserInfo getUserinfo() {
		if(userinfo ==null)
			userinfo=new UserInfo();
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	private String peerbaseid;//��������id
	private Integer ptype;//2��ְ��������1����ʦ����
	private Integer deptid;//����id
	private Date ctime;//����ʱ��
	private Date mtime;//�޸�ʱ��
	
	//���������û������ֶ�
	private String teachername;
	private String deptname;
	
	
	public String getTeachername() {
		return teachername;
	}
	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public Integer getRef() {
		return ref;
	}
	public void setRef(Integer ref) {
		this.ref = ref;
	}
	public String getUserid() {
		return this.getUserinfo().getRef();
	}
	public void setUserid(String userid) {
		this.getUserinfo().setRef(userid);
	}
	public String getPeerbaseid() {
		return peerbaseid;
	}
	public void setPeerbaseid(String peerbaseid) {
		this.peerbaseid = peerbaseid;
	}
	public Integer getPtype() {
		return ptype;
	}
	public void setPtype(Integer ptype) {
		this.ptype = ptype;
	}
	public Integer getDeptid() {
		return deptid;
	}
	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
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
}
