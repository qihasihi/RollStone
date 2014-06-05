
package  com.school.manager.inter;

import java.util.List;

import com.school.entity.ColumnInfo;
import com.school.manager.base.IBaseManager;

public interface IColumnManager  extends IBaseManager<ColumnInfo> { 
	/**
	 * 得到用户的栏目访问权限
	 * @param userref
	 * @return
	 */
	public List<ColumnInfo> getUserColumnList(String userref);
} 
