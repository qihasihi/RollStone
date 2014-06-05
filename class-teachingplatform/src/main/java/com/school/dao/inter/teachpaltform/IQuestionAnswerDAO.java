
package com.school.dao.inter.teachpaltform;

import java.util.List;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.QuestionAnswer;
import com.school.util.PageResult;

public interface IQuestionAnswerDAO extends ICommonDAO<QuestionAnswer>{
	List<QuestionAnswer> getListNoRepeat(QuestionAnswer t);


    public List<QuestionAnswer>getResouceStuNoteList(QuestionAnswer q,PageResult pageResult);
    public List<QuestionAnswer>getResouceStuNoteTreeList(QuestionAnswer q,PageResult pageResult);
}
