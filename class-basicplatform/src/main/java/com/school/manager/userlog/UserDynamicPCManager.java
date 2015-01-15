
package com.school.manager.userlog;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.userlog.IUserDynamicPCDAO;
import com.school.entity.userlog.UserDynamicPcLog;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.userlog.IUserDynamicPCManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDynamicPCManager extends BaseManager<UserDynamicPcLog> implements IUserDynamicPCManager {
	
	private IUserDynamicPCDAO userDynamicPCDAO;
	
	@Autowired
	@Qualifier("userDynamicPCDAO")
	public void setUserDynamicPCDAO(IUserDynamicPCDAO userDynamicPCDAO) {
		this.userDynamicPCDAO = userDynamicPCDAO;
	}
	
	public Boolean doSave(UserDynamicPcLog UserDynamicPcLog) {
		return userDynamicPCDAO.doSave(UserDynamicPcLog);
	}
	
	public Boolean doDelete(UserDynamicPcLog UserDynamicPcLog) {
		return userDynamicPCDAO.doDelete(UserDynamicPcLog);
	}

	public Boolean doUpdate(UserDynamicPcLog UserDynamicPcLog) {
		return userDynamicPCDAO.doUpdate(UserDynamicPcLog);
	}
	
	public List<UserDynamicPcLog> getList(UserDynamicPcLog UserDynamicPcLog, PageResult presult) {
		return userDynamicPCDAO.getList(UserDynamicPcLog,presult);	
	}
	
	public List<Object> getSaveSql(UserDynamicPcLog UserDynamicPcLog, StringBuilder sqlbuilder) {
		return userDynamicPCDAO.getSaveSql(UserDynamicPcLog,sqlbuilder);
	}

	public List<Object> getDeleteSql(UserDynamicPcLog UserDynamicPcLog, StringBuilder sqlbuilder) {
		return userDynamicPCDAO.getDeleteSql(UserDynamicPcLog,sqlbuilder);
	}

	public List<Object> getUpdateSql(UserDynamicPcLog UserDynamicPcLog, StringBuilder sqlbuilder) {
		return userDynamicPCDAO.getUpdateSql(UserDynamicPcLog,sqlbuilder);
	}
	public UserDynamicPcLog getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<UserDynamicPcLog> getBaseDAO() {
		// TODO Auto-generated method stub
		return userDynamicPCDAO;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return userDynamicPCDAO.getNextId();
	}
}

