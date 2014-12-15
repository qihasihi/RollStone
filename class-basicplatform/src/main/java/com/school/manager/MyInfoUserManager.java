
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
     * 添加消息，通用 评教系统
     * @param templateid  模版ID
     * @param userref   接收人REF
     * @param msgid    消息类型
     * @param msgname   消息名称
     * @param operateuserref  操作人
     * @param data    组合数据类型
     * @return
     */
    public boolean doSaveTongYongThpjTimesteup(Integer userref,int typeid){
        return myinfouserdao.doSaveTongYongThpjTimesteup(userref, typeid);
    }
    /**
     * 查询首页消息一段时间内的消息数量
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
     * 得到首页的动态数据个数
     * @param userref
     * @return
     */
    public List<Map<String,Object>> getSYMsgDataCount(String userref){
        return myinfouserdao.getSYMsgDataCount(userref);
    }
    /**
     * 得到首页的动态数据
     * @param userref
     * @return
     */
    public List<MyInfoUserInfo> getSYMsgData(String userref){
        return myinfouserdao.getSYMsgData(userref);
    }
    /**
     * 得到Im的动态数据
     * @return
     */
    public Integer getImMsgData(Integer clsid,Integer userid,String year){
        return myinfouserdao.getImMsgData(clsid,userid,year);
    }
    /**
     * 得到Im1.1.3中查询动态，加入班级的动态
     * @param clsid
     * @param userid
     * @return
     */
    public List<MyInfoUserInfo> getImMsgData(Integer clsid,Integer userid){
        return myinfouserdao.getImMsgData(clsid,userid);
    }
    /**
     * 查询加入班级动态信息
     * @param userid
     * @param classid
     * @param dcschoolid
     * @return
     */
    public List<Map<String,Object>> getUserClassInJoinLog(int userid,int classid,int dcschoolid){
        return myinfouserdao.getUserClassInJoinLog(userid,classid,dcschoolid);
    }
    /**
     * 记录加入班级动态
     * @param userid
     * @param classid
     * @param dcschoolid
     * @param allowInJoin 1:允许  0：不允许
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

