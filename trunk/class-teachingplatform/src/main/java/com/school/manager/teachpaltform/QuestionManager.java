
package  com.school.manager.teachpaltform;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.IQuestionDAO;

import com.school.entity.teachpaltform.QuestionInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.IQuestionManager;
import com.school.util.PageResult;

@Service
public class  QuestionManager extends BaseManager<QuestionInfo> implements IQuestionManager  { 
	
	private IQuestionDAO questiondao;
	
	@Autowired
	@Qualifier("questionDAO")
	public void setQuestiondao(IQuestionDAO questiondao) {
		this.questiondao = questiondao;
	}
	
	public Boolean doSave(QuestionInfo questioninfo) {
		return this.questiondao.doSave(questioninfo);
	}
	
	public Boolean doDelete(QuestionInfo questioninfo) {
		return this.questiondao.doDelete(questioninfo);
	}

	public Boolean doUpdate(QuestionInfo questioninfo) {
		return this.questiondao.doUpdate(questioninfo);
	}
	
	public List<QuestionInfo> getList(QuestionInfo questioninfo, PageResult presult) {
		return this.questiondao.getList(questioninfo,presult);	
	}
	
	public List<Object> getSaveSql(QuestionInfo questioninfo, StringBuilder sqlbuilder) {
		return this.questiondao.getSaveSql(questioninfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(QuestionInfo questioninfo, StringBuilder sqlbuilder) {
		return this.questiondao.getDeleteSql(questioninfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(QuestionInfo questioninfo, StringBuilder sqlbuilder) {
		return this.questiondao.getUpdateSql(questioninfo,sqlbuilder);
	}
    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(QuestionInfo entity,StringBuilder sqlbuilder){
        return this.questiondao.getSynchroSql(entity,sqlbuilder);
    }
	
	public QuestionInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<QuestionInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return questiondao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}
}

