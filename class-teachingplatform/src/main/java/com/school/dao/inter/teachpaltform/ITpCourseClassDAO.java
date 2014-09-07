
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.TpCourseClass;

import java.util.List;
import java.util.Map;

public interface ITpCourseClassDAO extends ICommonDAO<TpCourseClass>{
    public List<Map<String,Object>> getTpClassCourse(Long courseid,Integer userid,String termid);
    /**
     * 得到记录，根据班级ID,TERMID
     * 查询列  DISTINCT sub.subject_name,sub.lzx_subject_id,sub.subject_type
     * @param clsid
     * @param termid
     *  @author zhengzhou
     * @return
     */
    public List<TpCourseClass> getTpCourseClassByClsTermId(final Integer clsid,final String termid);
    /**
     * 得到班级记录，根据userid,TERMID,subjectid
     * 查询列  DISTINCT cls.class_grade,cls.year,cls.class_name
     * @param clsid
     * @param termid
     * @return
     * @author zhengzhou
     */
    public List<TpCourseClass> getTpClsEntityByUserSubTermId(final Integer subjectid,final Integer userid,final String termid);
}
