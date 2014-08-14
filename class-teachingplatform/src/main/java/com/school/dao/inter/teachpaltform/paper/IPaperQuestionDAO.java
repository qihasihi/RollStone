
package com.school.dao.inter.teachpaltform.paper;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.paper.PaperQuestion;
import com.school.util.PageResult;

import java.util.List;
import java.util.Map;

public interface IPaperQuestionDAO extends ICommonDAO<PaperQuestion>{
    Float getSumScore(PaperQuestion paperQuestion);
    /**
     * 查询试卷下试题数量（包含组试题子集）
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
     * 得到试卷下的所有分数或某题分数
     * @param paperid
     * @param quesid
     * @return
     */
    public List<Map<String,Object>> getPaperQuesAllScore(final Long paperid,final Long quesid);

    /**
     * 得到试卷下主观题的Count
     * @param paperid
     * @return
     */
    public List<Map<String,Object>> getZGTCount(final Long paperid);
}
