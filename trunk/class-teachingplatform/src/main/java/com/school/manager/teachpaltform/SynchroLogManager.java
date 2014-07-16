
package  com.school.manager.teachpaltform;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.ISynchroLogDAO;

import com.school.entity.teachpaltform.SynchroLogInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ISynchroLogManager;
import com.school.util.PageResult;

@Service
public class  SynchroLogManager extends BaseManager<SynchroLogInfo> implements ISynchroLogManager  { 
	
	private ISynchroLogDAO synchrologdao;
	
	@Autowired
	@Qualifier("synchroLogDAO")
	public void setSynchrologdao(ISynchroLogDAO synchrologdao) {
		this.synchrologdao = synchrologdao;
	}
	
	public Boolean doSave(SynchroLogInfo synchrologinfo) {
		return this.synchrologdao.doSave(synchrologinfo);
	}
	
	public Boolean doDelete(SynchroLogInfo synchrologinfo) {
		return this.synchrologdao.doDelete(synchrologinfo);
	}

	public Boolean doUpdate(SynchroLogInfo synchrologinfo) {
		return this.synchrologdao.doUpdate(synchrologinfo);
	}
	
	public List<SynchroLogInfo> getList(SynchroLogInfo synchrologinfo, PageResult presult) {
		return this.synchrologdao.getList(synchrologinfo,presult);	
	}
	
	public List<Object> getSaveSql(SynchroLogInfo synchrologinfo, StringBuilder sqlbuilder) {
		return this.synchrologdao.getSaveSql(synchrologinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(SynchroLogInfo synchrologinfo, StringBuilder sqlbuilder) {
		return this.synchrologdao.getDeleteSql(synchrologinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(SynchroLogInfo synchrologinfo, StringBuilder sqlbuilder) {
		return this.synchrologdao.getUpdateSql(synchrologinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.synchrologdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public SynchroLogInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<SynchroLogInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return synchrologdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}
}

