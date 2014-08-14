
package com.school.dao.inter.teachpaltform.paper;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.paper.PaperQuestion;
import com.school.util.PageResult;

import java.util.List;
import java.util.Map;

public interface IPaperQuestionDAO extends ICommonDAO<PaperQuestion>{
    Float getSumScore(PaperQuestion paperQuestion);
    /**
     * ��ѯ�Ծ������������������������Ӽ���
     * @param paperid
     * @return
     */
    Integer paperQuesCount(final Long paperid);
    Boolean updateQuesTeamScore(final PaperQuestion paperQuestion);
    public List<PaperQuestion> getQuestionByPaper(final Long paperid,final Integer classid,final Integer classtype);
    public List<PaperQuestion> getPaperTeamQuestionList(final PaperQuestion p,PageResult pageResult);

    public List<Object> getSynchroSql(final PaperQuestion paperquestion, StringBuilder sqlbuilder);

    public List<Map<String,Object>> getPaperQuesAllId(final Long paperid);

    /**
     * �õ��Ծ��µ����з�����ĳ�����
     * @param paperid
     * @param quesid
     * @return
     */
    public List<Map<String,Object>> getPaperQuesAllScore(final Long paperid,final Long quesid);

    /**
     * �õ��Ծ����������Count
     * @param paperid
     * @return
     */
    public List<Map<String,Object>> getZGTCount(final Long paperid);
}
