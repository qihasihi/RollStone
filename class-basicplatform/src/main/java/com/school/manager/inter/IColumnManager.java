
package  com.school.manager.inter;

import java.util.List;

import com.school.entity.ColumnInfo;
import com.school.manager.base.IBaseManager;

public interface IColumnManager  extends IBaseManager<ColumnInfo> { 
	/**
	 * �õ��û�����Ŀ����Ȩ��
	 * @param userref
	 * @return
	 */
	public List<ColumnInfo> getUserColumnList(String userref);
} 
