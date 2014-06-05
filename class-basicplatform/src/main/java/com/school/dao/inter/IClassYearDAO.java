
package com.school.dao.inter;

import java.util.List;
import java.util.Map;

import com.school.dao.base.ICommonDAO;
import com.school.entity.ClassYearInfo;

public interface  IClassYearDAO extends ICommonDAO<ClassYearInfo>{

	public List<ClassYearInfo> getCurrentYearList(String flag);
	
	/**
	 * �õ���ȥ�����
	 * @param cyear
	 * @return
	 */
	public List<Map<String,Object>> getClassYearPree(ClassYearInfo cyear);
}
