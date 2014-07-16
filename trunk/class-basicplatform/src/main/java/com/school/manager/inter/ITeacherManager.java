
package  com.school.manager.inter;

import java.util.List;

import com.school.entity.TeacherInfo;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;

public interface ITeacherManager  extends IBaseManager<TeacherInfo> { 

	public List<TeacherInfo> getTeacherListByUserId(String userid,String year);
	
	public TeacherInfo loadBy(String userRef);
	
	public List<TeacherInfo> getListByTchnameOrUsername(TeacherInfo obj, PageResult presult);
} 
