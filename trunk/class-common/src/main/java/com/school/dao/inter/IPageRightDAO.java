
package com.school.dao.inter;

import java.util.List;

import com.school.dao.base.ICommonDAO;
import com.school.entity.PageRightInfo;

public interface  IPageRightDAO extends ICommonDAO<PageRightInfo>{

	/**
	 * 得到用户的栏目访问权限
	 * @param userref
	 * @return
	 */
	public List<PageRightInfo> getUserColumnList(String columnid,String userref);
}
