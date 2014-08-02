package com.school.manager.inter.teachpaltform.award;

import com.school.entity.teachpaltform.award.TpGroupScore;
import com.school.manager.base.IBaseManager;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengzhou on 14-6-24.
 */
public interface ITpGroupScoreManager extends IBaseManager<TpGroupScore> {
    /**
     * �õ�ҳ���ϵĲ�ѯ
     * @param courseid
     * @param classid
     * @param classtype
     * @return
     */
    public List<Map<String,Object>> getPageDataList(final Long courseid,final Long classid,final Integer classtype,final Integer subjectid);
    /**
     * ��ӻ����
     * @param entity
     * @return
     */
    public boolean AddOrUpdate(final TpGroupScore entity);
}
