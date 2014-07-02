
package  com.school.manager.inter;

import java.util.List;

import com.school.entity.ColumnInfo;
import com.school.entity.EttColumnInfo;
import com.school.manager.base.IBaseManager;

public interface IColumnManager  extends IBaseManager<ColumnInfo> { 
	/**
	 * �õ��û�����Ŀ����Ȩ��
	 * @param userref
	 * @return
	 */
	public List<ColumnInfo> getUserColumnList(String userref);

    /**
     * ͬ��ETT��Ŀ��Ϣ
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getEttColumnSynchro(final EttColumnInfo entity,StringBuilder sqlbuilder);
} 
