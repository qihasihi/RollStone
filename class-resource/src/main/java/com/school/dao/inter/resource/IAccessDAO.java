
package com.school.dao.inter.resource;

import com.school.dao.base.ICommonDAO;
import com.school.entity.resource.AccessInfo;

public interface IAccessDAO extends ICommonDAO<AccessInfo>{
	public Boolean doSaveOrUpdate(AccessInfo accessinfo) ;

}
