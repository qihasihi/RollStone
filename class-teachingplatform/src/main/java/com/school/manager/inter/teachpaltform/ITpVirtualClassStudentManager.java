
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.TpVirtualClassStudent;
import com.school.manager.base.IBaseManager;

import java.util.List;
import java.util.Map;

public interface ITpVirtualClassStudentManager  extends IBaseManager<TpVirtualClassStudent> {

    public List<Map<String,Object>> getStudentList(String grade,Integer classid,String stuname,String year,Integer virclassid,Integer dcSchoolID);
} 
