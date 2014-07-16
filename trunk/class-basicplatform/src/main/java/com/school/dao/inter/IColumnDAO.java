
package com.school.dao.inter;

import java.util.List;

import com.school.dao.base.ICommonDAO;
import com.school.entity.ColumnInfo;
import com.school.entity.EttColumnInfo;
import com.school.util.PageResult;

public interface IColumnDAO extends ICommonDAO<ColumnInfo>{
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
    /**
     * ��ѯETT��Ŀ��Ϣ
     * @param entity
     * @param presult
     * @return
     */
    public List<EttColumnInfo> getEttColumnSplit(final EttColumnInfo entity,PageResult presult);
}
