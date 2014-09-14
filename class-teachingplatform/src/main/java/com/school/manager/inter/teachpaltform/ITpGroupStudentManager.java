
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.TpGroupStudent;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;

import java.util.List;
import java.util.Map;

public interface ITpGroupStudentManager  extends IBaseManager<TpGroupStudent> {

    public List<Map<String,Object>> getNoGroupStudentList(Integer classid,Integer classtype,Integer userid,String termid);

    public List<TpGroupStudent> getGroupStudentByClass(TpGroupStudent gs,PageResult presult);
} 
