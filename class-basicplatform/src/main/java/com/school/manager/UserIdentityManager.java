
package  com.school.manager;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.IUserIdentityDAO;

import com.school.entity.UserIdentityInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IUserIdentityManager;
import com.school.util.PageResult;

@Service
public class  UserIdentityManager extends BaseManager<UserIdentityInfo> implements IUserIdentityManager  { 
	
	private IUserIdentityDAO useridentitydao;
	
	@Autowired
	@Qualifier("userIdentityDAO")
	public void setUseridentitydao(IUserIdentityDAO useridentitydao) {
		this.useridentitydao = useridentitydao;
	}
	
	public Boolean doSave(UserIdentityInfo useridentityinfo) {
		return useridentitydao.doSave(useridentityinfo);
	}
	
	public Boolean doDelete(UserIdentityInfo useridentityinfo) {
		return useridentitydao.doDelete(useridentityinfo);
	}

	public Boolean doUpdate(UserIdentityInfo useridentityinfo) {
		return useridentitydao.doUpdate(useridentityinfo);
	}
	
	public List<UserIdentityInfo> getList(UserIdentityInfo useridentityinfo, PageResult presult) {
		return useridentitydao.getList(useridentityinfo,presult);	
	}
	
	public List<Object> getSaveSql(UserIdentityInfo useridentityinfo, StringBuilder sqlbuilder) {
		return useridentitydao.getSaveSql(useridentityinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(UserIdentityInfo useridentityinfo, StringBuilder sqlbuilder) {
		return useridentitydao.getDeleteSql(useridentityinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(UserIdentityInfo useridentityinfo, StringBuilder sqlbuilder) {
		return useridentitydao.getUpdateSql(useridentityinfo,sqlbuilder);
	}
	public UserIdentityInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<UserIdentityInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return useridentitydao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return useridentitydao.getNextId();
	}
}

