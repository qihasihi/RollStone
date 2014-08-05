package com.school.manager.inter;

import java.util.List;
import java.util.Map;

import com.school.entity.RoleUser;
import com.school.entity.UserInfo;
import com.school.entity.teachpaltform.TpTaskInfo;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;

public interface IUserManager extends IBaseManager<UserInfo> {
	
	public int checkUsername(String username);
	
	public UserInfo doLogin(UserInfo user);
	
	public UserInfo getUserInfo(UserInfo user);
	
	/**
	 * 获取用户 根据角色信息
	 * @param year
	 * @param isselstu (是否只查询学生) true :只查询学生
	 * @param isseljz (是否只查询家长) true :只查询家长
	 * @param ru
	 * @param presult
	 * @return
	 */
	 
	public List<UserInfo>getUserByCondition(String year,boolean isselstu,boolean isseljz,RoleUser ru,int dc_school_id,PageResult presult);
	
	public List<UserInfo> getUserForSelect(String roleid,String clsid,String cname,String grade,String username,PageResult presult);

	public  Map<String,Object>  getTestUser(String string, String string2,
			PageResult presult);

    /**
     * 获取未完成任务的学生名单
     * @param t
     * @return
     */
    List<UserInfo>getUserNotCompleteTask(TpTaskInfo t,String flag);
}
