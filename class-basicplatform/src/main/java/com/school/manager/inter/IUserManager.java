package com.school.manager.inter;

import java.util.List;
import java.util.Map;

import com.school.entity.RoleUser;
import com.school.entity.UserInfo;

import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


public interface IUserManager extends IBaseManager<UserInfo> {

    public int checkUsername(String username,Integer dcschoolid);

	
	public UserInfo doLogin(UserInfo user);
	public UserInfo getUserInfo(UserInfo user);
	
	/**
	 * ��ȡ�û� ���ݽ�ɫ��Ϣ
	 * @param year
	 * @param isselstu (�Ƿ�ֻ��ѯѧ��) true :ֻ��ѯѧ��
	 * @param isseljz (�Ƿ�ֻ��ѯ�ҳ�) true :ֻ��ѯ�ҳ�
	 * @param ru
	 * @param presult
	 * @return
	 */
	 
	public List<UserInfo>getUserByCondition(String year,boolean isselstu,boolean isseljz,RoleUser ru,int dc_school_id,PageResult presult);
	
	public List<UserInfo> getUserForSelect(String roleid,String clsid,String cname,String grade,String username,PageResult presult);

	public  Map<String,Object>  getTestUser(String string, String string2,
			PageResult presult);

    /**
     * ��ȡδ��������ѧ������
     * @return
     */
    List<UserInfo>getUserNotCompleteTask(Long taskid,Integer userid,Integer classid,String flag);

    /**
     * ��ETTUSER_ID��ETT_USER_ID����Ϊ���ֶ�
     * @param ettUserid
     * @param sqlbuilder
     * @return
     */
    public List<Object> getUpdateEttUserByEttUserIdSql(final Integer ettUserid,StringBuilder sqlbuilder);

    public List<Map<String,Object>> getCourseTaskCount(Integer userid);
}
