
package  com.school.manager.teachpaltform;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.ITpTaskAllotDAO;

import com.school.entity.teachpaltform.TpTaskAllotInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITpTaskAllotManager;
import com.school.util.PageResult;

@Service
public class  TpTaskAllotManager extends BaseManager<TpTaskAllotInfo> implements ITpTaskAllotManager  { 
	
	private ITpTaskAllotDAO tptaskallotdao;
	
	@Autowired
	@Qualifier("tpTaskAllotDAO")
	public void setTptaskallotdao(ITpTaskAllotDAO tptaskallotdao) {
		this.tptaskallotdao = tptaskallotdao;
	}
	
	public Boolean doSave(TpTaskAllotInfo tptaskallotinfo) {
		return this.tptaskallotdao.doSave(tptaskallotinfo);
	}
	
	public Boolean doDelete(TpTaskAllotInfo tptaskallotinfo) {
		return this.tptaskallotdao.doDelete(tptaskallotinfo);
	}

	public Boolean doUpdate(TpTaskAllotInfo tptaskallotinfo) {
		return this.tptaskallotdao.doUpdate(tptaskallotinfo);
	}
	
	public List<TpTaskAllotInfo> getList(TpTaskAllotInfo tptaskallotinfo, PageResult presult) {
		return this.tptaskallotdao.getList(tptaskallotinfo,presult);	
	}
	
	public List<Object> getSaveSql(TpTaskAllotInfo tptaskallotinfo, StringBuilder sqlbuilder) {
		return this.tptaskallotdao.getSaveSql(tptaskallotinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(TpTaskAllotInfo tptaskallotinfo, StringBuilder sqlbuilder) {
		return this.tptaskallotdao.getDeleteSql(tptaskallotinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(TpTaskAllotInfo tptaskallotinfo, StringBuilder sqlbuilder) {
		return this.tptaskallotdao.getUpdateSql(tptaskallotinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.tptaskallotdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public TpTaskAllotInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<TpTaskAllotInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return tptaskallotdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}
}

