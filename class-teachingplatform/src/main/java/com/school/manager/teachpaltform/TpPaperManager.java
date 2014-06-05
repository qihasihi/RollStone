
package  com.school.manager.teachpaltform;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.ITpPaperDAO;

import com.school.entity.teachpaltform.TpPaperInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITpPaperManager;
import com.school.util.PageResult;

@Service
public class  TpPaperManager extends BaseManager<TpPaperInfo> implements ITpPaperManager  { 
	
	private ITpPaperDAO tppaperdao;
	
	@Autowired
	@Qualifier("tpPaperDAO")
	public void setTppaperdao(ITpPaperDAO tppaperdao) {
		this.tppaperdao = tppaperdao;
	}
	
	public Boolean doSave(TpPaperInfo tppaperinfo) {
		return this.tppaperdao.doSave(tppaperinfo);
	}
	
	public Boolean doDelete(TpPaperInfo tppaperinfo) {
		return this.tppaperdao.doDelete(tppaperinfo);
	}

	public Boolean doUpdate(TpPaperInfo tppaperinfo) {
		return this.tppaperdao.doUpdate(tppaperinfo);
	}
	
	public List<TpPaperInfo> getList(TpPaperInfo tppaperinfo, PageResult presult) {
		return this.tppaperdao.getList(tppaperinfo,presult);	
	}
	
	public List<Object> getSaveSql(TpPaperInfo tppaperinfo, StringBuilder sqlbuilder) {
		return this.tppaperdao.getSaveSql(tppaperinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(TpPaperInfo tppaperinfo, StringBuilder sqlbuilder) {
		return this.tppaperdao.getDeleteSql(tppaperinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(TpPaperInfo tppaperinfo, StringBuilder sqlbuilder) {
		return this.tppaperdao.getUpdateSql(tppaperinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.tppaperdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public TpPaperInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<TpPaperInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return tppaperdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}
}

