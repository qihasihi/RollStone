
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.interactive.TpTopicInfo;

import java.util.List;
import java.util.Map;

/**
 * �����ռ�����
 */
public interface ITpTopicDAO extends ICommonDAO<TpTopicInfo>{
    /**
     * �õ������ռ���ҳ��ʾ��ר����Ŀ
     * @param userref ��ѯ�û���ref
     * @param termid  ѧ��id
     * @return
     */
    public List<Map<String,Object>> getListTopicIndex(String userref,String termid);

    /**
     * �õ�ͬ����SQL
     * @param entity  ����ʵ��
     * @param sqlbuilder  ������SQL��䣬����ʵ������
     * @return
     */
    public List<Object> getSynchroSql(TpTopicInfo entity,StringBuilder sqlbuilder);
}
