
package com.school.dao.inter;

import java.util.List;

import com.school.dao.base.ICommonDAO;
import com.school.entity.ClassInfo;

public interface  IClassDAO extends ICommonDAO<ClassInfo>{
	/**
	 * 根据UserRef,YEAR,PAttern得到班级
	 * @param userref
	 * @param year
	 * @param pattern
	 * @return
	 */
	public List<ClassInfo> getClassByUserYearPattern(String userref,String year,String pattern);
	
	/**
	 * 升级操作
	 * @param year
	 * @return
	 */
	public Boolean doClassLevelUp(String year,Integer dcschoolid);

    /**
     * 得到更新或添加的SQL
     * @param obj;
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSaveOrUpdateSql(ClassInfo obj, StringBuilder sqlbuilder);
}
