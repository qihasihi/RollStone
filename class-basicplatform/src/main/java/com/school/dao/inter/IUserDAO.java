package com.school.dao.inter;

import com.school.dao.base.ICommonDAO;
import com.school.entity.RoleUser;
import com.school.entity.UserInfo;
import com.school.util.PageResult;

import java.util.List;
import java.util.Map;

public interface IUserDAO extends ICommonDAO<UserInfo> {
	
	public int checkUsername(String username,Integer dcschoolid);
	
	public UserInfo doLogin(UserInfo user);
	
	public UserInfo getUser(UserInfo user);
	
	/**
	 * 获取用户 根据角色信息
	 * @param year
	 * @param isselstu (是否只查询学生) true :只查询学生
	 * @param isseljz (是否只查询学生) true :只查家长
	 * @param ru
	 * @param presult
	 * @return
	 */
	 
	public List<UserInfo>getUserByCondition(String year,boolean isselstu,boolean isseljz,RoleUser ru,int dc_school_id,PageResult presult);
	
	public List<UserInfo> getUserForSelect(String roleid,String clsid,String cname,String grade,String username,PageResult presult);

	public  Map<String,Object>  getTestUser(String year, String relation, PageResult presult);
    /**
     * 用ETTUSER_ID将ETT_USER_ID更新为空字段
     * @param ettUserid
     * @param sqlbuilder
     * @return
     */
    public List<Object> getUpdateEttUserByEttUserIdSql(final Integer ettUserid,StringBuilder sqlbuilder);
    /**
     * 获取未完成任务的学生名单
     * @param flag=1 查询未完成
     * @return
     */
    List<UserInfo>getUserNotCompleteTask(Long taskid,Integer userid,Integer classid,String flag);

    public List<Map<String,Object>> getCourseTaskCount(final Integer userid);

    Integer getUserId(Integer jid, Long taskId, Integer classId);
}
