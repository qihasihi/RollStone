package com.school.manager.inter.evalteacher;

import java.math.BigDecimal;
import java.util.List;

import com.school.entity.evalteacher.AppraiseItemInfo;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;


public interface IAppraiseItemManager extends IBaseManager<AppraiseItemInfo> {

	/**
	 * ���
	 */
	public abstract Integer save(AppraiseItemInfo t);

	/**
	 * ɾ��
	 */
	public abstract Integer delete(AppraiseItemInfo t);

	/**
	 * �޸�
	 */
	public abstract Integer update(AppraiseItemInfo t);

	/**
	 * ��ѯ
	 */
	public abstract List<AppraiseItemInfo> getList(AppraiseItemInfo t, PageResult presult);
	
	/**
	 * ��ȡ�޸ĵ�SQL���
	 * @param t
	 * @return
	 */
	public List<Object>  getUpdateSql(AppraiseItemInfo t,StringBuilder sqlbuilder);
	
	/**
	 * ��ȡ��ӵ�SQL���
	 * @param t
	 * @return
	 */
	public List<Object>  getSaveSql(AppraiseItemInfo t,StringBuilder sqlbuilder);
	/**
	 * �õ���һ����ʶ(��ˮ��)
	 * @return
	 */
	public BigDecimal getNextSeqId();
	
	/**
	 * ��ȡɾ����SQL
	 * @param t
	 * @param sqlbuilder
	 * @return
	 */
	public List<Object> getDeleteSql(AppraiseItemInfo t,StringBuilder sqlbuilder);
	
	/**
	 * �õ�����
	 * @param t
	 * @return
	 */
	public Integer getAppraiseTitleCount(AppraiseItemInfo t);
	
	/**
	 * �õ�title
	 * @param t
	 * @return
	 */
	public List<AppraiseItemInfo> getAppraiseTitle(AppraiseItemInfo t,BigDecimal targetid,Boolean order);
	
	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList);
	
}