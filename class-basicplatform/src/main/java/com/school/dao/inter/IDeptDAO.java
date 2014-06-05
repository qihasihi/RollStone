
package com.school.dao.inter;

import java.util.List;

import com.school.dao.base.ICommonDAO;
import com.school.entity.DeptInfo;
import com.school.entity.DeptUser;

public interface  IDeptDAO extends ICommonDAO<DeptInfo>{
	
	List<DeptUser> getNotInDeptUser(Integer roleid,String typeid,String name);
}
  