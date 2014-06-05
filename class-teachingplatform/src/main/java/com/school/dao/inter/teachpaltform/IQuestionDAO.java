
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.QuestionInfo;

import java.util.List;

public interface IQuestionDAO extends ICommonDAO<QuestionInfo>{
    /**
     * �õ�ͬ����SQL
     * @param entity  ����ʵ��
     * @param sqlbuilder  ������SQL��䣬����ʵ������
     * @return
     */
    public List<Object> getSynchroSql(QuestionInfo entity,StringBuilder sqlbuilder);

}
