
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.TpCourseResource;

import java.util.List;

public interface ITpCourseResourceDAO extends ICommonDAO<TpCourseResource>{
    /**
     * �õ�ͬ����SQL
     * @param entity  ����ʵ��
     * @param sqlbuilder  ������SQL��䣬����ʵ������
     * @return
     */
    public List<Object> getSynchroSql(TpCourseResource entity,StringBuilder sqlbuilder);
    public Boolean doAddDynamic(TpCourseResource courseres);
}
