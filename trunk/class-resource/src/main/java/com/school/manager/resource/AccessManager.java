
package  com.school.manager.resource;

import java.util.List;
import java.util.UUID;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.resource.IAccessDAO;

import com.school.entity.resource.AccessInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.resource.IAccessManager;
import com.school.util.PageResult;

@Service
public class  AccessManager extends BaseManager<AccessInfo> implements IAccessManager  { 
	
	private IAccessDAO accessdao;
	
	@Autowired
	@Qualifier("accessDAO")
	public void setAccessdao(IAccessDAO accessdao) {
		this.accessdao = accessdao;
	}
	
	public Boolean doSave(AccessInfo accessinfo) {
		return accessdao.doSave(accessinfo);
	}
	
	public Boolean doDelete(AccessInfo accessinfo) {
		return accessdao.doDelete(accessinfo);
	}

	public Boolean doUpdate(AccessInfo accessinfo) {
		return accessdao.doUpdate(accessinfo);
	}
	
	public List<AccessInfo> getList(AccessInfo accessinfo, PageResult presult) {
		return accessdao.getList(accessinfo,presult);	
	}
	
	public List<Object> getSaveSql(AccessInfo accessinfo, StringBuilder sqlbuilder) {
		return accessdao.getSaveSql(accessinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(AccessInfo accessinfo, StringBuilder sqlbuilder) {
		return accessdao.getDeleteSql(accessinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(AccessInfo accessinfo, StringBuilder sqlbuilder) {
		return accessdao.getUpdateSql(accessinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return accessdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public AccessInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<AccessInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}
	
	public Boolean doSaveOrUpdate(AccessInfo accessinfo){
		return accessdao.doSaveOrUpdate(accessinfo);
	}
}

