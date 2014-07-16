
package  com.school.manager.resource;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.resource.ICheckDAO;

import com.school.entity.resource.CheckInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.resource.ICheckManager;
import com.school.util.PageResult;

@Service
public class  CheckManager extends BaseManager<CheckInfo> implements ICheckManager  { 
	
	private ICheckDAO checkdao;
	
	@Autowired
	@Qualifier("checkDAO")
	public void setCheckdao(ICheckDAO checkdao) {
		this.checkdao = checkdao;
	}
	
	public Boolean doSave(CheckInfo checkinfo) {
		return checkdao.doSave(checkinfo);
	}
	
	public Boolean doDelete(CheckInfo checkinfo) {
		return checkdao.doDelete(checkinfo);
	}

	public Boolean doUpdate(CheckInfo checkinfo) {
		return checkdao.doUpdate(checkinfo);
	}
	
	public List<CheckInfo> getList(CheckInfo checkinfo, PageResult presult) {
		return checkdao.getList(checkinfo,presult);	
	}
	
	public List<Object> getSaveSql(CheckInfo checkinfo, StringBuilder sqlbuilder) {
		return checkdao.getSaveSql(checkinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(CheckInfo checkinfo, StringBuilder sqlbuilder) {
		return checkdao.getDeleteSql(checkinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(CheckInfo checkinfo, StringBuilder sqlbuilder) {
		return checkdao.getUpdateSql(checkinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return checkdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public CheckInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<CheckInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return checkdao;
	}
	
	

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}
}

