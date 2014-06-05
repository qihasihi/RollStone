
package  com.school.manager.inter;

import java.util.List;

import com.school.entity.PageRightInfo;
import com.school.manager.base.IBaseManager;

public interface IPageRightManager  extends IBaseManager<PageRightInfo> { 
	/**
	 * 得到用户的栏目访问权限
	 * @param userref
	 * @return
	 */
	public List<PageRightInfo> getUserColumnList(String columnid,String userref);
} 
