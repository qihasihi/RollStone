
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.TpVirtualClassStudent;

import java.util.List;
import java.util.Map;

public interface ITpVirtualClassStudentDAO extends ICommonDAO<TpVirtualClassStudent>{

    public List<Map<String,Object>> getStudentList(String grade,Integer classid,String stuname,String year,Integer virclassid,Integer dcSchoolID);
}
