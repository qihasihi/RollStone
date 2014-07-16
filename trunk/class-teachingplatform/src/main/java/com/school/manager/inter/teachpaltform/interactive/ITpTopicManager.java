
package  com.school.manager.inter.teachpaltform.interactive;

import com.school.entity.teachpaltform.interactive.TpTopicInfo;
import com.school.manager.base.IBaseManager;

import java.util.List;
import java.util.Map;

/**
 * 互动空间论题
 */
public interface ITpTopicManager  extends IBaseManager<TpTopicInfo> {
    /**
     * 得到互动空间首页显示的专题栏目
     * @param userref 查询用户的ref
     * @param termid  学期id
     * @return
     */
    public List<Map<String,Object>> getListTopicIndex(String userref,String termid);

    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(TpTopicInfo entity,StringBuilder sqlbuilder);
    
    /**
     * 根据CourseId得到互动空间的统计
     * @param courseid
     * @return
     */    
    public List<Map<String,Object>> getTopicStaticesPageIdx(Long courseid,String loginref,Integer roleInt);
    /**
     *得到论题统计页面数据
     * @param topicid
     * @param clsid
     * @return
     */
    public List<Map<String,Object>> getTopicStatices(Long topicid,Integer clsid,Integer clstype);
    /**
     *得到互动空间班级信息
     * @param courseid
     * @param userid
     * @param roletype
     * @return
     */
    public List<Map<String,Object>> getInteractiveClass(Long courseid,Integer userid,Integer roletype);


} 
