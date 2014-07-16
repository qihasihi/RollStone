
package  com.school.manager.inter;

import java.util.List;

import com.school.entity.MyInfoUserInfo;
import com.school.manager.base.IBaseManager;

public interface IMyInfoUserManager  extends IBaseManager<MyInfoUserInfo> { 
	/**
	 * �����Ϣ��ͨ�� ����ϵͳ
	 * @param templateid  ģ��ID
	 * @param userref   ������REF
	 * @param msgid    ��Ϣ����
	 * @param msgname   ��Ϣ����
	 * @param operateuserref  ������
	 * @param data    �����������
	 * @return
	 */
	public boolean doSaveTongYongThpjTimesteup(Integer userref,int typeid);
	
	/**
	 * ��ѯ��ҳ��Ϣһ��ʱ���ڵ���Ϣ����
	 * @param msgid
	 * @param userref
	 * @param btime
	 * @param etime
	 * @return
	 */
	public Integer getMyInfoUserInfoCountFirstPage(Integer msgid,String userref,String btime,String etime);
} 
