
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.TpCourseQuestion;

import java.util.List;

public interface ITpCourseQuestionDAO extends ICommonDAO<TpCourseQuestion>{
    /**
     * �õ�ͬ����SQL
     * @param entity  ����ʵ��
     * @param sqlbuilder  ������SQL��䣬����ʵ������
     * @return
     */
    public List<Object> getSynchroSql(TpCourseQuestion entity,StringBuilder sqlbuilder);
}
