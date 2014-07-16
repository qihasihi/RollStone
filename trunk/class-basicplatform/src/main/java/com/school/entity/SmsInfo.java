package com.school.entity;

import java.util.Date;

import javax.persistence.Entity;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

/**
 * ����
 * 
 * @author zhushixiong
 * 
 */
@Entity
public class SmsInfo implements java.io.Serializable {
	private String smstitle;
	private String smscontent;
	private Integer smsid;
	private Integer senderid;
	private String sendername;
	private String receiverlist;
	private Date ctime;
	private Date mtime;
	private Integer smsstatus;
	public static final int STATUS_DRAFT = 0;// ��ʾ�ݸ���
	public static final int STATUS_SEND_SUCCESS = 1;// ��ʾ�ѷ���
	public static final int STATUS_SEND_ERROR = -1;// ��ʾ����ʧ��
	public static final int STATUS_DELETED = -2;// ��ʾ����ʧ��

	
	public String getSendername() {
		return sendername;
	}

	public void setSendername(String sendername) {
		this.sendername = sendername;
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

	public Integer getSmsid() {
		return smsid;
	}

	public void setSmsid(Integer smsid) {
		this.smsid = smsid;
	}

	public String getCtime() {
		if (ctime == null)
			return null;
		return UtilTool.DateConvertToString(ctime, DateType.type1);
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Integer getSmsstatus() {
		return smsstatus;
	}

	public void setSmsstatus(Integer smsstatus) {
		this.smsstatus = smsstatus;
	}

	public Integer getSenderid() {
		return senderid;
	}

	public void setSenderid(Integer senderid) {
		this.senderid = senderid;
	}

	public String getReceiverlist() {
		return receiverlist;
	}

	public void setReceiverlist(String receiverlist) {
		this.receiverlist = receiverlist;
	}

	public String getMtime() {
		if (mtime == null)
			return null;
		return UtilTool.DateConvertToString(mtime, DateType.type1);
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	} 
	public String getCtimeString() {
		return (ctime == null ? "" : UtilTool.DateConvertToString(ctime,
				DateType.type1));
	}

}
