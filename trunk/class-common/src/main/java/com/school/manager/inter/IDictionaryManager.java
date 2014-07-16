
package  com.school.manager.inter;

import java.util.List;
import java.util.Map;

import com.school.entity.DictionaryInfo;
import com.school.manager.base.IBaseManager;

public interface IDictionaryManager  extends IBaseManager<DictionaryInfo> { 
	/**
	 * ����type,�õ�����
	 * @param type
	 * @param dicenum DictionaryInfo.DictOrderEnum �����ֶ�
	 * @return
	 */
	public List<DictionaryInfo> getDictionaryByType(String type);
	
	public Map<String, Object> getDicMapByType(String type) ;
} 
