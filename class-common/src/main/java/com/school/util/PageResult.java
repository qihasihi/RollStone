package com.school.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.school.util.jqgrid.JQGridConditionEntity;

public class PageResult implements Serializable {
	private String orderBy = "";
	private String sort = "asc";
	private Collection list; // 查询结果
	private Integer pageNo = 1; // 实际页号
	private Integer pageSize = 20; // 每页记录数
	private Integer recTotal = 0; // 总记录数
	private Integer orderIdx = null;// 排序的索引
	
	private JQGridConditionEntity jqgridcondition; // jqgrid 查询
	public JQGridConditionEntity getJqgridcondition() {
		if (jqgridcondition == null)
			jqgridcondition = new JQGridConditionEntity();
		return jqgridcondition;
	}

	public void setJqgridcondition(JQGridConditionEntity jqgridcondition) {
		this.jqgridcondition = jqgridcondition;
	}

	public Collection getList() {
		return (list=(list==null?new ArrayList():list));
	}

	public void setList(Collection list) {
		this.list = list;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return (0 == pageSize) ? 10 : pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public int getRecTotal() {
		return recTotal;
	}

	public void setRecTotal(Integer recTotal) {
		this.recTotal = recTotal;
	}

	public Integer getPageTotal() {
		Integer ret = (this.getRecTotal() - 1) / this.getPageSize() + 1;
		ret = (ret < 1) ? 1 : ret;
		return ret;
	}

	public Integer getFirstRec() {
		Integer ret = (this.getPageNo() - 1) * this.getPageSize();// + 1;
		ret = (ret < 1) ? 0 : ret;
		return ret;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Integer getOrderIdx() {
		return orderIdx;
	}

	public void setOrderIdx(Integer orderIdx) {
		this.orderIdx = orderIdx;
	}

}
