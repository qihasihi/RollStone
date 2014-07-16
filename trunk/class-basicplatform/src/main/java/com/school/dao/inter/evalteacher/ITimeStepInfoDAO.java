package com.school.dao.inter.evalteacher;

import java.util.List;

import com.school.dao.base.ICommonDAO;
import com.school.util.PageResult;

public interface ITimeStepInfoDAO<TimeStepInfo> extends ICommonDAO<TimeStepInfo> {
	/**
	 * ���
	 * @param t
	 * @return
	 */
	public abstract Integer save(TimeStepInfo t);
	
	/**
	 * �޸�
	 * @param t
	 * @return
	 */
	public abstract Integer update(TimeStepInfo t);
	
	/**
	 * ɾ��
	 * @param t
	 * @return
	 */
	public abstract Integer delete(TimeStepInfo t);
	
	/**
	 * ��ҳList
	 * @param t
	 * @param result
	 * @return
	 */
	public abstract List<TimeStepInfo> getPageList(TimeStepInfo t,PageResult result);
	
	/**
	 * �жϹ���ʱ��
	 * @param t
	 * @return
	 */
	public int getCount(TimeStepInfo t);
	
	/**
	 * �����������ȡ����һ������
	 * @param t
	 * @return
	 */
	public List<TimeStepInfo> getListByYear(TimeStepInfo t);
	
	
}
