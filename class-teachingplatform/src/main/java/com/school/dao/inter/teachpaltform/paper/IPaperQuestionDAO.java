
package com.school.dao.inter.teachpaltform.paper;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.paper.PaperQuestion;

import java.util.List;

public interface IPaperQuestionDAO extends ICommonDAO<PaperQuestion>{
    Float getSumScore(PaperQuestion paperQuestion);
    public List<PaperQuestion> getQuestionByPaper(Integer paperid);

    public List<Object> getSynchroSql(PaperQuestion paperquestion, StringBuilder sqlbuilder);
}
