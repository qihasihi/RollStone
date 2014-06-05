
package  com.school.manager.inter;

import java.util.List;

import com.school.entity.DeptInfo;
import com.school.entity.DeptUser;
import com.school.manager.base.IBaseManager;

public interface IDeptManager  extends IBaseManager<DeptInfo> { 
	List<DeptUser> getNotInDeptUser(Integer roleid,String typeid,String name);
} 
