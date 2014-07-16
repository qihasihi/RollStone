
package  com.school.manager.inter.resource;

import com.school.entity.resource.AccessInfo;
import com.school.manager.base.IBaseManager;

public interface IAccessManager  extends IBaseManager<AccessInfo> { 
	
	public Boolean doSaveOrUpdate(AccessInfo accessinfo) ;

} 
