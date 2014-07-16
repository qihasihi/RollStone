
package  com.school.manager;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.IIdentityDAO;

import com.school.entity.IdentityInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IIdentityManager;
import com.school.util.PageResult;

@Service
public class  IdentityManager extends BaseManager<IdentityInfo> implements IIdentityManager  { 
	
	private IIdentityDAO identitydao;
	
	@Autowired
	@Qualifier("identityDAO")
	public void setIdentitydao(IIdentityDAO identitydao) {
		this.identitydao = identitydao;
	}
	
	public Boolean doSave(IdentityInfo identityinfo) {
		return identitydao.doSave(identityinfo);
	}
	
	public Boolean doDelete(IdentityInfo identityinfo) {
		return identitydao.doDelete(identityinfo);
	}

	public Boolean doUpdate(IdentityInfo identityinfo) {
		return identitydao.doUpdate(identityinfo);
	}
	
	public List<IdentityInfo> getList(IdentityInfo identityinfo, PageResult presult) {
		return identitydao.getList(identityinfo,presult);	
	}
	
	public List<Object> getSaveSql(IdentityInfo identityinfo, StringBuilder sqlbuilder) {
		return identitydao.getSaveSql(identityinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(IdentityInfo identityinfo, StringBuilder sqlbuilder) {
		return identitydao.getDeleteSql(identityinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(IdentityInfo identityinfo, StringBuilder sqlbuilder) {
		return identitydao.getUpdateSql(identityinfo,sqlbuilder);
	}
	public IdentityInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<IdentityInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return identitydao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return identitydao.getNextId();
	}
}

