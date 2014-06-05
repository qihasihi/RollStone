
package  com.school.manager.inter.resource;

import java.util.List;
import java.util.Map;

import com.school.entity.resource.ExtendInfo;
import com.school.manager.base.IBaseManager;

public interface IExtendManager  extends IBaseManager<ExtendInfo> { 
	
	public Integer doSaveGetId(ExtendInfo extendinfo);
	/**
	 * 查找所有的树节点
	 * @param rootid
	 * @return
	 */
	public List<Map<String, Object>> getExtendCheckShu(String rootid);
} 
