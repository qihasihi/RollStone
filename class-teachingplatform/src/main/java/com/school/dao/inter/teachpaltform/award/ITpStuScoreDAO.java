package com.school.dao.inter.teachpaltform.award;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.award.TpGroupScore;
import com.school.entity.teachpaltform.award.TpStuScore;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengzhou on 14-6-24.
 */
public interface ITpStuScoreDAO extends ICommonDAO<TpStuScore> {
    boolean AddOrUpdate(final TpStuScore entity);
    /**
     * �õ�ҳ���ϵĲ�ѯ
     * @param courseid
     * @param classid
     * @param classtype
     * @return
     */
    public List<Map<String,Object>> getPageDataList(final Long courseid,final Long classid,final Integer classtype,final Integer subjectid,final String groupidStr);


    /**
     * ��ʦ�鳤�ύʱ����ʼ���������
     * @param obj
     * @return
     */
    public boolean stuScoreLastInit(final TpStuScore obj,final String groupidArr);
}
