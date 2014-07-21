
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.QuestionOption;
import com.school.util.PageResult;

import java.util.List;

public interface IQuestionOptionDAO extends ICommonDAO<QuestionOption>{
    public List<QuestionOption>getPaperQuesOptionList(QuestionOption questionOption,PageResult presult);
    /**
     * �õ�ͬ����SQL
     * @param entity  ����ʵ��
     * @param sqlbuilder  ������SQL��䣬����ʵ������
     * @return
     */
    public List<Object> getSynchroSql(QuestionOption entity,StringBuilder sqlbuilder);
}
