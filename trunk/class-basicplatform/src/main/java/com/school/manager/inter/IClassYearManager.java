
package  com.school.manager.inter;

import java.util.List;
import java.util.Map;

import com.school.entity.ClassYearInfo;
import com.school.manager.base.IBaseManager;

public interface IClassYearManager  extends IBaseManager<ClassYearInfo> { 

	public List<ClassYearInfo> getCurrentYearList(String flag);
	/**
	 * �õ���ȥ�����
	 * @param cyear
	 * @return
	 */
	public List<Map<String,Object>> getClassYearPree(ClassYearInfo cyear);
} 
