
package com.school.dao.inter;

import com.school.dao.base.ICommonDAO;
import com.school.entity.MyInfoUserInfo;

import java.util.List;
import java.util.Map;

public interface IMyInfoUserDAO extends ICommonDAO<MyInfoUserInfo>{
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

    /**
     * �õ���ҳ�Ķ�̬���ݸ���
     * @param userref
     * @return
     */
    public List<Map<String,Object>> getSYMsgDataCount(String userref);
    /**
     * �õ���ҳ�Ķ�̬����
     * @param userref
     * @return
     */
    public List<MyInfoUserInfo> getSYMsgData(String userref);
}
