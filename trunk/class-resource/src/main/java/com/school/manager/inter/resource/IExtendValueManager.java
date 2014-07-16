
package  com.school.manager.inter.resource;

import java.util.List;

import com.school.entity.resource.ExtendValueInfo;
import com.school.manager.base.IBaseManager;

public interface IExtendValueManager  extends IBaseManager<ExtendValueInfo> { 

	public List<ExtendValueInfo> getNotInList(String extendid,String valueList);
	
	public List<Object> getUpdateOrderBySql(ExtendValueInfo extendvalueinfo,
			StringBuilder sqlbuilder);
} 
