
package  com.school.manager.inter;

import java.util.List;
import java.util.Map;

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

    /**
     * �õ�Im�Ķ�̬����
     * @return
     */
    public Integer getImMsgData(Integer clsid,Integer userid,String year);
    /**
     * �õ�Im1.1.3�в�ѯ��̬������༶�Ķ�̬
     * @param clsid
     * @param userid
     * @return
     */
    public List<MyInfoUserInfo> getImMsgData(Integer clsid,Integer userid);
    /**
     * ��ѯ����༶��̬��Ϣ
     * @param userid
     * @param classid
     * @param dcschoolid
     * @return
     */
    public List<Map<String,Object>> getUserClassInJoinLog(int userid,int classid,int dcschoolid);
    /**
     * ��¼����༶��̬
     * @param userid
     * @param classid
     * @param dcschoolid
     * @param allowInJoin 1:����  0��������
     * @param operateid
     * @return
     */
    public boolean doSaveUserClassInJoinLog(int userid,int classid,int dcschoolid,int allowInJoin,int operateid);
} 
