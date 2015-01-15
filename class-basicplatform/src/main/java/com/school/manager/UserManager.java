package com.school.manager;

import java.util.List;
import java.util.Map;

import com.school.entity.teachpaltform.TpTaskInfo;
import com.school.entity.userlog.UserDynamicPcLog;
import com.school.manager.inter.teachpaltform.ITpTaskManager;
import com.school.manager.inter.userlog.IUserDynamicPCManager;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.IUserDAO;
import com.school.entity.RoleUser;
import com.school.entity.UserInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IOperateExcelManager;
import com.school.manager.inter.IUserManager;
import com.school.util.PageResult;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service


public class UserManager extends BaseManager<UserInfo> implements IUserManager{
	@Autowired
	@Qualifier("userDAO")
	private IUserDAO userdao;

    @Autowired
    private IUserDynamicPCManager userDynamicPCManager;

	public void setUserdao(IUserDAO userdao) {
		this.userdao = userdao;
	}


    /**
     * 用ETTUSER_ID将ETT_USER_ID更新为空字段
     * @param ettUserid
     * @param sqlbuilder
     * @return
     */
    public List<Object> getUpdateEttUserByEttUserIdSql(final Integer ettUserid,StringBuilder sqlbuilder){
        return userdao.getUpdateEttUserByEttUserIdSql(ettUserid,sqlbuilder);
    }
	@Override
	public Boolean doDelete(UserInfo obj) {
		return userdao.doDelete(obj);
	}

	@Override
	public Boolean doSave(UserInfo obj) {
		return userdao.doSave(obj);
	}

	@Override
	public Boolean doUpdate(UserInfo obj) {
		return userdao.doUpdate(obj);
	}

	@Override
	protected ICommonDAO<UserInfo> getBaseDAO() {
		return userdao;
	}

	@Override
	public List<UserInfo> getList(UserInfo obj, PageResult presult) {
		return userdao.getList(obj, presult);
	}


	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.userdao.getNextId();
	}

	public List<Object> getDeleteSql(UserInfo obj, StringBuilder sqlbuilder) {
		return userdao.getDeleteSql(obj, sqlbuilder);
	}

	public List<Object> getSaveSql(UserInfo obj, StringBuilder sqlbuilder) {
		return userdao.getSaveSql(obj, sqlbuilder);
	}

	public List<Object> getUpdateSql(UserInfo obj, StringBuilder sqlbuilder) {
		return userdao.getUpdateSql(obj, sqlbuilder);
	}



	public int checkUsername(String username,Integer dcschoolid) {
		// TODO Auto-generated method stub
		return this.userdao.checkUsername(username,dcschoolid);
	}



	public UserInfo doLogin(UserInfo user) {
		// TODO Auto-generated method stub
		return this.userdao.doLogin(user);
	}


    public UserInfo getUserInfo(UserInfo user) {
		// TODO Auto-generated method stub
		return this.userdao.getUser(user);
	}



	public UserInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}



	public List<UserInfo> getUserByCondition(String year, boolean isselstu,boolean isseljz,
			RoleUser ru,int dc_school_id, PageResult presult) {
		// TODO Auto-generated method stub
		return this.userdao.getUserByCondition(year, isselstu,isseljz, ru,dc_school_id, presult);
	}



	public List<UserInfo> getUserForSelect(String roleid,String clsid,String cname,String grade, String username,
			PageResult presult) {
		// TODO Auto-generated method stub
		
		return this.userdao.getUserForSelect(roleid,clsid,cname,grade, username, presult);
	}



	public  Map<String,Object>  getTestUser(String year, String relation,
			PageResult presult) {
		// TODO Auto-generated method stub
		return this.userdao.getTestUser(year,relation,presult);
	}

    public List<UserInfo>getUserNotCompleteTask(Long taskid,Integer userid,Integer classid,String flag){
        return this.userdao.getUserNotCompleteTask(taskid,userid,classid,flag);
    }

    public List<Map<String,Object>> getCourseTaskCount(Integer userid){
        return this.userdao.getCourseTaskCount(userid);
    }
}
