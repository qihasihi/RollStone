
package  com.school.manager.inter;

import java.util.List;
import java.util.Map;

import com.school.entity.DictionaryInfo;
import com.school.manager.base.IBaseManager;

public interface IDictionaryManager  extends IBaseManager<DictionaryInfo> { 
	/**
	 * 根据type,得到数据
	 * @param type
	 * @param dicenum DictionaryInfo.DictOrderEnum 排序字段
	 * @return
	 */
	public List<DictionaryInfo> getDictionaryByType(String type);
	
	public Map<String, Object> getDicMapByType(String type) ;
} 
