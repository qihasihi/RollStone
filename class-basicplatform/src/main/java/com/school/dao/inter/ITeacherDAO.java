
package com.school.dao.inter;

import java.util.List;

import com.school.dao.base.ICommonDAO;
import com.school.entity.TeacherInfo;
import com.school.util.PageResult;

public interface  ITeacherDAO extends ICommonDAO<TeacherInfo>{

	public List<TeacherInfo> getTeacherListByUserId(String userid, String year);
	
	public List<TeacherInfo> getListByTchnameOrUsername(TeacherInfo obj, PageResult presult);
}
