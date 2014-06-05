package com.school.entity;

import java.util.Date;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class SmsReceiver {

	public void SmsReceiver() {
	}

	private Date ctime;
	private java.lang.Integer status;
	private java.lang.Integer smsid;
	private Date mtime;
	private java.lang.Integer receiverid;
	private java.lang.Integer ref;
	private SmsInfo smsinfo;

	// //////////////” º˛–≈œ¢////////////
	private String smstitle;
	private String smscontent;
	private String sendername;

	// /////////////////////////////////

	public SmsInfo getSmsinfo() {
		return smsinfo;
	}

	public String getSmstitle() {
		return smstitle;
	}

	public void setSmstitle(String smstitle) {
		this.smstitle = smstitle;
	}

	public String getSmscontent() {
		return smscontent;
	}

	public void setSmscontent(String smscontent) {
		this.smscontent = smscontent;
	}

	public String getSendername() {
		return sendername;
	}

	public void setSendername(String sendername) {
		this.sendername = sendername;
	}

	public void setSmsinfo(SmsInfo smsinfo) {
		this.smsinfo = smsinfo;
	}

	public String getCtime() {
		if(ctime==null)
			return null;
		return UtilTool.DateConvertToString(ctime, DateType.type1);
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.Integer getSmsid() {
		return smsid;
	}

	public void setSmsid(java.lang.Integer smsid) {
		this.smsid = smsid;
	}

	public Date getMtime() {
		return mtime;
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}

	public java.lang.Integer getReceiverid() {
		return receiverid;
	}

	public void setReceiverid(java.lang.Integer receiverid) {
		this.receiverid = receiverid;
	}

	public java.lang.Integer getRef() {
		return ref;
	}

	public void setRef(java.lang.Integer ref) {
		this.ref = ref;
	}

}