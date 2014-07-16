
package com.school.dao.inter;

import java.util.List;

import com.school.dao.base.ICommonDAO;
import com.school.entity.StudentInfo;

public interface  IStudentDAO extends ICommonDAO<StudentInfo>{
	public List<StudentInfo> getStudentByClass(Integer classid,String year,String pattern);
}
