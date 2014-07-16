
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.TpCourseClass;
import com.school.manager.base.IBaseManager;

import java.util.List;
import java.util.Map;

public interface ITpCourseClassManager  extends IBaseManager<TpCourseClass> {
    public List<Map<String,Object>> getTpClassCourse(Long courseid,Integer userid,String termid);
} 
