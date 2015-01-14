
package com.school.dao.inter.teachpaltform.interactive;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.interactive.TpTopicThemeInfo;

import java.util.List;

public interface ITpTopicThemeDAO extends ICommonDAO<TpTopicThemeInfo>{
    /**
     * �õ�ͬ����SQL
     * @param entity  ����ʵ��
     * @return
     */
    public void getSynchroSql(TpTopicThemeInfo entity,List<String> sqlArrayList,List<List<Object>> objArrayList);

    public Integer getPingLunShu(final Long topicid,final  Integer clsid);
}
