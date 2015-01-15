
package com.school.manager.userlog;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.userlog.IUserDynamicInfoDAO;
import com.school.entity.userlog.UserDynamicInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.userlog.IUserDynamicInfoManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDynamicInfoManager extends BaseManager<UserDynamicInfo> implements IUserDynamicInfoManager {
	
	private IUserDynamicInfoDAO userDynamicInfoDAO;
	
	@Autowired
	@Qualifier("userDynamicInfoDAO")
	public void setuserDynamicInfoDAO(IUserDynamicInfoDAO userDynamicInfoDAO) {
		this.userDynamicInfoDAO = userDynamicInfoDAO;
	}
	
	public Boolean doSave(UserDynamicInfo t) {
		return userDynamicInfoDAO.doSave(t);
	}
	
	public Boolean doDelete(UserDynamicInfo t) {
		return userDynamicInfoDAO.doDelete(t);
	}

	public Boolean doUpdate(UserDynamicInfo t) {
		return userDynamicInfoDAO.doUpdate(t);
	}
	
	public List<UserDynamicInfo> getList(UserDynamicInfo t, PageResult presult) {
		return userDynamicInfoDAO.getList(t,presult);	
	}
	
	public List<Object> getSaveSql(UserDynamicInfo t, StringBuilder sqlbuilder) {
		return userDynamicInfoDAO.getSaveSql(t,sqlbuilder);
	}

	public List<Object> getDeleteSql(UserDynamicInfo t, StringBuilder sqlbuilder) {
		return userDynamicInfoDAO.getDeleteSql(t,sqlbuilder);
	}

	public List<Object> getUpdateSql(UserDynamicInfo t, StringBuilder sqlbuilder) {
		return userDynamicInfoDAO.getUpdateSql(t,sqlbuilder);
	}
	public UserDynamicInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<UserDynamicInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return userDynamicInfoDAO;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return userDynamicInfoDAO.getNextId();
	}
}

