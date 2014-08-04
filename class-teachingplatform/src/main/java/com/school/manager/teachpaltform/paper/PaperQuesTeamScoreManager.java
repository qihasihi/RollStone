
package  com.school.manager.teachpaltform.paper;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.paper.IPaperQuesTeamScoreDAO;

import com.school.entity.teachpaltform.paper.PaperQuesTeamScore;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.paper.IPaperQuesTeamScoreManager;
import com.school.util.PageResult;

@Service
public class  PaperQuesTeamScoreManager extends BaseManager<PaperQuesTeamScore> implements IPaperQuesTeamScoreManager  { 
	
	private IPaperQuesTeamScoreDAO paperquesteamscoredao;
	
	@Autowired
	@Qualifier("paperQuesTeamScoreDAO")
	public void setPaperquesteamscoredao(IPaperQuesTeamScoreDAO paperquesteamscoredao) {
		this.paperquesteamscoredao = paperquesteamscoredao;
	}
	
	public Boolean doSave(PaperQuesTeamScore paperquesteamscore) {
		return this.paperquesteamscoredao.doSave(paperquesteamscore);
	}
	
	public Boolean doDelete(PaperQuesTeamScore paperquesteamscore) {
		return this.paperquesteamscoredao.doDelete(paperquesteamscore);
	}

	public Boolean doUpdate(PaperQuesTeamScore paperquesteamscore) {
		return this.paperquesteamscoredao.doUpdate(paperquesteamscore);
	}
	
	public List<PaperQuesTeamScore> getList(PaperQuesTeamScore paperquesteamscore, PageResult presult) {
		return this.paperquesteamscoredao.getList(paperquesteamscore,presult);	
	}
	
	public List<Object> getSaveSql(PaperQuesTeamScore paperquesteamscore, StringBuilder sqlbuilder) {
		return this.paperquesteamscoredao.getSaveSql(paperquesteamscore,sqlbuilder);
	}

	public List<Object> getDeleteSql(PaperQuesTeamScore paperquesteamscore, StringBuilder sqlbuilder) {
		return this.paperquesteamscoredao.getDeleteSql(paperquesteamscore,sqlbuilder);
	}

	public List<Object> getUpdateSql(PaperQuesTeamScore paperquesteamscore, StringBuilder sqlbuilder) {
		return this.paperquesteamscoredao.getUpdateSql(paperquesteamscore,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.paperquesteamscoredao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public PaperQuesTeamScore getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<PaperQuesTeamScore> getBaseDAO() {
		// TODO Auto-generated method stub
		return paperquesteamscoredao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}
}

