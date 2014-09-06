
package com.school.dao.inter.teachpaltform.award;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.award.TpStuScoreLogs;

import java.util.List;
import java.util.Map;

public interface ITpStuScoreLogsDAO extends ICommonDAO<TpStuScoreLogs>{


    /**
     * ��ʦ�������β鿴ѧ������ͳ��ҳ��
     * @param termid
     * @param classid
     * @param subjectid
     * @param orderby
     * @return
     */
    public List<Map<String,Object>> getStuScoreTeachStatices(final String termid,final Integer classid,Integer subjectid,Integer orderby);
}
