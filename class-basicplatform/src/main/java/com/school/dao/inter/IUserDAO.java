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
	 * ��ȡ�û� ���ݽ�ɫ��Ϣ
	 * @param year
	 * @param isselstu (�Ƿ�ֻ��ѯѧ��) true :ֻ��ѯѧ��
	 * @param isseljz (�Ƿ�ֻ��ѯѧ��) true :ֻ��ҳ�
	 * @param ru
	 * @param presult
	 * @return
	 */
	 
	public List<UserInfo>getUserByCondition(String year,boolean isselstu,boolean isseljz,RoleUser ru,int dc_school_id,PageResult presult);
	
	public List<UserInfo> getUserForSelect(String roleid,String clsid,String cname,String grade,String username,PageResult presult);

	public  Map<String,Object>  getTestUser(String year, String relation, PageResult presult);
    /**
     * ��ETTUSER_ID��ETT_USER_ID����Ϊ���ֶ�
     * @param ettUserid
     * @param sqlbuilder
     * @return
     */
    public List<Object> getUpdateEttUserByEttUserIdSql(final Integer ettUserid,StringBuilder sqlbuilder);
    /**
     * ��ȡδ��������ѧ������
     * @param flag=1 ��ѯδ���
     * @return
     */
    List<UserInfo>getUserNotCompleteTask(Long taskid,Integer userid,Integer classid,String flag);

    public List<Map<String,Object>> getCourseTaskCount(final Integer userid);

    Integer getUserId(Integer jid, Long taskId, Integer classId);
}
