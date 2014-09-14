
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.TpGroupStudent;
import com.school.util.PageResult;

import java.util.List;
import java.util.Map;

public interface ITpGroupStudentDAO extends ICommonDAO<TpGroupStudent>{

    public List<Map<String,Object>> getNoGroupStudentList(Integer classid,Integer classtype,Integer userid,String termid);

    public List<TpGroupStudent> getGroupStudentByClass(TpGroupStudent gs,PageResult presult);

}
