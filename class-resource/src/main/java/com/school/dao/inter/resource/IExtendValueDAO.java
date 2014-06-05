
package com.school.dao.inter.resource;

import java.util.List;

import com.school.dao.base.ICommonDAO;
import com.school.entity.resource.ExtendValueInfo;

public interface IExtendValueDAO extends ICommonDAO<ExtendValueInfo>{

	public List<ExtendValueInfo> getNotInList(String extendid,String valueList);
	
	public List<Object> getUpdateOrderBySql(ExtendValueInfo extendvalueinfo,
			StringBuilder sqlbuilder);
}
