
package com.school.manager.teachpaltform;

import java.util.List;
import java.util.UUID;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.IQuestionAnswerDAO;

import com.school.entity.teachpaltform.QuestionAnswer;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.IQuestionAnswerManager;
import com.school.util.PageResult;

@Service
public class  QuestionAnswerManager extends BaseManager<QuestionAnswer> implements IQuestionAnswerManager  { 
	
	private IQuestionAnswerDAO questionanswerdao;
	
	@Autowired 
	@Qualifier("questionAnswerDAO") 
	public void setQuestionanswerdao(IQuestionAnswerDAO questionanswerdao) {
		this.questionanswerdao = questionanswerdao;
	}
	
	public Boolean doSave(QuestionAnswer questionanswer) {
		return this.questionanswerdao.doSave(questionanswer);
	}
	
	public Boolean doDelete(QuestionAnswer questionanswer) {
		return this.questionanswerdao.doDelete(questionanswer);
	}

	public Boolean doUpdate(QuestionAnswer questionanswer) {
		return this.questionanswerdao.doUpdate(questionanswer);
	}
	
	public List<QuestionAnswer> getList(QuestionAnswer questionanswer, PageResult presult) {
		return this.questionanswerdao.getList(questionanswer,presult);	
	}
	
	public List<Object> getSaveSql(QuestionAnswer questionanswer, StringBuilder sqlbuilder) {
		return this.questionanswerdao.getSaveSql(questionanswer,sqlbuilder);
	}

	public List<Object> getDeleteSql(QuestionAnswer questionanswer, StringBuilder sqlbuilder) {
		return this.questionanswerdao.getDeleteSql(questionanswer,sqlbuilder);
	}

	public List<Object> getUpdateSql(QuestionAnswer questionanswer, StringBuilder sqlbuilder) {
		return this.questionanswerdao.getUpdateSql(questionanswer,sqlbuilder);
	}

	public QuestionAnswer getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<QuestionAnswer> getBaseDAO() {
		// TODO Auto-generated method stub
		return questionanswerdao;
	}
	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}

	public List<QuestionAnswer> getListNoRepeat(QuestionAnswer t) {
		// TODO Auto-generated method stub
		return this.questionanswerdao.getListNoRepeat(t);
	}

    public List<QuestionAnswer> getResouceStuNoteList(QuestionAnswer q, PageResult pageResult) {
        return this.questionanswerdao.getResouceStuNoteList(q,pageResult);
    }

    public List<QuestionAnswer> getResouceStuNoteTreeList(QuestionAnswer q, PageResult pageResult) {
        return this.questionanswerdao.getResouceStuNoteTreeList(q,pageResult);
    }
}

