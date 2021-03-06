
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.interactive.TpTopicThemeInfo;

import java.util.List;

public interface ITpTopicThemeDAO extends ICommonDAO<TpTopicThemeInfo>{
    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @return
     */
    public void getSynchroSql(TpTopicThemeInfo entity,List<String> sqlArrayList,List<List<Object>> objArrayList);
}
