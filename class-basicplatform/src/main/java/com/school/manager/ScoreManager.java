
package  com.school.manager;

import java.util.List;
import java.util.UUID;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.IScoreDAO;

import com.school.entity.ScoreInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IScoreManager;
import com.school.util.PageResult;

@Service
public class  ScoreManager extends BaseManager<ScoreInfo> implements IScoreManager  { 
	
	private IScoreDAO scoredao;
	
	@Autowired
	// @Qualifier("scoreinfoDAO")
	public void setScoredao(IScoreDAO scoredao) {
		this.scoredao = scoredao;
	}
	
	public Boolean doSave(ScoreInfo scoreinfo) {
		return scoredao.doSave(scoreinfo);
	}
	
	public Boolean doDelete(ScoreInfo scoreinfo) {
		return scoredao.doDelete(scoreinfo);
	}

	public Boolean doUpdate(ScoreInfo scoreinfo) {
		return scoredao.doUpdate(scoreinfo);
	}
	
	public List<ScoreInfo> getList(ScoreInfo scoreinfo, PageResult presult) {
		return scoredao.getList(scoreinfo,presult);	
	}
	
	public List<Object> getSaveSql(ScoreInfo scoreinfo, StringBuilder sqlbuilder) {
		return scoredao.getSaveSql(scoreinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(ScoreInfo scoreinfo, StringBuilder sqlbuilder) {
		return scoredao.getDeleteSql(scoreinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(ScoreInfo scoreinfo, StringBuilder sqlbuilder) {
		return scoredao.getUpdateSql(scoreinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return scoredao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public ScoreInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<ScoreInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}
}

