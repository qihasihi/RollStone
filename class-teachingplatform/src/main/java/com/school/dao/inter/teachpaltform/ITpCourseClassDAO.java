
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.TpCourseClass;

import java.util.List;
import java.util.Map;

public interface ITpCourseClassDAO extends ICommonDAO<TpCourseClass>{
    public List<Map<String,Object>> getTpClassCourse(Long courseid,Integer userid,String termid);
}
