
package  com.school.manager.teachpaltform.paper;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.paper.IPaperDAO;

import com.school.entity.teachpaltform.paper.PaperInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.paper.IPaperManager;
import com.school.util.PageResult;

@Service
public class  PaperManager extends BaseManager<PaperInfo> implements IPaperManager  { 
	
	private IPaperDAO paperdao;
	
	@Autowired
	@Qualifier("paperDAO")
	public void setPaperdao(IPaperDAO paperdao) {
		this.paperdao = paperdao;
	}
	
	public Boolean doSave(PaperInfo paperinfo) {
		return this.paperdao.doSave(paperinfo);
	}
	
	public Boolean doDelete(PaperInfo paperinfo) {
		return this.paperdao.doDelete(paperinfo);
	}

	public Boolean doUpdate(PaperInfo paperinfo) {
		return this.paperdao.doUpdate(paperinfo);
	}
	
	public List<PaperInfo> getList(PaperInfo paperinfo, PageResult presult) {
		return this.paperdao.getList(paperinfo,presult);	
	}
	
	public List<Object> getSaveSql(PaperInfo paperinfo, StringBuilder sqlbuilder) {
		return this.paperdao.getSaveSql(paperinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(PaperInfo paperinfo, StringBuilder sqlbuilder) {
		return this.paperdao.getDeleteSql(paperinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(PaperInfo paperinfo, StringBuilder sqlbuilder) {
		return this.paperdao.getUpdateSql(paperinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.paperdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public PaperInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<PaperInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return paperdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}
}

