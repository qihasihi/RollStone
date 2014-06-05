
package com.school.dao.inter.resource;

import java.util.List;
import java.util.Map;

import com.school.dao.base.ICommonDAO;
import com.school.entity.resource.ExtendInfo;

public interface IExtendDAO extends ICommonDAO<ExtendInfo>{

	public Integer doSaveGetId(ExtendInfo extendinfo);
	
	/**
	 * 查找所有的树节点
	 * @param rootid
	 * @return
	 */
	public List<Map<String, Object>> getExtendCheckShu(String rootid);
}
