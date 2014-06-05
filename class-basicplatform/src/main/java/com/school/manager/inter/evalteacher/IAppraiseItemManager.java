package com.school.manager.inter.evalteacher;

import java.math.BigDecimal;
import java.util.List;

import com.school.entity.evalteacher.AppraiseItemInfo;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;


public interface IAppraiseItemManager extends IBaseManager<AppraiseItemInfo> {

	/**
	 * 添加
	 */
	public abstract Integer save(AppraiseItemInfo t);

	/**
	 * 删除
	 */
	public abstract Integer delete(AppraiseItemInfo t);

	/**
	 * 修改
	 */
	public abstract Integer update(AppraiseItemInfo t);

	/**
	 * 查询
	 */
	public abstract List<AppraiseItemInfo> getList(AppraiseItemInfo t, PageResult presult);
	
	/**
	 * 获取修改的SQL语句
	 * @param t
	 * @return
	 */
	public List<Object>  getUpdateSql(AppraiseItemInfo t,StringBuilder sqlbuilder);
	
	/**
	 * 获取添加的SQL语句
	 * @param t
	 * @return
	 */
	public List<Object>  getSaveSql(AppraiseItemInfo t,StringBuilder sqlbuilder);
	/**
	 * 得到下一个标识(流水号)
	 * @return
	 */
	public BigDecimal getNextSeqId();
	
	/**
	 * 获取删除的SQL
	 * @param t
	 * @param sqlbuilder
	 * @return
	 */
	public List<Object> getDeleteSql(AppraiseItemInfo t,StringBuilder sqlbuilder);
	
	/**
	 * 得到数量
	 * @param t
	 * @return
	 */
	public Integer getAppraiseTitleCount(AppraiseItemInfo t);
	
	/**
	 * 得到title
	 * @param t
	 * @return
	 */
	public List<AppraiseItemInfo> getAppraiseTitle(AppraiseItemInfo t,BigDecimal targetid,Boolean order);
	
	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList);
	
}