
package com.school.manager.inter.teachpaltform;

import java.util.List;

import com.school.entity.CommentInfo;
import com.school.entity.teachpaltform.QuestionAnswer;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;

public interface IQuestionAnswerManager  extends IBaseManager<QuestionAnswer> { 
	List<QuestionAnswer> getListNoRepeat(QuestionAnswer t);

    public List<QuestionAnswer>getResouceStuNoteList(QuestionAnswer q,PageResult pageResult);
    public List<QuestionAnswer>getResouceStuNoteTreeList(QuestionAnswer q,PageResult pageResult);
} 
