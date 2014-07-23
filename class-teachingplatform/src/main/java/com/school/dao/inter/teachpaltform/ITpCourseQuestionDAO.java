
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.TpCourseQuestion;
import com.school.util.PageResult;

import java.util.List;

public interface ITpCourseQuestionDAO extends ICommonDAO<TpCourseQuestion>{
    /**
     * �õ�ͬ����SQL
     * @param entity  ����ʵ��
     * @param sqlbuilder  ������SQL��䣬����ʵ������
     * @return
     */
    public List<Object> getSynchroSql(TpCourseQuestion entity,StringBuilder sqlbuilder);

    List<TpCourseQuestion>getQuestionTeamList(TpCourseQuestion tpCourseQuestion,PageResult pageResult);

    /**
     * ��ȡר���¿͹�������
     * @param tpCourseQuestion
     * @return
     */
    Integer getObjectiveQuesCount(TpCourseQuestion tpCourseQuestion);
}
