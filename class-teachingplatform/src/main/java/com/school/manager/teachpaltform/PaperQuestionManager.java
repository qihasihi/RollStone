
package  com.school.manager.teachpaltform;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.IPaperQuestionDAO;

import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.IPaperQuestionManager;
import com.school.util.PageResult;

@Service
public class  PaperQuestionManager extends BaseManager<PaperQuestion> implements IPaperQuestionManager  { 
	
	private IPaperQuestionDAO paperquestiondao;
	
	@Autowired
	@Qualifier("paperQuestionDAO")
	public void setPaperquestiondao(IPaperQuestionDAO paperquestiondao) {
		this.paperquestiondao = paperquestiondao;
	}
	
	public Boolean doSave(PaperQuestion paperquestion) {
		return this.paperquestiondao.doSave(paperquestion);
	}
	
	public Boolean doDelete(PaperQuestion paperquestion) {
		return this.paperquestiondao.doDelete(paperquestion);
	}

	public Boolean doUpdate(PaperQuestion paperquestion) {
		return this.paperquestiondao.doUpdate(paperquestion);
	}
	
	public List<PaperQuestion> getList(PaperQuestion paperquestion, PageResult presult) {
		return this.paperquestiondao.getList(paperquestion,presult);	
	}
	
	public List<Object> getSaveSql(PaperQuestion paperquestion, StringBuilder sqlbuilder) {
		return this.paperquestiondao.getSaveSql(paperquestion,sqlbuilder);
	}

	public List<Object> getDeleteSql(PaperQuestion paperquestion, StringBuilder sqlbuilder) {
		return this.paperquestiondao.getDeleteSql(paperquestion,sqlbuilder);
	}

	public List<Object> getUpdateSql(PaperQuestion paperquestion, StringBuilder sqlbuilder) {
		return this.paperquestiondao.getUpdateSql(paperquestion,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.paperquestiondao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public PaperQuestion getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<PaperQuestion> getBaseDAO() {
		// TODO Auto-generated method stub
		return paperquestiondao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}
}

