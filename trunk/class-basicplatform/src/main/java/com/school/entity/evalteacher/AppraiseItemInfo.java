package com.school.entity.evalteacher;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

/**
 * ����������
 * 
 * @author zhengzhou
 * 
 */
@Entity
public class AppraiseItemInfo implements Serializable {

	private Integer ref; // ��ˮ��
	private Integer itemid; // ����
	private String name; // ����
	private Integer yearid; // ��������
	private Date ctime; // ����ʱ��
	private Integer istitle; // �Ƿ�Ϊ���� ����Ϊ��
	private Integer optionid;// ѡ��ID. 1��2��3��4 ����������
	private Integer targetidentitytype; // ��Ŀ����û�������� 1������ 2�ον�ʦ 3������ʦ

	public Integer getRef() {
		return ref;
	}

	public void setRef(Integer ref) {
		this.ref = ref;
	}
	
	public Integer getItemid() {
		return itemid;
	}

	public void setItemid(Integer itemid) {
		this.itemid = itemid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getYearid() {
		return yearid;
	}

	public void setYearid(Integer yearid) {
		this.yearid = yearid;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Integer getIstitle() {
		return istitle;
	}

	public void setIstitle(Integer istitle) {
		this.istitle = istitle;
	}

	public Integer getOptionid() {
		return optionid;
	}

	public void setOptionid(Integer optionid) {
		this.optionid = optionid;
	}

	public Integer getTargetidentitytype() {
		return targetidentitytype;
	}

	public void setTargetidentitytype(Integer targetidentitytype) {
		this.targetidentitytype = targetidentitytype;
	}

}
