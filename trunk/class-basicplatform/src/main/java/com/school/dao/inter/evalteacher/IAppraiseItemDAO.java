package com.school.dao.inter.evalteacher;

import java.math.BigDecimal;
import java.util.List;

import com.school.dao.base.ICommonDAO;
import com.school.entity.evalteacher.AppraiseItemInfo;
import com.school.util.PageResult;


public interface IAppraiseItemDAO<AppraiseItemInfo> extends ICommonDAO<AppraiseItemInfo> {

	/**
	 * �õ���һ����ʶ(��ˮ��)
	 * @return
	 */
	public BigDecimal getNextSeqId();
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
	public abstract List<AppraiseItemInfo> getList(AppraiseItemInfo t,PageResult presult);
	
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
	public List<Object> getSaveSql(AppraiseItemInfo t,StringBuilder sqlbuilder);
	
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
	 * ��ȡtitle
	 * @param t
	 * @return
	 */
	public List getAppraiseTitle(AppraiseItemInfo t,BigDecimal targetid,Boolean order);
}