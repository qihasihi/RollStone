
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.interactive.TpTopicInfo;

import java.util.List;
import java.util.Map;

/**
 * 互动空间论题
 */
public interface ITpTopicDAO extends ICommonDAO<TpTopicInfo>{
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
}
