
package  com.school.manager;

import java.util.List;
import java.util.Map;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.IMyInfoUserDAO;

import com.school.entity.MyInfoUserInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IMyInfoUserManager;
import com.school.util.PageResult;

@Service
public class  MyInfoUserManager extends BaseManager<MyInfoUserInfo> implements IMyInfoUserManager  {

    private IMyInfoUserDAO myinfouserdao;

    @Autowired
    @Qualifier("myInfoUserDAO")
    public void setMyinfouserdao(IMyInfoUserDAO myinfouserdao) {
        this.myinfouserdao = myinfouserdao;
    }

    public Boolean doSave(MyInfoUserInfo myinfouserinfo) {
        return myinfouserdao.doSave(myinfouserinfo);
    }

    public Boolean doDelete(MyInfoUserInfo myinfouserinfo) {
        return myinfouserdao.doDelete(myinfouserinfo);
    }

    public Boolean doUpdate(MyInfoUserInfo myinfouserinfo) {
        return myinfouserdao.doUpdate(myinfouserinfo);
    }

    public List<MyInfoUserInfo> getList(MyInfoUserInfo myinfouserinfo, PageResult presult) {
        return myinfouserdao.getList(myinfouserinfo,presult);
    }

    public List<Object> getSaveSql(MyInfoUserInfo myinfouserinfo, StringBuilder sqlbuilder) {
        return myinfouserdao.getSaveSql(myinfouserinfo,sqlbuilder);
    }

    public List<Object> getDeleteSql(MyInfoUserInfo myinfouserinfo, StringBuilder sqlbuilder) {
        return myinfouserdao.getDeleteSql(myinfouserinfo,sqlbuilder);
    }

    public List<Object> getUpdateSql(MyInfoUserInfo myinfouserinfo, StringBuilder sqlbuilder) {
        return myinfouserdao.getUpdateSql(myinfouserinfo,sqlbuilder);
    }

    @Override
    protected ICommonDAO<MyInfoUserInfo> getBaseDAO() {
        // TODO Auto-generated method stub
        return myinfouserdao;
    }
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
    public boolean doSaveTongYongThpjTimesteup(Integer userref,int typeid){
        return myinfouserdao.doSaveTongYongThpjTimesteup(userref, typeid);
    }
    /**
     * ��ѯ��ҳ��Ϣһ��ʱ���ڵ���Ϣ����
     * @param msgid
     * @param userref
     * @param btime
     * @param etime
     * @return
     */
    public Integer getMyInfoUserInfoCountFirstPage(Integer msgid,String userref,String btime,String etime){
        return myinfouserdao.getMyInfoUserInfoCountFirstPage(msgid, userref, btime, etime);
    }
    /**
     * �õ���ҳ�Ķ�̬���ݸ���
     * @param userref
     * @return
     */
    public List<Map<String,Object>> getSYMsgDataCount(String userref){
        return myinfouserdao.getSYMsgDataCount(userref);
    }
    /**
     * �õ���ҳ�Ķ�̬����
     * @param userref
     * @return
     */
    public List<MyInfoUserInfo> getSYMsgData(String userref){
        return myinfouserdao.getSYMsgData(userref);
    }
    /**
     * �õ�Im�Ķ�̬����
     * @return
     */
    public Integer getImMsgData(Integer clsid,Integer userid,String year){
        return myinfouserdao.getImMsgData(clsid,userid,year);
    }
    /**
     * �õ�Im1.1.3�в�ѯ��̬������༶�Ķ�̬
     * @param clsid
     * @param userid
     * @return
     */
    public List<MyInfoUserInfo> getImMsgData(Integer clsid,Integer userid){
        return myinfouserdao.getImMsgData(clsid,userid);
    }
    /**
     * ��ѯ����༶��̬��Ϣ
     * @param userid
     * @param classid
     * @param dcschoolid
     * @return
     */
    public List<Map<String,Object>> getUserClassInJoinLog(int userid,int classid,int dcschoolid){
        return myinfouserdao.getUserClassInJoinLog(userid,classid,dcschoolid);
    }
    /**
     * ��¼����༶��̬
     * @param userid
     * @param classid
     * @param dcschoolid
     * @param allowInJoin 1:����  0��������
     * @param operateid
     * @return
     */
    public boolean doSaveUserClassInJoinLog(int userid,int classid,int dcschoolid,int allowInJoin,int operateid){
        return myinfouserdao.doSaveUserClassInJoinLog(userid,classid,dcschoolid,allowInJoin,operateid);
    }

    @Override
    public String getNextId() {
        // TODO Auto-generated method stub
        return myinfouserdao.getNextId();
    }

    public MyInfoUserInfo getOfExcel(Sheet rs, int cols, int d, String type) {
        // TODO Auto-generated method stub
        return null;
    }
}

