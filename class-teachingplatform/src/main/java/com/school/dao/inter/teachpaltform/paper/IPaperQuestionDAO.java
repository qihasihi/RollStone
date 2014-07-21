
package com.school.dao.inter.teachpaltform.paper;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.paper.PaperQuestion;
import com.school.util.PageResult;

import java.util.List;

public interface IPaperQuestionDAO extends ICommonDAO<PaperQuestion>{
    Float getSumScore(PaperQuestion paperQuestion);
    public List<PaperQuestion> getQuestionByPaper(Long paperid);
    public List<PaperQuestion> getPaperTeamQuestionList(PaperQuestion p,PageResult pageResult);

    public List<Object> getSynchroSql(PaperQuestion paperquestion, StringBuilder sqlbuilder);
}
