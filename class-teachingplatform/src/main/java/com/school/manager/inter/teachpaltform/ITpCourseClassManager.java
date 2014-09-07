
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.TpCourseClass;
import com.school.manager.base.IBaseManager;

import java.util.List;
import java.util.Map;

public interface ITpCourseClassManager  extends IBaseManager<TpCourseClass> {
    public List<Map<String,Object>> getTpClassCourse(Long courseid,Integer userid,String termid);
    /**
     * 得到记录，根据班级ID,TERMID
     * 查询列  DISTINCT sub.subject_name,sub.lzx_subject_id,sub.subject_type
     * @param clsid
     * @param termid
     * @return
     */
    public List<TpCourseClass> getTpCourseClassByClsTermId(final Integer clsid,final String termid);
} 
