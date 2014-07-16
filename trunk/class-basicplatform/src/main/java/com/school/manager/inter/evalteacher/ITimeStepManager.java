package com.school.manager.inter.evalteacher;

import java.util.List;

import com.school.entity.evalteacher.TimeStepInfo;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;


public interface ITimeStepManager extends IBaseManager<TimeStepInfo> {
	/**
	 * 添加
	 * @param t
	 * @return
	 */
	public abstract Integer save(TimeStepInfo t);
	
	/**
	 * 修改
	 * @param t
	 * @return
	 */
	public abstract Integer update(TimeStepInfo t);
	
	/**
	 * 删除
	 * @param t
	 * @return
	 */
	public abstract Integer delete(TimeStepInfo t);
	
	/**
	 * 分页List
	 * @param t
	 * @param result
	 * @return
	 */
	public abstract List<TimeStepInfo> getPageList(TimeStepInfo t,PageResult result);
	
	/**
	 * 判断过期时间
	 * @param t
	 * @return
	 */
	public int getCount(TimeStepInfo t);
	
	/**
	 * 根据评价年取List
	 * @param t
	 * @return
	 */
	public List<TimeStepInfo> getListByYear(TimeStepInfo t) ;
}
