package com.school.entity;

import javax.persistence.Entity;
import java.util.Date;
@Entity
public class FnClickInfo implements java.io.Serializable{
	private Integer ref;
	private Integer fnid;
	private Integer clickcount;
	private Date clickdate;
	public Integer getRef() {
		return ref;
	}
	public void setRef(Integer ref) {
		this.ref = ref;
	}
	public Integer getFnid() {
		return fnid;
	}
	public void setFnid(Integer fnid) {
		this.fnid = fnid;
	}
	public Integer getClickcount() {
		return clickcount;
	}
	public void setClickcount(Integer clickcount) {
		this.clickcount = clickcount;
	}
	public Date getClickdate() {
		return clickdate;
	}
	public void setClickdate(Date clickdate) {
		this.clickdate = clickdate;
	}
}
