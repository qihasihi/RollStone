
package  com.school.manager.inter;

import java.util.List;

import com.school.entity.ColumnInfo;
import com.school.entity.EttColumnInfo;
import com.school.manager.base.IBaseManager;

public interface IColumnManager  extends IBaseManager<ColumnInfo> { 
	/**
	 * 得到用户的栏目访问权限
	 * @param userref
	 * @return
	 */
	public List<ColumnInfo> getUserColumnList(String userref);

    /**
     * 同步ETT栏目信息
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getEttColumnSynchro(final EttColumnInfo entity,StringBuilder sqlbuilder);
} 
