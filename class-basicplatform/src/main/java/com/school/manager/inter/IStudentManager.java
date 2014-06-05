
package  com.school.manager.inter;

import java.util.List;

import com.school.entity.StudentInfo;
import com.school.manager.base.IBaseManager;

public interface IStudentManager  extends IBaseManager<StudentInfo> { 
	public List<StudentInfo> getStudentByClass(Integer classid,String year,String pattern);
} 
