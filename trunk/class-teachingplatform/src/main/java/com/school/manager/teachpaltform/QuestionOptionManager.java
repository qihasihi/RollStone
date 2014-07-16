
package  com.school.manager.teachpaltform;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.IQuestionOptionDAO;

import com.school.entity.teachpaltform.QuestionOption;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.IQuestionOptionManager;
import com.school.util.PageResult;

@Service
public class  QuestionOptionManager extends BaseManager<QuestionOption> implements IQuestionOptionManager  { 
	
	private IQuestionOptionDAO questionoptiondao;
	
	@Autowired
	@Qualifier("questionOptionDAO")
	public void setQuestionoptiondao(IQuestionOptionDAO questionoptiondao) {
		this.questionoptiondao = questionoptiondao;
	}
	
	public Boolean doSave(QuestionOption questionoption) {
		return this.questionoptiondao.doSave(questionoption);
	}
	
	public Boolean doDelete(QuestionOption questionoption) {
		return this.questionoptiondao.doDelete(questionoption);
	}

	public Boolean doUpdate(QuestionOption questionoption) {
		return this.questionoptiondao.doUpdate(questionoption);
	}
	
	public List<QuestionOption> getList(QuestionOption questionoption, PageResult presult) {
		return this.questionoptiondao.getList(questionoption,presult);	
	}
	
	public List<Object> getSaveSql(QuestionOption questionoption, StringBuilder sqlbuilder) {
		return this.questionoptiondao.getSaveSql(questionoption,sqlbuilder);
	}

	public List<Object> getDeleteSql(QuestionOption questionoption, StringBuilder sqlbuilder) {
		return this.questionoptiondao.getDeleteSql(questionoption,sqlbuilder);
	}

	public List<Object> getUpdateSql(QuestionOption questionoption, StringBuilder sqlbuilder) {
		return this.questionoptiondao.getUpdateSql(questionoption,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.questionoptiondao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(QuestionOption entity,StringBuilder sqlbuilder){
        return this.questionoptiondao.getSynchroSql(entity,sqlbuilder);
    }
	public QuestionOption getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<QuestionOption> getBaseDAO() {
		// TODO Auto-generated method stub
		return questionoptiondao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}
}

