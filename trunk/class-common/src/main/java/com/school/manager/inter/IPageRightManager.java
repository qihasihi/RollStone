
package  com.school.manager.inter;

import java.util.List;

import com.school.entity.PageRightInfo;
import com.school.manager.base.IBaseManager;

public interface IPageRightManager  extends IBaseManager<PageRightInfo> { 
	/**
	 * �õ��û�����Ŀ����Ȩ��
	 * @param userref
	 * @return
	 */
	public List<PageRightInfo> getUserColumnList(String columnid,String userref);
} 
