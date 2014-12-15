
package  com.school.manager.inter;

import java.util.List;
import java.util.Map;

import com.school.entity.MyInfoUserInfo;
import com.school.manager.base.IBaseManager;

public interface IMyInfoUserManager  extends IBaseManager<MyInfoUserInfo> {
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
    public boolean doSaveTongYongThpjTimesteup(Integer userref,int typeid);

    /**
     * 查询首页消息一段时间内的消息数量
     * @param msgid
     * @param userref
     * @param btime
     * @param etime
     * @return
     */
    public Integer getMyInfoUserInfoCountFirstPage(Integer msgid,String userref,String btime,String etime);

    /**
     * 得到首页的动态数据个数
     * @param userref
     * @return
     */
    public List<Map<String,Object>> getSYMsgDataCount(String userref);
    /**
     * 得到首页的动态数据
     * @param userref
     * @return
     */
    public List<MyInfoUserInfo> getSYMsgData(String userref);

    /**
     * 得到Im的动态数据
     * @return
     */
    public Integer getImMsgData(Integer clsid,Integer userid,String year);
    /**
     * 得到Im1.1.3中查询动态，加入班级的动态
     * @param clsid
     * @param userid
     * @return
     */
    public List<MyInfoUserInfo> getImMsgData(Integer clsid,Integer userid);
    /**
     * 查询加入班级动态信息
     * @param userid
     * @param classid
     * @param dcschoolid
     * @return
     */
    public List<Map<String,Object>> getUserClassInJoinLog(int userid,int classid,int dcschoolid);
    /**
     * 记录加入班级动态
     * @param userid
     * @param classid
     * @param dcschoolid
     * @param allowInJoin 1:允许  0：不允许
     * @param operateid
     * @return
     */
    public boolean doSaveUserClassInJoinLog(int userid,int classid,int dcschoolid,int allowInJoin,int operateid);
} 
