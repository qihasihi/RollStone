
package  com.school.manager;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.IUserColumnRightDAO;

import com.school.entity.UserColumnRightInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IUserColumnRightManager;
import com.school.util.PageResult;

@Service
public class  UserColumnRightManager extends BaseManager<UserColumnRightInfo> implements IUserColumnRightManager  { 
	
	private IUserColumnRightDAO usercolumnrightdao;
	
	@Autowired
	@Qualifier("userColumnRightDAO")
	public void setUsercolumnrightdao(IUserColumnRightDAO usercolumnrightdao) {
		this.usercolumnrightdao = usercolumnrightdao;
	}
	
	public Boolean doSave(UserColumnRightInfo usercolumnrightinfo) {
		return usercolumnrightdao.doSave(usercolumnrightinfo);
	}
	
	public Boolean doDelete(UserColumnRightInfo usercolumnrightinfo) {
		return usercolumnrightdao.doDelete(usercolumnrightinfo);
	}

	public Boolean doUpdate(UserColumnRightInfo usercolumnrightinfo) {
		return usercolumnrightdao.doUpdate(usercolumnrightinfo);
	}
	
	public List<UserColumnRightInfo> getList(UserColumnRightInfo usercolumnrightinfo, PageResult presult) {
		return usercolumnrightdao.getList(usercolumnrightinfo,presult);	
	}
	
	public List<Object> getSaveSql(UserColumnRightInfo usercolumnrightinfo, StringBuilder sqlbuilder) {
		return usercolumnrightdao.getSaveSql(usercolumnrightinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(UserColumnRightInfo usercolumnrightinfo, StringBuilder sqlbuilder) {
		return usercolumnrightdao.getDeleteSql(usercolumnrightinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(UserColumnRightInfo usercolumnrightinfo, StringBuilder sqlbuilder) {
		return usercolumnrightdao.getUpdateSql(usercolumnrightinfo,sqlbuilder);
	}
	public UserColumnRightInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<UserColumnRightInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return usercolumnrightdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return usercolumnrightdao.getNextId();
	}
}

