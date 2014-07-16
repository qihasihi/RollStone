package com.school.entity.evalteacher;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

/**
 * 评价项数据
 * 
 * @author zhengzhou
 * 
 */
@Entity
public class AppraiseItemInfo implements Serializable {

	private Integer ref; // 流水号
	private Integer itemid; // 主键
	private String name; // 名称
	private Integer yearid; // 适用期限
	private Date ctime; // 创建时间
	private Integer istitle; // 是否为标题 否则为答案
	private Integer optionid;// 选项ID. 1，2，3，4 可用于排序
	private Integer targetidentitytype; // 项目针对用户身份类型 1班主任 2任课教师 3体育老师

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
